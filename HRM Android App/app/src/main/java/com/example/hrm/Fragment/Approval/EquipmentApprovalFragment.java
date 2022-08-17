package com.example.hrm.Fragment.Approval;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.hrm.Adapter.Approval.ApprovalAttendanceAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Approval.AttendApproval.AttendApproval;
import com.example.hrm.Model.Approval.AttendApproval.GetAttendApprolModel;
import com.example.hrm.Model.Approval.AttendApproval.WorkFlow;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAttendapprovBinding;
import com.example.hrm.databinding.FragmentAttendanceApprovalBinding;
import com.example.hrm.databinding.FragmentEquipmentApprovalBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EquipmentApprovalFragment extends Fragment {


    private FragmentEquipmentApprovalBinding binding;



    SessionManager sessionManager;
    String token, sname;
    int iCurrentSelection = 0;
    List<MemberResponse> memberResponses = new ArrayList<>();





    private RecyclerView.LayoutManager mLayoutManager;
    ApprovalAttendanceAdapter approvalAttendanceAdapter;
    List<AttendApproval> attendApprovalList = new ArrayList<>();
    List<WorkFlow> workFlowList = new ArrayList<>();

    private DialogAttendapprovBinding dialogAttendapprovBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEquipmentApprovalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();



        binding.equapprrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.equapprrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
        getmember(token);
        GetAllAttend(token);
        return view;


    }


    public void getmember(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<Members> membersCall = ApiHandler.getApiInterface().getlistMember("Bearer " + access_token);
            Log.e("Tag", "response" + membersCall.toString());

            membersCall.enqueue(new Callback<Members>() {
                @Override
                public void onResponse(Call<Members> membersCall1, Response<Members> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                memberResponses = response.body().getData().getResponse();

                                Log.e("Tag", "respone" + memberResponses.toString());

                                // setspiner(memberResponses);
                                //String array to store all the book names
                                String[] items = new String[memberResponses.size()];

                                //Traversing through the whole list to get all the names
                                for (int i = 0; i < memberResponses.size(); i++) {
                                    //Storing names to string array
                                    items[i] = memberResponses.get(i).getName();
                                }

                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
                                ArrayAdapter<String> adapter;
                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                                //setting adapter to spinner
                                binding.spinnerlist.setAdapter(adapter);


                                dialog.dismiss();


                                //   Toast.makeText(AddLeave.this, ""+memberResponses, Toast.LENGTH_SHORT).show();
                            }

                            binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (iCurrentSelection == position) {
                                        return;
                                    } else {
                                        Integer idd = memberResponses.get(position).getId();
                                        Log.e("Tag", "idd=" + idd.toString());
                                        sname = parent.getItemAtPosition(position).toString();
                                        Log.e("Tag", "member=" + sname.toString());
                                        GetAllAttendByID(token, idd);
                                        iCurrentSelection = 0;

                                    }
                                    // Your code here
                                    iCurrentSelection = position;


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
//                                        Toast.makeText(getActivity(), "no selected", Toast.LENGTH_SHORT).show();
//                                        Log.e("Tag", "memberno=" + sname.toString());
                                    // sometimes you need nothing here
                                }
                            });


                        } else {

                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

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

    private void GetAllAttend(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAttendApprolModel> getAttendApprolModelCall = ApiHandler.getApiInterface().getAttendApp("Bearer " + access_token, 0, 0, 5, " ", "Desc", null);
            getAttendApprolModelCall.enqueue(new Callback<GetAttendApprolModel>() {
                @Override
                public void onResponse(Call<GetAttendApprolModel> getAttendApprolModelCall1, Response<GetAttendApprolModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                attendApprovalList = response.body().getData().getResponse();


                                if (attendApprovalList.size() == 0) {
                                    dialog.dismiss();
                                    binding.equapprrecycler.setVisibility(View.GONE);
                                } else if (attendApprovalList != null) {

                                    approvalAttendanceAdapter = new ApprovalAttendanceAdapter(getActivity(), attendApprovalList);
                                    binding.equapprrecycler.setAdapter(approvalAttendanceAdapter);
                                    binding.equapprrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    approvalAttendanceAdapter.setOnItemClickListener(new ApprovalAttendanceAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, AttendApproval obj, int position) {
                                            AttendApproval attendApproval1 = new AttendApproval();
                                            WorkFlow workFlow = new WorkFlow();
                                            attendApproval1 = attendApprovalList.get(position);
                                            //  Log.e("Tag", "work" + response1.toString());

                                            workFlowList = attendApproval1.getWorkFlow();



                                            workFlow = workFlowList.get(0);

                                            showCustomDialog(workFlow);

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
                public void onFailure(Call<GetAttendApprolModel> call, Throwable t) {
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


    // end


//  start   Base on Id

    private void GetAllAttendByID(final String access_token, Integer empid) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAttendApprolModel> getAttendApprolModelCall = ApiHandler.getApiInterface().getAttendApp("Bearer " + access_token, empid, 0, 5, " ", "Desc", null);
            getAttendApprolModelCall.enqueue(new Callback<GetAttendApprolModel>() {
                @Override
                public void onResponse(Call<GetAttendApprolModel> getAttendApprolModelCall1, Response<GetAttendApprolModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                attendApprovalList = response.body().getData().getResponse();
                                if (attendApprovalList.size() == 0) {
                                    dialog.dismiss();
                                    binding.equapprrecycler.setVisibility(View.GONE);
                                } else if (attendApprovalList != null) {

                                    approvalAttendanceAdapter = new ApprovalAttendanceAdapter(getActivity(), attendApprovalList);
                                    binding.equapprrecycler.setAdapter(approvalAttendanceAdapter);
                                    binding.equapprrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    approvalAttendanceAdapter.setOnItemClickListener(new ApprovalAttendanceAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, AttendApproval obj, int position) {
                                            AttendApproval attendApproval1 = new AttendApproval();
                                            WorkFlow workFlow = new WorkFlow();
                                            attendApproval1 = attendApprovalList.get(position);
                                            //  Log.e("Tag", "work" + response1.toString());

                                            workFlowList = attendApproval1.getWorkFlow();



                                            workFlow = workFlowList.get(0);

                                            showCustomDialog(workFlow);

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
                public void onFailure(Call<GetAttendApprolModel> call, Throwable t) {
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


    private void showCustomDialog(WorkFlow workFlow) {

        dialogAttendapprovBinding = DialogAttendapprovBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAttendapprovBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Integer type = workFlow.getType();
        String procname = workFlow.getProcessName();
        String displayName = workFlow.getDisplayName();
        String comments = workFlow.getComments();

        dialogAttendapprovBinding.apprtask.setText(Integer.toString(type));
        dialogAttendapprovBinding.apprproname.setText(procname);
        dialogAttendapprovBinding.apprdate.setText(displayName);
        dialogAttendapprovBinding.apprdisplyname.setText(comments);



        Boolean status = workFlow.getStatus();
        if (status == true) {

            dialogAttendapprovBinding.approved.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //  AddReq(token);
                    dialog.dismiss();

                }
            });
        }
        else
        {

            dialogAttendapprovBinding.approve.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.reject.setVisibility(View.VISIBLE);

            dialogAttendapprovBinding.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //  AddReq(token);
                    Toast.makeText(getActivity(), "Please wait", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

            dialogAttendapprovBinding.reject.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

        }
        dialogAttendapprovBinding.imgclose.setOnClickListener(new View.OnClickListener() {
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