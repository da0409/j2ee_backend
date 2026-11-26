package com.campus.security;


import com.campus.common.BusinessException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 认证拦截器（规范文档 4.1：Authorization: Bearer &lt;token&gt;）。
 *
 * <p>在 {@link com.campus.config.WebConfig} 中对 "/api/**" 全局注册，
 * {@link AuthInterceptor} 负责判断当前接口是否需要登录。</p>
 *
 * <p>未携带 Authorization 时直接放行（公开接口不需要登录，joined 字段按未登录处理）；
 * 携带了但校验失败则返回 401，避免前端拿着过期 token 仍被当作已登录。</p>
 */
@Component
public class JwtAuthenticationFilter implements HandlerInterceptor {

    /** request attribute 中保存当前登录用户 ID 的 key。 */
    public static final String CURRENT_USER_ID = AuthInterceptor.CURRENT_USER_ID;

    /** request attribute 中保存当前登录用户名的 key。 */
    public static final String CURRENT_USERNAME = AuthInterceptor.CURRENT_USERNAME;

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            // 未携带 token：不在这里拦截，交由 AuthInterceptor 判断接口是否需要登录
            return true;
        }

        String token = authorization.substring(BEARER_PREFIX.length()).trim();
        try {
            Long userId = jwtUtil.getUserId(token);
            if (userId == null) {
                throw new BusinessException(401, "未登录或登录已失效");
            }
            request.setAttribute(CURRENT_USER_ID, userId);
            request.setAttribute(CURRENT_USERNAME, jwtUtil.getUsername(token));
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(401, "未登录或登录已失效");
        }
    }
}

