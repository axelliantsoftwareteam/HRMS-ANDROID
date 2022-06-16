
package com.example.hrm.Model.Organo;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Response {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pid")
    @Expose
    private Integer pid;
    @SerializedName("txt")
    @Expose
    private String txt;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("isCollapsed")
    @Expose
    private Boolean isCollapsed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getIsCollapsed() {
        return isCollapsed;
    }

    public void setIsCollapsed(Boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }

}
