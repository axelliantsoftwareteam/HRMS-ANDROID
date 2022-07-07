package com.example.hrm.Fragment.Request;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hrm.Adapter.Request.RequestAttendanceAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.AddLeaveRequestModel.AddLeaveReq;
import com.example.hrm.Model.ReqAttendList.RequestListModel;
import com.example.hrm.R;
import com.example.hrm.UI.RequestActivity;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAttendBinding;
import com.example.hrm.databinding.DialogReqattendBinding;
import com.example.hrm.databinding.FragmentAllLeavesBinding;
import com.example.hrm.databinding.FragmentAttendanceBinding;
import com.example.hrm.databinding.FragmentRequestBinding;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceRequestFragment extends Fragment {



    private RecyclerView.LayoutManager mLayoutManager;
    RequestAttendanceAdapter requestAttendanceAdapter;



    private FragmentAttendanceBinding binding;
    private DialogReqattendBinding dialogReqattendBinding;
    DatePickerDialog picker;

    String token;
    String msg;
    String satttype,stime,sstartdate;



    SessionManager sessionManager;


    private String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.CAMERA"};
    private int GALLERY = 1, CAMERA = 2;

    List<com.example.hrm.Model.ReqAttendList.Response> responseList= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAttendanceBinding.inflate(inflater, container, false);
        View view = binding.getRoot();




//        noleave = view.findViewById(R.id.txt_no);

//        mRecyclerView = view.findViewById(R.id.allattd_recycler);
        binding.allattdrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.allattdrecycler.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        GetAllAttend(token);


        binding.btnaddAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog();

            }
        });




        return  view;

    }


    private void showCustomDialog()
    {



        dialogReqattendBinding = DialogReqattendBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogReqattendBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



        dialogReqattendBinding.primage.setOnClickListener(new View.OnClickListener() {
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

        dialogReqattendBinding.etStartdate.setOnClickListener(new View.OnClickListener() {
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
                                dialogReqattendBinding.etStartdate.setText(dateString);


                                sstartdate = dialogReqattendBinding.etStartdate.getText().toString().trim();
                            }
                        }, year, month, day);


                picker.show();


            }
        });

        dialogReqattendBinding.ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String time1 = sdf.format(dt);
                        dialogReqattendBinding.ettime.setText(time1);

                        stime = dialogReqattendBinding.ettime.getText().toString().trim();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        dialogReqattendBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Toast.makeText(getActivity(), "No Thanks", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });
        dialogReqattendBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                msg=dialogReqattendBinding.msg.getText().toString();
                AddReq(token);
                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void GetAllAttend(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<RequestListModel> requestListModelCall = ApiHandler.getApiInterface().getAllAttendList("Bearer " + access_token);
            requestListModelCall.enqueue(new Callback<RequestListModel>() {
                @Override
                public void onResponse(Call<RequestListModel> requestListModelCall1, Response<RequestListModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                responseList= response.body().getData().getResponse();
                                if (responseList.size()==0)
                                {
                                    dialog.dismiss();
                                    binding.allattdrecycler.setVisibility(View.GONE);
                                }
                                else if (responseList!=null){

                                    requestAttendanceAdapter = new RequestAttendanceAdapter(getActivity(), responseList);
                                    binding.allattdrecycler.setAdapter(requestAttendanceAdapter);
                                    binding.allattdrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }

                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<RequestListModel> call, Throwable t) {
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



    private void AddReq(final String access_token) {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<AddLeaveReq> addLeaveReqCall = ApiHandler.getApiInterface().AddRequest("Bearer " + token,ApiMap());

            addLeaveReqCall.enqueue(new Callback<AddLeaveReq>() {
                @Override
                public void onResponse(Call<AddLeaveReq> addLeaveReqCall1Call, Response<AddLeaveReq> response ) {

                    try {

                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();
                            if (status==200)
                            {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(), RequestActivity.class);
                                startActivity(intent);

                            }
                            else if(status==400)
                            {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+b, Toast.LENGTH_SHORT).show();


                            }
                            else if (status==500)
                            {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent =new Intent(getActivity(),RequestActivity.class);
                                startActivity(intent);

                            }
                            else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

                            }
                        }

                        else
                        {
                            String msg = response.body().getMeta().getMessage();
                            Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        }


                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<AddLeaveReq> call, Throwable t)
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

    private JsonObject ApiMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("type", "1");
            jsonObj_.put("date", sstartdate);
            jsonObj_.put("time", stime);
            jsonObj_.put("reason", msg);
            jsonObj_.put("image", "asdsdsdsd");


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
                    Toast.makeText(getActivity(), "Image Saved !"+bitmap, Toast.LENGTH_SHORT).show();
                    dialogReqattendBinding.primage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            dialogReqattendBinding.primage.setImageBitmap(thumbnail);

            //   String image = saveImageBitmap(thumbnail);
            Toast.makeText(getActivity(), "Image Saved"+thumbnail, Toast.LENGTH_SHORT).show();
        }
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


    @Override
    public void onResume(){
        GetAllAttend(token);
        super.onResume();

    }
}