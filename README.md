# 校园活动发现与个人计划平台（Campus Activity Platform）

JavaEE 课程大项目 · 选题 A

学生登录后可浏览、搜索、筛选校园活动，报名 / 取消报名，查看「我的活动」，并维护个人资料。
活动数据、报名关系与个人资料全部持久化到 MySQL。

运行链路：`Vue 3 → HTTP/JSON → Spring Boot → MySQL`

## 一、小组成员与分工

| 成员 | 角色 | 负责内容 | 分支 |
|---|---|---|---|
| 成员 A | 前端负责人 | `frontend/`：路由、页面、组件、Axios 封装、联调 | `frontend-dev` |
| 成员 B | 后端负责人 | Spring Boot 初始化、数据库与 `users` 表、注册登录、JWT、个人资料接口、统一响应、全局异常、跨域、`database/init.sql`、README 与后端整合 | `backend-user` |
| 成员 C | 后端业务负责人 | `activities` / `user_activity` 表、活动预置数据、活动列表/详情/搜索/筛选/排序、报名与取消报名、我的活动、报名状态与人数 | `backend-activity` |

`main` 分支由成员 B 管理，各成员在各自分支开发后合并。

## 二、技术栈与版本

| 层次 | 技术与版本 |
|---|---|
| 前端 | Vue 3 + Vite + Vue Router + Axios（可选 Element Plus / Pinia） |
| 后端 | Spring Boot 3.5.x（Java 17+）、Spring Web、Validation、MyBatis 3.0.x、JJWT 0.12.x、spring-security-crypto（BCrypt） |
| 数据库 | MySQL 8.x，字符集 utf8mb4 |
| 构建 | Maven 3.9+ |
| 工具 | IntelliJ IDEA、VS Code、Git |

## 三、目录结构

```text
campus-activity-platform/
├── frontend/                 # Vue 3 前端（成员 A）
├── backend/                  # Spring Boot 后端（成员 B / C）
│   ├── src/main/java/com/campus/
│   │   ├── CampusApplication.java      # 启动类
│   │   ├── common/                     # Result、BusinessException、GlobalExceptionHandler（B）
│   │   ├── config/                     # CorsConfig、WebConfig、SecurityConfig（B）
│   │   ├── user/                       # controller / service / mapper / entity / dto（B）
│   │   ├── activity/                   # controller / service / mapper / entity / dto（C）
│   │   └── security/                   # JwtUtil、JwtAuthenticationFilter、AuthInterceptor（B）
│   ├── src/main/resources/
│   │   ├── application.yml
│   │   └── mapper/                     # MyBatis XML 目录
│   ├── src/test/                       # 基础设施自测（H2 内存库，无需 MySQL）
│   └── pom.xml
├── database/
│   ├── init.sql              # 建库建表 + 默认测试用户 + 演示数据（B 主责，C 协同）
│   ├── seed.sql              # 活动与报名演示数据
│   └── README.md
├── docs/
│   └── api.md                # 接口文档（三人共同维护）
└── README.md
```

## 四、环境准备

1. JDK 17 或更高版本（`java -version`）。
2. Maven 3.9+（`mvn -v`）。
3. MySQL 8.x，已启动服务，并知道 root 账号密码。
4. Node.js 18+（前端开发需要）。

## 五、MySQL 初始化

```bash
# 1. 在仓库根目录执行，创建数据库、三张表、测试用户与演示数据
mysql -u root -p < database/init.sql
```

也可以用 Navicat / DataGrip 打开 `database/init.sql` 执行。

执行完成后：

- 数据库：`campus_activity`
- 表：`users`、`activities`、`user_activity`
- 默认账号（密码以 BCrypt 哈希存储）：

| 用户名 | 密码 | 姓名 |
|---|---|---|
| student01 | 123456 | 张三 |
| student02 | 123456 | 李四 |
| admin01 | admin123 | 管理员 |

## 六、数据库连接配置

配置文件：`backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/campus_activity?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:root}
```

推荐使用环境变量覆盖账号密码，避免把真实密码提交到仓库：

```bash
# Windows PowerShell
$env:DB_USERNAME="root"; $env:DB_PASSWORD="你的密码"
# macOS / Linux
export DB_USERNAME=root DB_PASSWORD=你的密码
```

## 七、后端启动

```bash
cd backend
mvn spring-boot:run
```

- 服务地址：`http://localhost:8080`
- 自测：`mvn test`（使用 H2 内存数据库，不需要本地 MySQL）
- 打包：`mvn clean package`，产物在 `backend/target/backend-1.0-SNAPSHOT.jar`

## 八、前端启动

```bash
cd frontend
npm install
npm run dev
```

- 访问地址：`http://localhost:5173`（Vite 默认端口，已在后端跨域白名单中）
- 后端跨域白名单可通过环境变量 `CORS_ORIGINS` 覆盖（application.yml：`app.cors.allowed-origins`）

## 九、接口文档

详见 [`docs/api.md`](docs/api.md)。

已完成（成员 B）：

| 接口 | 方法 | 需登录 |
|---|---|---|
| `/api/auth/register` | POST | 否 |
| `/api/auth/login` | POST | 否 |
| `/api/users/me` | GET | 是 |
| `/api/users/me` | PUT | 是 |

待实现（成员 C）：`/api/activities` 系列与报名相关接口，约定见 `docs/api.md` 第 3、4 节。

认证方式：登录后携带请求头 `Authorization: Bearer <token>`，后端从 Token 中解析用户身份，不接受前端传入的 userId。

## 十、主要功能

- 用户注册、登录，登录状态由 JWT 维持。
- 个人资料查看与修改，重启后端后数据仍可查询。
- 活动列表、关键词搜索、分类筛选、状态筛选、排序、分页。
- 活动详情，报名 / 取消报名，报名状态与人数同步。
- 我的活动列表（含空状态提示）。
- 首页、详情页、我的活动页报名状态一致。

## 十一、常见问题

| 问题 | 处理方式 |
|---|---|
| 启动报 `Access denied for user` | 检查 `DB_USERNAME` / `DB_PASSWORD` 是否与本机 MySQL 一致 |
| 启动报 `Unknown database 'campus_activity'` | 先执行 `database/init.sql` |
| 前端请求被 CORS 拦截 | 确认前端地址在 `app.cors.allowed-origins` 中，或设置 `CORS_ORIGINS` |
| 需要登录的接口返回 401 | 检查请求头是否带 `Authorization: Bearer <token>`，token 是否过期 |
| 登录报「用户名或密码错误」 | 确认已执行 init.sql，并使用上表中的默认账号密码 |

## 十二、AI 工具辅助开发说明

- 使用 AI 编程助手（Cline）按照《规范文档.md》完成后端骨架初始化：目录结构、`pom.xml` 依赖、统一响应与异常处理、跨域配置、JWT 工具与认证拦截器、users 表实体与 Mapper、注册/登录/资料接口。
- 使用 AI 助手生成 `database/init.sql` 初稿（建表语句来自规范文档 3.6）与测试用户 BCrypt 哈希。
- 使用 AI 助手编写后端自测用例（`CampusApplicationTests`，H2 内存库）并运行验证。
- 所有 AI 生成的代码均由小组成员阅读、审查并实际运行验证后才提交。
