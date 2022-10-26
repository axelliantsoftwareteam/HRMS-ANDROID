package com.khazana.hrm.Adapter.Organo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khazana.hrm.Model.Organo.OrganoData;
import com.khazana.hrm.R;

import java.util.List;

import dev.bandb.graphview.AbstractGraphAdapter;

public class GraphAdapter extends AbstractGraphAdapter<GraphAdapter.NodeViewHolder>  {
    Context context;
    List<OrganoData> organoDataList;

    public GraphAdapter(Context context, List<OrganoData> organoDataList) {
        this.context = context;
        this.organoDataList = organoDataList;
    }

    @NonNull
    @Override
    public NodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node, parent, false);
        return new NodeViewHolder(view) ;
        }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder holder,  int position) {
        OrganoData data = null;
        if (!organoDataList.isEmpty()){
                data = organoDataList.get(position);
        }
        holder.textName.setText(data.getName());
        holder.textPost.setText(data.getTxt());
        Glide.with(context).load(data.getImg()).into(holder.img);
        /*holder.imgCollapse.setOnClickListener(view -> {


        });*/
    }



    public class NodeViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageView imgCollapse;
        TextView textName;
        TextView textPost;

        public NodeViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            textName = itemView.findViewById(R.id.textName);
            textPost = itemView.findViewById(R.id.textPost);
           // imgCollapse = itemView.findViewById(R.id.imgCollapseExpand);
        }
    }

}
