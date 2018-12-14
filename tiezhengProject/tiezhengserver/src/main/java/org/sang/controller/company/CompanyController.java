package org.sang.controller.company;

import org.sang.bean.Company;
import org.sang.bean.Employee;
import org.sang.bean.Position;
import org.sang.bean.RespBean;
import org.sang.common.EmailRunnable;
import org.sang.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoxian on 2018/12/06.
 * 单位
 */
@RestController
@RequestMapping("/company/basic")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    /**
     * 添加
     * @param company
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.POST)
    public RespBean addCompany(Company company) {
        System.out.println("addCompany");
        if(companyService.addCompany(company)  == 1){
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    /**
     * 更新
     * @param company
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.PUT)
    public RespBean updateCompany(Company company, BindingResult bindingResult) {
        if (companyService.updateCompany(company) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/company/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteCompanyById(@PathVariable String ids) {
        if (companyService.deleteCompanyById(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    /**
     *列表
     * @return
     */
    @RequestMapping(value = "/company", method = RequestMethod.GET)
    public Map<String, Object> getCompanys(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> map = new HashMap<>();
        System.out.println("getCompanys");
        List<Company> companys = companyService.getCompanysByPage(page, size);
//        Long count = empService.getCountByKeywords(keywords, politicId, nationId,
//                posId,jobLevelId, engageForm, departmentId, beginDateScope);
        map.put("companys", companys);
        map.put("count", 50);
        return map;
    }
}
