<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<div><h3>ユーザー管理</h3></div>

<div><a href ="home">ホーム</a></div>

<c:if test="${not empty messages }">
	<div>
	<c:forEach items ="${messages}" var= "message">
		<c:out value="${message}"></c:out>
	</c:forEach>
	<c:remove var="messages" scope ="session"/>
	</div>
</c:if>

<div>
	<a href = "newEntry">新規登録</a>
</div>


<div>
	<table>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>名前</td>
			<td>所属</td>
			<td>部署・役職</td>
			<td>アカウント作成日時</td>
			<td>アカウント更新日時</td>
		</tr>
		<c:forEach items ="${userList }" var="user">
			<tr>
				<td>
					<form action="userEdit" method ="get">
						<input type="hidden" name ="targetUserId" value ="${user.id }">
						<input type="submit" value ="編集">
					</form>
				</td>
				<td>
					<c:if test="${sessionScope.loginUser.id != user.id }">
						
							<c:if test="${user.isStopped == 0}">
								<form action="userManagement" method ="post" class = "stop">
									<input type="hidden" name ="targetUserId" value="${user.id }">
									<input type ="hidden" name ="targetUserName" value ="${user.name }">
									<input type="submit" name ="stop" value ="停止">
								</form>
							</c:if>
							<c:if test="${user.isStopped == 1 }">
								<form action="userManagement" method ="post" class ="resurrection">
									<input type="hidden" name ="targetUserId" value="${user.id }">
									<input type ="hidden" name ="targetUserName" value ="${user.name }">
								<input type ="submit" name ="resurrection" value ="復活">
								</form>
							</c:if>
						
					</c:if>
				</td>
				<td>
					<c:if test="${sessionScope.loginUser.id != user.id }">
						<form action="userManagement" method ="post" class ="delete">
							<input type ="hidden" name ="targetUserId" value ="${user.id }">
							<input type ="hidden" name ="targetUserName" value =${user.name }>
							<input type ="submit"  name ="delete" value ="削除">
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
				<td><c:out value="${user.createdAt }"></c:out></td>
				<td><c:out value="${user.updatedAt }"></c:out></td>
			</tr>
		</c:forEach>
	</table>
</div>


</body>
</html>