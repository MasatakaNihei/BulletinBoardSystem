package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.CommentBean;
import utils.DBUtil;

public class CommentDao {
	public static List<CommentBean> getCommentList(Connection connection, String isDeleted){
		String sql = "SELECT * FROM comments_users WHERE is_deleted = 0 OR is_deleted = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, isDeleted);
			rs = ps.executeQuery();
			
			List<CommentBean> ret = toCommentList(rs);
			
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
	
	public static List<CommentBean> toCommentList(ResultSet rs) throws SQLException{
		List<CommentBean> ret = new ArrayList<CommentBean>();
		
		while(rs.next()){
			CommentBean comment = new CommentBean();
			comment.setId(rs.getString("id"));
			comment.setText(rs.getString("text"));
			comment.setContributionId(rs.getString("contribution_id"));
			comment.setUserId(rs.getString("user_id"));
			if(comment.getUserId() == null){
				comment.setUserName("削除されたユーザー");
			}else{
			comment.setUserName(rs.getString("user_name"));
			comment.setUserBranchId(rs.getString("user_branch_id"));
			comment.setUserBranchName(rs.getString("user_branch_name"));
			comment.setUserPositionId(rs.getString("user_position_id"));
			comment.setUserPositionName(rs.getString("user_position_name"));
			}
			comment.setCreatedAt(rs.getTimestamp("created_at"));
			comment.setUpdatedAt(rs.getTimestamp("updated_at"));
			comment.setIsDeleted(rs.getString("is_deleted"));
			
			ret.add(comment);
		}
		return ret;
	}

	public static void insert(Connection connection, CommentBean comment){
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("INSERT INTO comments (");
		sql.append("text");
		sql.append(",user_id");
		sql.append(",contribution_id");
		sql.append(")VALUES(");
		sql.append("?, ?, ?)");
		
		try {
			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, comment.getText());
			ps.setString(2, comment.getUserId());
			ps.setString(3, comment.getContributionId());
			
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
	
	public static void deleteComment(Connection connection, String targetCommentId){
		String sql = "UPDATE comments SET is_deleted = 1 WHERE id = ?";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, targetCommentId);
			
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
}
