
package com.khazana.hrm.Model.ClientInfo.UserExprience;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GetExpByIdData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("jobDescription")
    @Expose
    private String jobDescription;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("lastSalary")
    @Expose
    private String lastSalary;
    @SerializedName("resignDate")
    @Expose
    private String resignDate;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


    public String getResignDate() {
        return resignDate;
    }

    public void setResignDate(String resignDate) {
        this.resignDate = resignDate;
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

    public String getLastSalary() {
        return lastSalary;
    }

    public void setLastSalary(String lastSalary) {
        this.lastSalary = lastSalary;
    }

    public GetExpByIdData(Integer id, String designation, String jobDescription, String company, String lastSalary, String resignDate) {
        this.id = id;
        this.designation = designation;
        this.jobDescription = jobDescription;
        this.company = company;
        this.lastSalary = lastSalary;
        this.resignDate = resignDate;
    }
}
