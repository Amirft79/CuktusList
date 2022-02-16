package com.example.cuktuslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArchiveActivtiy extends AppCompatActivity {
    private  RecyclerView jobsdonerecycler;
    private JobsdoneAdapter adapter;
    private List<Todolist>todolists=new ArrayList<>();
    private JobsDoneDatabase DB_Helper;
    private TextView TV_Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive_activtiy);
        jobsdonerecycler=(RecyclerView)findViewById(R.id.jobsdonerecycler);
        TV_Result=(TextView)findViewById(R.id.tv_result);
        DB_Helper=new JobsDoneDatabase(this);
        SQLiteDatabase database=DB_Helper.getWritableDatabase();
        todolists=DB_Helper.showjobsdone();
        if (todolists.size()>0){
            TV_Result.setVisibility(View.GONE);
        }
        else {
            TV_Result.setVisibility(View.VISIBLE);
        }
        adapter=new JobsdoneAdapter(todolists,this);
        jobsdonerecycler.setLayoutManager(new LinearLayoutManager(this));
        jobsdonerecycler.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        if (todolists.size()>0){
            TV_Result.setVisibility(View.GONE);
        }
        else {
            TV_Result.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }

}