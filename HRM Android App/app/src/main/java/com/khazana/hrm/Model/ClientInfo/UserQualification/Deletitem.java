package com.khazana.hrm.Model.ClientInfo.UserQualification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deletitem {
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
