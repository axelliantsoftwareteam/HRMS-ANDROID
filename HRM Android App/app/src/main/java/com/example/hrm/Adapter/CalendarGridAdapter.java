package com.example.hrm.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hrm.Interface.OnCalenderDayClickListener;
import com.example.hrm.Model.Calender.Attendee;
import com.example.hrm.Model.Calender.GetCalenderInfoData;
import com.example.hrm.Model.CalenderEventObjects;
import com.example.hrm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by akumar1 on 11/22/2017.
 */

public class CalendarGridAdapter extends ArrayAdapter
{
    private static final String TAG = CalendarGridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    private List<GetCalenderInfoData> allEvents;
    private List<Attendee> attendeeList;
    private OnCalenderDayClickListener listener;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date d ;

    public CalendarGridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, List<GetCalenderInfoData> allEvents, OnCalenderDayClickListener listener) {
        super(context, R.layout.calendar_single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        final Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;
        if (view == null)
        {
            view = mInflater.inflate(R.layout.calendar_single_cell_layout, parent, false);
        }
        //Add day to calendar
        TextView cellNumber = (TextView) view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));


        if (displayMonth == currentMonth && displayYear == currentYear) {
            view.setBackgroundColor(Color.parseColor("#f5f5f5"));
        } else {
            view.setBackgroundColor(Color.parseColor("#f5f5f5"));
            cellNumber.setTextColor(Color.parseColor("#d7d7d7"));
        }

        Calendar currentData = Calendar.getInstance();
        if (dayValue == currentData.get(Calendar.DAY_OF_MONTH) && displayMonth == currentData.get(Calendar.MONTH) + 1 && displayYear == currentData.get(Calendar.YEAR)) {
            cellNumber.setBackgroundResource(R.drawable.button_nothanks_bg);
            cellNumber.setTextColor(Color.parseColor("#345ab3"));
        }
        //Add events to the calendar
        TextView eventIndicator = (TextView) view.findViewById(R.id.event_id);
        Calendar eventCalendar = Calendar.getInstance();
        GetCalenderInfoData eventObjects = null;
        for (int i = 0; i < allEvents.size(); i++)
        {


            String date =allEvents.get(i).getStart().getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date end = dateFormat.parse(date);
                eventCalendar.setTime(end);
                if (dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalendar.get(Calendar.MONTH) + 1
                        && displayYear == eventCalendar.get(Calendar.YEAR)) {
                    eventIndicator.setBackgroundColor(Color.parseColor("#FF4081"));
                    cellNumber.setTextColor(Color.parseColor("#FFFFFF"));
                    cellNumber.setBackgroundResource(R.drawable.button_bg);
                    eventObjects = allEvents.get(i);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        final GetCalenderInfoData finalEventObjects = eventObjects;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDayClick(position, "" + format.format(dateCal.getTime()), finalEventObjects);
                }
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }


}