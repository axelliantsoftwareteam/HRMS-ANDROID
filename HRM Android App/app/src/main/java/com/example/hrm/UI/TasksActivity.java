package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
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

import com.example.hrm.Adapter.Task.AllTaskAdapter;
import com.example.hrm.Fragment.Tasks.AlltaskFragment;
import com.example.hrm.Fragment.Tasks.CompletedFragment;
import com.example.hrm.Fragment.Tasks.HighFragment;
import com.example.hrm.Fragment.Tasks.LowFragment;
import com.example.hrm.Fragment.Tasks.NormalFragment;
import com.example.hrm.Fragment.Tasks.PendingFragment;
import com.example.hrm.Fragment.Tasks.SevendaysFragment;
import com.example.hrm.Fragment.Tasks.TodayFragment;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.BasicSetup.Skills.AddSkill.Addskill;
import com.example.hrm.Model.Building.TaskAdded.TaskAddedModel;
import com.example.hrm.Model.GetAllTask.Alltask.AddTask;
import com.example.hrm.Model.GetAllTask.Alltask.GetAlltask;
import com.example.hrm.Model.GetAllTask.Alltask.GetAlltaskData;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Model.UserEvaluations.post.AddUserEvaluations;
import com.example.hrm.Model.UserEvaluations.post.Question;
import com.example.hrm.Model.UserEvaluations.response.UserEvaluationsResponse;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityTasksBinding;
import com.example.hrm.databinding.DialogTaskcreateBinding;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.guna.libmultispinner.MultiSelectionSpinner;

