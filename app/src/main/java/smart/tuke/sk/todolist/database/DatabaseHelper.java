package smart.tuke.sk.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Connects do our db
 * <p/>
 * Created by Steve on 11.2.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String TAG = "DatabaseHelper";
	public static final int DATABASE_VERSION = 7;

	private static final String SQL_CREATE_TASK = "CREATE TABLE tasks (" +
	                                              "id INTEGER PRIMARY KEY, " +
	                                              "tag LONG, " + //category id number, only one category per task
	                                              "name TEXT NOT NULL, " +
	                                              "description TEXT NOT NULL, " +
	                                              "date LONG, " +
	                                              "hours LONG, " +
	                                              "minutes LONG" +
	                                              ")";

	/*private static final String SQL_CREATE_FORECAST = "CREATE TABLE forecast (" +
	                                                  "_id INTEGER PRIMARY KEY, " +
	                                                  "dt DATETIME UNIQUE NOT NULL, " +
	                                                  "city_id INTEGER, " +
	                                                  "data TEXT, " +
	                                                  "FOREIGN KEY(city_id) REFERENCES city(id))";*/


	public DatabaseHelper(Context context)
	{
		super(context, "todo.db", null, DATABASE_VERSION);
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

		//todo rework
		db.execSQL("DROP TABLE IF EXISTS tasks");
		onCreate(db);
	}
}
