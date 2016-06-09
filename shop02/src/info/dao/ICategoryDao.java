package info.dao;

import java.util.List;

import info.model.Category;

public interface ICategoryDao {
	public void add(Category category);
	public void delete(int id);
	public void update(Category category);
	public List<Category> list(String name);
	public List<Category> list();
	public Category load(int id);
}
