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

		<div>		
			<form id="joinForm" method="post" action="${pageContext.request.contextPath}/user/join">
				<table>
			      	<colgroup>
						<col style="width: 100px;">
						<col style="width: 170px;">
						<col style="">
					</colgroup>
		      		<tr>
		      			<td><label for="txtId">아이디</label></td>
		      			<td><input id="txtId" type="text" name="id" value=""></td>
		      			<td><button id="btnIdCheck" type="button">아이디체크</button></td>
		      		</tr>
		      		<tr>
		      			<td id="tdMsg" colspan="2"></td>
		      		</tr> 
		      		<tr>
		      			<td><label for="txtPassword">패스워드</label> </td>
		      			<td><input id="txtPassword" type="password" name="password" value=""></td>   
		      			<td></td>  			
		      		</tr> 
		      		<tr>
		      			<td><label for="txtUserName">이름</label> </td>
		      			<td><input id="txtUserName" type="text" name="userName" value=""></td>   
		      			<td></td>  			
		      		</tr>  
		      		<tr>
		      			<td><span>약관동의</span> </td>
		      			<td colspan="3">
		      				<input id="chkAgree" type="checkbox" name="agree" value="y">
		      				<label for="chkAgree">서비스 약관에 동의합니다.</label>
		      			</td>   
		      		</tr>   		
		      	</table>
	      		<div id="btnArea">
					<button id="btnJoin" class="btn" type="submit">회원가입</button>
				</div>
	      		
			</form>
			
		</div>
		
		
		<!-- 메인 푸터  자리-->
		<c:import url="/WEB-INF/views/includes/main-footer.jsp"></c:import>
		
	</div>

</body>

<script type="text/javascript">

var checked = "" // 중복체크된 아이디 저장

$("#btnIdCheck").on("click", function(){ // 중복체크 전 아이디 입력 여부 확인
	checked = "";
	
	if ($("#txtId").val().length <= 0) {
		alert("아이디를 입력해주세요.")
	} else {
		idcheck();
	}
});


function idcheck() { // 아이디 중복확인
	var id = $("#txtId").val(); // 초기화
	
	var test = {id: id};
	
	$.ajax({	
		url: "${pageContext.request.contextPath}/user/idcheck",
		type : "post",
		async: false,
		contentType : "application/json",
		data : JSON.stringify(test),
		
		dataType: "json",
		success : function(result){
			if (!result) {
				$("#tdMsg").text("다른 아이디로 가입해 주세요.");
				$("#txtId").val("");
				
			} else {
				$("#tdMsg").text("사용할 수 있는 아이디입니다.");
				checked = id; // 체크됐으면 저장
			}			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
}


$(btnJoin).on("click", function(){ // 폼 다 작성했는지 확인
	if ($("#txtId").val().length <= 0) {
		alert("아이디를 입력해주세요.")
		return false;
		
	} else if (checked != $("#txtId").val()) {
		alert("아이디 중복확인을 해주세요.")
		return false;
		
	} else if ($("#txtPassword").val().length <= 0) {
		alert("비밀번호를 입력해주세요.")
		return false;
		
	} else if ($("#txtUserName").val().length <= 0) {
		alert("이름을 입력해주세요.")
		return false;
		
	} else if (!$("#chkAgree").is(":checked")) {
		alert("약관에 동의해주세요.")
		return false;
	}
});

</script>

</html>