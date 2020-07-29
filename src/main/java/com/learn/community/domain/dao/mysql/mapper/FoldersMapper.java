package com.learn.community.domain.dao.mysql.mapper;

import com.learn.community.domain.bean.mysql.Folders;
import com.learn.community.infrastructure.annotation.SqlCopy;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FoldersMapper {

    @Insert("insert into folders(title, menuId, createTime, updateTime) values(#{title}, #{menuId}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys=true,keyProperty = "id", keyColumn = "id")
    int insert(Folders folders);

    @Delete("update folders set status = 1 where id = #{id}")
    int deleteByPrimaryKey(int id);

    @Update("update folders set title = #{title}, menuId = #{menuId}, updateTime = #{updateTime} where id = #{id}")
    int updateByPrimaryKey(Folders folders);

    @Select("select * from folders where id = #{id} and status = 1")
    Folders selectByPrimaryKey(int id);

    @Select("select * from folders where status = 1")
    List<Folders> selectAll();

    @Select("select * from folders where menuId = #{id} and status = 1")
    List<Folders> selectByMenuId(int id);
}
