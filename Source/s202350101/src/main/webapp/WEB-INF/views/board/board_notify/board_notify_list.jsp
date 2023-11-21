<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<style type="text/css">
	table {
		width: 100%;
		margin-top: 10px;
	}
	
	.title {
		width: 350px;
	}
	
	td, th, tr {
		padding-left: 20px;
		vertical-align: middle;	
	}
	
	table tr {
		height: 50px;
	}
	
	table th, td {
		text-align: center;
	}
	
	.pagebox {
		margin-top: 10px;
		text-align: center;
	}
</style>
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

<!-- HEADER -->
<header id="header"></header>

<!-- CONTENT -->
<div class="container-fluid">
	<div class="row">
 		
 		<!-- 메뉴 -->
		<div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
		</div>
		
		<!-- 본문 -->
		<main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
			<!------------------------------ //개발자 소스 입력 START ------------------------------->
			<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
			  <symbol id="house-door-fill" viewBox="0 0 16 16">
			    <path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5z"></path>
			  </symbol>
			</svg>		
			<nav aria-label="breadcrumb" style="padding-top:5px;padding-left: calc(var(--bs-gutter-x) * 0.5);">
			    <ol class="breadcrumb breadcrumb-chevron p-1">
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis" href="/main">
			          <svg class="bi" width="16" height="16"><use xlink:href="#house-door-fill"></use></svg>
			          <span class="visually-hidden">Home</span>
			        </a>
			      </li>
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="">전체 게시판</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">전체 공지사항</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">전체 공지사항</span>
				</div>
			</div>
			
			
			<input type="button" class="mt-4 mb-4" value="작성" onclick="location.href='board_write_insert_form'">
	 		
	 		
	 		<!-- 추천수 가장 높은 row 3개 -->
	 		<h5>Best</h5>
	 		<table class="table table-sm">
	 				<tr>
	 					<th>번호</th>      <th>이름</th>      <th>작성일</th> 
						<th>수정일</th>     <th>게시종류</th>    <th>제목</th>        
				        <th>조회수</th>     <th>추천</th>    
	 				</tr>
	 				
	 				<c:forEach var="good" items="${goodListRow }" varStatus="status">
	 					<tr id="good${status.count }">
	 						<td>${status.count }</td> 
	 						<td>${good.user_name }</td>
	 						<td>${good.create_date }</td>
	 						<td>${good.modify_date }</td>
	 						<td>${good.bd_category }</td>
	 						<td class="title"><a href="board_content?doc_no=${good.doc_no}">${good.subject }</a></td>
	 						<td>${good.bd_count }</td>
	 						<td>${good.good_count }</td>
	 					</tr>
	 				</c:forEach>
	 		</table>

	 		
	 		<!-- 전체 리스트 -->
	 		<h5 class="mt-5 pt-3">Count  ${totalBdFree}</h5>
			<table class="table table-sm"> 
				<tr>
					<th>번호</th>      <th>이름</th>      <th>작성일</th> 
					<th>수정일</th>    <th>게시종류</th>    <th>제목</th>        
			        <th>조회수</th>     <th>추천</th>    
				</tr> 
				
				<c:forEach var="bdFree" items="${bdFreeList }" varStatus="status">
					<tr id="bdFree${bdFree.rn}"> 
						<td>${bdFree.rn}</td> 
						<td>${bdFree.user_name}</td>     
					    <td>${bdFree.create_date}</td> 
						<td>${bdFree.modify_date}</td>     
						<td>${bdFree.bd_category}</td>     
						<td class="title"><a href="board_content?doc_no=${bdFree.doc_no}">${bdFree.subject}</a></td>
						<td>${bdFree.bd_count}</td>        
						<td>${bdFree.good_count}</td> 
					</tr>
				</c:forEach>
			</table>
			
			
			<!-- 페이징 작업 -->
			<div class="pagebox">
				<c:if test="${page.startPage > page.pageBlock }">
					<a href="board_notify?currentPage=${page.startPage - page.pageBlock }">[이전]</a>
				</c:if>
				
				<c:forEach var="a" begin="${page.startPage }" end="${page.endPage }">
					<a href="board_notify?currentPage=${a }">[${a }]</a>
				</c:forEach>
				
				<c:if test="${page.endPage < page.totalPage }">
					<a href="board_notify?currentPage=${page.startPage + page.pageBlock }">[다음]</a>
				</c:if>
			</div>
			
	  		<!------------------------------ //개발자 소스 입력 END ------------------------------->
		</main>		
	</div>
</div>

<!-- FOOTER -->
<footer class="footer py-2">
  <div id="footer" class="container">
  </div>
</footer>


    
    
</body>
</html>

