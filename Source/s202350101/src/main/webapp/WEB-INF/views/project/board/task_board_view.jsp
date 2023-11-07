<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="/lkh/css/lkh.css">
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
        });
    </script>
</head>
<body>

<header id="header"></header>

<div class="container-fluid">
    <div class="row">

        <!-- 메뉴 -->
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

                <h3> 총 작업수 : ${taskCount }</h3>


            <div class="board">
                <div class="task_status_0">
                    <c:forEach var="task_0" items="${taskStatus0}">
                            <a href='task_detail?task_id=${task_0.task_id}&project_id=${task_0.project_id}'>${task_0.task_subject}</a>
                    </c:forEach>
                </div>


                <div class="task_status_1">
                    <c:forEach var="task_1" items="${taskStatus1}">
                            <a href='task_detail?task_id=${task_1.task_id}&project_id=${task_1.project_id}'>${task_1.task_subject}</a>
                    </c:forEach>
                </div>

                <div class="task_status_2">
                    <c:forEach var="task_2" items="${taskStatus2}">
                            <a href='task_detail?task_id=${task_2.task_id}&project_id=${task_2.project_id}'>${task_2.task_subject}</a>

                    </c:forEach>
                </div>
            </div>

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
