
package com.example.hrm.Model.BasicSetup.Skills.AddSkill;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("Response")
    @Expose
    private SkillData skillData;

    public SkillData getResponse() {
        return skillData;
    }

    public void setResponse(SkillData skillData) {
        this.skillData = skillData;
    }

}
