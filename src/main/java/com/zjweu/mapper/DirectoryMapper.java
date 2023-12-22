package com.zjweu.mapper;

import com.zjweu.dto.DirectoryTO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

/**
 * author：Swithin
 * date：2023/12/22 10:48
 **/
@Mapper
public interface DirectoryMapper {

    @Insert("INSERT INTO tb_directory (directory_name,user_id,father_root) " +
            "VALUES (#{query.name},#{query.userId},#{query.did})")
    int addDirectory(@Param("query") DirectoryTO query);

    @Insert("INSERT INTO tb_directory (user_id) " +
            "VALUES (#{userId})")
    int addRootDirectory(Integer userId);

    @Select("select id from tb_directory where user_id = #{userId} and father_root is null")
    int findRootDirectory(int userId);

    @Select("select id from tb_directory where user_id = #{userId} and directory_name = #{name}")
    Integer findRootDirectoryByName(@Param("userId") int userId,@Param("name") String name);

    @Update("UPDATE tb_directory SET directory_name = #{query.name},modify_time = #{modifyTime} WHERE id = #{query.did} and uid = #{query.userId}")
    Integer renameDirectory(@Param("query") DirectoryTO query, @Param("modifyTime")LocalDateTime modifyTime);

    @Update("UPDATE tb_directory SET is_deleted = 1,modify_time = #{modifyTime} WHERE id = #{query.did} and user_id = #{query.userId}")
    Integer deleteDirectory(@Param("query") DirectoryTO query, @Param("modifyTime")LocalDateTime modifyTime);

    @Update("UPDATE tb_directory SET is_deleted = 0,modify_time = #{modifyTime} WHERE id = #{query.did} and user_id = #{query.userId}")
    Integer recoverDirectory(@Param("query") DirectoryTO query, @Param("modifyTime")LocalDateTime modifyTime);
}
