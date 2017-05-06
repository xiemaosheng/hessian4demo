package com.xms.task.entity;

import java.io.Serializable;
import java.util.Date;

public class UserRewardImportLog implements Serializable {
    private Long id;                                     // ID

    private String tenantId;                             // 租户Id

    private Long uid;                                    // 用户UID

    private String source;                               // 奖励来源（后台直接导入，用户执行任务生成）

    private String title;                                // 奖励标题

    private String rewardCode;                           // 奖励发放代码

    private String rewards;                              // 奖励项明细

    private Boolean autoGained;                          // 是否手动领取奖励（true:是，false：否）

    private Date createTime;                             // 创建时间，格式（yyyy-MM-dd'T'HH:mm:ss）

    private Date updateTime;                             // 更新时间，格式（yyyy-MM-dd'T'HH:mm:ss）

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(String rewardCode) {
        this.rewardCode = rewardCode == null ? null : rewardCode.trim();
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards == null ? null : rewards.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", uid=").append(uid);
        sb.append(", source=").append(source);
        sb.append(", title=").append(title);
        sb.append(", rewardCode=").append(rewardCode);
        sb.append(", rewards=").append(rewards);
        sb.append(", autoGained=").append(autoGained);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}