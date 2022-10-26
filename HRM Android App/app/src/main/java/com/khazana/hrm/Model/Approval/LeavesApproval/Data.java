
package com.khazana.hrm.Model.Approval.LeavesApproval;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    public List<LeaveApproval> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<LeaveApproval> leaves) {
        this.leaves = leaves;
    }

    @SerializedName("leaves")
    @Expose
    private List<LeaveApproval> leaves = null;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

}
