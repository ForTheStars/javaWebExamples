package info.dao;
/*
 * 简单工厂，违背开闭原则
 */
public class DAOFactory {

	public static IAddressDao getAddressDao() {
		return new AddressDao();
	}

	public static IUserDao getUserDao() {
		return new UserDao();
	}

}
