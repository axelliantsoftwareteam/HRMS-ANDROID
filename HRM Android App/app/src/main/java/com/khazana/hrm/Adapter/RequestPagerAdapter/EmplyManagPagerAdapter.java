package com.khazana.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khazana.hrm.Fragment.EmployeeManagment.WorkFlowFragment;


public class EmplyManagPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int code = 1;
    public EmplyManagPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                WorkFlowFragment tab1 = new WorkFlowFragment();

                return tab1;

//            case 1:
//                EmployeeFragment tab2 = new EmployeeFragment();
//                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}