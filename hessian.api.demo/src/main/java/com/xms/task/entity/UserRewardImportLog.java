package com.xms.task.entity;

import java.io.Serializable;
import java.util.Date;

public class UserRewardImportLog implements Serializable {
    private Long id;                                     // ID

    private String tenantId;                             // 租户Id

    private Long uid;                                    // 用户UID

    private String title;                                // 奖励标题

    private Integer exp;

    private Integer gold;

    private Integer flower;

    private Integer guardCoin;

    private Integer guardTicket;

    private Integer ticket;

    private Boolean autoGained;                          // 是否手动领取奖励（true:是，false：否）

    private Date createTime;                             // 创建时间，格式（yyyy-MM-dd'T'HH:mm:ss）


    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getFlower() {
        return flower;
    }

    public void setFlower(Integer flower) {
        this.flower = flower;
    }

    public Integer getGuardCoin() {
        return guardCoin;
    }

    public void setGuardCoin(Integer guardCoin) {
        this.guardCoin = guardCoin;
    }

    public Integer getGuardTicket() {
        return guardTicket;
    }

    public void setGuardTicket(Integer guardTicket) {
        this.guardTicket = guardTicket;
    }

    public Integer getTicket() {
        return ticket;
    }

    public void setTicket(Integer ticket) {
        this.ticket = ticket;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Boolean getAutoGained() {
        return autoGained;
    }

    public void setAutoGained(Boolean autoGained) {
        this.autoGained = autoGained;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserRewardImportLog{" +
                "id=" + id +
                ", tenantId='" + tenantId + '\'' +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", exp=" + exp +
                ", gold=" + gold +
                ", flower=" + flower +
                ", guardCoin=" + guardCoin +
                ", guardTicket=" + guardTicket +
                ", ticket=" + ticket +
                ", autoGained=" + autoGained +
                ", createTime=" + createTime +
                '}';
    }
}