package com.zjweu.mapper;


import com.zjweu.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author swithin
 * @since 2023-12-11
 */
@Mapper
public interface UserMapper {

    @Select("select * from tb_user where email = #{email}")
    public User findByEmail(String email);

    @Insert("INSERT INTO tb_user (username, email, avatar_path, phone_number, salt, password_hash) " +
            "VALUES (#{user.username}, #{user.email}, #{user.avatarPath}, #{user.phoneNumber}, #{user.salt}, #{user.passwordHash})")

    public int insertUser(@Param("user") User user);

}
