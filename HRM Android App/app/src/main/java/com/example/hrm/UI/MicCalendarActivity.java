package com.example.hrm.UI;

import static java.util.Arrays.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hrm.Adapter.Approval.ApprovalAttendanceAdapter;
import com.example.hrm.Adapter.Calender.CalenderEventAdapter;
import com.example.hrm.Adapter.CustomAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Interface.OnCalenderDayClickListener;
import com.example.hrm.Model.Building.TaskAdded.TaskAddedModel;
import com.example.hrm.Model.Calender.AddEvent.AddCalender;
import com.example.hrm.Model.Calender.AddEvent.AddEventCalender;
import com.example.hrm.Model.Calender.GetCalenderInfo;
import com.example.hrm.Model.Calender.GetCalenderInfoData;
import com.example.hrm.Model.GetAllTask.Alltask.AddTask;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityCalendarBinding;
import com.example.hrm.databinding.ActivityMicCalendarBinding;
import com.example.hrm.databinding.DialogCalendercreateBinding;
import com.example.hrm.databinding.DialogEventdetailsBinding;
import com.example.hrm.databinding.DialogTaskcreateBinding;
import com.example.hrm.widget.CalendarCustomView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MicCalendarActivity extends AppCompatActivity implements OnCalenderDayClickListener {


    List<GetCalenderInfoData> mEvents = new ArrayList<>();
    RecyclerView eventsView;
    CustomAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    CalenderEventAdapter calenderEventAdapter;

    private DialogEventdetailsBinding dialogEventdetailsBinding;

    SessionManager sessionManager;
    String token;
    AddEventCalender addEventCalender = new AddEventCalender();
    TextView noleave;

    List<GetCalenderInfoData> getCalenderDataList = new ArrayList<>();


    private static final String[] eventtype = {"Task", "Event"};

    String stime, etime, descrpt, eventname, selected_val;

    private ActivityMicCalendarBinding binding;

    CalendarCustomView mView;

    private DialogCalendercreateBinding dialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mic_calendar);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        isNetworkConnectionAvailable();

//        setContentView(R.layout.activity_mic_calendar);


        sessionManager = new SessionManager(MicCalendarActivity.this);
        token = sessionManager.getToken();


        mView = (CalendarCustomView) findViewById(R.id.custom_calendar);

//        mAdapter = new CustomAdapter(this);
        eventsView = findViewById(R.id.eventsView);
        eventsView.setHasFixedSize(true);
        eventsView.setLayoutManager(new LinearLayoutManager(this));
        eventsView.setAdapter(mAdapter);

