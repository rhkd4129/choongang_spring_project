<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<style type="text/css">
	td, th {margin: 80px;}
</style>
<!-- CSS END -->

<!-- JS START -->
<!-- JS END -->

<script type="text/javascript">

	// 추천
	function qnaGoodCount(doc_no) {
		alert("qna 추천 doc_no: " + doc_no);
		$.ajax({
			 url       : 'ajaxQnaGoodCount'
			,dataType  : 'text'
			,data      : {'doc_no' : doc_no}
			,success   : function(data) {
				if (data == "duplication") {
					alert("추천 중복입니다");
				} else if (data == "error") {
					alert("error");
				} else {
					alert("추천되었습니다");
					$('#qna_count_btn').text("추천수 " + data);
				}
			}
		});
	}
	
	// 삭제 
	function ajaxQnaDelete(doc_no, user_id){
		alert("qna 삭제 doc_no-> " + doc_no);
		alert("qna 삭제 user_id-> " + user_id);
		
		var inputUserId = prompt('회원 아이디를 입력하세요');
		if (inputUserId != user_id){
			alert('회원 ID가 올바르지 않습니다');
			return;
		}
		
		// 아이디 같으므로 삭제
		$.ajax({
			url 		: 'ajax_qna_delete',
			type		: 'POST',
			dataType 	: 'text',
			data 		: {'doc_no' : doc_no},
			success		: function(data){
				if(data == 1){
					alert('삭제되었습니다');
					var a = 'board_qna';
					window.location.href = a;
				} else {
					alert('삭제에 실패했습니다');
				}
			}
		});
	}
 	
</script>
</head>
<body>
<!------------------------------ //개발자 소스 입력 START ------------------------------->
	<input type="hidden" name="doc_no" value="${qnaContent.doc_no}"> 

	<div class="container-fluid">
		<div style="height:34px">
			<span class="apptitle">문서 조회</span>
		</div>
		<table width="100%" style="margin-top:10px">
			<tr>
				<td style="text-align:right">
					<button type="button" class="btn btn-dark btn-sm" onclick="closeDoc()">닫기</button>
				</td>
			</tr>
		</table>
		<table class="table">
			<colgroup>
				<col width="15%"></col>
				<col width="85%"></col>
			</colgroup>
			<tr> <th>글 번호</th>       <td>${qnaContent.doc_no}</td> </tr>
			<tr> <th>이름</th>         <td>${qnaContent.user_name}</td> </tr>
			<tr> <th>작성일</th>       <td>${qnaContent.create_date}</td> </tr>
			<tr> <th>수정일</th>       <td>${qnaContent.modify_date}</td> </tr>
			<tr> <th>게시종류</th>      <td>${qnaContent.bd_category}</td> </tr>
			<tr> <th>제목</th>         <td>${qnaContent.subject}</td> </tr>
			<tr> <th>본문</th>         <td>${qnaContent.doc_body}</td> </tr>
			<tr> <th>조회수</th>        <td>${qnaContent.bd_count}</td> </tr>
			<tr> <th>추천</th>         <td>${qnaContent.good_count}</td> </tr>
			<tr> <th>첨부파일</th>     <td><a href="javascript:popup('/upload/${qnaContent.attach_path}',800,600)">${qnaContent.attach_name}</a></td> </tr>	
		</table>
	</div>	
<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>

