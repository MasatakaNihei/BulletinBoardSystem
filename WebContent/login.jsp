<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %>
<%@taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>ログイン画面</title>
</head>
<body>
<div class = "siteTitle">わったい菜掲示板システム</div>

<div><h3>ログイン</h3></div>
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
<c:remove var="loginUser" scope="session"/>

<div>
<form action="login" method = "post"><br>
	<label for="loginId">ログインID</label>
	<input type = "text" name = "loginId"/><br>
	<label for = "password">パスワード</label>
	<input type = "password" name = "password"/><br>
	<input type = "submit" value = "ログイン"/>
	
</form>
</div>
</body>
</html>
