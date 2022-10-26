package com.khazana.hrm.Adapter.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.khazana.hrm.Interface.AttendanceItemClickListener;
import com.khazana.hrm.Model.Attendance.Attendance;
import com.khazana.hrm.Model.Attendance.Attendance_list;
import com.khazana.hrm.R;
import com.khazana.hrm.databinding.ItemAttendanceStatsBinding;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAttendanceStatusAdapter extends RecyclerView.Adapter<EmployeeAttendanceStatusAdapter.ViewHolder> {

    private List<Attendance_list> mAttendanceList;
    public AttendanceItemClickListener mItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemAttendanceStatsBinding mBinding;
        private View listView;

        public ViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
            listView = view;
        }
    }

    public EmployeeAttendanceStatusAdapter(List<Attendance_list> pAttendanceList, AttendanceItemClickListener pItemClickListener) {
        this.mAttendanceList = pAttendanceList;
        this.mItemClickListener = pItemClickListener;
    }

    public void addItems(List<Attendance_list> pAttendanceList) {
        this.mAttendanceList = pAttendanceList;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mAttendanceList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Attendance_list item, int position) {
        mAttendanceList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAttendanceStatsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_attendance_stats, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(true);

        Attendance_list attendance = mAttendanceList.get(position);
        //holder.mBinding.setItem(monthHeader);

        /**
         * # 1: 'Present',
         * # 2: 'Absent',
         * # 3: 'Future Date',
         * # 4: 'Weekend',
         * # 5: 'Early Out',
         * # 6: 'Late'
         *  # 7: 'Missing Check in/Check out'
         *  # 8: 'Public Holiday'
         * # 9: 'Leave'
         */

        if (attendance.getType() == 1) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.present);
        } else if (attendance.getType() == 2) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.ic_absent);
        } else if (attendance.getType() == 3) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.pending);
        } else if (attendance.getType() == 4) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.onleaves);
        } else if (attendance.getType() == 5) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.earlyout);
        } else if (attendance.getType() == 6) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.lateness);
        } else if (attendance.getType() == 7) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.earlyout);
        } else if (attendance.getType() == 8) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.public_holiday);
        } else if (attendance.getType() == 9) {
            holder.mBinding.ivAttendanceStatus.setImageResource(R.drawable.onleave);
        }

        holder.mBinding.ivAttendanceStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null){
                    List<Attendance_list> attendanceList = new ArrayList<>();
                    Attendance_list attendance_list = mAttendanceList.get(holder.getBindingAdapterPosition());
                    attendanceList.add(attendance_list);
                    Attendance attendance = new Attendance();
                    attendance.setAttendanceList(attendanceList);
                    mItemClickListener.onItemClick(view, holder.getBindingAdapterPosition(), attendance);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAttendanceList == null ? 0 : mAttendanceList.size();
    }
}