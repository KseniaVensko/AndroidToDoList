package smart.tuke.sk.todolist.categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import smart.tuke.sk.todolist.R;
import smart.tuke.sk.todolist.adapters.CategoryAdapter;
import smart.tuke.sk.todolist.tasklists.CategoryActivity;

import java.util.ArrayList;

/**
 * A user may choose from several categories.
 */
public class CategoryChooserActivity extends AppCompatActivity
{
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category_chooser);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		this.listView = (ListView) findViewById(R.id.useThisList);

		//Populate the listview using all categories
		CategoryAdapter ca = new CategoryAdapter(this, Category.get());
		this.listView.setAdapter(ca);
	}

	//Reverse calculating, because i give up
	protected long getCategoryTagFromName(String name)
	{
		for (Category c : Category.get())
		{
			if (c.getName().equals(name))
			{
				return c.getTag();
			}
		}
		return 0;
	}

	public void categorySearchClick(View view)
	{
		//We will store matching categories (only their numbers - tags) in an ArrayList
		ArrayList<Long> list = new ArrayList<>();

		//This code looks very dangerous without try block though
		for (int i = 0; i < this.listView.getChildCount(); i++)
		{
			LinearLayout layout = (LinearLayout) listView.getChildAt(i);

			//The child himself is inside a layout, inside a layout.
			//I will assume, and hope, that he will be the first one there.
			LinearLayout layout2 = (LinearLayout) layout.getChildAt(0);
			CheckBox child = (CheckBox) layout2.getChildAt(0);

			if (child.isChecked())
			{
				long tag = getCategoryTagFromName(child.getText().toString());
				if (tag > 0)
				{
					list.add(tag);
				}
			}
		}

		if(list.size()==0)
		{
			//User has to select at least one category
			Toast.makeText(this, "Select at least one category",Toast.LENGTH_SHORT).show();
		}
		else
		{
			long[] convertedlist = new long[list.size()];
			for (int i = 0; i < list.size(); i++)
			{
				convertedlist[i] = list.get(i);
			}

			//Array of category tags will be passed to the category activity class
			Intent intent = new Intent(this, CategoryActivity.class);
			intent.putExtra("categories", convertedlist);
			startActivity(intent);
		}
	}
}
