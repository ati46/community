package com.learn.community.controller.assembler;

import com.learn.community.controller.param.ContentParam;
import com.learn.community.controller.vo.ContentVO;
import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.domain.bean.mysql.Contents;

/**
 * @author lwt
 * @Title 文章转型类
 * @Description BeanToVO
 * @date 2020/7/16下午3:49
 */
public class ContentAssembler {

    public static ContentVO ContentToContentVO(Contents contents) {
        ContentVO contentVO = new ContentVO();
        contentVO.setId(contents.getId());
        contentVO.setArticleId(contents.getArticleId());
        contentVO.setHtmlContent(contents.getHtmlContent());
        contentVO.setMarkContent(contents.getMarkContent());
        contentVO.setTag(contents.getTag());
        return contentVO;
    }

    public static ContentVO ESDetailToContentVO(ESDetail esDetail) {
        ContentVO contentVO = new ContentVO();
        contentVO.setId(esDetail.getContentId());
        contentVO.setArticleId(esDetail.getArticleId());
        contentVO.setHtmlContent(esDetail.getHtmlContent());
        contentVO.setMarkContent(esDetail.getMarkContent());
        contentVO.setTag(esDetail.getTag());
        return contentVO;
    }

    public static Contents ContentParamToContents(ContentParam contentParam) {
        Contents contents = new Contents();
        contents.setId(contentParam.getId());
        contents.setArticleId(contentParam.getArticleId());
        contents.setTag(contentParam.getTag());
        contents.setHtmlContent(contentParam.getHtmlContent());
        contents.setMarkContent(contentParam.getMarkContent());
        return contents;
    }
}
