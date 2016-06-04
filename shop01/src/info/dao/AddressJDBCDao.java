package info.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.PSource;

import info.model.Address;
import info.model.User;
import info.util.DBUtil;

public class AddressJDBCDao implements IAddressDao {

	@Override
	public void add(Address address, int userId) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			String sql = "insert into t_address(name,phone,postcode,user_id) value (?,?,?,?)";
			connection = DBUtil.getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setString(1, address.getName());
			pStatement.setString(2, address.getPhone());
			pStatement.setString(3, address.getPostcode());
			pStatement.setInt(4, userId);
			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pStatement);
			DBUtil.close(connection);
		}
		
	}

	@Override
	public void update(Address address) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Address load(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Address> list(int userId) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		List<Address> addresses = new ArrayList<>();
		Address address = null;
		User user = null;
		try {
			String sql = "select t1.id as 'a_id',t1.name as 'a_name',t1.phone as 'phone'," +
					" t1.postcode,t2.id as 'user_id',t2.nickname,t2.password,t2.username," +
					"t2.type as 'user_type' from t_address t1 left join t_user t2 on(t1.user_id=t2.id) where user_id=?";
			connection = DBUtil.getConnection();
			pStatement = connection.prepareStatement(sql);
			pStatement.setInt(1, userId);
			resultSet = pStatement.executeQuery();
			while(resultSet.next()){
				address = new Address();
				address.setId(resultSet.getInt("a_id"));
				address.setName(resultSet.getString("a_name"));
				address.setPhone(resultSet.getString("phone"));
				address.setPostcode(resultSet.getString("postcode"));
				user = new User();
				user.setId(resultSet.getInt("user_id"));
				user.setNickname(resultSet.getString("nickname"));
				user.setPassword(resultSet.getString("password"));
				user.setType(resultSet.getInt("user_type"));
				user.setUsername(resultSet.getString("username"));
				
				address.setUser(user);
				addresses.add(address);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
