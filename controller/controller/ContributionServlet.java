package controller;

import java.io.IOException;
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

import beans.ContributionBean;
import beans.UserBean;
import service.ContributionService;


@WebServlet("/contribution")
public class ContributionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Set<String> categorySet = ContributionService.getCategorySet();
		request.setAttribute("categorySet", categorySet);
		request.getRequestDispatcher("contribution.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		UserBean loginUser = (UserBean)session.getAttribute("loginUser");
		
		ContributionBean contribution = new ContributionBean();
		if(!request.getParameter("title").isEmpty()){
			contribution.setTitle(request.getParameter("title"));
		}
		if(!request.getParameter("title").isEmpty()){
			contribution.setText(request.getParameter("text"));
		}
		if(!request.getParameter("selectCategory").isEmpty() && !request.getParameter("newCategory").isEmpty()){
			messages.add("カテゴリを選択する場合、新規作成フォームは空にしてください");
			
		}else if(!request.getParameter("selectCategory").isEmpty()){
			contribution.setCategory(request.getParameter("selectCategory"));
		}else if(!request.getParameter("newCategory").isEmpty()){
			contribution.setCategory(request.getParameter("newCategory"));
		}
		contribution.setUserId(loginUser.getId());
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ContributionBean>> result = validator.validate(contribution);
		if(!result.isEmpty()){
			for(ConstraintViolation<ContributionBean> violation : result){
				messages.add(violation.getMessage());
			}
		}
		
		if(!messages.isEmpty()){
			session.setAttribute("errorMessages", messages);
			session.setAttribute("newPost", contribution);
			response.sendRedirect("contribution");
			return;
		}
		
		ContributionService.newPost(contribution);
		
		response.sendRedirect("home");
	}

}
