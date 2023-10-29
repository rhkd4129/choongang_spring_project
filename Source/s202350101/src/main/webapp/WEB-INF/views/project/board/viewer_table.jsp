<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task List</title>
</head>
<body>
    <h1>Task List</h1>
    
    <table border="1">
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
    </table>
</body>
</html>
