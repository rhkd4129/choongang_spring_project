<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.bg-title {
  background-color: #2C3E50; /*#ffc107; #fdb933 9ad0f5*/
  /* background-color: rgba(44, 62, 80, 0.9); */
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
.pms-center-title{
	margin-left:10px;
	margin-right:10px;
}
.pms-circle {
	display:block;
	width:32px;
	height:32px;
	background-color:#fff;
	text-align:center;
	color:#2C3E50;
	font-weight:bold;
	line-height:32px;
	border-radius:50% !important;
	flex-shrink:0 !important;
}
.pms-circle.bg-1 {
	background-color:#007AFF;
	color:#fff;
}
.pms-circle.bg-2 {
	background-color:#C77EE2;
	color:#fff;
}
.pms-circle.bg-3 {
	background-color:#CFD973;
	color:#fff;
}
.pms-circle.bg-4 {
	background-color:#3B5998;
	color:#fff;
}
.pms-overflow {
	display:block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 300px;
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
		//main_center부분 정보 가져오기
		$.ajax({
			url			: '/main_bd_free/1',
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "1");
			}
		});
		
		$.ajax({
			url			: '/main_bd_free/2',
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "2");
			}
		});
		
		$.ajax({
			url			: '/main_bd_free/3',
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "3");
			}
		});
		
		$.ajax({
			url			: '/main_bd_qna',
			dataType 	: 'json',
			success		: function(data) {
				drawMainList(data, "4");
			}
		});

	});
	
	//메인 게시물 그려주기
	function drawMainList(boardList, cate_idx){
		var list = '';
		var divObj = $("#divMainList"+cate_idx);
		divObj.empty();
		if(boardList.length==0){
			//alert("등록된 게시물이 없습니다.");
			list 	+= '<div onclick="#" class="list-group-item list-group-item-action d-flex gap-3" style="background-color:var(--bs-tertiary-bg)" aria-current="true"><span style="font-weight:bold">';
			switch(cate_idx) {
			case "1": list += '공지'; break;
			case "2": list += '이벤트'; break;
			case "3": list += '자유게시판'; break;
			case "4": list += 'Q & A'; break;
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
					if(cate_idx == "4") {
						list	+= 'Q&A';
					}else{
						list	+= board.bd_category;
					}
					
					list 	+= '</span></div>';					
					divObj.append(list);
				}
				list 	= '';
				list 	+= '<div style="cursor:pointer" onclick="popup(\'';
				switch(cate_idx) {
				case "1": list += 'board_notify_read'; break;
				case "2": list += 'board_event_read'; break;
				case "3": list += 'board_free_read'; break;
				case "4": list += 'board_qna_read'; break;
				}
				list	+= '?doc_no=' + board.doc_no + '\')" class="list-group-item list-group-item-action d-flex gap-3 pms-py-1" aria-current="true">';
				list 	+= '<span class="pms-circle bg-' + cate_idx + '">';
				if(cate_idx == "4") {
					list	+= 'Q&A';
				}else{
					list	+= (board.bd_category=='이벤트'?'Event':board.bd_category);
				}
				list	+= '</span>';
				list 	+= '<div class="d-flex gap-2 w-100 justify-content-between">';
				list 	+= '<div>';
				list 	+= '<h6 class="mb-0 pms-overflow">' + (cate_idx=='4'?board.bd_category_name + ' ':'') + board.subject + '</h6>';
				list 	+= '<p class="mb-0 opacity-75 pms-overflow">' + board.doc_body + '</p>';
				list 	+= '</div>';
				list 	+= '<span class="opacity-50 text-nowrap">' + formatDate(board.create_date) + '</span>';
				list 	+= '</div>';
				list 	+= '</div>';

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
<table width="100%">
	<tr>
		<td width="70%" style="vertical-align:top">

			<!-- PMS 타이틀 -->
			<div class="d-flex align-items-center p-3 my-3 text-white bg-title rounded shadow-sm">
				<svg xmlns="http://www.w3.org/2000/svg" width="38" height="38" fill="currentColor" class="bi bi-p-square-fill" viewBox="0 0 16 16">
					<path d="M8.27 8.074c.893 0 1.419-.545 1.419-1.488s-.526-1.482-1.42-1.482H6.778v2.97H8.27Z"/>
					<path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2Zm3.5 4.002h2.962C10.045 4.002 11 5.104 11 6.586c0 1.494-.967 2.578-2.55 2.578H6.784V12H5.5V4.002Z"/>
				</svg>
				<div class="lh-1" style="margin-left:15px">
					<h1 class="h5 mb-0 text-white lh-1">PMS (Project Management System)</h1>
					<span>Since 2023</span>
				</div>
					

				<div style="margin-left:130px">
					<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-walking" viewBox="0 0 16 16">
					<path d="M9.5 1.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0M6.44 3.752A.75.75 0 0 1 7 3.5h1.445c.742 0 1.32.643 1.243 1.38l-.43 4.083a1.75 1.75 0 0 1-.088.395l-.318.906.213.242a.75.75 0 0 1 .114.175l2 4.25a.75.75 0 1 1-1.357.638l-1.956-4.154-1.68-1.921A.75.75 0 0 1 6 8.96l.138-2.613-.435.489-.464 2.786a.75.75 0 1 1-1.48-.246l.5-3a.75.75 0 0 1 .18-.375l2-2.25Z"/>
					<path d="M6.25 11.745v-1.418l1.204 1.375.261.524a.75.75 0 0 1-.12.231l-2.5 3.25a.75.75 0 1 1-1.19-.914zm4.22-4.215-.494-.494.205-1.843a1.93 1.93 0 0 0 .006-.067l1.124 1.124h1.44a.75.75 0 0 1 0 1.5H11a.75.75 0 0 1-.531-.22Z"/>
					</svg>
				</div>
				<div class="pms-center-title"><span class="pms-circle">요구</span></div>
				<div>
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-forward-fill" viewBox="0 0 16 16">
  					<path d="m9.77 12.11 4.012-2.953a.647.647 0 0 0 0-1.114L9.771 5.09a.644.644 0 0 0-.971.557V6.65H2v3.9h6.8v1.003c0 .505.545.808.97.557z"/>
					</svg>
				</div>
				<div class="pms-center-title"><span class="pms-circle">설계</span></div>
				<div>
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-forward-fill" viewBox="0 0 16 16">
  					<path d="m9.77 12.11 4.012-2.953a.647.647 0 0 0 0-1.114L9.771 5.09a.644.644 0 0 0-.971.557V6.65H2v3.9h6.8v1.003c0 .505.545.808.97.557z"/>
					</svg>
				</div>
				<div class="pms-center-title"><span class="pms-circle">구현</span></div>
				<div>
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-forward-fill" viewBox="0 0 16 16">
  					<path d="m9.77 12.11 4.012-2.953a.647.647 0 0 0 0-1.114L9.771 5.09a.644.644 0 0 0-.971.557V6.65H2v3.9h6.8v1.003c0 .505.545.808.97.557z"/>
					</svg>
				</div>
				<div class="pms-center-title"><span class="pms-circle">검증</span></div>
				<div>
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-forward-fill" viewBox="0 0 16 16">
  					<path d="m9.77 12.11 4.012-2.953a.647.647 0 0 0 0-1.114L9.771 5.09a.644.644 0 0 0-.971.557V6.65H2v3.9h6.8v1.003c0 .505.545.808.97.557z"/>
					</svg>
				</div>
				<div class="pms-center-title"><span class="pms-circle">배포</span></div>
				<div>
					<svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-arms-up" viewBox="0 0 16 16">
					<path d="M8 3a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3"/>
					<path d="m5.93 6.704-.846 8.451a.768.768 0 0 0 1.523.203l.81-4.865a.59.59 0 0 1 1.165 0l.81 4.865a.768.768 0 0 0 1.523-.203l-.845-8.451A1.492 1.492 0 0 1 10.5 5.5L13 2.284a.796.796 0 0 0-1.239-.998L9.634 3.84a.72.72 0 0 1-.33.235c-.23.074-.665.176-1.304.176-.64 0-1.074-.102-1.305-.176a.72.72 0 0 1-.329-.235L4.239 1.286a.796.796 0 0 0-1.24.998l2.5 3.216c.317.316.475.758.43 1.204Z"/>
					</svg>
				</div>

			</div>


			<!-- 스크롤 이미지  carousel-fade-->
<div id="carouselExampleInterval" class="carousel carousel-dark slide" data-bs-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active" data-bs-interval="10000">
      <center><img src="/common/images/intro/project_cycle.png" class="d-block" alt="..."></center>
    </div>
    <div class="carousel-item" data-bs-interval="2000">
      <center><img src="/common/images/intro/project_step.png" class="d-block" alt="..."></center>
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>

			<!-- PMS소개란 -->
			<div class="dropdown-menu position-static d-flex flex-column flex-lg-row align-items-stretch justify-content-start p-3 rounded-3 shadow mt-3" data-bs-theme="light">
				<nav id="navPMS" class="col-lg-8">
					<ul class="list-unstyled d-flex flex-column gap-2">
						<li>
							<a href="#" class="btn btn-hover-light rounded-2 d-flex align-items-start gap-2 py-1 px-3 lh-sm text-start">
								<svg class="bi" width="24" height="24"><use xlink:href="#image-fill"></use></svg>
								<div>
									<strong class="d-block">전체 작업 체계화/시각화 제공</strong>
									<span>Take a tour through the product</span>
								</div>
							</a>
						</li>
						<li>
							<a href="#" class="btn btn-hover-light rounded-2 d-flex align-items-start gap-2 py-1 px-3 lh-sm text-start">
								<svg class="bi" width="24" height="24"><use xlink:href="#image-fill"></use></svg>
								<div>
									<strong class="d-block">작업진행 과정 공유로 원활한 의사소통 가능</strong>
									<span>Take a tour through the product</span>
								</div>
							</a>
						</li>
						<li>
							<a href="#" class="btn btn-hover-light rounded-2 d-flex align-items-start gap-2 py-1 px-3 lh-sm text-start">
								<svg class="bi" width="24" height="24"><use xlink:href="#image-fill"></use></svg>
								<div>
									<strong class="d-block">프로젝트 스케쥴 관리의 편의성</strong>
									<span>Take a tour through the product</span>
								</div>
							</a>
						</li>
						<li>
							<a href="#" class="btn btn-hover-light rounded-2 d-flex align-items-start gap-2 py-1 px-3 lh-sm text-start">
								<svg class="bi" width="24" height="24"><use xlink:href="#image-fill"></use></svg>
								<div>
									<strong class="d-block">개발 표준화 제공 및 프로젝트 품질 향상</strong>
									<span>Take a tour through the product</span>
								</div>
							</a>
						</li>
						<li>
							<a href="#" class="btn btn-hover-light rounded-2 d-flex align-items-start gap-2 py-1 px-3 lh-sm text-start">
								<svg class="bi" width="24" height="24"><use xlink:href="#image-fill"></use></svg>
								<div>
									<strong class="d-block">효율적인 프로젝트 산출물 관리 (문서화 폴더링)</strong>
									<span>Take a tour through the product</span>
								</div>
							</a>
						</li>
					</ul>
				</nav>
				<nav id="navStepInfo" class="col-lg-8 pms-nav-step">
					<ul class="list-unstyled d-flex flex-column gap-2">
						<li id="step1" class="pms-step gap-2 py-1 px-3 text-start">
1. 요구사항 분석<br>
다양한 분야에 이해관계자들의 사용자의 요구사항을 고려해 새로운 제품이나 변경된 제품에 대해 부합하는 요구와 조건을 결정하고,
변경된 제품에 대해서는 기존 시스템의 문제점을 파악해 새로운 요구사항을 도출하여 수집<br>
이를 정리하여 다이어그램으로 나타낸 후 요구 분석 명세서를 작성<br>
 1) 사용자 요구사항 : 단순 시스템이 제공해야 하는 서비스에 대한 추상적 서술 및 시스템 제약 사항<br>
 2) 시스템 요구사항 : 시스템 기능에 대한 상세하고 정형화된 정의
						</li>
						<li id="step2" class="pms-step gap-2 py-1 px-3 text-start">
