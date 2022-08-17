package com.example.hrm.Interface;

import android.view.View;

import com.example.hrm.Model.Attendance.Attendance;

public interface AttendanceItemClickListener {

    void onItemClick(View view, int position, Attendance attendance);
}
