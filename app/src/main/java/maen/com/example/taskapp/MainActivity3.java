package maen.com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity3 extends AppCompatActivity {

    public static final String TASK_PREFS = "task_prefs";
    public static final String SELECTED_TASK_KEY = "selected_task";
    private TextView taskname;
    private EditText planetext;
    private CheckBox ch;
    private Button btnS;
    private Task selectedTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        taskname = findViewById(R.id.taskname);
        planetext = findViewById(R.id.planetext);
        ch = findViewById(R.id.ch);
        btnS= findViewById(R.id.btnS);
        displayTaskDetails();
        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUpdatedTask();

            }
        });


    }


    private Task getSelectedTask() {
        SharedPreferences prefs = getSharedPreferences(TASK_PREFS, MODE_PRIVATE);
        String taskJson = prefs.getString(SELECTED_TASK_KEY, null);

        if (taskJson != null) {
            Gson gson = new Gson();
            return gson.fromJson(taskJson, Task.class);
        }

        return null;
    }

    private void saveUpdatedTask() {
        // Update the task details based on user input
       selectedTask= getSelectedTask();

        if (selectedTask != null) {
            selectedTask.setExplane(planetext.getText().toString());

            SharedPreferences sharedPreferences = getSharedPreferences(TASK_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String updatedTaskJson = new Gson().toJson(selectedTask);
            editor.putString(SELECTED_TASK_KEY, updatedTaskJson);
            editor.commit();

        }



    }

    private void displayTaskDetails() {
        // Get the selected task from SharedPreferences
        Task selectedTask = getSelectedTask();

        // Display the task details
        if (selectedTask != null) {
            taskname.setText(selectedTask.getName());
            planetext.setText(selectedTask.getExplane());
            ch.setChecked(selectedTask.isDoneORNot());

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Save the task list to SharedPreferences when the activity is destroyed
        saveUpdatedTask();

    }}