<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        var rows=[];

        function drawChart() {
            // var container = $("#timeline");
            var chart = new google.visualization.Timeline($("#timeline"));
            var dataTable = new google.visualization.DataTable();

            dataTable.addColumn({ type: 'string', id: 'user_id' });
            dataTable.addColumn({ type: 'string', id: 'task_name' });
            dataTable.addColumn({ type: 'date', id: 'Start' });
            dataTable.addColumn({ type: 'date', id: 'End' });

            dataTable.addRows(rows);
            var options = {timeline: { groupByRowLabel: false }};
            chart.draw(dataTable,options);
        }

        $(function() {
            $.ajax({
                url			: '/main_header',
                dataType 	: 'text',
                success		: function(data) {
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

            $.ajax({
                url			: '/task_timeline',
                dataType 	: 'json',
                success		: function(data) {

                    for (let i = 0; i < data.length; i++) {
                        let start_year = Number(data[i].task_stat_time.slice(0, 4));

                        let start_month = Number(data[i].task_stat_time.slice(5, 7))

                        let start_day = Number(data[i].task_stat_time.slice(8, 10));

                        let end_year = Number(data[i].task_end_itme.slice(0, 4));
                        let end_month = Number(data[i].task_end_itme.slice(5, 7));
                        let end_day = Number(data[i].task_end_itme.slice(8, 10));
                        rows.push([
                            data[i].user_name,
                            data[i].task_subject,
                            new Date(start_year, start_month - 1, start_day), // 월은 0부터 시작하므로 -1 해야 함
                            new Date(end_year, end_month - 1, end_day) // 월은 0부터 시작하므로 -1 해야 함
                        ]);

                    }

                    console.log(rows);
                    google.charts.load('current', {'packages':['timeline']});
                    google.charts.setOnLoadCallback(drawChart);

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
                Task Time Line

                <div id="timeline" style="height: 300px;"></div>


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
