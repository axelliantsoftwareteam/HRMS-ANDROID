
package com.example.hrm.Model.UserEvaluations.response;

import com.google.gson.annotations.SerializedName;

public class UserEvaluationsResponse {

    @SerializedName("data")
    private UserEvaluationsData data;

    @SerializedName("meta")
    private UserEvaluationsMeta meta;

    public UserEvaluationsData getData() {
        return data;
    }

    public void setData(UserEvaluationsData data) {
        this.data = data;
    }

    public UserEvaluationsMeta getMeta() {
        return meta;
    }

    public void setMeta(UserEvaluationsMeta meta) {
        this.meta = meta;
    }
}
