package info.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import info.model.Address;
import info.model.Pager;
import info.model.User;
import info.util.DBUtil;

public class UserJDBCDao implements IUserDao {
	
	private User loadOnceUser(int id){
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		List<Address> addresses = new ArrayList<>();
		Address address = null;
		User user = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select *,t2.id as 'a_id' " +
					"from t_user t1 left join t_address t2 on(t1.id=t2.user_id) where t1.id=?";
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, id);
			resultSet = pStatement.executeQuery();
			while(resultSet.next()){
				if(user == null){
					user = new User();
					user.setId(resultSet.getInt("user_id"));
					user.setNickname(resultSet.getString("nickname"));
					user.setPassword(resultSet.getString("password"));
					user.setType(resultSet.getInt("type"));
					user.setUsername(resultSet.getString("username"));
				}
				address = new Address();
				address.setId(resultSet.getInt("a_id"));
				address.setName(resultSet.getString("name"));
				address.setPhone(resultSet.getString("phone"));
				address.setPostcode(resultSet.getString("postcode"));
				addresses.add(address);
			}
			user.setAddresses(addresses);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(resultSet);
			DBUtil.close(pStatement);
			DBUtil.close(connection);
		}
		return user;
	}
	
	private User loadSecondUser(int id){
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		List<Address> addresses = new ArrayList<>();
		Address address = null;
		User user = null;
		try {
			connection = DBUtil.getConnection();
			String sql = "select * from t_user where id=?";
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, id);
			resultSet = pStatement.executeQuery();
			while(resultSet.next()){
				user = new User();
				user.setId(resultSet.getInt("user_id"));
				user.setNickname(resultSet.getString("nickname"));
				user.setPassword(resultSet.getString("password"));
				user.setType(resultSet.getInt("type"));
				user.setUsername(resultSet.getString("username"));
			}
			sql = "select * from t_address where user_id=?";
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, id);
			resultSet = pStatement.executeQuery();
			while(resultSet.next()){
				address = new Address();
				address.setId(resultSet.getInt("a_id"));
				address.setName(resultSet.getString("name"));
				address.setPhone(resultSet.getString("phone"));
				address.setPostcode(resultSet.getString("postcode"));
				addresses.add(address);
			}
			user.setAddresses(addresses);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			DBUtil.close(resultSet);
			DBUtil.close(pStatement);
			DBUtil.close(connection);
		}
		return user;
	}
	@Override
	public void add(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delect(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User loadByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pager<User> find(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
