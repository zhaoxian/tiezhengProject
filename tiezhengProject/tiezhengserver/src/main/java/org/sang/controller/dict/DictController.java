package org.sang.controller.dict;

import org.sang.bean.*;
import org.sang.service.BidService;
import org.sang.service.CompanyService;
import org.sang.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * zhaoxian 2018/11/30
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    DictService dictService;
    @RequestMapping(value = "/dict",method = RequestMethod.GET)
    public Map<String,Object> getDicts(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> dictInfo = new HashMap<>();
        dictInfo.put("dicts",dictService.getDictsByPage(page,size));
        dictInfo.put("count",50);
        return dictInfo;
    }
    @RequestMapping(value = "/dict", method = RequestMethod.POST)
    public RespBean addDict(Dict dict) {
        if (dictService.addDict(dict) == 1) {
            System.out.println("菜单添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/dict", method = RequestMethod.PUT)
    public RespBean updateDict(Dict dict,BindingResult bindingResult) {
        if (dictService.updateDict(dict) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/dict/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteDictById(@PathVariable String ids) {
        if (dictService.deleteDictById(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    @RequestMapping(value = "/getParentDicts",method = RequestMethod.GET)
    public Map<String,Object> getParentDicts(){
        Map<String,Object> dictInfo = new HashMap<>();
        dictInfo.put("dicts",dictService.getParentDicts());
        return dictInfo;
    }
}
