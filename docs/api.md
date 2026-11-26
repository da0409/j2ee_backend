# 校园活动发现与个人计划平台 - API 接口文档

> 版本：V1.0　｜　维护人：全员（后端 B 汇总）　｜　最后更新：2026-11-26
>
> 本文与《规范文档.md》第四章保持一致。任何接口变化都必须同步更新本文档（规范文档 8.4）。

## 1. 通用约定

| 项目 | 约定 |
|---|---|
| API 前缀 | `/api` |
| 请求/响应格式 | JSON（`Content-Type: application/json`） |
| 登录认证 | `Authorization: Bearer <token>`（JWT） |
| 统一响应 | `{ "code": 200, "message": "success", "data": {...} }` |
| 字段命名 | JSON 使用 camelCase，数据库使用 snake_case |
| 时间格式 | `yyyy-MM-dd'T'HH:mm:ss`，时区 `Asia/Shanghai` |
| 活动状态 | `upcoming`（即将开始）/ `ended`（已结束），由后端按 `date` 计算 |

### 状态码

| code / HTTP | 含义 |
|---|---|
| 200 | 成功 |
| 400 | 参数错误或业务校验失败 |
| 401 | 未登录或登录凭证无效 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

错误响应示例：

```json
{
  "code": 400,
  "message": "用户名已存在",
  "data": null
}
```

## 2. 用户模块（负责人：成员 B，已完成）

### 2.1 用户注册

```http
POST /api/auth/register
```

请求：

```json
{
  "username": "student01",
  "password": "123456",
  "realName": "张三",
  "studentId": "20260001",
  "email": "student@example.com"
}
```

响应：

```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "id": 1,
    "username": "student01",
    "realName": "张三",
    "studentId": "20260001",
    "email": "student@example.com",
    "avatar": null
  }
}
```

业务规则：用户名不能为空且唯一（重复返回 400 用户名已存在）；密码不能为空，服务端使用 BCrypt 哈希后入库；响应不包含密码。

### 2.2 用户登录

```http
POST /api/auth/login
```

请求：

```json
{
  "username": "student01",
  "password": "123456"
}
```

响应：

```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "user": {
      "id": 1,
      "username": "student01",
      "realName": "张三",
      "studentId": "20260001",
      "email": "student@example.com",
      "avatar": null
    }
  }
}
```

业务规则：用户名或密码错误返回 400「用户名或密码错误」；token 有效期默认 24 小时（`app.jwt.expiration-hours`）。

### 2.3 获取当前用户资料

```http
GET /api/users/me
Authorization: Bearer <token>
```

响应：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "username": "student01",
    "realName": "张三",
    "studentId": "20260001",
    "email": "student@example.com",
    "avatar": null
  }
```

业务规则：只允许修改 `realName / studentId / email / avatar`；用户 ID 与用户名不可通过本接口修改；当前用户身份只从 Token 获取。

## 3. 活动模块（负责人：成员 C，待实现）

> 以下接口按《规范文档.md》4.3 定义，代码位置 `backend/src/main/java/com/campus/activity/`。

### 3.1 获取活动列表

```http
GET /api/activities?keyword=篮球&category=体育&status=upcoming&sort=time_asc&page=1&size=10
```

| 参数 | 类型 | 说明 |
|---|---|---|
| keyword | String | 标题模糊搜索 |
| category | String | 活动分类 |
| status | String | `upcoming` / `ended` |
| sort | String | `time_asc` / `time_desc` / `popular` |
| page | Integer | 页码，从 1 开始 |
| size | Integer | 每页数量 |

响应 `data`：`{ "records": [...], "total": 1, "page": 1, "size": 10, "pages": 1 }`，单条记录字段：
`id, title, category, image, date, location, organizer, status, participantCount, joined`。

### 3.2 获取活动详情

```http
GET /api/activities/{id}
```

响应 `data` 在列表字段基础上增加 `description`。不存在返回 404。

### 3.3 获取活动分类

```http
GET /api/activities/categories
```

响应 `data`：`["体育", "学术", "文艺", "志愿服务", "其他"]`。

### 3.4 获取报名人数

```http
GET /api/activities/{id}/participants/count
```

响应 `data`：`{ "activityId": 1, "participantCount": 30 }`。

## 4. 报名业务（负责人：成员 C，待实现）

| 接口 | 方法 | 需登录 | 响应 data |
|---|---|---|---|
| `/api/activities/{id}/join` | POST | 是 | `{ "activityId": 1, "joined": true }`，message「报名成功」 |
| `/api/activities/{id}/join` | DELETE | 是 | `{ "activityId": 1, "joined": false }`，message「取消成功」 |
| `/api/users/me/activities` | GET | 是 | `{ "records": [...], "total": 1, "page": 1, "size": 10 }`，记录含 `joinedAt` |
| `/api/activities/{id}/participation` | GET | 是 | `{ "activityId": 1, "joined": true }` |

业务规则：

- 活动必须存在，已结束的活动不能报名。
- 同一用户不能重复报名，由 `user_activity` 的 `(user_id, activity_id)` 唯一约束兜底并发。
- 报名状态以 `user_activity` 表为最终依据，不信任前端传入的 userId。

## 5. 后端对成员 C 的约定（后端 B 已提供）

1. 统一响应使用 `com.campus.common.Result#success(...)`。
2. 业务校验失败抛 `com.campus.common.BusinessException`（默认 400，可指定 404 等），由 `GlobalExceptionHandler` 统一转换。
3. 需要登录的接口在 Controller 参数上标注 `@CurrentUserId Long userId` 获取当前用户。
4. 公开接口（活动列表、详情、分类、报名人数）已在 `application.yml` 的 `app.security.public-paths` 中放行；如需新增公开接口，请与成员 B 确认后统一修改配置。

}
```

### 2.4 修改个人资料

```http
PUT /api/users/me
Authorization: Bearer <token>
```

请求：

```json
{
  "realName": "张三",
  "studentId": "20260001",
  "email": "new@example.com",
  "avatar": null
}
```

响应：

```json
{
  "code": 200,
  "message": "修改成功",
  "data": {
    "id": 1,
    "username": "student01",
    "realName": "张三",
    "studentId": "20260001",
    "email": "new@example.com",
    "avatar": null
  }
}
```

业务规则：只允许修改 `realName / studentId / email / avatar`；用户 ID 与用户名不可通过本接口修改；当前用户身份只从 Token 获取。
