<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.12.4.js"></script>

</head>
<body>
	<div id="center-content">
		
		
		<!-- 메인 해더 -->
		<c:import url="/WEB-INF/views/includes/main-header.jsp"></c:import>
		
		<div id="loginForm">
			<form id="login-form" method="post" action="${pageContext.request.contextPath}/user/login">
				<input type="hidden" name="address" value="${address}">
				<input type="hidden" name="cateNo" value="${post.cateNo}">
				<input type="hidden" name="pageNo" value="${page.pageNo}">
				
	      		<table>
			      	<colgroup>
						<col style="width: 100px;">
						<col style="">
					</colgroup>
		      		<tr>
		      			<td><label for="textId">아이디</label></td>
		      			<td><input id="textId" type="text" name="id"></td>
		      		</tr>
		      		<tr>
		      			<td><label for="textPassword">패스워드</label> </td>
		      			<td><input id="textPassword" type="password" name="password"></td>   
		      		</tr> 
		      		<tr>
		      			<td colspan="2" id="tdMsg" colspan="2">
		      				<span></span>
		      			</td>
		      		</tr> 
		      	</table>
	      		<div id="btnArea">
					<button id="login-btn" class="btn" type="submit">로그인</button>
				</div>
			</form>
		
		</div>
		
		<!-- 메인 푸터  자리-->
		<c:import url="/WEB-INF/views/includes/main-footer.jsp"></c:import>
		
	</div>
	
</body>

<script type="text/javascript">

$("#login-btn").on("click", function(){
	var rv = true;
	
	var login = {
			id: $("#textId").val(),
			password : $("#textPassword").val()
	}
	
	$.ajax({	
		url: "${pageContext.request.contextPath}/user/logincheck",
		type : "post",
		async: false,
		contentType : "application/json",
		data : JSON.stringify(login),
		
		dataType: "json",
		success : function(result){
			if (!result) {
				$("#tdMsg span").text("아이디 또는 비밀번호를 확인해주세요.");
				$("#textId").val("");
				$("#textPassword").val("");
				
				rv= false;
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
	console.log("rv" + rv);
	
	return rv;
});

</script>

</html>