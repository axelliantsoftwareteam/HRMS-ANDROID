package com.khazana.hrm.Fragment.AttendanceReport;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.khazana.hrm.Adapter.Attendance.ClockInAdapter;
import com.khazana.hrm.Adapter.Attendance.ClockOutAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.AddAttendReq.AttenReqResponse;
import com.khazana.hrm.Model.CheckIn.CheckInDetail;
import com.khazana.hrm.Model.CheckIn.CheckInModel;
import com.khazana.hrm.Model.CheckIn.CheckOutDetail;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.R;
import com.khazana.hrm.UI.CheckRequest;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khazana.hrm.databinding.DialogEventBinding;
import com.khazana.hrm.databinding.FragmentClockInBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClockInFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;

    private static final String IMAGE_DIRECTORY = "/YourDirectName";
    private Context mContext;
    private CircleImageView circleImageView;  // imageview
    private int GALLERY = 1, CAMERA = 2;

    private Button button;
    String msg;
    String satttype, sdate, stime, proimage;

    ClockInAdapter clockInAdapter;
    ClockOutAdapter clockOutAdapter;


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    List<CheckInDetail> checkInDetails = new ArrayList<>();


    private RecyclerView RecyclerView;
    private RecyclerView.LayoutManager LayoutManager;

    List<CheckOutDetail> checkOutDetails = new ArrayList<>();


    private String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.CAMERA"};


    private FragmentClockInBinding binding;
    private DialogEventBinding dialogEventBinding;

    NestedScrollView nestedScrollView;

    SessionManager sessionManager;
    String token;
    TextView nodata;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentClockInBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        nodata = view.findViewById(R.id.txt_no);
        nestedScrollView = view.findViewById(R.id.nested);


        binding.btnreqtodayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialogout();
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnReqtodayin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();
                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        // start clockout
        RecyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.setLayoutManager(LayoutManager);
        // end clockout

        GetCheckIn(token);
