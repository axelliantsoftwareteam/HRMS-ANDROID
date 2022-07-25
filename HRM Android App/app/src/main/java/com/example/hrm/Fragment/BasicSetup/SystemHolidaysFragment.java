
package com.example.hrm.Fragment.BasicSetup;

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

import com.example.hrm.Adapter.StaticData.HolidayAdapter;
import com.example.hrm.Adapter.StaticData.StaticDataAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.HolidayModel.GetHolidayData;
import com.example.hrm.Model.HolidayModel.GetHolidayModel;
import com.example.hrm.Model.StaticDataModel.GetDataMember.GetMemberList;
import com.example.hrm.Model.StaticDataModel.GetDataMember.GetStDataMemberModel;
import com.example.hrm.Model.StaticDataModel.GetStaticDataModel;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAdddispmemberstdtBinding;
import com.example.hrm.databinding.DialogAddholidayBinding;
import com.example.hrm.databinding.DialogEditholidayBinding;
import com.example.hrm.databinding.FragmentStaticDataBinding;
import com.example.hrm.databinding.FragmentSystemHolidaysBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SystemHolidaysFragment extends Fragment {




    private FragmentSystemHolidaysBinding binding;
    private DialogAddholidayBinding dialogAddholidayBinding;
    private DialogEditholidayBinding dialogEditholidayBinding;



    private RecyclerView.LayoutManager mLayoutManager;
    HolidayAdapter holidayAdapter;

    SessionManager sessionManager;
    String token, sname;
    int iCurrentSelection = 0;

    List<GetHolidayData> getHolidayDataList = new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSystemHolidaysBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.holidayrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.holidayrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getholiday(token);

        binding.btnaddstdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                showAddnewDialog();

            }
        });


        
        return view;




    }

    private void getholiday(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetHolidayModel> getHolidayModelCall = ApiHandler.getApiInterface().getHoliday("Bearer " + access_token);
            getHolidayModelCall.enqueue(new Callback<GetHolidayModel>() {
                @Override
                public void onResponse(Call<GetHolidayModel> getHolidayModelCall1, Response<GetHolidayModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getHolidayDataList = response.body().getData().getResponse();

                                if (getHolidayDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.holidayrecycler.setVisibility(View.GONE);

                                } else if (getHolidayDataList != null) {

                                    holidayAdapter = new HolidayAdapter(getActivity(), getHolidayDataList);
                                    binding.holidayrecycler.setAdapter(holidayAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.holidayrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    holidayAdapter.setOnItemClickListener(new HolidayAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetHolidayData obj, int position) {
                                            GetHolidayData getHolidayData = getHolidayDataList.get(position);
                                            String name =getHolidayData.getType();
                                            String val=getHolidayData.getStartDate();




                                            // Log.e("Tag", "work" + name.toString());

                                            showEditDialog();


                                        }
                                    });


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
                public void onFailure(Call<GetHolidayModel> call, Throwable t) {
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


    private void showAddnewDialog()
    {

        dialogAddholidayBinding = DialogAddholidayBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddholidayBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogAddholidayBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogAddholidayBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });






        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void showEditDialog()
    {

        dialogEditholidayBinding = DialogEditholidayBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditholidayBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditholidayBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogEditholidayBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);
                dialog.dismiss();

            }
        });






        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}