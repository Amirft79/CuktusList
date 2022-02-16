package com.example.cuktuslist;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobsdoneAdapter extends RecyclerView.Adapter<JobsdoneAdapter.JobsHolder> {
    List<Todolist> todolists;
    Context context;
    JobsDoneDatabase DB_Helper;
    Activity activity;
    public JobsdoneAdapter(List<Todolist> list , Context context){
        this.context=context;
        this.todolists=list;
        DB_Helper=new JobsDoneDatabase(context);
        activity= (Activity) context;
    }


    @NonNull
    @Override
    public JobsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobsHolder holder, int position) {
            holder.fill(todolists.get(position),position);
    }

    @Override
    public int getItemCount() {
        return todolists.size();
    }

    public class JobsHolder extends RecyclerView.ViewHolder{
        TextView tv_job,tv_instructions;
        ImageView image_menu;
        public JobsHolder(@NonNull View itemView) {
            super(itemView);
            tv_job = (TextView) itemView.findViewById(R.id.tv_listitem);
            tv_instructions = (TextView) itemView.findViewById(R.id.tv_ins);
            image_menu = (ImageView) itemView.findViewById(R.id.image_menu);
        }
            public void fill(final Todolist todolist,final int position){
                tv_job.setText(todolist.getJob());
                tv_instructions.setText(todolist.getInstructions());
                final PopupMenu menu=new PopupMenu(image_menu.getContext(),image_menu);
                menu.inflate(R.menu.popuo_menu);
                image_menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        menu.show();
                        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                if (menuItem.getItemId()==R.id.delete_item){
                                    DB_Helper.deleteitem(todolist.getTodoid());
                                    todolists.remove(position);
                                    notifyDataSetChanged();
                                }
                                else if (menuItem.getItemId()==R.id.item_update){
                                    showupdatedialog(todolist);
                                    notifyDataSetChanged();
                                }
                                return false;
                            }
                        });
                    }
                });
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
            public void showupdatedialog(final Todolist todolist){
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.insertlist_dialog_layout);
                setsize(dialog);
                dialog.setCancelable(true);
                dialog.show();
                final EditText Input_job=(EditText)dialog.findViewById(R.id.input_job);
                final EditText Input_instructions=(EditText)dialog.findViewById(R.id.input_instructions);
                Button btn_submit=(Button)dialog.findViewById(R.id.btn_submit);
                Input_job.setText(todolist.getJob());
                Input_instructions.setText(todolist.getInstructions());
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todolist.setJob(Input_job.getText().toString().trim());
                        todolist.setInstructions(Input_instructions.getText().toString().trim());
                        ContentValues values=new ContentValues();
                        values.put(Todolist.job_name,todolist.getJob());
                        values.put(Todolist.instructions_name,todolist.getInstructions());
                        values.put(Todolist.todoid_name,todolist.getTodoid());
                        DB_Helper.updateitem(todolist.getTodoid(),values);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
    }
}
