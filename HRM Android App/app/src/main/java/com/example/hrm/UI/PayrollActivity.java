package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.hrm.Adapter.RequestPagerAdapter.PayRollPagerAdapter;
import com.example.hrm.R;
import com.example.hrm.databinding.ActivityApprovalBinding;
import com.example.hrm.databinding.ActivityPayrollBinding;
import com.google.android.material.tabs.TabLayout;

public class PayrollActivity extends AppCompatActivity {


    private ActivityPayrollBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
//        setContentView(R.layout.activity_payroll);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tax Slabs"));
        tabLayout.addTab(tabLayout.newTab().setText("Health Slabs"));
        tabLayout.addTab(tabLayout.newTab().setText("EOBI Slabs"));
        tabLayout.addTab(tabLayout.newTab().setText("Payroll Structure"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
        tabLayout.setSelectedTabIndicatorHeight(10);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.applpager);
        final PayRollPagerAdapter adapter = new PayRollPagerAdapter
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
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PayrollActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(PayrollActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PayrollActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PayrollActivity.this, More.class);
                startActivity(i);
                finish();
            }
        });
    }
}