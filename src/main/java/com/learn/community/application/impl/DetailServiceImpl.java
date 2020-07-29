package com.learn.community.application.impl;

import com.learn.community.application.DetailService;
import com.learn.community.domain.bean.mongodb.Detail;
import com.learn.community.domain.dao.mongodb.repo.DetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lwt
 * @Title 文章信息表
 * @Description
 * @date 2020/7/23下午4:11
 */
@Service
public class DetailServiceImpl implements DetailService {

    @Autowired
    private DetailRepo detailRepo;

    @Override
    public Detail selectByContentId(int id) {
        return detailRepo.selectByContentId(id);
    }

    @Override
    public List<Detail> selectByFolderId(int id) {
        return detailRepo.selectByFolderId(id);
    }

    @Override
    public List<Detail> selectAll() {
        return detailRepo.selectAll();
    }

    @Override
    public List<Detail> selectAllByPage(int pageStart, int pageSize) {
        return detailRepo.selectAllByPage(pageStart, pageSize);
    }

    @Override
    public List<Detail> selectTop() {
        return detailRepo.selectTop();
    }

    @Override
    public List<Detail> search(String searchContent, int pageStart, int pageSize) {
        return detailRepo.search(searchContent, pageStart, pageSize);
    }
}
