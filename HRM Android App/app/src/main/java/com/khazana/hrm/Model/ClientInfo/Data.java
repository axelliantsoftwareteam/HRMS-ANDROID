
package com.khazana.hrm.Model.ClientInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("GetUserContactData")
    @Expose
    private GetUserByIdData getUserByIdData;

    public GetUserByIdData getResponse() {
        return getUserByIdData;
    }

    public void setResponse(GetUserByIdData getUserByIdData) {
        this.getUserByIdData = getUserByIdData;
    }

}
