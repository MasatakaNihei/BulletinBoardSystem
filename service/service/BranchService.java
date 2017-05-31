package service;

import java.sql.Connection;
import java.util.List;

import beans.BranchBean;
import dao.BranchDao;
import utils.DBUtil;

public class BranchService {
	public static List<BranchBean> getBranchList(){
		Connection connection = DBUtil.getConnection();
		return BranchDao.getBranchList(connection);
	}
}
