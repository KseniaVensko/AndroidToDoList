package smart.tuke.sk.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import smart.tuke.sk.todolist.database.DatabaseObject;
import smart.tuke.sk.todolist.database.DatabaseRequest;

import java.util.ArrayList;
import java.util.Random;

public class SettingsActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	public void randomDataClick(View view)
	{
			long m = new Random().nextInt();
			long n = new Random().nextInt();

			DatabaseRequest.insert(this, m,
			                       "first test",
			                       "data",
			                       m, n, n, true);
			DatabaseRequest.insert(this, n,
			                       "second data",
			                       "test",
			                       m, n, m, false);

		ArrayList<DatabaseObject> list = DatabaseRequest.load(this);
		Toast.makeText(this,"After adding 2 records,\ndatabase has "+list.size()+" rows.",Toast.LENGTH_SHORT).show();
	}

	public void eraseDatabaseClick(View view)
	{
		ArrayList<DatabaseObject> load = DatabaseRequest.load(this);
		Toast.makeText(this,"Deleted "+String.valueOf(load.size())+" rows.",Toast.LENGTH_SHORT).show();

		for (int i = 0; i < load.size(); i++)
		{
			DatabaseObject o = load.get(i);
			DatabaseRequest.delete(this, o.id);
		}
	}
}
