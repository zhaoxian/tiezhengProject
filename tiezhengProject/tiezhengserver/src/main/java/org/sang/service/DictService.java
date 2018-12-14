package org.sang.service;

import org.sang.bean.*;
import org.sang.mapper.BidMapper;
import org.sang.mapper.DictMapper;
import org.sang.mapper.EmpMapper;
import org.sang.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
@Service
@Transactional
public class DictService {
    @Autowired
    DictMapper dictMapper;

    public List<Dict> getDictsByPage(Integer page, Integer size){
        int start = (page - 1) * size;
        List<Dict> dictList = dictMapper.getDictsPage(start, size);
        for(Dict dict : dictList){
            Long parentId = dict.getParentId();
            if(!Utils.isBlank(parentId)){
                List<Dict> pDicts = dictMapper.getDictByPid(parentId);
                if(pDicts.size() != 0){
                    dict.setParentName(String.valueOf(pDicts.get(0).getName()));
                }
            }else{
                dict.setParentName("-");
            }
        }
        return dictList;
    }
    public int addDict(Dict dict) {
        return dictMapper.addDict(dict);
    }

    public int updateDict(Dict dict) {
        return dictMapper.updateDict(dict);
    }

    public boolean deleteDictById(String ids) {
        String[] split = ids.split(",");
        return dictMapper.deleteDictById(split) == split.length;
    }

    public List<Dict> getParentDicts() {
        return dictMapper.getParentDicts();
    }
}
