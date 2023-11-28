<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<!-- CSS END -->

<!-- JS START -->
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.9/index.global.min.js'></script>
<!-- JS END -->

<!-- 채팅 -->
<link rel="stylesheet" type="text/css" href="/pmschat/css/chat.css">
<script type="text/javascript" src="/pmschat/js/chat.js"></script>

<style>
.bg-yellow {
  background-color: #ffc107; /*#ffc107; #fdb933 9ad0f5*/
}
/*프로젝트 소개란*/
.pms-nav-step {
	background-color:#fff;
	height: 273px;
	display: none;
}
.pms-step {
	line-height: 25px;
}
.pms-circle-home {
	width:32px;
	height:32px;
	background-color:#5588ff;
	text-align:center;
	color:#fff;
	line-height:32px;
	border-radius:50% !important;
	flex-shrink:0 !important;
}
.pms-circle-home.bg-3 { /*공지/자료  8994BD*/
	background-color:#ffc107;
	color:#fff;
}
.pms-circle-home.bg-4 { /*업무보고 */
	background-color:#78ABAC;
	color:#fff;
}
.pms-circle-home.bg-5 { /*작업 */
	background-color:#55B4D1;
	color:#fff;
}
.pms-overflow {
	display:block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 300px;
}
.pms-overflow-task {
	display:block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 700px;
}
.pms-p-3{
	padding-top:16px;
	padding-left:16px;
	padding-right:16px;
	padding-bottom:0px;
}
.pms-py-3{
	padding-top:13px;
	padding-bottom:13px;
}

/*오늘 할 일*/
#todolistMain {	
	height: 393px;
}

