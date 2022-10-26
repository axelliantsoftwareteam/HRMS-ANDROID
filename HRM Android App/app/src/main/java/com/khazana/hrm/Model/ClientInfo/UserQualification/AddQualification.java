package com.khazana.hrm.Model.ClientInfo.UserQualification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddQualification
{
    @SerializedName("graduationYear")
    @Expose
    private String graduationYear;
    @SerializedName("institute")
    @Expose
    private String institute;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
