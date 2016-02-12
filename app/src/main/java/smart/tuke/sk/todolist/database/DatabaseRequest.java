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
		return insert(context, object.tag, object.name, object.description, object.date, object.hours, object.minutes,
		              object.finished);
	}

	public static boolean insert(Context context, Long tag, String name, String description, Long date, Long hours,
	                             Long minutes, Boolean finished)
	{
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try
		{
			db.execSQL(String.format("INSERT INTO tasks VALUES (NULL, %d, '%s', '%s', %d, %d, %d, '%s')",
			                         tag, name, description, date, hours, minutes, (finished ? "true" : "false")
			));
		}
		catch (SQLiteConstraintException e)
		{
			Log.e(TAG, "Trying to insert existing task entry");
			return false;
		}
		catch (SQLiteException e)
		{
			Log.e(TAG, "Insert error");
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
	                             Long hours, Long minutes, Boolean finished)
	{
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try
		{
			String cmd = String.format
				(
					"UPDATE tasks SET tag=%d, name='%s', description='%s', date=%d, hours=%d, minutes=%d, finished='%s' WHERE id=%d ",
					tag, name, description, date, hours, minutes, (finished ? "true" : "false"), id
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

	public static boolean updateFinished(Context context, Integer id, Boolean finished)
	{
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try
		{
			String cmd = String.format
				(
					"UPDATE tasks SET finished='%s' WHERE id=%d ",
					(finished ? "true" : "false"), id
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
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		try
		{
			ArrayList<DatabaseObject> list = load(db, context);
			return list;
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
	}

	//Overloaded version with explicitly stated database
	public static ArrayList<DatabaseObject> load(SQLiteDatabase db, Context context) throws SQLiteException
	{
		ArrayList<DatabaseObject> list = new ArrayList<>();

		//db.execSQL("SELECT * FROM tasks");
		Cursor c = db.rawQuery("SELECT * FROM tasks", new String[]{});

		if (c.moveToFirst())
		{
			while (!c.isAfterLast())
			{
				//It is advised to put a field into such try block when db upgrade happens
				Integer id;
				try
				{
					id = Integer.valueOf(c.getString(c.getColumnIndex("id")));
				}
				catch (IllegalStateException e)
				{
					id = 0;
				}

				long tag = Long.valueOf(c.getString(c.getColumnIndex("tag")));
				String name = c.getString(c.getColumnIndex("name"));
				String desc = c.getString(c.getColumnIndex("description"));
				long date = Long.valueOf(c.getString(c.getColumnIndex("date")));
				long hours = Long.valueOf(c.getString(c.getColumnIndex("hours")));
				long minutes = Long.valueOf(c.getString(c.getColumnIndex("minutes")));

				Boolean finished;
				try
				{
					finished = Boolean.valueOf(c.getString(c.getColumnIndex("finished")));
				}
				catch (IllegalStateException e)
				{
					finished = false;
				}

				DatabaseObject object = new DatabaseObject(id, tag, name, desc, date, hours, minutes, finished);

				list.add(object);
				c.moveToNext();
			}
		}

		return list;
	}
}
