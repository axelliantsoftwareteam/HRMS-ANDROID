
package com.khazana.hrm.Model.Approval.AttendApproval;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<AttendApproval> attendApproval = null;
    @SerializedName("recordsTotal")
    @Expose
    private Integer recordsTotal;

    public List<AttendApproval> getResponse() {
        return attendApproval;
    }

    public void setResponse(List<AttendApproval> attendApproval) {
        this.attendApproval = attendApproval;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

}
