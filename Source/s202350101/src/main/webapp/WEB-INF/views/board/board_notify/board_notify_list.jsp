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
			
			<div class="container-fluid">
				<c:if test="${result == 1}">
					<input type="button" class="btn btn-dark btn-sm" value="작성" onclick="location.href='board_write_insert_form'">
		 		</c:if>
		 		
		 		<!-- 추천수 가장 높은 row 3개 -->
		 		<div style="text-align:center;"><h6><b><추천 게시글></b></h6></div>
		 		<table class="table">
			 		<colgroup>
						<col width="5%"></col><col width="10%"></col><col width="37%"></col><col width="12%"></col>
						<col width="10%"></col><col width="12%"></col><col width="7%"></col><col width="7%"></col>
					</colgroup>	
		 			<thead class="table-light">
		 				<tr>
		 					<th>번호</th><th>게시종류</th><th>제목</th><th>이름</th>       
						    <th>작성일</th><th>수정일</th><th>조회</th><th>추천</th>
		 				</tr>
		 			</thead>
		 			<tbody>
		 				<c:forEach var="good" items="${goodListRow }" varStatus="status">
		 					<tr id="good${status.count }">
		 						<td>${status.count }</td> 
		 						<td>${good.bd_category }</td>
		 						<td class="title"><a href="board_content?doc_no=${good.doc_no}">${good.subject }</a></td>
		 						<td>
		 							${good.user_name }
									<span class="iconChat">
										<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
											<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
											<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
										</svg>
										<input type="hidden" name="chat_id" value="${good.user_id}">
									</span>
		 						</td>
		 						<td>${good.create_date }</td>
		 						<td>${good.modify_date }</td>
		 						<td>${good.bd_count }</td>
		 						<td>${good.good_count }</td>
		 					</tr>
		 				</c:forEach>
		 			</tbody>
		 		</table>
	
		 		
		 		<!-- 전체 리스트 -->
		 		<h6 class="mt-2 pt-2" style="text-align:right">총 건수 : ${totalBdFree}</h6>
				<table class="table">
			 		<colgroup>
						<col width="5%"></col><col width="10%"></col><col width="37%"></col><col width="12%"></col>
						<col width="10%"></col><col width="12%"></col><col width="7%"></col><col width="7%"></col>
					</colgroup>	
					<thead class="table-light">
						<tr>
		 					<th>번호</th><th>게시종류</th><th>제목</th><th>이름</th>       
						    <th>작성일</th><th>수정일</th><th>조회</th><th>추천</th>
						</tr> 
					</thead>
					<tbody>
					<c:forEach var="bdFree" items="${bdFreeList }" varStatus="status">
						<tr id="bdFree${bdFree.rn}"> 
							<td>${bdFree.rn}</td> 
							<td>${bdFree.bd_category}</td>     
							<td class="title"><a href="board_content?doc_no=${bdFree.doc_no}">${bdFree.subject}</a></td>
							<td>
								${bdFree.user_name}
								<span class="iconChat">
									<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
										<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
										<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
									</svg>
									<input type="hidden" name="chat_id" value="${bdFree.user_id}">
								</span>
							</td>     
						    <td>${bdFree.create_date}</td> 
							<td>${bdFree.modify_date}</td>     
							<td>${bdFree.bd_count}</td>        
							<td>${bdFree.good_count}</td> 
						</tr>
					</c:forEach>
					</tbody>
				</table>
				
				
				<!-- 페이징 작업 -->
				<nav aria-label="Page navigation example">
				  <ul class="pagination justify-content-center">

					<c:if test="${page.startPage > page.pageBlock }">
						<li class="page-item"><a class="page-link" href="board_notify?currentPage=${page.startPage - page.pageBlock }" tabindex="-1" aria-disabled="true">이전</a></li>
					</c:if>
					
					<c:forEach var="i" begin="${page.startPage }" end="${page.endPage }">
						<c:choose>
							<c:when test="${page.currentPage==i}"><li class="page-item active"></c:when>
							<c:otherwise><li class="page-item"></c:otherwise>
						</c:choose>
						<a class="page-link" href="board_notify?currentPage=${i}">${i}</a></li>
					</c:forEach>
					
					<c:if test="${page.endPage < page.totalPage }">
						<li class="page-item"><a class="page-link" href="board_notify?currentPage=${page.startPage + page.pageBlock }')">다음</a></li>
					</c:if>
				  </ul>
				</nav>
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

