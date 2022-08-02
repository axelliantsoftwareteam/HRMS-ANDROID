
package com.example.hrm.Model.BasicSetup.Roles;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class GetRolesData {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_building_association_needed")
    @Expose
    private Boolean isBuildingAssociationNeeded;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsBuildingAssociationNeeded() {
        return isBuildingAssociationNeeded;
    }

    public void setIsBuildingAssociationNeeded(Boolean isBuildingAssociationNeeded) {
        this.isBuildingAssociationNeeded = isBuildingAssociationNeeded;
    }

}
