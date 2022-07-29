
package com.example.hrm.Model.BasicSetup.Skills;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private List<GetSkillsData> getSkillsData = null;

    public List<GetSkillsData> getResponse() {
        return getSkillsData;
    }

    public void setResponse(List<GetSkillsData> getSkillsData) {
        this.getSkillsData = getSkillsData;
    }

}
