package com.example.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hrm.Fragment.BasicSetup.BuildingsFragment;
import com.example.hrm.Fragment.BasicSetup.DepartmentFragment;
import com.example.hrm.Fragment.BasicSetup.DesignationFragment;
import com.example.hrm.Fragment.BasicSetup.GroupsFragment;
import com.example.hrm.Fragment.BasicSetup.ResumeFragment;
import com.example.hrm.Fragment.BasicSetup.RolesFragment;
import com.example.hrm.Fragment.BasicSetup.ShiftsFragment;
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
            case 4:
                ShiftsFragment tab5 = new ShiftsFragment();
                return tab5;
            case 5:
                DesignationFragment tab6 = new DesignationFragment();
                return tab6;
            case 6:
                BuildingsFragment tab7 = new BuildingsFragment();
                return tab7;
            case 7:
                RolesFragment tab8 = new RolesFragment();
                return tab8;
            case 8:
                DepartmentFragment tab9 = new DepartmentFragment();
                return tab9;
            case 9:
                GroupsFragment tab10 = new GroupsFragment();
                return tab10;








            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}