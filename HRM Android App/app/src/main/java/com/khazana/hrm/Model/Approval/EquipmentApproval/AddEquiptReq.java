
package com.khazana.hrm.Model.Approval.EquipmentApproval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AddEquiptReq {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("model")
    @Expose
    private model model = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public model getModel() {
        return model;
    }

    public void setModel(model model) {
        this.model = model;
    }
}
