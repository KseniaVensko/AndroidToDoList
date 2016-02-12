package smart.tuke.sk.todolist.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

/**
 * Class that helps with DB requests, contains most used static queries. Does not throw exceptions outside.
 * <p/>
 * Returns symbolically true on success, false on error (or null object on error).
 * <p/>
 * Created by Steve on 11.2.2016.
 */
public class DatabaseRequest
{
	private static final String TAG = "DatabaseRequest";

	public static boolean insert(Context context, DatabaseObject object)
	{
		return insert(context, object.tag, object.name, object.description, object.date, object.hours, object.minutes);
	}

	public static boolean insert(Context context, Long tag, String name, String description, Long date, Long hours,
	                             Long minutes)
	{
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try
		{
			db.execSQL(String.format("INSERT INTO tasks VALUES (NULL, %d, '%s', '%s', %d, %d, %d)",
			                         tag, name, description, date, hours, minutes
			));
		}
		catch (SQLiteConstraintException e)
		{
			Log.e(TAG, "Trying to insert existing task entry");
			return false;
		}
		finally
		{
			db.close();
			dbHelper.close();
		}
		return true;
	}

	public static boolean update(Context context, Integer id, Long tag, String name, String description, Long date,
	                             Long hours, Long minutes)
	{
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try
		{
			String cmd = String.format
				(
					"UPDATE tasks SET tag=%d, name='%s', description='%s', date=%d, hours=%d, minutes=%d WHERE id=%d ",
					tag, name, description, date, hours, minutes, id
				);
			db.execSQL(cmd);
			Log.i(TAG, cmd);
		}
		catch (SQLiteException e)
		{
			Log.e(TAG, "Error trying to update task entry");
			return false;
		}
		finally
		{
			db.close();
			dbHelper.close();
		}
		return true;
	}

	public static boolean delete(Context context, Integer id)
	{
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try
		{
			db.execSQL
				(
					String.format
						(
							"DELETE FROM tasks WHERE id=%d ",
							id
						)
				);
		}
		catch (SQLiteException e)
		{
			Log.e(TAG, "Error trying to update task entry");
			return false;
		}
		finally
		{
			db.close();
			dbHelper.close();
		}
		return true;
	}

	public static ArrayList<DatabaseObject> load(Context context)
	{
		ArrayList<DatabaseObject> list = new ArrayList<>();

		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		try
		{
			//db.execSQL("SELECT * FROM tasks");
			Cursor c = db.rawQuery("SELECT * FROM tasks", new String[]{});

			if (c.moveToFirst())
			{
				while (!c.isAfterLast())
				{
					Integer id = Integer.valueOf(c.getString(c.getColumnIndex("id")));
					long tag = Long.valueOf(c.getString(c.getColumnIndex("tag")));
					String name = c.getString(c.getColumnIndex("name"));
					String desc = c.getString(c.getColumnIndex("description"));
					long date = Long.valueOf(c.getString(c.getColumnIndex("date")));
					long hours = Long.valueOf(c.getString(c.getColumnIndex("hours")));
					long minutes = Long.valueOf(c.getString(c.getColumnIndex("minutes")));

					DatabaseObject object = new DatabaseObject(id, tag, name, desc, date, hours, minutes);

					list.add(object);
					c.moveToNext();
				}
			}
		}
		catch (SQLiteException e)
		{
			Log.e(TAG, "Error during loading database list");
			return null;
		}
		finally
		{
			db.close();
			dbHelper.close();
		}

		return list;
	}
}
