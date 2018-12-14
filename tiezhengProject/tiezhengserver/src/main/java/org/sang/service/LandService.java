package org.sang.service;

import org.sang.bean.Land;
import org.sang.bean.Section;
import org.sang.mapper.LandMapper;
import org.sang.mapper.SectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sang on 2018/1/12.
 */
@Service
@Transactional
public class LandService {
    @Autowired
    LandMapper landMapper;

//    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
//    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
//    SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");
//    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public List<Land> getLandsByPage(Integer page, Integer size){
        int start = (page - 1) * size;
        return landMapper.getLandsByPage(start, size);
    }
    public int addLand(Land land) {
        return landMapper.addLand(land);
    }

    public int updateLand(Land land) {
        return landMapper.updateLand(land);
    }

    public boolean deleteLandById(String ids) {
        String[] split = ids.split(",");
        return landMapper.deleteLandById(split) == split.length;
    }

//    public List<PoliticsStatus> getAllPolitics() {
//        return empMapper.getAllPolitics();
//    }
//
//    public int addEmp(Employee employee) {
//        Date beginContract = employee.getBeginContract();
//        Date endContract = employee.getEndContract();
//        Double contractTerm = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12 + Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract));
//        employee.setContractTerm(Double.parseDouble(decimalFormat.format(contractTerm / 12)));
//        return empMapper.addEmp(employee);
//    }
//
//    public Long getMaxWorkID() {
//        Long maxWorkID = empMapper.getMaxWorkID();
//        return maxWorkID == null ? 0 : maxWorkID;
//    }
//
//    public List<Employee> getEmployeeByPage(Integer page, Integer size, String keywords, Long politicId, Long nationId, Long posId, Long jobLevelId, String engageForm, Long departmentId, String beginDateScope) {
//        int start = (page - 1) * size;
//        Date startBeginDate = null;
//        Date endBeginDate = null;
//        if (beginDateScope != null && beginDateScope.contains(",")) {
//            try {
//                String[] split = beginDateScope.split(",");
//                startBeginDate = birthdayFormat.parse(split[0]);
//                endBeginDate = birthdayFormat.parse(split[1]);
//            } catch (ParseException e) {
//            }
//        }
//        return empMapper.getEmployeeByPage(start, size, keywords, politicId, nationId, posId, jobLevelId, engageForm, departmentId, startBeginDate, endBeginDate);
//    }
//
//    public Long getCountByKeywords(String keywords, Long politicId, Long nationId, Long posId, Long jobLevelId, String engageForm, Long departmentId, String beginDateScope) {
//        Date startBeginDate = null;
//        Date endBeginDate = null;
//        if (beginDateScope != null && beginDateScope.contains(",")) {
//            try {
//                String[] split = beginDateScope.split(",");
//                startBeginDate = birthdayFormat.parse(split[0]);
//                endBeginDate = birthdayFormat.parse(split[1]);
//            } catch (ParseException e) {
//            }
//        }
//        return empMapper.getCountByKeywords(keywords, politicId, nationId, posId, jobLevelId, engageForm, departmentId, startBeginDate, endBeginDate);
//    }
//
//    public int updateEmp(Employee employee) {
//        Date beginContract = employee.getBeginContract();
//        Date endContract = employee.getEndContract();
//        Double contractTerm = (Double.parseDouble(yearFormat.format(endContract)) - Double.parseDouble(yearFormat.format(beginContract))) * 12 + Double.parseDouble(monthFormat.format(endContract)) - Double.parseDouble(monthFormat.format(beginContract));
//        employee.setContractTerm(Double.parseDouble(decimalFormat.format(contractTerm / 12)));
//        return empMapper.updateEmp(employee);
//    }
//
//    public boolean deleteEmpById(String ids) {
//        String[] split = ids.split(",");
//        return empMapper.deleteEmpById(split) == split.length;
//    }
//
//    public List<Employee> getAllEmployees() {
//        return empMapper.getEmployeeByPage(null, null, "", null, null, null, null, null, null, null, null);
//    }
//
//    public int addEmps(List<Employee> emps) {
//        return empMapper.addEmps(emps);
//    }
//
//    public List<Employee> getEmployeeByPageShort(Integer page, Integer size) {
//        int start = (page - 1) * size;
//        return empMapper.getEmployeeByPageShort(start,size);
//    }
}
