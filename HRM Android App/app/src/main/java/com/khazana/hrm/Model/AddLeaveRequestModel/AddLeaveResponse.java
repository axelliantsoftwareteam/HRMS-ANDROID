
package com.khazana.hrm.Model.AddLeaveRequestModel;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AddLeaveResponse {

    @SerializedName("serial_id")
    @Expose
    private String serialId;
    @SerializedName("work_flow")
    @Expose
    private List<WorkFlow> workFlow = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("end_timestamp")
    @Expose
    private Integer endTimestamp;
    @SerializedName("leave_type")
    @Expose
    private String leaveType;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("employee")
    @Expose
    private Integer employee;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("id")
    @Expose
    private String id;

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public List<WorkFlow> getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(List<WorkFlow> workFlow) {
        this.workFlow = workFlow;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Integer endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
