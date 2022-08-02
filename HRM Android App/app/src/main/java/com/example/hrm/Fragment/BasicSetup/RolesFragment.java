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

import com.example.hrm.Adapter.StaticData.RolesAdapter;
import com.example.hrm.Adapter.StaticData.SkillsAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.BasicSetup.Roles.GetRoles;
import com.example.hrm.Model.BasicSetup.Roles.GetRolesData;
import com.example.hrm.Model.BasicSetup.Skills.GetSkills;
import com.example.hrm.Model.BasicSetup.Skills.GetSkillsData;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogAddskillBinding;
import com.example.hrm.databinding.DialogEditskillBinding;
import com.example.hrm.databinding.FragmentRolesBinding;
import com.example.hrm.databinding.FragmentSkillsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RolesFragment extends Fragment {



    private FragmentRolesBinding binding;
    private DialogAddskillBinding dialogAddskillBinding;
    private DialogEditskillBinding dialogEditskillBinding;



    private RecyclerView.LayoutManager mLayoutManager;
    RolesAdapter rolesAdapter;

    List<GetRolesData> getRolesDataList = new ArrayList<>();


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
        binding = FragmentRolesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.rolesrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.rolesrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getrole(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            //    Addskill();

            }
        });

        return view;
    }

    private void getrole(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetRoles> getRolesCall = ApiHandler.getApiInterface().getroles("Bearer " + access_token);
            getRolesCall.enqueue(new Callback<GetRoles>() {
                @Override
                public void onResponse(Call<GetRoles> getRolesCall1, Response<GetRoles> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getRolesDataList = response.body().getData().getResponse();

                                if (getRolesDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.rolesrecycler.setVisibility(View.GONE);

                                } else if (getRolesDataList != null) {

                                    rolesAdapter = new RolesAdapter(getActivity(), getRolesDataList);
                                    binding.rolesrecycler.setAdapter(rolesAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.rolesrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    rolesAdapter.setOnItemClickListener(new RolesAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetRolesData obj, int position) {
                                            GetRolesData getRolesData = getRolesDataList.get(position);
                                            String name =getRolesData.getName();
                                            String val=getRolesData.getDescription();
                                            Integer sid=getRolesData.getId();
                                          //  showEditDialog(name,val,sid);





                                            // Log.e("Tag", "work" + name.toString());


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
                public void onFailure(Call<GetRoles> call, Throwable t) {
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