package smart.tuke.sk.todolist.categories;

/**
 * Each category has its name.
 * <p/>
 * There are some static categories we use.
 * <p/>
 * Created by Steve on 11.2.2016.
 */
public class Category
{
	public static final int NUMBER_OF_CATEGORIES = 2;

	private static Category[] categories = new Category[NUMBER_OF_CATEGORIES];

	static
	{
		//Our singleton categories
		Category.categories[0] = new Category(1, "School TimeTable");
		Category.categories[1] = new Category(2, "Work");
	}

	private long tag;
	private String name;

	//New category cannot be instantiated
	private Category(int tag, String name)
	{
		this.name = name;
		this.tag = tag;
	}

	//The correct way you should get all categories
	public static Category[] get()
	{
		return Category.categories;
	}

	//Getters for characteristics of a concrete category
	public long getTag()
	{
		return this.tag;
	}
	public String getName()
	{
		return this.name;
	}
}
