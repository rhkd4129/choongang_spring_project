<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/lkh/css/1.css' />">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
   	<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function() {
	    // HTML 구조는 준비되었지만, 모든 리소스의 로딩이 완료되지 않았을 때 실행됩니다.
   		$.ajax({
	   			url:"<%=request.getContextPath()%>/dashboard",  
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
   			success:function(response){
   				const barCtx = document.getElementById('bar_chart').getContext('2d');
   			  
   				console.log(response);
   				const horizontalStackBarData = {
   					  labels: ['이광현', '강준우', '문경훈', '차예지', '이진희', '조미혜', '황인정'],
   					  datasets: [
   					    {
   					      label: '진행전',
   					      data: [2, 2, 3, 2, 2, 3, 2],
   					      backgroundColor: 'red',
   					    },
   					    {
   					      label: '진행중',
   					      data: [2, 2, 3, 2, 2, 3, 2],
   					      backgroundColor: 'blue',
   					    },
   					    {
   					      label: '완료',
   					      data: [2, 2, 3, 2, 2, 3, 2],
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
	
    <div class="doughnut_1">
        <canvas id="doughnut_1"></canvas>
    </div>
  <a href="viewer_table">viewer_table</a>
  
    <div class="bar_chart">
        <canvas id="bar_chart"></canvas>
    </div>
  
 	<script src="/static/lkh/js/2.js"></script>
</body>
</html>


 
