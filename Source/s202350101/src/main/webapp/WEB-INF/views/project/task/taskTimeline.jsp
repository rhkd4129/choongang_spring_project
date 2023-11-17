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
                        dataTable.addColumn({ type: 'string', role: 'style' }); // 색상 열 추가

                        for (let i = 0; i < data.length; i++) {
                            let user_id = data[i].user_id;
                            if (!rows[user_id]) {
                                // 사용자(user_id)에 색상을 할당하지 않은 경우, predefinedColors에서 색상 할당
                                rows[user_id] = predefinedColors.shift(); // predefinedColors에서 색상 추출
                            }

                            let start_year = Number(data[i].task_stat_time.slice(0, 4));
                            let start_month = Number(data[i].task_stat_time.slice(5, 7));
                            let start_day = Number(data[i].task_stat_time.slice(8, 10));

                            let end_year = Number(data[i].task_end_itme.slice(0, 4));
                            let end_month = Number(data[i].task_end_itme.slice(5, 7));
                            let end_day = Number(data[i].task_end_itme.slice(8, 10));

                            dataTable.addRow([
                                data[i].user_name,
                                data[i].task_subject,
                                new Date(start_year, start_month - 1, start_day),
                                new Date(end_year, end_month - 1, end_day),
                                rows[user_id] // 사용자(user_id)에 할당된 색상
                            ]);
                        }

                        var options = { timeline: { groupByRowLabel: false } };
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
                <h2 style="margin: 3%"> 작업 타임라인 </h2>

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
