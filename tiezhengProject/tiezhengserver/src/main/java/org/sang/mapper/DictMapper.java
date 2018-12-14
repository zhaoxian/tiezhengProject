package org.sang.mapper;


import org.apache.ibatis.annotations.Param;
import org.sang.bean.Bid;
import org.sang.bean.Dict;

import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface DictMapper {
    List<Dict> getDictsPage(@Param("start") Integer start, @Param("size") Integer size);

    int addDict(Dict dict);

    int updateDict(@Param("dict") Dict dict);

    int deleteDictById(@Param("ids") String[] ids);

    List<Dict> getParentDicts();

    List<Dict> getDictByPid(@Param("pid")Long pid);
}
