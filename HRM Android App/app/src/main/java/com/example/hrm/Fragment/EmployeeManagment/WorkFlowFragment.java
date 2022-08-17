package com.example.hrm.Fragment.EmployeeManagment;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hrm.Adapter.EmplyManage.WorkFlowAdapter;
import com.example.hrm.Adapter.StaticData.SkillsAdapter;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.BasicSetup.Skills.GetSkills;
import com.example.hrm.Model.BasicSetup.Skills.GetSkillsData;
import com.example.hrm.Model.EmployeeManagement.WorkFlow.GetWorkFlow;
import com.example.hrm.Model.EmployeeManagement.WorkFlow.GetWorkFlowData;
import com.example.hrm.R;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.FragmentWorkFlowBinding;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkFlowFragment extends Fragment {


    private FragmentWorkFlowBinding binding;


    private RecyclerView.LayoutManager mLayoutManager;
    WorkFlowAdapter workFlowAdapter;

    List<GetWorkFlowData> getWorkFlowDataList = new ArrayList<>();


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
        binding = FragmentWorkFlowBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        binding.worksrecycler.setHasFixedSize(true);


        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                filter(newText);
                return false;
            }
        });


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.worksrecycler.setLayoutManager(mLayoutManager);



        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getskill(token);






        return view;


    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<GetWorkFlowData> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (GetWorkFlowData item : getWorkFlowDataList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getActivity(), "No Data Found", Toast.LENGTH_SHORT).show();

        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            workFlowAdapter.filterList(filteredlist);
        }
    }


    private void getskill(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetWorkFlow> getWorkFlowCall = ApiHandler.getApiInterface().getWorkflow("Bearer " + access_token);
            getWorkFlowCall.enqueue(new Callback<GetWorkFlow>() {
                @Override
                public void onResponse(Call<GetWorkFlow> getWorkFlowCall1, Response<GetWorkFlow> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getWorkFlowDataList = response.body().getData().getResponse();

                                if (getWorkFlowDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.worksrecycler.setVisibility(View.GONE);

                                } else if (getWorkFlowDataList != null) {

                                    workFlowAdapter = new WorkFlowAdapter(getActivity(), getWorkFlowDataList);
                                    binding.worksrecycler.setAdapter(workFlowAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.worksrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    workFlowAdapter.setOnItemClickListener(new WorkFlowAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetWorkFlowData obj, int position) {
                                            GetWorkFlowData getWorkFlowData = getWorkFlowDataList.get(position);
//                                            String name =getSkillsData.getName();
//                                            String val=getSkillsData.getDescription();
//                                            Integer sid=getSkillsData.getId();
//                                            showEditDialog(name,val,sid);





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
                public void onFailure(Call<GetWorkFlow> call, Throwable t) {
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