package org.sang.bean;

import java.util.Date;

public class Section extends BaseBean {
    private Long id;
    private Long tunnelId;
    private Long bidId;
    private String beginKm;
    private Long sectionNum;
    private Long sectionInterval;
    private String geologyType;
    private String conMethod;
    private String rockLevel;
    private String liningType;
    private Float clearHeight;
    private Float secLiningThickness;
    private String embLocation;
    private Date embTime = new Date();
    private String basedOnCriteria;
    private String envConditions;
    private String equipment;
    private String equiNo;
    private String detectionMan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Long tunnelId) {
        this.tunnelId = tunnelId;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public String getBeginKm() {
        return beginKm;
    }

    public void setBeginKm(String beginKm) {
        this.beginKm = beginKm;
    }

    public Long getSectionNum() {
        return sectionNum;
    }

    public void setSectionNum(Long sectionNum) {
        this.sectionNum = sectionNum;
    }

    public Long getSectionInterval() {
        return sectionInterval;
    }

    public void setSectionInterval(Long sectionInterval) {
        this.sectionInterval = sectionInterval;
    }

    public String getGeologyType() {
        return geologyType;
    }

    public void setGeologyType(String geologyType) {
        this.geologyType = geologyType;
    }

    public String getConMethod() {
        return conMethod;
    }

    public void setConMethod(String conMethod) {
        this.conMethod = conMethod;
    }

    public String getRockLevel() {
        return rockLevel;
    }

    public void setRockLevel(String rockLevel) {
        this.rockLevel = rockLevel;
    }

    public String getLiningType() {
        return liningType;
    }

    public void setLiningType(String liningType) {
        this.liningType = liningType;
    }

    public Float getClearHeight() {
        return clearHeight;
    }

    public void setClearHeight(Float clearHeight) {
        this.clearHeight = clearHeight;
    }

    public Float getSecLiningThickness() {
        return secLiningThickness;
    }

    public void setSecLiningThickness(Float secLiningThickness) {
        this.secLiningThickness = secLiningThickness;
    }

    public String getEmbLocation() {
        return embLocation;
    }

    public void setEmbLocation(String embLocation) {
        this.embLocation = embLocation;
    }

    public Date getEmbTime() {
        return embTime;
    }

    public void setEmbTime(Date embTime) {
        this.embTime = embTime;
    }

    public String getBasedOnCriteria() {
        return basedOnCriteria;
    }

    public void setBasedOnCriteria(String basedOnCriteria) {
        this.basedOnCriteria = basedOnCriteria;
    }

    public String getEnvConditions() {
        return envConditions;
    }

    public void setEnvConditions(String envConditions) {
        this.envConditions = envConditions;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getEquiNo() {
        return equiNo;
    }

    public void setEquiNo(String equiNo) {
        this.equiNo = equiNo;
    }

    public String getDetectionMan() {
        return detectionMan;
    }

    public void setDetectionMan(String detectionMan) {
        this.detectionMan = detectionMan;
    }
}
