
package com.example.myapplication.Model.LeavesModel;

import javax.annotation.Generated;

import com.example.myapplication.Model.CheckIn.CheckInDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("jsonschema2pojo")
public class Data
{
    @SerializedName("leaves")
    @Expose
    private List<Leaves> leaves = null;

    @SerializedName("work_flow")
    @Expose
    private List<WorkFlow> workFlows = null;

    public List<WorkFlow> getWorkFlows() {
        return workFlows;
    }

    public void setWorkFlows(List<WorkFlow> workFlows) {
        this.workFlows = workFlows;
    }

    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public List<Leaves> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<Leaves> leaves) {
        this.leaves = leaves;
    }
}
