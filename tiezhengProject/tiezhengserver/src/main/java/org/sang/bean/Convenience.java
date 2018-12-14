package org.sang.bean;

import java.util.Date;

public class Convenience extends BaseBean{
    private Long id;
    private Long tunnelId;
    private Long stationId;
    private Date observeDate = new Date();
    private String measureInterval;
    private String rulerVal;
    private String diff;
    private String addUpDiplacement;
    private String maxSpeed;
    private String observeMan;
    private Integer warnState;
    private Integer distanceFace;

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

    public String getMeasureInterval() {
        return measureInterval;
    }

    public void setMeasureInterval(String measureInterval) {
        this.measureInterval = measureInterval;
    }

    public String getRulerVal() {
        return rulerVal;
    }

    public void setRulerVal(String rulerVal) {
        this.rulerVal = rulerVal;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getAddUpDiplacement() {
        return addUpDiplacement;
    }

    public void setAddUpDiplacement(String addUpDiplacement) {
        this.addUpDiplacement = addUpDiplacement;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getObserveMan() {
        return observeMan;
    }

    public void setObserveMan(String observeMan) {
        this.observeMan = observeMan;
    }

    public Integer getWarnState() {
        return warnState;
    }

    public void setWarnState(Integer warnState) {
        this.warnState = warnState;
    }

    public Integer getDistanceFace() {
        return distanceFace;
    }

    public void setDistanceFace(Integer distanceFace) {
        this.distanceFace = distanceFace;
    }
}
