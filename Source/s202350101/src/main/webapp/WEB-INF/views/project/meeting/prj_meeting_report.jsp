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
	#meetingList {
		padding: 20px;
	}
	#meeting {
		width: 100%;
		padding: 20px;
		text-align: center;
		margin-top: 30px;
	}
	#title {
		width: 100%;
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
<!------------------------------ //개발자 소스 입력 START ----------------------------->
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
</body>
</html>