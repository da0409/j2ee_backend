-- 测试用 H2 建表脚本（仅 src/test/resources 下生效，与 database/init.sql 结构保持一致）
-- 每个测试用例都会重新执行，因此先删除表再创建

DROP TABLE IF EXISTS user_activity;
DROP TABLE IF EXISTS activities;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(50),
    student_id VARCHAR(30),
    email VARCHAR(100),
    avatar VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
