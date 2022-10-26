
package com.khazana.hrm.Model.ClientInfo.UserSkills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("GetUserContactData")
    @Expose
    private List<GetUserSkillByIdData> getUserSkillByIdData = null;

    public List<GetUserSkillByIdData> getResponse() {
        return getUserSkillByIdData;
    }

    public void setResponse(List<GetUserSkillByIdData> getUserSkillByIdData) {
        this.getUserSkillByIdData = getUserSkillByIdData;
    }

}
