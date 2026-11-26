package com.campus.user.dto;

/**
 * 注册请求体（规范文档 4.2「用户注册」）。
 *
 * <pre>
 * {
 *   "username": "student01",
 *   "password": "123456",
 *   "realName": "张三",
 *   "studentId": "20260001",
 *   "email": "student@example.com"
 * }
 * </pre>
 *
 * <p>具体校验与业务实现由成员 B 在 user/service 中完成。</p>
 */
public class RegisterRequest {

    private String username;

    private String password;

    private String realName;

    private String studentId;

    private String email;

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
}
