package smart.tuke.sk.todolist.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import smart.tuke.sk.todolist.R;
import smart.tuke.sk.todolist.Task;
import smart.tuke.sk.todolist.database.DatabaseObject;

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
		((CheckBox) rowView.findViewById(R.id.checkBoxFinished)).setChecked(true);//todo

		holder.cb = (TextView) rowView.findViewById(R.id.textinlist);

		//Information for a future Task activity
		rowView.setOnClickListener(new View.OnClickListener()
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
				v.getContext().startActivity(intent);
			}
		});
		return rowView;
	}
}
