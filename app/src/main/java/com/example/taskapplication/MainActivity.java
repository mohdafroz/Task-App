package com.example.taskapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.taskapplication.adapter.TaskAdapter;
import com.example.taskapplication.database.DataBase;
import com.example.taskapplication.model.Task;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskRv;
    private Button addTaskBtn;
    private List<Task> taskList;
    private LinearLayoutManager linearLayoutManager;
    private DataBase database;
    private TaskAdapter taskAdapter;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRv = findViewById(R.id.task_rv);
        addTaskBtn = findViewById(R.id.add_task_btn);

        //init database
        database = DataBase.getInstance(this);
        //store db value in taskList
        taskList = database.taskDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        taskRv.setLayoutManager(linearLayoutManager);
        taskAdapter = new TaskAdapter(taskList, MainActivity.this);
        //set adapter
        taskRv.setAdapter(taskAdapter);

        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create dialog
                Dialog dialog = new Dialog(MainActivity.this);
                // set content view
                dialog.setContentView(R.layout.dialog_create_task);

                //init width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //int height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.setCancelable(false);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                Button saveBtn = dialog.findViewById(R.id.save_btn);
                Button closeBtn = dialog.findViewById(R.id.close_btn);

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputText = editText.getText().toString().trim();
                        if (!inputText.equals("")) {
                            Task task = new Task();
                            //set text on task data
                            task.setText(inputText);
                            //insert text in database
                            database.taskDao().insert(task);
                            // clear
                            editText.setText("");
                            taskList.clear();
                            Toast.makeText(MainActivity.this, "Successfully added!", Toast.LENGTH_LONG).show();
                            taskList.addAll(database.taskDao().getAll());
                            taskAdapter.notifyDataSetChanged();
                            // dismiss dialog
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "Text should not be empty", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // dismiss dialog
                        dialog.dismiss();
                    }
                });

            }
        });

    }
}