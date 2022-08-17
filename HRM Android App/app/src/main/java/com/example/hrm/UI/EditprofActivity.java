package com.example.hrm.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Building.TaskAdded.TaskAddedModel;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.UserProfileModel.Buildings.GetBuildingDataList;
import com.example.hrm.Model.UserProfileModel.EditProfile;
import com.example.hrm.Model.UserProfileModel.Gender.GetGender;
import com.example.hrm.Model.UserProfileModel.Gender.GetGenderData;
import com.example.hrm.Model.UserProfileModel.Salutation.GetSalutation;
import com.example.hrm.Model.UserProfileModel.Salutation.GetSalutationData;
import com.example.hrm.Model.UserProfileModel.UserData.EditProfile.EditProf;
import com.example.hrm.Model.UserProfileModel.UserData.GetUserData;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.ActivityEditprofBinding;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditprofActivity extends AppCompatActivity {


    private ActivityEditprofBinding binding;
    SessionManager sessionManager;
    GetUserData obj;


    String name, email, dob, cnic, oldpwd, reprtto, empcode, depart, building;

    String gtname, gtemail, gtdob, getcnic, gtoldpwd, gtnewpwd, gtreport, profimage;
    List<GetGenderData> getGenderDataList = new ArrayList<>();

    List<GetSalutationData> getSalutationDataList = new ArrayList<>();

    List<GetBuildingDataList> getBuildingDataLists = new ArrayList<>();

    GetUserData getUserData = new GetUserData();
    EditProfile editProfile = new EditProfile();
    private int GALLERY = 1, CAMERA = 2;


    private String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.CAMERA"};

    String token;
    private int iCurrentSelection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(EditprofActivity.this);

        token = sessionManager.getToken();

        SharedPreferences mPrefs = getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = mPrefs.getString("userprof", "");
        obj = gson.fromJson(json, GetUserData.class);


        binding.etname.setText(obj.getName());

        editProfile.setName(binding.etname.getText().toString().trim());


//        cnic = sessionManager.getCinic();
        binding.etetcnic.setText(obj.getCnic());
        editProfile.setCnic(binding.etetcnic.getText().toString().trim());

//        email = sessionManager.getUserEmail();
        binding.etemail.setText(obj.getEmail());
        editProfile.setEmail(binding.etemail.getText().toString().trim());


//        dob = sessionManager.getDob();
        binding.etdob.setText(obj.getDob());
        editProfile.setDob(binding.etdob.getText().toString().trim());



        editProfile.setNew_password(binding.etnpasswd.getText().toString().trim());

//        oldpwd = sessionManager.getPassword();

        editProfile.setConfirm_password(binding.etconfirmpasswd.getText().toString().trim());

//
//        gtoldpwd = binding.etoldpasswd.getText().toString().trim();
//
//
//        gtnewpwd = binding.etnpasswd.getText().toString().trim();


//        reprtto = sessionManager.getReportTo();
//        binding.spDirectrept.setText(reprtto);


        binding.report.setText(obj.getReportingTo().getName());

        binding.spDirectrept.setText(obj.getReportingTo().getName());


        depart = sessionManager.getDepartment();
        binding.spDeprt.setText(depart);


        // binding.spDeprt.setText(obj.get);


//        empcode = sessionManager.getEmpcode();
//        binding.empcode.setText(empcode);

        binding.empcode.setText(obj.getEmployeeCode());


//        building = sessionManager.getBuilding();
//        binding.spBuild.setEnabled();

        profimage = obj.getProfilePicture();
        //   Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).dontAnimate().into(binding.profileimage);;
        Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).error(R.drawable.ic_baseline_account_circle_24).into(binding.primage);


        loadsalutation();
        loadgender();
        loadbuild();



        // getprofile();

        //  getdialogmember(token);

        //   getgender(token);