//       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//       try {
//            String dtStart = "2022-07-15";
//            String dtStart1 = "2022-07-17";
//           String dtStart2 = "2022-06-17";
//           String dtStart3 = "2022-07-1";
//           String dtStart4 = "2022-08-4";
//           Date date = format.parse(dtStart);
//           Date date1 = format.parse(dtStart1);
//          Date date2 = format.parse(dtStart2);
//           Date date3 = format.parse(dtStart3);
//            Date date4 = format.parse(dtStart4);
//
//           CalenderEventObjects event1 = new CalenderEventObjects("Test", "test", date);
//           CalenderEventObjects event2 = new CalenderEventObjects("Meeting", "test", date1);
//           CalenderEventObjects event3 = new CalenderEventObjects("Meeting", "test", date2);
//           CalenderEventObjects event4 = new CalenderEventObjects("Meeting", "test", date3);
//           CalenderEventObjects event5 = new CalenderEventObjects("Meeting", "test", date4);
//            mEvents.add(event1);
//           mEvents.add(event2);
//           mEvents.add(event3);
//           mEvents.add(event4);
//           mEvents.add(event5);
//
//        }
//        catch (ParseException e)
//        {
//            e.printStackTrace();
//        }

        getcalender(token);
        loadcalender(mEvents);


        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MicCalendarActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MicCalendarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MicCalendarActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MicCalendarActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onDayClick(int position, String date, GetCalenderInfoData eventObjects) {
        //  mAdapter.clearItem();
        if (eventObjects != null) {

            Log.e("eventObjects", "" + eventObjects);

            showCustomDialog(eventObjects);
            //  mAdapter.addItem(eventObjects);
        } else {
            addevent(date);
            Toast.makeText(this, "" + date, Toast.LENGTH_SHORT).show();
        }
    }

    public void getcalender(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(MicCalendarActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<GetCalenderInfo> getCalenderInfoCall = ApiHandler.getApiInterface().getCalenderinfo("Bearer " + access_token);
            getCalenderInfoCall.enqueue(new Callback<GetCalenderInfo>() {
                @Override
                public void onResponse(Call<GetCalenderInfo> getCalenderInfoCall1, Response<GetCalenderInfo> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                mEvents = response.body().getData().getResponse();

                                mAdapter = new CustomAdapter(MicCalendarActivity.this, mEvents);
                                eventsView.setAdapter(mAdapter);
                                binding.txtNo.setVisibility(View.GONE);
                                loadcalender(mEvents);
                                dialog.dismiss();

                            } else if (status == 400) {

                                binding.txtNo.setVisibility(View.VISIBLE);
                                binding.eventsView.setVisibility(View.GONE);

                                loadcalender(mEvents);
                                Toast.makeText(MicCalendarActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        } else {
                            loadcalender(mEvents);
                            Toast.makeText(MicCalendarActivity.this, "" + response.body().getData().getResponse(), Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<GetCalenderInfo> call, Throwable t) {
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

    private void loadcalender(List<GetCalenderInfoData> mEvents) {
        mView.loadCalender(mEvents, this);
    }


    private void addevent(String date) {


        dialogBinding = DialogCalendercreateBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(MicCalendarActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ArrayAdapter<String> eventadapter = new ArrayAdapter<String>(MicCalendarActivity.this,
                android.R.layout.simple_spinner_item, eventtype);
        eventadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogBinding.eventspinner.setAdapter(eventadapter);
        dialogBinding.eventspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                selected_val = dialogBinding.eventspinner.getSelectedItem().toString();

                if (selected_val.equals("Event")) {
                    int type = 1;
                    addEventCalender.setType(type);

                } else {

                    int type = 1;
                    addEventCalender.setType(type);


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

                // TODO Auto-generated method stub

            }
        });
        dialogBinding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogBinding.checkbox.isChecked()) {

                    dialogBinding.attendee.setVisibility(View.VISIBLE);
                    dialogBinding.eventype.setVisibility(View.GONE);


                } else {
                    dialogBinding.eventype.setVisibility(View.VISIBLE);
                    dialogBinding.attendee.setVisibility(View.GONE);
                }


            }
        });

        dialogBinding.etTag.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);

        List<String> items = new ArrayList<>();
        dialogBinding.etTag.setText(items);
        items.add(dialogBinding.etTag.toString());
        addEventCalender.setAttendees(items);


//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = format.format(calendar.getTime());
        addEventCalender.setDate(date);

        dialogBinding.etstrtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MicCalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm ");
                        String time1 = sdf.format(dt);
                        dialogBinding.etstrtime.setText(time1);

//                        stime = dialogBinding.etstrtime.getText().toString().trim();
                        addEventCalender.setStart(dialogBinding.etstrtime.getText().toString().trim());
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        dialogBinding.etendtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MicCalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String time1 = sdf.format(dt);
                        dialogBinding.etendtime.setText(time1);

//                        etime = dialogBinding.etendtime.getText().toString().trim();
                        addEventCalender.setEnd(dialogBinding.etendtime.getText().toString().trim());
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        addEventCalender.setAttendee(" ");


        dialogBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventCalender.setNotes(dialogBinding.etdescrpt.getText().toString());
                addEventCalender.setName(dialogBinding.eteventname.getText().toString());
                AddCalEvent(addEventCalender);
                Log.e("Tag", "data=" + addEventCalender.getDate());
                Log.e("Tag", "data=" + addEventCalender.getStart());
                Log.e("Tag", "data=" + addEventCalender.getType());
                Log.e("Tag", "data=" + addEventCalender.getAttendees());
                Log.e("Tag", "data=" + addEventCalender.getNotes());
                Log.e("Tag", "data=" + addEventCalender.getName());
                Log.e("Tag", "data=" + addEventCalender.getEnd());
                Log.e("Tag", "data=" + addEventCalender.getAttendee());


                dialog.dismiss();

            }
        });

        dialogBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  AddReq(token);

                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void AddCalEvent(AddEventCalender addEventCalender) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(MicCalendarActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<AddCalender> addCalenderCall = ApiHandler.getApiInterface().addeventcal("Bearer " + token, addEventCalender);
            addCalenderCall.enqueue(new Callback<AddCalender>() {
                @Override
                public void onResponse(Call<AddCalender> addCalenderCall1, Response<AddCalender> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Toast.makeText(MicCalendarActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MicCalendarActivity.this, MicCalendarActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (status == 400) {

                                Toast.makeText(MicCalendarActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else if (status == 401) {

                                Toast.makeText(MicCalendarActivity.this, "Enter Parameters are Invaild", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        } else {

                            Toast.makeText(MicCalendarActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<AddCalender> call, Throwable t) {
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


    private void showCustomDialog(GetCalenderInfoData eventObjects) {


        dialogEventdetailsBinding = DialogEventdetailsBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(MicCalendarActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEventdetailsBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogEventdetailsBinding.title.setText(eventObjects.getTitle());
        dialogEventdetailsBinding.startdate.setText(eventObjects.getStart().getDate());
        dialogEventdetailsBinding.starttime.setText(eventObjects.getStart().getTime());
        dialogEventdetailsBinding.enddate.setText(eventObjects.getEnd().getDate());
        dialogEventdetailsBinding.endtime.setText(eventObjects.getEnd().getTime());
        dialogEventdetailsBinding.tasknotes.setText(eventObjects.getMeta());


        dialogEventdetailsBinding.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(MicCalendarActivity.this, MainActivity.class);
        startActivity(i);
        finish();

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

}