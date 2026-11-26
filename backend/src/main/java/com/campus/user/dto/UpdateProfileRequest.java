package com.campus.user.dto;

/**
 * 修改个人资料请求体（规范文档 4.2「修改个人资料」）。
 *
 * <pre>
 * {
 *   "realName": "张三",
 *   "studentId": "20260001",
 *   "email": "new@example.com",
 *   "avatar": null
 * }
 * </pre>
 */
public class UpdateProfileRequest {

    private String realName;

    private String studentId;

    private String email;

    private String avatar;

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
}
