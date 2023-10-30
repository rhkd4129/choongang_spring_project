<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/static/lkh/css/1.css' />">

    <script type="text/javascript">
     $(document).ready(function() {
         var prevSelectedValue = ""; // 이전 선택한 값 저장
         $("#order_by").on("change", function() {
             var tableHeader = "<thead><tr><th>작업번호</th><th>작업 담당자</th><th>Project Step</th>" +
                 "<th>작업명</th><th>작업시작일</th><th>작업마감일</th><th>우선순위</th><th>작업상태</th></tr></thead>";
             var s = $(this).val();
             if (s !== prevSelectedValue && s === "task_status" ) {
                 prevSelectedValue = s; // 현재 선택한 값으로 업데이트
                 $.ajax({
                     url:'view_status',
                     dataType:'json',
                     success:function(data){
                         console.log("task_status 선택");
                         // 기존의 테이블 요소를 선택하고 삭제
                         $("#listTable").remove(); // tableId는 기존 테이블의 ID입니다.
                         $("#table2").html(""); // 빈 문자열로 설정
                         $("#table3").html(""); // 빈 문자열로 설정
                         $("#table4").html(""); // 빈 문자열로 설정
                         var newTable2 = $("<table>");
                         var newTable3 = $("<table>");
                         var newTable4 = $("<table>");

                         var newTbody2 = $("<tbody>");
                         var newTbody3 = $("<tbody>");
                         var newTbody4 = $("<tbody>");
                         newTable2.append("<caption> 예정된작업 </caption>");
                         newTable3.append("<caption> 진행중인작업 </caption>");
                         newTable4.append("<caption> 완료된 작업 </caption>");

                         newTable2.append(tableHeader);
                         newTable3.append(tableHeader);
                         newTable4.append(tableHeader);
                        for(var i =0 ; i<data.length; i++){
                            if(data[i].task_status === "0"){

                                var newRow2 = "<tr>" +
                                    "<td>"+ data[i].task_id +"</td>" +
                                    "<td>"+ data[i].user_name +"</td>" +
                                    "<td>"+ data[i].project_step_seq +"</td>" +
                                    "<td>"+ data[i].task_subject +"</td>" +
                                    "<td>"+ data[i].task_stat_time +"</td>" +
                                    "<td>"+ data[i].task_end_itme +"</td>" +
                                    "<td>"+ data[i].task_status +"</td>" +
                                    "<td>"+ data[i].task_priority +"</td>" +

                                    "</tr>";
                                newTbody2.append(newRow2);
                            }
                            else if(data[i].task_status === "1"){
                                var newRow3 = "<tr>" +
                                    "<td>"+ data[i].task_id +"</td>" +
                                    "<td>"+ data[i].user_name +"</td>" +
                                    "<td>"+ data[i].project_step_seq +"</td>" +
                                    "<td>"+ data[i].task_subject +"</td>" +

                                    "<td>"+ data[i].task_stat_time +"</td>" +
                                    "<td>"+ data[i].task_end_itme +"</td>" +
                                    "<td>"+ data[i].task_status +"</td>" +
                                    "<td>"+ data[i].task_priority +"</td>" +
                                    "</tr>";
                                newTbody3.append(newRow3);
                            }else if(data[i].task_status === "2"){
                                var newRow4 = "<tr>" +
                                    "<td>"+ data[i].task_id +"</td>" +
                                    "<td>"+ data[i].user_name +"</td>" +
                                    "<td>"+ data[i].project_step_seq +"</td>" +
                                    "<td>"+ data[i].task_subject +"</td>" +

                                    "<td>"+ data[i].task_stat_time +"</td>" +
                                    "<td>"+ data[i].task_end_itme +"</td>" +
                                    "<td>"+ data[i].task_status +"</td>" +
                                    "<td>"+ data[i].task_priority +"</td>" +
                                    "</tr>";
                                newTbody4.append(newRow4);
                            }
                            newTable2.append(newTbody2);
                            newTable3.append(newTbody3);
                            newTable4.append(newTbody4);

                            $("#table2").append(newTable2); // 여기서 "#container"는 원하는 대상 엘리먼트를 가리킵니다.
                            $("#table3").append(newTable3); // 여기서 "#container"는 원하는 대상 엘리먼트를 가리
                            $("#table4").append(newTable4); // 여기서 "#container"는 원하는 대상 엘리먼트를 가리
                            // "<caption> 상태 </caption>"
                        }
                     }
                 });
	        }else if(s !== prevSelectedValue && s === "task_priority" ){
                prevSelectedValue = s;
                 $.ajax({
                     url:'view_status',
                     dataType:'json',
                     success:function(data){
                         console.log("task_priority 선택");
                         $("#listTable").remove(); // tableId는 기존 테이블의 ID입니다.
                         $("#table2").html(""); // 빈 문자열로 설정
                         $("#table3").html(""); // 빈 문자열로 설정
                         $("#table4").html(""); // 빈 문자열로 설정
                         var newTable2 = $("<table>");
                         var newTable3 = $("<table>");
                         var newTable4 = $("<table>");

                         var newTbody2 = $("<tbody>");
                         var newTbody3 = $("<tbody>");
                         var newTbody4 = $("<tbody>");

                         newTable2.append("<caption> 낮음 </caption>");
                         newTable3.append("<caption> 보통 </caption>");
                         newTable4.append("<caption> 높음 </caption>");

                         newTable2.append(tableHeader);
                         newTable3.append(tableHeader);
                         newTable4.append(tableHeader);
                         for(var i =0 ; i<data.length; i++){
                             if(data[i].task_priority === "0"){
                                 var newRow2 = "<tr>" +
                                     "<td>"+ data[i].task_id +"</td>" +
                                     "<td>"+ data[i].user_name +"</td>" +
                                     "<td>"+ data[i].project_step_seq +"</td>" +
                                     "<td>"+ data[i].task_subject +"</td>" +

                                     "<td>"+ data[i].task_stat_time +"</td>" +
                                     "<td>"+ data[i].task_end_itme +"</td>" +
                                     "<td>"+ data[i].task_status +"</td>" +
                                     "<td>"+ data[i].task_priority +"</td>" +
                                     "</tr>";
                                 newTbody2.append(newRow2);
                             }
                             else if(data[i].task_priority === "1"){
                                 var newRow3 = "<tr>" +
                                     "<td>"+ data[i].task_id +"</td>" +
                                     "<td>"+ data[i].user_name +"</td>" +
                                     "<td>"+ data[i].project_step_seq +"</td>" +
                                     "<td>"+ data[i].task_subject +"</td>" +

                                     "<td>"+ data[i].task_stat_time +"</td>" +
                                     "<td>"+ data[i].task_end_itme +"</td>" +
                                     "<td>"+ data[i].task_status +"</td>" +
                                     "<td>"+ data[i].task_priority +"</td>" +
                                     "</tr>";
                                 newTbody3.append(newRow3);
                             }
                             else if(data[i].task_priority === "2"){
                                 var newRow4 = "<tr>" +
                                     "<td>"+ data[i].task_id +"</td>" +
                                     "<td>"+ data[i].user_name +"</td>" +
                                     "<td>"+ data[i].project_step_seq +"</td>" +
                                     "<td>"+ data[i].task_subject +"</td>" +

                                     "<td>"+ data[i].task_stat_time +"</td>" +
                                     "<td>"+ data[i].task_end_itme +"</td>" +
                                     "<td>"+ data[i].task_status +"</td>" +
                                     "<td>"+ data[i].task_priority +"</td>" +
                                     "</tr>";
                                 newTbody4.append(newRow4);
                             }


                             newTable2.append(newTbody2);
                             newTable3.append(newTbody3);
                             newTable4.append(newTbody4);
                             $("#table2").append(newTable2); // 여기서 "#container"는 원하는 대상 엘리먼트를 가리킵니다.
                             $("#table3").append(newTable3); // 여기서 "#container"는 원하는 대상 엘리먼트를 가리
                             $("#table4").append(newTable4); // 여기서 "#container"는 원하는 대상 엘리먼트를 가리

                         }
                     }
                 })
            }else if(s !== prevSelectedValue && s === "task_end_itme" ){
                prevSelectedValue = s;
                alert('마감일별');
            }else{
                prevSelectedValue = s;
                alert('단계별');

            }
	    });
	});

    </script>
