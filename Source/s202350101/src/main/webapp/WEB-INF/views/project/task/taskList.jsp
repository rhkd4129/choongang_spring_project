<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task List</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/lkh/css/lkh.css">
    <script type="text/javascript">
        // 테이블을 업데이트하는 함수
        function updateTable(table, data) {
            var tbody = $(table).find('tbody');

            tbody.empty();
            for (var i = 0; i < data.length; i++) {
                var statusText = "";
                var task_priority = "";

                switch (data[i].task_status) {
                    case '0':
                        statusText = '낮음';
                        break;
                    case '1':
                        statusText = '중간';
                        break;
                    case '2':
                        statusText = '높음';
                        break;
                }

                switch (data[i].task_priority) {
                    case '0':
                        task_priority = '예정';
                        break;
                    case '1':
                        task_priority = '진행중';
                        break;
                    case '2':
                        task_priority = '완료된 작업';
                        break;
                }

                var newRow = "<tr>" +
                    "<td>" + data[i].task_id + "</td>" +
                    "<td>" + data[i].user_name + "</td>" +
                    "<td>" + data[i].project_s_name + "</td>" +
                    "<td><a href='task_detail?task_id=" + data[i].task_id + "&project_id=" + data[i].project_id + "'>" + data[i].task_subject + "</a></td>" +
                    "<td>" + data[i].task_stat_time + "</td>" +
                    "<td>" + data[i].task_end_itme + "</td>" +
                    "<td>" + statusText + "</td>" +
                    "<td>" + task_priority + "</td>" +
                    "</tr>";
                tbody.append(newRow);
            }
        }

        $(document).ready(function () {
            $.ajax({
                url: '/main_header',
                dataType: 'html',
                success: function (data) {
                    console.log("ddd");
                    $('#header').html(data);
                }
            });
            $.ajax({
                url: '/main_menu',
                dataType: 'html',
                success: function (data) {
                    $('#menubar').html(data);
                }
            });
            $.ajax({
                url: '/main_footer',
                dataType: 'html',
                success: function (data) {
                    $('#footer').html(data);
                }
            });
        });

        function toggleButtonText() {
            var table = $("#table1"); // 기본 테이블
            var button = $("#sort");

            if (button.text() === "내") {
                button.text("오");
                $.ajax({
                    url: '/task_time_desc',
                    dataType: 'json',
                    success: function (data) {
                        console.log("내림차순으로 데이터 ");
                        console.log(data);
                        updateTable(table, data.onelist);
                    }
                });
            } else {
                button.text("내");
                $.ajax({
                    url: '/task_time_acsc',
                    dataType: 'json',
                    success: function (data) {
                        console.log("오름차순으로 데이터 ");
                        console.log(data);
                        updateTable(table, data.onelist);
                    }
                });
            }
        }

    </script>
</head>
<body>


<div id="header"></div>
<div class="container-fluid">
    <div class="row">
        <!-- 메뉴 -->
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <h1>Task List</h1>
            <a class="btn btn-primary" href="task_create_form">새 작업</a>

            <select id="order_by" class="form-control">
                <option value="task_status">상태별</option>
                <option value="task_priority">우선순위별</option>
                <option value="task_end_itme" selected>작업 마감일별</option>
                <option value="project_step_seq">단계별</option>
            </select>

            <h3>총 작업수: ${taskCount}</h3>
            <c:choose>
                <c:when test="${not empty param.status}">
                    <div class="alert alert-success" role="alert">저장 완료!</div>
                </c:when>
            </c:choose>
            <c:set var="num" value="${page.total - page.start + 1 }"></c:set>

            <div class="table-responsive">
                <table class="table table-striped" id="table1">
                    <thead>
                    <tr>
                        <th>작업번호</th>
                        <th>작업 담당자</th>
                        <th>Project Step</th>
                        <th>작업명</th>
                        <th>작업시작일</th>
                        <th>마감일<button id="sort" onclick='toggleButtonText()'>내</button></th>
                        <th>우선순위</th>
                        <th>작업상태</th>
                    </tr>
                    </thead>
                    <tbody id="tbodys">
                    <c:forEach var="task" items="${taskList}">
                        <tr>
                            <td>${task.rn}</td>
                            <td>${task.user_name}</td>
                            <td>${task.project_s_name}</td>
                            <td><a href='task_detail?task_id=${task.task_id}&project_id=${task.project_id}'>${task.task_subject}</a></td>
                            <td>${task.task_stat_time}</td>
                            <td>${task.task_end_itme}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${task.task_priority == '0'}">낮음</c:when>
                                    <c:when test="${task.task_priority == '1'}">보통</c:when>
                                    <c:when test="${task.task_priority == '2'}">높음</c:when>
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
                        <c:set var="num" value="${num - 1 }"></c:set>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <c:if test="${page.startPage > page.pageBlock}">
                <a href="task_list?currentPage=${page.startPage - page.pageBlock}" class="btn btn-primary">이전</a>
            </c:if>
            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                <a href="task_list?currentPage=${i}" class="btn btn-primary">${i}</a>
            </c:forEach>
            <c:if test="${page.endPage < page.totalPage}">
                <a href="task_list?currentPage=${page.startPage + page.pageBlock}" class="btn btn-primary">다음</a>
            </c:if>
        </main>
    </div>
</div>

<div id="footer"></div>

</body>
</html>