/*달력*/
#calendarMain {
	height: 393px;
	padding:10px;
}
.fc-scrollgrid-sync-table {
	height:355px;
}
.fc-theme-standard td, .fc-theme-standard th {
	border-radius:20%;
    border: 0px solid var(--fc-border-color);
}
.fc-daygrid-day-frame:hover {
	border-radius:20%;
    background-color: #f8f9fa;
}
.fc .fc-daygrid-day-top {
    justify-content: center;
}
.fc-theme-standard .fc-scrollgrid {
    border: 0px solid var(--fc-border-color);
}
.fc .fc-col-header-cell-cushion {
	text-decoration: none;
	--bs-text-opacity: 1;
	font-family: var(--bs-body-font-family);
	color: var(--bs-secondary-color) !important;
}
.fc .fc-daygrid-day-number {
    padding: 1px;
}
.fc-daygrid-day-top a {
    color: #555555;
    text-decoration: none;
}
.fc .fc-toolbar-title {
    font-size: 1.5em;
    margin: 0px;
}
.fc .fc-button {
	padding: 0.2em 0.35em;
}
.align-items-center {
    align-items: normal;
}
.fc .fc-daygrid-day.fc-day-today {
    background-color: var(--fc-today-bg-color);
}
</style>
<script type="text/javascript">

	$(function() {
		var params = getUrlParams();
		if(params.project_id != null && params.project_id != "") {
			var show_project_id = params.project_id;
		}else{
			var show_project_id = '${userInfoDTO.project_id}';
		}
		//alert("show_project_id="+show_project_id);
		
		//회의일정
		$.ajax({
			url			: '/main_prj_meeting?project_id='+show_project_id+'&meeting_status=1',
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "1", show_project_id);
			}
		});

		//회의록
		$.ajax({
			url			: '/main_prj_meeting?project_id='+show_project_id+'&meeting_status=2',
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "2", show_project_id);
			}
		});

		//공지/자료
		$.ajax({
			url			: '/main_prj_board_data?project_id='+show_project_id,
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "3", show_project_id);
			}
		});
		
		//업무보고
		$.ajax({
			url			: '/main_prj_board_report?project_id='+show_project_id,
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "4", show_project_id);
			}
		});	
		
		//작업-예정(0)
 		$.ajax({
			url			: '/main_prj_task?project_id='+show_project_id+'&task_status=0',
			dataType 	: 'json',
			success		: function(data) {
				drawTaskList(data, "0", show_project_id);
			}
		});
		//작업-진행중(1)
		$.ajax({
			url			: '/main_prj_task?project_id='+show_project_id+'&task_status=1',
			dataType 	: 'json',
			success		: function(data) {
				drawTaskList(data, "1", show_project_id);
			}
		});
		//작업-완료(2)
		$.ajax({
			url			: '/main_prj_task?project_id='+show_project_id+'&task_status=2',
			dataType 	: 'json',
			success		: function(data) {
				drawTaskList(data, "2", show_project_id);
			}
		});
		
		//main.jsp위에 js파일 선언
		loadCalendar(); //프로젝트 Home인 경우 달력 그려주기
		loadBarChart(); //프로젝트 Home인 경우 그래프(Bar) 그려주기
	});
	
	//Home 게시물 그려주기
	function drawMainList(boardList, cate_idx, show_project_id){
		var list = '';
		var divObj = $("#divMainList"+cate_idx);
		divObj.empty();
		if(boardList.length==0){
			//alert("등록된 게시물이 없습니다.");
			list 	+= '<div onclick="#" class="list-group-item list-group-item-action d-flex gap-3" style="background-color:var(--bs-tertiary-bg)" aria-current="true"><span style="font-weight:bold">';
			switch(cate_idx) {
			case "3": list += '공지/자료'; break;
			case "4": list += '업무보고'; break;
			}
			list 	+= '</span></div>';
			list 	+= '<div onclick="#" class="list-group-item list-group-item-action d-flex gap-3 pms-py-1" aria-current="true">';
			list 	+= '<div class="d-flex gap-2 w-100 justify-content-between">';
			list 	+= '<div>';
			list 	+= '<h6 class="mb-0 pms-overflow">등록된 자료가 없습니다.</h6>';
			list 	+= '<p class="mb-0 opacity-75 pms-overflow">...</p>';
			list 	+= '</div>';
			list 	+= '<span class="opacity-50 text-nowrap"></span>';
			list 	+= '</div>';
			list 	+= '</div>';
			divObj.append(list);
			divObj.show();
		}
		else{
			$(boardList).each(function(index, board){
				if(index == 0) {
					list    = '';
					list 	+= '<div onclick="" class="list-group-item list-group-item-action d-flex gap-3"  style="background-color:var(--bs-tertiary-bg)" aria-current="true"><span style="font-weight:bold">';
					switch(cate_idx) {
					case "1": list += '회의일정'; break;
					case "2": list += '회의록'; break;
					case "3": list += '공지/자료'; break;
					case "4": list += '업무보고'; break;
					}
					list 	+= '</span></div>';					
					divObj.append(list);
				}
				list 	= '';
				list 	+= '<div style="cursor:pointer" ';
				switch(cate_idx) {
				case "1": 
					list += 'onclick="alert(\'회의일정이 있습니다. ['+board.meeting_date + '] ' + board.meeting_title+'\')" ';
					break;
				case "2": 
					list += 'onclick="popup(\'prj_meeting_report_read?doc_no=' + board.doc_no + '&project_id='+show_project_id+'\')" '; 
					break;
				case "3": 
					list += 'onclick="popup(\'prj_board_data_read?doc_no=' + board.doc_no + '&project_id='+show_project_id+'\')" '; 
					break;
				case "4": 
					list += 'onclick="popup(\'prj_board_report_read?doc_no=' + board.doc_no + '&project_id='+show_project_id+'\')" '; 
					break;
				}
				list	+= 'class="list-group-item list-group-item-action d-flex gap-3 pms-py-1" aria-current="true">';
				list 	+= '<span class="pms-circle-home bg-' + cate_idx + '">';
				switch(cate_idx) {
				case "1": list += '일정'; break;
				case "2": list += '기록'; break;
				case "3": list += '자료'; break;
				case "4": list += '업무'; break;
				}
				list	+= '</span>';
				list 	+= '<div class="d-flex gap-2 w-100 justify-content-between">';
				list 	+= '<div>';
				list 	+= '<h6 class="mb-0 pms-overflow"><font class="text-primary" style="font-size:12px">';
				switch(cate_idx) {
				case "1": list += '['+board.meeting_date + '] ' + board.meeting_title; break;
				case "2": list += '['+board.meeting_date + '] ' + board.meeting_title; break;
				case "3": list += '['+board.bd_category_name + '] ' + board.subject; break;
				case "4": list += board.subject; break;
				}				
				list	+= '</font></h6>';
				list 	+= '<p class="mb-0 opacity-75 pms-overflow">';
				switch(cate_idx) {
				case "1": list += board.meeting_content; break;
				case "2": list += board.meeting_content; break;
				case "3": list += '['+board.bd_category_name + '] ' + board.doc_body; break;
				case "4": list	+= board.doc_body; break;
				}
				list	+= '</p>';
				list 	+= '</div>';
				list 	+= '<span class="opacity-50 text-nowrap">';
				list	+= formatDate(board.create_date);
				list	+= '</span>';
				list 	+= '</div>';
				list 	+= '</div>';

				divObj.append(list);
				divObj.show();
			});
		}
	}
	
	//작업 : 진행별 그려주기
 	function drawTaskList(boardList, cate_idx, show_project_id){
		
		var list = '';
		var divObj = $("#divTaskList"+cate_idx);
		divObj.empty();
		if(boardList.length==0){
			//alert("등록된 게시물이 없습니다.");
		}
		else{
			
			$(boardList).each(function(index, board){				
				if(index == 0) {
					list    = '';
					list 	+= '<div onclick="" class="list-group-item list-group-item-action d-flex gap-3"  style="background-color:var(--bs-tertiary-bg)" aria-current="true"><span style="font-weight:bold">';
					switch(cate_idx) {
					case "0": list += '작업 > 예정'; break;
					case "1": list += '작업 > 진행중'; break;
					case "2": list += '작업 > 완료'; break;
					}
					list 	+= '</span></div>';					
					divObj.append(list);
				}
				list 	= '';
				list 	+= '<label class="list-group-item d-flex gap-2" style="cursor:pointer" onclick="popup(\'';
				list	+= 'task_read?doc_no=' + board.doc_no + '&project_id='+show_project_id+'\')">';
				//list 	+= '<input class="form-check-input flex-shrink-0" type="checkbox" value="" checked="">';
				
				//list 	+= '<img style=" width: 32px; height: 32px; border-radius: 50%; margin-left:10px; margin-right:5px;" alt="${prjMember.user_name}"'; 
				//list 	+= 'src="${pageContext.request.contextPath}/'+prjmember.attach_path+'/'+prjmember.attach_name+'">';
				
				list 	+= '<small><font class="text-secondary">' + board.user_name+' ('+formatDate(board.create_date)+')</font> </small>';
				list 	+= '<span><font class="text-primary" style="font-size:12px">';
				list 	+= board.task_subject;
				list 	+= '</font><span class="d-block text-body-secondary pms-overflow-task"><small>';
				list 	+= board.task_content;
				list	+= '</small></span>';
				list 	+= '</span>';
				list 	+= '</label>';
				
				divObj.append(list);
				divObj.show();
			});
		}
	} 
	
	function showStepInfo(i) {		
		$(".pms-step").each(function(){
			$(this).hide();
		});
		$("#step"+i.toString()).show();
		$("#navStepInfo").show();
		$("#navPMS").hide();
	}
	
	function hideStepInfo(i) {
		$("#navPMS").show();
		$("#navStepInfo").hide();
	}
		
