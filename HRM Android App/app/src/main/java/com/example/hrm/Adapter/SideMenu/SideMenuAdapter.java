package com.example.hrm.Adapter.SideMenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrm.Adapter.Leaves.ApprovLeavesAdapter;
import com.example.hrm.Model.GetAllTask.Alltask.Today.GetTodayData;
import com.example.hrm.Model.LeavesModel.Leaves;
import com.example.hrm.Model.LeavesModel.WorkFlow;
import com.example.hrm.Model.SideMenu.Menu;
import com.example.hrm.Model.SideMenu.Screen;
import com.example.hrm.Model.SideMenu.SubMenu;
import com.example.hrm.R;
import com.example.hrm.UI.ApprovalActivity;
import com.example.hrm.UI.AttendanceActivity;
import com.example.hrm.UI.BasicSetupActivity;
import com.example.hrm.UI.CalendarActivity;
import com.example.hrm.UI.EditprofActivity;
import com.example.hrm.UI.EmplyManagActivity;
import com.example.hrm.UI.EvalutionActivity;
import com.example.hrm.UI.More;
import com.example.hrm.UI.OrganogramActivity;
import com.example.hrm.UI.PayrollActivity;
import com.example.hrm.UI.RequestActivity;
import com.example.hrm.UI.TasksActivity;
import com.example.hrm.UI.UserProfActivity;
import com.example.hrm.utils.Tools;
import com.example.hrm.utils.ViewAnimation;

import java.util.List;

public class SideMenuAdapter extends RecyclerView.Adapter<SideMenuAdapter.MyViewHolder> {
    Context context;
    Menu screen;
    SubMenu subMenu;
    private List<Menu> getscreenlist;
     List<SubMenu> subMenuList;
     
     
     
    private SideMenuAdapter.OnItemClickListener mOnItemClickListener;


    private RecyclerView.LayoutManager mLayoutManager;
    listadapter listadapter;


    public interface OnItemClickListener {
        void onItemClick(View view, Menu obj, int position);
    }

