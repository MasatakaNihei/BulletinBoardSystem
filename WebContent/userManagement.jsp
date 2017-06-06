<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix ="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

	<title>ユーザー管理</title>

	<script >
	$(function() {
		$(".stop").submit(function() {
			if (!confirm("アカウントを停止しますか？")) {
				return false;
			}
		});
	});
	</script>
	
	<script >
	$(function() {
		$(".resurrection").submit(function() {
			if (!confirm("アカウントを復帰させますか？")) {
				return false;
			}
		});
	});
	</script>
	
	<script >
	$(function() {
		$(".delete").submit(function() {
			if (!confirm("アカウントを削除しますか？")) {
				return false;
			}
		});
	});
	</script>
</head>

<body>

<div class ="head">
	<div class = "siteTitle">わったい菜掲示板システム</div>
	<div class ="loginUser">ログインユーザー：<c:out value="${loginUser.name }"></c:out></div>
	<div class ="menu">
		<a href = "home" >ホーム</a>
		<a href = "logout" >ログアウト</a>
	</div>
</div>

<div class ="pageTitle">
	<span class ="pageTitle">ユーザー管理</span>
</div>


<c:if test="${not empty messages }">
	<div class ="errorMessage">
		<c:forEach items ="${messages}" var= "message">
			<c:out value="${message}"></c:out>
		</c:forEach>
		<c:remove var="messages" scope ="session"/>
	</div>
</c:if>

<div class ="newEntryLink">
	
</div>


<div class ="main">
	<div class ="usersTable">
		<a href = "newEntry">新規登録</a><br>
		<table>
			<tr>
				<th></th>
				<th></th>
				<th></th>
				<th>名前</th>
				<th>所属</th>
				<th>部署・役職</th>
				<th>アカウント作成日時</th>
				<th>アカウント更新日時</th>
			</tr>
			<c:forEach items ="${userList }" var="user">
				<tr>
					<td>
						<form action="userEdit" method ="get">
							<input type="hidden" name ="targetUserId" value ="${user.id }">
							<input type="submit" class ="bottun" value ="編集">
						</form>
					</td>
					<td>
						<c:if test="${sessionScope.loginUser.id != user.id }">
							
								<c:if test="${user.isStopped == 0}">
									<form action="userManagement" method ="post" class = "stop">
										<input type="hidden" name ="targetUserId" value="${user.id }">
										<input type ="hidden" name ="targetUserName" value ="${user.name }">
										<input type="submit" class ="bottun" name ="stop" value ="停止">
									</form>
								</c:if>
								<c:if test="${user.isStopped == 1 }">
									<form action="userManagement" method ="post" class ="resurrection">
										<input type="hidden" name ="targetUserId" value="${user.id }">
										<input type ="hidden" name ="targetUserName" value ="${user.name }">
									<input type ="submit" class ="bottun" name ="resurrection" value ="復活">
									</form>
								</c:if>
							
						</c:if>
					</td>
					<td>
						<c:if test="${sessionScope.loginUser.id != user.id }">
							<form action="userManagement" method ="post" class ="delete">
								<input type ="hidden" name ="targetUserId" value ="${user.id }">
								<input type ="hidden" name ="targetUserName" value =${user.name }>
								<input type ="submit"  class ="bottun" name ="delete" value ="削除">
							</form>
						</c:if>
					</td>
					<td><c:out value="${user.name }"></c:out></td>
					<td><c:out value="${user.branchName }"></c:out></td>
					<td>
						<c:if test="${not empty user.positionName }">
							<c:out value="${user.positionName }"></c:out>
						</c:if>
					</td>
					<td><fmt:formatDate value="${user.createdAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/></td>
					<td><fmt:formatDate value="${user.updatedAt }" pattern ="yyyy/MM/dd(E) HH時mm分"/></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<div class ="return">
	<script type="text/javascript">
	if (history.length < 2) {
		document.write("<a href='javascript:self.close()'>閉じる</a>");
	} else {
		document.write("<a href='javascript:history.back()'>戻る</a>");
	}
	</script>
</div>

</body>
</html>