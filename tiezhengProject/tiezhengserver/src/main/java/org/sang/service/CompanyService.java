package org.sang.service;

import org.sang.bean.Company;
import org.sang.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by zhaoxian
 */
@Service
@Transactional
public class CompanyService {
    @Autowired
    CompanyMapper companyMapper;

    public int addCompany(Company company) {
        return companyMapper.addCompany(company);
    }

    public List<Company> getCompanysByPage(Integer page, Integer size) {
        int start = (page - 1) * size;
        return companyMapper.getCompanysByPage(start, size);
    }

    public int updateCompany(Company company) {
        return companyMapper.updateCompany(company);
    }

    public boolean deleteCompanyById(String ids) {
        String[] split = ids.split(",");
        return companyMapper.deleteCompanyById(split) == split.length;
    }
    public List<Company> getAllCompanys(){
        return companyMapper.getAllCompanys();
    }
}
