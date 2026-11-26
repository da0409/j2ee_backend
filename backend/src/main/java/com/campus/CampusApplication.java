package com.campus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园活动发现与个人计划平台 - 后端启动类。
 *
 * <p>规范文档 6.2：启动类位于 com.campus 根包，各模块按 user / activity 分包。</p>
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.campus.user.mapper", "com.campus.activity.mapper"})
public class CampusApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusApplication.class, args);
    }
}
