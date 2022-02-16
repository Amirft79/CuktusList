package com.example.cuktuslist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SettingFragment.getname {
    private TabLayout HomeTabs;
    private ViewPager viewPager;
    private viewpagerdapter adaptor;
    private Todolistdatabase db_helper;
    private List<Todolist>todolists=new ArrayList<>();
    private TextView tv_username;
    private Toolbar mytolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivtiy);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        HomeTabs=(TabLayout)findViewById(R.id.hometabs);
        tv_username=(TextView)findViewById(R.id.tv_welcome);
        mytolbar=(Toolbar)findViewById(R.id.mytollbar);
        db_helper=new Todolistdatabase(this);
        final SQLiteDatabase db=db_helper.getWritableDatabase();
        todolists=db_helper.showdata();
        setSupportActionBar(mytolbar);
        db_helper=new Todolistdatabase(this);
        Refresh();
        viewPager.setAdapter(adaptor);
        HomeTabs.setupWithViewPager(viewPager);
    }
    public void AddTabs(){
        adaptor.getfergment(new Homefrgment(),"لیست روزانه");
        adaptor.getfergment(new SettingFragment(),"تنظیمات");
    }
    public void Refresh(){
        adaptor=new viewpagerdapter(getSupportFragmentManager());
        AddTabs();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void username(String username) {
        tv_username.setText("خوش آمدی "+username);
    }

    public class viewpagerdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;
        List<String>stringLis;
        public viewpagerdapter(@NonNull FragmentManager fm) {
            super(fm);
            fragmentList=new ArrayList<>();
            stringLis=new ArrayList<>();
        }

        public void getfergment(Fragment fragment,String name){
            fragmentList.add(fragment);
            stringLis.add(name);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return stringLis.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.archive_menu,menu);
        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(HomeActivity.this,ArchiveActivtiy.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}