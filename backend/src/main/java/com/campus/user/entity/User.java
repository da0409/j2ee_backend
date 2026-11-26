package com.campus.user.entity;

import java.time.LocalDateTime;

/**
 * 用户实体，对应 users 表（规范文档 3.2）。
 *
 * <p>字段使用 camelCase，数据库使用 snake_case，由 MyBatis 的
 * {@code map-underscore-to-camel-case} 自动映射。</p>
 */
public class User {

    /** 用户 ID。 */
    private Long id;

    /** 用户名，唯一。 */
    private String username;

    /** 密码哈希（BCrypt），不得返回前端。 */
    private String password;

    /** 真实姓名。 */
    private String realName;

    /** 学号。 */
    private String studentId;

    /** 邮箱。 */
    private String email;

    /** 头像地址。 */
    private String avatar;

    /** 创建时间。 */
    private LocalDateTime createdAt;

    /** 修改时间。 */
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
