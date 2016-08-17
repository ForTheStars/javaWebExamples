package info.test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import info.dao.CommentDao;
import info.dao.DAOFactory;
import info.dao.ICommentDao;
import info.model.Comment;
import info.model.Message;
import info.model.Pager;
import info.model.SystemContext;

public class TestCommentDao {
	private static ICommentDao commentDao;
	
	@Before
	public void before(){
		commentDao = DAOFactory.getComment();
	}
	
	@Test
	public void testAdd() {
		Comment comment = new Comment();
		comment.setContent("回复内容如下：....");
		comment.setPostDate(new Date());
		comment.setMsgId(1);
		comment.setUserId(1);
		commentDao.add(comment, 1, 1);
	}
	
	@Test
	public void testLoad() {
		Comment comment = commentDao.load(1);
		System.out.println(comment.getContent() + "  " + comment.getPostDate());
	}
	
	@Test
	public void testDelete() {
		commentDao.delete(2);
	}
	
	@Test
	public void testList() {
		SystemContext.setPageIndex(1);
		SystemContext.setPageSize(15);
		Pager<Comment> ps = commentDao.list(1);
		System.out.println(ps.getTotalRecord());
		for(Comment comment:ps.getDatas()) {
			System.out.println(comment);
		}
	}
}
