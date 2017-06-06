<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./css/style.css" rel="stylesheet" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script>
		$(function(){
			var $position = $('.position'); 
			var original = $position.html(); 
			var branchId = $(".branch").attr("id");
			var positionId = $(".position").attr("id");
			
			if(branchId != null){
				$(".branch").val(branchId);
				
				$position.html(original).find('option').each(function() {
				    var val2 = $(this).attr('class'); 
				   
				    if (val2 != branchId) {
				      $(this).remove();
				    }
				 
				});
		
			}
			if(positionId != null){
				$(".position").val(positionId);
			}
			
			if($(".branch").val() == ""){
				$(".position").attr("disabled", "disabled");
			}
			
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
	
	<script>
		$(function(){
			var branchId = $(".branch").attr("id");
			var positionId = $(".position").attr("id");
			
			if(branchId != null){
				$(".branch").val(branchId);
				
				$position.html(original).find('option').each(function() {
				    var val2 = $(this).attr('class'); 
				   
				    if (val2 != branchId) {
				      $(this).remove();
				    }
				 
				});
		
			}
			if(positionId != null){
				$(".position").val(positionId);
			}
			
			if($(".branch").val() == ""){
				$(".position").attr("disabled", "disabled");
			}
		});
	</script>
<title>新規登録</title>

</head>
<body>
<div class ="head">
	<div class = "siteTitle">わったい菜掲示板システム</div>
	<div class ="loginUser">ログインユーザー：<c:out value="${loginUser.name }"></c:out></div>
	<div class ="menu">
		<a href = "home" >ホーム</a>
		<a href = "userManagement">ユーザー管理</a>
		<a href = "logout" >ログアウト</a>
	</div>
</div>

<div class ="pageTitle">
	<span class ="pageTitle">ユーザー新規登録</span>
</div>

<c:if test="${not empty errorMessages }">
	<div class ="errorMessage">
	<c:forEach items = "${errorMessages }" var="message">
		<c:out value="${message }"></c:out><br>
	</c:forEach>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
<div class ="main">
	<div class ="newEntryTable">
		
		<form action="newEntry" method ="post">
			<table>
				<tr>
					<th><label for = "loginId"> ログインID</label></th>
					<td><input type ="text" name ="loginId" value ="${newUser.loginId }" id= "loginId" ></td>
				</tr>
				<tr>
					<th><label for="password">パスワード</label></th>
					<td><input type ="password" name="password" id ="password"><br></td>
				</tr>
				<tr>
					<th><label for="confPassword">パスワード(確認用)</label></th>
					<td><input type="password" name ="confPassword" id ="confPassword"></td>
				</tr>
				<tr>
					<th><label for = "name">ユーザー名</label></th>
					<td><input type ="text" name = "name" value = "${newUser.name }" id ="name"></td>
				</tr>
				<tr>
					<th><label>支店</label></th>
					<td>
						<select name ="branchId" class ="branch" id ="${newUser.branchID }">
							<option value = "" selected>支店を選択してください</option>
							<c:forEach items ="${requestScope.branchList}" var = "branch">
								<option value = "${branch.id }"><c:out value="${branch.name}"></c:out></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th><label>部署・役職</label></th>
					<td>	
					<select  name ="positionId" class ="position"  id="${newUser.positionId }">
							<c:forEach items ="${branchList }" var ="branch">
								<c:if test="${positionMap.containsKey(branch.getId()) }">
									<option value = 0 selected class = "${branch.id }">なし</option>
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
						
								
						</select>
					</td>
				</tr>
				<tr>
					<td colspan ="2"><input type="submit" class ="bottun" value ="登録する"></td>
				</tr>
			</table>
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
<c:remove var="newUser" scope ="session"/>
</body>
</html>