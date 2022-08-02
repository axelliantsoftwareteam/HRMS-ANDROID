
package com.example.hrm.Model.BasicSetup.Roles;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetRolesData> getRolesData = null;

    public List<GetRolesData> getResponse() {
        return getRolesData;
    }

    public void setResponse(List<GetRolesData> getRolesData) {
        this.getRolesData = getRolesData;
    }

}
