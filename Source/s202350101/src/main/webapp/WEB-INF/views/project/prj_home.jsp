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
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.9/index.global.min.js'></script>
<!-- JS END -->

<!-- 채팅 -->
<link rel="stylesheet" type="text/css" href="/pmschat/css/chat.css">
<script type="text/javascript" src="/pmschat/js/chat.js"></script>

<!-- 그래프 -->
<link rel="stylesheet" type="text/css" href="/lkh/css/dashboard.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="/lkh/js/chart_options.js"></script>

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
		$.ajax({
			url			: '/main_header',
			async		: false, //동기식 호출
			dataType 	: 'html',
			success		: function(data) {
				$('#header').html(data);
			}
		});
		
		$.ajax({
			url			: '/main_menu',
			async		: false, //동기식 호출
			dataType 	: 'html',
			success		: function(data) {
				$('#menubar').html(data);
			}
		});

		$.ajax({
			url			: '/main_footer',
			dataType 	: 'text',
			success		: function(data) {
				$('#footer').html(data);
			}
		});
		
		
		
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
					list += 'onclick="popup(\'prj_meeting_report_popup?meeting_id=' + board.meeting_id + '&project_id='+show_project_id+'\')" '; 
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
				list	+= 'task_read?task_id=' + board.task_id + '&project_id='+show_project_id+'\')">';
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
        <a class="link-body-emphasis fw-semibold text-decoration-none" href="prj_home">프로젝트</a>
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
		              <c:if test="${prjInfo.attach_name ne ''}">
		              	<p class="card-text">첨부파일 : <a href="javascript:popup('/upload/${prjInfo.attach_path}',800,600)">${prjInfo.attach_name}</a></p>
		              </c:if>
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