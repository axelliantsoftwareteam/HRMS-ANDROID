package com.khazana.hrm.Fragment.Tasks;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.khazana.hrm.Adapter.Task.TodayAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.GetAllTask.Alltask.Today.GetToday;
import com.khazana.hrm.Model.GetAllTask.Alltask.Today.GetTodayData;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.khazana.hrm.databinding.DialogTaskdetailsBinding;
import com.khazana.hrm.databinding.FragmentTodayBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodayFragment extends Fragment {



    private RecyclerView.LayoutManager mLayoutManager;
    TodayAdapter todayAdapter;


    List<GetTodayData> getTodayDataList = new ArrayList<>();

    private FragmentTodayBinding binding;
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
        binding = FragmentTodayBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.todayrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        binding.todayrecycler.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
       // Toast.makeText(getActivity(), "Today", Toast.LENGTH_SHORT).show();
        GetAllTask(token);
        return view;
    }

    private void GetAllTask(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetToday> getTodayCall = ApiHandler.getApiInterface().getAllTodayList("Bearer " + access_token);
            getTodayCall.enqueue(new Callback<GetToday>() {
                @Override
                public void onResponse(Call<GetToday> getTodayCall1, Response<GetToday> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {
                                getTodayDataList = response.body().getData().getResponse();
                                if (getTodayDataList.size()==0)
                                {
                                    dialog.dismiss();
                                    binding.todayrecycler.setVisibility(View.GONE);
                                }
                                else if (getTodayDataList !=null)
                                {


                                    todayAdapter = new TodayAdapter(getActivity(), getTodayDataList);
                                    binding.todayrecycler.setAdapter(todayAdapter);
                                    binding.todayrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    // on item list clicked
                                    todayAdapter.setOnItemClickListener(new TodayAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetTodayData obj, int position)
                                        {
                                            GetTodayData getTodayData =new GetTodayData();
                                            getTodayData = getTodayDataList.get(position);
                                            showCustomDialog(getTodayData);

                                          //  Toast.makeText(getActivity(), "details", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }



                            }
                            else if (status ==400)
                            {
                                Toast.makeText(getActivity(), "Your session expired please login again!", Toast.LENGTH_SHORT).show();
                                binding.todayrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                dialog.dismiss();


                            }
                            else if (status == 401) {
                                binding.todayrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            }
                            else{
                                binding.todayrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }



                        } else {
                            binding.todayrecycler.setVisibility(View.GONE);
                            binding.txtno.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());

                            Toast.makeText(getActivity(), "Internal server error! ", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();



                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetToday> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "Internal server error! ", Toast.LENGTH_SHORT).show();
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

    private void showCustomDialog(GetTodayData getTodayData)
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

        String name= getTodayData.getName();
        String notes= getTodayData.getNotes();
        String duedate= getTodayData.getDueDate();

        int status= getTodayData.getStatus();
        if (status==1)
        {

            dialogTaskdetailsBinding.btnComplet.setVisibility(View.VISIBLE);
        }
        else {
            dialogTaskdetailsBinding.btnPend.setVisibility(View.VISIBLE);
        }

        int tag= getTodayData.getTag();
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

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}