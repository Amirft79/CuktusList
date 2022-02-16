package com.example.cuktuslist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class JobsDoneDatabase extends SQLiteOpenHelper {
    private Context context;
    public static final String Database_name="JobsDoneDatabase";
    public static  final String Table_name="Jobs_Table";
    public static final int Database_Version=1;
    private static final String CMD2=" CREATE TABLE IF NOT EXISTS '"+Table_name+"'('"+
            Todolist.todoid_name+"'INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,'"+
            Todolist.job_name+"'TEXT,'"+
            Todolist.instructions_name+"'TEXT"+")";
    public JobsDoneDatabase(@Nullable Context context) {
        super(context, Database_name, null, Database_Version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CMD2);

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Table_name);
        onCreate(sqLiteDatabase);
    }
    public void insertcheckedjobs(Todolist todolist){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Todolist.job_name,todolist.getJob());
        values.put(Todolist.instructions_name,todolist.getInstructions());
        sqLiteDatabase.insert(Table_name,null,values);
        Toast.makeText(context, "انجام شد", Toast.LENGTH_SHORT).show();
    }
    public List<Todolist> showjobsdone(){
        List<Todolist>todolists=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM '"+Table_name+"'",null);
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
        db.delete(Table_name,Todolist.todoid_name+"="+id,null);
    }
    public void updateitem(int id,ContentValues values){
        SQLiteDatabase db=getWritableDatabase();
        db.update(Table_name,values,Todolist.todoid_name+"="+id,null);
    }
}
