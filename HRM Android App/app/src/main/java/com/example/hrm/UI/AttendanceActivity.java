package com.example.hrm.UI;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrm.Adapter.Attendance.AttendanceDayAdapter;
import com.example.hrm.Adapter.Attendance.AttendanceDayHeaderAdapter;
import com.example.hrm.Adapter.Attendance.DayDateAdapter;
import com.example.hrm.Adapter.Attendance.EmployeeAttendanceAdapter;
import com.example.hrm.Adapter.Attendance.EmployeeNamesAdapter;
import com.example.hrm.Adapter.Attendance.GetUserAttendanceAdapter;
import com.example.hrm.Adapter.CustomAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Interface.AttendanceItemClickListener;
import com.example.hrm.Model.Attendance.Attendance;
import com.example.hrm.Model.Attendance.AttendancePost;
import com.example.hrm.Model.Attendance.AttendanceResponse;
import com.example.hrm.Model.Attendance.Attendance_list;
import com.example.hrm.Model.Attendance.FetchAttend.FetchAttend;
import com.example.hrm.Model.Attendance.MonthHeader;
import com.example.hrm.Model.CalenderEventObjects;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityAttendanceBinding;
import com.example.hrm.databinding.DialogAttendenanceStatusBinding;
import com.google.common.base.MoreObjects;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.guna.libmultispinner.MultiSelectionSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceActivity extends AppCompatActivity implements View.OnClickListener {


    //   implements OnCalenderDayClickListener


    private ActivityAttendanceBinding binding;
    TextView startdate, enddate, name;
    String ssdate, sedate, sname, username;
    DatePickerDialog picker;
    Spinner spinner;

    SessionManager sessionManager;
    String token;
    List<MemberResponse> memberResponses = new ArrayList<>();


    // recycler for date
    private RecyclerView RecyclerViewday;
    private RecyclerView.LayoutManager LayoutManagerday;
    GetUserAttendanceAdapter getUserAttendanceAdapter;
    Attendance_list attendance_list;
    List<Attendance_list> attendance_listArrayList = new ArrayList<>();
    //end recycler


    List<CalenderEventObjects> mEvents;
    RecyclerView eventsView;
    CustomAdapter mAdapter;


    // recycler for date
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    AttendanceDayHeaderAdapter attendanceDayHeaderAdapter;
    //  List<MonthHeader> monthHeaders= new ArrayList<>();
    //end recycler

//    // recycler for date
//    private RecyclerView mdRecyclerView;
//    private RecyclerView.LayoutManager mdLayoutManager;
//    AttendanceDayAdapter attendanceDayAdapter;
//    List<Attendance> attendances= new ArrayList<>();
//
//    Attendance attendance;
////end recycler


    TextView noleave, download;


    private static final int PERMISSION_STORAGE = 0;

    private int mPosition = 0;
    private List<MonthHeader> mMonthHeaderList = new ArrayList<>();
    private DayDateAdapter dayDateAdapter;

    private List<Attendance> mEmployeeNameList = new ArrayList<>();
    private EmployeeNamesAdapter employeeNamesAdapter;

    private List<Attendance> attendances = new ArrayList<>();
    private EmployeeAttendanceAdapter employeeAttendanceAdapter;

    private DialogAttendenanceStatusBinding dialogAttendenanceStatusBinding;
    private int iCurrentSelection = 0;


//    @Override
//    public void onPermissionsResponse(int requestCode, boolean isGranted) {
//        super.onPermissionsResponse(requestCode, isGranted);
//        if (isGranted) {
//            downloadfile(token);
//        } else {
//            Toast.makeText(this, "Please grant permission to continue", Toast.LENGTH_SHORT).show();
//        }
//    }


    AttendancePost attendancePost = new AttendancePost();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.ivLeft.setOnClickListener(this);
        binding.ivRight.setOnClickListener(this);

//        transparentStatusAndNavigation();


        noleave = findViewById(R.id.tvNoAttendance);
        download = findViewById(R.id.txt_download);

//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(AttendanceActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerView =findViewById(R.id.header_day_recycler_view);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // use a linear layout manager
//        mdLayoutManager = new LinearLayoutManager(AttendanceActivity.this, LinearLayoutManager.VERTICAL, false);
//        mdRecyclerView =findViewById(R.id.leave_recycler_view);
//        mdRecyclerView.setHasFixedSize(true);
//        mdRecyclerView.setLayoutManager(mdLayoutManager);
//        // use a linear layout manager
//        LayoutManagerday = new LinearLayoutManager(AttendanceActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        RecyclerViewday =findViewById(R.id.leaveday_recycler_view);
//        RecyclerViewday.setHasFixedSize(true);
//        RecyclerViewday.setLayoutManager(LayoutManagerday);


        attendancePost.setEndDate(null);
        attendancePost.setEmployeeList(null);
        attendancePost.setEndDate(null);


        sessionManager = new SessionManager(AttendanceActivity.this);
        token = sessionManager.getToken();

        GetAlldate(token, attendancePost);


        binding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetAlldate(token, attendancePost);

                Log.e("Tag", "attend=" + attendancePost.getEmployeeList().toString());

                Toast.makeText(AttendanceActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();


            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadfile(token);


            }
        });


        startdate = findViewById(R.id.etsdate);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AttendanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                startdate.setText(dateString);

                                //  ssdate = startdate.getText().toString().trim();

                                attendancePost.setStartDate(startdate.getText().toString().trim());

                                Log.e("Tag", "" + attendancePost.getStartDate().toString());


                            }
                        }, year, month, day);


                picker.show();


            }
        });


        enddate = findViewById(R.id.etldate);
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AttendanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                enddate.setText(dateString);
//                                sedate = enddate.getText().toString().trim();
                                attendancePost.setEndDate(enddate.getText().toString().trim());
                                Log.e("Tag", "" + attendancePost.getEndDate().toString());
                            }
                        }, year, month, day);
                picker.show();

            }
        });


        name = findViewById(R.id.name);
        spinner = findViewById(R.id.spinnerlist);
        sessionManager = new SessionManager(AttendanceActivity.this);
        token = sessionManager.getToken();
        // getmember(token);
        loadmemberlist();


        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AttendanceActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });
        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AttendanceActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        binding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetAlldate(token, attendancePost);

