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
		padding: 10px;	
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
		
		//분류별 보기 검색
		$("#bd_category_selectbox").change(function(){
			//alert("change~~~");
			document.frmQnaSearch.submit();
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
			<h3>QNA 게시판</h3><p>
			
			<input type="button" value="QNA 작성" onclick="location.href='qna_insert_form'">
	 		
	 		
	 		<!-- 검색 -->
	 		<h5>Q&A CATEGORY SEARCH</h5>
	 		<form name="frmQnaSearch" action="board_qna">
		 		<table border="1">
			 		<tr>
			 			<td>
			 				<select id="bd_category_selectbox" name="keyword">
				 				<option value="ALL">전체</option>
					 				<c:forEach var="code" items="${codeList}">
					 					<option value="${code.cate_code}">${code.cate_name}</option>
					 				</c:forEach>
				 			</select>
			 			</td>
			 		</tr>	
		 		</table>
	 		</form>
	 		
	 		
	 		<!-- 추천수 가장 높은 row 3개 -->
	 		<h5>추천수 가장 높은 글</h5>
	 		<table border="1">
	 				<tr>
	 					<th>번호</th>      <th>이름</th>      <th>작성일시</th> 
						<th>수정일시</th>   <th>질문종류</th>    <th>제목</th>        
				        <th>조회수</th>     <th>추천</th>    
	 				</tr>
	 				
	 				<c:forEach var="qnaRow" items="${qnaRow }" varStatus="status">
	 					<tr id="qnaRow${status.count }">
	 						<td>${status.count }</td> 
	 						<td>${qnaRow.user_name }</td>
	 						<td>${qnaRow.create_date }</td>
	 						<td>${qnaRow.modify_date }</td>
	 						<td>${qnaRow.bd_category_name }</td>
	 						<td class="title"><a href="qna_content?doc_no=${qnaRow.doc_no}">${qnaRow.subject}</a></td>
	 						<td>${qnaRow.bd_count }</td>
	 						<td>${qnaRow.good_count }</td>
	 					</tr>
	 				</c:forEach>
	 		</table>

	 	
	 		<!-- 전체 리스트 -->
	 		<h5>Count : ${qnaTotalCount}</h5><p>
			<table border="1">  
				<tr>
					<th>번호</th>      <th>이름</th>      <th>작성일시</th> 
					<th>수정일시</th>   <th>질문종류</th>    <th>제목</th>        
			        <th>조회수</th>     <th>추천</th>    
				</tr> 
				
				<c:if test="${qnaTotalCount > 0}">
				<c:forEach var="qnaList" items="${qnaList}" varStatus="status">
					<tr id="qnaList${qnaList.rn}"> 
						<td>${qnaList.rn}</td> 
						<td>${qnaList.user_name}</td>     
					    <td>${qnaList.create_date}</td> 
						<td>${qnaList.modify_date}</td>     
						<td>${qnaList.bd_category_name}</td>     
						<td class="title">
							<c:forEach begin="1" end="${qnaList.doc_indent}">-</c:forEach>
							<a href="qna_content?doc_no=${qnaList.doc_no}">${qnaList.subject}</a>
						</td>
						<td>${qnaList.bd_count}</td>        
						<td>${qnaList.good_count}</td> 
					</tr>
				</c:forEach>
				</c:if>
			</table>
			
			
			<!-- 페이징 작업 -->
			<div class="pagebox">
				<c:if test="${page.startPage > page.pageBlock }">
					<a href="board_qna?currentPage=${page.startPage - page.pageBlock }">[이전]</a>
				</c:if>
				
				<c:forEach var="a" begin="${page.startPage }" end="${page.endPage }">
					<a href="board_qna?currentPage=${a }">[${a }]</a>
				</c:forEach>
				
				<c:if test="${page.endPage < page.totalPage }">
					<a href="board_qna?currentPage=${page.startPage + page.pageBlock }">[다음]</a>
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

