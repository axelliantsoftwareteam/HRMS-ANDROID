package com.example.hrm.Fragment.AttendanceReport;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hrm.R;

//import com.sickmartian.calendarview.CalendarView;
//import com.sickmartian.calendarview.MonthView;
//import com.sickmartian.calendarview.WeekView;


public class ClockOutFragment extends Fragment
{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_clock_out, container, false);


        return view;
    }

}