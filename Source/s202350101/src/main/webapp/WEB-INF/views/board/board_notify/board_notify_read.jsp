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
		<table class="table table-hover">
			<colgroup>
				<col width="15%"></col>
				<col width="85%"></col>
			</colgroup>
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
	</div>
<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>