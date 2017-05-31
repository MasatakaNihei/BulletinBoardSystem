package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import beans.ContributionBean;
import utils.DBUtil;

public class ContributionDao {
	public static void insert(Connection connection, ContributionBean contribution){
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO contributions (");
		sql.append("title");
		sql.append(",text");
		sql.append(",category");
		sql.append(",user_id");
		sql.append(")VALUES(");
		sql.append("?, ?, ?, ?)");
		
		try {
			ps= connection.prepareStatement(sql.toString());
			ps.setString(1, contribution.getTitle());
			ps.setString(2, contribution.getText());
			ps.setString(3, contribution.getCategory());
			ps.setString(4, contribution.getUserId());
			
			ps.executeUpdate();
			DBUtil.commit(connection);
		} catch (SQLException e) {
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		
	}
	
	public static List<ContributionBean> getContributionList(Connection connection){
		String sql = "SELECT * FROM contributions_users";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
			
			List<ContributionBean> ret = toContributionList(rs);
			
			DBUtil.commit(connection);
			return ret;
		} catch (SQLException e) {
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(connection);
		}
				
	}
	
	public static List<ContributionBean> toContributionList(ResultSet rs) throws SQLException{
		List<ContributionBean> ret = new ArrayList<ContributionBean>();
		while(rs.next()){
			ContributionBean con = new ContributionBean();
			con.setId(rs.getString("id"));
			con.setTitle(rs.getString("title"));
			con.setText(rs.getString("text"));
			con.setCategory(rs.getString("category"));
			con.setUserId(rs.getString("user_id"));
			if(con.getUserId() == null){
				con.setUserName("削除されたユーザー");
			}else{
				con.setUserName(rs.getString("user_name"));
				con.setUserBranchId(rs.getString("user_branch_id"));
				con.setUserBranchName(rs.getString("user_branch_name"));
				con.setUserPositionId(rs.getString("user_position_id"));
				con.setUserPositionName(rs.getString("user_position_name"));
			}
			con.setCreatedAt(rs.getTimestamp("created_at"));
			con.setUpdatedAt(rs.getTimestamp("updated_at"));
			con.setIsDeleted(rs.getString("is_deleted"));
			ret.add(con);
		}
		
		return ret;
		
	}

	public static Set<String> getCategorySet(Connection connection){
		String sql = "SELECT category FROM contributions_users";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
			Set<String> ret =  new HashSet<String>();
			while(rs.next()){
				ret.add(rs.getString("category"));
			}
			DBUtil.commit(connection);
			return ret;
		} catch (SQLException e) {
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(connection);
		}
		
	}

	public static void deleteContribution(Connection connection, String targetContributionId){
		String sql = " UPDATE contributions SET is_deleted = 1 WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, targetContributionId);
			ps.executeUpdate();
			
			DBUtil.commit(connection);
		} catch (SQLException e) {
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		
				
		
	}
	
	public static List<ContributionBean> sortContribution(Connection connection, String startDate, String endDate, String category){
		StringBuilder sql = new StringBuilder();
		int index = 1;
		int endDateIndex = 0;
		int categoryIndex = 0;
		
		sql.append("SELECT * FROM contributions_users WHERE ");
	
		if(!startDate.isEmpty()){
			sql.append("DATE_FORMAT(created_at, '%Y/%m/%d') >= ? ");
			index++;
		}
		if(!endDate.isEmpty()){
			if(!startDate.isEmpty()){
				sql.append("AND ");
			}
			sql.append("DATE_FORMAT(created_at, '%Y/%m/%d') <= ? ");
			endDateIndex = index;
			index++;
		}
		if(!category.isEmpty()){
			if(!startDate.isEmpty() || !endDate.isEmpty()){
				sql.append("AND ");
			}
			sql.append("category = ?");
			categoryIndex = index;
		}
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql.toString());
			if(!startDate.isEmpty()){
				ps.setString(1, startDate);
			}
			if(!endDate.isEmpty()){
				ps.setString(endDateIndex, endDate);
			}
			if(!category.isEmpty()){
				ps.setString(categoryIndex, category);
			}
			
			rs = ps.executeQuery();
			List<ContributionBean> ret =toContributionList(rs);
			
			DBUtil.commit(connection);
			return ret;
		} catch (SQLException e) {
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
		
	}
}
