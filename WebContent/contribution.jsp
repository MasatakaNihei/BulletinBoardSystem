<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %>
<%@taglib prefix ="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script>
	$(function(){
		var selectCategory = $(".selectCategory").attr("id");
		if(selectCategory != null){
			$(".selectCategory").val(selectCategory);
		}

		if($(".selectCategory").val() != ""){
			$(".newCategory").val("");
			$(".newCategory").attr("disabled", "disabled");
		}
		
		$(".selectCategory").change(function(){
			if($(".selectCategory").val() != ""){
				$(".newCategory").val("");
				$(".newCategory").attr("disabled", "disabled");
			}else{
				$(".newCategory").removeAttr("disabled");
				$(".newCategory").removeAttr("value");
			}
		});
	});
	
	</script>
	
	<title>新規投稿</title>
</head>
<body>
<div class ="head">
	<div class = "siteTitle">わったい菜掲示板システム</div>
	<div class ="loginUser">ログインユーザー：<c:out value="${loginUser.name }"></c:out></div>
	<div class ="menu">
		<a href = "home" >ホーム</a>
		<c:if test="${loginUser.getBranchID().equals('1') && (loginUser.getPositionId()).equals('1') }">
			<a href = "userManagement" >ユーザー管理</a>
		</c:if>
		<a href = "logout" >ログアウト</a>
	</div>
</div>

<div class ="pageTitle">
	<span class ="pageTitle">新規投稿</span>
</div>

<c:if test="${not empty errorMessages }">
	<div class ="errorMessage">
		<c:forEach items = "${errorMessages}" var = "message">
			<c:out value = "${message}"/><br>
		</c:forEach>
	</div>
	<c:remove var="errorMessages" scope ="session"/>
</c:if>


<div class="main">
	<div class ="newPost">
		<form action="contribution" method ="post">
		<span>タイトル</span><br>
		<input type ="text" name ="title" value ="${newPost.title }"><br>
		<span>カテゴリー</span><br>
		<select name ="selectCategory" class ="selectCategory" id ="${sessionScope.selectCategory }">
			<option value = "">新規作成する
			<c:forEach items ="${categorySet }" var ="category">
				<option value ="${category }"><c:out value="${category }"></c:out>
			</c:forEach>
		</select>
		<input type ="text" name = newCategory class ="newCategory" value ="${newCategory }"><br>
		<span>本文</span><br>
		<textarea rows="20" cols="50" name="text" ><c:out value="${newPost.text }"></c:out></textarea><br>
		<input type ="submit" class ="bottun" value ="投稿する">
		</form>
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

<c:remove var="newPost" scope ="session"/>
<c:remove var="selectCategory" scope ="session"/>
<c:remove var="newCategory" scope ="session"/>
</body>
</html>