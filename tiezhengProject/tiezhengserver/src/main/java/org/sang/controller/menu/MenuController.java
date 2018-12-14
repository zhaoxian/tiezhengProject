package org.sang.controller.menu;

import org.sang.bean.Employee;
import org.sang.bean.Menu;
import org.sang.bean.Position;
import org.sang.bean.RespBean;
import org.sang.common.EmailRunnable;
import org.sang.service.EmpService;
import org.sang.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * zhaoxian 2018/11/30
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;
    @RequestMapping(value = "/getAllMenu",method = RequestMethod.GET)
    public Map<String,Object> getAllMenu(){
        Map<String,Object> menuInfo = new HashMap<>();
        menuInfo.put("menus",menuService.getAllMenu());
        menuInfo.put("count",menuService.getAllMenu().size());
        return menuInfo;
    }
    @RequestMapping(value = "/getMenus",method = RequestMethod.GET)
    public Map<String,Object> getMenus(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> menuInfo = new HashMap<>();
        menuInfo.put("menus",menuService.getMenusByParam(page,size));
        menuInfo.put("count",menuService.getAllMenu().size());
        return menuInfo;
    }
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    public RespBean addEmp(Menu menu) {
        if (menuService.addMenu(menu) == 1) {
//            List<Position> allPos = positionService.getAllPos();
//            for (Position allPo : allPos) {
//                if (allPo.getId() == employee.getPosId()) {
//                    employee.setPosName(allPo.getName());
//                }
//            }
//            executorService.execute(new EmailRunnable(employee,
//                    javaMailSender, templateEngine));
            System.out.println("菜单添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/updateMenu", method = RequestMethod.PUT)
    public RespBean updateMenu(Menu menu,BindingResult bindingResult) {
        if (menuService.updateMenu(menu) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/deleteMenu/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteMenuById(@PathVariable String ids) {
        if (menuService.deleteMenuById(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    @RequestMapping(value = "/getParentMenu",method = RequestMethod.GET)
    public Map<String,Object> getParentMenu(){
        Map<String,Object> menuInfo = new HashMap<>();
        menuInfo.put("pMenus",menuService.getParentMenu());
        return menuInfo;
    }
}
