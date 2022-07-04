
package com.example.hrm.Model.Attendance;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MonthHeader {

    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("week_day")
    @Expose
    private String weekDay;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

}
