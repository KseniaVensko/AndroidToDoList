package smart.tuke.sk.todolist.categories;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import smart.tuke.sk.todolist.adapters.CategoryAdapter;
import smart.tuke.sk.todolist.adapters.CustomAdapter;
import smart.tuke.sk.todolist.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  A user may choose from several categories.
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

	public void categorySearchClick(View view)
	{
		//We will store matching categories (only their numbers - tags) in an ArrayList
		ArrayList<Long> list = new ArrayList<>();

		//This code looks very dangerous without try block though
		for(int i=0; i<this.listView.getChildCount(); i++)
		{
			//Child has his category inside a "tag" (do not mistake this tag for a category's tag = ID number)
			CheckBox child = (CheckBox) listView.getChildAt(i);
			Category category = (Category) child.getTag();

			if(child.isChecked())
			{
				list.add(category.getTag());
			}
		}

		//Array of category tags will be passed to the category activity class
		Intent intent = new Intent(this, CategoryActivity.class);
		intent.putExtra("categories",list.toArray());
		startActivity(intent);
	}
}
