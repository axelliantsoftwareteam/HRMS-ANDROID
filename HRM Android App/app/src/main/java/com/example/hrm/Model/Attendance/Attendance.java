
package com.example.hrm.Model.Attendance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attendance {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("attendance_list")
    private List<Attendance_list> attendanceList = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attendance_list> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance_list> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
