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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityMainBinding;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    private ActivityMainBinding binding;
    SessionManager session;
    String name,profimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        isNetworkConnectionAvailable();
        transparentStatusAndNavigation();

        session = new SessionManager(MainActivity.this);
        name = session.getFirstName();
        binding.txtName.setText(name);
       // profimage = session.getProfimg();

     //   Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).dontAnimate().into(binding.profileimage);;
      //  Glide.with(this).load(Base64.decode(profimage,Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).error(R.drawable.ic_baseline_account_circle_24).into(binding.profileimage);

        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });


        binding.lytask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, TasksActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.lyLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, LeavesActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.attRep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CheckRequest.class);
                startActivity(intent);
                finish();
               // Toast.makeText(MainActivity.this, "Getting Attendance Report", Toast.LENGTH_SHORT).show();


            }
        });




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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}