package org.sang.controller.land;

import org.sang.bean.Land;
import org.sang.bean.RespBean;
import org.sang.service.LandService;
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
@RequestMapping("/vault")
public class VaultController {
    @Autowired
    LandService landService;
    @RequestMapping(value = "/land",method = RequestMethod.GET)
    public Map<String,Object> getLandByPage(@RequestParam(defaultValue = "1") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> landInfo = new HashMap<>();
        landInfo.put("lands",landService.getLandsByPage(page,size));
        landInfo.put("count",50);
        return landInfo;
    }
    @RequestMapping(value = "/land", method = RequestMethod.POST)
    public RespBean addLand(Land land) {
        if (landService.addLand(land) == 1) {
            System.out.println("菜单添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/land", method = RequestMethod.PUT)
    public RespBean updateLand(Land land,BindingResult bindingResult) {
        if (landService.updateLand(land) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/land/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteLandById(@PathVariable String ids) {
        if (landService.deleteLandById(ids)) {
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
