<%@page import="java.util.List"%>
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
	#category_title {
		position: absolute;
	}

	div #calendar {
		width: 80%;
		margin-top: 50px;
	}
	
	:root {
		--fc-button-text-color: black;
		--fc-button-bg-color: white;
		--fc-button-hover-bg-color: rgba(13, 110, 253, 0.1);
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

		var events = [];

		// Iterate over the meetings using JSTL				meetingDateList
		<c:forEach var="meeting" items="${meetingDateList}">
		events.push({
			title : '${meeting.meeting_title}',
			start : '${meeting.meeting_date}',
			end : '${meeting.meeting_date}',
			color : '#F2CB61'
		});
		</c:forEach>

		// Add the project event								prj
		events.push({
			title : '${prj.project_name}',
			start : '${prj.project_startdate}',
			end : '${prj.project_enddate}',
			color : '#6799FF'

		});

		var calendar = new FullCalendar.Calendar(calendarEl, {
			initialView : 'dayGridMonth',
			selectable : true,
			events : events,
			dateClick : function(info) {
				console.log("clicked date : " + info.dateStr);
			},
			
			eventClick: function(prjEvents) {
				const element = prjEvents.el;
				const backgroundColor = window.getComputedStyle(element).backgroundColor;
				
				console.log("클릭한 곳의 배경색: " + backgroundColor);
				
				if (backgroundColor == "rgb(103, 153, 255)") {
					var openurl = "/dashboard";
					
					console.log("openurl: " + openurl);
					
					window.open(openurl, "_self");
				}
				
			}
		});

		calendar.render();
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
			<div id="menubar"
				class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
			</div>

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
				
				<!-- fullcalendar 추가 -->
				<div id='calendar'></div>

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