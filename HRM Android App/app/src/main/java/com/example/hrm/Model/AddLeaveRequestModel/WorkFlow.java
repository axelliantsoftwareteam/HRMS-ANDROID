
package com.example.hrm.Model.AddLeaveRequestModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class WorkFlow {

    @SerializedName("RoleId")
    @Expose
    private Integer roleId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("ProcessName")
    @Expose
    private String processName;
    @SerializedName("ProcessStatus")
    @Expose
    private Boolean processStatus;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("IsCompletelyRejected")
    @Expose
    private Boolean isCompletelyRejected;
    @SerializedName("IsAddedtoTree")
    @Expose
    private Boolean isAddedtoTree;
    @SerializedName("TimeStamp")
    @Expose
    private Object timeStamp;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Boolean getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Boolean processStatus) {
        this.processStatus = processStatus;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Boolean getIsCompletelyRejected() {
        return isCompletelyRejected;
    }

    public void setIsCompletelyRejected(Boolean isCompletelyRejected) {
        this.isCompletelyRejected = isCompletelyRejected;
    }

    public Boolean getIsAddedtoTree() {
        return isAddedtoTree;
    }

    public void setIsAddedtoTree(Boolean isAddedtoTree) {
        this.isAddedtoTree = isAddedtoTree;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

}
