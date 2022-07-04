package com.example.hrm.Fragment.AttendanceReport;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrm.Adapter.CustomAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Interface.OnCalenderDayClickListener;
import com.example.hrm.Model.CalenderEventObjects;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Model.SpinerModel.SpinerLeaves;
import com.example.hrm.Model.SpinerModel.SpinerResponse;
import com.example.hrm.R;
import com.example.hrm.UI.AddLeave;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.widget.CalendarCustomView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ClockOutFragment extends Fragment
{


    TextView startdate, enddate;
    String ssdate,sedate,sname;
    DatePickerDialog picker;
    Spinner spinner;
    SessionManager sessionManager;
    String token;
    List<MemberResponse> memberResponses= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)   {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_clock_out, container, false);



        startdate=view.findViewById(R.id.etsdate);
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                startdate.setText(dateString);


                                ssdate = startdate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });


        enddate=view.findViewById(R.id.etldate);
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                enddate.setText(dateString);
                                sedate = enddate.getText().toString().trim();
                            }
                        }, year, month, day);
                picker.show();

            }
        });


        spinner = view.findViewById(R.id.spinnerlist);
        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
        getspiner(token);
        return view;
    }

    private void getspiner(final String access_token) {
        try {

            Call<Members> membersCall = ApiHandler.getApiInterface().getlistMember("Bearer " + access_token);
            membersCall.enqueue(new Callback<Members>() {
                @Override
                public void onResponse(Call<Members> membersCall1, Response<Members> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                memberResponses = response.body().getData().getResponse();
                                // setspiner(memberResponses);
                                //String array to store all the book names
                                String[] items = new String[memberResponses.size()];

                                //Traversing through the whole list to get all the names
                                for(int i=0; i<memberResponses.size(); i++){
                                    //Storing names to string array
                                    items[i] = memberResponses.get(i).getName();
                                }

                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                                //setting adapter to spinner
                                spinner.setAdapter(adapter);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                        sname = parent.getItemAtPosition(position).toString();


                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                        // sometimes you need nothing here
                                    }
                                });
                                //   Toast.makeText(AddLeave.this, ""+memberResponses, Toast.LENGTH_SHORT).show();
                            }


                        }
                        else {

                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<Members> call, Throwable t) {
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


}