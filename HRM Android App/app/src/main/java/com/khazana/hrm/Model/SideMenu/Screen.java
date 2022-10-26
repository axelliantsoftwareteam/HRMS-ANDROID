
package com.khazana.hrm.Model.SideMenu;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Screen {

    @SerializedName("canAdd")
    @Expose
    private Boolean canAdd;
    @SerializedName("canEdit")
    @Expose
    private Boolean canEdit;
    @SerializedName("canDelete")
    @Expose
    private Boolean canDelete;
    @SerializedName("canPrint")
    @Expose
    private Boolean canPrint;
    @SerializedName("canView")
    @Expose
    private Boolean canView;
    @SerializedName("isSuperAdmin")
    @Expose
    private Boolean isSuperAdmin;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("redirectTo")
    @Expose
    private String redirectTo;
    @SerializedName("subMenu")
    @Expose
    private List<Object> subMenu = null;

    public Boolean getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(Boolean canAdd) {
        this.canAdd = canAdd;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public Boolean getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Boolean getCanPrint() {
        return canPrint;
    }

    public void setCanPrint(Boolean canPrint) {
        this.canPrint = canPrint;
    }

    public Boolean getCanView() {
        return canView;
    }

    public void setCanView(Boolean canView) {
        this.canView = canView;
    }

    public Boolean getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(Boolean isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public List<Object> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<Object> subMenu) {
        this.subMenu = subMenu;
    }

}
