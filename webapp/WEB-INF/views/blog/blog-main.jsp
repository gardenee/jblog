<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">

<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>

</head>

<body>
	<div id="wrap">

		<!-- 개인블로그 해더 -->
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>
		
		<div id="content" class="clearfix">
			<div id="profilecate_area">
				<div id="profile">
					<!-- 기본이미지 -->
					<img id="proImg" src="${pageContext.request.contextPath}${bVo.logoFile}" />
					<div id="nick">${bVo.name}(${bVo.id})님</div>
				</div>
				
				<div id="cate">
					<div class="text-left">
						<strong>카테고리</strong>
					</div>
		
					<form id="cateForm" action="${pageContext.request.contextPath}/blog/${bVo.id}" method="POST">
						<input id="cateNo" type="hidden" name="cateNo" value="">
					</form>
					
					<ul id="cateList" class="text-left">
						<c:forEach items="${cList}" var="cate">
							<li><a class="cate-a" data-cateNo="${cate.cateNo}">${cate.cateName} (${cate.postNum})</a></li>
						</c:forEach>			
					</ul>
				</div>
			</div>
			
			<!-- profilecate_area -->
			<div id="post_area">				
				<c:if test="${!empty(post)}">
					<div id="postBox" class="clearfix">
							<div id="postTitle" class="text-left" data-postNo="${post.postNo}"><strong>${post.postTitle}</strong></div>
							<div id="postDate" class="text-left"><strong>${post.regDate}</strong></div>
							<div id="postNick">${post.userName}(${post.id})님</div>
					</div>
					<!-- //postBox -->
				
					<div id="post" >
						${post.postContent}
					</div>
					
					<!--  comments area  -->
						<div id="comments_area">
							<!--  코멘트 작성  -->
							<c:if test="${!empty(authUser)}">
								<table id="write-comments" border="1">
									<colgroup>
										<col style="width: 100px">
										<col style="width: 500px">
										<col style="width: 100px">
									</colgroup>
									<tr>
										<td><a id="userName" data-no="${authUser.userNo}">${authUser.userName}</a></td>
										<td><input name="cmtContent" type="text" value=""></td>
										<td><button id="btn-addcmt" type="button">저장</button></td>
									</tr>
								</table>
							</c:if>
							<!--  // 코멘트 작성  -->
							
							<!--  코멘트 읽기 -->
							<table id="read-comments" border="1">
								<colgroup>
									<col style="width: 100px">
									<col style="width: 450px">
									<col style="width: 100px">
									<col style="width: 50px">
								</colgroup>
							</table>
							<!--  //코멘트 읽기 -->
						</div>
					<!--  ///comments area -->
				</c:if>
				<!-- //post -->
				
				<!-- 글이 없는 경우 -->
				<c:if test="${empty(post)}">
					<div id="postBox" class="clearfix">
								<div id="postTitle" class="text-left"><strong>등록된 글이 없습니다.</strong></div>
								<div id="postDate" class="text-left"><strong></strong></div>
								<div id="postNick"></div>
					</div>
				    
					<div id="post">
					</div>
				</c:if>
				
				<!-- 리스트 영역 -->
				<c:if test="${!empty(post)}">
					<div id="list">
						<div id="listTitle" class="text-left"><strong>${cateName}의 글</strong></div>
						
						<form id="postForm" action="" method="POST">
								<input id="post-cateNo" type="hidden" name="cateNo" value="${post.cateNo}">
								<input id="pageNo" type="hidden" name="pageNo" value="${paging.currPage}">								
						</form>
						
						<table>
							<colgroup>
								<col style="">
								<col style="width: 20%;">
							</colgroup>
														
							<c:forEach items="${pList}" var="post">
								<tr>
									<td class="text-left"><a class="post-a" data-postNo="${post.postNo}">${post.postTitle}</a></td>
									<td class="text-right">${post.regDate}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<!--  // 리스트 영역 -->
					
					<!-- 페이징 -->
					<div id="paging">
						<form id="pageForm" action="${pageContext.request.contextPath}/blog/${bVo.id}/${post.postNo}" method="POST">
							<input id="page-cateNo" type="hidden" name="cateNo" value="${post.cateNo}">
							<input id="page-pageNo" type="hidden" name="pageNo" value="">
						</form>
						
						<ul>
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
						</ul>
					</div>
					<!-- //페이징 -->
				</c:if>
				<!-- //list -->
			</div>
			<!-- //post_area -->
			
		</div>	
		<!-- //content -->
		<div class=></div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>
		
	</div>
	<!-- //wrap -->
	