//        cnic = sessionManager.getCinic();
//        binding.etetcnic.setText(cnic);
//        getcnic = binding.etetcnic.getText().toString().trim();
//
//        email = sessionManager.getUserEmail();
//        binding.etemail.setText(email);
//        gtemail = binding.etemail.getText().toString().trim();
//
//
//        dob = sessionManager.getDob();
//        binding.etdob.setText(dob);
//        gtdob = binding.etdob.getText().toString().trim();
//
//
//        oldpwd = sessionManager.getPassword();
//        binding.etoldpasswd.setText(oldpwd);
//
//        gtoldpwd = binding.etoldpasswd.getText().toString().trim();
//
//
//        gtnewpwd = binding.etnpasswd.getText().toString().trim();
//
//        reprtto = sessionManager.getReportTo();
//        binding.report.setText(reprtto);
//
//        binding.spDirectrept.setText(reprtto);
//
//        depart = sessionManager.getDepartment();
//        binding.spDeprt.setText(depart);
//
//
//        empcode = sessionManager.getEmpcode();
//        binding.empcode.setText(empcode);
//
//        building = sessionManager.getBuilding();
//        binding.spBuilding.setText(building);

//        profimage = sessionManager.getProfimg();
//
//        //   Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).dontAnimate().into(binding.profileimage);;
//        Glide.with(this).load(Base64.decode(profimage, Base64.DEFAULT)).placeholder(R.drawable.ic_baseline_account_circle_24).error(R.drawable.ic_baseline_account_circle_24).into(binding.primage);


        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditprofActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        binding.btnAddleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Tag", "name="+editProfile.getName());
                Log.e("Tag", "profilimgae="+editProfile.getProfile_picture());
                Log.e("Tag", "cnic="+editProfile.getCnic());
                Log.e("Tag", "email="+editProfile.getEmail());
                Log.e("Tag", "dob="+editProfile.getDob());
                Log.e("Tag", "confir_pwd="+editProfile.getConfirm_password());
                Log.e("Tag", "new_pwd="+editProfile.getNew_password());


                editProf(editProfile);

