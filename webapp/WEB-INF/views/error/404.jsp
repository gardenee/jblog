<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">

</head>

<body>
	<div class="center-content">
		<!-- 메인해더 -->
		<c:import url="/WEB-INF/views/includes/main-header.jsp"></c:import>
		
		<h1>존재하지 않는 게시글입니다.</h1>
		<a href="${pageContext.request.contextPath}/blog/${bVo.id}">메인으로 돌아가기</a>
	</div>

</body>

</html>
