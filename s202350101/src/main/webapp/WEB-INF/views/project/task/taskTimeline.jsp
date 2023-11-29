<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        var rows=[];
        $(function() {
            var container = document.getElementById("timeline");
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

            $.ajax({
                url: '/task_timeline_asyn',
                dataType: 'json',
                success: function (data) {
                    var rows = {};
                    var predefinedColors = ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF', '#00FFFF', '#FF8000', '#0080FF'];

                    google.charts.load('current', { packages: ['timeline'] });
                    google.charts.setOnLoadCallback(drawChart);

                    function drawChart() {
                        var chart = new google.visualization.Timeline(container);
                        var dataTable = new google.visualization.DataTable();
                        dataTable.addColumn({ type: 'string', id: 'user_id' });
                        dataTable.addColumn({ type: 'string', id: 'task_name' });
                        dataTable.addColumn({ type: 'date', id: 'Start' });
                        dataTable.addColumn({ type: 'date', id: 'End' });
                        // dataTable.addColumn({ type: 'string', role: 'style' }); // 색상 열 추가

                        for (let i = 0; i < data.length; i++) {
                        //     let user_id = data[i].user_id;
                        //     if (!rows[user_id]) {
                        //         // 사용자(user_id)에 색상을 할당하지 않은 경우, predefinedColors에서 색상 할당
                        //         rows[user_id] = predefinedColors.shift(); // predefinedColors에서 색상 추출
                        //     }

                            let start_year = Number(data[i].task_start_time.slice(0, 4));
                            let start_month = Number(data[i].task_start_time.slice(5, 7));
                            let start_day = Number(data[i].task_start_time.slice(8, 10));

                            let end_year = Number(data[i].task_end_time.slice(0, 4));
                            let end_month = Number(data[i].task_end_time.slice(5, 7));
                            let end_day = Number(data[i].task_end_time.slice(8, 10));

                            dataTable.addRow([
                                data[i].user_name,
                                data[i].task_subject,
                                new Date(start_year, start_month - 1, start_day),
                                new Date(end_year, end_month - 1, end_day)
                                // rows[user_id] // 사용자(user_id)에 할당된 색상
                            ]);
                        }

                        var options = {
                            timeline: { groupByRowLabel: false },
                            hAxis:{format:'M/d',gridlines:{count:-1}},
                            minorGridlines: {count: -1 },// 작은 그리드 라인 수 조절

                            tooltip: {format: 'M/d'}// 툴팁의 날짜 형식을 변경
                        };
                        chart.draw(dataTable, options);
                    }
                }
            });

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
				      <li class="breadcrumb-item active" aria-current="page">타임 라인</li>
				    </ol>
				</nav>
				<div class="container-fluid">
					<div style="margin-top:15px;height:45px">
						<span class="apptitle">타임 라인</span>
					</div>
				</div>

                <div id="timeline" style=" height:1000px;   ;margin: 2%"></div>


                <!------------------------------ //개발자 소스 입력 END ------------------------------->
            </main>

        </div>
    </div>

    <!-- FOOTER -->
    <footer class="footer py-2">
        <div id="footer" class="container">
        </div>
    </footer>

</body>
</html>
