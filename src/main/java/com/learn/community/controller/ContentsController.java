package com.learn.community.controller;

import com.learn.community.application.ContentsService;
import com.learn.community.application.ESDetailService;
import com.learn.community.application.UsersService;
import com.learn.community.controller.assembler.ContentAssembler;
import com.learn.community.controller.param.ContentParam;
import com.learn.community.controller.vo.ContentVO;
import com.learn.community.domain.bean.es.ESDetail;
import com.learn.community.domain.bean.mysql.Contents;
import com.learn.community.infrastructure.annotation.AccessLimit;
import com.learn.community.infrastructure.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lwt
 * @Title 文章
 * @Description 提供文章的接口
 * @date 2020/7/9下午3:11
 */
@BaseResponse
@RestController
@RequestMapping("/content")
public class ContentsController {
    @Autowired
    private ContentsService contentsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ESDetailService esDetailService;

    @PostMapping("")
    public String addContents(@RequestBody Contents contents) {
        int result = contentsService.insert(contents);
        return result + "";
    }

    @PutMapping("")
    public String modifyContents(@RequestBody Contents contents) {
        int result = contentsService.updateByPrimaryKey(contents);
        return result + "";
    }

    @AccessLimit(limit = 1, time = 1000)
    @PutMapping("/detail")
    public ContentVO modifydetail(@RequestBody ContentParam contentParam) {
        Contents contents = contentsService.updateByPrimaryKeyForDetail(ContentAssembler.ContentParamToContents(contentParam));
        return ContentAssembler.ContentToContentVO(contents);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ContentVO find(@PathVariable int id) {
        //使用es的高亮搜索，所以需要把查询从sql改成es
        Contents contents = contentsService.selectByArticleId(id);
        ESDetail esDetail = esDetailService.selectByArticleId(id);

        ContentVO contentVO = new ContentVO();
        if (null != esDetail) {
            contentVO = ContentAssembler.ESDetailToContentVO(esDetail);
        }
        contentVO.setStatus(usersService.isLogin() ? 1 : 0);

        return contentVO;
    }
}
