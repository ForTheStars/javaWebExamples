package info.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import info.dao.ICategoryDao;
import info.model.Category;
import info.model.ShopDi;

public class TestCategoryDao extends BaseTest {
	private ICategoryDao categoryDao;
	
	@ShopDi
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Test
	public void test() {
		Category category = new Category();
		category.setName("服装");
		System.out.println(categoryDao);
		categoryDao.add(category);
		category = new Category();
		category.setName("玩具");
		categoryDao.add(category);
		category = new Category();
		category.setName("家电");
		categoryDao.add(category);
		category = new Category();
		category.setName("美妆");
		categoryDao.add(category);
		category = new Category();
		category.setName("学习");
		categoryDao.add(category);
		category = new Category();
		category.setName("家具");
		categoryDao.add(category);
	}
	
	@Test
	public void testLoad(){
		Category category = categoryDao.load(2);
		System.out.println(category.getName());
	}
	
	@Test
	public void testDelete() {
		categoryDao.delete(1);
	}
	
	@Test
	public void testUpdate(){
		Category category = categoryDao.load(2);
		category.setName("游戏");
		categoryDao.update(category);
	}
	
	@Test
	public void testListCon() {
		List<Category> list = categoryDao.list("具");
		for(Category category:list){
			System.out.println(category.getId()+","+category.getName());
		}
	}
}
