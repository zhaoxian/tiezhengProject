package org.sang.mapper;


import org.apache.ibatis.annotations.Param;
import org.sang.bean.Bid;

import java.util.Date;
import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
public interface BidMapper {
    List<Bid> getBidsPage(@Param("start") Integer start, @Param("size") Integer size);

    int addBid(Bid bid);

    int updateBid(@Param("bid") Bid bid);

    int deleteBidById(@Param("ids") String[] ids);

    List<Bid> getAllBids();

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
