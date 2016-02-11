package smart.tuke.sk.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task extends AppCompatActivity {

    private String task_name;
    private Date date;
    private String time;
    private String category;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        EditText etTaskName = (EditText) findViewById(R.id.TaskNameEdit);
        DatePicker etDate = (DatePicker) findViewById(R.id.datePicker);
        TimePicker etTime = (TimePicker) findViewById(R.id.timePicker);
        EditText etCategory = (EditText) findViewById(R.id.CategoryEdit);
        EditText etDescription = (EditText) findViewById(R.id.DescriptionEdit);
        this.task_name = etTaskName.getText().toString();
        this.date = new Date(etDate.getCalendarView().getDate());
        SimpleDateFormat sdt = new SimpleDateFormat("HH:MM:SSS");
        this.time = sdt.format(etTime);
        this.category = etCategory.getText().toString();
        this.description = etDescription.getText().toString();
    }

    public void onSaveClicked(View v){
        //saving to DB
        Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show();
    }
}