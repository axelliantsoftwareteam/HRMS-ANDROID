package com.khazana.hrm.Model.Attendance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttendancePost {

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    public List<Integer> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Integer> employeeList) {
        this.employeeList = employeeList;
    }

    @SerializedName("employee_list")
    private List<Integer> employeeList;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


}
