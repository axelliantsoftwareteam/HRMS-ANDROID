
package com.khazana.hrm.Model.Approval.GenaralApproval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("equipment")
    @Expose
    private List<GetGeneralData> getGeneralData = null;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;

    public List<GetGeneralData> getEquipment() {
        return getGeneralData;
    }

    public void setEquipment(List<GetGeneralData> getGeneralData) {
        this.getGeneralData = getGeneralData;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

}
