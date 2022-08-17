package com.example.hrm.Adapter.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Interface.AttendanceItemClickListener;
import com.example.hrm.Model.Attendance.Attendance;
import com.example.hrm.Model.Attendance.Attendance_list;
import com.example.hrm.R;
import com.example.hrm.databinding.ItemAttendanceBinding;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAttendanceAdapter extends RecyclerView.Adapter<EmployeeAttendanceAdapter.ViewHolder> {

    private List<Attendance> attendanceList;
    private int mPosition = 0;
    public AttendanceItemClickListener mItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemAttendanceBinding mBinding;
        private View listView;

        public ViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
            listView = view;
        }
    }

    public EmployeeAttendanceAdapter(List<Attendance> pAttendanceList, int pPosition, AttendanceItemClickListener pItemClickListener) {
        this.attendanceList = pAttendanceList;
        this.mPosition = pPosition;
        this.mItemClickListener = pItemClickListener;
    }

    public void addItems(List<Attendance> pAttendanceList) {
        this.attendanceList = pAttendanceList;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        attendanceList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Attendance item, int position) {
        attendanceList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAttendanceBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_attendance, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(true);

        Attendance attendance = attendanceList.get(position);
        //holder.mBinding.setItem(monthHeader);

        List<Attendance_list> localList = new ArrayList<>();
        int start = mPosition * 3;
        for (int i = start; i < attendance.getAttendanceList().size(); i++) {
            if (localList.size() == 3)
                break;
            localList.add(attendance.getAttendanceList().get(i));
        }

        EmployeeAttendanceStatusAdapter employeeAttendanceStatusAdapter = new EmployeeAttendanceStatusAdapter(localList, new AttendanceItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Attendance attendance) {
                attendance.setId(attendanceList.get(holder.getAbsoluteAdapterPosition()).getId());
                attendance.setName(attendanceList.get(holder.getAbsoluteAdapterPosition()).getName());

                if (mItemClickListener != null){
                    mItemClickListener.onItemClick(view, holder.getAbsoluteAdapterPosition(), attendance);
                }
            }
        });
        holder.mBinding.rvAttendance.setAdapter(employeeAttendanceStatusAdapter);
    }

    @Override
    public int getItemCount() {
        return attendanceList == null ? 0 : attendanceList.size();
    }
}