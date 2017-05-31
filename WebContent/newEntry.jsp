<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script>
		$(function(){
			var $position = $('.position'); 
			var original = $position.html(); 
			
			$('.branch').change(function() {
			 
			  var val1 = $(this).val();
			 
			  $position.html(original).find('option').each(function() {
			    var val2 = $(this).attr('class'); 
			 
			   
			    if (val1 != val2) {
			      $(this).remove();
			    }
			 
			  });
			
			  if( val1 == "") {
			    $position.attr('disabled', 'disabled');
			  } else {
			    $position.removeAttr('disabled');
			  }
			 
			})
		});
	</script>
<title>新規登録</title>

</head>
<body>
<div>
	<a href ="home">ホーム</a>
	<a href = "userManagement">ユーザー管理</a>
	<a href = "logout">ログアウト</a>
</div>

<div><h3>ユーザー新規登録</h3></div>

<c:if test="${not empty errorMessages }">
	<div>
	<c:forEach items = "${errorMessages }" var="message">
		<li><c:out value="${message }"></c:out>
	</c:forEach>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div>
	<form action="newEntry" method ="post"><br>
		<label for = "loginId"> ログインID</label>
		<input type ="text" name ="loginId" value ="${newUser.loginId }" ><br>
		<label for="password">パスワード</label>	
		<input type ="password" name="password"><br>
		<label for="confPassword">パスワード(確認用)</label>
		<input type="password" name ="confPassword"><br>
		<label for = "name">ユーザー名</label>
		<input type ="text" name = "name" value = "${newUser.name }"><br>
		支店
		<select name ="branchId" class ="branch" >
			<option value = "" selected>支店を選択してください</option>
			<c:forEach items ="${requestScope.branchList}" var = "branch">
				<option value = "${branch.id }"><c:out value="${branch.name}"></c:out></option>
			</c:forEach>
		</select><br>
		部署・役職	
					<%--可能なら値の保持 --%>
		<select  name ="positionId" class ="position" disabled>
			<c:forEach items ="${branchList }" var ="branch">
				<c:if test="${positionMap.containsKey(branch.getId()) }">
					<option value = 0 selected>なし</option>
					<c:forEach items ="${positionMap[branch.id]}" var ="position">
						<option value = "${position.id}" class = "${branch.id }"><c:out value="${position.name }"></c:out></option>
					</c:forEach>
				</c:if>
				<c:if test="${!positionMap.containsKey(branch.getId()) }">
					<c:forEach items = "${positionMap['0'] }" var ="position">
						<option value ="${position.id }" class ="${branch.id }"><c:out value="${position.name }"></c:out></option>
					</c:forEach>
				</c:if>
			</c:forEach>
		
				
		</select><br>
		<input type="submit" value ="登録する">
		<c:remove var="newUser" scope = "session"/>
	</form>
</div>
<c:remove var="newUser" scope ="session"/>
</body>
</html>