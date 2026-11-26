# ==================== 目录说明 ====================
# 本目录用于存放 MyBatis 的 XML 映射文件（规范文档 6.2：src/main/resources/mapper/）
#
# 命名约定：
#   UserMapper.xml      -> com.campus.user.mapper.UserMapper        （成员 B）
#   ActivityMapper.xml  -> com.campus.activity.mapper.ActivityMapper（成员 C）
#
# application.yml 中已配置：
#   mybatis.mapper-locations=classpath:mapper/*.xml
#   mybatis.configuration.map-underscore-to-camel-case=true（数据库 snake_case 自动映射为 camelCase）
#
# 说明：如果 Mapper 使用纯注解方式（@Select/@Insert 等），可以不创建 XML 文件；
#       但一旦在本目录新增 XML，请保证 namespace 与 Mapper 接口全限定名一致。
