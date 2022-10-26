
package com.khazana.hrm.Model.ClientInfo.UserExprience;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("GetUserContactData")
    @Expose
    private List<GetExpByIdData> getExpByIdData = null;

    public List<GetExpByIdData> getResponse() {
        return getExpByIdData;
    }

    public void setResponse(List<GetExpByIdData> getExpByIdData) {
        this.getExpByIdData = getExpByIdData;
    }

}
