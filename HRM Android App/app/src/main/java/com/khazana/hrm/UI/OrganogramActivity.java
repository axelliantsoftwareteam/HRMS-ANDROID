package com.khazana.hrm.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.library.CurveBottomBar;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khazana.hrm.Hundler.ApiHandler;
import com.khazana.hrm.Model.CustomProgressDialogue;
import com.khazana.hrm.Model.Organo.OrganoData;
import com.khazana.hrm.Model.Organo.OrganoModel;
import com.khazana.hrm.R;
import com.khazana.hrm.Utility.SessionManager;
import com.khazana.hrm.databinding.ActivityOrganogramBinding;

import java.util.ArrayList;
import java.util.List;

import de.blox.treeview.BaseTreeAdapter;
import de.blox.treeview.TreeNode;
import de.blox.treeview.TreeView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrganogramActivity extends AppCompatActivity {

    ActivityOrganogramBinding binding;
    SessionManager sessionManager;
    String token;
    OrganizationChart organizationChart = OrganizationChart.getInstance(OrganogramActivity.this);

    List<OrganoData> organoDataList = new ArrayList<>();
    OrganoData organoData = new OrganoData();
    WebView webView;
    TreeView treeView;
    BaseTreeAdapter<Viewholder> adapter;

    private CurveBottomBar cbb;
    @SuppressLint("NonConstantResourceId")
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.menu_me:
                Intent intent = new Intent(OrganogramActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_more:
                //  item.getIcon().setTint(ContextCompat.getColor(MainActivity.this, R.color.grey_10));
                Intent intentt = new Intent(OrganogramActivity.this, More.class);
                startActivity(intentt);
                finish();
                return true;
        }
        return false;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        setContentView(R.layout.activity_organogram);

        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        isNetworkConnectionAvailable();
        webView = view.findViewById(R.id.webview);
        sessionManager = new SessionManager(OrganogramActivity.this);
        token = sessionManager.getToken();
        getmember(token);

        treeView = view.findViewById(R.id.idTreeView);
        adapter = new BaseTreeAdapter<Viewholder>(this, R.layout.tree_view_node) {
            @NonNull
            @Override
            public Viewholder onCreateViewHolder(View view) {
                return new Viewholder(view);
            }

            @Override
            public void onBindViewHolder(Viewholder viewHolder, Object data, int position) {
                viewHolder.textView.setText(data.toString());
//                if (data.equals("Khazana Enterprises")){
//                    viewHolder.designationTxt.setText("Khazana Enterprises");
//                }
                for (int i = position; i< organoDataList.size(); i++){
                    String name = organoDataList.get(i).getName();
                    if (name.equals(data.toString())){
                        String image = organoDataList.get(i).getImg();
                        String designation = organoDataList.get(i).getTxt();
                        viewHolder.designationTxt.setText(designation);
                        Glide.with(OrganogramActivity.this)
                                .load(image)
                                .placeholder(R.drawable.ic_baseline_account_circle_24)
                                .into(viewHolder.imageView);
                    }
                }

            }
        };
        treeView.setAdapter(adapter);

        binding.me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganogramActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.fltHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrganogramActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganogramActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });

        binding.icMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Intent intent = new Intent(OrganogramActivity.this, UserProfActivity.class);
                startActivity(intent);
                finish();


            }
        });
        binding.icMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(OrganogramActivity.this, More.class);
                startActivity(intent);
                finish();


            }
        });




        binding.imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrganogramActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        cbb = findViewById(R.id.nav_view);
        cbb.setBottomBarColor(getResources().getColor(R.color.colorApp));
        cbb.setCurveRadius(90);
        cbb.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
    private void addTreeView(String id, String pid) {

        addNodes(adapter, id, pid);

    }
    TreeNode root;
    private void addNodes(BaseTreeAdapter<Viewholder> adapter, String id, String pid) {
        if (root.getData().equals(pid)) {
            root.addChild(new TreeNode(id));
        }

        List<TreeNode> list = root.getChildren();

        addChild(list, root, id, pid);

        adapter.setRootNode(root);
    }

    private void addChild(List<TreeNode> list, TreeNode root, String id, String pid) {
        for (int i = 0; i<list.size(); i++){
            TreeNode parentnode = list.get(i);
            if (parentnode.getData().equals(pid))
            {
                parentnode.addChild(new TreeNode(id));
            }
            if (parentnode.hasChildren())
            {
                root = parentnode;
                List<TreeNode> subChild = root.getChildren();
                addChild(subChild, root, id, pid);
            }
        }
    }

    public void getmember(final String access_token) {
        try {
            final CustomProgressDialogue dialog;
            dialog = new CustomProgressDialogue(OrganogramActivity.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<OrganoModel> organoModelCall = ApiHandler.getApiInterface().getorgano("Bearer " + access_token);
            Log.e("Tag", "response:" + organoModelCall.toString());

            organoModelCall.enqueue(new Callback<OrganoModel>() {
                @Override
                public void onResponse(Call<OrganoModel> organoModelCall1, Response<OrganoModel> response) {

                    try {
                        Log.d("rreess", response+"");
                        if (response.isSuccessful()) {
                            int status = response.body().getMeta().getStatus();
                            if (status == 200) {
                                organoDataList = response.body().getData().getResponse();

                                for (int i = 0; i < organoDataList.size(); i++) {
                                    //Storing names to string array
                                    organoData = organoDataList.get(i);

                                    String id = organoData.getId();
                                    String pid = organoData.getPid();
                                    Log.d("id,pid", id+":"+pid);
                                    if (root == null) {
                                        for (int j = 0; j < organoDataList.size(); j++) {
                                            if (organoDataList.get(j).getPid().equals("0")) {
                                                root = new TreeNode(organoDataList.get(j).getId());
                                            }
                                        }
                                    }

                                    addTreeView(id, pid);


                                    if (pid.equals("9PID") && id.equals("0"))
                                    {
                                        String name = organoData.getName();
                                        organizationChart.addChildToParent(id,name);
                                        webView.getSettings().setJavaScriptEnabled(true);
                                        webView.loadData(organizationChart.getChart(), "text/html", "UTF-8");


                                    }
//                                    String name = organoData.getName();
//                                    organizationChart.addChildToParent(id,name);
//                                    webView.getSettings().setJavaScriptEnabled(true);
//                                    webView.loadData(organizationChart.getChart(), "text/html", "UTF-8");


                                    if (i == organoDataList.size()-1){
                                        List<TreeNode> list = root.getChildren();
                                        System.out.print(list);
                                        changeNodeData(organoDataList, list, root);
                                        //root.setData("Khazana Enterprises");
                                        for (int j = 0; j < organoDataList.size(); j++) {
                                            String iid = organoDataList.get(j).getId();
                                            String name = organoDataList.get(j).getName();
                                            if (root.getData().equals(iid)) {
                                                root.setData(name);
                                                Log.d("id, name, root", iid + name);
                                            }
                                        }
                                    }


                                }


                                Log.e("Tag", "respone::" + organoDataList.toString());

                                dialog.dismiss();
                            }
                            else if (status == 401) {
                                Toast.makeText(OrganogramActivity.this, "Your session is expired please login again", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                sessionManager.logoutUser();
                                Intent intent = new Intent(OrganogramActivity.this, SignIn.class);
                                startActivity(intent);

                            }
                            else{
                                String msg=response.body().getMeta().getMessage();
                                Toast.makeText(OrganogramActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        } else {

                            Toast.makeText(OrganogramActivity.this, "" + response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        try {

                            Log.e("Tag", "error=" + e.toString());
                            dialog.dismiss();

                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                            dialog.dismiss();
                        }

                    }
                }

                @Override
                public void onFailure(Call<OrganoModel> call, Throwable t) {
                    try {

                        Log.e("Tag", "error" + t.toString());
                        Toast.makeText(OrganogramActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                }


            });


        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void changeNodeData(List<OrganoData> organoDataList, List<TreeNode> list, TreeNode root) {
        for (int i = 0; i < list.size(); i++){
            for (int j = 0; j < organoDataList.size(); j++) {
                String id = organoDataList.get(j).getId();
                String name = organoDataList.get(j).getName();
                Log.d("id, name", id+name);
                if (list.get(i).getData().equals(id)){
                    root.getChildren().get(i).setData(name);
                    System.out.print(list);
                    if (list.get(i).hasChildren()){
                        TreeNode subChildNode = list.get(i);
                        List<TreeNode> subChild = list.get(i).getChildren();
                        changeNodeData(organoDataList, subChild, subChildNode);
                    }
                }
            }
        }
    }


    public boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if (isConnected) {
            Log.d("Network", "Connected");
            return true;
        } else {
            checkNetworkConnection();
            Log.d("Network", "Not Connected");
            return false;
        }
    }

    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        // do something on back.
        Intent i = new Intent(OrganogramActivity.this, MainActivity.class);
        startActivity(i);
        finish();

    }


    public class Viewholder{
        TextView textView, designationTxt;
        CircleImageView imageView;

        Viewholder(View view){
            textView = view.findViewById(R.id.idTvnode);
            designationTxt = view.findViewById(R.id.txtTvnode);
            imageView = view.findViewById(R.id.image);
        }
    }
}