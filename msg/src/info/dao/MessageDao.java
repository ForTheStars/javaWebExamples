package info.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import info.model.Message;
import info.model.MsgException;
import info.model.Pager;
import info.model.SystemContext;
import info.util.DBUtil;

public class MessageDao implements IMessageDao {
	private IUserDao userDao;
	public MessageDao() {
		userDao = DAOFactory.getUserDao();
	}
	@Override
	public void add(Message message, int userId) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			if(userDao.load(userId) == null)
				throw new MsgException("添加留言的用户不存在!");
			connection = DBUtil.getConnection();
			String sql = "insert into t_msg (title,content,post_date,user_id) value (?,?,?,?)";
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, message.getTitle());
			pStatement.setString(2, message.getContent());
			pStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
			pStatement.setInt(4, userId);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(pStatement);
		}
		
	}

	@Override
	public void update(Message msg) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "update t_msg set title=?,content=? where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, msg.getTitle());
			preparedStatement.setString(2, msg.getContent());
			preparedStatement.setInt(3, msg.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
		}
		
	}

	@Override
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "delete from t_comment where msg_id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			sql = "delete from t_msg where id = ?";
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
	public Message load(int id) {
		Message message = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_msg where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				message= new Message();
				message.setContent(resultSet.getString("content"));
				message.setId(resultSet.getInt("id"));
				message.setPostDate(new Date(resultSet.getTimestamp("post_date").getTime()));
				message.setTitle(resultSet.getString("title"));
				message.setUserId(resultSet.getInt("user_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(resultSet);
		}
		return message;
	}

	@Override
	public Pager<Message> list() {
		Pager<Message> pages = new Pager<Message>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		List<Message> datas = new ArrayList<>();
		pages.setPageOffset(pageOffset);
		pages.setPageSize(pageSize);
		pages.setDatas(datas);
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_msg order by post_date desc limit ?,?";
			String sqlCount = "select count(*) from t_msg";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, pageOffset);
			preparedStatement.setInt(2, pageSize);
			resultSet = preparedStatement.executeQuery();
			Message message = null;
			while(resultSet.next()){
				message = new Message();
				message.setContent(resultSet.getString("content"));
				message.setId(resultSet.getInt("id"));
				message.setPostDate(new Date(resultSet.getTimestamp("post_date").getTime()));
				message.setTitle(resultSet.getString("title"));
				message.setUserId(resultSet.getInt("user_id"));
				datas.add(message);
			}
			preparedStatement = connection.prepareStatement(sqlCount);
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

	@Override
	public int getCommentCount(int msgId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = 0;
		try {
			connection = DBUtil.getConnection();
			String sql = "select count(*) from t_comment where msg_id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, msgId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(resultSet);
		}	
		return count;
	}

}
