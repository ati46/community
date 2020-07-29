package com.learn.community.domain.bean.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author lwt
 * @Title 内容表
 * @Description 内容表
 * @date 2020/7/23下午1:20
 */
@Getter
@Setter
@Document(collection = "Detail")
public class Detail {
    private Object id;
    private int folderId;
    private int articleId;
    private int contentId;
    //@TextIndexed 拥有这个注解，才会被视为全文检索
    @TextIndexed
    private String folderName;
    @TextIndexed
    private String articleName;
    @TextIndexed
    private String markContent;
    @TextIndexed
    private String htmlContent;
    @TextIndexed
    private String tag;
    private String author;
    private long updateTime;

}
