package org.sang.service;

import org.sang.bean.Menu;
import org.sang.bean.PMenu;
import org.sang.common.HrUtils;
import org.sang.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Service
@Transactional
@CacheConfig(cacheNames = "menus_cache")
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

//    @Cacheable(key = "#root.methodName")
    public List<Menu> getAllMenu(){
        List<Menu> menus = menuMapper.getAllMenu();
        return menus;
    }

    public List<Menu> getMenusByHrId() {
        return menuMapper.getMenusByHrId(HrUtils.getCurrentHr().getId());
    }

    public List<Menu> menuTree() {
        return menuMapper.menuTree();
    }

    public List<Long> getMenusByRid(Long rid) {
        return menuMapper.getMenusByRid(rid);
    }

    public int addMenu(Menu menu){
        return menuMapper.addMenu(menu);
    }

    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    public List<Menu> getMenusByParam(Integer page,Integer size) {
        int start = (page - 1) * size;
        List<Menu> menus = menuMapper.getMenusByParam(start,size);
        return menus;
    }

    public boolean deleteMenuById(String ids) {
        String[] split = ids.split(",");
        return menuMapper.deleteMenuById(split) == split.length;
    }

    public List<PMenu> getParentMenu() {
        List<PMenu> pMenus = menuMapper.getParentMenu();
        return pMenus;
    }
}
