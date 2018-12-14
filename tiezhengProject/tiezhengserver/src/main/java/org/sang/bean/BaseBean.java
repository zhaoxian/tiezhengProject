package org.sang.bean;

import java.util.Date;

public class BaseBean {
    private Date createDate = new Date();//创建时间
    private Date updateDate = new Date();//修改时间
    private String description;//描述

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
