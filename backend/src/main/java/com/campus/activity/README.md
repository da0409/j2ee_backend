# 活动模块（成员 C）

本目录对应《规范文档.md》4.3 / 4.4 中的接口，负责人为后端成员 C。

| 接口 | 方法 | Controller 建议位置 |
|---|---|---|
| `/api/activities` | GET | `activity/controller/ActivityController#list` |
| `/api/activities/{id}` | GET | `activity/controller/ActivityController#detail` |
| `/api/activities/categories` | GET | `activity/controller/ActivityController#categories` |
| `/api/activities/{id}/participants/count` | GET | `activity/controller/ActivityController#participantCount` |
| `/api/activities/{id}/join` | POST | `activity/controller/ParticipationController#join` |
| `/api/activities/{id}/join` | DELETE | `activity/controller/ParticipationController#cancel` |
| `/api/users/me/activities` | GET | `activity/controller/ParticipationController#myActivities` |
| `/api/activities/{id}/participation` | GET | `activity/controller/ParticipationController#participation` |

公共能力（由成员 B 提供，可直接使用）：

- 统一响应：`com.campus.common.Result`
- 业务异常：`com.campus.common.BusinessException`（已由 `GlobalExceptionHandler` 统一处理）
- 当前登录用户：Controller 参数上标注 `@CurrentUserId Long userId`
- 无需登录的接口已在 `application.yml -> app.security.public-paths` 中配置
