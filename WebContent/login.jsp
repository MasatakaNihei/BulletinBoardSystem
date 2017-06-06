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
<div class ="head">
	<div class = "siteTitle">わったい菜掲示板システム</div>
</div>

<div class ="pageTitle">
	<span class ="pageTitle">
		ログイン
	</span>
</div>
<c:if test="${not empty errorMessages }">
	<div class ="errorMessage">
		<c:forEach items = "${errorMessages}" var = "message">
			<c:out value = "${message}"/><br>
		</c:forEach>
	</div>
	<c:remove var="errorMessages" scope ="session"/>
</c:if>
<c:remove var="loginUser" scope="session"/>
<form action="login" method = "post"><br>
	<table class ="form">
		<tr>
			<td><label for="loginId">ログインID</label></td>
			<td><input class="text" type = "text" name = "loginId"/></td>
		</tr>
		<tr>
			<td><label for = "password">パスワード</label></td>
			<td><input class="text" type = "password" name = "password"/></td>
		</tr>
		<tr>
			<td colspan ="2"><input type = "submit"  class ="bottun" value = "ログイン"/></td>
		</tr>
	</table>
</form>
</body>
</html>
