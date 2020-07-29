package com.learn.community.domain.dao.mysql.mapper;

import com.learn.community.domain.bean.mysql.Articles;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticlesMapper {

    @Insert("insert into articles(title, subTitle, folderId, watchNum, commentNum, createTime, updateTime)" +
            "values(#{title}, #{subTitle}, #{folderId}, #{watchNum}, #{commentNum}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys=true,keyProperty = "id", keyColumn = "id")
    int insert(Articles articles);

    @Delete("update articles set status = 1 where id = #{id}")
    int deleteByPrimaryKey(int id);

    @Update("update articles set title = #{title}, subTitle = #{subTitle}," +
            "folderId = #{folderId}, watchNum=#{watchNum}, commentNum=#{commentNum}, updateTime = #{updateTime} where id = #{id}")
    int updateByPrimaryKey(Articles articles);

    @Select("select * from articles where id = #{id} and status = 1")
    Articles selectByPrimaryKey(int id);

    @Select("select * from articles where folderId = #{id} and status = 1")
    List<Articles> selectByFolderId(int id);

    @Select("select * from articles and status = 1")
    List<Articles> selectAll();
}
