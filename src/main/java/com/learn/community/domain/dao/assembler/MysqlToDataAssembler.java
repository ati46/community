package com.learn.community.domain.dao.assembler;

import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.domain.bean.mongodb.Detail;
import com.learn.community.domain.bean.mysql.Articles;
import com.learn.community.domain.bean.mysql.Contents;
import com.learn.community.domain.bean.mysql.Folders;

/**
 * @author lwt
 * @Title Mysql数据转为其他类型
 * @Description 把Mysql的转据转型成其他的数据
 * @date 2020/7/23下午1:54
 */
public class MysqlToDataAssembler {

    public static Detail mysqlContentsToMongodbDetail(Folders folders, Articles articles, Contents contents) {
        Detail detail = new Detail();
        detail.setFolderId(folders.getId());
        detail.setFolderName(folders.getTitle());
        detail.setArticleId(articles.getId());
        detail.setArticleName(articles.getTitle());
        mysqlContentsToMongodbDetail(contents, detail);
        return detail;
    }

    public static Detail mysqlContentsToMongodbDetail(Contents contents, Detail detail) {
        if (null == detail) {
            detail = new Detail();
        }
        detail.setContentId(contents.getId());
        detail.setHtmlContent(contents.getHtmlContent());
        detail.setMarkContent(contents.getMarkContent());
        detail.setAuthor(contents.getAuthor());
        detail.setTag(contents.getTag());
        return detail;
    }

    public static ESDetail detailToESDetail(Detail detail) {
        ESDetail esDetail = new ESDetail();
        esDetail.setArticleId(detail.getArticleId());
        esDetail.setArticleName(detail.getArticleName());
        esDetail.setAuthor(detail.getAuthor());
        esDetail.setContentId(detail.getContentId());
        esDetail.setHtmlContent(detail.getHtmlContent());
        esDetail.setMarkContent(detail.getMarkContent());
        esDetail.setFolderId(detail.getFolderId());
        esDetail.setFolderName(detail.getFolderName());
        esDetail.setTag(detail.getTag());
        esDetail.setUpdateTime(detail.getUpdateTime());
        return esDetail;
    }
}
