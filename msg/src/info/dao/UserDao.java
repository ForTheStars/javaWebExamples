package info.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import info.model.MsgException;
import info.model.Pager;
import info.model.SystemContext;
import info.model.User;
import info.util.DBUtil;

public class UserDao implements IUserDao {

	@Override
	public void add(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select count(*) from t_user where username=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				if(resultSet.getInt(1) > 0)
					throw new MsgException("添加的用户已经存在！不能继续添加");
			}
			sql = "insert into t_user(username,password,nickname,type,status) value (?,?,?,?,?)";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getNickname());
			preparedStatement.setInt(4, user.getType());
			preparedStatement.setInt(5, user.getStatus());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(resultSet);
		}
	}

	@Override
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBUtil.getConnection();
			User user = this.load(id);
			if(user.getUsername().equals("admin"))
				throw new MsgException("超级管理员不能被删除");
			String sql = "delete from t_user where id = ?";
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
	public void update(User user) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "update t_user set username=?,password=?,nickname=?,type=?,status=? where id=?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getNickname());
			preparedStatement.setInt(4, user.getType());
			preparedStatement.setInt(5, user.getStatus());
			preparedStatement.setInt(6, user.getId());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
		}
		
	}

	@Override
	public User load(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_user where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setNickname(resultSet.getString("nickname"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setStatus(resultSet.getInt("status"));
				user.setType(resultSet.getInt("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Pager<User> list(String condition) {
		int pageOffset = SystemContext.getPageOffset();
		int pageSize = SystemContext.getPageSize();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Pager<User> pages = new Pager<>();
		List<User> users = new ArrayList<>();
		User user = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_user";
			String sqlCount = "select count(*) from t_user";
			if(condition != null || !"".equals(condition)){
				sql += " where username like '%"+condition+"%' or nickname like '%"+condition+"%'";
				sqlCount += " where username like '%"+condition+"%' or nickname like '%"+condition+"%'";
			}
			// 获取当前页面 用户信息
			sql+=" limit ?,?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, pageOffset);
			preparedStatement.setInt(2, pageSize);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setNickname(resultSet.getString("nickname"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setStatus(resultSet.getInt("status"));
				user.setType(resultSet.getInt("type"));
				users.add(user);
			}
			// 获取总共的用户数目
			preparedStatement = connection.prepareStatement(sqlCount);
			resultSet = preparedStatement.executeQuery();
			int totalRecord = 0;
			while(resultSet.next()){
				totalRecord = resultSet.getInt(1);
			}
			int totalPage = (totalRecord-1)/pageSize + 1;  //计算该分几页显示
			pages.setPageOffset(pageOffset);
			pages.setPageSize(pageSize);
			pages.setTotalPage(totalPage);
			pages.setTotalRecord(totalRecord);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(resultSet);
		}
		pages.setDatas(users);
		return pages;
	}

	@Override
	public User login(String username, String password) {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet = null;
		User user = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_user where username = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setNickname(resultSet.getString("nickname"));
				user.setUsername(resultSet.getString("username"));
				user.setPassword(resultSet.getString("password"));
				user.setStatus(resultSet.getInt("status"));
				user.setType(resultSet.getInt("type"));
			}
			if(user == null)
				throw new MsgException("用户名不存在");
			if(!user.getPassword().equals(password))
				throw new MsgException("用户密码不正确");
			if(user.getStatus() == 0)
				throw new MsgException("用户处于停用状态不能登入");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(connection);
			DBUtil.close(preparedStatement);
			DBUtil.close(resultSet);
		}
		return user;
	}

}
