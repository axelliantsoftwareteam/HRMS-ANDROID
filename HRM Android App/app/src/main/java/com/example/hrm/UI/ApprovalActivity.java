package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hrm.Adapter.RequestPagerAdapter.ApprovalPagerAdapter;
import com.example.hrm.Adapter.RequestPagerAdapter.RequestPagerAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityApprovalBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalActivity extends AppCompatActivity {


    private ActivityApprovalBinding binding;

    SessionManager sessionManager;
    String token;
    List<MemberResponse> memberResponses= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        sessionManager = new SessionManager(ApprovalActivity.this);
//        token = sessionManager.getToken();
//        getmember(token);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Attendance Approval"));
        tabLayout.addTab(tabLayout.newTab().setText("Leave Approval"));
        tabLayout.addTab(tabLayout.newTab().setText("General Request Approval"));
        tabLayout.addTab(tabLayout.newTab().setText("Equipment Approval"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
        tabLayout.setSelectedTabIndicatorHeight(10);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.applpager);
        final ApprovalPagerAdapter adapter = new ApprovalPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

}