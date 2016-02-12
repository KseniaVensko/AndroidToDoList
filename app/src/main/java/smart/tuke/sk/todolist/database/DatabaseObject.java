package smart.tuke.sk.todolist.database;

/**
 * object from database = a task
 * <p/>
 * Created by Steve on 11.2.2016.
 */
public class DatabaseObject implements Comparable<DatabaseObject>
{
	public Integer id;
	public Long tag;
	public String name;
	public String description;
	public Long date;
	public Long hours;
	public Long minutes;
	public Boolean finished;

	public DatabaseObject(Integer id, Long tag, String name, String description, Long date, Long hours, Long minutes,
	                      Boolean finished)
	{
		this.id = id;
		this.tag = tag;
		this.name = name;
		this.description = description;
		this.date = date;
		this.hours = hours;
		this.minutes = minutes;
		this.finished = finished;
	}

	@Override
	public int compareTo(DatabaseObject another)
	{
		return ((Integer) (this.finished ? 1 : 0)).compareTo(another.finished ? 1 : 0);
	}
}
