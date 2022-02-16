package com.example.cuktuslist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Homefrgment extends Fragment  {
    private Button btn_add;
    private RecyclerView recyclerView;
    private TextView tv_date;
    private List<Todolist>todolistitem=new ArrayList<>();
    private TodolistAdaptor adaptor;
    private Activity activity;
    private Todolistdatabase db_helper;
    private TextView TV_Instructions;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.writelist_layout,container,false);
        assert container != null;
        this.activity= (Activity) container.getContext();
        tv_date=(TextView)view.findViewById(R.id.tv_date);
        btn_add=(Button)view.findViewById(R.id.btn_addlist);
        TV_Instructions=(TextView)view.findViewById(R.id.tv_instruction);
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        tv_date.setText(year+" / "+month+" / "+day);
        db_helper=new Todolistdatabase(container.getContext());
        todolistitem=db_helper.showdata();
        if (todolistitem.size()>0){
            TV_Instructions.setVisibility(View.GONE);
        }
        else {
            TV_Instructions.setVisibility(View.VISIBLE);
        }
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog=new Dialog(container.getContext());
                dialog.setContentView(R.layout.insertlist_dialog_layout);
                setsize(dialog);
                dialog.setCancelable(true);
                dialog.show();
                final EditText Input_job=(EditText) dialog.findViewById(R.id.input_job);
                final EditText Input_instructions=(EditText) dialog.findViewById(R.id.input_instructions);
                Button btn_submit=(Button)dialog.findViewById(R.id.btn_submit);
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TV_Instructions.setVisibility(View.GONE);
                        Todolist todolist=new Todolist();
                        todolist.setJob(Input_job.getText().toString().trim());
                        todolist.setInstructions(Input_instructions.getText().toString().trim());
                        db_helper.insertdata(todolist);
                        todolistitem= db_helper.showdata();
                        showlist();
                        adaptor.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        });
        showlist();
        return view;
    }
    public Point setdialogpoint(Activity activity){
        Point point=new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point;
    }
    public void setsize(Dialog dialog){
        Point size=setdialogpoint(activity);
        if (dialog.getWindow()!=null){
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,(int)(0.6*size.x));
        }
    }
    public void showlist(){
        adaptor=new TodolistAdaptor(todolistitem,activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity,RecyclerView.VERTICAL,false));
        adaptor.notifyDataSetChanged();
        recyclerView.setAdapter(adaptor);
    }
    @Override
    public void onResume() {
        if (todolistitem.size()>0){
            TV_Instructions.setVisibility(View.GONE);
        }
        else {
            TV_Instructions.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }
}
