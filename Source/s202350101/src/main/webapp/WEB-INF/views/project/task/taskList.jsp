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
        // 테이블을 업데이트하는 함수 정렬함수
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
                    "<td>" + data[i].rn + "</td>" +
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

        function toggleButtonText(keyword_division , keyword) {
            var table = $("#table1"); // 기본 테이블
            var button = $("#sort");
            console.log(keyword_division,keyword);
            if (button.text() === "내") {
                button.text("오");
                $("image").src=""
                $.ajax({
                    url: '/task_time_desc',
                    data:{"keyword":keyword, "keyword_division":keyword_division},
                    dataType: 'json',
                    success: function (data) {

                        console.log("내림차순으로 데이터 ");
                        updateTable(table, data.onelist);
                    }
                });
            } else {
                button.text("내");
                console.log(keyword_division,keyword);
                $.ajax({
                    url: '/task_time_acsc',
                    data:{"keyword":keyword, "keyword_division":keyword_division},
                    dataType: 'json',
                    success: function (data) {
                        console.log("오름차순으로 데이터 ");

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
        <div class="container border border-black p-3">
            <form action="task_list" method="GET">
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <label for="dropdown">항목별 검색</label>
                        <select class="form-control" id="dropdown" name="keyword_division">
                            <option value="task_subject">작업명</option>
                            <option value="project_s_name">단계별</option>
                            <option value="user_name">작업자별</option>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="keyword">검색어</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" required>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label></label>
                        <button type="submit" class="btn btn-primary">검색</button>
                    </div>
                     <a href="task_list" class="btn btn-primary">초기화</a>
                </div>
            </form>
        </div>


                <h3>총 작업수: ${taskCount}</h3>
                <a class="btn btn-primary" href="task_create_form">새 작업</a>


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
                        <th>마감일<button id="sort" onclick="toggleButtonText('${keyword_division}','${keyword}')"><img  src="/images/sort.png" width="15px" height="15px" alt="정렬"></button></th>
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
