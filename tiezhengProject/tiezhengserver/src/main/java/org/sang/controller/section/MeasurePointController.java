package org.sang.controller.section;

import org.sang.bean.MeasurePoint;
import org.sang.bean.RespBean;
import org.sang.bean.Section;
import org.sang.service.MeasurePointService;
import org.sang.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 测点控制层
 * zhaoxian 2018/11/30
 */
@RestController
@RequestMapping("/meaPoint")
public class MeasurePointController {
    @Autowired
    MeasurePointService measurePointService;
    @RequestMapping(value = "/point",method = RequestMethod.GET)
    public Map<String,Object> getMeasurePointByPage(@RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> pointInfo = new HashMap<>();
        pointInfo.put("points",measurePointService.getMeasurePointByPage(page,size));
        pointInfo.put("count",50);
        return pointInfo;
    }
    @RequestMapping(value = "/point", method = RequestMethod.POST)
    public RespBean addMeasurePoint(MeasurePoint measurePoint) {
        if (measurePointService.addMeasurePoint(measurePoint) == 1) {
            System.out.println("菜单添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/point", method = RequestMethod.PUT)
    public RespBean updateMeasurePoint(MeasurePoint measurePoint,BindingResult bindingResult) {
        if (measurePointService.updateMeasurePoint(measurePoint) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/point/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteMeasurePointById(@PathVariable String ids) {
        if (measurePointService.deleteMeasurePointById(ids)) {
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
