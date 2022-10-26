package com.khazana.hrm.Interface;

import android.view.View;

import com.khazana.hrm.Model.Attendance.Attendance;

public interface AttendanceItemClickListener {

    void onItemClick(View view, int position, Attendance attendance);
}
