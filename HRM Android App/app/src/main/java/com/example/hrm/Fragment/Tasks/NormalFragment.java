package com.example.hrm.Fragment.Tasks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.hrm.Adapter.Task.AllTaskAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.GetAllTask.GetAllTaskData;
import com.example.hrm.Model.GetAllTask.GetAllTaskModel;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogTaskdetailsBinding;
import com.example.hrm.databinding.FragmentLowBinding;
import com.example.hrm.databinding.FragmentNormalBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NormalFragment extends Fragment {




    private RecyclerView.LayoutManager mLayoutManager;
    AllTaskAdapter allTaskAdapter;


    List<GetAllTaskData> getAllTaskDataList = new ArrayList<>();

    private FragmentNormalBinding binding;
    private DialogTaskdetailsBinding dialogTaskdetailsBinding;
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
        binding = FragmentNormalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.alltaskrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        binding.alltaskrecycler.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
        GetAllTask(token);
        return view;
    }
    private void GetAllTask(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAllTaskModel> getAllTaskModelCall = ApiHandler.getApiInterface().getnormalList("Bearer " + access_token);
            getAllTaskModelCall.enqueue(new Callback<GetAllTaskModel>() {
                @Override
                public void onResponse(Call<GetAllTaskModel> getAllTaskModelCall1, Response<GetAllTaskModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {
                                getAllTaskDataList = response.body().getData().getResponse();
                                if (getAllTaskDataList.size()==0)
                                {
                                    dialog.dismiss();
                                    binding.alltaskrecycler.setVisibility(View.GONE);
                                }
                                else if (getAllTaskDataList !=null)
                                {


                                    allTaskAdapter = new AllTaskAdapter(getActivity(), getAllTaskDataList);
                                    binding.alltaskrecycler.setAdapter(allTaskAdapter);
                                    binding.alltaskrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    // on item list clicked
                                    allTaskAdapter.setOnItemClickListener(new AllTaskAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetAllTaskData obj, int position)
                                        {
                                            GetAllTaskData getAllTaskData1 =new GetAllTaskData();
                                            getAllTaskData1 = getAllTaskDataList.get(position);
                                            showCustomDialog(getAllTaskData1);

                                            Toast.makeText(getActivity(), "details", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }



                            }

                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            dialog.dismiss();



                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetAllTaskModel> call, Throwable t) {
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

    private void showCustomDialog(GetAllTaskData getAllTaskData)
    {

        dialogTaskdetailsBinding = DialogTaskdetailsBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogTaskdetailsBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        String name= getAllTaskData.getName();
        String notes= getAllTaskData.getNotes();
        String duedate= getAllTaskData.getDueDate();

        int status= getAllTaskData.getStatus();
        if (status==1)
        {

            dialogTaskdetailsBinding.btnComplet.setVisibility(View.VISIBLE);
        }
        else {
            dialogTaskdetailsBinding.btnPend.setVisibility(View.VISIBLE);
        }

        int tag= getAllTaskData.getTag();
        if (tag==1)
        {

            dialogTaskdetailsBinding.tasktag.setText("Normal");
        }
        else if (tag==0)
        {

            dialogTaskdetailsBinding.tasktag.setText("Low");
        }
        else if (tag==2)
        {

            dialogTaskdetailsBinding.tasktag.setText("High");
        }


        dialogTaskdetailsBinding.taskname.setText(name);
        dialogTaskdetailsBinding.tasknotes.setText(notes);
        dialogTaskdetailsBinding.txtdate.setText(duedate);




//        dialogEventBinding.primage.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                int requestCode = 200;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(permissions, requestCode);
//                    //  showPictureDialog();
////                    takePhotoFromCamera();
////                    dispatchTakePictureIntent();
//                    showPictureDialog();
//
//                }
//
//
//            }
//        });

//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = format.format(calendar.getTime());
//        dialogEventBinding.etStartdate.setText(dateString);
//        sdate = dialogEventBinding.etStartdate.getText().toString().trim();
//





//        dialogEventBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                msg=dialogEventBinding.msg.getText().toString();
//                AddReq(token);
//                dialog.dismiss();
//
//            }
//        });




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
}