요구사항 분석 단계에서 산출된 요구사항들을 설계하는 과정이다.<br>
시스템의 내부 프로그램 또는 모듈 간의 관계와 구조를 설계한다.<br>
프로그램 내의 각 모듈에서의 처리 절차나 알고리즘을 설계한 후 설계 명세서를 작성<br>
각 화면을 그리고 화면과 기능에 맞는 ERD를 작성한다.<br>
각 인원별로 담당할 기능들에 대해 구현 방법을 생각하고, 그에 해당하는 자료를 조사하여 설계 내용을 수정<br>
설계 방법을 생각했다면 버전 관리 방법도 생각해함<br>
 1) 시스템 설계 : 시스템을 여러 컴포넌트의 집합체로 보고 각 컴포넌트들이 요청한 결과를 <br>
 어떻게 상호작용하는지에 초점<br>
 2) 프로그램 설계(상세설계) : 각 모듈의 내부 논리 작성
						</li>
						<li id="step3" class="pms-step gap-2 py-1 px-3 text-start">
설계 단계에서 논리적으로 결정한 문제 해결 방법을 토대로 프로그래밍 언어를 사용해 실제로 프로그램을 만드는 단계<br>
많은 사람이 참여하여 작업하기 때문에 다양한 규칙이 존재하므로 결정된 규칙에 맞춰 진행<br>
(개발에 사용할 프로그래밍 언어와 기법, 스타일, 순서 등을 결정)<br>
구현 시에는 설계 내용을 바탕으로 개발 방법을 정해 모든 인원이 통일된 개발 방법을 사용할 수 있도록 해야함<br>
다른 인원이 개발한 사항을 수정할 때에는 내용을 상대방이 이해할 수 있도록 명확하게 알려줘야 함<br>
 1) 읽기 쉽고 이해하기 쉬운(단순화/명확성)<br>
 2) 테스팅과 유지보수하기 쉬운 코드<br>
 3) 리팩토링
						</li>
						<li id="step4" class="pms-step gap-2 py-1 px-3 text-start">
