package com.example.myapplication.Adapter.Attendance;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.Fragment.AttendanceReport.ClockInFragment;
import com.example.myapplication.Fragment.AttendanceReport.ClockOutFragment;
import com.example.myapplication.Fragment.AttendanceReport.RequestInFragment;

public class AttendancPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public AttendancPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                ClockInFragment tab1 = new ClockInFragment();
                return tab1;

            case 1:
                ClockOutFragment tab2 = new ClockOutFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
