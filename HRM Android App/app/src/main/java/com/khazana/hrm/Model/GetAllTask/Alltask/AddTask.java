package com.khazana.hrm.Model.GetAllTask.Alltask;

import com.google.gson.annotations.SerializedName;

public class AddTask {

    @SerializedName("assigned_to")
    private String assigned_to ;

    @SerializedName("name")
    private String name;
    @SerializedName("tag")
    private Integer tag;

    @SerializedName("due_date")
    private String due_date;

    @SerializedName("notes")
    private String notes;

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
