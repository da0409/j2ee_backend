package com.campus.security;

import com.campus.common.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录校验拦截器（规范文档 4.1 / 12.1 第三点）。
 *
 * <p>认证信息由 {@link JwtAuthenticationFilter} 解析并写入 request attribute，
 * 本拦截器只负责判断当前接口是否需要登录：需要登录而没有认证信息时返回 401。</p>
 *
 * <p>注册位置见 {@link com.campus.config.WebConfig#addInterceptors}。</p>
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    /** request attribute 中保存当前登录用户 ID 的 key。 */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /** request attribute 中保存当前登录用户名的 key。 */
    public static final String CURRENT_USERNAME = "CURRENT_USERNAME";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getAttribute(CURRENT_USER_ID) == null) {
            throw new BusinessException(401, "未登录或登录已失效");
        }
        return true;
    }
}

