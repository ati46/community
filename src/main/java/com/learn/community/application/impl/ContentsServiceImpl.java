package com.learn.community.application.impl;

import com.learn.community.application.ContentsService;
import com.learn.community.domain.bean.mysql.Contents;
import com.learn.community.domain.dao.mysql.mapper.ContentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lwt
 * @Title 文章
 * @Description 文章实现类
 * @date 2020/7/9下午3:04
 */
@Service
public class ContentsServiceImpl implements ContentsService {

    @Autowired
    private ContentsMapper contentsMapper;


    @Override
    public int insert(Contents contents) {
        return contentsMapper.insert(contents);
    }

    @Override
    public int deleteByPrimaryKey(int id) {
        return contentsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Contents contents) {
        return contentsMapper.updateByPrimaryKey(contents);
    }

    @Override
    public Contents selectByArticleId(int id) {
        return contentsMapper.selectByArticleId(id);
    }

    @Override
    public Contents selectByPrimaryKey(int id) {
        return contentsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Contents> selectAll() {
        return contentsMapper.selectAll();
    }

    @Override
    public Contents updateByPrimaryKeyForDetail(Contents contents) {
        if (contents.getId() == 0) {
            contents.setAuthor("admin");
            contents.setAuthorId(1);
            contents.setTag("java");
            contents.setCreateTime(System.currentTimeMillis());
            contents.setUpdateTime(System.currentTimeMillis());
            contentsMapper.insert(contents);
        }else{
            contents.setUpdateTime(System.currentTimeMillis());
            contentsMapper.updateByPrimaryKeyForDetail(contents);
        }
        return contents;
    }
}
