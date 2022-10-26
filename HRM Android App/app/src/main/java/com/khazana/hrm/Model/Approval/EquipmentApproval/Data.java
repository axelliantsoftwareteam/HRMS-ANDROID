
package com.khazana.hrm.Model.Approval.EquipmentApproval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("leaves")
    @Expose
    private List<GetEquipmentData> leaves = null;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;

    public List<GetEquipmentData> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<GetEquipmentData> leaves) {
        this.leaves = leaves;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

}
