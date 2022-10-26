
package com.khazana.hrm.Model.Organo;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<OrganoData> organoData = null;

    public List<OrganoData> getResponse() {
        return organoData;
    }

    public void setResponse(List<OrganoData> organoData) {
        this.organoData = organoData;
    }

}
