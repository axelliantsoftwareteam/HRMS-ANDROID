package com.example.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hrm.Fragment.BasicSetup.ResumeFragment;
import com.example.hrm.Fragment.BasicSetup.SkillsFragment;
import com.example.hrm.Fragment.BasicSetup.StaticDataFragment;
import com.example.hrm.Fragment.BasicSetup.SystemHolidaysFragment;
import com.example.hrm.Fragment.Request.AttendanceRequestFragment;
import com.example.hrm.Fragment.Request.EquipmentsFragment;
import com.example.hrm.Fragment.Request.GeneralRequestFragment;
import com.example.hrm.Fragment.Request.LeaveRequestFragment;


public class StaticDataPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int code = 1;
    public StaticDataPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                StaticDataFragment tab1 = new StaticDataFragment();

                return tab1;

            case 1:
                SystemHolidaysFragment tab2 = new SystemHolidaysFragment();
                return tab2;
            case 2:
                ResumeFragment tab3 = new ResumeFragment();
                return tab3;
            case 3:
                SkillsFragment tab4 = new SkillsFragment();
                return tab4;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}