package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hrm.Adapter.Request.RequestAttendanceAdapter;
import com.example.hrm.Fragment.Leaves.AllLeavesFragment;
import com.example.hrm.Fragment.Tasks.AlltaskFragment;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.GetAllTask.CountsInfo;
import com.example.hrm.Model.GetAllTask.GetAllTaskModel;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Model.ReqAttendList.RequestListModel;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityTasksBinding;
import com.example.hrm.databinding.DialogTaskcreateBinding;
import com.example.hrm.utils.Tools;
import com.example.hrm.utils.ViewAnimation;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksActivity extends AppCompatActivity {

    private ActivityTasksBinding binding;
    String sname,sdate,snote;
    SessionManager sessionManager;
    String token,alltask,today,nextseven,pendtask,complt,high,low,normal,sstartdate;
    List<MemberResponse> memberResponses= new ArrayList<>();
    DatePickerDialog picker;

    private DialogTaskcreateBinding dialogTaskcreateBinding;


    private static final String[] paths = {"All Task", "Today", "Next 7 Days", "Pending Task","Completed"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_tasks);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        transparentStatusAndNavigation();
        isNetworkConnectionAvailable();

        sessionManager = new SessionManager(TasksActivity.this);
        token = sessionManager.getToken();
        getmember(token);

        GetAllTags(token);


        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TasksActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(TasksActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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
        binding.spinnerlisttask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                String selected_val=binding.spinnerlisttask.getSelectedItem().toString();

                if (selected_val.equals("All Task"))
                {
                    AlltaskFragment alltaskFragment =new AlltaskFragment();
                    moveToFragment(alltaskFragment);

//                    String alltask =null;
//                    Toast.makeText(TasksActivity.this, ""+alltask, Toast.LENGTH_SHORT).show();


                }
                else if (selected_val.equals("Today"))
                {
                    int status = 1;

                    AllLeavesFragment allLeavesFragment =new AllLeavesFragment();
                    moveToFragment(allLeavesFragment);

//                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();
//

                }
                if (selected_val.equals("Next 7 Days"))
                {
                    int status = 2;
                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();


                }
                if (selected_val.equals("Pending Task"))
                {
                    int status = 4;
                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();


                }
                if (selected_val.equals("Completed"))
                {
                    int status = 5;
                    Toast.makeText(TasksActivity.this, ""+status, Toast.LENGTH_SHORT).show();


                }


                Toast.makeText(TasksActivity.this, selected_val ,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void moveToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    private void showCustomDialog()
    {



        dialogTaskcreateBinding = DialogTaskcreateBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(TasksActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogTaskcreateBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



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
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dateString = format.format(calendar.getTime());
                                dialogTaskcreateBinding.etStartdate.setText(dateString);


                                sstartdate = dialogTaskcreateBinding.etStartdate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });


        dialogTaskcreateBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(TasksActivity.this, "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogTaskcreateBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sname = dialogTaskcreateBinding.txtname.getText().toString().trim();
                snote=dialogTaskcreateBinding.msg.getText().toString();

              //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    public void getmember(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(TasksActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<Members> membersCall = ApiHandler.getApiInterface().getlistMember("Bearer " + access_token);
            Log.e("Tag", "response" + membersCall.toString());

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

                                Log.e("Tag", "respone" +memberResponses.toString());

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


                                dialog.dismiss();

                                binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        Integer idd = memberResponses.get(position).getId();
                                        sname = parent.getItemAtPosition(position).toString();
                                        Log.e("Tag", "member=" + sname.toString());
                                        Log.e("Tag", "idd=" + idd.toString());


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


    private void GetAllTags(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(TasksActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAllTaskModel> getAllTaskModelCall = ApiHandler.getApiInterface().getAllTagsList("Bearer " + access_token);
            getAllTaskModelCall.enqueue(new Callback<GetAllTaskModel>() {
                @Override
                public void onResponse(Call<GetAllTaskModel> getAllTaskModelCall1, Response<GetAllTaskModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {
                                CountsInfo countsInfo =new CountsInfo();
                                countsInfo = response.body().getData().getCountsInfo();

                                if (countsInfo.equals("0"))
                                {
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.nested.setVisibility(View.GONE);
                                    dialog.dismiss();

                                }
                                else {


                                     alltask = Integer.toString(countsInfo.getAllTasks());
//
//                                    binding.txtalltask.setText(alltask);
//                                     today = Integer.toString(countsInfo.getToday());
//                                    binding.txttoday.setText(today);
//                                     nextseven = Integer.toString(countsInfo.getDays7());
//                                    binding.txtnextseventask.setText(nextseven);
//                                     pendtask = Integer.toString(countsInfo.getPending());
//                                    binding.txtpendingtask.setText(pendtask);
//                                     complt = Integer.toString(countsInfo.getCompleted());
//                                    binding.txtcomplettask.setText(complt);
//                                    high = Integer.toString(countsInfo.getHigh());
//                                    binding.txthightag.setText(high);
//                                    normal = Integer.toString(countsInfo.getNormal());
//                                    binding.txtnormltag.setText(normal);
//                                    low = Integer.toString(countsInfo.getLow());
//                                    binding.txtlowtag.setText(low);








//                                    binding.txtalltask.setText(Integer.toString(countsInfo.getAllTasks()));
//

                                //    binding.txttoday.setText(Integer.toString(countsInfo.getCompleted()));
//                                    binding.txtnextseventask.setText(countsInfo.getAllTasks());
//                                    binding.txtpendingtask.setText(countsInfo.getAllTasks());
//                                    binding.txtcomplettask.setText(countsInfo.getAllTasks());
                                    binding.nested.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }




                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(TasksActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<GetAllTaskModel> call, Throwable t) {
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
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS ,false);
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