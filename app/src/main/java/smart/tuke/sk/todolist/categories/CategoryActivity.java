package smart.tuke.sk.todolist.categories;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import smart.tuke.sk.todolist.database.DatabaseObject;
import smart.tuke.sk.todolist.Main_Activity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Shows list of tasks within a certain category as a filter for the user.
 * <p/>
 * Created by Steve on 11.2.2016.
 */
public class CategoryActivity extends Main_Activity
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

		Intent intent = getIntent();
		if(!intent.hasExtra("categories"))
		{
			Toast.makeText(this,"Wrong intent for filtered tasks.",Toast.LENGTH_SHORT);
			finish();
		}

		this.categories = intent.getLongArrayExtra("categories");

	}
	//Checking if the object matches at least one category (tag)
	private boolean matchesTag(DatabaseObject object)
	{
		for(Category category: this.categories)
		{
			if(object.tag == category.getTag())
			{
				return true;
			}
		}
		return false;
	}

	//We only filter out the objects that match our category
	@Override
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
