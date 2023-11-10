<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<style type="text/css">
	div #calendar {
		width: 80%;
		margin-top: 30px;
	}
	
	#center {
		display: flex;
	}
	
	#meetingList {
		padding: 30px;
	}
	
	#meeting {
		width: 80%;
		padding: 20px;
		text-align: left;
		padding-top: 20px;
	}
	
	table tr {
		height: 50px;
	}
	
	#title {
		width: 80%;
		text-align: center;
		font-size: 25pt;
	}
	
	.list_date {
		padding-top: 20px;
		font-size: 18pt;
		border-bottom: solid gray 1px;
	}
	
	.list_title {
		font-size: 19pt;
	}
	
	.list_title a {
		text-decoration: none;
		color: black;
	}
	
	.radio {
		margin-left: 30px;
	}
	
	select {
		width: 100%;
	}
	
	.delBtn {
		text-align: left;
	}
</style>


<!-- CSS END -->

<!-- JS START -->
<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.9/index.global.min.js'></script>
<!-- JS END -->

<script type="text/javascript">
	$(function() {

		$.ajax({
			url : '/main_header',
			dataType : 'html',
			success : function(data) {
				$('#header').html(data);
			}
		});

		$.ajax({
			url : '/main_menu',
			dataType : 'html',
			success : function(data) {
				$('#menubar').html(data);
			}
		});

		$.ajax({
			url : '/main_footer',
			dataType : 'html',
			success : function(data) {
				$('#footer').html(data);
			}
		});
	});

	// fullcalendar
	document.addEventListener('DOMContentLoaded', function() {
		var calendarEl = document.getElementById('calendar');

		var meetingDateEvents = [];

		// Iterate over the meetings using JSTL				meetingDateList
		<c:forEach var="meeting" items="${meetingDateList}">			//회의 일정		1이 노란색
			meetingDateEvents.push({
				title : '${meeting.meeting_title}',
				start : '${meeting.meeting_date}',
				end : '${meeting.meeting_date}',
				color : '#F2CB61',
				id : '${meeting.meeting_id}'
			});
		</c:forEach>
		
		var meetingEvents = [];
		
		<c:forEach var="meeting" items="${meetingList}">				//회의록			2,3 보라
			meetingEvents.push({
				title : '${meeting.meeting_title}',
				start : '${meeting.meeting_date}',
				end : '${meeting.meeting_date}',
				color : '#B5B2FF',
				id : '${meeting.meeting_id}'
			});
		</c:forEach>

		// console.log("meetingEvents: "+meetingEvents);
		// console.log("meetingDateEvents: " +meetingDateEvents);
		
		var calendar = new FullCalendar.Calendar(calendarEl, {
			initialView : 'dayGridMonth',
			selectable : true,
			events : meetingEvents.concat(meetingDateEvents),

			dateClick : function(info) {
				console.log("clicked date : " + info.dateStr);
			},

			headerToolbar : {
				left : 'addEventButton meetingListButton', // headerToolbar 왼쪽에 커스텀 버튼 추가
				center : 'title'
			},

			customButtons : {
				addEventButton : {
					text : '회의일정 등록',
					click : function() { // 버튼 클릭 시 이벤트 추가
						$("#calendarModal").modal("show"); // modal 나타내기
					}
				},

				meetingListButton: {
				    text: '회의록',
				    click: function() {
				    	var project_id = ${project_id};
				        var sendurl = "prj_meeting_report_list_ajax/?project_id=" + ${project_id}; // project_id를 적절히 정의해야 합니다.
				        console.log("location.href = 'prj_meeting_report_list?project_id=" + project_id + "'");
				        console.log("sendURL : " + sendurl);

				        $.ajax({
				            url: sendurl,					// 이동할 페이지
				            dataType: 'json',				// return 데이터 타입
				            success: function(jsondata) {	// 성공 시 실행
				                var meetingList = $('#meeting');
				                var meetingList_body = $('#meetingList');

				                meetingList.empty();

				                // jQuery로 스타일 조작
				                if (meetingList_body.css('display') == 'none') {
				                	meetingList_body.show();
				                } else {
				                	meetingList_body.hide();
				                }

				                $.each(jsondata, function(index, MTList) {
				                    console.log("MTList: " + MTList.meeting_title);
				                    console.log("MTList: " + MTList.meeting_date);

				                    var authOptionBox = $('<div><p class="list_date">' + MTList.meeting_date + '</p><p class="list_title"><a href="prj_meeting_report_read/?meeting_id=' + MTList.meeting_id + '&project_id=' + MTList.project_id + '">' + MTList.meeting_title + '</a></p></div>');

				                    meetingList.append(authOptionBox);
				                });
				            }
				        });
				    }
				}
			},
			
			eventClick: function(meetingDateEvents) {
			    const element = meetingDateEvents.el; // 클릭한 이벤트 요소
			    const backgroundColor = window.getComputedStyle(element).backgroundColor; // 배경색 가져오기

			    var eventId = meetingDateEvents.event.id;
			    
			    /* alert("backgroundColor"+backgroundColor); */
			    // console.log("클릭한 곳의 배경색: " + backgroundColor);
				console.log("클릭한 곳의 id: " + eventId);
			    
			    if (backgroundColor == "rgb(242, 203, 97)") {
			    	
			    	var project_id = ${project_id};
			    	var sendurl = "/prj_meeting_date_select/?meeting_id=" + eventId + "&project_id=" + project_id;
			    	console.log("sendurl: " + sendurl);
			    	
			    	$.ajax({
			    		url: sendurl,
			    		dataType: 'json',
			    		success: function(data) {
			    			// console.log(data);
			    			
			    			var firList = data.firList;				// 회의일정 
 			    			var firstData = firList[0]; 			// firList의 첫 번째 항목 가져오기
							console.log(firList);
 			    			
			    			var meeting_title = firstData.meeting_title;		// firstData의 meeting_title
			    			var meeting_date = firstData.meeting_date;			// firstData의 meeting_date
			    			var meeting_place = firstData.meeting_place;		// firstData의 meeting_place
			    			var meeting_content = firstData.meeting_content;	// firstData의 meeting_content
			    			var meeting_category = firstData.meeting_category;	// firstData의 meeting_category
			    			var meeting_id = firstData.meeting_id;
			    			
			    			var secList = data.secList;				// 프로젝트 팀원 목록
			    			console.log(secList);
			    			
			    			var meetuserList = $('#meetuserList');
			    			meetuserList.empty();
			    			var authOptionBox = '';
			    			
			    			$.each(secList, function(index, prjMemList) {
			    				var result = 0;
			    				
			    				$.each(firList, function(index, meetingList) {
			    					if (meetingList.meetuser_id == prjMemList.user_id) {
			    						result += 1;
			    					}
			    				});
			    				
			    				if (result > 0) {
			    					authOptionBox += '<input type="checkbox" id="meetuser_id" name="meetuser_id" value="' + prjMemList.user_id + '" checked> ' + prjMemList.user_name + '<br>';
			    				} else {
			    					authOptionBox += '<input type="checkbox" id="meetuser_id" name="meetuser_id" value="' + prjMemList.user_id + '"> ' + prjMemList.user_name + '<br>';
			    				}
			    				console.log("prjMemList, result: "+prjMemList.user_id +""+ result);
			                });

		    				meetuserList.append(authOptionBox);
		    				
			    			
			    			var md_category = $('#md_category');
			    			md_category.empty();
			    			var category = '';
			    			
			    			if (meeting_category == "킥오프미팅") {
			    				category += '<option value="킥오프미팅" selected>킥오프미팅</option>';
			    			} else {
			    				category += '<option value="킥오프미팅">킥오프미팅</option>';
			    			}
			    			
			    			if (meeting_category == "주간 업무보고") {
			    				category += '<option value="주간 업무보고" selected>주간 업무보고</option>';
			    			} else {
			    				category += '<option value="주간 업무보고">주간 업무보고</option>';
			    			}
			    			
			    			if (meeting_category == "월간 회의") {
			    				category += '<option value="월간 회의" selected>월간 회의</option>';
			    			} else {
			    				category += '<option value="월간 회의">월간 회의</option>';
			    			}
			    			
			    			if (meeting_category == "내부 회의") {
			    				category += '<option value="내부 회의" selected>내부 회의</option>';
			    			} else {
			    				category += '<option value="내부 회의">내부 회의</option>';
			    			}
			    			
			    			if (meeting_category == "회의") {
			    				category += '<option value="회의" selected>회의</option>';
			    			} else {
			    				category += '<option value="회의">회의</option>';
			    			}
			    			
			    			md_category.append(category);
			    			
			    			$('input[name = meeting_id]').attr('value', meeting_id);
			    			$('input[id = md_title]').attr('value', meeting_title);
			    			$('input[id = md_date]').attr('value', meeting_date);
			    			$('input[id = md_place]').attr('value', meeting_place);
			    			$('textarea[id = md_content]').html(meeting_content);
			    			
							$("#dateClickModal").modal("show"); // modal 나타내기 
			    		}
			    	});	
			    }
			}
		});

		calendar.render();
	});

	// form 입력값 체크
	function chk1() {

		// meeting_member : 참석자 
		const query = 'input[name="meetuser_id"]:checked'; 			// 	체크된 참석자 	
		const selectedElements = document.querySelectorAll(query);	//	모든 체크된 참석자 

		// 선택된 목록의 갯수 세기
		const selectedElementsCnt = selectedElements.length;	//	체크된 참석자 수
		console.log("selectedElementsCnt: "+selectedElementsCnt);						//	검증.

		if (!frm1.meeting_status.value) {
			alert("분류를 선택하세요.");
			return false;
		}
		
		if (!frm1.meeting_title.value) { 		//	제목 없으면 발생.
			alert("제목을 입력하세요.");
			frm1.meeting_title.focus();
			return false;
		}

		if (!frm1.meeting_date.value) { 			//	날짜 지정 안하면 발생.
			alert("일정을 선택하세요.");
			return false;
		}

		if (selectedElementsCnt < 1) { 			//	체크된 참석자 없으면 발생.
			alert("참석자를 선택하세요.");
			return false;
		}
		
	}
	
	// form 입력값 체크
	function chk2() {

		// meeting_member : 참석자 
		const query = 'input[name="meetuser_id"]:checked'; 			// 	체크된 참석자 	
		const selectedElements = document.querySelectorAll(query);	//	모든 체크된 참석자 

		// 선택된 목록의 갯수 세기
		const selectedElementsCnt = selectedElements.length;	//	체크된 참석자 수
		console.log("selectedElementsCnt: "+selectedElementsCnt);						//	검증.
		
		if (!frm2.meeting_title.value) { 		//	제목 없으면 발생.
			alert("제목을 입력하세요.");
			frm2.meeting_title.focus();
			return false;
		}

		if (!frm2.meeting_date.value) { 			//	날짜 지정 안하면 발생.
			alert("일정을 선택하세요.");
			return false;
		}

		if (selectedElementsCnt < 1) { 			//	체크된 참석자 없으면 발생.
			alert("참석자를 선택하세요.");
			return false;
		}
		
	}
	
	// 회의일정 삭제
	function delMeetingDate() {
		var answer = confirm('회의록을 삭제하시겠습니까?') 
		
		var meeting_id = $('#meeting_id').val();
		var project_id = $('#project_id').val();
		var sendurl = "/prj_meeting_report_delete/?meeting_id=" + meeting_id + "&project_id=" + project_id;
		
		if (answer) {
			$.ajax({
				url: sendurl,
				dataType: 'json',
				success: function(data) {
					alert("삭제되었습니다");
					location.href="/prj_meeting_calendar";
				}
			});
		} else {
			return false;
		}
	}
