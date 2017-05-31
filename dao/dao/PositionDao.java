package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.PositionBean;
import utils.DBUtil;

public class PositionDao {
	
	//キー=branchID、要素=その支店に対応するPositionBeanのリスト　でMapヘ格納
	public static Map<String, List<PositionBean>> getPositionMap(Connection connection){
		Map<String, List<PositionBean>> ret = new HashMap<String, List<PositionBean>>();
		List<PositionBean> positionList = getPositionList(connection);
		boolean con = true;
		for (int i = 0; con ; i++ ){
			List<PositionBean> list = new ArrayList<PositionBean>();
			for(PositionBean position : positionList){
				
				if(position.getBranchId().equals(String.valueOf(i))){
					list.add(position);
				}
			}
			
			if(!(list.isEmpty())){
				ret.put(String.valueOf(i), list);
			}else{
				con = false;
			}
		}
		return ret;
	}
	
	public static List<PositionBean> getPositionList(Connection connection){
		List<PositionBean> ret = new ArrayList<PositionBean>();
		String sql = "SELECT * FROM positions";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
			ret = toPositionList(rs);
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(connection);
		}
		
	}
	
	private static List<PositionBean> toPositionList(ResultSet rs) throws SQLException{
		List<PositionBean> ret = new ArrayList<PositionBean>();
		while(rs.next()){
			PositionBean position = new PositionBean();
			position.setId(rs.getString("id"));
			position.setName(rs.getString("name"));
			position.setBranchId(rs.getString("branch_id"));
			position.setCreatedAt(rs.getDate("created_at"));
			position.setUpdatedAt(rs.getDate("updated_at"));
			
			ret.add(position);
		}
		return ret;
	}

}
