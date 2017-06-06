<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored = "false" %>
<%@taglib prefix = "c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix ="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<link href="./css/style.css" rel="stylesheet" type="text/css">
	<title>ホーム</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css" >
	<script>
 		$(function() {
    		$(".datepicker").datepicker();
  		});
	</script>
	
	<script >
		$(function() {
			$(".delete").submit(function() {
				if (!confirm("削除しますか？")) {
					return false;
				}
			});
		});
	</script>
	
	<script>
		$(function() {
			var category = $(".selectCategory").attr('id');
			if(category != null && category != ''){
				$(".selectCategory").val(category);
			}	
			
			var sort = $(".sort").attr('id');
			if(sort != null && sort != ""){
				$(".sort").val(sort);
			}
		});
	</script>
	
</head>
<body>
<div class ="head">
	<div class = "siteTitle">わったい菜掲示板システム</div>
	<div class ="loginUser">ログインユーザー：<c:out value="${loginUser.name }"></c:out></div>
	<div class ="menu">
		<a href = "contribution" >新規投稿</a>
		<c:if test="${loginUser.getBranchID().equals('1') && (loginUser.getPositionId()).equals('1') }">
			<a href = "userManagement" >ユーザー管理</a>
		</c:if>
		<a href = "logout" >ログアウト</a>
	</div>
</div>

<div class ="pageTitle">
	<span class ="pageTitle">ホーム</span>
</div>

<c:if test="${not empty errorMessages }">
	<div class ="errorMessage">
		<c:forEach items = "${errorMessages}" var = "message">
			<c:out value = "${message}"/><br>
		</c:forEach>
	</div>
	<c:remove var="errorMessages" scope ="session"/>
