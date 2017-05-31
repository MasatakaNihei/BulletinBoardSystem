package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import beans.UserBean;
import utils.DBUtil;

public class UserDao {
	
	public static void insert(Connection connection, UserBean user){
		
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users (");
			sql.append("login_id");
			sql.append(",password");
			sql.append(",name");
			sql.append(",branch_id");
			sql.append(",position_id");
			sql.append(")VALUES(");
			sql.append("?");   //1 loginId
			sql.append(", ?"); //2 password
			sql.append(", ?"); //3 name
			sql.append(", ?"); //4 branch_id
			sql.append(", ?"); //5 position_id
			sql.append(")");
			
			ps = connection.prepareStatement(sql.toString());
			
			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranchID());
			ps.setString(5, user.getPositionId());
			
			ps.executeUpdate();
			
			DBUtil.commit(connection);
		}catch(SQLException e){
			DBUtil.rollback(connection);
			System.out.println(e);
		}finally{
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	public static  UserBean getUser(Connection connection, String loginId, String password){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT * FROM users_branches_positions WHERE login_id = ? AND password = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			
			DBUtil.commit(connection);
			
			List<UserBean> userList = toUserList(rs);
			if(userList.isEmpty()){
				return null;
			}else if (userList.size() >= 2){
				throw new RuntimeException("userList.size() >= 2");
			}else{
				return userList.get(0);
			}
			
		}catch(SQLException e){
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}

	public static  UserBean getUserFromId(Connection connection, String Id){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT * FROM users_branches_positions WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, Id);
			
			rs = ps.executeQuery();
			
			DBUtil.commit(connection);
			
			List<UserBean> userList = toUserList(rs);
			if(userList.isEmpty()){
				return null;
			}else if (userList.size() >= 2){
				throw new RuntimeException("userList.size() >= 2");
			}else{
				return userList.get(0);
			}
			
		}catch(SQLException e){
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}
	
	public static void userStop(Connection connection, String id){
		String sql = "UPDATE users SET is_stopped = 1 WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps =connection.prepareStatement(sql);
			ps.setString(1, id);
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

	public static void userResurrectin(Connection connection, String id){
		String sql = "UPDATE users SET is_stopped = 0 WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps =connection.prepareStatement(sql);
			ps.setString(1, id);
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
	
	public static void update(Connection connection, UserBean user){
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE  users SET ");
			sql.append("login_id = ? ");
			sql.append(",password = ? ");
			sql.append(",name = ? ");
			sql.append(",branch_id = ? ");
			sql.append(",position_id = ?");
			sql.append("WHERE id = ?");
			
			ps = connection.prepareStatement(sql.toString());
			
			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getBranchID());
			ps.setString(5, user.getPositionId());
			ps.setString(6, user.getId());
		
			ps.executeUpdate();
			
			DBUtil.commit(connection);
		}catch(SQLException e){
			DBUtil.rollback(connection);
			System.out.println(e);
		}finally{
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}
	
	private static List<UserBean> toUserList(ResultSet rs) throws SQLException{
		List<UserBean> ret = new ArrayList<UserBean>();
		
		while(rs.next()){
			UserBean user = new UserBean();
			user.setId(rs.getString("id"));
			user.setLoginId(rs.getString("login_id"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("name"));
			user.setBranchID(rs.getString("branch_id"));
			user.setBranchName(rs.getString("branch_name"));
			user.setPositionId(rs.getString("position_id"));
			user.setPositionName(rs.getString("position_name"));
			user.setIsStopped(rs.getString("is_stopped"));
			user.setCreatedAt(rs.getTimestamp("created_at"));
			user.setUpdatedAt(rs.getTimestamp("updated_at"));
				
			ret.add(user);
		}
			
			return ret;
	}

	public static List<UserBean> getUserList(Connection connection){
		String sql = "SELECT * FROM users_branches_positions";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
		
			List<UserBean> userList = toUserList(rs);
			return userList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(connection);
		}
		
	}

	public static Set<String> getIdList(Connection connection){
		String sql = "SELECT id FROM users";
		ResultSet rs = null;
		try {
			rs = connection.createStatement().executeQuery(sql);
			Set<String> ret = new HashSet<String>();
			while(rs.next()){
				ret.add(rs.getString("id"));
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
	
	public static void delete(Connection connection, String userId){
		String sql = "DELETE FROM users WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, userId);
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
	 
	public static boolean isDuplicatedLoginId(Connection connection, String loginId){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			String sql = "SELECT * FROM users_branches_positions WHERE login_id = ?";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			
			rs = ps.executeQuery();
			
			boolean ret = rs.next();
		
			DBUtil.commit(connection);
			return ret;
			
		}catch(SQLException e){
			DBUtil.rollback(connection);
			throw new RuntimeException(e);
		}finally{
			DBUtil.close(rs);
			DBUtil.close(ps);
			DBUtil.close(connection);
		}
	}
	 
}
