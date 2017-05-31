package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBean;
import dao.UserDao;
import service.UserService;
import utils.DBUtil;


@WebServlet(urlPatterns ="/userManagement")
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserBean loginUser = (UserBean)session.getAttribute("loginUser");
		if(!(loginUser.getBranchID().equals("1") && loginUser.getPositionId().equals("1"))){
			List<String> messages = new ArrayList<String>();
			messages.add("アクセス権限がありません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("home");
			return;
		}
		
		Connection connection = DBUtil.getConnection();
		List<UserBean> userList = UserDao.getUserList(connection);
		
		request.setAttribute("userList", userList);
		
		request.getRequestDispatcher("userManagement.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetUserId = request.getParameter("targetUserId");
		String targetUserName =request.getParameter("targetUserName");
		Connection connection = DBUtil.getConnection();
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		
		if(request.getParameter("stop") != null){
			UserDao.userStop(connection, targetUserId);
			messages.add(targetUserName +"を停止しました。");
			session.setAttribute("messages", messages);
			response.sendRedirect("userManagement");
			return;
		}
		
		if(request.getParameter("resurrection") != null){
			UserDao.userResurrectin(connection, targetUserId);
			messages.add(targetUserName + "を復活しました。");
			session.setAttribute("messages", messages);
			response.sendRedirect("userManagement");
			return;
		}
		
		if(request.getParameter("delete") != null){
			UserService.userDelete(targetUserId);
			messages.add(targetUserName + "を削除しました。");
			session.setAttribute("messages", messages);
			response.sendRedirect("userManagement");
		}
	}

}