</c:if>
<div class ="main">
	<div class="sort">
		<div class ="title">日付とカテゴリで絞込み</div>
		<form action="home" method ="get">
			<span>
				日付
				<input type ="text" class = "datepicker" name ="startDate" value ="${startDate }">
				から
				<input type ="text" class = "datepicker" name ="endDate" value ="${endDate }">
				まで
				<select name ="sort" class ="sort" id ="${sort}">
					<option value ="0" selected>投稿が新しい順</option>
					<option value ="1">投稿が古い順</option>
				</select>
			</span>
			<span>
				カテゴリー
				<select name ="category" class ="selectCategory" id ="${sessionScope.category }">
					<option value ="" selected>全てのカテゴリー</option>
					<c:forEach items ="${categorySet }" var ="category">
						<option value ="${category}"><c:out value="${category }"></c:out></option>
					</c:forEach>
				</select>
			</span>
			<br>
			<span>
				<c:if test="${viewDeleted.equals('0') }">
					<label for ="viewDeleted" class="checkBox">
						<input type ="checkbox" name ="viewDeleted" value ="1" id="viewDeleted"><span>削除された投稿を表示する</span>
					</label>
				</c:if>
				<c:if test="${viewDeleted.equals('1') }">
					<label for ="viewDeleted" class="checkBox">
						<input type ="checkbox" name ="viewDeleted" value ="1" id="viewDeleted" checked>削除された投稿を表示する
					</label>
				</c:if>
			</span>
			<br>
			<div class ="sortSubmit">
				<input type ="submit" class="bottun" value ="絞り込む">
				<input type ="reset" class="bottun" value ="リセット">
			</div>
		</form>
	</div>
	
	
	
	<c:forEach items ="${contributionList }" var ="contribution">
		<div class ="contributions">
			<c:choose>
				<c:when test="${contribution.getIsDeleted().equals('1') }">
					<div class = "deletedPost">
						この投稿は削除されました。<br>
						投稿日時：<fmt:formatDate value="${contribution.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
						削除日時：<fmt:formatDate value="${contribution.updatedAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
					</div>
				</c:when>
				<c:otherwise>
					<table>
						<tr>
							<td colspan ="2" class = "title">
								<c:out value="${contribution.title }" ></c:out><br>
							</td>
						</tr>
						<tr>
							<td colspan ="2">
								投稿者：
								<c:out value="${contribution.userName }"></c:out>
								<c:out value="${contribution.userBranchName }"></c:out>
								<c:if test="${!contribution.getUserPositionId().equals('0') }">
									<c:out value="${contribution.userPositionName}"></c:out>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan ="2" class ="category">
								カテゴリー :<c:out value="${contribution.category }"></c:out><br>
							</td>
						</tr>
						
						<tr>
							<td colspan ="2" class ="text">
								<c:forEach items ="${fn:split(contribution.text, '
								')  }" var="splitedText">
									<c:out value="${splitedText }"></c:out><br>
								</c:forEach><br>
							</td>
						</tr>
						<tr>
							<td>
								投稿日時：<fmt:formatDate value="${contribution.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/>
							</td>
							<td class ="postDeleteBottun">
								<c:if test="${contribution.getUserId().equals(loginUser.getId()) }" var ="isLoginUserPost"></c:if>
								<c:if test="${isLoginUserPost }">
									<form action="deleteContribution" method="post" class ="delete">
										<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
										<input type ="submit" class ="bottun" value ="この投稿を削除する"> <br>
									</form>
								</c:if>
								<c:if test="${ isLoginUserPost == false && contribution.getUserBranchId().equals(loginUser.getBranchID()) && loginUser.getPositionId().equals('3')}">
									<form action="deleteContribution" method="post" class ="delete">
										<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
										<input type ="submit" class ="bottun" value ="この投稿を削除する"> <br>
									</form>
								</c:if>
								<c:if test="${isLoginUserPost == false && loginUser.getBranchID().equals('1') && loginUser.getPositionId().equals('2') }">
									<form action="deleteContribution" method="post" class ="delete">
										<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
										<input type ="submit" class ="bottun" value ="この投稿を削除する"> <br>
									</form>
								</c:if>
							</td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>
			<div class ="comment">
				<div class = "newComment">
					<label>コメント新規投稿</label><br>
					<form action="newComment" method ="post">
						<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
						<c:if test="${contribution.getId().equals(newComment.getContributionId()) }">
							<c:set var = "newText" value ="${newComment.text}"></c:set>
						</c:if>
						<textarea rows="10" cols="40" name="text"><c:out value="${newText}"></c:out></textarea><br>
						<input type ="submit" class ="bottun" value ="コメントする">
						<c:remove var="newText"/>
					</form>
				</div>
				
				<div class ="commentList">
					<c:if test="${commentMap.containsKey(String.valueOf(contribution.getId())) }">
						<label for ="${contribution.id}" class ="openComment">
							コメントを表示する(<c:out value="${commentMap.get(String.valueOf(contribution.getId())).size()}"/>件)
						</label><br>
					</c:if>
					<c:if test="${!commentMap.containsKey(String.valueOf(contribution.getId())) }">
						<label class ="noComment">
							コメントはありません
						</label><br>
					</c:if>
					<input type ="checkbox" id ="${contribution.id}" class ="openComment"/>
					<div class ="commentContainer">
						<div class ="commentScroll">
							<c:forEach items ="${commentMap.get(String.valueOf(contribution.getId()))}" var ="comment">
								<c:choose>
									<c:when test="${comment.getIsDeleted().equals('1') }">
										<div class = "deletedComment">
											このコメントは削除されました。<br>
											投稿日時：<fmt:formatDate value="${comment.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
											削除日時：<fmt:formatDate value="${comment.updatedAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
										</div>
									</c:when>
									<c:otherwise>
										<table>
											<tr>
												<td>
													From: <c:out value="${comment.userName }"></c:out><br>
													<c:out value="${comment.userBranchName }"></c:out>
													<c:if test="${!comment.getUserPositionId().equals('0') }">
														<c:out value="${comment.userPositionName}"></c:out>
													</c:if>
												</td>
											</tr>
											<tr>
												<td class ="text">
													<c:forEach items ="${fn:split(comment.text, '
													')  }" var="splitedText">
														<c:out value="${splitedText }"></c:out><br>
													</c:forEach>
												</td>
											</tr>
											<tr>
												<td>
													<br>
													投稿日時：<fmt:formatDate value="${comment.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/>
												</td>
											</tr>
											<tr>
												<td class ="deleteBottun">
													<c:if test="${comment.getUserId().equals(loginUser.getId()) }" var ="IsLoginUserComment"></c:if>
													<c:if test="${IsLoginUserComment }">
														<form action="deleteComment" method="post" class ="delete">
															<input type ="hidden" name ="targetCommentId" value ="${comment.id }">
															<input type ="submit" class ="bottun" value ="このコメントを削除する"> <br>
														</form>
													</c:if>
													<c:if test="${IsLoginUserComment == false && comment.getUserBranchId().equals(loginUser.getBranchID()) && loginUser.getPositionId().equals('3')}">
														<form action="deleteComment" method="post" class ="delete">
															<input type ="hidden" name ="targetCommentId" value ="${comment.id }">
															<input type ="submit" class ="bottun" value ="このコメントを削除する"> <br>
														</form>
													</c:if>
													<c:if test="${IsLoginUserComment == false && loginUser.getBranchID().equals('1') && loginUser.getPositionId().equals('2')}">
														<form action="deleteComment" method="post" class ="delete">
															<input type ="hidden" name ="targetCommentId" value ="${comment.id }">
															<input type ="submit" class ="bottun" value ="このコメントを削除する"><br>
														</form>
													</c:if>
												</td>
											</tr>
										</table>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:forEach>
	
</div>
<c:remove var="newComment" scope ="session"/>
<c:remove var="contributionList" scope ="session"/>
<c:remove var="startDate" scope = "session"/>
<c:remove var="endDate" scope = "session"/>
<c:remove var="category" scope = "session"/>
<c:remove var="sort" scope ="session"/>
<c:remove var="viewDeleted" scope ="session"/>
</body>
</html>