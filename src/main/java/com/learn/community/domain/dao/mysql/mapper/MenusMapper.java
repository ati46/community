package com.learn.community.domain.dao.mysql.mapper;

import com.learn.community.domain.bean.mysql.Menus;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenusMapper {

    @Insert("insert into menus(name, url, userId, visible, sort, createTime, updateTime)" +
            "values(#{title}, #{name}, #{url}, #{userId}, #{visible}, #{sort}, #{createTime}, #{updateTime})")
    int insert(Menus menus);

    @Delete("update menus set status = 0 where id = #{id}")
    int deleteByPrimaryKey(int id);

    @Update("update menus set name = #{name}, url = #{url}, userId=#{userId}, visible=#{visible}, status=#{status}, sort=#{sort}, updateTime = #{updateTime} where id = #{id}")
    int updateByPrimaryKey(Menus menus);

    @Select("select * from menus where id = #{id} and status = 1 order by sort")
    Menus selectByPrimaryKey(int id);

    //@Select("select * from menus where status = 1 and visible = #{visible} order by sort")
    @Select("<script>"
            + "SELECT "
            + "*"
            + "FROM menus "
            + "WHERE status = 1 "
            + "<if test='visible != 0'>"
            + "and visible = 1"
            + "</if>"
            + " order by sort"
            + "</script>")
    List<Menus> selectAll(int visible);
}
