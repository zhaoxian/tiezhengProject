package org.sang.controller.tunnel;

import org.sang.bean.*;
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
@RequestMapping("/tunnel")
public class TunnelController {
    @Autowired
    TunnelService tunnelService;
    @RequestMapping(value = "/getTunnelsByPage",method = RequestMethod.GET)
    public Map<String,Object> getTunnels(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> bidInfo = new HashMap<>();
        bidInfo.put("tunnels",tunnelService.getTunnelByPage(page,size));
        bidInfo.put("count",50);
        return bidInfo;
    }
    @RequestMapping(value = "/addTunnel", method = RequestMethod.POST)
    public RespBean addTunnel(Tunnel tunnel) {
        if (tunnelService.addTunnel(tunnel) == 1) {
            System.out.println("菜单添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/updateTunnel", method = RequestMethod.PUT)
    public RespBean updateTunnel(Tunnel tunnel,BindingResult bindingResult) {
        if (tunnelService.updateTunnel(tunnel) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/deleteTunnel/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteTunnelById(@PathVariable String ids) {
        if (tunnelService.deleteTunnelById(ids)) {
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
