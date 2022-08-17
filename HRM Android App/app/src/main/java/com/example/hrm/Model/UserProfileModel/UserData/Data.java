
package com.example.hrm.Model.UserProfileModel.UserData;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private GetUserData getUserData;

    public GetUserData getResponse() {
        return getUserData;
    }

    public void setResponse(GetUserData getUserData) {
        this.getUserData = getUserData;
    }

}
