package com.example.hrm.Adapter.Attendance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.media.metrics.Event;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.CheckIn.CheckInDetail;
import com.example.hrm.R;
import com.example.hrm.UI.AddLeave;
import com.example.hrm.databinding.DialogEventBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ClockInAdapter extends RecyclerView.Adapter<ClockInAdapter.MyViewHolder> {
    Context context;
    CheckInDetail checkInDetail;
    private OnItemClickListener mOnItemClickListener;
    private List<CheckInDetail> checkInDetailList;

    public interface OnItemClickListener {
        void onItemClick(View view, CheckInDetail obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) mItemClickListener;
    }
    public ClockInAdapter(Context context, List<CheckInDetail> checkInDetailList) {
        this.context = context;
        this.checkInDetailList = checkInDetailList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView day;
        public TextView date;
        public TextView today;


        public MyViewHolder(View v) {
            super(v);
            day = v.findViewById(R.id.txt_twoin);
            date = v.findViewById(R.id.txt_reqtwoin);

        }
    }


    @NonNull
    @Override
    public ClockInAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clockin_layout, parent, false);

        ClockInAdapter.MyViewHolder vh = new ClockInAdapter.MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ClockInAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        checkInDetail = checkInDetailList.get(position);

        //binding the data with the viewholder views
        holder.day.setText(checkInDetail.getDay());
      //  holder.today.setText(checkInDetail.getResponse().getCheckIn());


        if(checkInDetail.getType().equals("1"))
        {
            holder.date.setTextColor(Color.parseColor("#b38e34"));
            holder.date.setText(checkInDetail.getHours());
        }
        if(checkInDetail.getType().equals("2"))
        {
            holder.date.setTextColor(Color.parseColor("#1565C0"));
            holder.date.setText("Request Check-In");

            holder.date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(v, checkInDetailList.get(position), position);
                        }
                        //showCustomDialog();
                    }
                }
            });

        }
        if(checkInDetail.getType().equals("4"))
        {
            holder.date.setTextColor(Color.parseColor("#b38e34"));
            holder.date.setText("Weekend");
        }
        if(checkInDetail.getType().equals("10"))
        {
            holder.date.setTextColor(Color.parseColor("#b38e34"));
            holder.date.setText("Request Pending");
        }




    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return checkInDetailList.size();
    }


    @Override
    public int getItemViewType(int i) {
        return 0;
    }


    public void showCustomDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

         TextView spn_from_date = dialog.findViewById(R.id.etStartdate);
         TextView spn_from_time = dialog.findViewById(R.id.etTimeFrom);
         TextView spn_to_reason = dialog.findViewById(R.id.et_reason);
         Button nothank = dialog.findViewById(R.id.btn_nothank);
         Button save = dialog.findViewById(R.id.btn_update);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(calendar.getTime());
        spn_from_date.setText(dateString);




        spn_from_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                    {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                        String time1 = sdf.format(dt);
                        spn_from_time.setText(time1);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });






        nothank.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

//                Event event = new Event();
//                event.email = tv_email.getText().toString();
//                event.name = et_name.getText().toString();
//                event.location = et_location.getText().toString();
//                event.from = spn_from_date.getText().toString() + " (" + spn_from_time.getText().toString() + ")";
//                event.to = spn_to_date.getText().toString() + " (" + spn_to_time.getText().toString() + ")";
//                event.is_allday = cb_allday.isChecked();
//                event.timezone = spn_timezone.getSelectedItem().toString();
//                displayDataResult(event);

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

//    private void showPictureDialog() {
//        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
//        pictureDialog.setTitle("Select Action");
//        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
//        pictureDialog.setItems(pictureDialogItems,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                choosePhotoFromGallary();
//                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
//                        }
//                    }
//                });
//        pictureDialog.show();
//    }
//
//    public void choosePhotoFromGallary() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, GALLERY);
//    }
//
//    private void takePhotoFromCamera() {
//        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, CAMERA);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == getActivity().RESULT_CANCELED) {
//            return;
//        }
//        if (requestCode == GALLERY) {
//            if (data != null) {
//                Uri contentURI = data.getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
//                    //     String path = saveImageBitmap(bitmap);
//                    Toast.makeText(getActivity(), "Image Saved !", Toast.LENGTH_SHORT).show();
//                    circleImageView.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//        } else if (requestCode == CAMERA) {
//            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            circleImageView.setImageBitmap(thumbnail);
//
//            //  String image = saveImageBitmap(thumbnail);
//            Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public boolean isStoragePermissionGranted() {
//        String TAG = "Storage Permission";
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    == PackageManager.PERMISSION_GRANTED) {
//                Log.v(TAG, "Permission is granted");
//                return true;
//            } else {
//                Log.v(TAG, "Permission is revoked");
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                return false;
//            }
//        } else { //permission is automatically granted on sdk<23 upon installation
//            Log.v(TAG, "Permission is granted");
//            return true;
//        }
//    }
//
//    public void saveImageBitmap(Bitmap image_bitmap, String image_name) {
//        String root = Environment.getExternalStorageDirectory().toString();
//        if (isStoragePermissionGranted()) { // check or ask permission
//            File myDir = new File(root, "/saved_images");
//            if (!myDir.exists()) {
//                myDir.mkdirs();
//            }
//            String fname = "Image-" + image_name + ".jpg";
//            File file = new File(myDir, fname);
//            if (file.exists()) {
//                file.delete();
//            }
//            try {
//                file.createNewFile(); // if file already exists will do nothing
//                FileOutputStream out = new FileOutputStream(file);
//                image_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                out.flush();
//                out.close();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            MediaScannerConnection.scanFile(getActivity(), new String[]{file.toString()}, new String[]{file.getName()}, null);
//        }
//    }

}