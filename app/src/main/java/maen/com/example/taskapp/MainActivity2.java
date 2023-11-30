package maen.com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private Button btn2;
    private EditText edttxt;
    private ListView listview;
    public static final String DATA = "DATA";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    ArrayList<Task> T = new ArrayList<>();


    private ArrayAdapter<Task> adapter;


    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

    }
    private void saveTasksToSharedPreferences() {
        Gson gson = new Gson();
        String tasksJson = gson.toJson(T);

        editor.putString(DATA, tasksJson);
       editor.apply();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setupSharedPrefs();

       // Intent intent = getIntent();


        btn2 = findViewById(R.id.btn2);
        edttxt = findViewById(R.id.edttxt);
        listview = findViewById(R.id.listview);


        loadTasksFromSharedPreferences();



        adapter = new ArrayAdapter<Task>(MainActivity2.this, android.R.layout.simple_list_item_1, T);
        listview.setAdapter(adapter);


        AdapterView.OnItemClickListener itemClickListener = (parent, view, position, id) -> {
            // Get the selected task
            Task selectedTask = T.get(position);

            // Save the selected task to SharedPreferences
            saveSelectedTask(selectedTask);
           // saveTasksToSharedPreferences();

            // Start TaskDetailsActivity
            Intent intent2 = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent2);
        };




        listview.setOnItemClickListener(itemClickListener);
    }


    private void loadTasksFromSharedPreferences() {
        String tasksJson = prefs.getString(DATA, null);
        if (tasksJson != null) {
            Type type = new TypeToken<ArrayList<Task>>() {}.getType();
            T = new Gson().fromJson(tasksJson, type);
        }
    }



    public void addItemToListView(View view) {
        EditText editText = findViewById(R.id.edttxt);
        String newItem = editText.getText().toString().trim();


        if (!newItem.isEmpty()) {
            Task newTask = new Task(newItem, "Default Description", false);
           // T.add(newTask);


            adapter.notifyDataSetChanged();
            saveTasksToSharedPreferences();

            editText.setText("");


        }

    }

    private void saveSelectedTask(Task task) {
        SharedPreferences prefs = getSharedPreferences(MainActivity3.TASK_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String taskJson = gson.toJson(task);

        editor.putString(MainActivity3.SELECTED_TASK_KEY, taskJson);
        editor.apply();
    }


    public void onClickSend2(View view) {

        String newTaskName = edttxt.getText().toString().trim();

        if (!newTaskName.isEmpty()) {
            Task newTask = new Task(newTaskName, "Default Description", false);
            T.add(newTask);
            saveTasksToSharedPreferences();
            adapter.notifyDataSetChanged();



            addItemToListView(listview);



            // Clear the EditText after adding the task
            edttxt.setText("");
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Save the task list to SharedPreferences when the activity is destroyed
        saveTasksToSharedPreferences();
    }



}