package org.sang.mapper;


import org.apache.ibatis.annotations.Param;
import org.sang.bean.Bid;
import org.sang.bean.MeasurePoint;

import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface MeasurePointMapper {
    List<MeasurePoint> getMeasurePointsByPage(@Param("start") Integer start, @Param("size") Integer size);

    int addMeasurePoint(MeasurePoint measurePoint);

    int updateMeasurePoint(@Param("measurePoint") MeasurePoint measurePoint);

    int deleteMeasurePointById(@Param("ids") String[] ids);

}
