<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
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
                    <div class="status-text"></div>
                    <c:forEach var="task" items="${taskStatus0}">
                        <div class="task">
                            <a href='task_detail?task_id=${task.task_id}&project_id=${task.project_id}'>${task.task_subject}</a>
                        </div>
                    </c:forEach>
                </div>


                <div class="task_status_1">
                    <div class="status-text"></div>
                    <c:forEach var="task" items="${taskStatus1}">
                        <div class="task">
                            <a href='task_detail?task_id=${task.task_id}&project_id=${task.project_id}'>${task.task_subject}</a>
                        </div>
                    </c:forEach>
                </div>

                <div class="task_status_2">
                    <div class="status-text"></div>
                    <c:set var="loop_flag" value="false"/>
                    <c:forEach var="task" items="${taskStatus2}" varStatus="status">

                        <c:if test="${not doneLoop}">
                            <div class="task">
                                <a href='task_detail?task_id=${task.task_id}&project_id=${task.project_id}'>${task.task_subject}</a>
                            </div>
                            <c:if test="${status.count == 5}">
                                <div class="task">
                                    .....
                                </div>
                                <c:set var="doneLoop" value="true"/>
                            </c:if>
                        </c:if>
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
