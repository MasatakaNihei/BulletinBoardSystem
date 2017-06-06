package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();
		if(session.getAttribute("errorMessages") != null){
			messages = (List<String>) session.getAttribute("errorMessages");
		}
		
		
		String startDate = request.getParameter("startDate");
		if(startDate == null){
			startDate ="";
		}
		String endDate = request.getParameter("endDate");
		if(endDate == null){
			endDate = "";
		}
		
		if(!startDate.isEmpty() && !startDate.matches("\\d{4}/\\d{2}/\\d{2}")){
			messages.add("絞込み開始日付の指定が不正です。");
			startDate = "";
		}
		
		if(!endDate.isEmpty() && !endDate.matches("\\d{4}/\\d{2}/\\d{2}")){
			messages.add("絞込み終了日付の指定が不正です");
			endDate = "";
		}
		
		if(!startDate.isEmpty() && !endDate.isEmpty()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			try {
				Date start = sdf.parse(startDate);
				Date end = sdf.parse(endDate);
				if(!(start.before(end) || start.equals(end))){
					messages.add("絞り込み開始日を絞り込み終了日より後には設定できません");
				}
				
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			
		}
		
		String category = request.getParameter("category");
		if(category == null){
			category = "";
		}
		
		String isDeleted = request.getParameter("viewDeleted");
		if(isDeleted == null || isDeleted.isEmpty()){
			isDeleted = "0";
		}
		List<ContributionBean> contributionList = null;
		if((startDate.isEmpty() && endDate.isEmpty() && category.isEmpty() && isDeleted.equals("0")) || !messages.isEmpty()){
			contributionList = ContributionService.getContributionList();
			if(contributionList.isEmpty()){
				messages.add("投稿がありません。");
			}
		}else{
			contributionList = ContributionService.sortContribution(startDate, endDate, category, isDeleted);
			if(contributionList.isEmpty()){
				messages.add("該当する投稿がありません。");
			}
		}
		
		String sort = request.getParameter("sort");
		if(sort == null){
			sort = "0";
		}
		if(sort.equals("0")){
			contributionList.sort((a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
		}else{
			contributionList.sort((a,b) -> a.getCreatedAt().compareTo(b.getCreatedAt()));
		}
		
		session.setAttribute("contributionList", contributionList);
		session.setAttribute("startDate", startDate);
		session.setAttribute("endDate", endDate);
		session.setAttribute("category", category);
		session.setAttribute("sort", sort);
		session.setAttribute("errorMessages", messages);
		session.setAttribute("viewDeleted" ,isDeleted);
		List<CommentBean> AllCommentList = CommentService.getCommentList(isDeleted);
		
		Map<String,List<CommentBean>> commentMap = new HashMap<String, List<CommentBean>>();
		
		for(CommentBean comment :AllCommentList){
			if(!commentMap.containsKey(comment.getContributionId())){
				commentMap.put(String.valueOf(comment.getContributionId()), new ArrayList<CommentBean>());
			}
			commentMap.get(comment.getContributionId()).add(comment);
		}
		
		
		Set<String> categorySet = ContributionService.getCategorySet();
		
		request.setAttribute("commentMap", commentMap);
		request.setAttribute("categorySet", categorySet);
		
		request.getRequestDispatcher("home.jsp").forward(request, response);
	}
	
}