</script>
</head>
<body>
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
        <a class="link-body-emphasis fw-semibold text-decoration-none" href="">프로젝트</a>
      </li>
      <li class="breadcrumb-item active" aria-current="page">프로젝트 Home</li>
    </ol>
</nav>
<div class="container-fluid">
	<div style="margin-top:15px;height:25px">
		<span class="apptitle">프로젝트 Home</span>
	</div>
</div>

<table width="100%">
	<tr>
		<td width="65%" style="vertical-align:top">
		
			<!-- 스크롤 이미지 -->
			<div class="dropdown-menu position-static d-flex flex-column flex-lg-row align-items-stretch justify-content-start p-3 rounded-3 shadow mt-3" data-bs-theme="light">
				<div class="card w-100per shadow-sm">
		            <div class="card-body">
		              <p class="card-text"><h5 class="text-primary"><b>${prjInfo.project_name}</b></h5></p>
		              <p class="card-text">${prjInfo.project_intro}</p>
		              <img src="/common/images/intro/project_step.png" style="margin:10px 0px">
		              <p class="card-text">프로젝트 기간 : ${prjInfo.project_startdate} ~ ${prjInfo.project_enddate}</p>
		              <p class="card-text">팀장 : 
		              	<c:forEach var="prjMember" items="${prjMemList}">
		              		<c:if test="${prjMember.user_id eq prjInfo.project_manager_id}">
				              	<img style=" width: 32px; height: 32px; border-radius: 50%; margin-left:10px; margin-right:5px;" 
				              	alt="${prjMember.user_name}" 
				              	src="${pageContext.request.contextPath}/${prjMember.attach_path }/${prjMember.attach_name}">
				              	${prjMember.user_name}
				              	<span class="iconChat">
									<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
										<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
										<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
									</svg>
									<input type="hidden" name="chat_id" value="${prjMember.user_id}">
								</span>	
				            </c:if>
		              	</c:forEach>	
	              
		              </p>
		              <p class="card-text">팀원 : 
		              	<c:forEach var="prjMember" items="${prjMemList}">
		              		<c:if test="${prjMember.user_id ne prjInfo.project_manager_id}">
				              	<img style=" width: 32px; height: 32px; border-radius: 50%; margin-left:10px; margin-right:5px;" 
				              	alt="${prjMember.user_name}" 
				              	src="${pageContext.request.contextPath}/${prjMember.attach_path }/${prjMember.attach_name}">
				              	${prjMember.user_name}
							    <span class="iconChat">
									<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
										<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
										<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
									</svg>
									<input type="hidden" name="chat_id" value="${prjMember.user_id}">
								</span>				              	
				            </c:if>
		              	</c:forEach>	
		              </p>
		              <!-- <div class="d-flex justify-content-between align-items-center">
		                <div class="btn-group">
		                  <button type="button" class="btn btn-sm btn-outline-secondary">더보기</button>
		                  <button type="button" class="btn btn-sm btn-outline-secondary">입장</button>
		                </div>
		                <small class="text-body-secondary"></small>
		              </div> -->
		            </div>
	          	</div>
			</div>
			
			<!-- 그래프 -->
			<div class="dropdown-menu position-static d-flex flex-column flex-lg-row align-items-stretch justify-content-start p-3 rounded-3 shadow mt-3" data-bs-theme="light">
				<!-- <div><button type="button" class="btn btn-sm btn-outline-secondary" onclick="loadBarChart()">새로고침</button></div> -->
				<div class="bar_chart">
					<canvas id="bar_chart"></canvas>
				</div>				
			</div>
			
			<!-- 팀원별 진척률 -->
			<div class="dropdown-menu position-static d-flex flex-column flex-lg-row align-items-stretch justify-content-start p-3 rounded-3 shadow mt-3" data-bs-theme="light">
				<div class="card w-100per shadow-sm">
		            <div class="card-body">
		            	<p style="text-align:center"><font style="font-size:12px"><b>팀원별 작업 진척률</b></font></p>
		              	<p style="text-align:right"><small>* 작업 : 총건수(예정/진행/완료) 진척률%</small></p>
		              	<c:forEach var="TaskProgress" items="${prjTaskProgressList}">
		              		<c:if test="${TaskProgress.user_id eq prjInfo.project_manager_id}">
		              			<p class="card-text">
					              	<c:forEach var ="prjMember" items="${prjMemList}">
					              		<c:if test="${prjMember.user_id eq TaskProgress.user_id}">
					              			<table width="100%">
					              				<colgroup>
					              					<col width="50"></col>
					              					<col width="150"></col>
					              					<col width="100"></col>
					              					<col width="50"></col>
					              					<col width="*"></col>
					              				</colgroup>
					              				<tr>
					              					<td>팀장 : </td>
						              				<td>
							              				<img style=" width: 32px; height: 32px; border-radius: 50%; margin-left:10px; margin-right:5px;" alt="" 
								              			src="${pageContext.request.contextPath}/${prjMember.attach_path }/${prjMember.attach_name}">
								              			${prjMember.user_name}
								              			<span class="iconChat">
															<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
																<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
																<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
															</svg>
															<input type="hidden" name="chat_id" value="${TaskProgress.user_id}">
														</span>
						              				</td>
						              				<td>
					              						<font class="text-primary"><b>${TaskProgress.status_all_count}</b></font>건
					              						(${TaskProgress.status_0_count}/${TaskProgress.status_1_count}/<font class="text-danger">${TaskProgress.status_2_count}</font>)
						              				</td>
						              				<td>
						              					<font class="text-danger"><b>${TaskProgress.status_progress}</b></font>%
						              				</td>
													<td>
														<progress value="${TaskProgress.status_progress}" max="100" class="pms-progress-bar"></progress>
													</td>
					              				</tr>
					              			</table>
					              		</c:if>
					              	</c:forEach>
								</p>
				            </c:if>
		              	</c:forEach>
		              
		              	<c:forEach var="TaskProgress" items="${prjTaskProgressList}">
		              		<c:if test="${TaskProgress.user_id ne prjInfo.project_manager_id}">
		              			<p class="card-text"> 
					              	<c:forEach var ="prjMember" items="${prjMemList}">
					              		<c:if test="${prjMember.user_id eq TaskProgress.user_id}">
					              			<table width="100%">
					              				<colgroup>
					              					<col width="50"></col>
					              					<col width="150"></col>
					              					<col width="100"></col>
					              					<col width="50"></col>
					              					<col width="*"></col>
					              				</colgroup>
					              				<tr>
					              					<td>팀원 : </td>
						              				<td>
							              				<img style=" width: 32px; height: 32px; border-radius: 50%; margin-left:10px; margin-right:5px;" alt="" 
								              			src="${pageContext.request.contextPath}/${prjMember.attach_path }/${prjMember.attach_name}">
								              			${prjMember.user_name}
								              			<span class="iconChat">
															<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
																<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
																<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
															</svg>
															<input type="hidden" name="chat_id" value="${TaskProgress.user_id}">
														</span>
						              				</td>
						              				<td>
					              						<font class="text-primary"><b>${TaskProgress.status_all_count}</b></font>건
					              						(${TaskProgress.status_0_count}/${TaskProgress.status_1_count}/<font class="text-danger">${TaskProgress.status_2_count}</font>)
						              				</td>
						              				<td>
						              					<font class="text-danger"><b>${TaskProgress.status_progress}</b></font>%
						              				</td>
													<td>
														<progress value="${TaskProgress.status_progress}" max="100" class="pms-progress-bar"></progress>
													</td>
					              				</tr>
					              			</table>
					              		</c:if>
					              	</c:forEach>
								</p>
				            </c:if>
		              	</c:forEach>
		            </div>
	          	</div>
			</div>

			<!-- 상태별 작업 -->			
			<div class="d-flex flex-column flex-md-row gap-1 py-md-2 align-items-center justify-content-center"> <!-- p-4 -->
				<div class="dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per" data-bs-theme="light">
					<div id="divTaskList0" class="list-group p-3 px-md-3">
					</div>
				</div>
			</div>
			<div class="d-flex flex-column flex-md-row gap-1 py-md-2 align-items-center justify-content-center"> <!-- p-4 -->
				<div class="dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per" data-bs-theme="light">
					<div id="divTaskList1" class="list-group p-3 px-md-3">
					</div>
				</div>
			</div>
			<div class="d-flex flex-column flex-md-row gap-1 py-md-2 align-items-center justify-content-center"> <!-- p-4 -->
				<div class="dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per" data-bs-theme="light">
					<div id="divTaskList2" class="list-group p-3 px-md-3">
					</div>
				</div>
			</div>
		</td>
		<td width="35%" style="vertical-align:top">
		
			<div id="calendarMain" class="dropdown-menu d-block position-static m-3 p-3 shadow rounded-3 w-90per" data-bs-theme="light" style="height:400px;z-index:auto;"><!-- w-340px -->
				<!-- <div class="d-grid gap-1"> -->
							
				<div id="calendar"></div>
						
				<!-- </div> -->
			</div>
			
			<div id="divMainList1" class="list-group pms-p-3 px-md-3"></div>
  
			<div id="divMainList2" class="list-group pms-p-3 px-md-3"></div>
			
			<div id="divMainList3" class="list-group pms-p-3 px-md-3"></div>
			
			<div id="divMainList4" class="list-group pms-p-3 px-md-3"></div>
			
		</td>
	</tr>
</table>
		
</body>
</html>