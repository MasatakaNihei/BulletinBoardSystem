package service;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import beans.ContributionBean;
import dao.ContributionDao;
import utils.DBUtil;

public class ContributionService {
	public static void newPost(ContributionBean contribution){
		Connection connection = DBUtil.getConnection();
		ContributionDao.insert(connection, contribution);
	}
	
	public static List<ContributionBean> getContributionList(){
		Connection connection = DBUtil.getConnection();
		List<ContributionBean> ret = ContributionDao.getContributionList(connection);
		return ret;
	}
	
	public static Set<String> getCategorySet(){
		Connection connection = DBUtil.getConnection();
		Set<String> ret = ContributionDao.getCategorySet(connection);
		return ret;
	}
	
	public static void deleteContribution(String targetContributionId){
		Connection connection = DBUtil.getConnection();
		ContributionDao.deleteContribution(connection, targetContributionId);
	}
	
	public static List<ContributionBean> sortContribution(String startDate, String endDate, String category, String isDeleted){
		Connection connection = DBUtil.getConnection();
		return ContributionDao.sortContribution(connection, startDate, endDate, category, isDeleted);
	}
}
