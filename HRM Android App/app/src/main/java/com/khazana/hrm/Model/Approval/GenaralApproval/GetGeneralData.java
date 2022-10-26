
package com.khazana.hrm.Model.Approval.GenaralApproval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GetGeneralData {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("work_flow")
    @Expose
    private List<WorkFlow> workFlow = null;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("employee")
    @Expose
    private int employee;
    @SerializedName("name")
    @Expose
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<WorkFlow> getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(List<WorkFlow> workFlow) {
        this.workFlow = workFlow;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GetGeneralData() {
    }

    public GetGeneralData(int id, List<WorkFlow> workFlow, int status, String itemName, String comment) {
        this.id = id;
        this.workFlow = workFlow;
        this.status = status;
        this.itemName = itemName;
        this.comment = comment;
    }

//    public GetGeneralData(int id, int status, String itemName, String comment) {
//        this.id = id;
//        this.status = status;
//        this.itemName = itemName;
//        this.comment = comment;
//    }
}
