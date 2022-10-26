
package com.khazana.hrm.Model.SpinerModel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class SpinerResponse {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("displayMember")
    @Expose
    private String displayMember;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayMember() {
        return displayMember;
    }

    public void setDisplayMember(String displayMember) {
        this.displayMember = displayMember;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
