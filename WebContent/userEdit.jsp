<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー編集</title>
</head>
<body>
<div>
	<a href ="home">ホーム</a>
	<a href = "userManagement">ユーザー管理</a>
	<a href = "logout">ログアウト</a>
</div>

<div><h3>ユーザー編集</h3></div>

<c:if test="${not empty errorMessages }">
	<div>
		<ul>
			<c:forEach items = "${errorMessages}" var = "message">
				<li><c:out value = "${message}"/>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope ="session"/>
</c:if>

<div>
	<form action="userEdit" method ="post">
		<input type ="hidden" name ="id" value ="${targetUser.id }">
		<input type ="text" name ="name" value="${targetUser.name }">名前<br>
		<input type ="text" name ="loginId" value="${targetUser.loginId }">ログインID<br>
		<input type ="password" name="password">パスワード(空白の場合変更しません)<br>
		<input type ="password" name ="confPassword">パスワード(確認用)<br>
		支店(現在：<c:out value="${targetUser.branchName }"></c:out>)
		<c:if test="${targetUser.id == loginUser.id }">
			<input type ="hidden" name ="branchId" value ="noEdit">
			自分自身の所属は編集できません<br>
		</c:if>
		<c:if test="${targetUser.id != loginUser.id }">
			<select name ="branchId" >
				<option value = "" selected>変更しない</option>
				<c:forEach items ="${requestScope.branchList}" var = "branch">
					<option value = ${branch.id }><c:out value="${branch.name}"></c:out></option>
				</c:forEach>
			</select><br>
		</c:if>
		部署・役職(現在：<c:out value="${targetUser.positionName }"></c:out>)
		<c:if test="${targetUser.id == loginUser.id }">
			<input type ="hidden" name ="positionId" value ="noEdit">
			自分自身の所属は編集できません<br>
		</c:if>
		<c:if test="${targetUser.id != loginUser.id }">
			<select  name ="positionId" >
				<option value = "" selected>変更しない</option>
				<option value = 0>なし</option>
				<c:forEach items ="${positionList}" var ="position">
					<option value = "${position.id}"><c:out value="${position.name }"></c:out></option>
				</c:forEach>
			</select><br>
		</c:if>
		<input type ="submit" value ="編集を確定">
	</form>
</div>
<c:remove var="targetUser" scope ="session"/>
</body>
</html>