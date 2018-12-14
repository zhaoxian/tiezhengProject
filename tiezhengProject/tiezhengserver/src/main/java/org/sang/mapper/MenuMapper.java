package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Menu;
import org.sang.bean.PMenu;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
public interface MenuMapper {
    List<Menu> getAllMenu();

    List<Menu> getMenusByHrId(Long hrId);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);

    int addMenu(Menu menu);

    int updateMenu(@Param("menu") Menu menu);

    List<Menu> getMenusByParam(@Param("start") Integer start, @Param("size") Integer size);

    int deleteMenuById(@Param("ids") String[] split);

    List<PMenu> getParentMenu();
}
