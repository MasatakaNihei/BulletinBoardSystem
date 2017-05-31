package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import beans.BranchBean;
import beans.PositionBean;
import beans.UserBean;
import dao.UserDao;
import service.BranchService;
import service.PositionService;
import service.UserService;
import utils.CipherUtil;
import utils.DBUtil;


@WebServlet("/userEdit")
public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean loginUser = (UserBean)session.getAttribute("loginUser");
		List<String> messages = new ArrayList<String>();
		if(!(loginUser.getBranchID().equals("1") && loginUser.getPositionId().equals("1"))){
			messages.add("アクセス権限がありません");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("home");
			return;
		}
		
		if(request.getParameter("targetUserId") == null && session.getAttribute("targetUser") == null){
			response.sendRedirect("userManagement");
			return;
		}
		UserBean targetUser = UserService.getUserFromId(request.getParameter("targetUserId"));
		if(targetUser == null){
			messages.add("不正なアクセスです。");
			session.setAttribute("errorMessages", messages);
			response.sendRedirect("home");
			return;
		}
		
		
		
		if(session.getAttribute("targetUser") == null){
			session.setAttribute("targetUser", targetUser);
		}
		
		List<BranchBean> branchList = BranchService.getBranchList();
		request.setAttribute("branchList", branchList);
		
		List<PositionBean> positionList = PositionService.getPositionList();
		request.setAttribute("positionList", positionList);
		
		request.getRequestDispatcher("userEdit.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();
		Connection connection = DBUtil.getConnection();
		UserBean user = UserDao.getUserFromId(connection, request.getParameter("id"));
		String oldUserName = user.getName();
		String newUserName = request.getParameter("name");
		String newLoginId = request.getParameter("loginId");
		user.setName(request.getParameter("name"));
		
		if(!user.getLoginId().equals(newLoginId) && UserService.isDuplicatedLoginId(newLoginId)){
			messages.add("ログインIDが既に使用されています");
		}
		
		user.setLoginId(request.getParameter("loginId"));
		
		String password = request.getParameter("password");
		if(!password.equals(request.getParameter("confPassword"))){
			messages.add("パスワードと確認用パスワードが一致しません");
			session.setAttribute("errorMessages", messages);
			session.setAttribute("targetUser", user);
			response.sendRedirect("userEdit");
			return;
		}else if(!password.isEmpty()){
			user.setPassword(password);
		}
		
		if(!request.getParameter("branchId").isEmpty()){
			user.setBranchID(request.getParameter("branchId"));
		}
		if(!request.getParameter("positionId").isEmpty()){
			user.setPositionId(request.getParameter("positionId"));
		}
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		if(!password.isEmpty()){
			Set<ConstraintViolation<UserBean>> result = validator.validate(user);
			if(!result.isEmpty()){
				for(ConstraintViolation<UserBean> violation : result){
					messages.add(violation.getMessage());
				}
			}
		}else{
			Set<ConstraintViolation<UserBean>> result = validator.validateProperty(user, "loginId");
			if(!result.isEmpty()){
				for(ConstraintViolation<UserBean> violation : result){
					messages.add(violation.getMessage());
				}
			}
			result =validator.validateProperty(user, "name");
			if(!result.isEmpty()){
				for(ConstraintViolation<UserBean> violation : result){
					messages.add(violation.getMessage());
				}
			}
		}
		
		
		if(!messages.isEmpty()){
			session.setAttribute("errorMessages", messages);
			session.setAttribute("targetUser", user);
			response.sendRedirect("userEdit");
			return;
		}
		
		String encPassword = CipherUtil.encrypt(request.getParameter("password"));
		user.setPassword(encPassword);
			
		connection = DBUtil.getConnection();
		UserDao.update(connection, user);
		if(oldUserName.equals(newUserName)){
			messages.add(newUserName + "の編集が完了しました");
		}else{
			messages.add(newUserName +"(旧ユーザー名：" + oldUserName + ")の編集が完了しました");
		}
			
		session.setAttribute("messages", messages);
		response.sendRedirect("userManagement");
		
	}

}
