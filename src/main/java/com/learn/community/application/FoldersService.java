package com.learn.community.application;

import com.learn.community.domain.bean.mysql.Folders;

import java.util.List;

public interface FoldersService {

    int insert(Folders folders);

    int deleteByPrimaryKey(int id);

    int updateByPrimaryKey(Folders folders);

    Folders selectByPrimaryKey(int id);

    List<Folders> selectByMenuId(int id);

    List<Folders> selectAll();
}
