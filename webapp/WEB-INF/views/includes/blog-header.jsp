<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

		<div id="header" class="clearfix">
			<h1><a href="${pageContext.request.contextPath}/blog/${bVo.id}">${bVo.blogTitle}</a></h1>
			<ul class="clearfix">
				<c:if test="${empty(authUser)}">
					<form id="loginForm" action="${pageContext.request.contextPath}/user/loginForm" method="POST">
						<input type="hidden" id="login-cate" name="cateNo" value="${post.cateNo}">
						<input type="hidden" id="login-page" name="pageNo" value="${paging.currPage}">
					</form>
					<li><a class="btn_s">로그인</a></li>
				</c:if>
				
				<!-- 로그인 후 메뉴 -->
				<!-- 자신의 블로그일때만 관리 메뉴가 보인다. -->
				<c:if test="${!empty(authUser) &&  authUser.id == bVo.id}">
					<li><a class="btn_s" href="${pageContext.request.contextPath}/blog/${bVo.id}/admin/basic">내블로그 관리</a></li>
				</c:if>
				<c:if test="${!empty(authUser)}">
					<li><a class="btn_s" href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
		 		</c:if>
			</ul>
		</div>
		<!-- //header -->
		