package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hrm.Adapter.RequestPagerAdapter.EmplyManagPagerAdapter;
import com.example.hrm.Adapter.RequestPagerAdapter.PayRollPagerAdapter;
import com.example.hrm.R;
import com.example.hrm.databinding.ActivityEmployeeBinding;
import com.example.hrm.databinding.ActivityEmplyManagBinding;
import com.example.hrm.databinding.ActivityPayrollBinding;
import com.google.android.material.tabs.TabLayout;

public class EmplyManagActivity extends AppCompatActivity {

    private ActivityEmplyManagBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        
//        setContentView(R.layout.activity_emply_manag);
//        
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Work Flow"));
        tabLayout.addTab(tabLayout.newTab().setText("Employee"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
        tabLayout.setSelectedTabIndicatorHeight(10);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.applpager);
        final EmplyManagPagerAdapter adapter = new EmplyManagPagerAdapter
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
            public void onTabUnselected(TabLayout.Tab tab)
            {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {


            }
        });
        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(EmplyManagActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(EmplyManagActivity.this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EmplyManagActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(EmplyManagActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmplyManagActivity.this, More.class);
                startActivity(i);
                finish();
            }
        });
        
    }
    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(EmplyManagActivity.this, More.class);
        startActivity(i);
        finish();

    }
}