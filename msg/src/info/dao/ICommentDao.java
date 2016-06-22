package info.dao;

import info.model.Comment;
import info.model.Pager;

public interface ICommentDao {
	public void add(Comment comment,int userId,int msgId);
	public void delete(int id);
	public Comment load(int id);
	public Pager<Comment> list(int msgId);
}
