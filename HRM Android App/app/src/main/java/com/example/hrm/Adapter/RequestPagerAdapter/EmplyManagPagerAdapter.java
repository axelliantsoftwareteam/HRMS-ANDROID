package com.example.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hrm.Fragment.EmployeeManagment.EmployeeFragment;
import com.example.hrm.Fragment.EmployeeManagment.WorkFlowFragment;
import com.example.hrm.Fragment.Payroll.EobiFragment;
import com.example.hrm.Fragment.Payroll.HealthSlabsFragment;
import com.example.hrm.Fragment.Payroll.PayrollStructureFragment;
import com.example.hrm.Fragment.Payroll.TaxSlabsFragment;


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

            case 1:
                EmployeeFragment tab2 = new EmployeeFragment();
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