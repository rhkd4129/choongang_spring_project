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
<script type="text/javascript">
	function closeDoc() {
	   if(opener) {
	      opener.location.reload();
	      window.close();
	   }else{
	      location.reload();
	   }
	}
</script>
<!-- JS END -->

<script type="text/javascript">
	$(function() {
		
		$.ajax({
			url			: '/main_header',
			dataType 	: 'html',
			success		: function(data) {
				$('#header').html(data);
			}
		});
		
		$.ajax({
			url			: '/main_menu',
			dataType 	: 'html',
			success		: function(data) {
				$('#menubar').html(data);
			}
		});
	
		$.ajax({
			url			: '/main_footer',
			dataType 	: 'html',
			success		: function(data) {
				$('#footer').html(data);
			}
		});
	});
</script>
</head>
<body>
<!------------------------------ //개발자 소스 입력 START ----------------------------->
			<h4 class ="pt-4">문서 조회</h4>
			
			<table width="100%" style="margin-top:10px;">
				<tr>
					<td style="text-align:right">
						<button type="button" class="btn btn-secondary btn-sm" onclick="closeDoc()">닫기</button>
					</td>
				</tr>
			</table>
			
			<table class="table table-hover">
				<tr> <th>글 번호</th>       <td>${notifyContent.doc_no}</td> </tr>
				<tr> <th>이름</th>         <td>${notifyContent.user_name}</td> </tr>
				<tr> <th>작성일</th>      <td>${notifyContent.create_date}</td> </tr>
				<tr> <th>수정일</th>      <td>${notifyContent.modify_date}</td> </tr>
				<tr> <th>게시종류</th>      <td>${notifyContent.bd_category}</td> </tr>
				<tr> <th>제목</th>        <td>${notifyContent.subject}</td> </tr>
				<tr> <th>본문</th>        <td>${notifyContent.doc_body}</td> </tr>
				<tr> <th>조회수</th>       <td>${notifyContent.bd_count}</td> </tr>
				<tr> <th>추천</th>        <td>${notifyContent.good_count}</td> </tr>
				<tr> <th>첨부파일</th>     <td>${notifyContent.attach_name}<img alt="" src="${pageContext.request.contextPath}/${notifyContent.attach_path}/${notifyContent.attach_name}"></td> </tr>	
			</table>
			
			<div>댓글</div>				
			<table class="table table-sm">
				<tr>
					<th>이름</th> <th>작성일</th> <th>내용</th>
				</tr>
				<c:forEach var="comt" items="${freeComtList}">
					<tr>
						<td>${comt.user_name}</td>
						<td>${comt.create_date}</td>
						<td>${comt.comment_context}</td>
					</tr>
				</c:forEach>
			</table>

<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>