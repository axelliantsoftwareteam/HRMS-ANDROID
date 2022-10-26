package com.khazana.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khazana.hrm.Fragment.BasicSetup.BuildingsFragment;
import com.khazana.hrm.Fragment.BasicSetup.DepartmentFragment;
import com.khazana.hrm.Fragment.BasicSetup.DesignationFragment;
import com.khazana.hrm.Fragment.BasicSetup.GroupsFragment;
import com.khazana.hrm.Fragment.BasicSetup.RolesFragment;
import com.khazana.hrm.Fragment.BasicSetup.ShiftsFragment;
import com.khazana.hrm.Fragment.BasicSetup.SkillsFragment;
import com.khazana.hrm.Fragment.BasicSetup.StaticDataFragment;
import com.khazana.hrm.Fragment.BasicSetup.SystemHolidaysFragment;


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
                SkillsFragment tab3 = new SkillsFragment();
                return tab3;
            case 3:
                ShiftsFragment tab4 = new ShiftsFragment();
                return tab4;
//            case 4:
//                DesignationFragment tab5 =new DesignationFragment();
//                return tab5;
            case 5:
                BuildingsFragment tab6 = new BuildingsFragment();
                return tab6;
            case 6:
                RolesFragment tab7 = new RolesFragment();
                return tab7;
            case 7:
                DepartmentFragment tab8 = new DepartmentFragment();
                return tab8;
            case 8:
                GroupsFragment tab9 = new GroupsFragment();
                return tab9;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}