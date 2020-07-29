package com.learn.community.application.impl;

import com.learn.community.application.FoldersService;
import com.learn.community.domain.bean.mysql.Folders;
import com.learn.community.domain.dao.mysql.mapper.FoldersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoldersServiceImpl implements FoldersService {
    @Autowired
    FoldersMapper foldersMapper;
    @Override
    public int insert(Folders folders) {
        folders.setCreateTime(System.currentTimeMillis());
        folders.setUpdateTime(System.currentTimeMillis());
        return foldersMapper.insert(folders);
    }

    @Override
    public int deleteByPrimaryKey(int id) {
        return foldersMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(Folders folders) {
        return foldersMapper.updateByPrimaryKey(folders);
    }

    @Override
    public Folders selectByPrimaryKey(int id) {
        return foldersMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Folders> selectByMenuId(int id) {
        return foldersMapper.selectByMenuId(id);
    }

    @Override
    public List<Folders> selectAll() {
        return foldersMapper.selectAll();
    }
}
