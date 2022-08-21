
package com.example.hrm.Model.Calender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class GetCalenderInfoData {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("attendees")
    @Expose
    private List<Attendee> attendees = null;
    @SerializedName("meta")
    @Expose
    private String meta;
    @SerializedName("start")
    @Expose
    private Start start;
    @SerializedName("end")
    @Expose
    private End end;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public End getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }

}
