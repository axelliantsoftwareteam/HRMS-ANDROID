
package com.example.hrm.Model.Attendance;

import java.util.List;
import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Attendance {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("attendance_list")
    @Expose
    private List<Attendance_list> attendanceList = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
