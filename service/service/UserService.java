package service;

import java.sql.Connection;
import java.util.Set;

import beans.UserBean;
import dao.UserDao;
import utils.CipherUtil;
import utils.DBUtil;

public class UserService {
	public static void newEntry(UserBean user){
		Connection connection = null;
		
		connection = DBUtil.getConnection();
			
		String encPassword = CipherUtil.encrypt(user.getPassword());
		user.setPassword(encPassword);
			
		UserDao.insert(connection, user);
			
	}

	public static Set<String> getIdSet(){
		Connection connection = DBUtil.getConnection();
		Set<String> ret = UserDao.getIdList(connection);
		return ret;
	}
	
	public static void userDelete(String userId){
		Connection connection = DBUtil.getConnection();
		UserDao.delete(connection, userId);
	}
	
	public static boolean isDuplicatedLoginId(String loginId){
		Connection connection = DBUtil.getConnection();
		return UserDao.isDuplicatedLoginId(connection, loginId);
	}

	public static UserBean getUserFromId(String Id){
		Connection connection = DBUtil.getConnection();
		return UserDao.getUserFromId(connection, Id);
	}
	
}
