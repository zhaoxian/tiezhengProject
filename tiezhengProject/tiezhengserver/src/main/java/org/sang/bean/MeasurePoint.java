package org.sang.bean;

import java.util.Date;

public class MeasurePoint extends BaseBean{

    private Long id;
    private Long sectionId;
    private Long tunnelId;
    private String measPointNoFormat;
    private Long measPointNum;
    private String embLocation;
    private Date embTime = new Date();
    private String equipment;
    private String equiNo;
    private String basedOnCriteria;
    private String envConditions;
    private String maxDisplacement;//极限位移参考值
    private String XMaxDisplacement;//x方向极限位移参考值
    private String YMaxDisplacement;//y方向极限位移参考值
    private String ZMaxDisplacement;//z方向极限位移参考值
    private String sinkSpeed;//变形速率
    private String modValue;//修正值
    private String baseDistance;//据基点距离，单位m
    private String detectionMan;//检测人
    private String setIdentity;//沉降标识（+或-）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(Long tunnelId) {
        this.tunnelId = tunnelId;
    }

    public String getMeasPointNoFormat() {
        return measPointNoFormat;
    }

    public void setMeasPointNoFormat(String measPointNoFormat) {
        this.measPointNoFormat = measPointNoFormat;
    }

    public Long getMeasPointNum() {
        return measPointNum;
    }

    public void setMeasPointNum(Long measPointNum) {
        this.measPointNum = measPointNum;
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

    public String getMaxDisplacement() {
        return maxDisplacement;
    }

    public void setMaxDisplacement(String maxDisplacement) {
        this.maxDisplacement = maxDisplacement;
    }

    public String getXMaxDisplacement() {
        return XMaxDisplacement;
    }

    public void setXMaxDisplacement(String XMaxDisplacement) {
        this.XMaxDisplacement = XMaxDisplacement;
    }

    public String getYMaxDisplacement() {
        return YMaxDisplacement;
    }

    public void setYMaxDisplacement(String YMaxDisplacement) {
        this.YMaxDisplacement = YMaxDisplacement;
    }

    public String getZMaxDisplacement() {
        return ZMaxDisplacement;
    }

    public void setZMaxDisplacement(String ZMaxDisplacement) {
        this.ZMaxDisplacement = ZMaxDisplacement;
    }

    public String getSinkSpeed() {
        return sinkSpeed;
    }

    public void setSinkSpeed(String sinkSpeed) {
        this.sinkSpeed = sinkSpeed;
    }

    public String getModValue() {
        return modValue;
    }

    public void setModValue(String modValue) {
        this.modValue = modValue;
    }

    public String getBaseDistance() {
        return baseDistance;
    }

    public void setBaseDistance(String baseDistance) {
        this.baseDistance = baseDistance;
    }

    public String getDetectionMan() {
        return detectionMan;
    }

    public void setDetectionMan(String detectionMan) {
        this.detectionMan = detectionMan;
    }

    public String getSetIdentity() {
        return setIdentity;
    }

    public void setSetIdentity(String setIdentity) {
        this.setIdentity = setIdentity;
    }
}
