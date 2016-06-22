package info.dao;

public class DAOFactory {
	public static IUserDao getUserDao(){
		return new UserDao();
	}
	
	public static IMessageDao getMessage(){
		return new MessageDao();
	}
	
	public static ICommentDao getComment() {
		return new CommentDao();
	}
}
