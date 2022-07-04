package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.hrm.Adapter.CustomAdapter;
import com.example.hrm.Interface.OnCalenderDayClickListener;
import com.example.hrm.Model.CalenderEventObjects;
import com.example.hrm.R;
import com.example.hrm.widget.CalendarCustomView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity implements OnCalenderDayClickListener {


    List<CalenderEventObjects> mEvents;
    RecyclerView eventsView;
    CustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarCustomView mView = (CalendarCustomView) findViewById(R.id.custom_calendar);

        mEvents = new ArrayList<>();
        mAdapter = new CustomAdapter(this);
        eventsView = findViewById(R.id.list_eventsView);
        eventsView.setHasFixedSize(true);
        eventsView.setLayoutManager(new LinearLayoutManager(this));
        eventsView.setAdapter(mAdapter);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dtStart = "2022-06-15";
            String dtStart1 = "2022-06-17";
            Date date = format.parse(dtStart);
            Date date1 = format.parse(dtStart1);
            CalenderEventObjects event1 = new CalenderEventObjects("Hi 1", "aaaa", date);
            CalenderEventObjects event2 = new CalenderEventObjects("Hi 2", "dddddd", date1);
            mEvents.add(event1);
            mEvents.add(event2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mView.loadCalender(mEvents,this);
    }
    @Override
    public void onDayClick(int position, String date, CalenderEventObjects eventObjects) {
        mAdapter.clearItem();
        if (eventObjects != null) {
            Log.e("eventObjects",""+eventObjects);
            mAdapter.addItem(eventObjects);
        } else {
            Toast.makeText(this, "" + date, Toast.LENGTH_SHORT).show();
        }
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