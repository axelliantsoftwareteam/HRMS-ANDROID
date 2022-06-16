package com.example.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.LeaveRequestModel.LeavesRequest;
import com.example.hrm.Model.SpinerModel.SpinerLeaves;
import com.example.hrm.Model.SpinerModel.SpinerResponse;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityAddLeaveBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLeave extends AppCompatActivity {

    private ActivityAddLeaveBinding binding;
    SessionManager sessionManager;
    String token;
    String ssdate, senddate,stype,scomments;

    DatePickerDialog picker;
    Spinner spinner;


  List<SpinerResponse> spinerResponses= new ArrayList<>();



    private static final String[] paths = {"Select a type", "Casual", "Sick", "Annual", "Short"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        transparentStatusAndNavigation();
        //  setContentView(R.layout.activity_add_leave);

        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLeave.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddLeave.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddLeave.this, More.class);
                startActivity(intent);
                finish();


            }
        });

//        binding.etTimeFrom.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(AddLeave.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//
//                        binding.etTimeFrom.setText(selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//
//            }
//        });
//        binding.etTimeEnd.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(AddLeave.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//
//                        binding.etEnddate.setText(selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//
//            }
//        });


        binding.etreason.getText().toString().trim();
        binding.etStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddLeave.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                binding.etStartdate.setText(dateString);


                                ssdate = binding.etStartdate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });


        binding.etEnddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddLeave.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                binding.etEnddate.setText(dateString);
                                senddate = binding.etEnddate.getText().toString().trim();
                            }
                        }, year, month, day);
                picker.show();

            }
        });


        sessionManager = new SessionManager(AddLeave.this);
        token = sessionManager.getToken();
      getspiner(token);


// add spinner
   spinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddLeave.this,
//                android.R.layout.simple_spinner_item, paths);
///        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//

        binding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postleave(token);
            }
        });


    }

    private void getspiner(final String access_token) {
        try {

            Call<SpinerLeaves> spinerLeavesCall = ApiHandler.getApiInterface().getspiner("Bearer " + access_token);
            spinerLeavesCall.enqueue(new Callback<SpinerLeaves>() {
                @Override
                public void onResponse(Call<SpinerLeaves> spinerLeavesCall1, Response<SpinerLeaves> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                spinerResponses = response.body().getData().getResponse();
                               // setspiner(spinerResponses);
                                //String array to store all the book names
                                String[] items = new String[spinerResponses.size()];

                                //Traversing through the whole list to get all the names
                                for(int i=0; i<spinerResponses.size(); i++){
                                    //Storing names to string array
                                    items[i] = spinerResponses.get(i).getValue();
                                }

                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(AddLeave.this, android.R.layout.simple_list_item_1, items);
                                //setting adapter to spinner
                                spinner.setAdapter(adapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        stype = parent.getItemAtPosition(position).toString();
                                        Toast.makeText(parent.getContext(),
                                                "" +stype,
                                                Toast.LENGTH_SHORT).show();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                        // sometimes you need nothing here
                                    }
                                });
                             //   Toast.makeText(AddLeave.this, ""+spinerResponses, Toast.LENGTH_SHORT).show();
                            }


                        } else {

                            Toast.makeText(AddLeave.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }



            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    private void postleave(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(AddLeave.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<LeavesRequest> leavesRequestCall = ApiHandler.getApiInterface().postleave("Bearer " + token,ApiMapJson());

            leavesRequestCall.enqueue(new Callback<LeavesRequest>() {
                @Override
                public void onResponse(Call<LeavesRequest> leavesRequestCall1, Response<LeavesRequest> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(AddLeave.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if(status==400)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(AddLeave.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                            else if(status==500)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(AddLeave.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(AddLeave.this, "" + msg, Toast.LENGTH_SHORT).show();

                            }
                        }

                        else
                        {
                            String msg = response.body().getMeta().getMessage();
                            Toast.makeText(AddLeave.this, ""+msg, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());

                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<LeavesRequest> call, Throwable t)
                {
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

    private JsonObject ApiMapJson() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("type", stype);
            jsonObj_.put("date", ssdate);
            jsonObj_.put("end_date", senddate);
            jsonObj_.put("comment", scomments);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return gsonObject;
    }



//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//    {


//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent)
//    {
//
//
//    }
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
        Intent i = new Intent(AddLeave.this, LeavesActivity.class);
        startActivity(i);
        finish();
    }


}