//        setAdapter();

        return view;
    }

    private void GetCheckIn(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<CheckInModel> checkInModelCall = ApiHandler.getApiInterface().getClock("Bearer " + access_token);

            //  Call<UserModel> registerCall = ApiHandler.getApiInterface().getUser(ApiMapJson());
            checkInModelCall.enqueue(new Callback<CheckInModel>() {
                @Override
                public void onResponse(Call<CheckInModel> checkInModelCall, Response<CheckInModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {


                                checkInDetails = response.body().getData().getResponse().getCheckInDetails();

                                if (checkInDetails.size() == 0) {
                                    dialog.dismiss();
                                    nestedScrollView.setVisibility(View.GONE);

                                } else {

                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    nodata.setVisibility(View.GONE);

                                    String clockin = response.body().getData().getResponse().getCheckIn();
                                    if (clockin.equals("")) {
                                        binding.rely.setVisibility(View.VISIBLE);
                                        binding.txtOnday.setVisibility(View.GONE);
                                    } else {
                                        binding.rely.setVisibility(View.GONE);
                                        binding.txtOnday.setVisibility(View.VISIBLE);
                                        binding.txtOnday.setText(clockin);
                                    }

                                    clockInAdapter = new ClockInAdapter(getActivity(), checkInDetails);
                                    mRecyclerView.setAdapter(clockInAdapter);

                                    dialog.dismiss();

                                    // on item list clicked
                                    clockInAdapter.setOnItemClickListener(new ClockInAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, CheckInDetail obj, int position) {
                                            showCustomDialog();

                                        }
                                    });

                                }
                                checkOutDetails = response.body().getData().getResponse().getCheckOutDetails();

                                if (checkOutDetails.size() == 0) {
                                    dialog.dismiss();
                                    nestedScrollView.setVisibility(View.GONE);

                                } else {
                                    nestedScrollView.setVisibility(View.VISIBLE);
                                    nodata.setVisibility(View.GONE);
                                    String clockout = response.body().getData().getResponse().getCheckOut();

                                    if (clockout.equals("")) {
                                        binding.relyout.setVisibility(View.VISIBLE);
                                        binding.colckout.setVisibility(View.GONE);
                                    } else {
                                        binding.relyout.setVisibility(View.GONE);
                                        binding.colckout.setVisibility(View.VISIBLE);
                                        binding.colckout.setText(clockout);
                                    }


                                    clockOutAdapter = new ClockOutAdapter(getActivity(), checkOutDetails);
                                    RecyclerView.setAdapter(clockOutAdapter);
                                    dialog.dismiss();

                                    // on item list clicked
                                    clockOutAdapter.setOnItemClickListener(new ClockOutAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, CheckOutDetail obj, int position) {
                                            showoutDialog();
                                        }

                                    });


                                }


                            } else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        } else {


                            Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                            sessionManager.logoutUser();
                            Intent intent = new Intent(getActivity(), SignIn.class);
                            startActivity(intent);
                            dialog.dismiss();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<CheckInModel> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "" + t.toString(), Toast.LENGTH_SHORT).show();
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
//    private void setAdapter() {
//        //set data and list adapter
//        clockInAdapter = new ClockInAdapter(getActivity(), checkInDetails);
//        mRecyclerView.setAdapter(clockInAdapter);
//        // on item list clicked
//        clockInAdapter.setOnItemClickListener(new ClockInAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, CheckInDetail obj, int position)
//            {
//                showCustomDialog();
//
//            }
//        });
//
//
//    }


    //checkut request open call

    private void showCustomDialogout() {

        dialogEventBinding = DialogEventBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEventBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        SessionManager session = new SessionManager(getActivity());
        String prof = session.getProfimg();

        Glide.with(this).load(Base64.decode(prof, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).error(R.drawable.ic_baseline_account_circle_24).into(dialogEventBinding.primage);

        dialogEventBinding.leavetype.setText("Check Out");


        dialogEventBinding.primage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 200;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, requestCode);
                    //  showPictureDialog();
//                    takePhotoFromCamera();
//                    dispatchTakePictureIntent();
                    showPictureDialog();

                }


            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(calendar.getTime());
        dialogEventBinding.etStartdate.setText(dateString);
        sdate = dialogEventBinding.etStartdate.getText().toString().trim();


        dialogEventBinding.etTimeFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String time1 = sdf.format(dt);
                        dialogEventBinding.etTimeFrom.setText(time1);

                        stime = dialogEventBinding.etTimeFrom.getText().toString().trim();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        dialogEventBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogEventBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogEventBinding.etTimeFrom.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.etTimeFrom.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                if (dialogEventBinding.etStartdate.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.etStartdate.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                if (dialogEventBinding.msg.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.msg.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    msg = dialogEventBinding.msg.getText().toString();
                    AddReqout(token);
                    dialog.dismiss();
                }


            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void AddReqout(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AttenReqResponse> attenReqResponseCall = ApiHandler.getApiInterface().AddAttendReq("Bearer " + token, ApiMapout());

            attenReqResponseCall.enqueue(new Callback<AttenReqResponse>() {
                @Override
                public void onResponse(Call<AttenReqResponse> attenReqResponseCall, Response<AttenReqResponse> response) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), CheckRequest.class);
                                startActivity(intent);

                            } else if (status == 400) {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), CheckRequest.class);
                                startActivity(intent);

                            } else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else if (status == 500) {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), CheckRequest.class);
                                startActivity(intent);

                            }
                            else {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                            sessionManager.logoutUser();
                            Intent intent = new Intent(getActivity(), SignIn.class);
                            startActivity(intent);
                            dialog.dismiss();



                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AttenReqResponse> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
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

    private JsonObject ApiMapout() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("type", "2");
            jsonObj_.put("date", sdate);
            jsonObj_.put("time", stime);
            jsonObj_.put("reason", msg);
            jsonObj_.put("image", "asdsdsdsd");


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


//close the check request call


    // check in request call
    private void showCustomDialog() {

        dialogEventBinding = DialogEventBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEventBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        SessionManager session = new SessionManager(getActivity());
        String prof = session.getProfimg();

        Glide.with(this).load(Base64.decode(prof, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).error(R.drawable.ic_baseline_account_circle_24).into(dialogEventBinding.primage);


        dialogEventBinding.leavetype.setText("Check In");


        dialogEventBinding.primage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 200;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, requestCode);
                    //  showPictureDialog();
//                    takePhotoFromCamera();
//                    dispatchTakePictureIntent();
                    showPictureDialog();

                }


            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(calendar.getTime());
        dialogEventBinding.etStartdate.setText(dateString);
        sdate = dialogEventBinding.etStartdate.getText().toString().trim();


        dialogEventBinding.etTimeFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String time1 = sdf.format(dt);
                        dialogEventBinding.etTimeFrom.setText(time1);

                        stime = dialogEventBinding.etTimeFrom.getText().toString().trim();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        dialogEventBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogEventBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogEventBinding.etTimeFrom.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.etTimeFrom.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                if (dialogEventBinding.etStartdate.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.etStartdate.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                if (dialogEventBinding.msg.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.msg.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    msg = dialogEventBinding.msg.getText().toString();
                    AddReq(token);
                    dialog.dismiss();
                }


            }
        });