</head>
<body>
    <h1>Task List</h1>
    <a href="task_create_view">새작업 </a>



    <select id="order_by">
        <option value="task_status">상태별</option>
        <option value="task_priority">우선순위별</option>
        <option value="task_end_itme" selected>작업 마감일별</option>
        <option value="project_step_seq">단계별 </option>
    </select>

        <div id="table1">
            <table id="listTable">
                <thead>
                <tr>
                    <th>작업번호</th>
                    <th>작업 담당자 </th>
                    <th>Project Step</th>
                    <th>작업명</th>
                    <th>작업시작일</th>
                    <th>작업마감일</th>
                    <th>우선순위</th>
                    <th>작업상태</th>
                </tr>
                </thead>
                <tbody id="tbodys">
                <c:forEach var="task" items="${taskList}">
                    <tr>
                        <td>${task.task_id}</td>
                        <td>${task.user_name}</td>
                        <td>${task.project_step_seq}</td>
                        <td>${task.task_subject}</td>
                        <td>${task.task_stat_time}</td>
                        <td>${task.task_end_itme}</td>
                        <td>
                            <c:choose>
                                <c:when test="${task.task_priority == '0'}"> 낮음 </c:when>
                                <c:when test="${task.task_priority == '1'}"> 보통   </c:when>
                                <c:when test="${task.task_priority == '2'}"> 높음 </c:when>
                            </c:choose>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${task.task_status == '0'}">예정</c:when>
                                <c:when test="${task.task_status == '1'}">진행중</c:when>
                                <c:when test="${task.task_status == '2'}">완료됨</c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class ="table2" id="table2"></div>
        <div class ="table3" id="table3"></div>
        <div class ="table4" id="table4"></div>



</body>
</html>
