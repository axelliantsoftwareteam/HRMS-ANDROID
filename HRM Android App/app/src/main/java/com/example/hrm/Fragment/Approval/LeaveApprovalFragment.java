package com.example.hrm.Fragment.Approval;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
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

import com.example.hrm.Adapter.Approval.ApprovalLeaveAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Approval.LeavesApproval.GetLeaveApprolModel;
import com.example.hrm.Model.Approval.LeavesApproval.LeaveApproval;
import com.example.hrm.Model.Approval.LeavesApproval.WorkFlow;
import com.example.hrm.Model.Memberlist.MemberResponse;
import com.example.hrm.Model.Memberlist.Members;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogLeaveapprovBinding;
import com.example.hrm.databinding.FragmentLeaveApprovalBinding;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveApprovalFragment extends Fragment {


    private RecyclerView.LayoutManager mLayoutManager;
    ApprovalLeaveAdapter approvalLeaveAdapter;


    SessionManager sessionManager;
    String token, sname;


    List<LeaveApproval> leaveApprovalList = new ArrayList<>();
    List<WorkFlow> workFlowList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentLeaveApprovalBinding binding;

    private DialogLeaveapprovBinding dialogLeaveapprovBinding;

    List<MemberResponse> memberResponses = new ArrayList<>();
    int iCurrentSelection = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLeaveApprovalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.leaveapprrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.leaveapprrecycler.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
        //    getmember(token);
        GetAllAttend(token);

        loadmemberlist();

        return view;

    }

    public void loadmemberlist()
    {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences",MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("member", null);
        //    Log.e("Tag", "" + json.toString());
//        Toast.makeText(getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<MemberResponse>>() {}.getType();
        memberResponses = gson.fromJson(json, type);

        if (memberResponses == null)
        {

            memberResponses = new ArrayList<>();
        }
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
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
        //setting adapter to spinner
        binding.spinnerlist.setAdapter(adapter);

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

                // sometimes you need nothing here
            }
        });

//        for (int i = 0; i<memberResponseList.size(); i++ )
//        {
//            memberResponse = memberResponseList.get(i);
//            String name = memberResponse.getName();
//            Toast.makeText(EditprofActivity.this, ""+name, Toast.LENGTH_SHORT).show();
//
//        }
    }


