package com.zjweu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * author：Swithin
 * date：2023/12/22 10:22
 **/
@Mapper
public interface FileMapper {

    @Select("select id from tb_directory where uid = #{uid} and father_root is null")
    int findRootPid(int uid);
}
