package com.xms.task.repository.mysql.criteria;

/**
 * Created by Administrator on 2017/4/15 0015.
 */
public class DevicePOJO {
    private String deviceName;//设备名称
    private String deviceCount;//设备总数

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(String deviceCount) {
        this.deviceCount = deviceCount;
    }
}
