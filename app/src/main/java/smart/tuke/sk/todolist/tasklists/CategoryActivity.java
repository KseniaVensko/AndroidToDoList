package smart.tuke.sk.todolist.tasklists;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import smart.tuke.sk.todolist.R;
import smart.tuke.sk.todolist.Task;
import smart.tuke.sk.todolist.adapters.CustomAdapter;
import smart.tuke.sk.todolist.database.DatabaseObject;
import smart.tuke.sk.todolist.database.DatabaseRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Shows list of tasks within a certain category as a filter for the user.
 * <p/>
 * Created by Steve on 11.2.2016.
 */
public class CategoryActivity extends AppCompatActivity
{
	private static final String TAG = "CategoryActivity";
	//private Category[] categories;
	private long[] categories;

	/*
	Constructor is probably a bad idea within the activity
	public CategoryActivity(Category[] categories)
	{
		this.categories = categories;
	}*/

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				//Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
				Intent intent = new Intent(getApplicationContext(), Task.class);
				intent.putExtra("new", true);
				startActivity(intent);
			}
		});

		Intent intent = getIntent();
		if (!intent.hasExtra("categories"))
		{
			Toast.makeText(this, "Wrong intent for filtered tasks.", Toast.LENGTH_SHORT).show();
			finish();
		}

		this.categories = intent.getLongArrayExtra("categories");
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		//(Re-)Downloading entire database to the listview
		Log.i(TAG, "Re-downloaded entire database");
		ListView lv = (ListView) findViewById(R.id.useThisList);
		repopulateListView(this, lv);
	}

	//Loads the most current data into a listview
	public void repopulateListView(Context context, ListView lv)
	{
		final ArrayList<DatabaseObject> list = DatabaseRequest.load(this);
		if (list == null)
		{
			Toast.makeText(this, "There was a problem loading tasks", Toast.LENGTH_SHORT).show();
			return;
		}

		Collections.sort(list);

		CustomAdapter ca = new CustomAdapter(this, filterList(list));
		lv.setAdapter(ca);
	}

	//Checking if the object matches at least one category (tag)
	private boolean matchesTag(DatabaseObject object)
	{
		for (long category : this.categories)
		{
			if (object.tag == category)
			{
				return true;
			}
		}
		return false;
	}

	//We only filter out the objects that match our category
	protected ArrayList<DatabaseObject> filterList(ArrayList<DatabaseObject> list)
	{
		//We will iterate over the old list and populate our new categoryList
		final ArrayList<DatabaseObject> categoryList = new ArrayList<>();
		Iterator<DatabaseObject> iterator = list.iterator();

		while (iterator.hasNext())
		{
			DatabaseObject object = iterator.next();

			if (matchesTag(object))
			{
				categoryList.add(object);
			}
		}

		return categoryList;
	}
}
