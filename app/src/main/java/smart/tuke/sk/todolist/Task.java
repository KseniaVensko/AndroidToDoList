package smart.tuke.sk.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import smart.tuke.sk.todolist.database.DatabaseRequest;
import smart.tuke.sk.todolist.scheduling.Alarm;

import java.util.Date;

public class Task extends AppCompatActivity
{
	private static final String TAG = "Task";
	private Integer id;
	private String task_name;
	private Date date;
	private long hours;
	private long minutes;
	private String description;

	//Specifies if the task is supposed to be a new task
	private Boolean newTask;

	private CheckBox box1;
	private CheckBox box2;

	//Conversion between date formats: Date and Long
	public Long dateToLong(Date date)
	{
		return date.getTime();
	}
	public Date dateFromLong(Long input)
	{
		return new Date(input);
	}

	//Returns true only if fields are correct for intention of saving them into a database (by updating or creating)
	protected boolean dataCorrectForSaving()
	{
		if (this.task_name.isEmpty() || this.description.isEmpty())
		{
			return false;
		}

		return true;
	}

	//Saving entire status of current Task activity into database, updating and overwriting data with the same ID
	public boolean saveCurrentData()
	{
		//Loads all important data into fields so that they can be inserted into a database
		loadFields();

		if(!dataCorrectForSaving())
		{
			Log.i(TAG,"Data have incorrect format, dataCorrectForSaving() returned false.");
			return false;
		}

		//Calculating tag
		long category = 0;
		if (box1.isChecked())
		{
			category = 1;
		}
		else if (box2.isChecked())
		{
			category = 2;
		}

		//Based on the "intent", new task gets created, or the existing one gets modified
		if (this.newTask)
		{
			return DatabaseRequest
				.insert(this, category, this.task_name, this.description, dateToLong(this.date), this.hours,
				        this.minutes);
		}
		else
		{
			return DatabaseRequest.update
				(this, this.id, category, this.task_name, this.description, dateToLong(this.date), this.hours,
				 this.minutes);
		}
	}

	//Loads fields (local variables) from the layout objects (widgets)
	public void loadFields()
	{
		//Getting layout widget references
		EditText etTaskName = (EditText) findViewById(R.id.TaskNameEdit);
		DatePicker etDate = (DatePicker) findViewById(R.id.datePicker);
		TimePicker etTime = (TimePicker) findViewById(R.id.timePicker);
		EditText etDescription = (EditText) findViewById(R.id.DescriptionEdit);

		//Saving them to fields
		this.task_name = etTaskName.getText().toString();
		this.date = new Date(etDate.getCalendarView().getDate());
		this.hours = etTime.getCurrentHour();
		this.minutes = etTime.getCurrentMinute();
		this.description = etDescription.getText().toString();
	}

	//Loads category (using tag)
	public void loadCategory(long tag)
	{
		if (tag == 1)
		{
			box1.setChecked(true);
			box2.setChecked(false);
		}

		if (tag == 2)
		{
			box1.setChecked(false);
			box2.setChecked(true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);

		//Saving cat(egorie)s into boxes
		this.box1 = (CheckBox) findViewById(R.id.Cat1);
		this.box2 = (CheckBox) findViewById(R.id.Cat2);

		//Getting layout widget references
		EditText etTaskName = (EditText) findViewById(R.id.TaskNameEdit);
		DatePicker etDate = (DatePicker) findViewById(R.id.datePicker);
		TimePicker etTime = (TimePicker) findViewById(R.id.timePicker);
		EditText etDescription = (EditText) findViewById(R.id.DescriptionEdit);

		//Putting variables from intent to fields and layout

		Intent intent = getIntent();
		if (intent != null)
		{
			//This block gets executed when user adds a new task
			if (intent.hasExtra("new"))
			{
				this.newTask = true;

				//When creating a new item there should be no
				(findViewById(R.id.delButton)).setVisibility(Button.INVISIBLE);
			}
			//This block gets executed if user opened an existing entry for updating
			else
			{
				this.newTask = false;

				//Loading fields
				loadCategory(intent.getLongExtra("tag", 0));
				this.id = intent.getIntExtra("id", 0);
				this.task_name = intent.getStringExtra("name");
				this.description = intent.getStringExtra("desc");
				this.date = dateFromLong(intent.getLongExtra("date", 0));
				this.hours = intent.getLongExtra("hours", 0);
				this.minutes = intent.getLongExtra("minutes", 0);

				//Loading layout from fields
				etTaskName.setText(this.task_name);
				etDescription.setText(this.description);
				etTime.setCurrentHour((int) this.hours);
				etTime.setCurrentMinute((int) this.minutes);
				etDate.getCalendarView()
				      .setDate(dateToLong(this.date)); //possible overload setDate() with animation maybe

				//Something with alarm?
				//Not sure if it has to be executed when new task gets created (outside this block),
				//but if yes, may crash on wrong data! //todo check
				Alarm alarm = new Alarm();
				alarm.setAlarm(getApplicationContext(), (int) this.hours, (int) this.minutes, this.date);
			}
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Log.i(TAG,
		      "Activity is resumed and current task ID is " + this.id + " (a" + (this.newTask ? " new" : "n existing") +
		      " task), just making sure it doesn't reset on Destroy.");
	}

	public void onSaveClicked(View v)
	{
		//Saving to DB, only finishes this activity if the database request goes okay
		if (saveCurrentData())
		{
			Toast.makeText(this, "Task saved successfully", Toast.LENGTH_SHORT).show();

			//End of current Task activity
			finish();
		}
		else
		{
			Toast.makeText(this, "There is a syntax error somewhere", Toast.LENGTH_SHORT).show();
		}
	}

	//TODO add a cancel button maybe?
	public void onCancelClicked(View v)
	{
		finish();
	}

	//Deletes current opened task defined by its ID
	public void onDeleteClicked(View v)
	{
		if (DatabaseRequest.delete(this, this.id))
		{
			Toast.makeText(this, "Task is permanently deleted", Toast.LENGTH_SHORT).show();

			//End of current Task activity
			finish();
		}
		else
		{
			Toast.makeText(this, "There is some problem.", Toast.LENGTH_SHORT).show();
		}
	}

	public void onCat1Clicked(View v)
	{
		if (((CheckBox) v).isChecked())
		{
			box2.setChecked(false);
		}
	}

	public void onCat2Clicked(View v)
	{
		if (((CheckBox) v).isChecked())
		{
			box1.setChecked(false);
		}
	}
}
