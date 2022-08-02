package com.example.hrm.Fragment.BasicSetup;

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
import android.widget.Toast;

import com.example.hrm.Adapter.StaticData.GroupsAdapter;
import com.example.hrm.Adapter.StaticData.SkillsAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.BasicSetup.Group.GetGroup;
import com.example.hrm.Model.BasicSetup.Group.GetGroupData;
import com.example.hrm.Model.BasicSetup.Skills.GetSkills;
import com.example.hrm.Model.BasicSetup.Skills.GetSkillsData;
import com.example.hrm.Model.BasicSetup.StaticDataModel.AddStatic.GetAddStaticData;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddskillBinding;
import com.example.hrm.databinding.DialogEditskillBinding;
import com.example.hrm.databinding.FragmentGeneralRequestBinding;
import com.example.hrm.databinding.FragmentGroupsBinding;
import com.example.hrm.databinding.FragmentSkillsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupsFragment extends Fragment {


    private FragmentGroupsBinding binding;
    private DialogAddskillBinding dialogAddskillBinding;
    private DialogEditskillBinding dialogEditskillBinding;



    private RecyclerView.LayoutManager mLayoutManager;
    GroupsAdapter groupsAdapter;

    List<GetGroupData> getGroupDataList = new ArrayList<>();


    String stskill,stdecrpt;
    SessionManager sessionManager;
    String token, sname,sdisply;
    Integer sidd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGroupsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.groupsrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.groupsrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getgroup(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

               // Addskill();

            }
        });

        return view;
    }
    private void getgroup(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetGroup> getGroupCall = ApiHandler.getApiInterface().getgroups("Bearer " + access_token);
            getGroupCall.enqueue(new Callback<GetGroup>() {
                @Override
                public void onResponse(Call<GetGroup> getGroupCall1, Response<GetGroup> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getGroupDataList = response.body().getData().getResponse();

                                if (getGroupDataList.size() == 0)
                                {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.groupsrecycler.setVisibility(View.GONE);

                                } else if (getGroupDataList != null)
                                {

                                    groupsAdapter = new GroupsAdapter(getActivity(), getGroupDataList);
                                    binding.groupsrecycler.setAdapter(groupsAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.groupsrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();


                                    groupsAdapter.setOnItemClickListener(new GroupsAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetGroupData obj, int position) {
                                            GetGroupData getGroupData = getGroupDataList.get(position);
                                            String name =getGroupData.getName();
                                            String val=getGroupData.getDisplayName();
                                            Integer sid=getGroupData.getId();
                                            //  showEditDialog(name,val,sid);

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


                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetGroup> call, Throwable t) {
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

}