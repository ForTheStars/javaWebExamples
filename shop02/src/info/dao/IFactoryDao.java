package info.dao;

public interface IFactoryDao {
	public IUserDao createUserDao();
	public IAddressDao createAddressDao();
}
