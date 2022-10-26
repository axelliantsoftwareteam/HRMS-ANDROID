package com.khazana.hrm.Model.UserProfileModel;

import com.google.gson.annotations.SerializedName;

public class EditProfile {


    @SerializedName("name")
    private String name;
    @SerializedName("cnic")
    private String cnic ;

    @SerializedName("confirm_password")
    private String confirm_password;

    @SerializedName("dob")
    private String dob;

    @SerializedName("email")
    private String email;


    @SerializedName("new_password")
    private String new_password;

    @SerializedName("profile_picture")
    private String profile_picture;

    @SerializedName("salutation")
    private Integer salutation;

    @SerializedName("building")
    private Integer building;

    @SerializedName("gender")
    private Integer gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public Integer getSalutation() {
        return salutation;
    }

    public void setSalutation(Integer salutation) {
        this.salutation = salutation;
    }

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
