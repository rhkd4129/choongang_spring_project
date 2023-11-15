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
	td, th {padding: 15px;}
</style>
<!-- CSS END -->

<!-- JS START -->
<!-- JS END -->

<script type="text/javascript">

	// 추천
	function eventGood(doc_no) {
		alert("이벤트 추천 doc_no-> " + doc_no);
		
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
					$('#event_btn').text("추천수 " + data);
				} 
			}
		});
	}

	// 삭제
	function eventDelete(doc_no, user_id) {
		alert("글 번호 : " + doc_no);
		alert("아이디 : "  + user_id);
		
//		var result = 0;
		var inputUserId = prompt('회원 아이디를 입력하세요');
		if (inputUserId != user_id) {
			alert('회원 ID가 올바르지 않습니다');
			return;
		}
		
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
	    
	    // 이벤트_댓글
	    $("#comment-form").submit(function(e) {
	        e.preventDefault();  			     	// 폼의 기본 동작을 막음	
	        var bdFreeComt = $(this).serialize();   // 데이터를 직렬화 : bdFreeComt --> this = #comment-form
	        console.log(bdFreeComt);
	        alert(bdFreeComt);
	        	$.ajax({
	    			 url      : 'ajaxComt'
	    			,type     : 'POST' 
	    			,dataType : 'json'  
	    			,data     :  bdFreeComt  		     // 보낼 데이터 
	        		,success  :  function(comtSelect) {  // comtSelect : 입력한 댓글 갖고 옴 , data로 작성해도 됨  
	        			console.log(comtSelect);
	    			}
	    		});
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

			<h3>상세 페이지</h3>
			
			<table border="1">
				<tr> <th>글 번호</th>       <td>${eventContent.doc_no}</td> </tr>
				<tr> <th>이름</th>         <td>${eventContent.user_name}</td> </tr>
				<tr> <th>작성일시</th>      <td>${eventContent.create_date}</td> </tr>
				<tr> <th>수정일시</th>      <td>${eventContent.modify_date}</td> </tr>
				<tr> <th>게시종류</th>      <td>${eventContent.bd_category}</td> </tr>
				<tr> <th>제목</th>        <td>${eventContent.subject}</td> </tr>
				<tr> <th>본문</th>        <td>${eventContent.doc_body}</td> </tr>
				<tr> <th>조회수</th>       <td>${eventContent.bd_count}</td> </tr>
				<tr> <th>추천</th>        <td>${eventContent.good_count}</td> </tr>
				<tr> <th>첨부파일명</th>     <td>${eventContent.attach_name}<img alt="" src="${pageContext.request.contextPath}/${eventContent.attach_path}/${eventContent.attach_name}"></td> </tr>	
						
				
				<tr>
					<td colspan="2">
						<input type="button" value="목록" onclick="location.href='board_event'">
						
						<c:if test="${result == 1}">
							<input type="button" value="수정" onclick="location.href='event_update?doc_no=${eventContent.doc_no}'">
						</c:if>
						 
						<c:if test="${result == 1}">
							<input type="button" value="삭제" onclick="eventDelete(${eventContent.doc_no}, '${eventContent.user_id }' )">
						</c:if> 
					</td>
				</tr>
			</table>
			
			
			<!-- 추천 -->
			<button type="button" id="event_btn" onclick="eventGood(${eventContent.doc_no})">추천수  ${eventContent.good_count}</button>
			

			<!-- 댓글 -->
			<form id="comment-form">
					<input type="hidden" id="doc_no"    name="doc_no"    value="${eventContent.doc_no}">
					<input type="hidden" id="user_id"   name="user_id"   value="${eventContent.user_id}">
					
					<input type="text"   name ="comment_context">
					<input type="submit" value="댓글 등록">
			</form>
				
			<table border="1">
				<tr>
					<th>이름</th> <th>작성일시</th> <th>내용</th>
				</tr>
					
				<c:forEach var="comt" items="${comt}">	
					<tr>
						<td>${comt.user_name}</td>
						<td>${comt.create_date}</td>
						<td>${comt.comment_context}</td>
					</tr> 
				</c:forEach>			
			</table>
				

		
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

