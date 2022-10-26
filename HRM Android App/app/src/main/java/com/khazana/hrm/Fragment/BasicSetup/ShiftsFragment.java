package com.khazana.hrm.Fragment.BasicSetup;

import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.khazana.hrm.Adapter.StaticData.ShiftAdapter;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.BasicSetup.Shifts.GetShift;
import com.khazana.hrm.Model.BasicSetup.Shifts.GetShiftData;
import com.khazana.hrm.Model.BasicSetup.Skills.AddSkill.Addskill;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.UI.BasicSetupActivity;
import com.khazana.hrm.UI.SignIn;
import com.khazana.hrm.Utility.SessionManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.guna.libmultispinner.MultiSelectionSpinner;
import com.khazana.hrm.databinding.DialogAddshiftBinding;
import com.khazana.hrm.databinding.DialogEditshiftBinding;
import com.khazana.hrm.databinding.FragmentShiftsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShiftsFragment extends Fragment implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {


    // initialize variables
    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    int position;

    String[] dayArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};


    private FragmentShiftsBinding binding;
    private DialogAddshiftBinding dialogAddshiftBinding;
    private DialogEditshiftBinding dialogEditshiftBinding;


    private RecyclerView.LayoutManager mLayoutManager;
    ShiftAdapter shiftAdapter;

    List<GetShiftData> getShiftDataList = new ArrayList<>();


    SessionManager sessionManager;
    private String token, stime, etime, sshiftdecrpt, sshiftname;
    private String stname, stdescrpt, sttime, stendtime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentShiftsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.shiftrecycler.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        binding.shiftrecycler.setLayoutManager(mLayoutManager);


        sessionManager = new SessionManager(getActivity());
        token = sessionManager.getToken();

        getskill(token);

        binding.btnaddskill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Addskill();

            }
        });


        return view;

    }


    private void getskill(final String access_token) {
        try {

            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<GetShift> getShiftCall = ApiHandler.getApiInterface().getShift("Bearer " + access_token);
            getShiftCall.enqueue(new Callback<GetShift>() {
                @Override
                public void onResponse(Call<GetShift> getShiftCall1, Response<GetShift> response) {

                    try {
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {

                                getShiftDataList = response.body().getData().getResponse();

                                if (getShiftDataList.size() == 0) {
                                    dialog.dismiss();
                                    binding.txtno.setVisibility(View.VISIBLE);
                                    binding.shiftrecycler.setVisibility(View.GONE);

                                } else if (getShiftDataList != null) {

                                    shiftAdapter = new ShiftAdapter(getActivity(), getShiftDataList);

                                    binding.shiftrecycler.setAdapter(shiftAdapter);
                                    Log.e("Tag", "work" + getShiftDataList.toString());
                                    binding.txtno.setVisibility(View.GONE);
                                    binding.shiftrecycler.setVisibility(View.VISIBLE);
                                    dialog.dismiss();

                                    shiftAdapter.setOnItemClickListener(new ShiftAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(View view, GetShiftData obj, int position) {
                                            GetShiftData getShiftData = getShiftDataList.get(position);
//                                            String name =getHolidayData.getType();
//                                            String val=getHolidayData.getStartDate();


//
//                                             Log.e("Tag", "work" + name.toString());

                                            showEditDialog();
                                        }
                                    });

                                }

                            }
                            else if (status == 401)
                            {
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            }
                            else
                            {
                                binding.shiftrecycler.setVisibility(View.GONE);
                                binding.txtno.setVisibility(View.VISIBLE);
                                String msg =response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        } else {
                            binding.txtno.setVisibility(View.VISIBLE);
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
                public void onFailure(Call<GetShift> call, Throwable t) {
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

    private void Addskill() {
        dialogAddshiftBinding = DialogAddshiftBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogAddshiftBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogAddshiftBinding.etsttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String time1 = sdf.format(dt);
                        dialogAddshiftBinding.etsttime.setText(time1);

                        stime = dialogAddshiftBinding.etsttime.getText().toString().trim();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        dialogAddshiftBinding.etendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Date dt = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
                        String time1 = sdf.format(dt);
                        dialogAddshiftBinding.etendtime.setText(time1);

                        etime = dialogAddshiftBinding.etendtime.getText().toString().trim();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        dialogAddshiftBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sshiftname = dialogAddshiftBinding.etname.getText().toString().trim();
                sshiftdecrpt = dialogAddshiftBinding.etdescrpt.getText().toString().trim();
                AddShift(token);
                dialog.dismiss();

            }
        });

        dialogAddshiftBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogAddshiftBinding.dialogspinnerlist.setItems(dayArray);
        dialogAddshiftBinding.dialogspinnerlist.setSelection(new int[]{2, 4});
        dialogAddshiftBinding.dialogspinnerlist.setListener(this);

//        // initialize selected language array
//        selectedLanguage = new boolean[dayArray.length];
//
//        dialogAddshiftBinding.textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // Initialize alert dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//                // set title
//                builder.setTitle("Week Holidays");
//
//                // set dialog non cancelable
//                builder.setCancelable(false);
//
//                builder.setMultiChoiceItems(dayArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//                        // check condition
//                        if (b) {
//                            // when checkbox selected
//                            // Add position  in lang list
//                            langList.add(i);
//
//                            // Sort array list
//                            Collections.sort(langList);
//
//
//                        } else {
//                            // when checkbox unselected
//                            // Remove position from langList
//                            langList.remove(Integer.valueOf(i));
//                        }
//                    }
//                });
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i)
//                    {
//                        // Initialize string builder
//                        StringBuilder stringBuilder = new StringBuilder();
//
//
//
//
//                        // use for loop
//                        for (int j = 0; j < langList.size(); j++)
//                        {
//
//                            // concat array value
//                            position=langList.get(j);
//                            stringBuilder.append(dayArray[langList.get(j)]);
//
//
//                            // check condition
//                            if (j != langList.size() - 1) {
//                                // When j value  not equal
//                                // to lang list size - 1
//                                // add comma
//                                stringBuilder.append(", ");
//                            }
//                        }
//                        // set text on textView
//                        dialogAddshiftBinding.textView.setText(stringBuilder.toString());
//
//
//                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // dismiss dialog
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // use for loop
//                        for (int j = 0; j < selectedLanguage.length; j++) {
//                            // remove all selection
//                            selectedLanguage[j] = false;
//                            // clear language list
//                            langList.clear();
//                            // clear text view value
//                            dialogAddshiftBinding.textView.setText("");
//                        }
//                    }
//                });
//                // show dialog
//                builder.show();
//            }
//        });
//


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void AddShift(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(getActivity());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<Addskill> addskillCall = ApiHandler.getApiInterface().addshifts("Bearer " + token, Apimember());

            addskillCall.enqueue(new Callback<Addskill>() {
                @Override
                public void onResponse(Call<Addskill> addskillCall1, Response<Addskill> response) {

                    try {

                        if (response.isSuccessful()) {

                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), BasicSetupActivity.class);
                                startActivity(intent);

                            } else if (status == 400) {
                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + b, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), BasicSetupActivity.class);
                                startActivity(intent);

                            } else if (status == 401) {
//                                String b = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(getActivity(), SignIn.class);
                                startActivity(intent);

                            } else if (status == 500) {
                                Toast.makeText(getActivity(), "Internal Server Error!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                Intent intent = new Intent(getActivity(), BasicSetupActivity.class);
                                startActivity(intent);

                            } else {

                                String msg = response.body().getMeta().getMessage();
                                Toast.makeText(getActivity(), "" + msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        } else {
                            String msg = response.body().getMeta().getMessage();
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
                            Toast.makeText(getActivity(), "" + e.toString(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<Addskill> call, Throwable t) {
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

    private JsonObject Apimember() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("name", sshiftname);
            jsonObj_.put("description", sshiftdecrpt);
            jsonObj_.put("startTime", stime);
            jsonObj_.put("endTime", etime);


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


    private void showEditDialog() {

        dialogEditshiftBinding = DialogEditshiftBinding.inflate(getLayoutInflater());

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogEditshiftBinding.getRoot());
        dialog.setCancelable(true);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        dialogEditshiftBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });

        dialogEditshiftBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  AddReq(token);
                dialog.dismiss();

            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void selectedIndices(List<Integer> indices, MultiSelectionSpinner spinner) {
        Toast.makeText(getActivity(), "" + indices, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void selectedStrings(List<String> strings, MultiSelectionSpinner spinner) {

    }
}