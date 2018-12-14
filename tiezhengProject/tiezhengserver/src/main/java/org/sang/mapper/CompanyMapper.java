package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.bean.Company;

import java.util.List;

public interface CompanyMapper {
    int addCompany(Company company);

    int updateCompany(@Param("company") Company company);

    List<Company> getCompanysByPage(@Param("start") Integer start, @Param("size") Integer size);

    int deleteCompanyById(@Param("ids") String[] ids);

    List<Company> getAllCompanys();
}
