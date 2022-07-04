
package com.example.hrm.Model.Attendance;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Attendance_list {

    @SerializedName("check_in")
    @Expose
    private String checkIn;
    @SerializedName("check_out")
    @Expose
    private String checkOut;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("week_day")
    @Expose
    private String weekDay;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("early_out")
    @Expose
    private Boolean earlyOut;
    @SerializedName("is_late")
    @Expose
    private Boolean isLate;

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getEarlyOut() {
        return earlyOut;
    }

    public void setEarlyOut(Boolean earlyOut) {
        this.earlyOut = earlyOut;
    }

    public Boolean getIsLate() {
        return isLate;
    }

    public void setIsLate(Boolean isLate) {
        this.isLate = isLate;
    }

}
