package com.zjweu.mapper;


import com.zjweu.entity.TbUser;
import org.apache.ibatis.annotations.Mapper;
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
    public TbUser findByEmail(String email);

}
