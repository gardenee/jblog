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
	<div id="wrap">
		<!-- 개인블로그 해더 -->
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>

		<div id="content">
			<ul id="admin-menu" class="clearfix">
				<li class="tabbtn"><a href="${pageContext.request.contextPath}/${bVo.id}/admin/basic">기본설정</a></li>
				<li class="tabbtn selected"><a href="${pageContext.request.contextPath}/${bVo.id}/admin/category">카테고리</a></li>
				<li class="tabbtn"><a href="${pageContext.request.contextPath}/${bVo.id}/admin/writeForm">글작성</a></li>
			</ul>
			<!-- //admin-menu -->
			
			<div id="admin-content">
		
				<table id="admin-cate-list">
					<colgroup>
						<col style="width: 50px;">
						<col style="width: 200px;">
						<col style="width: 100px;">
						<col>
						<col style="width: 50px;">
					</colgroup>
		      		<thead>
			      		<tr>
			      			<th>번호</th>
			      			<th>카테고리명</th>
			      			<th>포스트 수</th>
			      			<th>설명</th>
			      			<th>삭제</th>      			
			      		</tr>
		      		</thead>
		      		<tbody id="cateList">
		      			<!-- 리스트 영역 -->
					</tbody>
				</table>
      	
		      	<table id="admin-cate-add" >
		      		<colgroup>
						<col style="width: 100px;">
						<col style="">
					</colgroup>
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name" value=""></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="desc"></td>
		      		</tr>
		      	</table> 
			
				<div id="btnArea">
		      		<button id="btnAddCate" class="btn_l" type="button" >카테고리추가</button>
		      	</div>
			
			</div>
			<!-- //admin-content -->
			
		</div>	
		<!-- //content -->
		
		<!-- 개인블로그 푸터 -->
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>
		
	</div>
	<!-- //wrap -->
	
</body>

<script type="text/javascript">

var temp = 0;
var count = 0; //전체 카테고리 수

$(document).ready(function(){
	$.ajax({	//카테고리 테이블 불러오기
		url: "${pageContext.request.contextPath}/${authUser.id}/admin/categoryList",
		type : "post",
		async: false,
		
		success : function(cList){
			count = cList.length;
			temp = count;
			
			for (var i = 0; i < cList.length; i++) {
				render(cList[i])
			}			
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
})


function render(category) {	//초기 카테고리 테이블 불러올 때 사용
	$("#cateList").append(
			"<tr data-no='" + category.cateNo + "'>"
		+ 		"<td class='info' id='no" + temp + "'>" + temp + "</td>" 
		+		"<td>" + category.cateName + "</td>" 
		+ 		"<td>" + category.postNum + "</td>" 
		+ 		"<td>" + category.description + "</td>" 
		+ 		"<td class='text-center'>"
		+ 			"<img class='btnCateDel' src='${pageContext.request.contextPath}/assets/images/delete.jpg' data-num='" + category.cateNo + "' data-post='" + category.postNum + "'>"
		+ 		"</td>"
		+	"</tr>"
	);	
	temp -= 1;
}


$("#cateList").on("click", ".btnCateDel", function() { // 삭제 버튼 클릭
	var no = $(this).attr("data-num");
	var postNum = $(this).attr("data-post");
    var cateName = $(this).attr("data-name");
    var number = parseInt($(this).parent().siblings(".info").text());
    
	if (postNum != 0) {
		alert("삭제할 수 없습니다. (게시글 존재)")
		
		return false;
		
	} else if (cateName == "미분류") {
		alert("삭제할 수 없습니다. (기본 카테고리)")
		
		return false;
	
	} else { // 삭제하기
		var map = {cateNo: no};
		
		$.ajax({	
			url: "${pageContext.request.contextPath}/${authUser.id}/admin/deleteCategory", // db에서 제거
			type : "post",
			contentType : "application/json",
			data : JSON.stringify(map),
			
			dataType: "json",
			success : function(result){
				if (result) {
					$("[data-no=" + no + "]").remove(); // 테이블에서 제거
				   	update(number); // 번호 밀린 것 처리
					count -= 1;

					alert("삭제되었습니다.")
					
				} else {
					alert("삭제를 실패했습니다.")
				}
			},
			error : function(XHR, status, error) {
				console.error(status + " : " + error);
			}
		});	
	}
});


function update(number) { // 번호 갱신(삭제한 번호+1부터 끝까지)
	for (var i = (number + 1); i <= count; i++) {
		$("#no" + i).text(i-1);
		$("#no" + i).attr("id", "no" + (i-1))
	}
}


$("#btnAddCate").on("click", function(){ // 카테고리 추가
	var cateName = $("[name=name]").val();
	var description = $("[name=desc]").val();
	
	if (cateName.length <= 0) {
		alert("카테고리명을 입력해주세요.")
		
		return false;
	}
		
	var map = {
			id: "${authUser.id}",
			cateName: cateName,
			description: description
	};
	
	$.ajax({	
		url: "${pageContext.request.contextPath}/${authUser.id}/admin/addCategory", // 카테고리 추가
		type : "post",
		contentType : "application/json",
		data : JSON.stringify(map),
		
		dataType: "json",
		success : function(cateNo){
			if  (cateNo != -1) { 
				var no = cateNo;
				
				$("#cateList").prepend(
						"<tr data-no='" + cateNo + "'>"
					+ 		"<td class='info' id='no" + (count + 1) + "'>" + (count + 1) + "</td>" 
					+		"<td>" + cateName + "</td>" 
					+ 		"<td>0</td>" 
					+ 		"<td>" + description + "</td>" 
					+ 		"<td class='text-center'>"
					+ 			"<img class='btnCateDel' src='${pageContext.request.contextPath}/assets/images/delete.jpg' data-num='" + cateNo + "' data-post='0'>"
					+ 		"</td>"
					+	"</tr>"
				);
				
				count += 1;
				
				alert("추가되었습니다.");
				
			} else { // 이름 겹치거나 오류 발생하면 cateNo = -1 로 넘어옴
				alert("사용할 수 없는 카테고리명입니다.");
			}
		},
		error : function(XHR, status, error) {
			console.error(status + " : " + error);
		}
	});	
	
	$("[name=name]").val("");
	$("[name=desc]").val("");
});

</script>

</html>