//    public void getmember(final String access_token) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(getActivity());
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//            Call<Members> membersCall = ApiHandler.getApiInterface().getlistMember("Bearer " + access_token);
//            Log.e("Tag", "response" + membersCall.toString());
//
//            membersCall.enqueue(new Callback<Members>() {
//                @Override
//                public void onResponse(Call<Members> membersCall1, Response<Members> response) {
//
//                    try {
//                        if (response.isSuccessful()) {
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200) {
//                                memberResponses = response.body().getData().getResponse();
//
//                                Log.e("Tag", "respone" + memberResponses.toString());
//
//                                // setspiner(memberResponses);
//                                //String array to store all the book names
//                                String[] items = new String[memberResponses.size()];
//
//                                //Traversing through the whole list to get all the names
//                                for (int i = 0; i < memberResponses.size(); i++) {
//                                    //Storing names to string array
//                                    items[i] = memberResponses.get(i).getName();
//                                }
//
//                                //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
//                                ArrayAdapter<String> adapter;
//                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
//                                //setting adapter to spinner
//                                binding.spinnerlist.setAdapter(adapter);
//
//
//                                dialog.dismiss();
//
//
//                                //   Toast.makeText(AddLeave.this, ""+memberResponses, Toast.LENGTH_SHORT).show();
//                            }
//
//                            binding.spinnerlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                @Override
//                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                    if (iCurrentSelection == position) {
//                                        return;
//                                    } else {
//                                        Integer idd = memberResponses.get(position).getId();
//                                        Log.e("Tag", "idd=" + idd.toString());
//                                        sname = parent.getItemAtPosition(position).toString();
//                                        Log.e("Tag", "member=" + sname.toString());
//                                        GetAllAttendByID(token, idd);
//                                        iCurrentSelection = 0;
//
//                                    }
//                                    // Your code here
//                                    iCurrentSelection = position;
//
//
//                                }
//
//                                @Override
//                                public void onNothingSelected(AdapterView<?> parent) {
////                                        Toast.makeText(getActivity(), "no selected", Toast.LENGTH_SHORT).show();
////                                        Log.e("Tag", "memberno=" + sname.toString());
//                                    // sometimes you need nothing here
//                                }
//                            });
//
//
//                        } else {
//
//                            Toast.makeText(getActivity(), "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            dialog.dismiss();
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
//                public void onFailure(Call<Members> call, Throwable t) {
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

    // Get all Attendacne Approval

    private void GetAllAttend(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetLeaveApprolModel> getLeaveApprolModelCall = ApiHandler.getApiInterface().getLeaveApp("Bearer " + access_token, 0, 0, 5, " ", "Desc", null);
            getLeaveApprolModelCall.enqueue(new Callback<GetLeaveApprolModel>() {
                @Override
                public void onResponse(Call<GetLeaveApprolModel> getLeaveApprolModelCall1, Response<GetLeaveApprolModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                leaveApprovalList = response.body().getData().getLeaves();


                                if (leaveApprovalList.size() == 0) {
                                    dialog.dismiss();
                                    binding.leaveapprrecycler.setVisibility(View.GONE);
                                } else if (leaveApprovalList != null) {

                                    approvalLeaveAdapter = new ApprovalLeaveAdapter(getActivity(), leaveApprovalList);
                                    binding.leaveapprrecycler.setAdapter(approvalLeaveAdapter);
                                    binding.leaveapprrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    approvalLeaveAdapter.setOnItemClickListener(new ApprovalLeaveAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, LeaveApproval obj, int position) {
                                            LeaveApproval leaveApproval = new LeaveApproval();
                                            WorkFlow workFlow = new WorkFlow();
                                            leaveApproval = leaveApprovalList.get(position);
                                            //  Log.e("Tag", "work" + response1.toString());

                                            workFlowList = leaveApproval.getWorkFlow();


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
                public void onFailure(Call<GetLeaveApprolModel> call, Throwable t) {
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
            Call<GetLeaveApprolModel> getLeaveApprolModelCall = ApiHandler.getApiInterface().getLeaveApp("Bearer " + access_token, empid, 0, 5, " ", "Desc", null);
            getLeaveApprolModelCall.enqueue(new Callback<GetLeaveApprolModel>() {
                @Override
                public void onResponse(Call<GetLeaveApprolModel> getLeaveApprolModelCall1, Response<GetLeaveApprolModel> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                leaveApprovalList = response.body().getData().getLeaves();
                                if (leaveApprovalList.size() == 0) {
                                    dialog.dismiss();
                                    binding.leaveapprrecycler.setVisibility(View.GONE);
                                }
                                else if (leaveApprovalList != null)
                                {

                                    approvalLeaveAdapter = new ApprovalLeaveAdapter(getActivity(), leaveApprovalList);
                                    binding.leaveapprrecycler.setAdapter(approvalLeaveAdapter);
                                    binding.leaveapprrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    approvalLeaveAdapter.setOnItemClickListener(new ApprovalLeaveAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, LeaveApproval obj, int position) {
                                            LeaveApproval leaveApproval = new LeaveApproval();
                                            WorkFlow workFlow = new WorkFlow();
                                            leaveApproval = leaveApprovalList.get(position);
                                            //  Log.e("Tag", "work" + response1.toString());

                                            workFlowList = leaveApproval.getWorkFlow();


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
                public void onFailure(Call<GetLeaveApprolModel> call, Throwable t) {
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

        dialogLeaveapprovBinding = DialogLeaveapprovBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogLeaveapprovBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Integer type = workFlow.getType();
        String procname = workFlow.getProcessName();
        String displayName = workFlow.getDisplayName();
        String comments = workFlow.getComments();

        dialogLeaveapprovBinding.apprtask.setText(Integer.toString(type));
        dialogLeaveapprovBinding.apprproname.setText(procname);
        dialogLeaveapprovBinding.apprdate.setText(displayName);
        dialogLeaveapprovBinding.apprdisplyname.setText(comments);


        Boolean status = workFlow.getStatus();
        if (status == true) {

            dialogLeaveapprovBinding.approved.setVisibility(View.VISIBLE);
            dialogLeaveapprovBinding.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  AddReq(token);
                    dialog.dismiss();

                }
            });
        } else {

            dialogLeaveapprovBinding.approve.setVisibility(View.VISIBLE);
            dialogLeaveapprovBinding.reject.setVisibility(View.VISIBLE);

            dialogLeaveapprovBinding.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  AddReq(token);
                    Toast.makeText(getActivity(), "Please wait", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

            dialogLeaveapprovBinding.reject.setVisibility(View.VISIBLE);
            dialogLeaveapprovBinding.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

        }
        dialogLeaveapprovBinding.imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}