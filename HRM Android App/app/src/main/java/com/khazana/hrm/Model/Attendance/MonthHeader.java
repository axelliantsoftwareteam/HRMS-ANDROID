
package com.khazana.hrm.Model.Attendance;

import com.google.gson.annotations.SerializedName;

public class MonthHeader {
    @SerializedName("day")
    private int day;

    @SerializedName("week_day")
    private String weekDay;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }
}
