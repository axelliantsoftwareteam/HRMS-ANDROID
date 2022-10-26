package com.khazana.hrm.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

public class CalendarActivity extends AppCompatActivity  {

//implements OnCalenderDayClickListener
//    List<CalenderEventObjects> msEvents;
//    RecyclerView eventsView;
//    CustomAdapter mAdapter;
//
//    private RecyclerView mRecyclerView;
//    private RecyclerView.LayoutManager mLayoutManager;
//    CalenderEventAdapter calenderEventAdapter;
//
//    SessionManager sessionManager;
//    String token;
//
//    TextView noleave;
//
//    List<GetCalenderData> mEvents= new ArrayList<>();
//
//
//    private static final String[] eventtype = {"Task", "Event"};
//
//    String stime,etime,descrpt,eventname, selected_val;
//    CalendarCustomView mView;
//
//    private ActivityCalendarBinding binding;
//
//    private DialogCalendercreateBinding dialogBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        // setContentView(R.layout.activity_tasks);
//        binding = binding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
//        setContentView(view);
//
//        isNetworkConnectionAvailable();
//
////        binding.eventsView.setHasFixedSize(true);
////
////        // use a linear layout manager
////        mLayoutManager = new LinearLayoutManager(CalendarActivity.this);
////        mRecyclerView.setLayoutManager(mLayoutManager);
////
//        sessionManager = new SessionManager(CalendarActivity.this);
//        token = sessionManager.getToken();
//
//        getcalender(token);
//
//
//        CalendarCustomView mView = (CalendarCustomView) findViewById(R.id.custom_calendar);
//
//        msEvents = new ArrayList<>();
//        mAdapter = new CustomAdapter(this);
//        eventsView = findViewById(R.id.eventsView);
//        eventsView.setHasFixedSize(true);
//        eventsView.setLayoutManager(new LinearLayoutManager(this));
//        eventsView.setAdapter(mAdapter);
//
////       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////       try {
////            String dtStart = "2022-08-15";
////            String dtStart1 = "2022-08-17";
////
////           Date date = format.parse(dtStart);
////           Date date1 = format.parse(dtStart1);
//
////
////           mEven  CalenderEventObjects event1 = new CalenderEventObjects("Test", date, date);
//////           CalenderEventObjects event2 = new CalenderEventObjects("Meeting", date, date1);ts.add(event1);
////           mEvents.add(mEvents);
//
//
////        }
////        catch (ParseException e)
////        {
////            e.printStackTrace();
////        }
//     //   getcalender();
//
//        mView.loadCalender(msEvents,this);
//
//
//        binding.me.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CalendarActivity.this, UserProfActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
//        binding.fltHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
//        binding.more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(CalendarActivity.this, More.class);
//                startActivity(intent);
//                finish();
//
//
//            }
//        });
//        binding.imgback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(CalendarActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });


    }
