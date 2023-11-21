<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
.pms-circle {
	width:32px;
	height:32px;
	background-color:#5588ff;
	text-align:center;
	color:#fff;
	line-height:32px;
	border-radius:50% !important;
	flex-shrink:0 !important;
}
.pms-circle.bg-1 {
	background-color:#5588ff;
}
.pms-circle.bg-2 {
	background-color:#ff8855;
}
.pms-circle.bg-3 {
	background-color:#88cc55;
}
.pms-circle.bg-4 {
	background-color:#005588;
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
	
	//댓글목록 그려주기
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
				case "1": list += 'event_read'; break;
				case "2": list += 'event_read'; break;
				case "3": list += 'event_read'; break;
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

			<div class="d-flex align-items-center p-3 my-3 text-white bg-yellow rounded shadow-sm">
				<img class="me-3" src="/bootstrap-5.3.2-examples/assets/brand/bootstrap-logo-white.svg" alt="" width="48" height="38">
				<div class="lh-1">
					<h1 class="h5 mb-0 text-white lh-1">PMS (Project Management System)</h1>
					<span>Since 2023</span>
				</div>
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
						<li id="step1" class="pms-step gap-2 py-1 px-3 text-start">프로젝트를 수행하기 위해 단계적으로 필요한 요구 사항을 분석합니다.<br>개발하고자 하는 소프트웨어를 구성하는 여러 기능을 체계적으로 정의하고 목표 사용자가 제공하는 요구 사항을 명확히 할 수 있습니다.</li>
						<li id="step2" class="pms-step gap-2 py-1 px-3 text-start">개발하고자 하는 시스템의 기능 구조 및 사용가능한 데이터를 설계합니다.<br>설계는 기본적으로 시스템의 방식과 동작 데이터 저장 방식 및 사용되는 소프트웨어와 하드웨어 코드를 정의합니다.</li>
						<li id="step3" class="pms-step gap-2 py-1 px-3 text-start">사용자는 어떤 프로그래밍 언어로 개발할 것이냐에 따라 구현이 달라집니다.<br>이 과정에서 기본 요구 사항을 프로그램 코드로 변환합니다.<br>관련 라이브러리와 데이터베이스를 사용하여 코드를 작성하며 기본 과정을 완료합니다.</li>
						<li id="step4" class="pms-step gap-2 py-1 px-3 text-start">각 기능에 대한 유효성 검사가 필요한데 모든 일을 완료한 후 버그의 존재를 확인합니다.<br>각 기능이 적절한 방법으로 작동하고 명확하게 정의 된 사용자 스토리를 충족하는 지 확인합니다.</li>
						<li id="step5" class="pms-step gap-2 py-1 px-3 text-start">테스트를 완료하면 버그를 발견하면 수정하고 테스트가 완료되면 애플리케이션을 배포합니다.<br>이 단계 또한 배포 방법에 따라 다를 수 있습니다.</li>
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
			
			<div class="d-flex flex-column flex-md-row gap-4 py-md-4 align-items-center justify-content-center"> <!-- p-4 -->
			
				<!-- TO-DO LIST -->
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

				<!-- 달력 -->
				<div id="calendarMain" class="dropdown-menu d-block position-static p-3 mx-0 shadow rounded-3 w-100per" data-bs-theme="light" style="z-index:auto;"><!-- w-340px -->
					<!-- <div class="d-grid gap-1"> -->
								
					<div id="calendar"></div>
							
					<!-- </div> -->
				</div>
				
			</div>
			
		</td>
		<td width="30%" style="vertical-align:top">
		
			<div id="divMainList1" class="list-group pms-p-3 px-md-3"></div>
  
			<div id="divMainList2" class="list-group pms-p-3 px-md-3"></div>
			
			<div id="divMainList3" class="list-group pms-p-3 px-md-3"></div>
			
			<div id="divMainList4" class="list-group pms-p-3 px-md-3"></div>
			
			<!-- <div id="divMainBdQna" class="list-group p-3 px-md-3">
			    <label class="list-group-item d-flex gap-2">
			      <input class="form-check-input flex-shrink-0" type="checkbox" value="" checked="">
			      <span>
			        First checkbox
			        <span class="d-block text-body-secondary">With support text underneath to add more detail</span>
			      </span>
			    </label>
			</div>  -->
  
		</td>
	</tr>
</table>			