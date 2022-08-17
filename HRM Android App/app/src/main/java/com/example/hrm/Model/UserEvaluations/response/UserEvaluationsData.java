
package com.example.hrm.Model.UserEvaluations.response;

import com.google.gson.annotations.SerializedName;

public class UserEvaluationsData {

    @SerializedName("Response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
