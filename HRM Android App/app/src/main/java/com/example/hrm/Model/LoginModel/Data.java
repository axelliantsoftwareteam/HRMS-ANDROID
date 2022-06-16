
package com.example.hrm.Model.LoginModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("token")
    @Expose
    private String getmicToken;

    public String getGetmicToken() {
        return getmicToken;
    }

    public void setGetmicToken(String getmicToken) {
        this.getmicToken = getmicToken;
    }





    @SerializedName("role_screens")
    @Expose
    private List<Object> roleScreens = null;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Object> getRoleScreens() {
        return roleScreens;
    }

    public void setRoleScreens(List<Object> roleScreens) {
        this.roleScreens = roleScreens;
    }

}
