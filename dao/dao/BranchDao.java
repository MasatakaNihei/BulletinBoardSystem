package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BranchBean;
import utils.DBUtil;

public class BranchDao {
	public static List<BranchBean> getBranchList(Connection connection){
		List<BranchBean> ret = new ArrayList<BranchBean>(); 
		String sql = "SELECT * FROM branches";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
			
			ret = toBranchList(rs);
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(connection);
		}
		
		
	}
	
	private static List<BranchBean> toBranchList(ResultSet rs) throws SQLException{
		List<BranchBean> ret = new ArrayList<BranchBean>(); 
		
		while(rs.next()){
			BranchBean branch = new BranchBean();
			branch.setId(rs.getString("id"));
			branch.setName(rs.getString("name"));
			branch.setCreatedAt(rs.getDate("created_at"));
			branch.setUpdatedAt(rs.getDate("updated_at"));
			
			ret.add(branch);
		}
		return ret;	
		
	}

	
}
