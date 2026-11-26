package com.campus.user.mapper;

import com.campus.user.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * users 表数据访问（规范文档 3.2 / 6.3：Mapper 只负责操作数据库）。
 *
 * <p>建议成员 B 在此补充新 SQL，或迁移到 resources/mapper/UserMapper.xml 中编写。</p>
 */
@Mapper
public interface UserMapper {

    @Select("SELECT id, username, password, real_name, student_id, email, avatar, created_at, updated_at "
            + "FROM users WHERE id = #{id}")
    User findById(@Param("id") Long id);

    @Select("SELECT id, username, password, real_name, student_id, email, avatar, created_at, updated_at "
            + "FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Select("SELECT COUNT(1) FROM users WHERE username = #{username}")
    int countByUsername(@Param("username") String username);

    @Insert("INSERT INTO users (username, password, real_name, student_id, email, avatar) "
            + "VALUES (#{username}, #{password}, #{realName}, #{studentId}, #{email}, #{avatar})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("UPDATE users SET real_name = #{realName}, student_id = #{studentId}, "
            + "email = #{email}, avatar = #{avatar} WHERE id = #{id}")
    int updateProfile(User user);
}
