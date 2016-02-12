package smart.tuke.sk.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matúš on 11.2.2016.
 */
public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Task> list = new ArrayList<>();
    private static LayoutInflater inflater=null;
    public CustomAdapter(list tasklist,List tasks) {
        context=tasklist;
        list = tasks;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Holder
    {
       CheckBox cb;
    }
    //
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.activity_list, null);
        holder.cb =(CheckBox)rowView.findViewById(R.id.checkBox);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"SLDLASK",Toast.LENGTH_SHORT).show()
                Intent intent = new Intent(v.getContext(),DetailEvent.class);
                intent.putExtra("event",list.get(position));
                v.getContext().startActivity(intent);
            }
        });
        return rowView;
    }
    private int getImage(String cat){
        switch (cat){
            case "Divadlo" : return R.drawable.theatre;
            case "sport" : return R.drawable.sport;
            case "Koncert" : return R.drawable.music;
            case "party" : return R.drawable.party;
            case "veda a vyskum" : return R.drawable.science;
            case "festival" : return R.drawable.fest;
            default: return R.drawable.fest;
        }
    }
}