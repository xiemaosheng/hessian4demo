package com.xms.task.repository.mysql.criteria;

import java.io.Serializable;

public class Device implements Serializable {
    private String deviceSn;

    private Integer deviceCatId;

    private String deviceName;

    private String deviceType;

    private static final long serialVersionUID = 1L;

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn == null ? null : deviceSn.trim();
    }

    public Integer getDeviceCatId() {
        return deviceCatId;
    }

    public void setDeviceCatId(Integer deviceCatId) {
        this.deviceCatId = deviceCatId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deviceSn=").append(deviceSn);
        sb.append(", deviceCatId=").append(deviceCatId);
        sb.append(", deviceName=").append(deviceName);
        sb.append(", deviceType=").append(deviceType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}