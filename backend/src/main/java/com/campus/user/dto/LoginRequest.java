package com.campus.user.dto;

/**
 * 登录请求体（规范文档 4.2「用户登录」）。
 *
 * <pre>
 * {
 *   "username": "student01",
 *   "password": "123456"
 * }
 * </pre>
 */
public class LoginRequest {

    private String username;

    private String password;

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
}
