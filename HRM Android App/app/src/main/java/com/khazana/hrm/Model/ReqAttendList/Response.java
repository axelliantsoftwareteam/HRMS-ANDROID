
package com.khazana.hrm.Model.ReqAttendList;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Response {

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
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("employee")
    @Expose
    private Integer employee;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("date")
    @Expose
    private String date;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
