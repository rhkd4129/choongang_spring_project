<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <style text="text/css">
        #test {
            margin-top: 5%;
            margin-bottom: 15%;
        }

        #admin_page_list {
            margin-bottom: 5%;
        }
    </style>

    <script type="text/javascript">
        $(function () {

            $.ajax({
                url: '/main_header',
                dataType: 'html',
                success: function (data) {
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

    </script>
</head>

<body>

<!-- HEADER -->
<header id="header"></header>


<!-- CONTENT -->
<div class="container-fluid">
    <div class="row">

        <!-- 메뉴 -->
        <div id="menubar"
             class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <!------------------------------ //개발자 소스 입력 START ------------------------------->

            <div id="test">
                <div id="admin_page_list">
                    <div class="btn btn-secondary" onclick="location.href='/admin_projectmanager'">팀장 권한 설정</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_board'">게시판 관리</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_approval'">프로젝트 관리</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_add_class'">반 생성</div>
                    <div class="btn btn-primary" onclick="location.href='/admin_class_list'">반 목록</div>
                </div>


                <table class="table">
                    <thead>
                    <tr class="table-warning">
                        <th>학원 위치</th>
                        <th>반 번호</th>
                        <th>담당 강사</th>
                        <th>강의 이름</th>
                        <th>시작 날짜</th>
                        <th>종료 날짜</th>
                    </tr>
                    <c:forEach items="${CRList}" var="list">
                        <tr>
                            <td>${list.class_area}</td>
                            <td>${list.class_room_num}</td>
                            <td>${list.class_master}</td>
                            <td>${list.class_name}</td>
<%--                            <td><fmt:formatDate value="${list.class_start_date}" pattern="yy/MM/dd"/></td>--%>
                            <td>${list.class_start_date}</td>
                            <td>${list.class_end_date}</td>
                        </tr>
                    </c:forEach>
                    </thead>
                </table>
            </div>
            <!------------------------------ //개발자 소스 입력 END ------------------------------->
        </main>

    </div>
</div>

<!-- FOOTER -->
<footer class="footer py-2">
    <div id="footer" class="container"></div>
</footer>


</body>

</html>