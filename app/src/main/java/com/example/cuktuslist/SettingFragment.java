package com.example.cuktuslist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class SettingFragment extends Fragment {
    private ImageView iv_name,iv_calender,iv_Task;
    private Activity activity;
    private EditText Username;
    private Button btn_save;
    private TextView tv_jobsundone;
    private getname activtiy;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    private  List<Todolist>todolists;
    private Todolistdatabase db_helper;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dashbord_layout,container,false);
        iv_name=(ImageView)view.findViewById(R.id.name_image);
        iv_calender=(ImageView)view.findViewById(R.id.calender);
        tv_jobsundone=(TextView)view.findViewById(R.id.jobsundone);
        iv_Task=(ImageView)view.findViewById(R.id.task);
        activtiy=(getname)getActivity();
        preferences=container.getContext().getSharedPreferences("username",0);
         editor=preferences.edit();
        activity= (Activity) container.getContext();
        settingsjobs(container.getContext());
        getusernametoactivity();
        return view;
    }
    private void settingsjobs(final Context context) {
        iv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.username_layout);
                setdialogsize(dialog);
                dialog.setCancelable(true);
                btn_save=(Button)dialog.findViewById(R.id.btn_save);
                Username=(EditText)dialog.findViewById(R.id.inputusername);
                Username.setText(preferences.getString("Username","User"));
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doname(editor);
                        getusernametoactivity();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        iv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.calenderview_layout);
                dialog.show();

            }

        });
        iv_Task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_helper=new Todolistdatabase(context);
                todolists=db_helper.showdata();
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("برنامه های انجام نشده");
                builder.setMessage("در حال حاضر"+todolists.size()+"انجام نشده");
                builder.setCancelable(true);
                builder.show();
            }
        });
    }
    public void doname(SharedPreferences.Editor editor){
        editor.putString("Username",Username.getText().toString().trim());
        editor.apply();
    }
    public Point getsize(Activity activity){
        Point size=new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return size;
    }
    public void setdialogsize(Dialog dialog){
        Point point=getsize(activity);
        dialog.getWindow().setLayout(((point.x)),ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public interface getname{
        public void username(String username);
    }
    public void getusernametoactivity(){
        String username=preferences.getString("Username","user");
        activtiy.username(username);
    }
}
