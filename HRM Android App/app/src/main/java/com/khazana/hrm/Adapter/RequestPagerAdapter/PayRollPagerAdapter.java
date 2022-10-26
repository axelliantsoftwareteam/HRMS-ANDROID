package com.khazana.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khazana.hrm.Fragment.Payroll.EobiFragment;
import com.khazana.hrm.Fragment.Payroll.HealthSlabsFragment;
import com.khazana.hrm.Fragment.Payroll.PayrollStructureFragment;
import com.khazana.hrm.Fragment.Payroll.TaxSlabsFragment;


public class PayRollPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int code = 1;
    public PayRollPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                TaxSlabsFragment tab1 = new TaxSlabsFragment();

                return tab1;

            case 1:
                HealthSlabsFragment tab2 = new HealthSlabsFragment();
                return tab2;
            case 2:
                EobiFragment tab3 = new EobiFragment();
                return tab3;
            case 3:
                PayrollStructureFragment tab4 = new PayrollStructureFragment();
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