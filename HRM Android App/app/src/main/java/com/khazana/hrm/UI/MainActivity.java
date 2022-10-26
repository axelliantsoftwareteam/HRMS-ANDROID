package com.khazana.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.akshay.library.CurveBottomBar;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khazana.hrm.R;
import com.khazana.hrm.Utility.SessionManager;
import com.khazana.hrm.databinding.ActivityMainBinding;
import com.nimbusds.jwt.SignedJWT;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements Serializable {

    private ActivityMainBinding binding;
    SessionManager session;
    String name, profimage, token, currentdate, exprietime, expdate;


    private CurveBottomBar cbb;
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.menu_me:
                Intent intent = new Intent(MainActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_more:
                //  item.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.grey_10));
                Intent intentt = new Intent(MainActivity.this, More.class);
                startActivity(intentt);
                finish();
                return true;
        }
        return false;
    };

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
        profimage = session.getProfimg();

        cbb = findViewById(R.id.nav_view);
        cbb.setBottomBarColor(getResources().getColor(R.color.colorAppDark));
        cbb.setCurveRadius(90);
        cbb.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //   Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).dontAnimate().into(binding.profileimage);;
        Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).error(R.drawable.ic_baseline_account_circle_24).into(binding.profileimage);
        token = session.getToken();
        Log.d("token", "" + token.toString());

        JWT parsedJWT = new JWT(token);
        Claim subscriptionMetaData = parsedJWT.getClaim("UserID");
        String parsedValue = subscriptionMetaData.asString();
        Claim subs = parsedJWT.getClaim("expiry_time");
        String expiry_time = subs.asString();
        session.saveUserID(parsedValue);
        session.saveOfexpire(expiry_time);



//        SignedJWT decodedJWT = null;
//        try {
//            decodedJWT = SignedJWT.parse(token);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String header = decodedJWT.getHeader().toString();
//        String payload = decodedJWT.getPayload().toString();
//        Log.d("body", "" + payload.toString());

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date currenTimeZone = (Date) calendar.getTime();
//        currentdate = sdf.format(currenTimeZone);
//        Log.d("currentdate", "" + currentdate.toString());
//        exprietime = session.getexpiretime();
//        Log.d("exprietime", "" + exprietime.toString());
//        long timestamp = Long.parseLong(exprietime);
//        expdate = getDateCurrentTimeZone(timestamp);
//        Log.d("expdate", "" + expdate.toString());


     //  checklogin(expdate, currentdate);


//        if (exprietime.compareTo(currentdate) < 0 )
//        {
//            session.logoutUser();
//            Intent intent = new Intent(MainActivity.this, SignIn.class);
//            startActivity(intent);
//            finish();
//            Toast.makeText(MainActivity.this, "out", Toast.LENGTH_SHORT).show();
//
//            //  0 comes when two date are same,
//            //  1 comes when date1 is higher then date2
//            // -1 comes when date1 is lower then date2
//
//        }
//
//        else
//        {
          //  Toast.makeText(MainActivity.this, "in", Toast.LENGTH_SHORT).show();

//            binding.me.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    Intent intent = new Intent(MainActivity.this, UserProfActivity.class);
//                    startActivity(intent);
//                    finish();
//
//
//                }
//            });
//            binding.icMe.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    Intent intent = new Intent(MainActivity.this, UserProfActivity.class);
//                    startActivity(intent);
//                    finish();
//
//
//                }
//            });
            binding.fltHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();


                }
            });
//            binding.more.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, More.class);
//                    startActivity(intent);
//                    finish();
//
//
//                }
//            });
//            binding.icMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, More.class);
//                    startActivity(intent);
//                    finish();
//
//
//                }
//            });


            binding.lytask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, TasksActivity.class);
                    startActivity(intent);
                    finish();


                }
            });

            binding.lyOrgano.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ApprovalActivity.class);
                    startActivity(intent);
                    finish();


                }
            });

            binding.lyattendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            binding.lyAward.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MicCalendarActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            binding.evaluation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EvalutionActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            binding.lyleave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LeavesActivity.class);
                    startActivity(intent);
                    finish();


                }
            });

            binding.lyrequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RequestActivity.class);
                    startActivity(intent);
                    finish();
                    // Toast.makeText(MainActivity.this, "Getting Attendance Report", Toast.LENGTH_SHORT).show();


                }
            });
            binding.attRep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CheckRequest.class);
                    startActivity(intent);
                    finish();
                    // Toast.makeText(MainActivity.this, "Getting Attendance Report", Toast.LENGTH_SHORT).show();


                }
            });
            binding.profileimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, EditprofActivity.class);
                    startActivity(intent);
                    finish();
                }
            });




      //  }


//        else if(exprietime.compareTo(currentdate) < 1)
//        {
//            Toast.makeText(MainActivity.this, "nothing", Toast.LENGTH_SHORT).show();
//
//        }
//        else if(exprietime.compareTo(currentdate) < -1)
//        {
//            Toast.makeText(MainActivity.this, "nothing", Toast.LENGTH_SHORT).show();
//
//        }

//        if (Objects.equals(exprietime,currentdate)) {
//            session.logoutUser();
//            Intent intent = new Intent(MainActivity.this, SignIn.class);
//            startActivity(intent);
//            finish();
//            Toast.makeText(MainActivity.this, "out", Toast.LENGTH_SHORT).show();
//            Log.d("intent", ""+exprietime.toString());
//
//        }
//        else
//        {
//            Toast.makeText(MainActivity.this, "nothing", Toast.LENGTH_SHORT).show();



     //   }

    }
//


    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
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