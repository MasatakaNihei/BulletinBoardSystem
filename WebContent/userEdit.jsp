<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored ="false" %>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
		var oldBranchId = $(".branch").attr("id");
		var oldPositionId = $(".position").attr("id");
		
		
		$(".branch").val(oldBranchId);
	
		$position.html(original).find('option').each(function() {
		    var val2 = $(this).attr('class'); 
		    if (val2 != oldBranchId) {
		      $(this).remove();
		    }
		});
	
		$(".position").val(oldPositionId);
		
		$('.branch').change(function() { 
		  var val1 = $(this).val();
		  $position.html(original).find('option').each(function() {
		    var val2 = $(this).attr('class'); 
		    if (val1 != val2) {
		      $(this).remove();
		    }
		  });
		})
	});
	</script>
<title>ユーザー編集</title>
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
	<div class="userEdit">
		<form action="userEdit" method ="post">
			<table>
			<tr>
			<th>
			<input type ="hidden" name ="id" value ="${targetUser.id }">
			名前
			</th>
			<td>
			<input type ="text" name ="name" value="${targetUser.name }"><br>
			</td>
			</tr>
			<tr>
			<th>
			ログイン
			</th>
			<td><input type ="text" name ="loginId" value="${targetUser.loginId }"><br>
			</td>
			</tr>
			<tr>
			<th>
			パスワード
			</th>
			<td><input type ="password" name="password"><br>
			</td>
			<td class ="right">
			(空白の場合は変更しません)
			</td>
			</tr>
			<tr>
			<th>
			パスワード(確認用)
			</th>
			<td>
			<input type ="password" name ="confPassword"><br>
			</td>
			</tr>
			
			
			<tr>
			<th>
			支店
			</th>
			<td>
			<c:if test="${targetUser.id == loginUser.id }" var = "isLoginUser">
			</c:if>
			<c:if test="${isLoginUser }">
				<input type ="hidden" name ="branchId" value ="">
				※自分の支店は編集はできません※<br>
			</c:if>	
			<c:if test="${not isLoginUser }">
				<c:if test="${sessionScope.oldBranchId == null }">
					<c:set var = "oldBranchId" value ="${targetUser.branchID }" scope ="session"></c:set>
				</c:if>
				<select name ="branchId" class ="branch" id ="${oldBranchId }">
					<option value = "${oldBranchId }" selected >変更しない</option>
					<c:forEach items ="${requestScope.branchList}" var = "branch">
						<c:if test="${!branch.getId().equals(oldBranchId) }">
							<option value = ${branch.id }><c:out value="${branch.name}"></c:out></option>
						</c:if>
					</c:forEach>
				</select><br>
			</c:if>
			</td>
			<td class ="right">
			(現在：<c:out value="${targetUser.branchName }"></c:out>)
			</td>
			</tr>
			<tr>
			<th>
			部署・役職
			</th>
			<td>
			<c:if test="${isLoginUser }">
				<input type ="hidden" name ="positionId" value ="">
				※自分の部署・役職は編集できません※<br>
			</c:if>
			<c:if test="${not isLoginUser }">
				<c:if test="${sessionScope.oldPositionId == null }">
					<c:set var = "oldPositionId" value ="${targetUser.positionId }" scope ="session"/>
				</c:if>
				<select  name ="positionId"  class = "position" id ="${oldPositionId }">	
					<option value = "${oldPositionId }" class = "${oldBranchId}" selected >変更しない</option>
					<c:forEach items ="${branchList }" var ="branch">
						<c:if test="${positionMap.containsKey(branch.getId()) }">
							<c:if test="${!oldPositionId.equals('0') }">
								<option value = 0 class = "${branch.id }">なし</option>
							</c:if>
							<c:forEach items ="${positionMap[branch.id]}" var ="position">
								<c:if test="${!position.getId().equals(oldPositionId) }">
									<option value = "${position.id}" class = "${branch.id }"><c:out value="${position.name }"></c:out></option>
								</c:if>
							</c:forEach>
						</c:if>
						<c:if test="${!positionMap.containsKey(branch.getId()) }">
							<c:forEach items = "${positionMap['0'] }" var ="position">
								<c:if test="${!position.getId().equals(oldPositionId) }">
									<option value ="${position.id }" class ="${branch.id }"><c:out value="${position.name }"></c:out></option>
								</c:if>
							</c:forEach>
						</c:if>
					</c:forEach>
			</select><br>
			</c:if>
			</td>
			<td class ="right">
			(現在：<c:out value="${targetUser.positionName }"></c:out>)
			</td>
			</tr>
			<tr>
			<td colspan="3">
			<input type ="submit" class ="bottun" value ="編集を確定"/>
			</td>
			</tr>
			</table>
		</form>
	</div>
</div>
<c:remove var="targetUser" scope ="session"/>
<c:remove var="oldBranchId" scope ="session"/>
<c:remove var="oldPositionId" scope ="session"/>
</body>
</html>