    public void setOnItemClickListener(final SideMenuAdapter.OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = (SideMenuAdapter.OnItemClickListener) mItemClickListener;
    }
    public SideMenuAdapter(Context context, List<Menu> respons) {
        this.context = context;
        this.getscreenlist = respons;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
        public TextView name;
        public ImageView imageView;
        public CardView cardView;
        public ImageButton btn;
        private NestedScrollView nested_scroll_view;
        private View lyt_expand_text, lyt_expand_input;
        private RecyclerView mRecyclerView;



        public MyViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.txtresume);
            imageView = v.findViewById(R.id.sideimage);
            cardView=v.findViewById(R.id.cardView);
            btn=v.findViewById(R.id.bt_toggle_text);
            // text section
            lyt_expand_text = v.findViewById(R.id.lyt_expand_text);
            mRecyclerView = v.findViewById(R.id.listyrecycler);


        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ly_menu, parent, false);

        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        screen = getscreenlist.get(position);
        subMenuList=screen.getSubMenu();

        if (screen.getSubMenu().size()==0)
        {
            holder.btn.setVisibility(View.GONE);
           // holder.btn_hide.setVisibility(View.GONE);
        }

        else
        {

            holder.btn.setVisibility(View.VISIBLE);
            holder.mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(context);
            holder.mRecyclerView.setLayoutManager(mLayoutManager);

            listadapter = new listadapter(context, subMenuList);
            holder.mRecyclerView.setAdapter(listadapter);


           listadapter.setOnItemClickListener(new listadapter.OnItemClickListener() {
               @Override
               public void onItemClick(View view, SubMenu obj, int position)
               {

                   String scren= obj.getName();
                   Toast.makeText(context, ""+scren, Toast.LENGTH_SHORT).show();
                   if(scren.equals("Static Data"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("System Holidays"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }

                   else if(scren.equals("Resume"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }

                   else if(scren.equals("Skills"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);

                   }

                   else if(scren.equals("Shifts"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Designation"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Buildings"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("EOBI Slabs"))
                   {
                       Intent intent = new Intent(context, PayrollActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Department"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Groups"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Workflow"))
                   {
                       Intent intent = new Intent(context, EmplyManagActivity.class);
                       context.startActivity(intent);


                   }

                   else if(scren.equals("Employee"))
                   {
                       Intent intent = new Intent(context, EmplyManagActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Organogram"))
                   {
                       Intent intent = new Intent(context, OrganogramActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Tasks"))
                   {
                       Intent intent = new Intent(context, TasksActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Roles"))
                   {
                       Intent intent = new Intent(context, BasicSetupActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Attendance Approval"))
                   {
                       Intent intent = new Intent(context, ApprovalActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Leave Approval"))
                   {
                       Intent intent = new Intent(context, ApprovalActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("General Request Approval"))
                   {
                       Intent intent = new Intent(context, ApprovalActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Equipment Approval"))
                   {
                       Intent intent = new Intent(context, ApprovalActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Attendance"))
                   {
                       Intent intent = new Intent(context, AttendanceActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Calendar"))
                   {
                       Intent intent = new Intent(context, CalendarActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Evaluation"))
                   {
                       Intent intent = new Intent(context, EvalutionActivity.class);
                       context.startActivity(intent);


                   }

                   else if(scren.equals("Requests"))
                   {
                       Intent intent = new Intent(context, RequestActivity.class);
                       context.startActivity(intent);


                   }
                   else if(scren.equals("Profile"))
                   {
                       Intent intent = new Intent(context, EditprofActivity.class);
                       context.startActivity(intent);


                   }


               }
           });


            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    boolean show = toggleArrow(v);
                    if (show)
                    {
                        ViewAnimation.expand(holder.lyt_expand_text, new ViewAnimation.AnimListener() {
                            @Override
                            public void onFinish()
                            {
                                holder.mRecyclerView.setVisibility(View.VISIBLE);
                               // Toast.makeText(context, "close", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                    {
                        ViewAnimation.collapse(holder.lyt_expand_text);


                    }



                }
            });

        }


        holder.name.setText(screen.getCaption());
        holder.imageView.setImageResource(R.drawable.ic_baseline_list_alt_24);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                // check if item still exists
                if (pos != RecyclerView.NO_POSITION)
                {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(v, getscreenlist.get(position), position);

                       // showCustomDialog();
                    }

                }
            }
        });

    }


    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return getscreenlist.size();
       // return leavesList.size();
    }



    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    public static class listadapter extends RecyclerView.Adapter<listadapter.MyViewHolder> {
        Context context;
        SubMenu subMenu;

        private List<SubMenu> subMenuList;


        private listadapter.OnItemClickListener mOnItemClickListener;


        private RecyclerView.LayoutManager mLayoutManager;
        listadapter listadapter;


        public interface OnItemClickListener {
            void onItemClick(View view, SubMenu obj, int position);
        }

        public void setOnItemClickListener(final listadapter.OnItemClickListener mItemClickListener) {
            this.mOnItemClickListener = (listadapter.OnItemClickListener) mItemClickListener;
        }


        public listadapter(Context context, List<SubMenu> leaves) {
            this.context = context;
            this.subMenuList= leaves;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            //        // each data item is just a string in this case
            public TextView name;


            public MyViewHolder(View v) {
                super(v);
                name = v.findViewById(R.id.text);

            }
        }


        @NonNull
        @Override
        public listadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ly_sidemenu, parent, false);

            listadapter.MyViewHolder vh = new listadapter.MyViewHolder(v);

            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final listadapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

            subMenu = subMenuList.get(position);

            holder.name.setText(subMenu.getName());

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        if (mOnItemClickListener != null)
                        {
                            mOnItemClickListener.onItemClick(v, subMenuList.get(position), position);

                            // showCustomDialog();
                        }

                    }
                }
            });


        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return subMenuList.size();
            // return s.size();
        }



        @Override
        public int getItemViewType(int i) {
            return 0;
        }
    }
}