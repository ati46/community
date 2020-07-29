package com.learn.community.domain.dao.data;

import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.domain.bean.mongodb.Detail;
import com.learn.community.domain.bean.mysql.Articles;
import com.learn.community.domain.bean.mysql.Contents;
import com.learn.community.domain.bean.mysql.Folders;
import com.learn.community.domain.dao.assembler.MysqlToDataAssembler;
import com.learn.community.domain.dao.es.repo.ESDetailRepo;
import com.learn.community.domain.dao.mongodb.repo.DetailRepo;
import com.learn.community.domain.dao.mysql.mapper.ArticlesMapper;
import com.learn.community.domain.dao.mysql.mapper.FoldersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lwt
 * @Title
 * @Description 查询出mysql中的数据, 转为Mongodb
 * @date 2020/7/23下午2:06
 */
@Component
public class MysqlToData {

    @Autowired
    private ArticlesMapper articlesMapper;

    @Autowired
    private FoldersMapper foldersMapper;

    @Autowired
    private ESDetailRepo esDetailRepo;

    @Autowired
    private DetailRepo detailRepo;

    public void ContentsToDetail(Contents contents) {
        Articles articles = articlesMapper.selectByPrimaryKey(contents.getArticleId());
        Folders folders = foldersMapper.selectByPrimaryKey(articles.getFolderId());
        Detail detail = MysqlToDataAssembler.mysqlContentsToMongodbDetail(folders, articles, contents);
        detailRepo.insertOrUpdate(detail);
        ESDetail esDetail = MysqlToDataAssembler.detailToESDetail(detail);
        esDetailRepo.insert(esDetail);
    }

    public void updateContent(Contents contents) {
        Detail detail = MysqlToDataAssembler.mysqlContentsToMongodbDetail(contents, null);
        detailRepo.updateContent(detail);
    }
}
