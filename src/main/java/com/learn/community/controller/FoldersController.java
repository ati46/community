package com.learn.community.controller;

import com.learn.community.application.FoldersService;
import com.learn.community.controller.assembler.FolderAssembler;
import com.learn.community.controller.vo.FolderVO;
import com.learn.community.domain.bean.mysql.Folders;
import com.learn.community.infrastructure.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@BaseResponse
@RestController
@RequestMapping("/folder")
public class FoldersController {

    @Autowired
    private FoldersService foldersService;
    @PostMapping("")
    public FolderVO addFolder(@RequestBody Folders folders) {
        int result = foldersService.insert(folders);
        FolderVO folderVO = FolderAssembler.folderToFolderVO(folders);
        return folderVO;
    }

    @PutMapping("")
    public int modifyFolder(@RequestBody Folders folders) {
        int result = foldersService.updateByPrimaryKey(folders);
        return result;
    }

    @GetMapping("")
    public Map<String, Object> findAll() {
        List<Folders> list = foldersService.selectAll();
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("count", list.size());
        return map;
    }

    @GetMapping("/{id}")
    public List<Folders> find(@PathVariable int id) {
        List<Folders> folders = foldersService.selectByMenuId(id);
        return folders;
    }
}
