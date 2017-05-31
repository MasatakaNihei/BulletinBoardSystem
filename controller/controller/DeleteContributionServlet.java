package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.ContributionService;


@WebServlet("/deleteContribution")
public class DeleteContributionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("home");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetContributionId = request.getParameter("targetContributionId");
		ContributionService.deleteContribution(targetContributionId);
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		messages.add("投稿を削除しました。");
		session.setAttribute("errorMessages", messages);
		response.sendRedirect("home");
	}

}
