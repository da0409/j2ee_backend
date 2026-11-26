package com.campus.user.service;

import com.campus.user.dto.LoginRequest;
import com.campus.user.dto.LoginResponse;
import com.campus.user.dto.RegisterRequest;
import com.campus.user.dto.UpdateProfileRequest;
import com.campus.user.dto.UserVO;

/**
 * 用户业务接口（规范文档 4.2，负责人：成员 B）。
 *
 * <p>与接口文档一一对应：</p>
 * <ul>
 *   <li>POST /api/auth/register  → {@link #register}</li>
 *   <li>POST /api/auth/login     → {@link #login}</li>
 *   <li>GET  /api/users/me       → {@link #getProfile}</li>
 *   <li>PUT  /api/users/me       → {@link #updateProfile}</li>
 * </ul>
 *
 * <p>实现类位于 user/service/impl，由成员 B 完成。</p>
 */
public interface UserService {

    /**
     * 用户注册：检查用户名重复、BCrypt 哈希密码后写入 users 表。
     *
     * @return 仅包含 id 与 username 的响应数据
     */
    UserVO register(RegisterRequest request);

    /**
     * 用户登录：校验用户名与密码，成功后签发 JWT。
     */
    LoginResponse login(LoginRequest request);

    /**
     * 获取当前登录用户资料。
     *
     * @param userId 来自 Token 的当前用户 ID
     */
    UserVO getProfile(Long userId);

    /**
     * 修改当前登录用户资料（realName / studentId / email / avatar）。
     */
    UserVO updateProfile(Long userId, UpdateProfileRequest request);
}
