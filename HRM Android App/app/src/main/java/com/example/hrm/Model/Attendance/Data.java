
package com.example.hrm.Model.Attendance;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Data {

    @SerializedName("attendance")
    @Expose
    private List<Attendance> attendance = null;
    @SerializedName("month_header")
    @Expose
    private List<MonthHeader> monthHeader = null;

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public List<MonthHeader> getMonthHeader() {
        return monthHeader;
    }

    public void setMonthHeader(List<MonthHeader> monthHeader) {
        this.monthHeader = monthHeader;
    }

}
