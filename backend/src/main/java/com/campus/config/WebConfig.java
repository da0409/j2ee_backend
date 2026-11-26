package com.campus.config;


import com.campus.security.AuthInterceptor;
import com.campus.security.CurrentUserArgumentResolver;
import com.campus.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web 统一配置（规范文档 6.2）。
 *
 * <ul>
 *   <li>注册 JWT 解析过滤器：对 /api/** 全局解析 Authorization（有 token 则填充当前用户）</li>
 *   <li>注册登录校验拦截器：仅对需要登录的接口生效，公开接口通过配置排除</li>
 *   <li>注册当前用户参数解析器：支持 Controller 中使用 {@code @CurrentUserId Long userId}</li>
 * </ul>
 *
 * <p>跨域配置见 {@link CorsConfig}。</p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthInterceptor authInterceptor;

    private final CurrentUserArgumentResolver currentUserArgumentResolver;

    private final String[] publicPaths;

    public WebConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                     AuthInterceptor authInterceptor,
                     CurrentUserArgumentResolver currentUserArgumentResolver,
                     @Value("${app.security.public-paths}") String publicPaths) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authInterceptor = authInterceptor;
        this.currentUserArgumentResolver = currentUserArgumentResolver;
        this.publicPaths = publicPaths.split("\\s*,\\s*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 1. 先解析 token（不拦截），使公开接口也能拿到 joined 等个性化字段
        registry.addInterceptor(jwtAuthenticationFilter)
                .addPathPatterns("/api/**");

        // 2. 再校验必须登录的接口，排除公开接口
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(publicPaths);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(currentUserArgumentResolver);
    }
}

