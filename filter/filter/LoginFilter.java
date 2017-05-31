package filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserBean;
import dao.UserDao;
import utils.DBUtil;


@WebFilter("/*")
public class LoginFilter implements Filter {

    
	public void destroy() {
	
	}

	private static boolean isFilterTarget(HttpServletRequest req){
		String requestURL = req.getRequestURL().toString();
		String[] URLset = {"/login", "/css", "/logout", "/js" }; 
		for(String URL : URLset){
			if(requestURL.contains(URL)){
				return false;
			}
		}
		return true;
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		List<String> messages = new ArrayList<String>();
		
		
		if(isFilterTarget(req)){
			if(session.getAttribute("loginUser") == null){
				messages.add("ログインしていません。");
				session.setAttribute("errorMessages", messages);
				
				res.sendRedirect("login");
				return;
			}
			
		}
		
		if(isFilterTarget(req)){
			Connection connection = DBUtil.getConnection();
			UserBean loginUser = (UserBean)session.getAttribute("loginUser");
			String loginUserId = loginUser.getId();
			UserBean nowUser = UserDao.getUserFromId(connection, loginUserId);
		
			if(loginUser.getUpdatedAt() != nowUser.getUpdatedAt()){
				session.removeAttribute("loginUser");
				session.setAttribute("loginUser", nowUser);
			}
		}

		if(session.getAttribute("loginUser") != null){
			UserBean loginUser = (UserBean)session.getAttribute("loginUser");
			if(loginUser.getIsStopped().equals("1")){
				messages.add("アカウントが停止されています。");
				session.setAttribute("errorMessages", messages);
				session.removeAttribute("loginUser");
				res.sendRedirect("login");
				return;
			}
		}
		chain.doFilter(req, res);
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
