package com.khazana.hrm.Fragment.Approval;

import static android.content.Context.MODE_PRIVATE;

import static com.microsoft.identity.common.internal.cache.SharedPreferencesFileManager.getSharedPreferences;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.khazana.hrm.Adapter.Approval.ApprovalAttendanceAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.Approval.AttendApproval.AttendApproval;
import com.khazana.hrm.Model.Approval.AttendApproval.GetAttendApprolModel;
import com.khazana.hrm.Model.Approval.AttendApproval.WorkFlow;
import com.khazana.hrm.Model.Approval.LeavesApproval.AddLeaveApproval;
import com.khazana.hrm.Model.Approval.LeavesApproval.leavemodel;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.Memberlist.MemberResponse;
import com.khazana.hrm.Model.UserProfileModel.UserData.EditProfile.EditProf;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.khazana.hrm.databinding.DialogAttendapprovBinding;
import com.khazana.hrm.databinding.DialogSavegeneralBinding;
import com.khazana.hrm.databinding.FragmentAttendanceApprovalBinding;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceApprovalFragment extends Fragment {


    private RecyclerView.LayoutManager mLayoutManager;
    ApprovalAttendanceAdapter approvalAttendanceAdapter;
    private DialogSavegeneralBinding dialogSavegeneralBinding;
//    List<MemberResponse> memberResponseList = new ArrayList<>();

    SessionManager sessionManager;
    String token, sname;


    List<AttendApproval> attendApprovalList = new ArrayList<>();
    List<WorkFlow> workFlowList = new ArrayList<>();
    AddLeaveApproval addLeaveApproval =new AddLeaveApproval();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentAttendanceApprovalBinding binding;
    private DialogAttendapprovBinding dialogAttendapprovBinding;
    List<MemberResponse> memberResponses = new ArrayList<>();
    int iCurrentSelection = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentAttendanceApprovalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.attapprodrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.attapprodrecycler.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();
        //  getmember(token);
        GetAllAttend(token);
        loadmemberlist();

        return view;

    }

    public void loadmemberlist() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("member", null);
        //    Log.e("Tag", "" + json.toString());
//        Toast.makeText(getActivity(), ""+json, Toast.LENGTH_SHORT).show();
        Type type = new TypeToken<ArrayList<MemberResponse>>() {
        }.getType();
        memberResponses = gson.fromJson(json, type);

        if (memberResponses == null) {

            memberResponses = new ArrayList<>();
        }
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


