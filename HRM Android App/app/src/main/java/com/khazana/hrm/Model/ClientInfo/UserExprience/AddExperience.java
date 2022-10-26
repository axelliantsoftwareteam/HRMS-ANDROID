package com.khazana.hrm.Model.ClientInfo.UserExprience;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddExperience
{
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("jobDescription")
    @Expose
    private String jobDescription;
    @SerializedName("lastSalary")
    @Expose
    private String lastSalary;

    @SerializedName("resignDate")
    @Expose
    private String resignDate;

    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getLastSalary() {
        return lastSalary;
    }

    public void setLastSalary(String lastSalary) {
        this.lastSalary = lastSalary;
    }

    public String getResignDate() {
        return resignDate;
    }

    public void setResignDate(String resignDate) {
        this.resignDate = resignDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
