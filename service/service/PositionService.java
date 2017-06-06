package service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import beans.PositionBean;
import dao.PositionDao;
import utils.DBUtil;

public class PositionService {
	public static List<PositionBean> getPositionList(){
		Connection connection = DBUtil.getConnection();
		return PositionDao.getPositionList(connection);
		
	}
	
	public static Map<String, List<PositionBean>> getPositionMap(){
		Connection connection = DBUtil.getConnection();
		return PositionDao.getPositionMap(connection);
	}


}
