package com.campus.security;

import com.campus.common.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 当前登录用户参数解析器（规范文档 12.1 第三点：身份只从 Token 中获取）。
 *
 * <p>Controller 方法上直接写 {@code @CurrentUserId Long userId} 即可拿到当前用户 ID，
 * 不需要、也不允许前端通过参数传入 userId。</p>
 */
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUserId.class)
                && Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        Object userId = request == null ? null : request.getAttribute(AuthInterceptor.CURRENT_USER_ID);
        if (userId == null) {
            throw new BusinessException(401, "未登录或登录已失效");
        }
        return userId;
    }
}
