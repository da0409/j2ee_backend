package com.campus.user.dto;

import com.campus.user.entity.User;

/**
 * 返回前端的用户资料（规范文档 4.2 / 3.2：不返回密码等敏感字段）。
 */
public class UserVO {

    private Long id;

    private String username;

    private String realName;

    private String studentId;

    private String email;

    private String avatar;

    public UserVO() {
    }

    public UserVO(Long id, String username, String realName, String studentId, String email, String avatar) {
        this.id = id;
        this.username = username;
        this.realName = realName;
        this.studentId = studentId;
        this.email = email;
        this.avatar = avatar;
    }

    /**
     * 由实体转换，自动丢弃 password / createdAt / updatedAt。
     */
    public static UserVO from(User user) {
        if (user == null) {
            return null;
        }
        return new UserVO(user.getId(), user.getUsername(), user.getRealName(),
                user.getStudentId(), user.getEmail(), user.getAvatar());
    }

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
