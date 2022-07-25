package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityUserProfBinding;

public class UserProfActivity extends AppCompatActivity {

    private ActivityUserProfBinding binding;
    SessionManager sessionManager;
    String name,email,dob,cnic,profimage,depart,urdatejoin,reprtto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_prof);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
      //  transparentStatusAndNavigation();
        isNetworkConnectionAvailable();

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

}