//                Intent i = new Intent(EditprofActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
            }
        });


        binding.primage.setOnClickListener(new View.OnClickListener() {
            @Override
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


    }

    private void editProf(EditProfile editProfile) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(EditprofActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Log.d("Tag",""+editProfile.toString());

            Call<EditProf> editProfCall = ApiHandler.getApiInterface().editprof("Bearer " + token, editProfile);
            editProfCall.enqueue(new Callback<EditProf>()
            {

                @Override
                public void onResponse(Call<EditProf> editProfCall1, Response<EditProf> response) {

                    try {
                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200)
                            {


                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Toast.makeText(EditprofActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditprofActivity.this, More.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (status == 400)
                            {
                                Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                                Toast.makeText(EditprofActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


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
                public void onFailure(Call<EditProf> call, Throwable t) {
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

    public void getprofile()
    {

    }


    public void loadsalutation() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("salutation", null);
//        Log.e("Tag", "" + json.toString());
//        Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<GetSalutationData>>() {
        }.getType();
        getSalutationDataList = gson.fromJson(json, type);

        if (getSalutationDataList == null) {

            getSalutationDataList = new ArrayList<>();
        }
        String[] items = new String[getSalutationDataList.size()];


        for (int i = 0; i < getSalutationDataList.size(); i++) {

            items[i] = getSalutationDataList.get(i).getValue();
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(EditprofActivity.this, android.R.layout.simple_list_item_1, items);
        binding.spSalu.setAdapter(adapter);
        binding.spSalu.setSelection(getSalutationDataList.indexOf(getSalutationDataList.lastIndexOf(obj.getSalutation())));//set selected value in spinner


        binding.spSalu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //       String sname = parent.getItemAtPosition(position).toString();
                if (iCurrentSelection == position) {
                    return;
                } else {
                    editProfile.setSalutation(getSalutationDataList.get(position).getId());
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

    public void loadbuild() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("bulid", null);
//        Log.e("Tag", "" + json.toString());
//        Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<GetBuildingDataList>>() {
        }.getType();
        getBuildingDataLists = gson.fromJson(json, type);

        if (getBuildingDataLists == null) {

            getBuildingDataLists = new ArrayList<>();
        }
        String[] items = new String[getBuildingDataLists.size()];


        for (int i = 0; i < getBuildingDataLists.size(); i++) {

            items[i] = getBuildingDataLists.get(i).getName();
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(EditprofActivity.this, android.R.layout.simple_list_item_1, items);
        binding.spbuild.setAdapter(adapter);
//        binding.spbuild.setSelection(getBuildingDataLists.indexOf(getBuildingDataLists.get(obj.getBuilding())));//set selected value in spinner


        binding.spbuild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String sname = parent.getItemAtPosition(position).toString();

                if (iCurrentSelection == position) {
                    return;
                } else {
                    editProfile.setBuilding(getBuildingDataLists.get(position).getId());
                    // sname = parent.getItemAtPosition(position).toString();
                    //  Log.e("Tag", "member=" + sname.toString());
                    // GetAllAttendByID(token, idd);
                    iCurrentSelection = 0;

                }
                // Your code here
                iCurrentSelection = position;


//                Log.e("Tag", "member=" + sname.toString());
//                Log.e("Tag", "idd=" + idd.toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

    }

    public void loadgender() {

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("gender", null);
//        Log.e("Tag", "" + json.toString());
//        Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<GetGenderData>>() {
        }.getType();
        getGenderDataList = gson.fromJson(json, type);

        if (getGenderDataList == null) {

            getGenderDataList = new ArrayList<>();
        }
        // setspiner(getGenderDataList);
        //String array to store all the book names
        String[] items = new String[getGenderDataList.size()];

        //Traversing through the whole list to get all the names
        for (int i = 0; i < getGenderDataList.size(); i++) {
            //Storing names to string array
            items[i] = getGenderDataList.get(i).getValue();
        }

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(EditprofActivity.this, android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        binding.spGender.setAdapter(adapter);
        binding.spGender.setSelection(getGenderDataList.indexOf(getGenderDataList.lastIndexOf(obj.getGender())));//set selected value in spinner


        binding.spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                editProfile.setGender(getGenderDataList.get(position).getId());
                String sname = parent.getItemAtPosition(position).toString();
//                Log.e("Tag", "member=" + sname.toString());
//                Log.e("Tag", "idd=" + idd.toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


    }


    // for image save

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(EditprofActivity.this);
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
        if (resultCode == EditprofActivity.this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditprofActivity.this.getContentResolver(), contentURI);
                    //     String path = saveImageBitmap(bitmap);
                    Toast.makeText(EditprofActivity.this, "Image Saved !" + bitmap, Toast.LENGTH_SHORT).show();
                    binding.primage.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditprofActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            binding.primage.setImageBitmap(thumbnail);
            //  final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            String encodedImage = encodeImage(thumbnail);
           // String image="aasdsdsdasdasd";

            editProfile.setProfile_picture(encodedImage);

            //   String image = saveImageBitmap(thumbnail);

           // Log.e("Tag", "" + encodedImage.toString());
          //  Toast.makeText(EditprofActivity.this, "Image Saved" + encodedImage, Toast.LENGTH_SHORT).show();

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
            if (EditprofActivity.this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(EditprofActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    // complete the images save


//    public ArrayList<Object> getArrayList(String key){
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(EditprofActivity.this);
//        Gson gson = new Gson();
//        String json = prefs.getString(key, null);
//        Type type = new TypeToken<ArrayList<String>>() {}.getType();
//        return gson.fromJson(json, type);
//    }


    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(EditprofActivity.this, More.class);
        startActivity(i);
        finish();

    }
}