package smart.tuke.sk.todolist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import smart.tuke.sk.todolist.R;
import smart.tuke.sk.todolist.categories.Category;

/**
 * Adapter for Categories in form of checkboxes.
 * <p/>
 * It doesn't need to change onCheckedChange events.
 * <p/>
 * Created by Steve on 12.2.2016.
 */
public class CategoryAdapter extends BaseAdapter
{
	private Context context;
	private Category[] categories;

	private static LayoutInflater inflater = null;

	public CategoryAdapter(Context tasklist, Category[] tasks)
	{
		this.context = tasklist;
		this.categories = tasks;
		CategoryAdapter.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return categories.length;
	}
	@Override
	public Object getItem(int position)
	{
		return categories[position];
	}
	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		View rowView = inflater.inflate(R.layout.onerow_checkbox, null);
		((TextView) rowView.findViewById(R.id.checkBox)).setText(this.categories[position].getName());

		//A new way of handling the situation using tags
		//rowView.setTag(this.categories[position]);
		/*
		//Expecting it to always be a checkbox, we make a checkedchange listener and keep track of current state
		CheckBox box = (CheckBox)rowView;
		box.setOnCheckedChangeListener(
			new CompoundButton.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					//Each category, as a singleton, will have its own checked state
					//Category.get()[position].setChecked(isChecked);
				}
			}
		);
		*/

		return rowView;
	}
}
