<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %>
<%@taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
</head>
<body>
<div>
	<a href ="home">ホーム</a>
	<a href = "logout">ログアウト</a>
</div>

<div><h3>新規投稿</h3></div>

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
	<form action="contribution" method ="post">
	タイトル<br>
	<input type ="text" name ="title" value ="${newPost.title }"><br>
	カテゴリー<br>
	選択<select name ="selectCategory">
		<option value = "">新規作成する
		<c:forEach items ="${categorySet }" var ="category">
			<option value ="${category }"><c:out value="${category }"></c:out>
		</c:forEach>
	</select>
	または新規作成<input type ="text" name = newCategory><br>
	本文<br>
	<textarea rows="10" cols="50" name="text" ><c:out value="${newPost.text }"></c:out></textarea><br>
	<input type ="submit" value ="投稿する">
	</form>
</div>
<c:remove var="newPost" scope ="session"/>
</body>
</html>