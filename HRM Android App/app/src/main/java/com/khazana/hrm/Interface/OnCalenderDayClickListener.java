package com.khazana.hrm.Interface;


import com.khazana.hrm.Model.Calender.GetCalenderInfoData;

/**
 * Created by akumar1 on 11/22/2017.
 */

public interface OnCalenderDayClickListener {
    void onDayClick(int position, String date, GetCalenderInfoData eventObjects);
}