</body>

<script type="text/javascript">

$(document).ready(function(){ // 페이지 로딩 시 댓글 데려오기
	var postNo = "${post.postNo}";
	
	if (postNo != "") {
		
		var postVo = {
			postNo: postNo		
		}
		
		$.ajax({
			url: "${pageContext.request.contextPath}/comments/loadComment",
			type : "post",
			contentType : "application/json",
			data: JSON.stringify(postVo),
			
			dataType: "json",
			success : function(cmtList){
				console.log(cmtList);
				
				for (var i = 0; i < cmtList.length; i++) {
					render(cmtList[i]);
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	}
});


function render(cmtVo){ // 댓글 추가 함수
	if ("${authUser.userNo}" == cmtVo.userNo) {
		$("#read-comments").prepend(
			  "<tr class='comment' name='del" + cmtVo.cmtNo + "'>"
			+     "<td>" + cmtVo.userName + "</td>"
			+     "<td>" + cmtVo.cmtContent + "</td>"
			+     "<td>" + cmtVo.regDate + "</td>"
			+     "<td class='delte-cmt'>"
			+         "<a class='delete-this' data-cmtNo='" + cmtVo.cmtNo + "'>X</a>"
			+     "</td>"
			+ "</tr>"
		)
	} else {
		$("#read-comments").prepend(
				  "<tr class='comment'>"
				+     "<td>" + cmtVo.userName + "</td>"
				+     "<td>" + cmtVo.cmtContent + "</td>"
				+     "<td>" + cmtVo.regDate + "</td>"
				+     "<td></td>"
				+ "</tr>"
			)
	}
};


$("#btn-addcmt").on("click", function(){ // 댓글 추가 버튼 클릭
	if ("${authUser}".length == 0) { // 로그인 풀린 경우
		return false;
    }
	
	var userNo = "${authUser.userNo}";
	var content = $("[name=cmtContent]").val();
	var postNo = $("#postTitle").attr("data-postNo");
	
	if (content == null || content == "") { // 댓글 없는 데 submit 누른 경우
		alert("댓글을 입력해주세요.")
		return false;
	}
	
	var cmtVo = {
			userNo: userNo,
			cmtContent: content,
			postNo: postNo
	}
		
	$.ajax({ // db에 댓글 추가
		url: "${pageContext.request.contextPath}/comments/addComment",
		type : "post",
		contentType : "application/json",
		data: JSON.stringify(cmtVo),
			
		dataType: "json",
		success : function(newCmt){
			if (newCmt != null) {
				render(newCmt);
				$("[name=cmtContent]").val("");
			} else {
				alert("오류가 발생했습니다");
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});
});


$("#read-comments").on("click", ".delete-this", function(){ // 댓글 삭제 버튼 클릭
	var cmtNo = $(this).attr("data-cmtNo");
	
	if (confirm("삭제하시겠습니까?")) {		
		var cmtVo = {
				cmtNo: cmtNo
		}
		
		$.ajax({
			url: "${pageContext.request.contextPath}/comments/deleteComment",
			type : "post",
			contentType : "application/json",
			data: JSON.stringify(cmtVo),
				
			dataType: "json",
			success : function(result){
				if (result) {
					$("[name = del" + cmtNo + "]").remove();
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});
	} else {
		return;
	}
});


$(".cate-a").on("click", function(){ // 카테고리 칸에서 카테고리 선택
	var cateNo = $(this).attr("data-cateNo");
	$("#cateNo").val(cateNo);
	
	$("#cateForm").submit();
});


$(".post-a").on("click", function(){ // 게시글 리스트에서 게시글 선택
	var postNo = $(this).attr("data-postNo");
	$("#postForm").attr("action", "${pageContext.request.contextPath}/blog/${bVo.id}/" + postNo);
	
	$("#postForm").submit();
});


$(".page-a").on("click", function(){ // 페이징 버튼 클릭
	var pageNo = $(this).attr("data-page");
	$("#page-pageNo").val(pageNo);
	
	$("#pageForm").submit();
});


$(".btn_s").on("click", function(){ // 로그인 버튼 클릭(주소 유지용)
	$("#loginForm").submit();
})

</script>

</html>