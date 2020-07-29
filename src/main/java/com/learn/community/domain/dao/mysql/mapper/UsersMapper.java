package com.learn.community.domain.dao.mysql.mapper;

import com.learn.community.domain.bean.mysql.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {

    @Insert("insert into users(name, loginName, passWord, phone, photo, email, roleId, lastLoginTime, createTime, updateTime)" +
            "values(#{title}, #{name}, #{loginName}, #{passWord}, #{phone}, #{photo}, #{email}, #{roleId}, #{lastLoginTime}, #{createTime}, #{updateTime})")
    int insert(Users users);

    @Delete("update users where id = #{id} and status = 0")
    int deleteByPrimaryKey(int id);

    @Update("update users set name = #{name}, loginName = #{loginName}, passWord = #{passWord}, phone = #{phone}, photo = #{photo}, email = #{email}, roleId = #{roleId}, lastLoginTime = #{lastLoginTime}, status = {#status}, updateTime = #{updateTime} where id = #{id}")
    int updateByPrimaryKey(Users users);

    @Select("select * from users where id = #{id} and status = #{status}")
    Users selectByPrimaryKey(int id);

    @Select("select * from users where status = #{status}")
    List<Users> selectAll();

    @Select("select * from users where loginName = #{loginName} and passWord = #{passWord}")
    Users selectByUserInfo(String loginName, String passWord);

    @Select("select * from users where loginName = #{loginName}")
    Users selectByLoginName(String loginName);
}
