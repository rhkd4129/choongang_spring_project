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
	#meeting {
		width: 80%;
		padding: 20px;
		text-align: center;
		margin-top: 30px;
	}
	#title {
		width: 80%;
		text-align: center;
		font-size: 25pt;
	}
	table tr {
		height: 50px;
		border-top: solid gray 1px;
	}
	table td {
		text-align: left;
	    width: 70%;
	    padding-left: 30px;
	}
	.button {
		text-align: center;
	}
	.uploadFile {
		height: 100px;
		padding-right: 20px;
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
	
	function delchk(){
		// 클릭 이벤트 핸들러
	    // 이곳에 버튼이 클릭될 때 실행하고자 하는 코드를 작성합니다.
		var answer = confirm('회의록을 삭제하시겠습니까?')
		
		if (answer) {		// 확인 버튼 누르면 동작
			var meeting_id = ${meeting_id};
			var project_id = ${project_id};
			
			var sendurl = "/prj_meeting_report_delete/?meeting_id=" + meeting_id + "&project_id=" + project_id;

			console.log("sendURL : " + sendurl);
			$.ajax({
				
				url: sendurl,
				dataType: 'json',
				success : function(data) {
					alert("삭제되었습니다");
					location.href="/prj_meeting_calendar";
				}
			});
			
		} else {
			return false;
		}
	};
	
</script>
</head>
<body>

<!-- HEADER -->
<header id="header"></header>

<!-- CONTENT -->
<div class="container-fluid">
	<div class="row">
 		
 		<!-- 메뉴 -->
		<div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary"></div>
		
		<!-- 본문 -->
		<main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
			<!------------------------------ //개발자 소스 입력 START ------------------------------->
			<div id="category_title">
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
				        <a class="link-body-emphasis fw-semibold text-decoration-none" href="/dashboard">프로젝트</a>
				      </li>
				      <li class="breadcrumb-item active" aria-current="page">회의록</li>
				    </ol>
				</nav>
			</div>
			
			<div id="meetingList">
				<label id="title">회의록</label>
				<input type="hidden" name="user_id" value="${user_id }">
				<input type="hidden" name="project_id" value="${project_id }">
				<input type="hidden" name="meeting_id" value="${meeting_id }">
				<table id="meeting">
					<c:forEach items="${meeting }" var="meeting" begin="0" end="0">
						<tr>
							<th>회의제목</th><td>${meeting.meeting_title}</td>
						</tr>
						<tr>
							<th>회의일정</th><td>${meeting.meeting_date}</td>
						</tr>
						<tr>
							<th>회의장소</th><td>${meeting.meeting_place}</td>
						</tr>
					</c:forEach>
					<tr>
						<th>참석자</th>
						<td><c:forEach items="${meeting }" var="meeting">
							 ${meeting.user_name}&nbsp;&nbsp;
						</c:forEach></td>
					</tr>
					<c:forEach items="${meeting }" var="meeting" begin="0" end="0">
						<tr>
							<th>회의유형</th><td>${meeting.meeting_category}</td>
						</tr>
						<tr>
							<th>첨부파일</th>
							<td><c:if test="${meeting.attach_name != null}"><a href="javascript:popup('${pageContext.request.contextPath}/${meeting.attach_path }/${meeting.attach_name}',800,600)">${meeting.attach_name}</a></c:if></td>
						</tr>
						<tr>
							<th>회의내용</th><td>${meeting.meeting_content}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="2" class="button">
						<input type="button" value="목록" onclick="location.href='/prj_meeting_calendar?project_id=${project_id}'">
						<input type="button" value="수정" onclick="location.href='/prj_meeting_report_update?meeting_id=${meeting_id}&project_id=${project_id}'">
						<input type="button" value="삭제" id="deleteChk" onclick="delchk()">
						</td>
					</tr>
				</table>
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