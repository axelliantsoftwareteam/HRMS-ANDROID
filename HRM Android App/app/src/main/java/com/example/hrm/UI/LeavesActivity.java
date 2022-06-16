package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hrm.Adapter.Leaves.LeavesPagerAdapter;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityLeavesBinding;
import com.google.android.material.tabs.TabLayout;

public class LeavesActivity extends AppCompatActivity {

    private ActivityLeavesBinding binding;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        transparentStatusAndNavigation();
//        setContentView(R.layout.activity_tasks);

        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeavesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeavesActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LeavesActivity.this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LeavesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeavesActivity.this, More.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(LeavesActivity.this, "Add Leave", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LeavesActivity.this, AddLeave.class);
                startActivity(intent);
                finish();
            }
        });


        // start Display the the leaves tabs

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("All Leaves"));
        tabLayout.addTab(tabLayout.newTab().setText("Pending"));
        tabLayout.addTab(tabLayout.newTab().setText("Approved"));
        tabLayout.addTab(tabLayout.newTab().setText("Rejected"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final LeavesPagerAdapter adapter = new LeavesPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
// end Display the the leaves tabs

    }

    // start Navigation start transparent
    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.parseColor("#b38e34"));
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    // end Navigation  transparent
    @Override
    public void onBackPressed() {
        Intent i = new Intent(LeavesActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }
}