개발한 시스템이 요구사항과 부합하는지 실행 결과를 검사하고 평가함<br>
테스트는 단위/통합/시스템/인수 테스트로 나뉘며 테스트 계획서 및 통합 테스트 결과 보고서를 작성<br>
테스트를 하며 누락된 기능 및 항목이 있는지 확인함<br>
 1) 단위테스트<br>
 2) 통합테스트<br>
 3) 시스템테스트<br>
 4) 인수테스트
						</li>
						<li id="step5" class="pms-step gap-2 py-1 px-3 text-start">
						테스트를 완료하면 버그를 발견하면 수정하고 테스트가 완료되면 애플리케이션을 배포합니다.<br>
						이 단계 또한 배포 방법에 따라 다를 수 있습니다.
						</li>
					</ul>
				</nav>
				<div class="d-none d-lg-block vr mx-4 opacity-10">&nbsp;</div>
					<hr class="d-lg-none">
					<div class="col-lg-auto pe-3">
						<nav>
							<ul class="d-flex flex-column gap-2 list-unstyled span">
								<li onmouseover="showStepInfo(1)" onmouseout="hideStepInfo(1)"><a href="#" class="link-offset-2 link-underline link-underline-opacity-25 link-underline-opacity-75-hover">1. 요구사항 분석</a></li>
								<li onmouseover="showStepInfo(2)" onmouseout="hideStepInfo(2)"><a href="#" class="link-offset-2 link-underline link-underline-opacity-25 link-underline-opacity-75-hover">2. 설계</a></li>
								<li onmouseover="showStepInfo(3)" onmouseout="hideStepInfo(3)"><a href="#" class="link-offset-2 link-underline link-underline-opacity-25 link-underline-opacity-75-hover">3. 구현</a></li>
								<li onmouseover="showStepInfo(4)" onmouseout="hideStepInfo(4)"><a href="#" class="link-offset-2 link-underline link-underline-opacity-25 link-underline-opacity-75-hover">4. 테스트</a></li>
								<li onmouseover="showStepInfo(5)" onmouseout="hideStepInfo(5)"><a href="#" class="link-offset-2 link-underline link-underline-opacity-25 link-underline-opacity-75-hover">5. 배포 및 OPEN준비</a></li>
							</ul>
						</nav>
					</div>				
				</div>
			</div>
			

 			<div style="display:none" class="d-flex flex-column flex-md-row gap-4 py-md-4 align-items-center justify-content-center">
			
 				<div id="todolistMain" class="dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per" data-bs-theme="light">
					<form class="p-2 mb-2 bg-body-tertiary border-bottom">
						<input type="search" class="form-control" autocomplete="false" placeholder="오늘 할 일을 메모합니다...">
					</form>
					    
 					<div class="list-group">
						<label class="list-group-item d-flex gap-3 bg-body-tertiary">
						      <input class="form-check-input form-check-input-placeholder bg-body-tertiary flex-shrink-0 pe-none" disabled="" type="checkbox" value="" style="font-size: 1.375em;">
						      <span class="pt-1 form-checked-content">
						        <span contenteditable="true" class="w-100">Boot Sprint Project 생성하기</span>
						        <span class="d-block text-body-secondary">
						          <svg class="bi me-1" width="1em" height="1em"><use xlink:href="#list-check"></use></svg>
						          Choose list...
						        </span>
						      </span>
						</label>
						<label class="list-group-item d-flex gap-3">
							<input class="form-check-input flex-shrink-0" type="checkbox" value="" checked="" style="font-size: 1.375em;">
							<span class="pt-1 form-checked-content">
								<strong>index파일 및 각 담당자별 폴더 및 파일 구성하기</strong>
								<span class="d-block text-body-secondary">
									<svg class="bi me-1" width="1em" height="1em"><use xlink:href="#calendar-event"></use></svg>
									1:00–2:00pm
								</span>
							</span>
						</label>
						<label class="list-group-item d-flex gap-3">
							<input class="form-check-input flex-shrink-0" type="checkbox" value="" style="font-size: 1.375em;">
							<span class="pt-1 form-checked-content">
								<strong>Oracle DB에 ERD F/E으로 Table생성하기</strong>
								<span class="d-block text-body-secondary">
									<svg class="bi me-1" width="1em" height="1em"><use xlink:href="#calendar-event"></use></svg>
									2:00–2:30pm
								</span>
							</span>
						</label>
						<label class="list-group-item d-flex gap-3">
							<input class="form-check-input flex-shrink-0" type="checkbox" value="" style="font-size: 1.375em;">
							<span class="pt-1 form-checked-content">
								<strong>DB Table에 샘플 데이타 생성하기</strong>
								<span class="d-block text-body-secondary">
									<svg class="bi me-1" width="1em" height="1em"><use xlink:href="#alarm"></use></svg>
									Tomorrow
								</span>
							</span>
						</label>
						<label class="list-group-item d-flex gap-3">
							<input class="form-check-input flex-shrink-0" type="checkbox" value="" style="font-size: 1.375em;">
							<span class="pt-1 form-checked-content">
								<strong>DB Table에 샘플 데이타 생성하기</strong>
								<span class="d-block text-body-secondary">
									<svg class="bi me-1" width="1em" height="1em"><use xlink:href="#alarm"></use></svg>
									Tomorrow
								</span>
							</span>
						</label>
					</div>
				</div>
				
				<div id="calendarMain" class="dropdown-menu d-block position-static p-3 mx-0 shadow rounded-3 w-100per" data-bs-theme="light" style="z-index:auto;">
					<div id="calendar"></div>
				</div>
				
			</div>

		</td>
		<td width="30%" style="vertical-align:top">
		
			<div id="divMainList1" class="list-group pms-p-3 px-md-3"></div>
  
			<div id="divMainList2" class="list-group pms-p-3 px-md-3"></div>
			
			<div id="divMainList3" class="list-group pms-p-3 px-md-3"></div>
			
			<div id="divMainList4" class="list-group pms-p-3 px-md-3"></div>
			
		</td>
	</tr>
</table>			