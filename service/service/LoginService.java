package service;

import java.sql.Connection;

import beans.UserBean;
import dao.UserDao;
import utils.CipherUtil;
import utils.DBUtil;

public class LoginService {
	public static UserBean login(String loginId, String password){
		Connection connection = null;
		try{
			connection = DBUtil.getConnection();
			
			String encPassword = CipherUtil.encrypt(password);
			UserBean user = UserDao.getUser(connection, loginId, encPassword);
			
			return user;
					
		}catch(RuntimeException e){
			DBUtil.rollback(connection);
			throw e;
		}catch (Error e){
			DBUtil.rollback(connection);
			throw e;
		}finally{
			DBUtil.close(connection);
		}
	}

}
