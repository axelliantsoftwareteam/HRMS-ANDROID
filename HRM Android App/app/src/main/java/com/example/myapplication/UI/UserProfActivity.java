package com.example.myapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.Utility.SessionManager;
import com.example.myapplication.databinding.ActivityUserProfBinding;

public class UserProfActivity extends AppCompatActivity {

    private ActivityUserProfBinding binding;
    SessionManager sessionManager;
    String name,email,dob,cnic,profimage,depart,urdatejoin,reprtto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_prof);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        transparentStatusAndNavigation();

        // setting the session
        sessionManager= new SessionManager(UserProfActivity.this);

        // end session setting

      //  getting the value from sessionManager in to string

        name = sessionManager.getFirstName();
        binding.txtName.setText(name);

        name = sessionManager.getFirstName();
        binding.txtUname.setText(name);


        email =sessionManager.getUserEmail();
        binding.txtEmail.setText(email);

        email =sessionManager.getUserEmail();
        binding.txtUemail.setText(email);


        dob = sessionManager.getDob();
        binding.txtDob.setText(dob);

        cnic = sessionManager.getCinic();
        binding.txtCnic.setText(cnic);


        depart = sessionManager.getDepartment();
        binding.txtDepart.setText(depart);

        urdatejoin =sessionManager.getUserDateJoined();
        binding.txtUrjoin.setText(urdatejoin);

        reprtto=sessionManager.getReportTo();
        binding.txtRepto.setText(reprtto);



        profimage =sessionManager.getProfimg();
//        Glide.with(this)
//                .load(Base64.decode(profimage, Base64.DEFAULT))
//                .into(binding.image);

        Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).dontAnimate().into(binding.image);;



        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                Intent intent = new Intent(UserProfActivity.this, UserProfile.class);
//                startActivity(intent);
//                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserProfActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(UserProfActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });


    }
    //navigation transparent
    //start

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
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS ,false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
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
    //navigation transparent
    //end
    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(UserProfActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }
}