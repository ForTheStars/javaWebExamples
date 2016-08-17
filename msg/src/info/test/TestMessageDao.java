package info.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import info.dao.DAOFactory;
import info.dao.IMessageDao;
import info.model.Message;
import info.model.Pager;
import info.model.SystemContext;
import info.model.User;

public class TestMessageDao {
	private static IMessageDao messageDao;
	
	@Before
	public void before() {
		messageDao = DAOFactory.getMessage();
	}
	
	@Test
	public void testAdd() {
		Message message = new Message();
		message.setTitle("主题测试");
		message.setContent("测试内容如下：....");
		message.setPostDate(new Date());
		message.setUserId(1);
		messageDao.add(message, 1);
	}
	
	@Test
	public void testLoad() {
		Message message = messageDao.load(1);
		System.out.println(message.getTitle()+"\n"+message.getContent());
	}
	
	@Test
	public void testUpdate() {
		Message message = messageDao.load(1);
		message.setTitle("主题被更改了");
		message.setContent("内容也被更改了...");
		messageDao.update(message);
	}
	
	@Test
	public void testDelete() {
		messageDao.delete(2);
	}
	
	@Test
	public void testList() {
		SystemContext.setPageIndex(1);
		SystemContext.setPageSize(15);
		Pager<Message> ps = messageDao.list();
		System.out.println(ps.getTotalRecord());
		for(Message message:ps.getDatas()) {
			System.out.println(message);
		}
	}
	
	@Test
	public void testCommentCount() {
		int count = messageDao.getCommentCount(1);
		System.out.println(count);
	}
}
