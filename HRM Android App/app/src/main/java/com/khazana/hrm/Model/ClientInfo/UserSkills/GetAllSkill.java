
package com.khazana.hrm.Model.ClientInfo.UserSkills;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GetAllSkill {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("skill")
    @Expose
    private String skill;
    @SerializedName("is_removed")
    @Expose
    private Boolean isRemoved;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Boolean isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GetAllSkill(Integer id, String skill) {
        this.id = id;
        this.skill = skill;
    }
}
