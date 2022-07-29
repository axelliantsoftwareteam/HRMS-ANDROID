package com.example.hrm.Fragment.Evaluation;

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

import com.example.hrm.Adapter.Evaluation.EvalutionAdapter;
import com.example.hrm.Adapter.Evaluation.QuestionAdapter;
import com.example.hrm.Adapter.Evaluation.ValueModel;
import com.example.hrm.Hundler.ApiHandler;
import com.example.hrm.Model.Evalution.GetEvaluation;
import com.example.hrm.Model.Evalution.GetEvaluationData;
import com.example.hrm.Model.Evalution.Questions.GetEvalQuesData;
import com.example.hrm.Model.Evalution.Questions.GetEvalQuestion;
import com.example.hrm.Utility.SessionManager;
import com.example.hrm.databinding.DialogEvalquestionBinding;
import com.example.hrm.databinding.FragmentEvaluationBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EvaluationFragment extends Fragment {


    private FragmentEvaluationBinding binding;

    private DialogEvalquestionBinding dialogEvalquestionBinding;
    boolean status = false;


    private RecyclerView.LayoutManager mLayoutManager;
    EvalutionAdapter evalutionAdapter;

    private RecyclerView.LayoutManager mqLayoutManager;
    QuestionAdapter questionAdapter;

    SessionManager sessionManager;
    String token, sname;

    List<GetEvaluationData> getevaluationlist = new ArrayList<>();

    List<GetEvalQuesData> getEvalQuestionList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEvaluationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.evalurecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.evalurecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getEavulation(token);

        binding.mucheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.mucheck.isChecked()) {
                    getEavulationtrue(token);
                   // Toast.makeText(getActivity(), "" + status, Toast.LENGTH_SHORT).show();

                } else {
                    getEavulation(token);
                   // Toast.makeText(getActivity(), "" + status, Toast.LENGTH_SHORT).show();


                }

            }
        });

        return view;


    }
    private void getEavulationtrue(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEvaluation> getEvalutionCall = ApiHandler.getApiInterface().getEvalution("Bearer " + access_token, true);
            getEvalutionCall.enqueue(new Callback<GetEvaluation>() {
                @Override
                public void onResponse(Call<GetEvaluation> getEvalutionCall1, Response<GetEvaluation> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                getevaluationlist = response.body().getData().getResponse();

                                if (getevaluationlist.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.evalurecycler.setVisibility(View.GONE);

                                } else if (getevaluationlist != null) {

                                    evalutionAdapter = new EvalutionAdapter(getActivity(), getevaluationlist);
                                    binding.evalurecycler.setAdapter(evalutionAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.evalurecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

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
                public void onFailure(Call<GetEvaluation> call, Throwable t) {
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

    private void getEavulation(final String access_token) {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEvaluation> getEvalutionCall = ApiHandler.getApiInterface().getEvalution("Bearer " + access_token, status);
            getEvalutionCall.enqueue(new Callback<GetEvaluation>() {
                @Override
                public void onResponse(Call<GetEvaluation> getEvalutionCall1, Response<GetEvaluation> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                getevaluationlist = response.body().getData().getResponse();

                                if (getevaluationlist.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.evalurecycler.setVisibility(View.GONE);

                                }
                                else if (getevaluationlist != null)
                                {

                                    evalutionAdapter = new EvalutionAdapter(getActivity(), getevaluationlist);
                                    binding.evalurecycler.setAdapter(evalutionAdapter);
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.evalurecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                }

                                evalutionAdapter.setOnItemClickListener(new EvalutionAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, GetEvaluationData obj, int position)
                                    {

                                        GetEvaluationData getEvaluationData =new GetEvaluationData();
                                        getEvaluationData=getevaluationlist.get(position);


                                        boolean status= getEvaluationData.getIsCompleted();
                                        if (status ==false)
                                        {
                                            showEditDialog();
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();

                                        }





                                    }
                                });

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
                public void onFailure(Call<GetEvaluation> call, Throwable t) {
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

    private void showEditDialog()
    {

        dialogEvalquestionBinding = DialogEvalquestionBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEvalquestionBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



        dialogEvalquestionBinding.quesrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mqLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        dialogEvalquestionBinding.quesrecycler.setLayoutManager(mqLayoutManager);
        getQuestion(token);



        dialogEvalquestionBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //  AddReq(token);

                List<ValueModel> valueModels = new ArrayList<>();

                for (int i =0 ; i<valueModels.size();i++) {
                    String comment = valueModels.get(i).getComemnt();
                    Toast.makeText(getActivity(), ""+comment, Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();

            }
        });

        dialogEvalquestionBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
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

    private void getQuestion(final String access_token)
    {
        try {

            final ProgressDialog dialog;
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetEvalQuestion> getEvalutionCall = ApiHandler.getApiInterface().getEvalQuest("Bearer " + access_token);
            getEvalutionCall.enqueue(new Callback<GetEvalQuestion>() {
                @Override
                public void onResponse(Call<GetEvalQuestion> getEvalutionCall1, Response<GetEvalQuestion> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                // Toast.makeText(getActivity(), "hahaha", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();


                                getEvalQuestionList = response.body().getData().getResponse();

                                if (getEvalQuestionList.size() == 0) {
                                    dialog.dismiss();
                                    dialogEvalquestionBinding.txtno.setVisibility(View.VISIBLE);
                                    dialogEvalquestionBinding.quesrecycler.setVisibility(View.GONE);

                                } else if (getEvalQuestionList != null) {

                                    questionAdapter = new QuestionAdapter(getActivity(), getEvalQuestionList);
                                    dialogEvalquestionBinding.quesrecycler.setAdapter(questionAdapter);
                                    dialogEvalquestionBinding.txtno.setVisibility(View.GONE);
                                    dialogEvalquestionBinding.quesrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                }



                            }

                        } else {
                            dialogEvalquestionBinding.txtno.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<GetEvalQuestion> call, Throwable t) {
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