//                Log.e("Tag", "attend=" + attendancePost.getEmployeeList().toString());

              //  Toast.makeText(AttendanceActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();


            }
        });


    }

    public void loadmemberlist() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("member", null);
        //    Log.e("Tag", "" + json.toString());
//        Toast.makeText(getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<MemberResponse>>() {
        }.getType();
        memberResponses = gson.fromJson(json, type);

        if (memberResponses == null) {

            memberResponses = new ArrayList<>();
        }
        // setspiner(memberResponses);
        //String array to store all the book names
        String[] items = new String[memberResponses.size()];

        //Traversing through the whole list to get all the names
        for (int i = 0; i < memberResponses.size(); i++) {
            //Storing names to string array
            items[i] = memberResponses.get(i).getName();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(AttendanceActivity.this, android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        spinner.setAdapter(adapter);
//
//        binding.dialogspinnerlist.setItems(items);
//        binding.dialogspinnerlist.setSelection(new int[]{});
//        binding.dialogspinnerlist.setListener(this);
//        binding.dialogspinnerlist.setI


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (iCurrentSelection == position)
                {
                    return;
                }
                else
                {
                    Integer idd = memberResponses.get(position).getId();
                 //   Log.e("Tag", "idd=" + idd.toString());
//                    sname = parent.getItemAtPosition(position).toString();
                   // Toast.makeText(AttendanceActivity.this, ""+idd, Toast.LENGTH_SHORT).show();
                    attendancePost.setEmployeeList(Arrays.asList(idd));
                  //  Toast.makeText(AttendanceActivity.this, ""+idd, Toast.LENGTH_SHORT).show();
//                    Log.e("Tag", "member=" + sname.toString());



//                    GetAllAttendByID(token, idd);
                    iCurrentSelection = 0;

                }
                // Your code here
                iCurrentSelection = position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

//        for (int i = 0; i<memberResponseList.size(); i++ )
//        {
//            memberResponse = memberResponseList.get(i);
//            String name = memberResponse.getName();
//            Toast.makeText(EditprofActivity.this, ""+name, Toast.LENGTH_SHORT).show();
//
//        }
    }

    private void downloadfile(final String access_token) {
//        ApiInterface downloadService = ServiceGenerator.create(ApiInterface.class);
//
//        Call<ResponseBody> call = downloadService.downloadFileWithDynamicUrlSync(fileUrl);

        Call<ResponseBody> call = ApiHandler.getApiInterface().getdownload("Bearer " + access_token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    Log.d(TAG, "ressponse: " + response.body().byteStream());
                    boolean writeToDisk = writeToDisk(response.body());

                    if (writeToDisk) {
                        Toast.makeText(AttendanceActivity.this, "File downloaded", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AttendanceActivity.this, "Failed to download file", Toast.LENGTH_SHORT).show();
                    }

                    Log.d(TAG, "file downloaded " + writeToDisk);
                } else {
                    Log.d(TAG, "server error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });
    }

    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    private boolean writeToDisk(ResponseBody body) {
        if (isStoragePermissionGranted()) {
            try {
                File mediaStorageDir = new File(
                        Environment
                                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                        getString(R.string.app_name));

                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        Log.e("file", "Oops! Failed create "
                                + "file" + " directory");
                    }
                }
                File futureStudioIconFile = new File(mediaStorageDir.getPath() + File.separator
                        + "AttendanceReport.xls");

                InputStream inputStream = null;
                OutputStream outputStream = null;

                try {
                    byte[] fileReader = new byte[9896];

                    long fileSize = body.contentLength();
                    long fileSizeDownloaded = 0;

                    inputStream = body.byteStream();
                    outputStream = new FileOutputStream(futureStudioIconFile);

                    while (true) {
                        int read = inputStream.read(fileReader);

                        if (read == -1) {
                            break;
                        }

                        outputStream.write(fileReader, 0, read);

                        fileSizeDownloaded += read;

                        Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                    }

                    outputStream.flush();

                    return true;
                } catch (IOException e) {
                    return false;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    if (outputStream != null) {
                        outputStream.close();
                    }
                }
            } catch (IOException e) {
                return false;
            }
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            return false;
        }
    }


//    public void getmember(final String access_token) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(AttendanceActivity.this);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//            Call<Members> membersCall = ApiHandler.getApiInterface().getlistMember("Bearer " + access_token);
//            Log.e("Tag", "response" + membersCall.toString());
//
//            membersCall.enqueue(new Callback<Members>() {
//                @Override
//                public void onResponse(Call<Members> membersCall1, Response<Members> response) {
//
//                    try {
//                        if (response.isSuccessful()) {
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200) {
//                                memberResponses = response.body().getData().getResponse();
//
//                                Log.e("Tag", "respone" + memberResponses.toString());
//
//                                // setspiner(memberResponses);
//                                //String array to store all the book names
//                                String[] items = new String[memberResponses.size()];
//
//                                //Traversing through the whole list to get all the names
//                                for (int i = 0; i < memberResponses.size(); i++) {
//                                    //Storing names to string array
//                                    items[i] = memberResponses.get(i).getName();
//                                }
//
//                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//                                ArrayAdapter<String> adapter;
//                                adapter = new ArrayAdapter<String>(AttendanceActivity.this, android.R.layout.simple_list_item_1, items);
//                                //setting adapter to spinner
//                                spinner.setAdapter(adapter);
//
//
//                                dialog.dismiss();
//                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                                        sname = parent.getItemAtPosition(position).toString();
//                                        Log.e("Tag", "member=" + sname.toString());
//
//
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                        // sometimes you need nothing here
//                                    }
//                                });
//                                //   Toast.makeText(AddLeave.this, ""+memberResponses, Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        } else {
//
//                            Toast.makeText(AttendanceActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            dialog.dismiss();
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
//                public void onFailure(Call<Members> call, Throwable t) {
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

    private void GetAlldate(final String access_token, AttendancePost attendancePost) {
        try {
//            AttendancePost attendance = new AttendancePost();
//            attendance.setStartDate(null);
//            attendance.setEndDate(null);
//            attendance.setEmployeeList(null);

            final ProgressDialog dialog;
            dialog = new ProgressDialog(AttendanceActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<AttendanceResponse> attendanceCall = ApiHandler.getApiInterface().getattend("Bearer " + access_token, attendancePost);
            //Call<AttendanceResponse> attendanceCall = ApiHandler.getApiInterface().getattend("Bearer " + access_token, attendance);
            attendanceCall.enqueue(new Callback<AttendanceResponse>() {
                @Override
                public void onResponse(Call<AttendanceResponse> attendanceResponseCall, Response<AttendanceResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                Gson gson = new Gson();
                                gson.toJson(response);

                                mMonthHeaderList = response.body().getData().getMonthHeader();


                                if (mMonthHeaderList == null || mMonthHeaderList.size() == 0) {
                                    dialog.dismiss();
                                    binding.tvNoAttendance.setVisibility(View.VISIBLE);
                                    Toast.makeText(AttendanceActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                                } else {


                                    attendances = response.body().getData().getAttendance();
                                    mEmployeeNameList = attendances;
                                    showDataEmployeeNames();
                                    showDataDayDate(0);
                                    dialog.dismiss();
                                    Log.e("Tag", "member=" +attendances);
                                }


                                //attendances = response.body().getData().getAttendance();
                                // attendance_listArrayList= (List<Attendance_list>) response.body().getData().getAttendance().get(attendances.size());

                                //    Log.e("Tag", "member=" + attendancelists);


                                // changes ....
                                /*if (monthHeaders.size() == 0) {
                                    dialog.dismiss();
                                    rvDayDate.setVisibility(View.GONE);
                                } else if (monthHeaders != null) {

                                    attendanceDayHeaderAdapter = new AttendanceDayHeaderAdapter(AttendanceActivity.this, monthHeaders);
                                    rvDayDate.setAdapter(attendanceDayHeaderAdapter);
                                    rvDayDate.setVisibility(View.VISIBLE);
                                    noleave.setVisibility(View.GONE);
                                    dialog.dismiss();

                                }*/


                                // List itemss = new ArrayList<>(attendances);
                                //     List arrayList = new ArrayList<>(attendances);
//
//                                ArrayList arrayList = new ArrayList();
//                                arrayList.addAll(Arrays.asList(attendances));
//
//
////
////                                //Traversing through the whole list to get all the names
//                                for(int i=0; i<attendances.size(); i++){
//                                    //Storing names to string array
//                                    arrayList.set(i, attendances.get(i).getAttendanceList());
//                                }


                                /*if (attendances.equals(null)) {
                                    dialog.dismiss();
                                    mdRecyclerView.setVisibility(View.GONE);
                                } else if (attendances != null) {
                                    // Toast.makeText(AttendanceActivity.this, "testt", Toast.LENGTH_SHORT).show();

                                    attendanceDayAdapter = new AttendanceDayAdapter(AttendanceActivity.this, attendances);
                                    mdRecyclerView.setAdapter(attendanceDayAdapter);
                                    mdRecyclerView.setVisibility(View.VISIBLE);
                                    noleave.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }*/


                                //changes
/*                                for (int i = 0; i < attendances.size(); i++) {

                                    //    Storing names to string array
                                    attendance = attendances.get(i);

//                                    ArrayList arrayList = new ArrayList();
//                                arrayList.addAll(Arrays.asList(attendance.getAttendanceList()));
////
//                                    for (int j = 0; j < arrayList.size(); j++)
//                                    {
                                    //   attendance_listArrayList = attendance.getAttendanceList();

                                    //   Toast.makeText(AttendanceActivity.this, ""+attendance_listArrayList, Toast.LENGTH_SHORT).show();


                                    //}

                                    // Toast.makeText(AttendanceActivity.this, ""+attendance_list.getStatus(), Toast.LENGTH_SHORT).show();

                                    method(attendance);
                                }*/

//                                getUserAttendanceAdapter = new GetUserAttendanceAdapter(AttendanceActivity.this,  attendance_listArrayList);
//                                RecyclerViewday.setAdapter(getUserAttendanceAdapter);
//                                RecyclerViewday.setVisibility(View.VISIBLE);
                                //dialog.dismiss();


                            }

                        } else {
                            binding.tvNoAttendance.setVisibility(View.VISIBLE);
                            Toast.makeText(AttendanceActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(AttendanceActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        } catch (Resources.NotFoundException e1) {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(AttendanceActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(AttendanceActivity.this, "Internal Server Error", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                        Log.e("Tag", "error=" + e.toString());
                        Toast.makeText(AttendanceActivity.this, "Start must be Smaller than End Date ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }


            });


        } catch (Resources.NotFoundException e) {

            e.printStackTrace();
            Log.e("Tag", "error=" + e.toString());
            Toast.makeText(AttendanceActivity.this, "Start must be Smaller than End Date ", Toast.LENGTH_SHORT).show();
        }
    }

    private void method(Attendance attendance) {
//        String name = attendance.getName();
//        Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
        attendance_listArrayList.clear();
        attendance_listArrayList = attendance.getAttendanceList();
        getUserAttendanceAdapter = new GetUserAttendanceAdapter(AttendanceActivity.this, attendance_listArrayList);
        RecyclerViewday.setAdapter(getUserAttendanceAdapter);

    }


    private JsonObject ApiMapJson() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("start_date", null);
            jsonObj_.put("end_date", null);
            jsonObj_.put("employee_list", null);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
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
        Intent i = new Intent(AttendanceActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


//    public void requestAppPermissions(final String[] requestedPermissions, final int requestCode) {
//        int permissionCheck = PackageManager.PERMISSION_GRANTED;
//        boolean shouldShowRequestPermissionRationale = false;
//        for (String permission : requestedPermissions) {
//            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
//            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
//        }
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
//        } else {
//            onPermissionsResponse(requestCode, true);
//        }
//    }


    private void showDataDayDate(int pPosition) {

        if (pPosition == 0) {
            binding.ivLeft.setAlpha(0.5f);
            binding.ivLeft.setEnabled(false);
        } else {
            binding.ivLeft.setAlpha(1.0f);
            binding.ivLeft.setEnabled(true);
        }

        if ((pPosition + 1) * 3 >= mMonthHeaderList.size()) {
            binding.ivRight.setAlpha(0.5f);
            binding.ivRight.setEnabled(false);
        } else {
            binding.ivRight.setAlpha(1.0f);
            binding.ivRight.setEnabled(true);
        }

        List<MonthHeader> localList = new ArrayList<>();
        int start = pPosition * 3;
        for (int i = start; i < mMonthHeaderList.size(); i++) {
            if (localList.size() == 3)
                break;
            localList.add(mMonthHeaderList.get(i));
        }

        dayDateAdapter = new DayDateAdapter(localList);
        binding.rvDayDate.setAdapter(dayDateAdapter);

        showDataEmployeeAttendance(pPosition);
    }

    private void showDataEmployeeNames() {

        employeeNamesAdapter = new EmployeeNamesAdapter(mEmployeeNameList);
        binding.rvNames.setAdapter(employeeNamesAdapter);
    }

    private void showDataEmployeeAttendance(int pPosition) {
        employeeAttendanceAdapter = new EmployeeAttendanceAdapter(attendances, pPosition, new AttendanceItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Attendance attendance) {
                //Toast.makeText(AttendanceActivity.this, attendance.getName() + "\n" + attendance.getAttendanceList().get(0).getDate() + "\n" + attendance.getAttendanceList().get(0).getStatus(), Toast.LENGTH_LONG).show();
                showEditDialog(attendance);
            }
        });
        binding.rvAttendance.setAdapter(employeeAttendanceAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLeft:
                if (mPosition > 0)
                    showDataDayDate(--mPosition);
                break;
            case R.id.ivRight:
                showDataDayDate(++mPosition);
                break;
        }
    }

    private void showEditDialog(Attendance attendance) {

        dialogAttendenanceStatusBinding = DialogAttendenanceStatusBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAttendenanceStatusBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Attendance_list attendance_list = attendance.getAttendanceList().get(0);
        dialogAttendenanceStatusBinding.tvNAme.setText(attendance.getName());
        dialogAttendenanceStatusBinding.tvDate.setText("(" + attendance_list.getDate() + " - " + attendance_list.getWeekDay() + ") " + attendance_list.getStatus());
        dialogAttendenanceStatusBinding.tvCheckIn.setText(attendance_list.getCheckIn());
        dialogAttendenanceStatusBinding.tvCheckOut.setText(attendance_list.getCheckOut());


        dialogAttendenanceStatusBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


//    @Override
//    public void selectedIndices(List<Integer> indices, MultiSelectionSpinner spinner)
//    {
//        Toast.makeText(this, ""+indices.toString(), Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void selectedStrings(List<String> strings, MultiSelectionSpinner spinner)
//    {
//
//    }
}