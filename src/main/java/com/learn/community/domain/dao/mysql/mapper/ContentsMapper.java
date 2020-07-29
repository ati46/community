package com.learn.community.domain.dao.mysql.mapper;

import com.learn.community.domain.bean.mysql.Contents;
import com.learn.community.infrastructure.annotation.SqlCopy;
import com.learn.community.infrastructure.annotation.ModifyCopy;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ContentsMapper {

    @ModifyCopy
    @Insert("insert into contents(articleId, tag, htmlContent, markContent, author, authorId, createTime, updateTime)" +
            "values(#{articleId}, #{tag}, #{htmlContent}, #{markContent}, #{author}, #{authorId}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys=true,keyProperty = "id", keyColumn = "id")
    int insert(Contents contents);

    @ModifyCopy
    @Delete("update contents set status = 1 where id = #{id}")
    int deleteByPrimaryKey(int id);

    @ModifyCopy
    @Update("update contents set tag = #{tag}, htmlContent = #{htmlContent},  markContent = #{markContent}, author = #{author}, authorId = #{authorId} ,updateTime = #{updateTime} where id = #{id}")
    int updateByPrimaryKey(Contents contents);

    @Select("select * from contents where id = #{id} and status = 1")
    Contents selectByPrimaryKey(int id);

    @Select("select * from contents where articleId = #{id} and status = 1")
    Contents selectByArticleId(int id);

    @Select("select * from contents and status = 1")
    List<Contents> selectAll();

    @ModifyCopy
    @Update("update contents set htmlContent = #{htmlContent}, markContent = #{markContent}, updateTime = #{updateTime} where id = #{id} and articleId = #{articleId}")
    int updateByPrimaryKeyForDetail(Contents contents);
}
