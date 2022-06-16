package com.example.hrm.Fragment.AttendanceReport;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.Toast;

import com.example.hrm.Adapter.Attendance.ClockInAdapter;
import com.example.hrm.Adapter.Attendance.ClockOutAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.CheckIn.CheckInDetail;
import com.example.hrm.Model.CheckIn.CheckInModel;
import com.example.hrm.Model.CheckIn.CheckOutDetail;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogEventBinding;
import com.example.hrm.databinding.FragmentClockInBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClockInFragment extends Fragment {


    private static final String IMAGE_DIRECTORY = "/YourDirectName";
    private Context mContext;
    private CircleImageView circleImageView;  // imageview
    private int GALLERY = 1, CAMERA = 2;

    private Button button;

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

    SessionManager sessionManager;
    String token;

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


        binding.btnReqtodayin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showCustomDialog();
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

        return view;
    }

    private void GetCheckIn(final String access_token) {
        try {

            Call<CheckInModel> checkInModelCall = ApiHandler.getApiInterface().getClock("Bearer " + access_token);

           //  Call<UserModel> registerCall = ApiHandler.getApiInterface().getUser(ApiMapJson());
            checkInModelCall.enqueue(new Callback<CheckInModel>() {
                @Override
                public void onResponse(Call<CheckInModel> checkInModelCall, Response<CheckInModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                checkInDetails = response.body().getData().getResponse().getCheckInDetails();
                                checkOutDetails = response.body().getData().getResponse().getCheckOutDetails();

                                clockInAdapter = new ClockInAdapter(getActivity(), checkInDetails);
                                mRecyclerView.setAdapter(clockInAdapter);

                                clockOutAdapter = new ClockOutAdapter(getActivity(), checkOutDetails);
                                RecyclerView.setAdapter(clockOutAdapter);

                            }

                        } else {

                            Toast.makeText(getActivity(), "" + response.errorBody(), Toast.LENGTH_SHORT).show();

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
                public void onFailure(Call<CheckInModel> call, Throwable t) {
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

        dialogEventBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Out", Toast.LENGTH_SHORT).show();


            }
        });

        dialogEventBinding.profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int requestCode = 200;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissions, requestCode);
                    showPictureDialog();
                }


            }
        });

        dialogEventBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Save", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

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
//
//                dialog.dismiss();
//            }
//        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
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
                    Toast.makeText(getActivity(), "Image Saved !", Toast.LENGTH_SHORT).show();
                    circleImageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(thumbnail);

            //  String image = saveImageBitmap(thumbnail);
            Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT).show();
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