<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>


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


        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

            <h3> 총 작업수 : ${taskCount }</h3>
            <c:choose>
                <c:when test="${not empty param.status}">
                    <h2>저장 완료!</h2>
                </c:when>
            </c:choose>
            <c:set var="num" value="${page.total-page.start+1 }"></c:set>
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
                    <c:forEach var="task" items="${garbageList}">
                        <tr>
                            <td>${task.rn}</td>
                                <%--                            <td>${task.task_id}</td>--%>
                            <td>${task.user_name}</td>
                            <td>${task.project_s_name}</td>
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
                        <c:set var="num" value="${num - 1 }"></c:set>
                    </c:forEach>
                    </tbody>
                </table>

                <c:if test="${page.startPage > page.pageBlock }">
                    <a href="task_list?currentPage=${page.startPage-page.pageBlock}">[이전]</a>
                </c:if>
                <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                    <a href="task_list?currentPage=${i}">[${i}]</a>
                </c:forEach>
                <c:if test="${page.endPage < page.totalPage }">
                    <a href="task_list?currentPage=${page.startPage+page.pageBlock}">[다음]</a>
                </c:if>
            </div>
        </main>
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
