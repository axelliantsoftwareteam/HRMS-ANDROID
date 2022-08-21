package com.example.hrm.Model.Calender.AddEvent;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddEventCalender {

    @SerializedName("name")
    private String name ;

    @SerializedName("date")
    private String date;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    @SerializedName("type")
    private Integer type;

    @SerializedName("attendee")
    private String attendee;

    @SerializedName("notes")
    private String notes;


    @SerializedName("attendees")
    private List<String> attendees;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAttendee() {
        return attendee;
    }

    public void setAttendee(String attendee) {
        this.attendee = attendee;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<String> attendees) {
        this.attendees = attendees;
    }
}
