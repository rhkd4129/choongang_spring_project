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
	
	<h4 class ="pt-4">문서 조회</h4>
	
	<input type="hidden" name="doc_no" value="${qnaContent.doc_no}"> 
	
	<table class="table table-hover">
		<tr> <th>글 번호</th>       <td>${qnaContent.doc_no}</td> </tr>
		<tr> <th>이름</th>         <td>${qnaContent.user_name}</td> </tr>
		<tr> <th>작성일</th>       <td>${qnaContent.create_date}</td> </tr>
		<tr> <th>수정일</th>       <td>${qnaContent.modify_date}</td> </tr>
		<tr> <th>게시종류</th>      <td>${qnaContent.bd_category}</td> </tr>
		<tr> <th>제목</th>         <td>${qnaContent.subject}</td> </tr>
		<tr> <th>본문</th>         <td>${qnaContent.doc_body}</td> </tr>
		<tr> <th>조회수</th>        <td>${qnaContent.bd_count}</td> </tr>
		<tr> <th>추천</th>         <td>${qnaContent.good_count}</td> </tr>
		<tr> <th>첨부파일</th>      <td>${qnaContent.attach_name}<img alt="" src="${pageContext.request.contextPath}/${qnaContent.attach_path}/${qnaContent.attach_name}"></td> </tr>	
		
		
		<tr>
			<td colspan="2">
				<input type="button" value="목록" onclick="location.href='board_qna'"> 
				
			    <c:if test="${result == 1}">
				    <input type="button" value="수정" onclick="location.href='qna_update?doc_no=${qnaContent.doc_no}'"> 
				</c:if>
				
				<c:if test="${result == 1}">
					<input type="button" value="삭제" onclick="ajaxQnaDelete(${qnaContent.doc_no}, '${qnaContent.user_id}' )">
				</c:if> 
			</td>	
		</tr>
	</table>
	
	
	<!-- 추천 -->
	<button class="mb-4" type="button" id="qna_count_btn" onclick="qnaGoodCount(${qnaContent.doc_no})">추천수  ${qnaContent.good_count}</button><p>
	
	
	<!-- 답글 작성 -->
	<%
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpleDateFormat.format(date);
	%> 
	
	 <form action="qna_content_replyInsert" method="post"> 
		<input type="hidden" name="doc_no" 		value="${qnaContent.doc_no }">
		<input type="hidden" name="app_id" 		value="2">
		<input type="hidden" name="user_id" 	value="${userInfoDTO.user_id }">
		<input type="hidden" name="bd_category" value="${qnaContent.bd_category}">
		<input type="hidden" name="attach_name" value="">
		<input type="hidden" name="attach_path" value="">
		<input type="hidden" name="bd_count" 	value="0">
		<input type="hidden" name="good_count" 	value="0">
		<input type="hidden" name="doc_group" 	value="${qnaContent.doc_group}">
		<input type="hidden" name="doc_step" 	value="${qnaContent.doc_step}">
		<input type="hidden" name="doc_indent" 	value="${qnaContent.doc_indent}">
		<input type="hidden" name="alarm_flag" 	value="">
		<input type="hidden" name="parent_doc_no" 	   value="${qnaContent.doc_no}">
		<input type="hidden" name="parent_doc_user_id" value="${qnaContent.user_id}">
	
		<div>답변 작성</div>
		<table class="table table-bordered">
			<tr>
	            <th>작성일</th>
	            <td><%=strDate%></td> 
	        </tr>
	
			<tr>
	            <th>작성자</th>
	            <td>
	               ${userInfoDTO.user_name}
	            </td>
	        </tr>
	        
	        <tr>
	            <th>제목</th>
	            <td>
	                <input type="text" name="subject" class="form-control" value="[답변] ${qnaContent.subject}">
	                <form:errors path="subject" class="error"/>
	            </td>
	        </tr>
	
	        <tr>
	            <th>본문</th>
	            <td>
	                <input type="text" name="doc_body" class="form-control">
	                <form:errors path="doc_body" class="error"/>
	            </td>
	        </tr>
	        
         	<tr>
	            <td colspan="2">
	                <input type="submit" value="등록">
	            </td>
	        </tr>
	        
		</table>
	</form> 
	
	
 		<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>

