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
	<title>ホーム</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1/i18n/jquery.ui.datepicker-ja.min.js"></script>
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
</head>
<body>
<div><h3>ホーム</h3></div>

<div>
メニュー
<a href = "contribution">新規投稿</a>
<c:if test="${loginUser.getBranchID().equals('1') && (loginUser.getPositionId()).equals('1') }">
	<a href = "userManagement">ユーザー管理</a>
</c:if>
<a href = "logout">ログアウト</a>
</div>

<c:if test="${not empty errorMessages }">
	<div>
		<ul>
			<c:forEach items = "${errorMessages}" var = "message">
				<c:out value = "${message}"/><br>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope ="session"/>
</c:if>

<c:if test="${empty contributionList }">
	<div>
		該当する投稿がありません。
	</div>
</c:if>
<div>
	日付とカテゴリで絞込み<br>
	<form action="home" method ="post">
		日付
		<input type ="text" class = "datepicker" name ="startDate" value ="${startDate }">
		～
		<input type ="text" class = "datepicker" name ="endDate" value ="${endDate }">
		<select name ="sort">
			<option value ="0" selected>投稿が新しい順</option>
			<option value ="1">投稿が古い順</option>
		</select>
		カテゴリー
		<select name ="category">
			<option value ="" selected>全てのカテゴリー</option>
			<c:forEach items ="${categorySet }" var ="category">
				<option value ="${category}"><c:out value="${category }"></c:out></option>
			</c:forEach>
		</select>
	<input type ="submit" value ="絞り込む">
	<input type ="reset" value ="リセット">
	</form>
</div>
<div>
<c:forEach items ="${contributionList }" var ="contribution">
	<c:choose>
		<c:when test="${contribution.getIsDeleted().equals('1') }">
			<div>
			この投稿は削除されました。<br>
			投稿日時：<fmt:formatDate value="${contribution.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
			削除日時：<fmt:formatDate value="${contribution.updatedAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<c:out value="${contribution.title }"></c:out><br>
				<c:out value="${contribution.category }"></c:out><br>
				<c:out value="${contribution.text }"></c:out><br>
				<c:out value="${contribution.userName }"></c:out>
				<c:out value="${contribution.userBranchName }"></c:out>
				<c:if test="${!contribution.getUserPositionId().equals('0') }">
					<c:out value="${contribution.userPositionName}"></c:out>
				</c:if>
				<br>
				投稿日時：<fmt:formatDate value="${contribution.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
				<c:if test="${contribution.getUserId().equals(loginUser.getId()) }" var ="isLoginUserPost"></c:if>
				<c:if test="${isLoginUserPost }">
					<form action="deleteContribution" method="post" class ="delete">
						<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
						<input type ="submit" value ="この投稿を削除する"> <br>
					</form>
				</c:if>
				<c:if test="${ isLoginUserPost == false && contribution.getUserBranchId().equals(loginUser.getBranchID()) && loginUser.getPositionId().equals('3')}">
					<form action="deleteContribution" method="post" class ="delete">
						<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
						<input type ="submit" value ="この投稿を削除する"> <br>
					</form>
				</c:if>
				<c:if test="${isLoginUserPost == false && loginUser.getBranchID().equals('1') && loginUser.getPositionId().equals('2') }">
					<form action="deleteContribution" method="post" class ="delete">
						<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
						<input type ="submit" value ="この投稿を削除する"> <br>
					</form>
				</c:if>
			</div>
		</c:otherwise>
	</c:choose>
	<div>
		<form action="newComment" method ="post">
			<input type ="hidden" name ="targetContributionId" value ="${contribution.id }">
			<c:if test="${contribution.getId().equals(newComment.getContributionId()) }">
				<c:set var = "newText" value ="${newComment.text}"></c:set>
			</c:if>
			<textarea rows="3" cols="50" name="text"><c:out value="${newText}"></c:out></textarea>
			<input type ="submit" value ="コメントする">
			<c:remove var="newText"/>
		</form>
	</div>
	<div>
		<c:forEach items ="${commentList }" var ="comment">
			<c:if test="${comment.getContributionId().equals(contribution.getId())}">
			<c:choose>
				<c:when test="${comment.getIsDeleted().equals('1') }">
					<div>
					このコメントは削除されました。<br>
					投稿日時：<fmt:formatDate value="${comment.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
					削除日時：<fmt:formatDate value="${comment.updatedAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach items ="${fn:split(comment.text, '
					')  }" var="splitedText">
						<c:out value="${splitedText }"></c:out><br>
					</c:forEach>
					
					<c:out value="${comment.userName }"></c:out>
					<c:out value="${comment.userBranchName }"></c:out>
					<c:if test="${!comment.getUserPositionId().equals('0') }">
						<c:out value="${comment.userPositionName}"></c:out>
					</c:if>
					<br>
					投稿日時：<fmt:formatDate value="${comment.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/><br>
					<c:if test="${comment.getUserId().equals(loginUser.getId()) }" var ="IsLoginUserComment"></c:if>
					<c:if test="${IsLoginUserComment }">
					<form action="deleteComment" method="post" class ="delete">
						<input type ="hidden" name ="targetCommentId" value ="${comment.id }">
						<input type ="submit" value ="このコメントを削除する"> <br>
					</form>
				</c:if>
					<c:if test="${IsLoginUserComment == false && comment.getUserBranchId().equals(loginUser.getBranchID()) && loginUser.getPositionId().equals('3')}">
						<form action="deleteComment" method="post" class ="delete">
							<input type ="hidden" name ="targetCommentId" value ="${comment.id }">
							<input type ="submit" value ="このコメントを削除する"> <br>
						</form>
					</c:if>
					<c:if test="${IsLoginUserComment == false && loginUser.getBranchID().equals('1') && loginUser.getPositionId().equals('2')}">
						<form action="deleteComment" method="post" class ="delete">
							<input type ="hidden" name ="targetCommentId" value ="${comment.id }">
							<input type ="submit" value ="このコメントを削除する"><br>
						</form>
					</c:if>
				</c:otherwise>
			</c:choose>
			</c:if>
		</c:forEach>
	</div>
</c:forEach>
</div>
<c:remove var="newComment" scope ="session"/>
<c:remove var="contributionList" scope ="session"/>
<c:remove var="startDate" scope = "session"/>
<c:remove var="endDate" scope = "session"/>
<c:remove var="category" scope = "session"/>
</body>
</html>