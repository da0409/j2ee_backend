package com.campus.user.service.impl;

import com.campus.common.BusinessException;
import com.campus.security.JwtUtil;
import com.campus.user.dto.LoginRequest;
import com.campus.user.dto.LoginResponse;
import com.campus.user.dto.RegisterRequest;
import com.campus.user.dto.UpdateProfileRequest;
import com.campus.user.dto.UserVO;
import com.campus.user.entity.User;
import com.campus.user.mapper.UserMapper;
import com.campus.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户业务实现（规范文档 4.2 / 7.2，负责人：成员 B）。
 *
 * <p>实现要点：</p>
 * <ul>
 *   <li>用户名唯一，重复注册返回 400「用户名已存在」</li>
 *   <li>密码使用 BCrypt 哈希后入库，数据库中不保存明文</li>
 *   <li>登录成功后签发 JWT，返回 {token, user}</li>
 *   <li>用户资料接口的所有 userId 均来自 Token</li>
 * </ul>
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserVO register(RegisterRequest request) {
        String username = trimToNull(request.getUsername());
        String password = request.getPassword();

        if (username == null) {
            throw new BusinessException(400, "用户名不能为空");
        }
        if (!StringUtils.hasText(password)) {
            throw new BusinessException(400, "密码不能为空");
        }
        if (userMapper.countByUsername(username) > 0) {
            throw new BusinessException(400, "用户名已存在");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRealName(trimToNull(request.getRealName()));
        user.setStudentId(trimToNull(request.getStudentId()));
        user.setEmail(trimToNull(request.getEmail()));
        user.setAvatar(null);

        try {
            userMapper.insert(user);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            // 并发注册时由 users.username 唯一约束兜底
            throw new BusinessException(400, "用户名已存在");
        }

        return UserVO.from(userMapper.findById(user.getId()));
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String username = trimToNull(request.getUsername());
        String password = request.getPassword();

        if (username == null || !StringUtils.hasText(password)) {
            throw new BusinessException(400, "用户名和密码不能为空");
        }

        User user = userMapper.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(400, "用户名或密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return new LoginResponse(token, UserVO.from(user));
    }

    @Override
    public UserVO getProfile(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException(401, "未登录或登录已失效");
        }
        return UserVO.from(user);
    }

    @Override
    public UserVO updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException(401, "未登录或登录已失效");
        }

        // 只允许修改以下四个字段，用户名与密码不在此接口内修改
        user.setRealName(trimToNull(request.getRealName()));
        user.setStudentId(trimToNull(request.getStudentId()));
        user.setEmail(trimToNull(request.getEmail()));
        user.setAvatar(trimToNull(request.getAvatar()));
        userMapper.updateProfile(user);

        return UserVO.from(userMapper.findById(userId));
    }

    private static String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
