package org.sang.controller.section;

import org.sang.bean.*;
import org.sang.service.SectionService;
import org.sang.service.TunnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * zhaoxian 2018/11/30
 */
@RestController
@RequestMapping("/section")
public class SectionController {
    @Autowired
    SectionService sectionService;
    @RequestMapping(value = "/section",method = RequestMethod.GET)
    public Map<String,Object> getSections(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> sectionInfo = new HashMap<>();
        sectionInfo.put("sections",sectionService.getSectionByPage(page,size));
        sectionInfo.put("count",50);
        return sectionInfo;
    }
    @RequestMapping(value = "/section", method = RequestMethod.POST)
    public RespBean addSection(Section section) {
        if (sectionService.addSection(section) == 1) {
            System.out.println("菜单添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/section", method = RequestMethod.PUT)
    public RespBean updateSection(Section section,BindingResult bindingResult) {
        if (sectionService.updateSection(section) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/section/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteSectionById(@PathVariable String ids) {
        if (sectionService.deleteSectionById(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
//    @RequestMapping(value = "/getParentMenu",method = RequestMethod.GET)
//    public Map<String,Object> getParentMenu(){
//        Map<String,Object> menuInfo = new HashMap<>();
//        menuInfo.put("pMenus",menuService.getParentMenu());
//        return menuInfo;
//    }
}
