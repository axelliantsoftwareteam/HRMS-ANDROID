package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hrm.Adapter.Attendance.AttendancPagerAdapter;
import com.example.hrm.R;
import com.example.hrm.databinding.ActivityCheckRequestBinding;
import com.google.android.material.tabs.TabLayout;

public class CheckRequest extends AppCompatActivity {


    private ActivityCheckRequestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        transparentStatusAndNavigation();
//        setContentView(R.layout.activity_check_request);


        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckRequest.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckRequest.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CheckRequest.this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CheckRequest.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckRequest.this, More.class);
                startActivity(intent);
                finish();
            }
        });

        // start Display the the leaves tabs

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Daily"));
        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final AttendancPagerAdapter adapter = new AttendancPagerAdapter
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
// end Display the the leaves tabs

    }



    // start Navigation start transparent
    @SuppressLint("ResourceAsColor")
    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}