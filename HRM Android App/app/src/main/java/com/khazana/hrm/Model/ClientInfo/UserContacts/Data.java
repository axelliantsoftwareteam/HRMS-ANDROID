
package com.khazana.hrm.Model.ClientInfo.UserContacts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("GetUserContactData")
    @Expose
    private List<GetUserContactData> getUserContactData = null;

    public List<GetUserContactData> getResponse() {
        return getUserContactData;
    }

    public void setResponse(List<GetUserContactData> getUserContactData) {
        this.getUserContactData = getUserContactData;
    }

}
