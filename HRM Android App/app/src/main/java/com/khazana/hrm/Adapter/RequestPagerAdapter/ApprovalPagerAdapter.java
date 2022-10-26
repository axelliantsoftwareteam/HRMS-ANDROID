package com.khazana.hrm.Adapter.RequestPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khazana.hrm.Fragment.Approval.AttendanceApprovalFragment;
import com.khazana.hrm.Fragment.Approval.EquipmentApprovalFragment;
import com.khazana.hrm.Fragment.Approval.GeneralApprovalFragment;
import com.khazana.hrm.Fragment.Approval.LeaveApprovalFragment;


public class ApprovalPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int code = 1;
    public ApprovalPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                AttendanceApprovalFragment tab1 = new AttendanceApprovalFragment();

                return tab1;

            case 1:
                LeaveApprovalFragment tab2 = new LeaveApprovalFragment();
                return tab2;
            case 2:
                GeneralApprovalFragment tab3 = new GeneralApprovalFragment();
                return tab3;
            case 3:
                EquipmentApprovalFragment tab4 = new EquipmentApprovalFragment();
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