package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hrm.Adapter.CustomAdapter;
import com.example.hrm.Fragment.Tasks.AlltaskFragment;
import com.example.hrm.Fragment.Tasks.CompletedFragment;
import com.example.hrm.Fragment.Tasks.PendingFragment;
import com.example.hrm.Fragment.Tasks.SevendaysFragment;
import com.example.hrm.Fragment.Tasks.TodayFragment;
import com.example.hrm.Interface.OnCalenderDayClickListener;
import com.example.hrm.Model.CalenderEventObjects;
import com.example.hrm.R;
import com.example.hrm.databinding.ActivityCalendarBinding;
import com.example.hrm.databinding.DialogAdddispmemberstdtBinding;
import com.example.hrm.databinding.DialogAddnewvaluestdtBinding;
import com.example.hrm.databinding.DialogCalendercreateBinding;
import com.example.hrm.widget.CalendarCustomView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements OnCalenderDayClickListener {


    List<CalenderEventObjects> mEvents;
    RecyclerView eventsView;
    CustomAdapter mAdapter;


    private static final String[] eventtype = {"Task", "Event"};

    String stime,etime,descrpt,eventname, selected_val;


    private ActivityCalendarBinding binding;

    private DialogCalendercreateBinding dialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // setContentView(R.layout.activity_tasks);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        isNetworkConnectionAvailable();



        CalendarCustomView mView = (CalendarCustomView) findViewById(R.id.custom_calendar);

        mEvents = new ArrayList<>();
        mAdapter = new CustomAdapter(this);
        eventsView = findViewById(R.id.eventsView);
        eventsView.setHasFixedSize(true);
        eventsView.setLayoutManager(new LinearLayoutManager(this));
        eventsView.setAdapter(mAdapter);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dtStart = "2022-07-15";
            String dtStart1 = "2022-07-17";
            String dtStart2 = "2022-06-17";
            String dtStart3 = "2022-07-1";
            String dtStart4 = "2022-08-4";
            Date date = format.parse(dtStart);
            Date date1 = format.parse(dtStart1);
            Date date2 = format.parse(dtStart2);
            Date date3 = format.parse(dtStart3);
            Date date4 = format.parse(dtStart4);

            CalenderEventObjects event1 = new CalenderEventObjects("Test", "test", date);
            CalenderEventObjects event2 = new CalenderEventObjects("Meeting", "test", date1);
            CalenderEventObjects event3 = new CalenderEventObjects("Meeting", "test", date2);
            CalenderEventObjects event4 = new CalenderEventObjects("Meeting", "test", date3);
            CalenderEventObjects event5 = new CalenderEventObjects("Meeting", "test", date4);
            mEvents.add(event1);
            mEvents.add(event2);
            mEvents.add(event3);
            mEvents.add(event4);
            mEvents.add(event5);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        mView.loadCalender(mEvents,this);


        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }
    @Override
    public void onDayClick(int position, String date, CalenderEventObjects eventObjects) {
        mAdapter.clearItem();
        if (eventObjects != null)
        {
            Log.e("eventObjects",""+eventObjects);
            mAdapter.addItem(eventObjects);
        }
        else
        {
            addevent();
            Toast.makeText(this, "" + date, Toast.LENGTH_SHORT).show();
        }
    }


    private void addevent()
        {

            dialogBinding = DialogCalendercreateBinding.inflate(getLayoutInflater());

            final Dialog dialog = new Dialog(CalendarActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(dialogBinding.getRoot());
            dialog.setCancelable(true);


            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;




            ArrayAdapter<String> eventadapter = new ArrayAdapter<String>(CalendarActivity.this,
                    android.R.layout.simple_spinner_item, eventtype);
            eventadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dialogBinding.eventspinner.setAdapter(eventadapter);
            dialogBinding.eventspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3)
                {

                     selected_val=dialogBinding.eventspinner.getSelectedItem().toString();
                    Toast.makeText(CalendarActivity.this, ""+selected_val, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0)
                {
                    // TODO Auto-generated method stub

                }
            });

            dialogBinding.etstrtime.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                        {
                            Date dt = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                            String time1 = sdf.format(dt);
                            dialogBinding.etstrtime.setText(time1);

                            stime = dialogBinding.etstrtime.getText().toString().trim();
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
                    mTimePicker = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                        {
                            Date dt = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                            String time1 = sdf.format(dt);
                            dialogBinding.etendtime.setText(time1);

                            etime = dialogBinding.etendtime.getText().toString().trim();
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });





            dialogBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    descrpt=dialogBinding.etdescrpt.getText().toString();
                    eventname=dialogBinding.eteventname.getText().toString();

                    //  AddReq(token);
                    dialog.dismiss();

                }
            });

            dialogBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    //  AddReq(token);
                    dialog.dismiss();

                }
            });






            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(CalendarActivity.this, MainActivity.class);
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