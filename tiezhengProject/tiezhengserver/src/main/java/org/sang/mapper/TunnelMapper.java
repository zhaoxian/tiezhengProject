package org.sang.mapper;


import org.apache.ibatis.annotations.Param;
import org.sang.bean.Bid;
import org.sang.bean.Tunnel;

import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface TunnelMapper {
    List<Tunnel> getTunnelsPage(@Param("start") Integer start, @Param("size") Integer size);

    int addTunnel(Tunnel tunnel);

    int updateTunnel(@Param("tunnel") Tunnel tunnel);

    int deleteTunnelById(@Param("ids") String[] ids);

//    List<PoliticsStatus> getAllPolitics();
//
//    int addEmp(Employee employee);
//
//    Long getMaxWorkID();
//
//    List<Employee> getEmployeeByPage(@Param("start") Integer start, @Param("size") Integer size, @Param("keywords") String keywords, @Param("politicId") Long politicId, @Param("nationId") Long nationId, @Param("posId") Long posId, @Param("jobLevelId") Long jobLevelId, @Param("engageForm") String engageForm, @Param("departmentId") Long departmentId, @Param("startBeginDate") Date startBeginDate, @Param("endBeginDate") Date endBeginDate);
//
//    Long getCountByKeywords(@Param("keywords") String keywords, @Param("politicId") Long politicId, @Param("nationId") Long nationId, @Param("posId") Long posId, @Param("jobLevelId") Long jobLevelId, @Param("engageForm") String engageForm, @Param("departmentId") Long departmentId, @Param("startBeginDate") Date startBeginDate, @Param("endBeginDate") Date endBeginDate);
//
//    int updateEmp(@Param("emp") Employee employee);
//
//    int deleteEmpById(@Param("ids") String[] ids);
//
//    int addEmps(@Param("emps") List<Employee> emps);
//
//    List<Employee> getEmployeeByPageShort(@Param("start") int start, @Param("size") Integer size);
}
