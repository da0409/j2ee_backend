-- =====================================================================
-- 校园活动发现与个人计划平台 - 数据库初始化脚本
-- 对应《规范文档.md》3.6 建议的 SQL 建表语句
-- 负责人：成员 B（主责），成员 C 协同；结构变更需三人确认
--
-- 使用方法：
--   1. MySQL 8.x 命令行：mysql -u root -p < database/init.sql
--   2. 或使用 Navicat / DataGrip 打开本文件并执行
-- 注意：
--   本脚本会重建三张业务表，重复执行会清空原有数据
-- =====================================================================

CREATE DATABASE IF NOT EXISTS campus_activity
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE campus_activity;

-- 按外键依赖顺序删除旧表
DROP TABLE IF EXISTS user_activity;
DROP TABLE IF EXISTS activities;
DROP TABLE IF EXISTS users;

-- ---------------------------------------------------------------------
-- 1. users 用户表（规范文档 3.2）
-- ---------------------------------------------------------------------
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户 ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT 'BCrypt 密码哈希',
    real_name VARCHAR(50) NULL COMMENT '真实姓名',
    student_id VARCHAR(30) NULL COMMENT '学号',
    email VARCHAR(100) NULL COMMENT '邮箱',
    avatar VARCHAR(500) NULL COMMENT '头像地址',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表';

-- ---------------------------------------------------------------------
-- 2. activities 活动表（规范文档 3.3）
-- ---------------------------------------------------------------------
CREATE TABLE activities (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '活动 ID',
    title VARCHAR(150) NOT NULL COMMENT '活动名称',
    category VARCHAR(50) NOT NULL COMMENT '活动类别',
    image VARCHAR(500) NULL COMMENT '图片地址',
    date DATETIME NOT NULL COMMENT '活动时间',
    location VARCHAR(150) NOT NULL COMMENT '活动地点',
    description TEXT NULL COMMENT '活动介绍',
    organizer VARCHAR(100) NULL COMMENT '主办方',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    INDEX idx_activity_date (date),
    INDEX idx_activity_category (category)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '校园活动表';

-- ---------------------------------------------------------------------
-- 3. user_activity 用户活动报名关系表（规范文档 3.4）
-- ---------------------------------------------------------------------
CREATE TABLE user_activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关系 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    activity_id BIGINT NOT NULL COMMENT '活动 ID',
    joined_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',

    UNIQUE KEY uk_user_activity (user_id, activity_id),
    INDEX idx_activity_user (activity_id, user_id),

    CONSTRAINT fk_ua_user
        FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_ua_activity
        FOREIGN KEY (activity_id)
        REFERENCES activities (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户活动报名关系表';

-- =====================================================================
-- 4. 默认测试用户（规范文档 11.2：密码必须是安全哈希，不得保存明文）
--    密码明文：123456（student01）/ admin123（admin01）
--    哈希由后端 BCryptPasswordEncoder 生成，可直接用于登录接口
-- =====================================================================
INSERT INTO users (username, password, real_name, student_id, email, avatar) VALUES
('student01', '$2a$10$2FwP6ipL92Ih9o8p/yhyEOsjRfFgdNexpY.dwcMTOwXLGk.oCMg1G', '张三', '20260001', 'student01@campus.edu.cn', NULL),
('student02', '$2a$10$2FwP6ipL92Ih9o8p/yhyEOsjRfFgdNexpY.dwcMTOwXLGk.oCMg1G', '李四', '20260002', 'student02@campus.edu.cn', NULL),
('admin01',   '$2a$10$kF7SrMB4Xcc3O3q5OMMGoOUzZ/4NAfhoz3/yEHZ29DN8/FLBuCMKO', '管理员', '20260000', 'admin01@campus.edu.cn', NULL);

