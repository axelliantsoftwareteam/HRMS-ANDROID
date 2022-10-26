
package com.khazana.hrm.Model.Approval.EquipmentApproval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class WorkFlow {

    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("RoleId")
    @Expose
    private Integer roleId;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("TimeStamp")
    @Expose
    private Object timeStamp;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("ProcessName")
    @Expose
    private String processName;
    @SerializedName("IsAddedtoTree")
    @Expose
    private Boolean isAddedtoTree;
    @SerializedName("ProcessStatus")
    @Expose
    private Boolean processStatus;
    @SerializedName("IsCompletelyRejected")
    @Expose
    private Boolean isCompletelyRejected;

    public WorkFlow() {

    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Boolean getIsAddedtoTree() {
        return isAddedtoTree;
    }

    public void setIsAddedtoTree(Boolean isAddedtoTree) {
        this.isAddedtoTree = isAddedtoTree;
    }

    public Boolean getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Boolean processStatus) {
        this.processStatus = processStatus;
    }

    public Boolean getIsCompletelyRejected() {
        return isCompletelyRejected;
    }

    public void setIsCompletelyRejected(Boolean isCompletelyRejected) {
        this.isCompletelyRejected = isCompletelyRejected;
    }

    public WorkFlow(int type, Boolean status, String displayName, String processName) {
        this.type = type;
        this.status = status;
        this.displayName = displayName;
        this.processName = processName;
    }


}
