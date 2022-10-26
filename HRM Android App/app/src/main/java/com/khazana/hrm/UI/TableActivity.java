package com.khazana.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.SearchView;
import android.widget.TableLayout;

import com.khazana.hrm.Adapter.ClubAdapter;
import com.khazana.hrm.Model.Attendance.Attendance;
import com.khazana.hrm.Model.Attendance.Attendance_list;
import com.khazana.hrm.Model.Attendance.MonthHeader;
import com.khazana.hrm.Model.Club;
import com.khazana.hrm.R;
import com.khazana.hrm.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    int scrollX = 0;

    List<Club> clubList = new ArrayList<>();

    RecyclerView rvClub;

    HorizontalScrollView headerScroll;

    SearchView searchView;

    ClubAdapter clubAdapter;

    List<Attendance> attendances= new ArrayList<>();
    List<MonthHeader> monthHeaders= new ArrayList<>();
    List<Attendance_list> attendance_listArrayList=new ArrayList<>();

    SessionManager sessionManager;
    String token;
    TableLayout stk;
    MonthHeader monthHeader =new MonthHeader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        sessionManager = new SessionManager(TableActivity.this);
        token = sessionManager.getToken();
         stk = (TableLayout) findViewById(R.id.table_main);
      //  GetAlldate(token);



    }

//    private void GetAlldate(final String access_token) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(TableActivity.this);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//            Call<AttendanceResponse> attendanceCall = ApiHandler.getApiInterface().getattend("Bearer " + access_token,ApiMapJson());
//            attendanceCall.enqueue(new Callback<AttendanceResponse>() {
//                @Override
//                public void onResponse(Call<AttendanceResponse> attendanceResponseCall, Response<AttendanceResponse> response) {
//
//                    try {
//                        if (response.isSuccessful()) {
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200)
//                            {
//                                monthHeaders = response.body().getData().getMonthHeader();
//
//                                attendances=response.body().getData().getAttendance();
//
//                                if (monthHeaders.size()==0)
//                                {
//                                    dialog.dismiss();
//
//                                }
//                                else if (monthHeaders!=null)
//                                {
//
//                                    for (int i =0 ;i<monthHeaders.size();i++)
//                                    {
//
//                                        monthHeader=monthHeaders.get(i);
//                                        TableRow tbrow0 = new TableRow(TableActivity.this);
//
//                                        TextView tv0 = new TextView(TableActivity.this);
//                                        tv0.setText(monthHeader.getWeekDay());
//                                        tv0.setTextColor(Color.WHITE);
//                                        tbrow0.addView(tv0);
//                                        stk.addView(tbrow0);
//
//                                    }
//                                    dialog.dismiss();
//
//                                }
//
//
//                                if (attendances.equals(null))
//                                {
//                                    dialog.dismiss();
//
//                                }
//                                else if (attendances!=null)
//                                {
//                                    for (int i =0 ;i<attendances.size();i++)
//                                    {
//                                        Attendance attendance= new Attendance();
//
//                                        attendance=attendances.get(i);
//                                        TableRow tbrow0 = new TableRow(TableActivity.this);
//
//                                        TextView tv0 = new TextView(TableActivity.this);
//                                        tv0.setText(attendance.getName());
//                                        tv0.setTextColor(Color.WHITE);
//                                        tbrow0.addView(tv0);
//                                        stk.addView(tbrow0);
//
//                                    }
//
//                                }
//
//
//                                }
//                                dialog.dismiss();
//
//                            }
//                             else
//                              {
//
//                            Toast.makeText(TableActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        try {
//                            Log.e("Tag", "error=" + e.toString());
//
//
//                        } catch (Resources.NotFoundException e1) {
//                            e1.printStackTrace();
//                        }
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<AttendanceResponse> call, Throwable t) {
//                    try {
//                        Log.e("Tag", "error" + t.toString());
//
//                    } catch (Resources.NotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//            });
//
//
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private JsonObject ApiMapJson() {
//
//        JsonObject gsonObject = new JsonObject();
//        try {
//            JSONObject jsonObj_ = new JSONObject();
//            jsonObj_.put("start_date", null);
//            jsonObj_.put("end_date",null);
//            jsonObj_.put("employee_list", null);
//
//            JsonParser jsonParser = new JsonParser();
//            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());
//
//            //print parameter
//            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
//
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//
//        return gsonObject;
//    }


}