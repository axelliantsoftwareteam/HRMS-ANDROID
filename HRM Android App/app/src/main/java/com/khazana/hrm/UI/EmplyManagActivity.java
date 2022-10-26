package com.khazana.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.akshay.library.CurveBottomBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khazana.hrm.Adapter.RequestPagerAdapter.EmplyManagPagerAdapter;
import com.khazana.hrm.R;
import com.google.android.material.tabs.TabLayout;
import com.khazana.hrm.databinding.ActivityEmplyManagBinding;

public class EmplyManagActivity extends AppCompatActivity {

    private ActivityEmplyManagBinding binding;

    private CurveBottomBar cbb;

    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {

            case R.id.menu_more:
//                  item.getIcon().setTint(ContextCompat.getColor(UserProfActivity.this, R.color.white));
                Intent intentt = new Intent(EmplyManagActivity.this, More.class);
                startActivity(intentt);
                finish();
                return true;
            case R.id.menu_me:
                Intent intent = new Intent(EmplyManagActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return false;
    };

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
//        tabLayout.addTab(tabLayout.newTab().setText("Employee"));
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
              //  Toast.makeText(EmplyManagActivity.this, "Home", Toast.LENGTH_SHORT).show();
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



        cbb = findViewById(R.id.nav_view);
        cbb.setBottomBarColor(getResources().getColor(R.color.colorApp));
        cbb.setCurveRadius(90);
        cbb.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




    }
    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(EmplyManagActivity.this, More.class);
        startActivity(i);
        finish();

    }
}