//
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

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAttendApprolModel> getAttendApprolModelCall = ApiHandler.getApiInterface().getAttendApp("Bearer " + access_token, 0, 0, " ", "Desc", null);
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
                                    binding.attapprodrecycler.setVisibility(View.GONE);
                                } else if (attendApprovalList != null) {

                                    approvalAttendanceAdapter = new ApprovalAttendanceAdapter(getActivity(), attendApprovalList);
                                    binding.attapprodrecycler.setAdapter(approvalAttendanceAdapter);
                                    binding.attapprodrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    approvalAttendanceAdapter.setOnItemClickListener(new ApprovalAttendanceAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, AttendApproval obj, int position) {
                                            AttendApproval attendApproval1 = new AttendApproval();
                                            WorkFlow workFlow = new WorkFlow();

                                            String id = obj.getId();
                                            int status=obj.getStatus();

                                            attendApproval1 = attendApprovalList.get(position);
                                            //  Log.e("Tag", "work" + response1.toString());

                                            workFlowList = attendApproval1.getWorkFlow();


                                            workFlow = workFlowList.get(0);

                                            showCustomDialog(workFlow,id,status);

                                        }
                                    });
                                }

                            } else if (status == 400) {
                                binding.txtno.setVisibility(View.VISIBLE);
                                binding.attapprodrecycler.setVisibility(View.GONE);
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            } else if (status == 401) {
                                binding.txtno.setVisibility(View.VISIBLE);
                                binding.attapprodrecycler.setVisibility(View.GONE);
                                //   String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "Your session will be expired please login again!", Toast.LENGTH_SHORT).show();
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
                            dialog.dismiss();

                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetAttendApprolModel> call, Throwable t) {
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


    // end


//  start   Base on Id

    private void GetAllAttendByID(final String access_token, Integer empid) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetAttendApprolModel> getAttendApprolModelCall = ApiHandler.getApiInterface().getAttendApp("Bearer " + access_token, empid, 0, " ", "Desc", null);
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
                                    binding.attapprodrecycler.setVisibility(View.GONE);
                                    binding.txtno.setVisibility(View.VISIBLE);
                                } else if
                                (attendApprovalList != null) {

                                    approvalAttendanceAdapter = new ApprovalAttendanceAdapter(getActivity(), attendApprovalList);
                                    binding.attapprodrecycler.setAdapter(approvalAttendanceAdapter);
                                    binding.attapprodrecycler.setVisibility(View.VISIBLE);
                                    binding.txtno.setVisibility(View.GONE);
                                    dialog.dismiss();

                                    approvalAttendanceAdapter.setOnItemClickListener(new ApprovalAttendanceAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, AttendApproval obj, int position) {
                                            AttendApproval attendApproval1 = new AttendApproval();
                                            WorkFlow workFlow = new WorkFlow();


                                            String id = obj.getId();
                                            int status=obj.getStatus();

                                            attendApproval1 = attendApprovalList.get(position);
                                            //  Log.e("Tag", "work" + response1.toString());

                                            workFlowList = attendApproval1.getWorkFlow();


                                            workFlow = workFlowList.get(0);

                                            showCustomDialog(workFlow,id,status);

                                        }
                                    });
                                }

                            } else if (status == 400) {
                                binding.attapprodrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else if (status == 401) {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                sessionManager.logoutUser();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else {
                                binding.attapprodrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        } else {
                            binding.attapprodrecycler.setVisibility(View.GONE);
                            binding.txtno.setVisibility(View.VISIBLE);
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


    private void showCustomDialog(WorkFlow workFlow, String sid, int sstatus) {

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


        if (type == 0) {
            dialogAttendapprovBinding.apprtask.setVisibility(View.GONE);
        } else {
            dialogAttendapprovBinding.apprtask.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprtask.setText(Integer.toString(type));
        }
        String procname = workFlow.getProcessName();
        if (procname == null) {
            dialogAttendapprovBinding.apprproname.setVisibility(View.GONE);

        } else {
            dialogAttendapprovBinding.apprproname.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprproname.setText(procname);
        }

        String displayName = workFlow.getDisplayName();
        if (displayName == null) {
            dialogAttendapprovBinding.apprdate.setVisibility(View.GONE);

        } else {
            dialogAttendapprovBinding.apprdate.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprdate.setText(displayName);
        }
        String comments = workFlow.getComments();

        if (comments == null) {
            dialogAttendapprovBinding.apprdisplyname.setVisibility(View.GONE);

        } else {
            dialogAttendapprovBinding.apprdisplyname.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.apprdisplyname.setText(comments);
        }

//        Double datetime = workFlow.getTimeStamp();
//        long dattime = (new Double(datetime)).longValue();
//
//
//        String dttime = getDateCurrentTimeZone(dattime);
//        if (dttime == null) {
//            dialogAttendapprovBinding.apprprodate.setVisibility(View.GONE);
//
//        } else {
//            dialogAttendapprovBinding.apprprodate.setVisibility(View.VISIBLE);
//            dialogAttendapprovBinding.apprprodate.setText(dttime);
//        }

//        String displayName = workFlow.getDisplayName();
//        String comments = workFlow.getComments();
//

//
//        dialogAttendapprovBinding.apprtask.setText(Integer.toString(type));
//
//
//        dialogAttendapprovBinding.apprproname.setText(procname);
//        dialogAttendapprovBinding.apprdate.setText(displayName);
//        dialogAttendapprovBinding.apprdisplyname.setText(comments);


        Boolean status = workFlow.getStatus();

//        int sta = sstatus;


        if (status && sstatus == 1) {

            dialogAttendapprovBinding.approved.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  AddReq(token);
                    dialog.dismiss();

                }
            });
        } else if (status && sstatus == 4) {

            dialogAttendapprovBinding.approved.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.approved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  AddReq(token);
                    dialog.dismiss();

                }
            });
        } else if (!status && sstatus == 2) {

            // dialogAttendapprovBinding.approve.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.rejected.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.rejected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  AddReq(token);
                    // Toast.makeText(getActivity(), "Please wait", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            });

        } else {
            dialogAttendapprovBinding.approve.setVisibility(View.VISIBLE);
            dialogAttendapprovBinding.reject.setVisibility(View.VISIBLE);


            dialogAttendapprovBinding.approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();
                   // showCustom(sid, workFlow);
                    dialog.dismiss();

                }
            });
            dialogAttendapprovBinding.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();

                   // showCustomReject(sid, workFlow);
                    dialog.dismiss();

                }
            });

        }


        dialogAttendapprovBinding.imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);

                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    private void showCustom(String id, WorkFlow workFlow) {


        dialogSavegeneralBinding = DialogSavegeneralBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogSavegeneralBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


//
//       int iid=workFlow.getUserId();
//       Toast.makeText(getActivity(), ""+iid, Toast.LENGTH_SHORT).show();
////
        dialogSavegeneralBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSavegeneralBinding.etdescrpt.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogSavegeneralBinding.etdescrpt.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                } else {

                    leavemodel leavemodel = new leavemodel();

                    String sid = id;
                    addLeaveApproval.setId(sid);

                    leavemodel.setType(1);
                    leavemodel.setStatus(true);
                    leavemodel.setTimeStamp(null);
                    leavemodel.setDisplayName("LeaveApprovalProcess");
                    leavemodel.setProcessName("LeaveApprovalProcess");
//        int use=workFlow.getUserId();
//        Toast.makeText(getActivity(), ""+use, Toast.LENGTH_SHORT).show();
//
                    leavemodel.setIsAddedtoTree(false);
                    leavemodel.setProcessStatus(false);
                    leavemodel.setIsCompletelyRejected(false);
                    leavemodel.setComments(dialogSavegeneralBinding.etdescrpt.getText().toString().trim());

                    addLeaveApproval.setLeavemodel(leavemodel);


                    addexpr(token, addLeaveApproval);
                    dialog.dismiss();

                }


            }
        });

        dialogSavegeneralBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void addexpr(String token, AddLeaveApproval addLeaveApproval) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
