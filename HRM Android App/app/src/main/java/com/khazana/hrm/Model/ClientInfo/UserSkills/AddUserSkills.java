package com.khazana.hrm.Model.ClientInfo.UserSkills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddUserSkills
{

    @SerializedName("skill")
    @Expose
    private int skill;

    @SerializedName("userId")
    @Expose
    private int userId;

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
