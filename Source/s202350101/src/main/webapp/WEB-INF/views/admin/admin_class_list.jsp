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
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <!------------------------------ //개발자 소스 입력 START ------------------------------->
			<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
			  <symbol id="house-door-fill" viewBox="0 0 16 16">
			    <path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5z"></path>
			  </symbol>
			</svg>		
			<nav aria-label="breadcrumb" style="padding-top:5px;padding-left: calc(var(--bs-gutter-x) * 0.5);">
			    <ol class="breadcrumb breadcrumb-chevron p-1">
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis" href="/main">
			          <svg class="bi" width="16" height="16"><use xlink:href="#house-door-fill"></use></svg>
			          <span class="visually-hidden">Home</span>
			        </a>
			      </li>
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="/admin_projectmanager">관리자 설정</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">반 목록</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:20px;height:45px">
					<span class="apptitle">관리자 설정</span>
				</div>
			</div>
			
            <div class="container-fluid">
                
				<div id="admin_page_list" class="bd-example m-0 border-0">
					<nav>
						<div class="nav nav-tabs mb-3" id="nav-tab" role="tablist">
							<button class="nav-link" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-1" type="button" role="tab" aria-controls="nav-1" aria-selected="false" onclick="location.href='/admin_projectmanager'">팀장 권한 설정</button>
							<button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-2" type="button" role="tab" aria-controls="nav-2" aria-selected="false" tabindex="-1" onclick="location.href='/admin_board'">게시판 관리</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-3" type="button" role="tab" aria-controls="nav-3" aria-selected="false" tabindex="-1" onclick="location.href='/admin_approval'">프로젝트 관리</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-4" type="button" role="tab" aria-controls="nav-4" aria-selected="false" tabindex="-1" onclick="location.href='/admin_add_class'">반 생성</button>
							<button class="nav-link active" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-5" type="button" role="tab" aria-controls="nav-5" aria-selected="true" tabindex="-1" onclick="location.href='/admin_class_list'">반 목록</button>
						</div>
					</nav>
				</div>

                <table class="table" style="margin-top:5px;margin-bottom:5px">
                    <thead>
                    <tr class="table-warning">
                        <th>학원 위치</th>
                        <th>반 번호</th>
                        <th>담당 강사</th>
                        <th>강의 이름</th>
                        <th>시작 날짜</th>
                        <th>종료 날짜</th>
                        <th>삭제</th>
                    </tr>
                    <c:forEach items="${CRList}" var="list">
                        <tr>
                            <td>${list.class_area}</td>
                            <td>${list.class_room_num}</td>
                            <td>${list.class_master}</td>
                            <td>${list.class_name}</td>
<%--                            <td><fmt:formatDate value="${list.class_start_date}" pattern="yy/MM/dd"/></td>--%>
                            <td>${list.startDate}</td>
                            <td>${list.endDate}</td>
                            <td><a href="/admin_del_class?class_id=${list.class_id}">삭제</a> </td>
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