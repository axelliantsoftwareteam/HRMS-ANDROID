package com.khazana.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.akshay.library.CurveBottomBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khazana.hrm.Adapter.RequestPagerAdapter.StaticDataPagerAdapter;
import com.khazana.hrm.R;
import com.google.android.material.tabs.TabLayout;
import com.khazana.hrm.databinding.ActivityBasicSetupBinding;

public class BasicSetupActivity extends AppCompatActivity {

    private ActivityBasicSetupBinding binding;

    private CurveBottomBar cbb;
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.menu_me:
                Intent intent = new Intent(BasicSetupActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_more:
                //  item.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.grey_10));
                Intent intentt = new Intent(BasicSetupActivity.this, More.class);
                startActivity(intentt);
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
//        setContentView(R.layout.activity_basic_setup);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        isNetworkConnectionAvailable();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.basictab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Static Data"));
        tabLayout.addTab(tabLayout.newTab().setText("System Holiday"));
        tabLayout.addTab(tabLayout.newTab().setText("Skills"));
        tabLayout.addTab(tabLayout.newTab().setText("Shifts"));
       // tabLayout.addTab(tabLayout.newTab().setText("Designation"));
        tabLayout.addTab(tabLayout.newTab().setText("Building"));
        tabLayout.addTab(tabLayout.newTab().setText("Roles"));
        tabLayout.addTab(tabLayout.newTab().setText("Department"));
        tabLayout.addTab(tabLayout.newTab().setText("Groups"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_AUTO);
        tabLayout.setSelectedTabIndicatorHeight(10);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.basicpager);
        final StaticDataPagerAdapter adapter = new StaticDataPagerAdapter
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
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicSetupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicSetupActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(BasicSetupActivity.this, "Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BasicSetupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BasicSetupActivity.this, More.class);
                startActivity(intent);
                finish();
            }
        });

        cbb = findViewById(R.id.nav_view);
        cbb.setBottomBarColor(getResources().getColor(R.color.colorApp));
        cbb.setCurveRadius(90);
        cbb.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }


    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }
    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(BasicSetupActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }


}