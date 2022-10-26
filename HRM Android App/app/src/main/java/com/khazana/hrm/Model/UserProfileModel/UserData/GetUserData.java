
package com.khazana.hrm.Model.UserProfileModel.UserData;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GetUserData {

    @SerializedName("salutation")
    @Expose
    private Integer salutation;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("dob_string")
    @Expose
    private String dobString;
    @SerializedName("dob")
    @Expose
    private String dob;
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
    private List<Integer> designation = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("department")
    @Expose
    private Integer department;
    @SerializedName("building")
    @Expose
    private Integer building;
    @SerializedName("reports_to_list")
    @Expose
    private List<ReportsTo> reportsToList = null;
    @SerializedName("reporting_to")
    @Expose
    private ReportingTo reportingTo;

    public Integer getSalutation() {
        return salutation;
    }

    public void setSalutation(Integer salutation) {
        this.salutation = salutation;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDobString() {
        return dobString;
    }

    public void setDobString(String dobString) {
        this.dobString = dobString;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public List<Integer> getDesignation() {
        return designation;
    }

    public void setDesignation(List<Integer> designation) {
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

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
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
