# database 目录（负责人：成员 B 主责，成员 C 协同）

| 文件 | 说明 |
|---|---|
| `init.sql` | 创建数据库 `campus_activity`、三张业务表、默认测试用户，并包含活动与报名预置数据 |
| `seed.sql` | 仅包含活动与报名关系的预置数据，表结构已存在、只想重置演示数据时单独执行 |

## 执行方式

```bash
# 一次性初始化（推荐）
mysql -u root -p < database/init.sql

# 只重置演示数据
mysql -u root -p campus_activity < database/seed.sql
```

也可以用 Navicat / DataGrip 打开脚本直接执行。

## 默认测试账号

密码以 BCrypt 哈希形式存储，不会出现明文。

| 用户名 | 密码 | 姓名 | 学号 |
|---|---|---|---|
| student01 | 123456 | 张三 | 20260001 |
| student02 | 123456 | 李四 | 20260002 |
| admin01 | admin123 | 管理员 | 20260000 |

## 表结构

- `users`：用户表，`username` 唯一，密码为 BCrypt 哈希。
- `activities`：活动表，`date` / `category` 建有索引，活动状态由后端按时间计算。
- `user_activity`：报名关系表，`(user_id, activity_id)` 联合唯一，外键级联删除。

数据库结构变更流程见《规范文档.md》8.5：先由成员 C 提出字段需求，成员 B 统一整合本脚本，再通知全员并同步实体类与 API 文档。