</script>
</head>
<body>

	<!-- HEADER -->
	<header id="header"></header>

	<!-- CONTENT -->
	<div class="container-fluid">
		<div class="row">

			<!-- 메뉴 -->
			<div id="menubar"
				class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
			</div>

			<!-- 본문 -->
			<main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<!------------------------------ //개발자 소스 입력 START ------------------------------->
				<!-- fullcalendar 추가 -->
				<div id='calendar'></div>

				<!-- 회의일정 등록 modal 추가 -->
				<div class="modal fade" id="calendarModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<form action="prj_meeting_date_write" name="frm1" onsubmit="return chk1()" method="post" enctype="multipart/form-data">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">회의 등록</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close" data-bs-dismiss="modal">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<input type="hidden" name="project_id" value="${project_id }">
										<input type="radio" name="meeting_status" value="1"> 회의 일정 
										<input type="radio" class="radio" name="meeting_status" value="2"> 회의록<br>
										<label for="taskId" class="col-form-label">회의 제목</label>
										<input type="text" class="form-control" id="meeting_title" name="meeting_title">
										<label for="taskId"	class="col-form-label">회의 일정</label>
										<input type="date"	class="form-control" id="meeting_date" name="meeting_date">

										<label for="taskId" class="col-form-label">회의 장소</label>
										<input type="text" class="form-control" id="meeting_place" name="meeting_place">
										
										<label for="taskId" class="col-form-label">참석자</label><br>
										<c:forEach var="prjMem" items="${prjMemList }">
											<input type="checkbox" id="meetuser_id" name="meetuser_id" value="${prjMem.user_id }"> ${prjMem.user_name }<br>
										</c:forEach>
										
										<label for="taskId" class="col-form-label">회의 유형</label><br>
										<select id="meeting_category" name="meeting_category">
											<option value="킥오프미팅">킥오프미팅</option>
											<option value="주간 업무보고">주간 업무보고</option>
											<option value="월간 회의">월간 회의</option>
											<option value="내부 회의">내부 회의</option>
											<option value="회의">회의</option>
										</select><br>
										
										<label for="taskId" class="col-form-label">첨부파일</label>
										<input type="file" class="form-control" name="file1">

										<label for="taskId" class="col-form-label">회의 내용</label> 
										<textarea rows="5" cols="60" id="meeting_content" name="meeting_content"></textarea>
									</div>
								</div>
								<div class="modal-footer">
									<input type="submit" class="btn btn-outline-success" value="추가">
									<button type="button" class="btn btn-outline-secondary" data-dismiss="modal" id="sprintSettingModalClose" data-bs-dismiss="modal">취소</button>
								</div>
							</div>
						</form>
					</div>
				</div>
				
				<!-- 일정 선택 시 회의록 등록하는  modal 추가 -->
				<div class="modal fade" id="dateClickModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<form action="prj_meeting_report_insert" name="frm2" onsubmit="return chk2()" method="post" enctype="multipart/form-data">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">회의록 등록</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close" data-bs-dismiss="modal">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<input type="hidden" id="project_id" name="project_id" value="${project_id }">
										<input type="hidden" id="meeting_id" name="meeting_id">
										<label for="taskId" class="col-form-label">회의 제목</label>
										<input type="text" class="form-control" id="md_title" name="meeting_title">
										<label for="taskId"	class="col-form-label">회의 일정</label>
										<input type="date"	class="form-control" id="md_date" name="meeting_date">

										<label for="taskId" class="col-form-label">회의 장소</label>
										<input type="text" class="form-control" id="md_place" name="meeting_place">
										
										<label for="taskId" class="col-form-label">참석자</label><br>
										<div id="meetuserList">
										
										</div>
										
										<label for="taskId" class="col-form-label">회의 유형</label><br>
										<select id="md_category" name="meeting_category">

										</select><br>
										
										<label for="taskId" class="col-form-label">첨부파일</label>
										<input type="file" class="form-control" name="file1">

										<label for="taskId" class="col-form-label">회의 내용</label> 
										<textarea rows="5" cols="60" id="md_content" name="meeting_content"></textarea>
									</div>
								</div>
								<div class="modal-footer">
									<input type="submit" class="btn btn-outline-success" value="수정">
									<button type="button" class="btn btn-outline-dark" onclick="delMeetingDate()">일정 삭제</button>
									<button type="button" class="btn btn-outline-secondary" data-dismiss="modal" id="sprintSettingModalClose" data-bs-dismiss="modal">취소</button>
								</div>
							</div>
						</form>
					</div>
				</div>

				<div id="right_side" style="width: 30%">
					<div>
						<div id="meetingList" style="display:none;">
							<h1 id="title">회의록</h1>
							<div id="meeting">

							</div>
						</div>
					</div>
				</div>
				<!------------------------------ //개발자 소스 입력 END ------------------------------->
			</main>

		</div>
	</div>

	<!-- FOOTER -->
	<footer class="footer py-2">
		<div id="footer" class="container"></div>
	</footer>

	<!-- color-modes -->
	<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
      <symbol id="check2" viewBox="0 0 16 16">
        <path
			d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z" />
      </symbol>
      <symbol id="circle-half" viewBox="0 0 16 16">
        <path
			d="M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z" />
      </symbol>
      <symbol id="moon-stars-fill" viewBox="0 0 16 16">
        <path
			d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z" />
        <path
			d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z" />
      </symbol>
      <symbol id="sun-fill" viewBox="0 0 16 16">
        <path
			d="M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z" />
      </symbol>
    </svg>

	<div
		class="dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle">
		<button
			class="btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center"
			id="bd-theme" type="button" aria-expanded="false"
			data-bs-toggle="dropdown" aria-label="Toggle theme (auto)">
			<svg class="bi my-1 theme-icon-active" width="1em" height="1em">
				<use href="#circle-half"></use></svg>
			<span class="visually-hidden" id="bd-theme-text">Toggle theme</span>
		</button>
		<ul class="dropdown-menu dropdown-menu-end shadow"
			aria-labelledby="bd-theme-text">
			<li>
				<button type="button"
					class="dropdown-item d-flex align-items-center"
					data-bs-theme-value="light" aria-pressed="false">
					<svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em">
						<use href="#sun-fill"></use></svg>
					Light
					<svg class="bi ms-auto d-none" width="1em" height="1em">
						<use href="#check2"></use></svg>
				</button>
			</li>
			<li>
				<button type="button"
					class="dropdown-item d-flex align-items-center"
					data-bs-theme-value="dark" aria-pressed="false">
					<svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em">
						<use href="#moon-stars-fill"></use></svg>
					Dark
					<svg class="bi ms-auto d-none" width="1em" height="1em">
						<use href="#check2"></use></svg>
				</button>
			</li>
			<li>
				<button type="button"
					class="dropdown-item d-flex align-items-center active"
					data-bs-theme-value="auto" aria-pressed="true">
					<svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em">
						<use href="#circle-half"></use></svg>
					Auto
					<svg class="bi ms-auto d-none" width="1em" height="1em">
						<use href="#check2"></use></svg>
				</button>
			</li>
		</ul>
	</div>

</body>
</html>