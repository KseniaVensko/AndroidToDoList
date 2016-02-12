package smart.tuke.sk.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import smart.tuke.sk.todolist.adapters.CustomAdapter;
import smart.tuke.sk.todolist.categories.CategoryActivity;
import smart.tuke.sk.todolist.categories.CategoryChooserActivity;
import smart.tuke.sk.todolist.database.DatabaseObject;
import smart.tuke.sk.todolist.database.DatabaseRequest;

import java.util.ArrayList;

public class Main_Activity extends AppCompatActivity
	implements NavigationView.OnNavigationItemSelectedListener
{
	private static final String TAG = "Main_Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		//Toolbar, actionbutton, drawer, navigation
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
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
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
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

	//Subclasses can filter the list if they want to - this is a hook
	protected ArrayList<DatabaseObject> filterList(
		ArrayList<DatabaseObject> list)
	{
		return list;
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

		CustomAdapter ca = new CustomAdapter(this, filterList(list));
		lv.setAdapter(ca);
	}

	//If drawer is currently open, just close it on back press (instead of quitting)
	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the Main_Activity; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings ("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item)
	{
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.my_tasks)
		{
			Intent intent = new Intent(getApplicationContext(), Main_Activity.class);
			//We don't want 2 instances of the main activity, do we?
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		else if (id == R.id.categories)
		{
			startActivity(new Intent(getApplicationContext(), CategoryChooserActivity.class));
		}
		else if (id == R.id.settings)
		{
			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