//            Log.d("Tag",""+addQualification.toString());
//            int sid=addLeaveApproval.getLeavemodel().getRoleId();
//
//            Toast.makeText(getActivity(), ""+sid, Toast.LENGTH_SHORT).show();

            Call<EditProf> editProfCall = ApiHandler.getApiInterface().leaveapproval("Bearer " + token, addLeaveApproval);
            editProfCall.enqueue(new Callback<EditProf>()
            {

                @Override
                public void onResponse(Call<EditProf> editProfCall1, Response<EditProf> response) {

                    try {
                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200)
                            {

                                GetAllAttend(token);
                                dialog.dismiss();


                            }
                            else if (status == 400)
                            {
                                String msg =response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if (status == 401)
                            {
                                String msg =response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        }

                        else
                        {
                            Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                            Toast.makeText(getActivity(), "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<EditProf> call, Throwable t) {
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

    private void showCustomReject(String id, WorkFlow workFlow)
    {



        dialogSavegeneralBinding = DialogSavegeneralBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogSavegeneralBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


//
//       int iid=workFlow.getUserId();
        Toast.makeText(getActivity(), ""+id, Toast.LENGTH_SHORT).show();
////
        dialogSavegeneralBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(dialogSavegeneralBinding.etdescrpt.getText().toString().isEmpty()) {
                    // editText is empty
                    dialogSavegeneralBinding.etdescrpt.setError("The item cannot be empty ");
                    //  Toast.makeText(getActivity(), "Please enter the value", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    leavemodel leavemodel =new leavemodel();

                    String sid =id;
                    addLeaveApproval.setId(sid);

                    leavemodel.setType(1);
                    leavemodel.setStatus(false);
                    leavemodel.setTimeStamp(null);
                    leavemodel.setDisplayName("LeaveApprovalProcess");
                    leavemodel.setProcessName("LeaveApprovalProcess");
//        int use=workFlow.getUserId();
//        Toast.makeText(getActivity(), ""+use, Toast.LENGTH_SHORT).show();
//
                    leavemodel.setIsAddedtoTree(false);
                    leavemodel.setProcessStatus(false);
                    leavemodel.setIsCompletelyRejected(false);
                    leavemodel.setComments(dialogSavegeneralBinding.etdescrpt.getText().toString().trim());

                    addLeaveApproval.setLeavemodel(leavemodel);


                    addrej(token, addLeaveApproval);

                    dialog.dismiss();

                }


            }
        });

        dialogSavegeneralBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(getActivity(), "Please wait ", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void addrej(String token, AddLeaveApproval addLeaveApproval) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
//            Log.d("Tag",""+addQualification.toString());
//            int sid=addLeaveReq.getLeavemodel().getRoleId();
//
//            Toast.makeText(getActivity(), ""+sid, Toast.LENGTH_SHORT).show();

            Call<EditProf> editProfCall = ApiHandler.getApiInterface().rejectleaveappr("Bearer " + token, addLeaveApproval);
            editProfCall.enqueue(new Callback<EditProf>()
            {

                @Override
                public void onResponse(Call<EditProf> editProfCall1, Response<EditProf> response) {

                    try {
                        if (response.isSuccessful())
                        {

                            int status = response.body().getMeta().getStatus();


                            if (status == 200)
                            {

                                GetAllAttend(token);
                                dialog.dismiss();



                            }
                            else if (status == 400)
                            {
                                String msg =response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else if (status == 401)
                            {
                                String msg =response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        }

                        else
                        {
                            Log.e("Tag", "error=" + response.body().getMeta().getMessage().toString());
                            Toast.makeText(getActivity(), "Something went wrong in Parameters", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Log.e("Tag", "error=" + e.toString());
                            Toast.makeText(getActivity(), "Internal server error!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();


                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<EditProf> call, Throwable t) {
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



//    public String getDateCurrentTimeZone(long timestamp) {
//        try {
//            Calendar calendar = Calendar.getInstance();
//            TimeZone tz = TimeZone.getDefault();
//            calendar.setTimeInMillis(timestamp * 1000);
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date currenTimeZone = (Date) calendar.getTime();
//            return sdf.format(currenTimeZone);
//        } catch (Exception e) {
//        }
//        return "";
//    }


}