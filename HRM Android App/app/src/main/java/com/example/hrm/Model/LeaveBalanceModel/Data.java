
package com.example.hrm.Model.LeaveBalanceModel;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<LeaveBalanceData> leaveBalanceData = null;

    public List<LeaveBalanceData> getResponse() {
        return leaveBalanceData;
    }

    public void setResponse(List<LeaveBalanceData> leaveBalanceData) {
        this.leaveBalanceData = leaveBalanceData;
    }

}
