<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/lkh/css/dashboard.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
        document.addEventListener("DOMContentLoaded", function() {
            // HTML 구조는 준비되었지만, 모든 리소스의 로딩이 완료되지 않았을 때 실행됩니다.
            $.ajax({
                url: "<%=request.getContextPath()%>/doughnut_chart",
                //data:data
                dataType: 'json',
                success: function (response) {
                    const doughnutCtx = document.getElementById('doughnut_1').getContext('2d');
                    const doughnut_data = {
                        labels: ['예정된 작업', '진행중인 작업', '완료된 작업'],
                        datasets: [{
                            data: response,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.5)', // 빨간색
                                'rgba(54, 162, 235, 0.5)', // 파란색
                                'rgba(176, 136, 255, 0.5)', // 보라색
                            ],
                            borderColor: [
                                'rgb(255, 99, 132)', // 빨간색
                                'rgb(54, 162, 235)', // 파란색
                                'rgb(176, 136, 255)', // 보라색
                            ],
                            borderWidth: 1
                        }]
                    };
                    
                    createDrawChart(doughnutCtx, 'doughnut', doughnut_data, doughnut_options);
                }
            });
            $.ajax({
                url: "<%=request.getContextPath()%>/workload_chart",
                //data:data
                dataType: 'json',
                success: function (workload) {
                    const barCtx = document.getElementById('bar_chart').getContext('2d');

                    var labels = []
                    var stats_0 = []
                    var stats_1 = []
                    var stats_2 = []
                    for (let i = 0; i < workload.length; i++) {
                        labels.push(workload[i].user_name);
                        stats_0.push(workload[i].status_0_count);
                        stats_1.push(workload[i].status_1_count);
                        stats_2.push(workload[i].status_2_count);
                    }
                    const horizontalStackBarData = {
                        labels: labels,
                        datasets: [
                            {
                                label: '예정된 작업',
                                data: stats_0,
                                backgroundColor: 'rgba(255, 99, 132, 0.5)',  // 빨간색 배경
                                borderColor: 'rgb(255, 99, 132)',  // 빨간색 테두리
                            },
                            {
                                label: '진행중인 작업',
                                data: stats_1,
                                backgroundColor: 'rgba(54, 162, 235, 0.5)',  // 파란 배경
                                borderColor: 'rgb(54, 162, 235)',  // 파란 테두리
                            },
                            {
                                label: '완료된 작업',
                                data: stats_2,
                                backgroundColor: 'rgba(176, 136, 255, 0.5)',  // 보라색 배경
                                borderColor: 'rgb(176, 136, 255)',  // 보라색 테두리
                            },
                        ]
                    };
                    createDrawChart(barCtx, 'bar', horizontalStackBarData, horizontalStackBarOption);
                }
            });

            $.ajax({
                url: "<%=request.getContextPath()%>/project_day",
                dataType: 'json',
                success: function (prjInfo) {
                    // Oracle에서 가져온 문자열을 JavaScript Date 객체로 변환
                    const projectStartDate = new Date(prjInfo.project_startdate);
                    const projectEndDate = new Date(prjInfo.project_enddate);
                    var a = $("#project_time");
                    a.text(prjInfo.project_startdate+'~'+prjInfo.project_enddate);

                    const currentDate = new Date();                                     /// 현재 날짜를 가져오기
                    const timeDiff = projectEndDate - projectStartDate;                 // 두 날짜 사이의 차이 계산
                    const totalDays = Math.floor(timeDiff / (1000 * 60 * 60 * 24));     // 밀리초(ms)를 일(day)로 변환
                    const timeDiffFromStart = currentDate - projectStartDate;           // 시작 날짜와 현재 날짜 사이의 차이 계산
                    const daysPassed = Math.floor(timeDiffFromStart / (1000 * 60 * 60 * 24));
                    const timeDiffToFinish = projectEndDate - currentDate;               // 현재로부터 프로젝트 종료일까지의 남은 기간 계산\
                    const remainingDays = Math.floor(timeDiffToFinish / (1000 * 60 * 60 * 24));
                    // projectStartDate 프로젝트 시작일
                    // projectEndDate   프로젝트 종료일
                    // totalDays        프로젝트 기간
                    // daysPassed       시작이롤부터 오늘까지 경과한 일 수
                    // remainingDays    현재로부터 프로젝트 종료일까지 남은 기간
                    const barCtx = document.getElementById('project_chart').getContext('2d');
                    const proejctData = {
                        labels: ['프로젝트 기간 '],
                        datasets: [
                            {
                                label: '일한일수',
                                data: [daysPassed],
                                backgroundColor: 'rgba(255, 99, 132, 0.5)',  // 빨간색 배경
                                borderColor: 'rgb(255, 99, 132)',  // 빨간색 테두리
                            },
                            {
                                label: '남은일수',
                                data: [Math.abs(remainingDays)],
                                backgroundColor: 'rgba(54, 162, 235, 0.5)',  // 파란색 배경
                                borderColor: 'rgb(54, 162, 235)',  // 파란색 테두리
                            }

                        ]
                    }
                    createDrawChart(barCtx, 'bar', proejctData, proejctOption);
                }
            });

            $.ajax({
                url: "<%=request.getContextPath()%>/project_step_chart",
                dataType: 'json',
                success: function (data) {
                    ////////////////////// 현재 진행중인 프로젝트 보기 /////////////////////////////
                    const onelist = data.onelist;
                    const current_task = $('.current_task');
                    if(onelist.length > 0) {
                        for (let i = 0; i < onelist.length; i++) {
                            var cur_task = $('<div></div>');
                            cur_task.addClass("cur_task");
                            if (i > 6) {
                                cur_task.text('......');
                                current_task.add(cur_task)
                                break;
                            }
                            cur_task.html("<a href='task_detail?task_id=" + onelist[i].task_id + '&project_id=' + onelist[i].project_id + "'>" + onelist[i].task_subject + "</a>");
                            current_task.append(cur_task);
                        }
                    }
                    else{
                        var cur_task = $('<div></div>');
                        cur_task.addClass("cur_task");
                        cur_task.text('진행중인 작업이 아직 없습니다.');
                        current_task.add(cur_task)
                    }
                    ////////////////////// 프로젝트 단계별 보기 /////////////////////////////
                    const project_step_chart = $('.project_step_chart');
                    $.each(data.mapData, function(key, values) {
                        // 새로운 div 요소 생성
                        const newDiv = $('<div></div>');
                        newDiv.addClass("project_step");
                        const newstep = $('<div>'+key+'</div>'); // 텍스트를 담을 span 요소 생성
                        newDiv.append(newstep); // div에 span을 추가
                        newstep.addClass("project_step_subject");
                        // // key 값으로 가져온 데이터를 div에 추가
                        for(let i=0; i<values.length;i++) {
                            var newtask = $('<div></div>').text(values[i]);
                            newtask.addClass("project_step_task_subject")
                            newDiv.append(newtask);
                            if(i === 7){
                                var newtask = $('<div></div>').text(" ... ");
                                newtask.addClass("project_step_task_subject")
                                newDiv.append(newtask);
                                break;
                            }
                        }
                        project_step_chart.append(newDiv);
                    });
                }
            });
        });
    </script>