//        final Button spn_from_date = (Button) dialog.findViewById(R.id.spn_from_date);
//        final Button spn_from_time = (Button) dialog.findViewById(R.id.spn_from_time);
//        final Button spn_to_date = (Button) dialog.findViewById(R.id.spn_to_date);
//        final Button spn_to_time = (Button) dialog.findViewById(R.id.spn_to_time);
//        final TextView tv_email = (TextView) dialog.findViewById(R.id.tv_email);
//        final EditText et_name = (EditText) dialog.findViewById(R.id.et_name);
//        final EditText et_location = (EditText) dialog.findViewById(R.id.et_location);
//        final AppCompatCheckBox cb_allday = (AppCompatCheckBox) dialog.findViewById(R.id.cb_allday);
//        final AppCompatSpinner spn_timezone = (AppCompatSpinner) dialog.findViewById(R.id.spn_timezone);
//
//        String[] timezones = getResources().getStringArray(R.array.timezone);
//        ArrayAdapter<String> array = new ArrayAdapter<>(this, R.layout.simple_spinner_item, timezones);
//        array.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        spn_timezone.setAdapter(array);
//        spn_timezone.setSelection(0);
//
//        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Event event = new Event();
//                event.email = tv_email.getText().toString();
//                event.name = et_name.getText().toString();
//                event.location = et_location.getText().toString();
//                event.from = spn_from_date.getText().toString() + " (" + spn_from_time.getText().toString() + ")";
//                event.to = spn_to_date.getText().toString() + " (" + spn_to_time.getText().toString() + ")";
//                event.is_allday = cb_allday.isChecked();
//                event.timezone = spn_timezone.getSelectedItem().toString();
//                displayDataResult(event);

