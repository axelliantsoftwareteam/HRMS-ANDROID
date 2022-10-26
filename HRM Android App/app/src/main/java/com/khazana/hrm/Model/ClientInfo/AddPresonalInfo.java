package com.khazana.hrm.Model.ClientInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPresonalInfo
{

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    private String name;
    @SerializedName("cnic")
    private String cnic ;

    @SerializedName("linkedin")
    private String linkedin ;

    @SerializedName("dob")
    private String dob;

    @SerializedName("primaryEmail")
    private String primaryEmail;


    @SerializedName("salutation")
    private Integer salutation;


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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }

    public void setPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
