
package com.khazana.hrm.Model;

public class User {

    private Integer id;

    private String primaryEmail;

    private String linkedin;

    private Integer salutation;

    private String cnic;

    private String dob;

    private String email;

    private String name;

    private Integer gender;

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

    public Integer getSalutation() {
        return salutation;
    }

    public void setSalutation(Integer salutation) {
        this.salutation = salutation;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public User(Integer id, String primaryEmail, String linkedin, String cnic, String dob, String email, String name) {
        this.id = id;
        this.primaryEmail = primaryEmail;
        this.linkedin = linkedin;
        this.cnic = cnic;
        this.dob = dob;
        this.email = email;
        this.name = name;
    }
}