//                dialog.dismiss();
//            }
//        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void AddReq(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AttenReqResponse> attenReqResponseCall = ApiHandler.getApiInterface().AddAttendReq("Bearer " + token, ApiMap());

            attenReqResponseCall.enqueue(new Callback<AttenReqResponse>() {
                @Override
                public void onResponse(Call<AttenReqResponse> attenReqResponseCall, Response<AttenReqResponse> response) {

                    try {

                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), CheckRequest.class);
                                startActivity(intent);

                            } else if (status == 400) {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            } else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), CheckRequest.class);
                                startActivity(intent);

                            } else {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                            sessionManager.logoutUser();
                            Intent intent = new Intent(getActivity(), SignIn.class);
                            startActivity(intent);
                            dialog.dismiss();


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AttenReqResponse> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
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

    private JsonObject ApiMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("type", "1");
            jsonObj_.put("date", sdate);
            jsonObj_.put("time", stime);
            jsonObj_.put("reason", msg);
            jsonObj_.put("image", "asdsdsdsd");


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


    // check in request call

    private void showoutDialog() {


        dialogEventBinding = DialogEventBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEventBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        SessionManager session = new SessionManager(getActivity());
        String prof = session.getProfimg();

        Glide.with(this).load(Base64.decode(prof, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).error(R.drawable.ic_baseline_account_circle_24).into(dialogEventBinding.primage);

        dialogEventBinding.leavetype.setText("Check In");


        dialogEventBinding.primage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 200;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, requestCode);
                    //  showPictureDialog();
//                    takePhotoFromCamera();
//                    dispatchTakePictureIntent();
                    showPictureDialog();

                }


            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(calendar.getTime());
        dialogEventBinding.etStartdate.setText(dateString);
        sdate = dialogEventBinding.etStartdate.getText().toString().trim();


        dialogEventBinding.etTimeFrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String time1 = sdf.format(dt);
                        dialogEventBinding.etTimeFrom.setText(time1);

                        stime = dialogEventBinding.etTimeFrom.getText().toString().trim();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        dialogEventBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogEventBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dialogEventBinding.etTimeFrom.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.etTimeFrom.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                if (dialogEventBinding.etStartdate.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.etStartdate.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                if (dialogEventBinding.msg.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogEventBinding.msg.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {
                    msg = dialogEventBinding.msg.getText().toString();
                    AddReqs(token);
                    dialog.dismiss();
                }


            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void AddReqs(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AttenReqResponse> attenReqResponseCall = ApiHandler.getApiInterface().AddAttendReq("Bearer " + token, ApiMapp());

            attenReqResponseCall.enqueue(new Callback<AttenReqResponse>() {
                @Override
                public void onResponse(Call<AttenReqResponse> attenReqResponseCall, Response<AttenReqResponse> response) {

                    try {

                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), CheckRequest.class);
                                startActivity(intent);

                            } else if (status == 400) {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                            } else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), CheckRequest.class);
                                startActivity(intent);

                            } else if (status == 500) {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            } else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                            sessionManager.logoutUser();
                            Intent intent = new Intent(getActivity(), SignIn.class);
                            startActivity(intent);
                            dialog.dismiss();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AttenReqResponse> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
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

    private JsonObject ApiMapp() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("type", "2");
            jsonObj_.put("date", sdate);
            jsonObj_.put("time", stime);
            jsonObj_.put("reason", msg);
            jsonObj_.put("image", "asdsdsdsd");


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        try {
//            getActivity().startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        } catch (ActivityNotFoundException e) {
//            // display error state to the user
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            dialogEventBinding.primage.setImageBitmap(imageBitmap);
//            try {
//                createImageFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//
//       // File storageDir = g(Environment.DIRECTORY_PICTURES);
//        File path = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                path      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//
//    }


// for image save

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhotoFromCamera();

                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    //     String path = saveImageBitmap(bitmap);
                    // Toast.makeText(getActivity(), "Image Saved !"+bitmap, Toast.LENGTH_SHORT).show();
                    dialogEventBinding.primage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            dialogEventBinding.primage.setImageBitmap(thumbnail);
            //  final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(thumbnail);

            //   String image = saveImageBitmap(thumbnail);

            Log.e("Tag", "" + encodedImage.toString());
            Toast.makeText(getActivity(), "Image Saved" + encodedImage, Toast.LENGTH_SHORT).show();

        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    // complete the images save


    public void saveImageBitmap(Bitmap image_bitmap, String image_name) {
        String root = Environment.getExternalStorageDirectory().toString();
        if (isStoragePermissionGranted()) { // check or ask permission
            File myDir = new File(root, "/saved_images");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            String fname = "Image-" + image_name + ".jpg";
            File file = new File(myDir, fname);
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile(); // if file already exists will do nothing
                FileOutputStream out = new FileOutputStream(file);
                image_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, new String[]{file.getName()}, null);
        }
    }


//    // Select image from camera and gallery
//    private void selectImage() {
//        try {
//            PackageManager pm = getActivity().getPackageManager();
//
////            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getActivity().getPackageName());
////            if (hasPerm == PackageManager.PERMISSION_GRANTED)
////            {
//                final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Select Option");
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                        if (options[item].equals("Take Photo")) {
//                            dialog.dismiss();
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
//                        } else if (options[item].equals("Choose From Gallery")) {
//                            dialog.dismiss();
//                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
//                        } else if (options[item].equals("Cancel")) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//                builder.show();
//          //  } else
//               // Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        inputStreamImg = null;
//        if (requestCode == PICK_IMAGE_CAMERA) {
//            try {
//                Uri selectedImage = data.getData();
//                bitmap = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
//
//                Log.e("Activity", "Pick from Camera::>>> ");
//
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//                destination = new File(Environment.getExternalStorageDirectory() + "/" +
//                        getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
//                FileOutputStream fo;
//                try {
//                    destination.createNewFile();
//                    fo = new FileOutputStream(destination);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                imgPath = destination.getAbsolutePath();
//                imageview.setImageBitmap(bitmap);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == PICK_IMAGE_GALLERY) {
//            Uri selectedImage = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
//                Log.e("Activity", "Pick from Gallery::>>> ");
//
//                imgPath = getRealPathFromURI(selectedImage);
//                destination = new File(imgPath.toString());
//                imageview.setImageBitmap(bitmap);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public String getRealPathFromURI(Uri contentUri) {
//        String[] proj = {MediaStore.Audio.Media.DATA};
//        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
//


    //GetCheckOut start


//    private void GetCheckOut(final String access_token) {
//        try {
//
//            Call<CheckInModel> checkInModelCall = ApiHandler.getApiInterface().getClock("Bearer " + access_token);
//
//            checkInModelCall.enqueue(new Callback<CheckInModel>() {
//                @Override
//                public void onResponse(Call<CheckInModel> checkInModelCall, Response<CheckInModel> response) {
//
//                    try {
//                        if (response.isSuccessful()) {
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200) {
//
//                                checkInDetails = response.body().getData().getResponse().getCheckInDetails();
//
//
////                                    for (int i = 0; i <=0; i++)
////                                    {
////
////                                       CheckInDetail checkInDetail =checkInDetails.get(i);
////
//////
//////                                       binding.txtFrtin.setText(checkInDetail.getDay());
//////                                        binding.txtTwoin.setText(checkInDetail.getDay());
//////                                       binding.txtThreein.setText(checkInDetail.getDay());
//////                                        binding.txtFrtin.setText(checkInDetail.getDay());
//////                                        binding.txtFivein.setText(checkInDetail.getDay());
//////                                       binding.txtSixin.setText(checkInDetail.getDay());
////
////                                        checkInDetails.add(new CheckInDetail(checkInDetail.getDay(),checkInDetail.getDate()));
////
////                                    }
//
//
//
//                                clockInAdapter = new ClockInAdapter(getActivity(), checkInDetails);
//                                mRecyclerView.setAdapter(clockInAdapter);
//
//                            }
//
//                        } else {
//
//                            Toast.makeText(getActivity(), "" + response.errorBody(), Toast.LENGTH_SHORT).show();
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
//                public void onFailure(Call<CheckInModel> call, Throwable t) {
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

}