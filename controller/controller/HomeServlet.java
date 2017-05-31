package controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.CommentBean;
import beans.ContributionBean;
import service.CommentService;
import service.ContributionService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("contributionList") == null){
			List<ContributionBean> contributionList = ContributionService.getContributionList();
			contributionList.sort((a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
			request.setAttribute("contributionList", contributionList);
		}
		
		List<CommentBean> commentList = CommentService.getCommentList();
		Set<String> categorySet = ContributionService.getCategorySet();
		
		request.setAttribute("commentList", commentList);
		request.setAttribute("categorySet", categorySet);
		
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String category = request.getParameter("category");
		List<ContributionBean> contributionList = null;
		if(startDate.isEmpty() && endDate.isEmpty() && category.isEmpty()){
			contributionList = ContributionService.getContributionList();
		}else{
			contributionList = ContributionService.sortContribution(startDate, endDate, category);
		}
		
		if(request.getParameter("sort").equals("0")){
			contributionList.sort((a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
		}else{
			contributionList.sort((a,b) -> a.getCreatedAt().compareTo(b.getCreatedAt()));
		}
		HttpSession session = request.getSession();
		session.setAttribute("contributionList", contributionList);
		session.setAttribute("startDate", startDate);
		session.setAttribute("endDate", endDate);
		session.setAttribute("category", category);
		response.sendRedirect("home");
	}

}
