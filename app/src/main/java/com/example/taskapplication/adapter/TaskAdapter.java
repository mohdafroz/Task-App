package com.example.taskapplication.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskapplication.R;
import com.example.taskapplication.database.DataBase;
import com.example.taskapplication.model.Task;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    //initialize variables
    private List<Task> taskList;
    private Activity context;
    private DataBase database;
    AlertDialog.Builder builder;

    //create constructor
    public TaskAdapter(List<Task> taskList, Activity context) {
        this.taskList = taskList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        //int task data
        Task task = taskList.get(position);
        //init db
        database = DataBase.getInstance(context);
        //set text in textview
        holder.textView.setText(task.getText());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(v.getContext());
                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure you want to delete this task?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Task task = taskList.get(holder.getAdapterPosition());
                                //delete text from database
                                database.taskDao().delete(task);
                                //notify when data is deleted
                                int position = holder.getAdapterPosition();
                                taskList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, taskList.size());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Confirmation");
                alert.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}
