package com.example.hrm.Model.Attendance.FetchAttend;

import com.google.gson.annotations.SerializedName;

public class FetchAttend {



    @SerializedName("employee_list")
    private Integer employee_list ;

    @SerializedName("end_date")
    private String end_date;

    @SerializedName("start_date")
    private String start_date;
    public Integer getEmployee_list() {
        return employee_list;
    }

    public void setEmployee_list(Integer employee_list) {
        this.employee_list = employee_list;
    }


    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
}
