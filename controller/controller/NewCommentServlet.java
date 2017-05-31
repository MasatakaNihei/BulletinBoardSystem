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

import beans.CommentBean;
import beans.UserBean;
import service.CommentService;


@WebServlet("/newComment")
public class NewCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("home");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		UserBean loginUser = (UserBean)session.getAttribute("loginUser");
		CommentBean comment = new CommentBean();
		if(!request.getParameter("text").isEmpty()){
			comment.setText(request.getParameter("text"));
		}
		comment.setUserId(loginUser.getId());
		comment.setContributionId(request.getParameter("targetContributionId"));
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<CommentBean>> result = validator.validate(comment);
		if(!result.isEmpty()){
			for(ConstraintViolation<CommentBean> violation : result){
				messages.add(violation.getMessage());
			}
		}
		
		if(!messages.isEmpty()){
			session.setAttribute("errorMessages", messages);
			session.setAttribute("newComment", comment);
			response.sendRedirect("home");
			return;
		}
		
		
		CommentService.newComment(comment);
		response.sendRedirect("home");
	}

}
