package com.learn.community.domain.dao.mysql.mapper;

import com.learn.community.domain.bean.mysql.Roles;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RolesMapper {

    @Insert("insert into roles(name, remark, createTime, updateTime)" +
            "values(#{title}, #{name}, #{url}, #{createTime}, #{updateTime})")
    int insert(Roles roles);

    @Delete("update roles where id = #{id} and status = 0")
    int deleteByPrimaryKey(int id);

    @Update("update roles set name = #{name}, remark = #{remark}, status = {#status}, updateTime = #{updateTime} where id = #{id}")
    int updateByPrimaryKey(Roles roles);

    @Select("select * from roles where id = #{id} and status = #{status}")
    Roles selectByPrimaryKey(int id);

    @Select("select * from roles where status = #{status}")
    List<Roles> selectAll();
}
