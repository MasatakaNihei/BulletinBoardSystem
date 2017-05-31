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

import beans.UserBean;
import service.LoginService;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
   @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}
   
   @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String loginId = request.getParameter("loginId");
	   String password = request.getParameter("password");
	   HttpSession session = request.getSession();
	   if(loginId.equals("0")){
		   List<String> messages = new ArrayList<String>();
		   messages.add("ログインに失敗しました");
		   session.setAttribute("errorMessages", messages);
		   response.sendRedirect("login");
		   return;
	   }
	   UserBean user = LoginService.login(loginId, password);
	  
	   if(user == null){
		   List<String> messages = new ArrayList<String>();
		   messages.add("ログインに失敗しました");
		   session.setAttribute("errorMessages", messages);
		   response.sendRedirect("login");
		   return;
	   }else if(user.getIsStopped().equals("1")){
		   List<String> messages = new ArrayList<String>();
		   messages.add("アカウントが停止されています");
		   session.setAttribute("errorMessages", messages);
		   response.sendRedirect("login");
		   return;
	   }else{
		   session.setAttribute("loginUser", user);
		   response.sendRedirect("home");
	   }
	}

}
