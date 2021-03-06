package org.sang.bean;

import java.util.Date;

public class Vault extends BaseBean{
    private Long id;
    private Long tuunelId;
    private Long stationId;
    private Date observeDate = new Date();
    private String measureInterVal;
    private String observeVal;
    private String sinkDiffVal;
    private String sinkSpeed;
    private String addUpDisplacement;
    private String maxDisplacement;
    private String measureMan;
    private Integer warningState;
    private Integer distanceFace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTuunelId() {
        return tuunelId;
    }

    public void setTuunelId(Long tuunelId) {
        this.tuunelId = tuunelId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Date getObserveDate() {
        return observeDate;
    }

    public void setObserveDate(Date observeDate) {
        this.observeDate = observeDate;
    }

    public String getMeasureInterVal() {
        return measureInterVal;
    }

    public void setMeasureInterVal(String measureInterVal) {
        this.measureInterVal = measureInterVal;
    }

    public String getObserveVal() {
        return observeVal;
    }

    public void setObserveVal(String observeVal) {
        this.observeVal = observeVal;
    }

    public String getSinkDiffVal() {
        return sinkDiffVal;
    }

    public void setSinkDiffVal(String sinkDiffVal) {
        this.sinkDiffVal = sinkDiffVal;
    }

    public String getSinkSpeed() {
        return sinkSpeed;
    }

    public void setSinkSpeed(String sinkSpeed) {
        this.sinkSpeed = sinkSpeed;
    }

    public String getAddUpDisplacement() {
        return addUpDisplacement;
    }

    public void setAddUpDisplacement(String addUpDisplacement) {
        this.addUpDisplacement = addUpDisplacement;
    }

    public String getMaxDisplacement() {
        return maxDisplacement;
    }

    public void setMaxDisplacement(String maxDisplacement) {
        this.maxDisplacement = maxDisplacement;
    }

    public String getMeasureMan() {
        return measureMan;
    }

    public void setMeasureMan(String measureMan) {
        this.measureMan = measureMan;
    }

    public Integer getWarningState() {
        return warningState;
    }

    public void setWarningState(Integer warningState) {
        this.warningState = warningState;
    }

    public Integer getDistanceFace() {
        return distanceFace;
    }

    public void setDistanceFace(Integer distanceFace) {
        this.distanceFace = distanceFace;
    }
}
