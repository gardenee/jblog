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
		<!--메인 해더 자리 -->
		<c:import url="/WEB-INF/views/includes/main-header.jsp"></c:import>
		
		<form id="search-form" action="${pageContext.request.contextPath}/" method="post">
			<fieldset>
				<input type="text" id="search-keyword" name="keyword" value="">
				<button id="btnSearch" type="submit">검색</button>
			</fieldset>
			
			<fieldset>
				<label for="rdo-title">블로그 제목</label> 
				<input id="rdo-title" type="radio" name="option" value="title" > 
				
				<label for="rdo-userName">블로거 이름</label> 
				<input id="rdo-userName" type="radio" name="option" value="name" > 
			</fieldset>
		</form>
		
		<div id="resultList">
			<c:if test="${option != null}">
				<c:if test="${bList.size() == 0}">
					<p>검색 결과가 존재하지 않습니다.</p>
				</c:if>
				
				<c:if test="${bList.size() > 0}">
					<c:forEach items="${bList}" var="blog">
						<table border="1">
							<tr>
								<td><img src="${pageContext.request.contextPath}${blog.logoFile}"></td>
								<td><a href="${pageContext.request.contextPath}/${blog.id}" target="_blank">${blog.blogTitle}</a></td>
								<td>${blog.name}(${blog.id})</td>
								<td>
									<c:if test="${blog.regDate == '01/01/01'}">
										<a>최근 게시물 없음</a>
									</c:if>
									
									<c:if test="${blog.regDate != '01/01/01'}">
										${blog.regDate}
									</c:if>
								</td>
							</tr>
						</table>
					</c:forEach>
					
					<div id="paging">
						<form id="pagingForm" action="${pageContext.request.contextPath}/" method="POST">
							<input type="hidden" name="option" value="${option}">
							<input type="hidden" name="keyword" value="${keyword}">
							<input id="pageNo" type="hidden" name="pageNo" value="">
						</form>
						
						<c:if test="${paging.prev}">
							<li><a class="page-a" data-page="${paging.startBtn-1}">◀</a></li>
						</c:if>
						
						<c:forEach begin="${paging.startBtn}" end="${paging.endBtn}" step="1" var="page">
							<c:if test="${page == paging.currPage}">
								<li class="active"><a class="page-a" data-page="${page}">${page}</a></li>
							</c:if>
							<c:if test="${page != paging.currPage}">
								<li><a class="page-a" data-page="${page}">${page}</a></li>
							</c:if>
						</c:forEach>
						
						<c:if test="${paging.next}">
							<li><a class="page-a" data-page="${paging.endBtn+1}">▶</a></li>
						</c:if>
					</div>
				</c:if>
			</c:if> 
			
		</div>
		
		<!-- 메인 푸터  자리-->
		<c:import url="/WEB-INF/views/includes/main-footer.jsp"></c:import>
	
	</div>
	<!-- //center-content -->
</body>

<script type="text/javascript">

$("#btnSearch").on("click", function(){ // 검색 옵션 선택 여부 확인
	if (!$("#rdo-title").is(":checked") && !$("#rdo-userName").is(":checked")) {
		alert("검색 옵션을 선택해주세요");
	}
});


$(".page-a").on("click", function(){ // 검색하기
	var pageNo = $(this).attr("data-page");
	$("#pageNo").val(pageNo);
	
	$("#pagingForm").submit();
});


</script>

</html>