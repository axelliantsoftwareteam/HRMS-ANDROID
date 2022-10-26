package com.khazana.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khazana.hrm.Fragment.Request.AttendanceRequestFragment;
import com.khazana.hrm.Fragment.Request.EquipmentsFragment;
import com.khazana.hrm.Fragment.Request.GeneralRequestFragment;
import com.khazana.hrm.Fragment.Request.LeaveRequestFragment;


public class RequestPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int code = 1;
    public RequestPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                LeaveRequestFragment tab1 = new LeaveRequestFragment();

                return tab1;

            case 1:
                AttendanceRequestFragment tab2 = new AttendanceRequestFragment();
                return tab2;
            case 2:
                EquipmentsFragment tab3 = new EquipmentsFragment();
                return tab3;
            case 3:
                GeneralRequestFragment tab4 = new GeneralRequestFragment();
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