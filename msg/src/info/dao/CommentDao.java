package info.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.model.Comment;
import info.model.Message;
import info.model.MsgException;
import info.model.Pager;
import info.model.SystemContext;
import info.util.DBUtil;

public class CommentDao implements ICommentDao {
	private IUserDao userDao;
	private IMessageDao messageDao;
	public CommentDao() {
		userDao = DAOFactory.getUserDao();
		messageDao = DAOFactory.getMessage();
	}
	
	@Override
	public void add(Comment comment, int userId, int msgId) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			if(userDao.load(userId) == null)
				throw new MsgException("添加留言的用户不存在!");
			if(messageDao.load(msgId) == null)
				throw new MsgException("添加留言的评论不存在!");
			connection = DBUtil.getConnection();
			String sql = "insert into t_comment(content,post_date,user_id,msg_id) value(?,?,?,?)";
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, comment.getContent());
			pStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
			pStatement.setInt(3, userId);
			pStatement.setInt(4, msgId);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(pStatement);
		}
	}

	@Override
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "delete from t_comment where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
		}
	}

	@Override
	public Comment load(int id) {
		Comment comment = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_comment where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				comment = new Comment();
				comment.setContent(resultSet.getString("content"));
				comment.setId(resultSet.getInt("id"));
				comment.setPostDate(new Date(resultSet.getTimestamp("post_date").getTime()));
				comment.setUserId(resultSet.getInt("user_id"));
				comment.setId(resultSet.getInt("msg_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(resultSet);
		}
		return comment;
	}

	@Override
	public Pager<Comment> list(int msgId) {
		Pager<Comment> pages = new Pager<Comment>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<Comment> datas = new ArrayList<>();
		pages.setPageOffset(pageOffset);
		pages.setPageSize(pageSize);
		pages.setDatas(datas);
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_comment where msg_id=? order by post_date asc limit ?,?";
			String sqlCount = "select count(*) from t_comment where msg_id=? order by post_date asc";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, msgId);
			preparedStatement.setInt(2, pageOffset);
			preparedStatement.setInt(3, pageSize);
			resultSet = preparedStatement.executeQuery();
			Comment comment = null;
			while(resultSet.next()){
				comment = new Comment();
				comment.setContent(resultSet.getString("content"));
				comment.setId(resultSet.getInt("id"));
				comment.setPostDate(new Date(resultSet.getTimestamp("post_date").getTime()));
				comment.setUserId(resultSet.getInt("user_id"));
				comment.setMsgId(resultSet.getInt("msg_id"));
				datas.add(comment);
			}
			preparedStatement = connection.prepareStatement(sqlCount);
			preparedStatement.setInt(1, msgId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				int totalRecord = resultSet.getInt(1);
				int totalPage = (totalRecord-1)/pageSize+1;
				pages.setTotalPage(totalPage);
				pages.setTotalRecord(totalRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(resultSet);
		}
		return pages;
	}

}
