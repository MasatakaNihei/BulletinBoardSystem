package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import dao.BranchDao;
import dao.PositionDao;
import service.UserService;
import utils.DBUtil;

@WebServlet("/newEntry")
public class NewEntryServlet extends HttpServlet {
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
		List<BranchBean> branchList = BranchDao.getBranchList(connection);
		request.setAttribute("branchList", branchList);
		
		connection =DBUtil.getConnection();
		Map<String, List<PositionBean>> positionMap = PositionDao.getPositionMap(connection);		
		request.setAttribute("positionMap", positionMap);
		
		request.getRequestDispatcher("newEntry.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		
		
		
		UserBean user = new UserBean();
		if(!request.getParameter("loginId").isEmpty()){
			user.setLoginId(request.getParameter("loginId"));
		}
		if(!request.getParameter("password").isEmpty()){
			user.setPassword(request.getParameter("password"));
		}
		if(!request.getParameter("name").isEmpty()){
			user.setName(request.getParameter("name"));
		}
		user.setBranchID(request.getParameter("branchId"));
		user.setPositionId(request.getParameter("positionId"));
		
		if(!(request.getParameter("password").equals(request.getParameter("confPassword")))){
			messages.add("パスワードと確認用パスワードが一致しません");
			
		}	
		
		Validator validator  = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<UserBean>> result = validator.validate(user);
		if(!result.isEmpty()){
			for(ConstraintViolation<UserBean> violation : result){
				messages.add(violation.getMessage());
			}
		}
		
		if(UserService.isDuplicatedLoginId(user.getLoginId())){
			messages.add("ログインIDが既に使用されています");
		}
		
		if(user.getPositionId() != null){
			if(user.getBranchID().equals("1") && user.getPositionId().equals("3")){
				messages.add("支店と部署・役職の組み合わせが不正です。");
			}
			
			if(!user.getBranchID().equals("1") && !(user.getPositionId().equals("0") || user.getPositionId().equals("3"))){
				messages.add("支店と部署・役職の組み合わせが不正です。");
			}
		}
		
		if(!messages.isEmpty()){
			session.setAttribute("errorMessages", messages);
			session.setAttribute("newUser", user);
			response.sendRedirect("newEntry");
			return;
		}
	
		UserService.newEntry(user);
		messages.add(user.getName() + "のアカウントを作成しました。");
		session.setAttribute("messages", messages);
		response.sendRedirect("userManagement");
		
	}

}
