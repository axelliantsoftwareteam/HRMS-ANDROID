package com.khazana.hrm.Fragment.Leaves;

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
import android.widget.TextView;
import android.widget.Toast;

import com.khazana.hrm.Adapter.Leaves.PendingLeavesAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.LeavesModel.AllLeavesModel;
import com.khazana.hrm.Model.LeavesModel.Leaves;
import com.khazana.hrm.R;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingLeavesFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;


    PendingLeavesAdapter pendingLeavesAdapter;

    SessionManager sessionManager;
    String token;

    TextView noleave;

    List<Leaves> leaves= new ArrayList<>();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pending_leaves, container, false);
        noleave = view.findViewById(R.id.txt_no);

        mRecyclerView = view.findViewById(R.id.pend_recycler);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        GetPendingLeaves(token);


        return view;

    }
    private void GetPendingLeaves(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();



//            Call<Leaf> leafCall = ApiHandler.getApiInterface().getClock("Bearer " + access_token);

//            Map<String, String> params = new HashMap<String, String>();
//            params.put("start", "0");
//            params.put("limit", "5");
//            params.put("sort", " ");
//            params.put("order", "Desc");
//            params.put("search", "nullli");

            Call<AllLeavesModel> allleaves = ApiHandler.getApiInterface().getpend("Bearer " + access_token);
            allleaves.enqueue(new Callback<AllLeavesModel>() {
                @Override
                public void onResponse(Call<AllLeavesModel> allLeavesModelCall, Response<AllLeavesModel> response) {

                    try {
                        if (response.isSuccessful())
                        {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200)
                            {

                                leaves = response.body().getData().getLeaves();
                                if (leaves.size()==0)
                                {
                                    dialog.dismiss();
                                    mRecyclerView.setVisibility(View.GONE);
                                }
                                else if (leaves!=null){

                                    pendingLeavesAdapter = new PendingLeavesAdapter(getActivity(), leaves);
                                    mRecyclerView.setAdapter(pendingLeavesAdapter);
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    noleave.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }

                            }
                            else if (status==400)
                            {
                                noleave.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                            }
                            else if (status == 401) {
                                noleave.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            }
                            else
                            {
                                noleave.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
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
                public void onFailure(Call<AllLeavesModel> call, Throwable t) {
                    try {
                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(getActivity(), "internal server error!", Toast.LENGTH_SHORT).show();
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