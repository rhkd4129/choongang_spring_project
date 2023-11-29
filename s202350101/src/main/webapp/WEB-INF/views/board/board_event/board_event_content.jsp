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
	td, th {margin: 80px;}
</style>
<!-- CSS END -->

<!-- JS START -->
<!-- JS END -->

<script type="text/javascript">

	// 추천
	function eventGood(doc_no) {
//		alert("이벤트 추천 doc_no-> " + doc_no);
 		$.ajax({
			url       : 'ajaxEventGoodCount'
		   ,dataType  : 'text'
		   ,data      : {'doc_no' : doc_no}
	 	   ,success	  : function(data) {	
				if (data == "duplication") {
					alert("중복 추천입니다");
				} else if (data == "error") {
					alert("error");
				} else {
					alert("추천되었습니다");
					$('#count_btn').text(data);
				} 
			}
		});
	}

	// 게시글 삭제
	function eventDelete(doc_no, user_id) {
//		alert("글 번호 : " + doc_no);
//		alert("아이디 : "  + user_id);
//		var inputUserId = prompt('회원 아이디를 입력하세요');
	//	if (inputUserId != user_id) {
	//		alert('회원 ID가 올바르지 않습니다');
		//	return;
		//}
		// 아이디 같으므로 삭제
		$.ajax({
			url      : 'ajaxDelete'
		   ,type     : 'post'	
		   ,dataType : 'text'
		   ,data     : {'doc_no' : doc_no}
		   ,success  : function(data) {
			   if (data == 1) {
				   alert('삭제되었습니다');
				   var a = "board_event";
				   window.location.href = a;
			   } else {
				   alert('삭제에 실패했습니다');
			   }
		   }	    
		});
	}
	
	// 댓글 삭제
	function eventComtDelete(doc_no, comment_doc_no, writer_id, current_id){
//		alert('event 댓글 삭제 doc_no: ' + doc_no);
//		alert('event 댓글 삭제 user_id: ' + user_id);
		if (current_id != writer_id) {
			alert("작성자만 삭제 할 수 있습니다.");
			return;
		}
		$.ajax({
			url 		 : 'event_comt_delete',
			type 		 : 'POST',
			dataType 	 : 'text',
			data 		 : {'doc_no':doc_no, 'comment_doc_no':comment_doc_no},
			success		 : function(data){
				if(data == 1){
					alert('삭제되었습니다');
					window.location.href = "event_content?doc_no="+doc_no;
				}
			}
		});
	}
	

	$(function() {	
	    $.ajax({
	        url: '/main_header',
	        dataType: 'html',
	        success: function(data) {
	            $('#header').html(data);	
	        }
	    });
	
	    $.ajax({
	        url: '/main_menu',
	        dataType: 'html',
	        success: function(data) {
	            $('#menubar').html(data);
	        }
	    });
	
	    $.ajax({
	        url: '/main_footer',
	        dataType: 'html',
	        success: function(data) {
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
			
			<h4 class ="pt-4">문서 조회</h4>
			
			<table width="100%" style="margin-top:7px">
				<tr>
					<td style="text-align:right">
						<input type="button" class="btn btn-dark btn-sm" value="목록" onclick="location.href='board_event'">
						
						<c:if test="${result == 1}">
							<input type="button" class="btn btn-dark btn-sm" value="수정" onclick="location.href='event_update?doc_no=${eventContent.doc_no}'">
						</c:if>
						 
						<c:if test="${result == 1}">		
							<input type="button" class="btn btn-dark btn-sm" value="삭제" onclick="eventDelete(${eventContent.doc_no}, '${eventContent.user_id}')">
						</c:if> 
						<button type="button" class="btn btn-dark btn-sm" onclick="eventGood(${eventContent.doc_no})">추천</button>
						<button type="button" class="btn btn-dark btn-sm" onclick="closeDoc()">닫기</button>
					</td>
				</tr>
			</table>
			<table class="table">
				<colgroup>
					<col width="15%"></col>
					<col width="85%"></col>
				</colgroup>
				<tr> <th>글 번호</th>       <td>${eventContent.doc_no}</td> </tr>
				<tr> <th>이름</th>         <td>${eventContent.user_name}</td> </tr>
				<tr> <th>작성일</th>       <td>${eventContent.create_date}</td> </tr>
				<tr> <th>수정일</th>       <td>${eventContent.modify_date}</td> </tr>
				<tr> <th>게시종류</th>      <td>${eventContent.bd_category}</td> </tr>
				<tr> <th>제목</th>        <td>${eventContent.subject}</td> </tr>
				<tr> <th>본문</th>        <td><pre>${eventContent.doc_body}</pre></td> </tr>
				<tr> <th>조회수</th>       <td>${eventContent.bd_count}</td> </tr>
				<tr> <th>추천</th>        <td id="count_btn">${eventContent.good_count}</td> </tr>
				<tr> <th>첨부파일</th>     <td><a href="javascript:popup('/upload/${eventContent.attach_path}',800,600)">${eventContent.attach_name}</a></td> </tr>	
			</table>
			
			<!-- 댓글 등록 -->
			<form action="comtInsert" method="post">
				<input type="hidden" id="doc_no"    name="doc_no"    value="${eventContent.doc_no}">
				<input type="hidden" id="user_id"   name="user_id"   value="${eventContent.user_id}">
				<table class="table">
					<colgroup>
						<col width="15%"></col>
						<col width="65%"></col>
						<col width="20%"></col>
					</colgroup>
					<tr>
						<th>댓글</th>
						<td><textarea  cols="100"  rows="5"    name="comment_context" class="form-control"></textarea></td>
						<td style="vertical-align:bottom"><input type="submit" class="btn btn-dark btn-sm" value="댓글 등록"></td>
					</tr>
				</table>
			</form>

			<table class="table">
				<thead class="table-light">
				<tr>
					<th>번호</th> <th>이름</th> <th>작성일</th> <th>내용</th><th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="comment" items="${commentList}">	
					<tr id="comment${comment.rn}">
						<td>${comment.rn}</td>
						<td>${comment.user_name}</td>
						<td>${comment.create_date}</td>
						<td>${comment.comment_context}</td>
						<td><input type="button" value="댓글 삭제" onclick="eventComtDelete(${comment.doc_no}, ${comment.comment_doc_no}, '${comment.user_id}' ,'${userInfo.user_id}')"></td>
					</tr> 
				</c:forEach>
				</tbody>			
			</table>
			
			
			<!-- 페이징 작업 -->
			<div class="pagebox">
				<c:if test="${page.startPage > page.pageBlock }">
					<a href="event_content?doc_no=${eventContent.doc_no}&currentPage=${page.startPage - page.pageBlock }">[이전]</a>
				</c:if>
				
				<c:forEach var="a" begin="${page.startPage }" end="${page.endPage }">
					<a href="event_content?doc_no=${eventContent.doc_no}&currentPage=${a }">[${a }]</a>
				</c:forEach>
				
				<c:if test="${page.endPage < page.totalPage }">
					<a href="event_content?doc_no=${eventContent.doc_no}&currentPage=${page.startPage + page.pageBlock }">[다음]</a>
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
   


</body>
</html>

