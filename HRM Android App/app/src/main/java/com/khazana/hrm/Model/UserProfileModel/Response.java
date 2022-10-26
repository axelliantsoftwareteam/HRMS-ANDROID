
package com.khazana.hrm.Model.UserProfileModel;

import java.util.List;
import javax.annotation.Generated;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Response {

    @SerializedName("user_contacts")
    @Expose
    private List<UserContact> userContacts = null;
    @SerializedName("user_qualification")
    @Expose
    private List<UserQualification> userQualification = null;
    @SerializedName("user_skills")
    @Expose
    private List<UserSkill> userSkills = null;
    @SerializedName("salutation")
    @Expose
    private String salutation;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("joining_date")
    @Expose
    private String joiningDate;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("cnic")
    @Expose
    private String cnic;
    @SerializedName("employee_code")
    @Expose
    private String employeeCode;
    @SerializedName("designation")
    @Expose
    private List<Object> designation = null;


    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("building")
    @Expose
    private String building;
    @SerializedName("reports_to_list")
    @Expose
    private List<ReportsTo> reportsToList = null;
    @SerializedName("reporting_to")
    @Expose
    private ReportingTo reportingTo;

    public List<UserContact> getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(List<UserContact> userContacts) {
        this.userContacts = userContacts;
    }

    public List<UserQualification> getUserQualification() {
        return userQualification;
    }

    public void setUserQualification(List<UserQualification> userQualification) {
        this.userQualification = userQualification;
    }

    public List<UserSkill> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(List<UserSkill> userSkills) {
        this.userSkills = userSkills;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public List<Object> getDesignation() {
        return designation;
    }

    public void setDesignation(List<Object> designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public List<ReportsTo> getReportsToList() {
        return reportsToList;
    }

    public void setReportsToList(List<ReportsTo> reportsToList) {
        this.reportsToList = reportsToList;
    }

    public ReportingTo getReportingTo() {
        return reportingTo;
    }

    public void setReportingTo(ReportingTo reportingTo) {
        this.reportingTo = reportingTo;
    }

}
