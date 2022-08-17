package com.example.hrm.Adapter.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Model.Attendance.MonthHeader;
import com.example.hrm.R;
import com.example.hrm.databinding.ItemDayDateBinding;

import java.util.List;


public class DayDateAdapter extends RecyclerView.Adapter<DayDateAdapter.ViewHolder> {

    private List<MonthHeader> mMonthHeaderList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemDayDateBinding mBinding;
        private View listView;

        public ViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
            listView = view;
        }
    }

    public DayDateAdapter(List<MonthHeader> monthHeaderList) {
        this.mMonthHeaderList = monthHeaderList;
    }

    public void addItems(List<MonthHeader> monthHeaderList) {
        this.mMonthHeaderList = monthHeaderList;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mMonthHeaderList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(MonthHeader item, int position) {
        mMonthHeaderList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDayDateBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.item_day_date, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(true);

        MonthHeader monthHeader = mMonthHeaderList.get(position);
        //holder.mBinding.setItem(monthHeader);
        holder.mBinding.tvDayDate.setText(monthHeader.getDay() + "\n" + monthHeader.getWeekDay());
    }

    @Override
    public int getItemCount() {
        return mMonthHeaderList == null ? 0 : mMonthHeaderList.size();
    }
}