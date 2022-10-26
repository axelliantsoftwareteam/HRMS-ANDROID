package com.khazana.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.akshay.library.CurveBottomBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khazana.hrm.Adapter.Leaves.LeavesPagerAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.LeaveRequestModel.LeavesRequest;
import com.khazana.hrm.Model.SpinerModel.SpinerLeaves;
import com.khazana.hrm.Model.SpinerModel.SpinerResponse;
import com.khazana.hrm.R;
import com.khazana.hrm.Utility.SessionManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khazana.hrm.databinding.ActivityLeavesBinding;
import com.khazana.hrm.databinding.DialogAddleavesBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeavesActivity extends AppCompatActivity {

    private ActivityLeavesBinding binding;
    SessionManager session;
    private DialogAddleavesBinding dialogAddleavesBinding;
    String token, reason;
    String ssdate, senddate, stype;
    DatePickerDialog picker;

    List<SpinerResponse> spinerResponses = new ArrayList<>();


    private CurveBottomBar cbb;
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.menu_me:
                Intent intent = new Intent(LeavesActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_more:
                //  item.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.grey_10));
                Intent intentt = new Intent(LeavesActivity.this, More.class);
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

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        session = new SessionManager(LeavesActivity.this);
        token = session.getToken();

        //  transparentStatusAndNavigation();
//        setContentView(R.layout.activity_tasks);
        isNetworkConnectionAvailable();


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
               // Toast.makeText(LeavesActivity.this, "Home", Toast.LENGTH_SHORT).show();
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

        binding.icMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Intent intent = new Intent(LeavesActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.icMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LeavesActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });




        binding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   Toast.makeText(LeavesActivity.this, "Add Leave", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LeavesActivity.this, AddLeave.class);
//                startActivity(intent);
//                finish();
                add();
            }
        });
        cbb = findViewById(R.id.nav_view);
        cbb.setBottomBarColor(getResources().getColor(R.color.colorApp));
        cbb.setCurveRadius(90);
        cbb.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    private void add() {
        dialogAddleavesBinding = DialogAddleavesBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(LeavesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddleavesBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogAddleavesBinding.etStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LeavesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogAddleavesBinding.etStartdate.setText(dateString);
                                ssdate = dialogAddleavesBinding.etStartdate.getText().toString().trim();

                            }
                        }, year, month, day);


                picker.show();


            }
        });
        dialogAddleavesBinding.etEnddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LeavesActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogAddleavesBinding.etEnddate.setText(dateString);
                                senddate = dialogAddleavesBinding.etEnddate.getText().toString().trim();
                            }
                        }, year, month, day);
                picker.show();

            }
        });
        getspiner(token);

        dialogAddleavesBinding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogAddleavesBinding.etStartdate.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogAddleavesBinding.etStartdate.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                if (dialogAddleavesBinding.etEnddate.getText().toString().isEmpty()) {

                    // editText is empty
                    dialogAddleavesBinding.etEnddate.setError("The item cannot be empty ");
//                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }


                if (dialogAddleavesBinding.etreason.getText().toString().isEmpty()) {

                    // editText is empty
                    dialogAddleavesBinding.etreason.setError("The item cannot be empty ");
//                    Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    reason = dialogAddleavesBinding.etreason.getText().toString().trim();
                    // editText is not empty
                    postleave(token);
                    dialog.dismiss();
                }


//                    Log.e("Tag", "job="+addExperience.getJobDescription());
//                    Log.e("Tag", "lastsalary="+addExperience.getLastSalary());
//                    Log.e("Tag", "design="+addExperience.getDesignation());
//                    Log.e("Tag", "company="+addExperience.getCompany());
//                    Log.e("Tag", "resign date="+addExperience.getResignDate());




            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void postleave(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(LeavesActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<LeavesRequest> leavesRequestCall = ApiHandler.getApiInterface().postleave("Bearer " + token, ApiMapJson());

            leavesRequestCall.enqueue(new Callback<LeavesRequest>() {
                @Override
                public void onResponse(Call<LeavesRequest> leavesRequestCall1, Response<LeavesRequest> response) {

                    try {

                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(LeavesActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LeavesActivity.this, LeavesActivity.class);
                                startActivity(intent);
                                finish();
                                dialog.dismiss();
                            } else if (status == 400)
                            {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(LeavesActivity.this, "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(LeavesActivity.this, LeavesActivity.class);
                                startActivity(intent);
                                finish();

                            } else if (status == 401) {
                                Toast.makeText(LeavesActivity.this, "Your session is expired please login again ", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                session.logoutUser();
                                Intent intent = new Intent(LeavesActivity.this, SignIn.class);
                                startActivity(intent);
                                finish();

                            }

                            else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(LeavesActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            String msg = response.body().getMeta().getMessage();
                            Toast.makeText(LeavesActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(LeavesActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<LeavesRequest> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(LeavesActivity.this, "Internal server error!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }

                }


            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private JsonObject ApiMapJson() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("type", stype);
            jsonObj_.put("date", ssdate);
            jsonObj_.put("end_date", senddate);
            jsonObj_.put("comment", reason);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


    private void getspiner(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(LeavesActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<SpinerLeaves> spinerLeavesCall = ApiHandler.getApiInterface().getspiner("Bearer " + access_token);
            spinerLeavesCall.enqueue(new Callback<SpinerLeaves>() {
                @Override
                public void onResponse(Call<SpinerLeaves> spinerLeavesCall1, Response<SpinerLeaves> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                spinerResponses = response.body().getData().getResponse();
                                // setspiner(spinerResponses);
                                //String array to store all the book names
                                String[] items = new String[spinerResponses.size()];

                                //Traversing through the whole list to get all the names
                                for (int i = 0; i < spinerResponses.size(); i++) {
                                    //Storing names to string array
                                    items[i] = spinerResponses.get(i).getValue();
                                }

                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(LeavesActivity.this, android.R.layout.simple_list_item_1, items);
                                //setting adapter to spinner
                                dialogAddleavesBinding.spinner.setAdapter(adapter);
                                dialog.dismiss();
                                dialogAddleavesBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        stype = parent.getItemAtPosition(position).toString();

//                                        Toast.makeText(parent.getContext(),
//                                                "" +stype,
//                                                Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                        // sometimes you need nothing here
                                    }
                                });
                                //   Toast.makeText(LeavesActivity.this, ""+spinerResponses, Toast.LENGTH_SHORT).show();
                            }
                            else if (status == 400)
                            {
                                Toast.makeText(LeavesActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if (status == 401) {

                                Toast.makeText(LeavesActivity.this, "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                session.logoutUser();
                                Intent intent = new Intent(LeavesActivity.this, SignIn.class);
                                startActivity(intent);
                                finish();
                            }


                        } else {

                            Toast.makeText(LeavesActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());


                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<SpinerLeaves> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        dialog.dismiss();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }


            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    private void tab() {

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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LeavesActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
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
    public void onResume() {
        super.onResume();
        tab();
        //   Toast.makeText(this, "tab_activity", Toast.LENGTH_SHORT).show();
        // do what you need to do if your activity resumes
    }

}