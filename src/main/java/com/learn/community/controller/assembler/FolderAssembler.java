package com.learn.community.controller.assembler;

import com.learn.community.controller.vo.FolderVO;
import com.learn.community.domain.bean.mysql.Folders;

/**
 * @author lwt
 * @Title 文件夹转型类
 * @Description
 * @date 2020/7/17下午12:04
 */
public class FolderAssembler {

    public static FolderVO folderToFolderVO(Folders folders) {
        FolderVO folderVO = new FolderVO();
        folderVO.setId(folders.getId());
        folderVO.setTitle(folders.getTitle());
        return folderVO;
    }
}
