
package com.example.hrm.Model.Attendance;

import com.google.gson.annotations.SerializedName;

public class Attendance_list {

    @SerializedName("check_in")
    private String checkIn;

    @SerializedName("check_out")
    private String checkOut;

    @SerializedName("type")
    private int type;

    @SerializedName("date")
    private String date;

    @SerializedName("week_day")
    private String weekDay;

    @SerializedName("status")
    private String status;

    @SerializedName("early_out")
    private boolean earlyOut;

    @SerializedName("is_late")
    private boolean isLate;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public boolean isEarlyOut() {
        return earlyOut;
    }

    public void setEarlyOut(boolean earlyOut) {
        this.earlyOut = earlyOut;
    }

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }
}
