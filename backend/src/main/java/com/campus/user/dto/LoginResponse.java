package com.campus.user.dto;

/**
 * 登录成功返回体（规范文档 4.2「用户登录」）。
 *
 * <pre>
 * {
 *   "token": "example-token",
 *   "user": { "id": 1, "username": "student01", "realName": "张三", "email": "..." }
 * }
 * </pre>
 */
public class LoginResponse {

    private String token;

    private UserVO user;

    public LoginResponse() {
    }

    public LoginResponse(String token, UserVO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
