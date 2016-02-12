package smart.tuke.sk.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Connects do our db
 * <p/>
 * Created by Steve on 11.2.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String TAG = "DatabaseHelper";
	public static final int DATABASE_VERSION = 8;

	private Context context;

	private static final String SQL_CREATE_TASK = "CREATE TABLE tasks (" +
	                                              "id INTEGER PRIMARY KEY, " +
	                                              "tag LONG, " + //category id number, only one category per task
	                                              "name TEXT NOT NULL, " +
	                                              "description TEXT NOT NULL, " +
	                                              "date LONG, " +
	                                              "hours LONG, " +
	                                              "minutes LONG, " +
	                                              "finished BIT" +
	                                              ")";

	//Old code as an example:
	/*private static final String SQL_CREATE_FORECAST = "CREATE TABLE forecast (" +
	                                                  "_id INTEGER PRIMARY KEY, " +
	                                                  "dt DATETIME UNIQUE NOT NULL, " +
	                                                  "city_id INTEGER, " +
	                                                  "data TEXT, " +
	                                                  "FOREIGN KEY(city_id) REFERENCES city(id))";*/


	public DatabaseHelper(Context context)
	{
		super(context, "todo.db", null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.i(TAG, "onCreate()");

		db.execSQL(SQL_CREATE_TASK);

		Log.i(TAG, "onCreate() finished");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		Log.i(TAG, String.format("onUpgrade(): old: %d, new: %d", oldVersion, newVersion));

		//Backup of old data
		ArrayList<DatabaseObject> backup = DatabaseRequest.load(db, this.context);

		//Recreating db
		db.execSQL("DROP TABLE IF EXISTS tasks");
		onCreate(db);

		//Reloading a new data
		//TODO: re-work it to work with db instead of DatabaseRequest
		/*
		int i=0;
		for(DatabaseObject backupItem: backup)
		{
			DatabaseRequest.insert(this.context, backupItem);
			i++;
		}
		*/

		Toast.makeText(this.context, "Database updated", Toast.LENGTH_SHORT).show();
	}
}
