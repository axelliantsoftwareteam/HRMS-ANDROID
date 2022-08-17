
package com.example.hrm.Model.Attendance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("attendance")
    private List<Attendance> attendance = null;

    @SerializedName("month_header")
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
