<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
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

			<!------------------------------ //개발자 소스 입력 START ------------------------------->
		<div class="container-fluid">
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
							<td><c:if test="${meeting.attach_name != null}"><a href="javascript:popup('${pageContext.request.contextPath}/upload/${meeting.attach_path}',800,600)">${meeting.attach_name}</a></c:if></td>
						</tr>
						<tr>
							<th>회의내용</th><td><pre>${meeting.meeting_content}</pre></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="2" class="button">
							<button type="button" class="btn btn-dark btn-sm" onclick="window.close()">닫기</button>
<%-- 						<input type="button" value="목록" onclick="location.href='/prj_meeting_calendar?project_id=${project_id}'">
						<input type="button" value="수정" onclick="location.href='/prj_meeting_report_update?meeting_id=${meeting_id}&project_id=${project_id}'">
						<input type="button" value="삭제" id="deleteChk" onclick="delchk()"> --%>
						</td>
					</tr>
				</table>
			</div>
		</div>
	  		<!------------------------------ //개발자 소스 입력 END ------------------------------->

    
</body>
</html>