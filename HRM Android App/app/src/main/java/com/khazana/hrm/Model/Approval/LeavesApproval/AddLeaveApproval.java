
package com.khazana.hrm.Model.Approval.LeavesApproval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AddLeaveApproval {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("model")
    @Expose
    private leavemodel leavemodel = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public leavemodel getLeavemodel() {
        return leavemodel;
    }

    public void setLeavemodel(leavemodel leavemodel) {
        this.leavemodel = leavemodel;
    }
}
