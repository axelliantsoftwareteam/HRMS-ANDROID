
package com.khazana.hrm.Model.UserProfileModel.Gender;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetGenderData> getGenderData = null;

    public List<GetGenderData> getResponse() {
        return getGenderData;
    }

    public void setResponse(List<GetGenderData> getGenderData) {
        this.getGenderData = getGenderData;
    }

}