import org.apache.commons.math3.analysis.function.Add;
import org.apache.commons.math3.analysis.function.Constant;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksActivity extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    private ActivityTasksBinding binding;
    String sname, sdate, snote;
    SessionManager sessionManager;

    String token, alltask, today, nextseven, pendtask, complt, high, low, normal, sstartdate;
    List<MemberResponse> memberResponses = new ArrayList<>();
    DatePickerDialog picker;

    String assign;
    int iCurrentSelection = 0;
    AllTaskAdapter allTaskAdapter;

    AddTask addTask=new AddTask();

    List<GetAlltaskData> getAllTaskDataList = new ArrayList<>();


    private RecyclerView.LayoutManager mLayoutManager;
    private DialogTaskcreateBinding dialogTaskcreateBinding;


    private static final String[] paths = {"All Task", "Today", "Next 7 Days", "Pending Task", "Completed"};


    private static final String[] tagpaths = {"High", "Normal", "Low"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // setContentView(R.layout.activity_tasks);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // transparentStatusAndNavigation();
        isNetworkConnectionAvailable();

        sessionManager = new SessionManager(TasksActivity.this);
        token = sessionManager.getToken();
      //  getmember(token);
        loadmemberlist();

        AlltaskFragment alltaskFragment = new AlltaskFragment();
        moveToFragment(alltaskFragment);


//        binding.alltaskrecycler.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(TasksActivity.this, LinearLayoutManager.VERTICAL, false);
//
//        binding.alltaskrecycler.setLayoutManager(mLayoutManager);

        //  GetAllTask(token);
        //   GetAllTags(token);


        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });


        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TasksActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        binding.btnaddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomDialog();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TasksActivity.this,
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerlisttask.setAdapter(adapter);


        ArrayAdapter<String> tagadapter = new ArrayAdapter<String>(TasksActivity.this,
                android.R.layout.simple_spinner_item, tagpaths);
        tagadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerlisttag.setAdapter(tagadapter);



        binding.spinnerlisttask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long arg3) {

                String selected_val = binding.spinnerlisttask.getSelectedItem().toString();

                if (selected_val.equals("All Task")) {
                    binding.alltaskrecycler.setVisibility(View.GONE);
                    binding.frameLayout.setVisibility(View.VISIBLE);

                    AlltaskFragment alltaskFragment = new AlltaskFragment();
                    moveToFragment(alltaskFragment);

//                    String alltask =null;
//                    Toast.makeText(TasksActivity.this, ""+alltask, Toast.LENGTH_SHORT).show();


                } else if (selected_val.equals("Today")) {
                    //
                    binding.alltaskrecycler.setVisibility(View.GONE);
                    binding.frameLayout.setVisibility(View.VISIBLE);
                    int status = 1;

                    TodayFragment todayFragment = new TodayFragment();
                    moveToFragment(todayFragment);

//                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();
//

                }
                if (selected_val.equals("Next 7 Days")) {
                    binding.alltaskrecycler.setVisibility(View.GONE);
                    binding.frameLayout.setVisibility(View.VISIBLE);
                    int status = 2;

                    SevendaysFragment sevendaysFragment = new SevendaysFragment();
                    moveToFragment(sevendaysFragment);


//                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();


                }
                if (selected_val.equals("Pending Task")) {
                    binding.alltaskrecycler.setVisibility(View.GONE);
                    binding.frameLayout.setVisibility(View.VISIBLE);
                    int status = 4;
                    PendingFragment pendingFragment = new PendingFragment();
                    moveToFragment(pendingFragment);


//                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();


                }
                if (selected_val.equals("Completed")) {
                    binding.alltaskrecycler.setVisibility(View.GONE);
                    binding.frameLayout.setVisibility(View.VISIBLE);
                    int status = 5;
                    CompletedFragment completedFragment = new CompletedFragment();
                    moveToFragment(completedFragment);


//                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        binding.spinnerlisttag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
                if (iCurrentSelection == position) {
                    return;
                } else {
                    String selected_val = binding.spinnerlisttag.getSelectedItem().toString();

                    if (selected_val.equals("High")) {
                        binding.alltaskrecycler.setVisibility(View.GONE);
                        binding.frameLayout.setVisibility(View.VISIBLE);
                        HighFragment highFragment = new HighFragment();
                        moveToFragment(highFragment);

//                    String alltask =null;
//                    Toast.makeText(TasksActivity.this, ""+alltask, Toast.LENGTH_SHORT).show();


                    } else if (selected_val.equals("Normal")) {
                        binding.alltaskrecycler.setVisibility(View.GONE);
                        binding.frameLayout.setVisibility(View.VISIBLE);
                        //  int status = 1;

                        NormalFragment normalFragment = new NormalFragment();
                        moveToFragment(normalFragment);

//                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();
//

                    }
                    if (selected_val.equals("Low")) {
                        binding.alltaskrecycler.setVisibility(View.GONE);
                        binding.frameLayout.setVisibility(View.VISIBLE);
                        LowFragment lowFragment = new LowFragment();
                        moveToFragment(lowFragment);

//                    int status = 2;
//                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();


                    }
                    iCurrentSelection = 0;
                }
                // Your code here
                iCurrentSelection = position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


    }

    public void loadmemberlist()
    {

        SharedPreferences sharedPreferences =getSharedPreferences("shared preferences",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("member", null);
        //    Log.e("Tag", "" + json.toString());
//        Toast.makeText(getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<MemberResponse>>() {}.getType();
        memberResponses = gson.fromJson(json, type);

        if (memberResponses == null)
        {

            memberResponses = new ArrayList<>();
        }
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
        adapter = new ArrayAdapter<String>(TasksActivity.this, android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        binding.spinnerlist.setAdapter(adapter);

        binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    sname = parent.getItemAtPosition(position).toString();
                //    Log.e("Tag", "member=" + sname.toString());
                   // GetAllAttendByID(token, idd);
                    iCurrentSelection = 0;

                }


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

    public void dialogloadmemberlist()
    {

        SharedPreferences sharedPreferences =getSharedPreferences("shared preferences",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("member", null);
        //    Log.e("Tag", "" + json.toString());
//        Toast.makeText(getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<MemberResponse>>() {}.getType();
        memberResponses = gson.fromJson(json, type);

        if (memberResponses == null)
        {

            memberResponses = new ArrayList<>();
        }
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
        adapter = new ArrayAdapter<String>(TasksActivity.this, android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        dialogTaskcreateBinding.dialogspinnerlist.setAdapter(adapter);

        dialogTaskcreateBinding.dialogspinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (iCurrentSelection == position) {
                    return;
                } else {
                Integer idd = memberResponses.get(position).getId();


              //  Log.e("Tag", "idd=" + idd.toString());
               // sname = parent.getItemAtPosition(position).toString();
              //  Log.e("Tag", "member=" + sname.toString());
                // GetAllAttendByID(token, idd);
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

    }




    private void GetAllTask(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(TasksActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAlltask> getAlltaskCall = ApiHandler.getApiInterface().getAllList("Bearer " + access_token);
            getAlltaskCall.enqueue(new Callback<GetAlltask>() {
                @Override
                public void onResponse(Call<GetAlltask> getAlltaskCall1, Response<GetAlltask> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                getAllTaskDataList = response.body().getData().getResponse();
                                if (getAllTaskDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.alltaskrecycler.setVisibility(View.GONE);
                                } else if (getAllTaskDataList != null) {


                                    allTaskAdapter = new AllTaskAdapter(TasksActivity.this, getAllTaskDataList);
                                    binding.alltaskrecycler.setAdapter(allTaskAdapter);
                                    binding.alltaskrecycler.setVisibility(View.VISIBLE);
                                    // binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    // on item list clicked
                                    allTaskAdapter.setOnItemClickListener(new AllTaskAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetAlltaskData obj, int position) {
                                            GetAlltaskData getAlltaskData = new GetAlltaskData();
                                            getAlltaskData = getAllTaskDataList.get(position);
                                            //   showCustomDialog(getAlltaskData);

                                           // Toast.makeText(TasksActivity.this, "details", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }


                            }

                        } else {
                            ///  binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(TasksActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<GetAlltask> call, Throwable t) {
                    try {
                      //  Log.e("Tag", "error" + t.toString());
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

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    private void showCustomDialog() {



        dialogTaskcreateBinding = DialogTaskcreateBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(TasksActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogTaskcreateBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


//        String[] array = {"Monday", "Tuesday", "Webnesday", "Thursday", "Saturday", "Sunday"};


        ArrayAdapter<String> tagadapter = new ArrayAdapter<String>(TasksActivity.this,
                android.R.layout.simple_spinner_item, tagpaths);
        tagadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogTaskcreateBinding.spinner.setAdapter(tagadapter);


        dialogTaskcreateBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
                if (iCurrentSelection == position) {
                    return;
                } else {
                    String selected_val = dialogTaskcreateBinding.spinner.getSelectedItem().toString();

                    if (selected_val.equals("High")) {
                        addTask.setTag(2);


                    } else if (selected_val.equals("Normal")) {

                        addTask.setTag(1);
                    } else {


                        addTask.setTag(0);


                    }
                   // Toast.makeText(TasksActivity.this, "" + addTask.getTag(), Toast.LENGTH_SHORT).show();

                    iCurrentSelection = 0;
                }

                // Your code here
                iCurrentSelection = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        dialogTaskcreateBinding.etStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(TasksActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogTaskcreateBinding.etStartdate.setText(dateString);

                                addTask.setDue_date(dialogTaskcreateBinding.etStartdate.getText().toString().trim());
                                sstartdate = dialogTaskcreateBinding.etStartdate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });

//        getdialogmember(token);
        dialogloadmemberlist();


        dialogTaskcreateBinding.selfcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogTaskcreateBinding.selfcheck.isChecked()) {


                    // true,do the task
//                    dialogTaskcreateBinding.selfcheck.setVisibility(View.VISIBLE);

//                    dialogTaskcreateBinding.list.setVisibility(View.INVISIBLE);
//
//                    addTask.setAssigned_to();
                    // Toast.makeText(TasksActivity.this, "Self task", Toast.LENGTH_SHORT).show();

                } else {

//                    getdialogmember(token);
                    dialogTaskcreateBinding.list.setVisibility(View.VISIBLE);


                }
            }
        });


        dialogTaskcreateBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(TasksActivity.this, "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogTaskcreateBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sname = dialogTaskcreateBinding.txtname.getText().toString().trim();
                snote = dialogTaskcreateBinding.msg.getText().toString();

                addTask.setName(dialogTaskcreateBinding.txtname.getText().toString().trim());
                addTask.setNotes(dialogTaskcreateBinding.msg.getText().toString().trim());

                addTask(addTask, dialog);

                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void addTask(AddTask addTask, Dialog pDialog) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(TasksActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<TaskAddedModel> taskAddedModelCall = ApiHandler.getApiInterface().addTask("Bearer " + token, addTask);
            taskAddedModelCall.enqueue(new Callback<TaskAddedModel>() {
                @Override
                public void onResponse(Call<TaskAddedModel> taskAddedModelCall1, Response<TaskAddedModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Toast.makeText(TasksActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                pDialog.dismiss();
                                Intent intent = new Intent(TasksActivity.this, TasksActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {

                            Toast.makeText(TasksActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<TaskAddedModel> call, Throwable t) {
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

//
//    public void getmember(final String access_token) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(TasksActivity.this);
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
//                                adapter = new ArrayAdapter<String>(TasksActivity.this, android.R.layout.simple_list_item_1, items);
//                                //setting adapter to spinner
//                                binding.spinnerlist.setAdapter(adapter);
//
//
//                                dialog.dismiss();
//
//                                binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                        Integer idd = memberResponses.get(position).getId();
//                                        sname = parent.getItemAtPosition(position).toString();
//                                        Log.e("Tag", "member=" + sname.toString());
//                                        Log.e("Tag", "idd=" + idd.toString());
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
//                            Toast.makeText(TasksActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
//            });
//
//
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void getdialogmember(final String access_token) {
//        try {
//
//            final ProgressDialog progressDialog;
//            progressDialog = new ProgressDialog(TasksActivity.this);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
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
//                                adapter = new ArrayAdapter<String>(TasksActivity.this, android.R.layout.simple_list_item_1, items);
//                                //setting adapter to spinner
//                                dialogTaskcreateBinding.dialogspinnerlist.setAdapter(adapter);
//
//
//                                progressDialog.dismiss();
//
//                                dialogTaskcreateBinding.dialogspinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                        Integer idd = memberResponses.get(position).getId();
//                                        sname = parent.getItemAtPosition(position).toString();
//                                        Log.e("Tag", "member=" + sname.toString());
//                                        Log.e("Tag", "idd=" + idd.toString());
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
//                            Toast.makeText(TasksActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            progressDialog.dismiss();
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
//            });
//
//
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//

//    private void GetAllTags(final String access_token) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(TasksActivity.this);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//            Call<GetAlltask> getAllTaskModelCall = ApiHandler.getApiInterface().getAllList("Bearer " + access_token);
//            getAllTaskModelCall.enqueue(new Callback<GetAlltask>() {
//                @Override
//                public void onResponse(Call<GetAlltask> getAllTaskModelCall1, Response<GetAlltask> response) {
//
//                    try {
//                        if (response.isSuccessful()) {
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200)
//                            {
//                                CountsInfo countsInfo =new CountsInfo();
//                                countsInfo = response.body().getData().getCountsInfo();
//
//                                if (countsInfo.equals("0"))
//                                {
//                                    binding.txtno.setVisibility(View.VISIBLE);
//                                    binding.nested.setVisibility(View.GONE);
//                                    dialog.dismiss();
//
//                                }
//                                else {
//
//
//                                     alltask = Integer.toString(countsInfo.getAllTasks());
////
////                                    binding.txtalltask.setText(alltask);
////                                     today = Integer.toString(countsInfo.getToday());
////                                    binding.txttoday.setText(today);
////                                     nextseven = Integer.toString(countsInfo.getDays7());
////                                    binding.txtnextseventask.setText(nextseven);
////                                     pendtask = Integer.toString(countsInfo.getPending());
////                                    binding.txtpendingtask.setText(pendtask);
////                                     complt = Integer.toString(countsInfo.getCompleted());
////                                    binding.txtcomplettask.setText(complt);
////                                    high = Integer.toString(countsInfo.getHigh());
////                                    binding.txthightag.setText(high);
////                                    normal = Integer.toString(countsInfo.getNormal());
////                                    binding.txtnormltag.setText(normal);
////                                    low = Integer.toString(countsInfo.getLow());
////                                    binding.txtlowtag.setText(low);
//
//
//
//
//
//
//
//
////                                    binding.txtalltask.setText(Integer.toString(countsInfo.getAllTasks()));
////
//
//                                //    binding.txttoday.setText(Integer.toString(countsInfo.getCompleted()));
////                                    binding.txtnextseventask.setText(countsInfo.getAllTasks());
////                                    binding.txtpendingtask.setText(countsInfo.getAllTasks());
////                                    binding.txtcomplettask.setText(countsInfo.getAllTasks());
//                                    binding.nested.setVisibility(View.VISIBLE);
//                                    binding.txtno.setVisibility(View.GONE);
//                                    dialog.dismiss();
//                                }
//
//
//
//
//                            }
//
//                        } else {
//                            binding.txtno.setVisibility(View.VISIBLE);
//                            Toast.makeText(TasksActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
//                public void onFailure(Call<GetAlltask> call, Throwable t) {
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


    //navigation transparent
    //start

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
    //navigation transparent
    //end


    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(TasksActivity.this, MainActivity.class);
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


    @Override
    public void selectedIndices(List<Integer> indices, MultiSelectionSpinner spinner) {
        AddTask addTask = new AddTask();
        addTask.setAssigned_to(indices.toString());

//         assign = indices.toString();

        Toast.makeText(this, "" + addTask.getAssigned_to(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void selectedStrings(List<String> strings, MultiSelectionSpinner spinner) {

        //  Toast.makeText(this, "Array : " + strings.toString(), Toast.LENGTH_LONG).show();

    }
}



//{
//        "client_id" : "294d3f69-409b-4e34-bf39-f5cf2047b4b2",
//        "authorization_user_agent" : "DEFAULT",
//        "redirect_uri" : "msauth://com.example.hrm/1I5EWqVH3n4ujgGjXoA9Qbea1C4%3D",
//        "account_mode" : "SINGLE",
//        "broker_redirect_uri_registered": true,
//        "authorities" : [
//        {
//        "type": "AAD",
//        "audience": {
//        "type": "AzureADandPersonalMicrosoftAccount",
//        "tenant_id": "common"
//        }
//        }
//        ]
//        }
