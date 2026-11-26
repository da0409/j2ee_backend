package com.campus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全相关 Bean（规范文档 3.2：密码使用 BCrypt 哈希）。
 *
 * <p>暂不引入 Spring Security 过滤器链，认证由
 * {@link com.campus.security.JwtAuthenticationFilter} 拦截器完成；
 * 如需升级为完整 Spring Security，可在此基础上扩展。</p>
 */
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
