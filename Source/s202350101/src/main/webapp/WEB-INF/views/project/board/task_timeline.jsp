<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <!--CSS START -->
    <!-- CSS END -->

    <!-- JS START -->
    <!-- JS END -->z

    <script type="text/javascript">
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
                    console.log(data);
                    for(var i =0;i<data.length;i++ ){
                        console.log(data[i].user_id);
                        console.log(data[i].task_subject);
                        console.log(data[i].task_stat_time);
                        console.log(data[i].task_end_itme);


                    }

                }
            });




            google.charts.load('current', {'packages':['timeline']});
            google.charts.setOnLoadCallback(drawChart);
            function drawChart() {
                var container = document.getElementById('timeline');
                var chart = new google.visualization.Timeline(container);
                var dataTable = new google.visualization.DataTable();

                dataTable.addColumn({ type: 'string', id: 'user_id' });
                dataTable.addColumn({ type: 'string', id: 'task_name' });
                dataTable.addColumn({ type: 'date', id: 'Start' });
                dataTable.addColumn({ type: 'date', id: 'End' });

                // 날짜로 변환된 데이터
                dataTable.addRows([
                    [ 'user_1','1 task', new Date(2020, 1, 30), new Date(2020, 2, 4) ],
                    [ 'user_1','2 task', new Date(2020, 2, 2), new Date(2020, 2, 8) ],
                    [ 'user_1', '3 task', new Date(2020, 2, 8), new Date(2020, 2, 12) ]
                ]);
                var options = {
                    timeline: { groupByRowLabel: false }
                };
                chart.draw(dataTable,options);
            }



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
