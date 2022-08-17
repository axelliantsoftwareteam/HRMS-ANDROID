package com.example.hrm.Adapter.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Attendance.Attendance;
import com.example.hrm.R;
import com.example.hrm.databinding.ItemNameBinding;

import java.util.List;

public class EmployeeNamesAdapter extends RecyclerView.Adapter<EmployeeNamesAdapter.ViewHolder> {

    private List<Attendance> attendanceList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemNameBinding mBinding;
        private View listView;

        public ViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
            listView = view;
        }
    }

    public EmployeeNamesAdapter(List<Attendance> pAttendanceList) {
        this.attendanceList = pAttendanceList;
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
        ItemNameBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_name, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(true);

        Attendance attendance = attendanceList.get(position);
        //holder.mBinding.setItem(monthHeader);
        holder.mBinding.tvName.setText(attendance.getName());
    }

    @Override
    public int getItemCount() {
        return attendanceList == null ? 0 : attendanceList.size();
    }
}