</head>
<body>
    <div id="header"></div>
    <div class="container-fluid">
        <div class="row">
	        <!-- 메뉴 -->
	        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
	        </div>
	        <!-- 본문 -->
	        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
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
				      <li class="breadcrumb-item active" aria-current="page">작업 보드</li>
				    </ol>
				</nav>
				<div class="container-fluid">
					<div style="margin-top:15px;height:45px">
						<span class="apptitle">작업 보드</span>
					</div>
				</div>
	            
				<div class="chart_1">
                   <div class="project_chart">
                       <canvas id="project_chart"></canvas>
                       <p id="project_time"></p>
                       <!-- <div class="controller">
                               <a type="button" class="btn btn btn-outline-primary" href="task_list">작업 목록</a>
                               <a type="button" class="btn btn btn-outline-primary" href="garbage_list">휴지통</a>
                               <a type="button" class="btn btn-outline-primary" href="task_timeline">타임 라인</a>
                               <a type="button" class="btn btn-outline-primary" href="task_create_form"> 새작업 </a>

                       </div> -->

                   </div>

                   <div class="doughnut_1">
                       <canvas id="doughnut_1"></canvas>
                   </div>

                   <div class="bar_chart">
                       <canvas id="bar_chart"></canvas>
                   </div>
				</div>


				<div class="chart_2" >
                   <div class="project_step_chart" style=" overflow: hidden;">
                   </div>

                   <div class ="current_task">
                       <div class="current_name">현재 작업중인 작업</div>
                   </div>
				</div>
			</main>

        </div>
    </div>

<script src="/lkh/js/chart_options.js"></script>
</body>
</html>



