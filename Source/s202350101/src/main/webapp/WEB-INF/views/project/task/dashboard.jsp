<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/lkh/css/lkh.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script type="text/javascript">
        $(function() {
            $.ajax({
                url			: '/main_header',
                dataType 	: 'text',
                success		: function(data) {
                    console.log("ddd");
                    $('#header').html(data);
                }
            });

            $.ajax({
                url			: '/main_menu',
                dataType 	: 'text',
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
                        labels: ['예정', '진행중', '완료됨'],
                        datasets: [{
                            data: response,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.5)', // 빨간색
                                'rgba(54, 162, 235, 0.5)', // 파란색
                                'rgba(153, 102, 255, 0.5)', // 보라색
                            ],
                            borderColor: [
                                'rgb(255, 99, 132)', // 빨간색
                                'rgb(54, 162, 235)', // 파란색
                                'rgb(153, 102, 255)', // 보라색
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
                    console.log(workload);
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
                                label: '진행전',
                                data: stats_0,
                                backgroundColor: 'rgba(255, 99, 132, 0.5)',  // 빨간색 배경
                                borderColor: 'rgb(255, 99, 132)',  // 빨간색 테두리
                            },
                            {
                                label: '진행중',
                                data: stats_1,
                                backgroundColor: 'rgba(75, 192, 192, 0.5)',  // 녹색 배경
                                borderColor: 'rgb(75, 192, 192)',  // 녹색 테두리
                            },
                            {
                                label: '완료',
                                data: stats_2,
                                backgroundColor: 'rgba(153, 102, 255, 0.5)',  // 보라색 배경
                                borderColor: 'rgb(153, 102, 255)',  // 보라색 테두리
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
                <div class="chart">
                    <div class="project_chart">
                        <canvas id="project_chart"></canvas>
                        <div class="controller">
                            <a type="button"   class="btn btn-primary" href="task_list">작업 목록</a>
                            <a type="button"   class="btn btn-primary" href="garbage_list">휴지통</a>
                            <a type="button"  class="btn btn-primary" href="task_timeline">타임 라인</a>
                            <a type="button"  class="btn btn-primary" href="task_board">작업 보드 </a>
                        </div>

                    </div>

                    <div class="doughnut_1">
                        <canvas id="doughnut_1"></canvas>
                    </div>

                    <div class="bar_chart">
                        <canvas id="bar_chart"></canvas>
                    </div>
                </div>


            </main>

        </div>
    </div>


    <div id="footer"></div>
<script src="/lkh/js/chart_options.js"></script>
</body>
</html>



