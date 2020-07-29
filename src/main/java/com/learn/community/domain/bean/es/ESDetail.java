package com.learn.community.domain.bean.es;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author lwt
 * @Title 内容表
 * @Description 内容表
 * @date 2020/7/23下午1:20
 */
@Getter
@Setter
public class ESDetail implements Serializable {
    private static final long serialVersionUID = -3868856015035139579L;

    private Object id;
    private int folderId;
    private int articleId;
    private int contentId;
    private String folderName;
    private String articleName;
    private String markContent;
    private String htmlContent;
    private String tag;
    private String author;
    private long updateTime;

}
