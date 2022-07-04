package com.example.hrm.Interface;


import com.example.hrm.Model.CalenderEventObjects;

/**
 * Created by akumar1 on 11/22/2017.
 */

public interface OnCalenderDayClickListener {
    void onDayClick(int position, String date, CalenderEventObjects eventObjects);
}
