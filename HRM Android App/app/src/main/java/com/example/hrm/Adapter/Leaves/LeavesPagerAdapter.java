package com.example.hrm.Adapter.Leaves;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hrm.Fragment.Leaves.AllLeavesFragment;
import com.example.hrm.Fragment.Leaves.ApprovedLeavesFragment;
import com.example.hrm.Fragment.Leaves.PendingLeavesFragment;
import com.example.hrm.Fragment.Leaves.RejectLeavesFragment;


public class LeavesPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    int code = 1;
    public LeavesPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                AllLeavesFragment tab1 = new AllLeavesFragment();

                return tab1;

            case 1:
                PendingLeavesFragment tab2 = new PendingLeavesFragment();
                return tab2;
            case 2:
                ApprovedLeavesFragment tab3 = new ApprovedLeavesFragment();
                return tab3;
            case 3:
                RejectLeavesFragment tab4 = new RejectLeavesFragment();
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