
package com.khazana.hrm.Model.ClientInfo.UserQualification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("GetUserContactData")
    @Expose
    private List<GetQualifyData> getQualifyData = null;

    public List<GetQualifyData> getResponse() {
        return getQualifyData;
    }

    public void setResponse(List<GetQualifyData> getQualifyData) {
        this.getQualifyData = getQualifyData;
    }

}
