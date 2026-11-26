package com.campus.user.controller;

import com.campus.common.Result;
import com.campus.security.CurrentUserId;
import com.campus.user.dto.UpdateProfileRequest;
import com.campus.user.dto.UserVO;
import com.campus.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户资料接口（规范文档 4.2，负责人：成员 B）。
 *
 * <p>当前用户身份从 Token 中获取（{@link CurrentUserId}），不接收前端传入的 userId。</p>
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前用户资料。
     *
     * <pre>
     * GET /api/users/me
     * Authorization: Bearer &lt;token&gt;
     * </pre>
     */
    @GetMapping("/me")
    public Result<UserVO> me(@CurrentUserId Long userId) {
        return Result.success(userService.getProfile(userId));
    }

    /**
     * 修改个人资料。
     *
     * <pre>
     * PUT /api/users/me
     * Authorization: Bearer &lt;token&gt;
     * </pre>
     */
    @PutMapping("/me")
    public Result<UserVO> updateMe(@CurrentUserId Long userId,
                                   @Valid @RequestBody UpdateProfileRequest request) {
        return Result.success("修改成功", userService.updateProfile(userId, request));
    }
}
