<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %> 
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
	  		<div class="container-fluid">
					<p>
					<h3>내가 쓴 댓글 </h3>
					<%-- <h3>내가 쓴 Q&A 게시글 : ${totalComt } 개</h3>
					<h3>내가 쓴 공용 게시글 : ${totalBdFree } 개</h3>
					<h3>내가 쓴 PJ& 공지자료 게시글 : ${totalDtPrj } 개</h3>
					<h3>내가 쓴 업무보고 게시글 : ${totalRepPrj } 개</h3> --%>
					<p>
					<table width="100%" style="margin-top:20px;height:45px">
						<tr>
							<td align="right">
								<form action="mypost_comment_list">
									<table>
										<tr>
											<td>
												<select class="form-select" name="search" style="font-size:0.8rem">
													<c:forEach var="code" items="${search_codelist}">
														<option value="${code.cate_code}">${code.cate_name}</option>
													</c:forEach>
												</select>
											</td>
											<td><input type="text" class="form-control me-2" style="font-size:0.8rem" name="keyword" placeholder="검색어를 입력하세요" required="required"></td>
											<td><button type="submit" class="btn btn-primary btn-sm">검색</button></td>
										</tr>
									</table>
								</form>	
							</td>
						</tr>
					</table>						
					<table width="100%">
						<tr>
							<td width="*" style="text-align:right">
								<c:if test="${not empty keyword}">								
									<a href="mypost_comment_list"><img src="/common/images/btn_icon_delete2.png" width="18" height="19" style="vertical-align:bottom"></a> 
									검색어( <c:forEach var="code" items="${search_codelist}"><c:if test="${code.cate_code == search}">${code.cate_name}</c:if></c:forEach> = ${keyword} ) 
									<img src="/common/images/icon_search.png" width="14" height="14" style="vertical-align:bottom"> 검색 건수
								</c:if>
								<c:if test="${keyword eq null}">총 건수</c:if>
								 : ${totalComt}
							</td>
						</tr>
					</table>
					
					<table class="table table-hover">
						<thead>
							<tr>
								<td>게시판</td>
								<td>댓글내용</td>
								<td>작성일</td>
							</tr>
						</thead>
						<tbody>
							<!-- <tr onclick="goto('project_board_data_read.html')"> -->
							<tr>	
								<c:forEach var="allComt" items="${selectAllComt }">
									<c:choose>
										<c:when test="${allComt.app_id == 1}">
											<c:if test="${allComt.bd_category == '공지'}">
												<tr>
													<td>${allComt.bd_category }</td>
													<td><a href="board_content?doc_no=${allComt.doc_no }&comment_doc_no=${allComt.comment_doc_no}">${allComt.comment_context }</a></td>
													<td>${allComt.create_date }</td>
												</tr>
											</c:if>
											<c:if test="${allComt.bd_category == '자유'}">
												<tr>
													<td>${allComt.bd_category }</td>
													<td><a href="free_content?doc_no=${allComt.doc_no }&comment_doc_no=${allComt.comment_doc_no}">${allComt.comment_context }</a></td>
													<td>${allComt.create_date }</td>
												</tr>
											</c:if>
											<c:if test="${allComt.bd_category == '이벤트'}">
												<tr>
													<td>${allComt.bd_category }</td>
													<td><a href="event_content?doc_no=${allComt.doc_no }&comment_doc_no=${allComt.comment_doc_no}">${allComt.comment_context }</a></td>
													<td>${allComt.create_date }</td>
												</tr>
											</c:if>
										</c:when>
										<c:when test="${allComt.app_id == 2}">
											<tr>
												<td>${allComt.app_name }</td>
												<td><a href="bdQnaContent?doc_no=${allComt.doc_no }#${allComt.comment_doc_no}&comment_doc_no=${allComt.comment_doc_no}">${allComt.comment_context }</a></td>
												<td>${allComt.create_date }</td>
											</tr>
										</c:when>
										<c:when test="${allComt.app_id == 3}">
											<tr>
												<td>${allComt.app_name }</td>
												<td><a href="javascript:popup('prj_board_data_read?doc_no=${allComt.doc_no}&project_id=${allComt.project_id}&comment_doc_no=${allComt.comment_doc_no}')">${allComt.comment_context }</a></td>
												<td>${allComt.create_date }</td>
											</tr>
										</c:when>
										<c:when test="${allComt.app_id == 4}">
											<tr>
												<td>${allComt.app_name }</td>
												<td><a href="prj_board_report_read?doc_no=${allComt.doc_no }&project_id=${allComt.project_id}&comment_doc_no=${allComt.comment_doc_no}">${allComt.comment_context }</a></td>
												<td>${allComt.create_date }</td>
											</tr>
										</c:when>
									</c:choose>
								</c:forEach>
							</tr>
						</tbody>
					</table>
					
					<!-- 페이징 작업 -->
					<%-- <c:if test="${page.startPage > page.pageBlock }">
						<a href="mypost_comment_list?currentPage=${page.startPage - page.pageBlock }">[이전]</a>
					</c:if>
					
					<c:forEach var="a" begin="${page.startPage }" end="${page.endPage }">
						<a href="mypost_comment_list?currentPage=${a }">[${a }]</a>
					</c:forEach>
					
					<c:if test="${page.endPage < page.totalPage }">
						<a href="mypost_comment_list?currentPage=${page.startPage + page.pageBlock }">[다음]</a>
					</c:if> --%>
					
					<c:if test="${page.startPage > page.pageBlock}">
					   	<li class="page-item disabled"><a class="page-link" href="javascript:gotoPage('${page.startPage-page.pageBlock}')" tabindex="-1" aria-disabled="true">Previous</a></li>
					</c:if>
				    <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
						<c:choose>
							<c:when test="${page.currentPage==i}"><li class="page-item active"></c:when>
							<c:otherwise><li class="page-item"></c:otherwise>
						</c:choose>
						<a class="page-link" href="javascript:gotoPage('${i}')">${i}</a></li>
					</c:forEach>						
				    <c:if test="${page.endPage > page.totalPage}">
				    	<li class="page-item"><a class="page-link" href="javascript:gotoPage('${page.startPage+page.pageBlock}')">Next</a></li>
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

<!-- color-modes -->
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
      <symbol id="check2" viewBox="0 0 16 16">
        <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
      </symbol>
      <symbol id="circle-half" viewBox="0 0 16 16">
        <path d="M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z"/>
      </symbol>
      <symbol id="moon-stars-fill" viewBox="0 0 16 16">
        <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z"/>
        <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>
      </symbol>
      <symbol id="sun-fill" viewBox="0 0 16 16">
        <path d="M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
      </symbol>
    </svg>

    <div class="dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle">
      <button class="btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center"
              id="bd-theme"
              type="button"
              aria-expanded="false"
              data-bs-toggle="dropdown"
              aria-label="Toggle theme (auto)">
        <svg class="bi my-1 theme-icon-active" width="1em" height="1em"><use href="#circle-half"></use></svg>
        <span class="visually-hidden" id="bd-theme-text">Toggle theme</span>
      </button>
      <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="bd-theme-text">
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="light" aria-pressed="false">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#sun-fill"></use></svg>
            Light
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="dark" aria-pressed="false">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#moon-stars-fill"></use></svg>
            Dark
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center active" data-bs-theme-value="auto" aria-pressed="true">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#circle-half"></use></svg>
            Auto
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
      </ul>
    </div>
    
</body>
</html>