package org.sang.mapper;


import org.apache.ibatis.annotations.Param;
import org.sang.bean.Bid;
import org.sang.bean.Land;

import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface LandMapper {
    List<Land> getLandsByPage(@Param("start") Integer start, @Param("size") Integer size);

    int addLand(Land land);

    int updateLand(@Param("land") Land land);

    int deleteLandById(@Param("ids") String[] ids);

}
