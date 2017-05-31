package service;

import java.sql.Connection;
import java.util.List;

import beans.CommentBean;
import dao.CommentDao;
import utils.DBUtil;

public class CommentService {

	public static List<CommentBean> getCommentList(){
		Connection connection = DBUtil.getConnection();
		List<CommentBean> ret = CommentDao.getCommentList(connection);
		return ret;
	}
	
	public static void newComment(CommentBean comment){
		Connection connection = DBUtil.getConnection();
		CommentDao.insert(connection, comment);
	}
	
	public static void deleteComment(String targetCommentId){
		Connection connection = DBUtil.getConnection();
		CommentDao.deleteComment(connection, targetCommentId);
	}
}
