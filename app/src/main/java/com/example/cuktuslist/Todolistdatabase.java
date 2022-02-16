package com.example.cuktuslist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Todolistdatabase extends SQLiteOpenHelper {
    Context context;
    private static final String database_name="database";
    private static final String tablename="table_todo";
    private static final int database_version=6;
    public Todolistdatabase(@Nullable Context context) {
        super(context, database_name, null, database_version);
        this.context=context;
    }
    private static final String CMD=" CREATE TABLE IF NOT EXISTS '"+tablename+"'('"+
            Todolist.todoid_name+"'INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,'"+
            Todolist.job_name+"'TEXT,'"+
            Todolist.instructions_name+"'TEXT"+")";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+tablename);
        onCreate(sqLiteDatabase);
    }
    public void insertdata(Todolist todolist){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Todolist.job_name,todolist.getJob());
        values.put(Todolist.instructions_name,todolist.getInstructions());
        db.insert(tablename,null,values);
        Toast.makeText(context, "اضافه شد", Toast.LENGTH_SHORT).show();
    }
    public List<Todolist> showdata(){
        List<Todolist>todolists=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM'"+tablename+"'",null);
        if (cursor.moveToFirst()){
            do {
                Todolist todolist=new Todolist();
                todolist.setTodoid(cursor.getInt(cursor.getColumnIndex(Todolist.todoid_name)));
                todolist.setJob(cursor.getString(cursor.getColumnIndex(Todolist.job_name)));
                todolist.setInstructions(cursor.getString(cursor.getColumnIndex(Todolist.instructions_name)));
                todolists.add(todolist);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return todolists;
    }
    public void deleteitem(int id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(tablename,Todolist.todoid_name+"="+id,null);
    }
    public void updateitem(int id,ContentValues values){
        SQLiteDatabase db=getWritableDatabase();
        db.update(tablename,values,Todolist.todoid_name+"="+id,null);
    }
}