//    @Override
//    public void onDayClick(int position, String date, CalenderEventObjects eventObjects) {
////        mAdapter.clearItem();
//        if (eventObjects != null)
//        {
//            Log.e("eventObjects",""+eventObjects);
//            mAdapter.addItem(eventObjects);
//        }
//        else
//        {
//            addevent();
//            Toast.makeText(this, "" + date, Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void getcalender(final String access_token) {
//        try {
//
//            final ProgressDialog dialog;
//            dialog = new ProgressDialog(CalendarActivity.this);
//            dialog.setMessage("Loading...");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.show();
//
//            Call<GetCalender> getCalenderCall = ApiHandler.getApiInterface().getCalenderinfo("Bearer " + access_token);
//            getCalenderCall.enqueue(new Callback<GetCalender>()
//            {
//
//                @Override
//                public void onResponse(Call<GetCalender> getCalenderCall1, Response<GetCalender> response)
//                {
//
//                    try {
//                        Log.e("Tag", "event=" + getCalenderCall.toString());
//                        if (response.isSuccessful())
//                        {
//
//                            int status = response.body().getMeta().getStatus();
//                            if (status == 200)
//                            {
//
//                                mEvents = response.body().getData().getResponse();
//                                Log.e("Tag", "event=" + mEvents.toString());
//
//
////                                GetCalenderData getCalenderData = new GetCalenderData();
//                                CalenderEventObjects calenderEventObjects = new CalenderEventObjects();
//                                for (int i=0 ;i<mEvents.size();i++)
//                                {
//                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                                    LocalDateTime localDateTime = LocalDateTime.parse(mEvents.get(i).getStart());
//                                    String localdate = localDateTime.format(formatter);
//                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                    Date end = dateFormat.parse(localdate);
//
//                                    DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                                    LocalDateTime localDateTimes = LocalDateTime.parse(mEvents.get(i).getEnd());
//                                    String enddate = localDateTimes.format(formatters);
//                                    SimpleDateFormat dateeFormat = new SimpleDateFormat("yyyy-MM-dd");
//                                    Date d = dateeFormat.parse(enddate);
//
//                                    String title =mEvents.get(i).getTitle();
//                                    calenderEventObjects.setTitle(title);
//                                    calenderEventObjects.setDate(end);
//                                    calenderEventObjects.setEdate(d);
//
//
////                                    CalenderEventObjects calenderEventObjects = new CalenderEventObjects(title,d,end);
//
//                                }
//
//
//
////                                if (mEvents.size()==0)
////                                {
////                                    dialog.dismiss();
////                                    mRecyclerView.setVisibility(View.GONE);
////                                }
////                                else if (getCalenderDataList!=null){
////
////                                    calenderEventAdapter = new CalenderEventAdapter(CalendarActivity.this, getCalenderDataList);
////                                    mRecyclerView.setAdapter(calenderEventAdapter);
////                                    mRecyclerView.setVisibility(View.VISIBLE);
////                                    noleave.setVisibility(View.GONE);
////                                    dialog.dismiss();
////                                }
//
//
//
//                            }
//
//
//                        } else {
//                            Toast.makeText(CalendarActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
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
//                public void onFailure(Call<GetCalender> call, Throwable t) {
//                    try {
//                        Log.e("Tag", "error" + t.toString());
//                        dialog.dismiss();
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
//
//
//
//    private void addevent()
//        {
//
//            dialogBinding = DialogCalendercreateBinding.inflate(getLayoutInflater());
//
//            final Dialog dialog = new Dialog(CalendarActivity.this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(dialogBinding.getRoot());
//            dialog.setCancelable(true);
//
//
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//
//
//
//            ArrayAdapter<String> eventadapter = new ArrayAdapter<String>(CalendarActivity.this,
//                    android.R.layout.simple_spinner_item, eventtype);
//            eventadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            dialogBinding.eventspinner.setAdapter(eventadapter);
//            dialogBinding.eventspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3)
//                {
//
//                     selected_val=dialogBinding.eventspinner.getSelectedItem().toString();
//                    Toast.makeText(CalendarActivity.this, ""+selected_val, Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> arg0)
//                {
//                    // TODO Auto-generated method stub
//
//                }
//            });
//
//            dialogBinding.etstrtime.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Calendar mcurrentTime = Calendar.getInstance();
//                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                    int minute = mcurrentTime.get(Calendar.MINUTE);
//                    TimePickerDialog mTimePicker;
//                    mTimePicker = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
//                        {
//                            Date dt = new Date();
//                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//                            String time1 = sdf.format(dt);
//                            dialogBinding.etstrtime.setText(time1);
//
//                            stime = dialogBinding.etstrtime.getText().toString().trim();
//                        }
//                    }, hour, minute, true);//Yes 24 hour time
//                    mTimePicker.setTitle("Select Time");
//                    mTimePicker.show();
//
//                }
//            });
//            dialogBinding.etendtime.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    Calendar mcurrentTime = Calendar.getInstance();
//                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                    int minute = mcurrentTime.get(Calendar.MINUTE);
//                    TimePickerDialog mTimePicker;
//                    mTimePicker = new TimePickerDialog(CalendarActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                        @Override
//                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
//                        {
//                            Date dt = new Date();
//                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
//                            String time1 = sdf.format(dt);
//                            dialogBinding.etendtime.setText(time1);
//
//                            etime = dialogBinding.etendtime.getText().toString().trim();
//                        }
//                    }, hour, minute, true);//Yes 24 hour time
//                    mTimePicker.setTitle("Select Time");
//                    mTimePicker.show();
//
//                }
//            });
//
//
//
//
//
//            dialogBinding.btnUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//                    descrpt=dialogBinding.etdescrpt.getText().toString();
//                    eventname=dialogBinding.eteventname.getText().toString();
//
//                    //  AddReq(token);
//                    dialog.dismiss();
//
//                }
//            });
//
//            dialogBinding.btnNothank.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//
//                    //  AddReq(token);
//                    dialog.dismiss();
//
//                }
//            });
//
//
//
//
//
//
//            dialog.show();
//            dialog.getWindow().setAttributes(lp);
//        }
//
//    @Override
//    public void onBackPressed() {
//        // do something on back.
//        Intent i = new Intent(CalendarActivity.this, MainActivity.class);
//        startActivity(i);
//        finish();
//
//    }
//    public boolean isNetworkConnectionAvailable(){
//        ConnectivityManager cm =
//                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//        boolean isConnected = activeNetwork != null &&
//                activeNetwork.isConnected();
//        if(isConnected) {
//            Log.d("Network", "Connected");
//            return true;
//        }
//        else{
//            checkNetworkConnection();
//            Log.d("Network","Not Connected");
//            return false;
//        }
//    }
//    public void checkNetworkConnection(){
//        AlertDialog.Builder builder =new AlertDialog.Builder(this);
//        builder.setTitle("No internet Connection");
//        builder.setMessage("Please turn on internet connection to continue");
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
//        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }

}