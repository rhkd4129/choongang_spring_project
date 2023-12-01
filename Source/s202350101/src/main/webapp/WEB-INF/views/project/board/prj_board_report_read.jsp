<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<!-- CSS END -->

<!-- JS START -->
<script type="text/javascript" src="/project/board/js/prj_board_report.js"></script>
<!-- JS END -->

<script type="text/javascript">

//---------------------------------------------------------------------
//댓글등록
function insertComment() {
	var params = {};
	params.doc_no = "${board.doc_no}";
	params.project_id = "${board.project_id}";
	params.user_id = "${userInfoDTO.user_id}";
	
	params.comment_context = $('textarea[name=comment_context]').val();

	$.ajax({
		url			: "prj_board_report_insert_comment",
		type		: 'POST',		
		contentType : 'application/json; charset:utf-8',
		data 		: JSON.stringify(params),
		dataType 	: 'text',
		success		: function(data) {
			if(data == "success") {
				showCommentList();
			}else{
				alert("댓글등록에 실패하였습니다. 관리자에 문의하세요.");
			}			
		},
		error: function(xhr, status, error){
			console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
		}
	});
}

//댓글목록 조회
function showCommentList() {
	var params = {};
	params.doc_no = "${board.doc_no}";
	params.project_id = "${board.project_id}";

	$.ajax({
		url			: 'prj_board_report_comment_list',
		type		: 'POST',
		contentType : 'application/json; charset:utf-8',
		data 		: JSON.stringify(params),
		dataType 	: 'json',
		success		: function(data) {
			//--------------------
			drawCommentList(data);
			//--------------------
		},
		error: function(xhr, status, error){
			console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
		}
	});
}

//댓글목록 그려주기
function drawCommentList(comments){
	$("#divCommentList").empty();
	if(comments.length==0){
		//alert("댓글 정보가 없습니다.");
		$("#divCommentList").html("");
		$("#divCommentList").hide();
		$("#divCommentCount").html("");
		$("#divCommentCount").hide();
	}
	else{
		$(comments).each(function(index, comment){
			var list = '<label class="list-group-item d-flex gap-2" id="comment_' + comment.comment_doc_no + '">';
			list 	+= '<span>';
			list 	+= '<small class="d-block text-body-secondary">작성자 : ' + comment.user_name + '</small>';
			//작성일 표시처리 : common.js안에 formatDateTime() : 2023-11-09T01:44:25.000+00:00->2023-11-09 01:44:25
			list 	+= '<small class="d-block text-body-secondary">작성일 : ' + formatDateTime(comment.create_date) + '</small>';
			list 	+= comment.comment_context;
			list 	+= '</span>';
			if(comment.user_id == "${userInfoDTO.user_id}") { //로그인사용자가 작성한 댓글만 삭제버튼 표시
				list 	+= '<span style="cursor:pointer;position:absolute;top:5px;right:8px;" onclick="deleteComment(\''+comment.comment_doc_no+'\')">X</span>';
			}
			list 	+= '</label>';			
			$("#divCommentList").append(list);
			$("#divCommentList").show();
		});
		$("#divCommentCount").html("댓글 : " + comments.length.toString());
		$("#divCommentCount").show();
	}
	
	var params = getUrlParams(); //common.js안 정의
	if(params.comment_doc_no != null) {
		var comment_doc_no = params.comment_doc_no;
		var offset = $("#comment_"+comment_doc_no).offset(); //선택한 태그의 위치를 반환
		//animate()메서드를 이용해서 선택한 태그의 스크롤 위치를 지정해서 0.4초 동안 부드럽게 해당 위치로 이동함
		$('html, body').animate({scrollTop : offset.top}, 400);
		$("#comment_"+comment_doc_no).css('backgroundColor', '#fff3cd');
	}
}

//댓글 삭제
function deleteComment(comment_doc_no) {
	var params = {};
	params.doc_no = "${board.doc_no}";
	params.project_id = "${board.project_id}";
	params.comment_doc_no = comment_doc_no;
	params.user_id = "${userInfoDTO.user_id}";

	$.ajax({
		url			: "prj_board_report_delete_comment",
		type		: 'POST',		
		contentType : 'application/json; charset:utf-8',
		data 		: JSON.stringify(params),
		dataType 	: 'text',
		success		: function(data) {
			if(data == "success") {
				showCommentList();
			}else{
				alert("댓글삭제에 실패하였습니다. 관리자에 문의하세요.");
			}			
		},
		error: function(xhr, status, error){
			console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
		}
	});
}

$(function(){
	showCommentList(); //댓글 조회
});

</script>
</head>
<body>
<!-- CONTENT -->
<!------------------------------ //개발자 소스 입력 START ------------------------------->
	<div class="container-fluid">
		<div style="height:34px">
			<span class="apptitle">문서 조회</span>
		</div>
		<table width="100%" style="margin-top:10px">
			<tr>
				<td style="text-align:right">
					<c:if test="${(userInfo.user_id eq board.user_id) or (userInfo.user_id eq 'admin')}">
						<button type="button" class="btn btn-dark btn-sm" onclick="callAction('edit','prj_board_report_edit?doc_no=${board.doc_no}&project_id=${board.project_id}')">수정</button>
						<button type="button" class="btn btn-dark btn-sm" onclick="callAction('delete','prj_board_report_delete?doc_no=${board.doc_no}&project_id=${board.project_id}&attach_path=${board.attach_path}')">삭제</button>
					</c:if>
					<button type="button" class="btn btn-dark btn-sm" onclick="closeDoc()">닫기</button>
				</td>
			</tr>
		</table>						
		<table class="table" width="100%" style="margin-top:7px">
			<colgroup>
				<col width="20%"></col>
				<col width="80%"></col>
			</colgroup>
			<tr>
				<th>작성자</th>
				<td>${board.user_name}</td>
			</tr>
			<tr>
				<th>작성일</th>
				<td><fmt:formatDate value="${board.create_date}" type="date" pattern="yyyy-MM-dd hh:mm:ss"/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${board.subject}</td>
			</tr>
			<tr>
				<th>분류</th>
				<td>${board.bd_category_name}</td>
			</tr>
			<c:if test="${board.attach_path ne null}">
				<tr>
					<th>파일첨부</th>
					<td><a href="javascript:popup('/upload/${board.attach_path}',800,600)">${board.attach_name}</a></td>
				</tr>
			</c:if>
			<tr style="height:100px">
				<th>본문</th>
				<td><pre>${board.doc_body}</pre></td>
			</tr>
		</table>
		<!-- 댓글 작성 -->
		<table class="table">
			<tr>
				<td>
					<div class="input-group" style="padding-left:inherit">
						<span class="input-group-text">댓글</span>
						<textarea class="form-control" aria-label="With textarea" name="comment_context" rows="3"></textarea>
					</div>						
				</td>
				<td width="100" valign="bottom">
					<button type="button" class="btn btn-dark btn-sm" onclick="insertComment()">댓글등록</button>
				</td>
			</tr>
		</table>
		<!-- 댓글 조회 -->
		<div id="divCommentCount" style="margin-left:16px"></div>
		<div id="divCommentList" class="list-group p-3 px-md-3"></div>
	</div>
<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>