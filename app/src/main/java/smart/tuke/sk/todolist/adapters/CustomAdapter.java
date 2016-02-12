package smart.tuke.sk.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import smart.tuke.sk.todolist.R;
import smart.tuke.sk.todolist.Task;
import smart.tuke.sk.todolist.database.DatabaseObject;
import smart.tuke.sk.todolist.database.DatabaseRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for Tasks (DatabaseObjects)
 * <p/>
 * Created by Matúš on 11.2.2016.
 */
public class CustomAdapter extends BaseAdapter
{
	private Context context;
	private List<DatabaseObject> list = new ArrayList<>();

	private static LayoutInflater inflater = null;

	public CustomAdapter(Context tasklist, List<DatabaseObject> tasks)
	{
		//changed type to Context, this adapter is called from CategoryActivity too
		context = tasklist;
		list = tasks;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return list.size();
	}
	@Override
	public Object getItem(int position)
	{
		return position;
	}
	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public class Holder
	{
		TextView cb;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		Holder holder = new Holder();
		View rowView;
		rowView = inflater.inflate(R.layout.onerow, null);

		//Filling the information inside each row
		((TextView) rowView.findViewById(R.id.textinlist)).setText(list.get(position).name);
		((CheckBox) rowView.findViewById(R.id.checkBoxFinished)).setChecked(list.get(position).finished);

		holder.cb = (TextView) rowView.findViewById(R.id.textinlist);

		//Information for a future Task activity
		rowView.setOnClickListener(
			new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					//Toast.makeText(v.getContext(),"SLDLASK",Toast.LENGTH_SHORT).show()
					Intent intent = new Intent(CustomAdapter.this.context, Task.class);
					intent.putExtra("id", list.get(position).id);
					intent.putExtra("tag", list.get(position).tag);
					intent.putExtra("name", list.get(position).name);
					intent.putExtra("desc", list.get(position).description);
					intent.putExtra("date", list.get(position).date);
					intent.putExtra("hours", list.get(position).hours);
					intent.putExtra("minutes", list.get(position).minutes);
					intent.putExtra("finished", list.get(position).finished);
					v.getContext().startActivity(intent);
				}
			});

		final CheckBox checkBoxFinished = ((CheckBox) rowView.findViewById(R.id.checkBoxFinished));
		checkBoxFinished.setOnCheckedChangeListener(
			new CheckBox.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					/*
					Old version: was trying to re-run activity, problems appeared with category
					Intent intent = new Intent(CustomAdapter.this.context, buttonView.getContext().getClass());
					//I guess this should be good to not duplicate activity
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					intent
					buttonView.getContext().startActivity(intent);*/

					DatabaseRequest.updateFinished(CustomAdapter.this.context, list.get(position).id, checkBoxFinished.isChecked());
				}
			}
		);
		return rowView;
	}
}
