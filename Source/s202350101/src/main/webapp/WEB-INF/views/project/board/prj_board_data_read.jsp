<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<style>

.prjlistbox {
	--bs-alert-color: var(--bs-primary-text-emphasis);
    --bs-alert-bg: var(--bs-primary-bg-subtle);
    
    --bs-alert-padding-x: 1rem;
    --bs-alert-padding-y: 1rem;
    --bs-alert-margin-bottom: 1rem;
    --bs-alert-border-color: transparent;
    --bs-alert-border: var(--bs-border-width) solid var(--bs-alert-border-color);
    --bs-alert-border-radius: var(--bs-border-radius);
    --bs-alert-link-color: inherit;
    position: relative;
    padding: var(--bs-alert-padding-y) var(--bs-alert-padding-x);
    margin-bottom: var(--bs-alert-margin-bottom);
    color: var(--bs-alert-color);
    background-color: var(--bs-alert-bg);
    border: var(--bs-alert-border);
    border-radius: var(--bs-alert-border-radius);
}
</style>
<!-- CSS END -->

<!-- JS START -->
<script type="text/javascript" src="/project/board/js/prj_board_data.js"></script>
<!-- JS END -->

<script type="text/javascript">

//---------------------------------------------------------------------
//추천자목록
function showGoodList(pDocNo, pProjectID) {
	var params = {doc_no : pDocNo, project_id : pProjectID};
	$.ajax({
		url			: 'prj_board_data_good_list',
		type		: 'POST',
		contentType : 'application/json; charset:utf-8',
		data 		: JSON.stringify(params),
		dataType 	: 'json',
		success		: function(data) {
			//--------------------
			drawGoodList(data);
			//--------------------
		},
		error: function(xhr, status, error){
			console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
		}
	});
}

//추천자 정보 그려주기
function drawGoodList(users){
	$("#idGoodList").empty();
	if(users.length==0){
		alert("추천자 정보가 없습니다.");
		$("#divGoodList").hide();
	}
	else{
		$(users).each(function(index, user){
			var list = '<span style="margin-right:5px">'+user.user_name+'</span>';
			$("#idGoodList").append(list);
			$("#divGoodList").show();
		});	
	}
}

function hideGoodList() {
	$("#divGoodList").hide();
}

//---------------------------------------------------------------------
//댓글등록
function insertComment() {
	var params = {};
	params.doc_no = "${board.doc_no}";
	params.project_id = "${board.project_id}";
	params.user_id = "${userInfoDTO.user_id}";	
	params.comment_context = $('textarea[name=comment_context]').val();

	$.ajax({
		url			: "prj_board_data_insert_comment",
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
		url			: 'prj_board_data_comment_list',
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
			var list = '<label class="list-group-item d-flex gap-2">';
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
}

//댓글 삭제
function deleteComment(comment_doc_no) {
	var params = {};
	params.doc_no = "${board.doc_no}";
	params.project_id = "${board.project_id}";
	params.comment_doc_no = comment_doc_no;
	params.user_id = "${userInfoDTO.user_id}";

	$.ajax({
		url			: "prj_board_data_delete_comment",
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
						<div id="idDocTitle" style="margin-top:20px;height:34px">
							<h3>문서 조회</h3>
						</div>
						<table width="100%" style="margin-top:10px;">
							<tr>
								<td style="text-align:right">
									<button type="button" class="btn btn-secondary btn-sm" onclick="callAction('write','prj_board_data_write?parent_doc_no=${board.doc_no}&project_id=${board.project_id}')">답변작성</button>
									<button type="button" class="btn btn-secondary btn-sm" onclick="callAction('edit','prj_board_data_edit?doc_no=${board.doc_no}&project_id=${board.project_id}')">수정</button>
									<button type="button" class="btn btn-secondary btn-sm" onclick="callAction('delete','prj_board_data_delete?doc_no=${board.doc_no}&project_id=${board.project_id}&attach_path=${board.attach_path}')">삭제</button>
									<button type="button" class="btn btn-secondary btn-sm" onclick="callAction('good','prj_board_data_good?doc_no=${board.doc_no}&project_id=${board.project_id}')">추천</button>
									<button type="button" class="btn btn-secondary btn-sm" onclick="closeDoc()">닫기</button>
								</td>
							</tr>
						</table>						
						<table class="table" width="100%" style="margin-top:7px">
							<colgroup>
								<col width="20%"></col>
								<col width="80%"></col>
							</colgroup>
							<tr> 
								<td>작성자</td>
								<td>${board.user_name}</td>
							</tr>
							<tr>
								<td>작성일</td>
								<td><fmt:formatDate value="${board.create_date}" type="date" pattern="yyyy-MM-dd hh:mm:ss"/></td>
							</tr>
							<c:if test="${board.parent_doc_no ne ''}">
							<tr>
								<td>원글제목</td>											 
								<td><a href="javascript:callAction('read','prj_board_data_read?doc_no=${board.parent_doc_no}&project_id=${board.project_id}')">${board.parent_doc_subject}</a></td>
							</tr>
							</c:if>
							<tr>
								<td>제목</td>
								<td>${board.subject}</td>
							</tr>
							<c:if test="${board.notify_flag eq 'Y'}">
								<tr>
									<td>공지여부</td>
									<td>공지</td>
								</tr>
							</c:if>
							<tr>
								<td>분류</td>
								<td>${board.bd_category_name}</td>
							</tr>
							<c:if test="${board.attach_path ne null}">
								<tr>
									<td>파일첨부</td>
									<td><a href="javascript:popup('/upload/${board.attach_path}',800,600)">${board.attach_name}</a></td>
								</tr>
							</c:if>
							<tr>
								<td>조회수</td>
								<td>${board.bd_count}</td>
							</tr>
							<tr>
								<td>추천수</td>
								<td>
									<a href="javascript:showGoodList('${board.doc_no}','${board.project_id}')">${board.good_count}</a>
									<c:if test="${board.good_count > 0}">
										<span style="margin-left:10px"><a href="javascript:showGoodList('${board.doc_no}','${board.project_id}')"><img src="/common/images/icon_person.gif"></a></span>
									</c:if>
									<!-- 추천자 목록 조회 -->
									<div id="divGoodList" style="display:none;margin-top:5px;" class="prjlistbox">
										<div id="idGoodList"></div>
										<span style="cursor:pointer;position:absolute;top:5px;right:8px;" onclick="hideGoodList()">X</span>
									</div>
								</td>
							</tr>
							<tr style="height:100px">
								<td>본문</td>
								<td>${board.doc_body}</td>
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
									<button type="button" class="btn btn-secondary btn-sm" onclick="insertComment()">댓글등록</button>
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