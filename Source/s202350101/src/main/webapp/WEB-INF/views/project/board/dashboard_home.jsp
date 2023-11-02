<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/lkh/css/1.css">
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
                url:"<%=request.getContextPath()%>/dashboard_doughnut",
                //data:data
                dataType:'json',
                success:function(response){
                    const doughnutCtx = document.getElementById('doughnut_1').getContext('2d');

                    const doughnut_data = {
                        labels: ['예정', '진행중', '완료됨'],
                        datasets: [{
                            data: response,
                            backgroundColor: ['orange', 'pink', 'yellow'], // Corrected the color
                            borderWidth: 1
                        }]
                    };
                    createDrawChart(doughnutCtx, 'doughnut', doughnut_data, doughnut_options);
                }
            });

            $.ajax({
                url:"<%=request.getContextPath()%>/dashboard_bar",
                //data:data
                dataType:'json',
                success:function(workload){
                    const barCtx = document.getElementById('bar_chart').getContext('2d');

                    console.log(workload);
                    var labels = []
                    var stats_0=[]
                    var stats_1=[]
                    var stats_2=[]
                    for(let i =0; i<workload.length;i++){
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
                                backgroundColor: 'red',
                            },
                            {
                                label: '진행중',
                                data: stats_1,
                                backgroundColor: 'blue',
                            },
                            {
                                label: '완료',
                                data: stats_2,
                                backgroundColor: 'orange',
                            },
                        ]
                    };
                    createDrawChart(barCtx, 'bar', horizontalStackBarData, horizontalStackBarOption);
                }
            });
        });
    </script>
</head>
<body>
<!-- -
       window.onload = function() {

   }
- -->

<div id="header"></div>
<div class="container-fluid">
    <div class="row">

        <!-- 메뉴 -->
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div class="doughnut_1">
                <canvas id="doughnut_1"></canvas>
            </div>
            <a type="button"   class="btn btn-primary" href="task_list">viewer_table</a>
            <a type="button"   class="btn btn-primary" href="task_garbage">task_garbage</a>
            <a type="button"   class="btn btn-primary" href="task_timeline_view">task_timeline</a>
            <div class="bar_chart">
                <canvas id="bar_chart"></canvas>
            </div>
        </main>

    </div>
</div>


<div id="footer"></div>



<script src="/lkh/js/2.js"></script>
</body>
</html>



