<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<%@ taglib  prefix="form"  uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="/lkh/css/lkh.css">

    <script type="text/javascript">
        $(function() {

            $.ajax({
                url         : '/main_header',
                dataType    : 'text',
                success      : function(data) {
                    $('#header').html(data);
                }
            });

            $.ajax({
                url         : '/main_menu',
                dataType    : 'text',
                success      : function(data) {
                    $('#menubar').html(data);
                }
            });


            $.ajax({
                url         : '/main_footer',
                dataType    : 'text',
                success      : function(data) {
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
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4" >
            <!------------------------------ //개발자 소스 입력 END ------------------------------->
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
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="">프로젝트</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">작업</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">작업 작성</span>
				</div>
			</div>
                
			<div class="container-fluid">
				<form:form action="task_create" method="post" modelAttribute='task' enctype="multipart/form-data" >
					<table width="100%" style="margin-top:7px">
						<tr>
							<td style="text-align:right">
								<button type="submit" class="btn btn-dark btn-sm">저장</button>
								<button type="button" class="btn btn-dark btn-sm" onclick="history.go(-1)">닫기</button>
							</td>
						</tr>
					</table>
					<table class="table" width="100%">
						<colgroup>
							<col width="10%"></col>
							<col width="40%"></col>
							<col width="10%"></col>
							<col width="40%"></col>
						</colgroup>
						<tr>
							<th>프로젝트 단계</th>
							<td>
								<select id="order_by" name="project_step_seq" class="form-select">
	                                <c:forEach var="step" items="${prjStepList}">
	                                    <option  value="${step.project_step_seq}">${step.project_order}: ${step.project_s_name}</option>
	                                </c:forEach>
	                            </select>
							</td>
							<th>작성일</th>
							<td>${todayDate}</td>
						</tr>
						<tr>
							<th>작업명</th>
							<td>
								<input type="text" id="task_subject" class="form-control" name="task_subject" id="task_subject">
	                            <form:errors path="task_subject" class="errors"/>
	                        </td>
							<th rowspan="7">작업 내용</th>
							<td rowspan="7">
								<textarea  rows="20" class="form-control" name="task_content" id="task_content"></textarea>
	                            <form:errors path="task_content" class="errors" />
							</td>
						</tr>
						<tr>
							<th>시작일 ~ 마감일</th>
							<td>
								<table>
									<tr>
										<td><input type="date" class="form-control" id="task_start_time" name="task_start_time" value="${todayDate}" min="2023-07-22" max="2030-12-31" /></td>
										<td>~</td>
										<td><input type="date" class="form-control" id="task_end_time" name="task_end_time" value="${todayDate}" min="2023-07-22" max="2030-12-31" /></td>
									</tr>
								</table>							
							</td>
						</tr>
						<tr>
							<th>우선 순위</th>
							<td>
								<input type="radio" class="form-check-input" name="task_priority" value="0" id="priority0">
	                            <label class="form-check-label" for="priority0">낮음</label>
	                            <input type="radio" class="form-check-input" name="task_priority" value="1" id="priority1" checked>
	                            <label class="form-check-label" for="priority1">보통</label>
	                            <input type="radio" class="form-check-input" name="task_priority" value="2" id="priority2">
	                            <label class="form-check-label" for="priority2">높음</label>
							</td>
						</tr>
						<tr>
							<th>작업 상태</th>
							<td>
								<input type="radio" class="form-check-input" name="task_status" value="0" id="status0" checked>
								<label class="form-check-label" for="status0">예정된 작업</label>
								<input type="radio" class="form-check-input" name="task_status" value="1" id="status1">
	                            <label class="form-check-label" for="status1">진행중인 작업</label>
	                            <input type="radio" class="form-check-input" name="task_status" value="2" id="status2">
	                            <label class="form-check-label" for="status2">완료된 작업</label>
	                            <form:errors path="task_status" class="errors" />
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td>${userInfo.user_name}</td>
						</tr>
						<tr>
							<th>공동 작업자</th>
							<td style="line-height:25px">
								<c:forEach var="user" items="${task_create_form_worker_list}">
	                            	<input type="checkbox" name="workerIdList" value="${user.user_id}" class="form-check-input flex-shrink-0" style="font-size: 1.375em;"> ${user.user_name}<br>
	                            </c:forEach>
							</td>
						</tr>
						<tr>
							<th>파일 첨부</th>
							<td>
	                            <input type="file" class="form-control" id="file1" name="file1" multiple="multiple" >
							</td>
						</tr>
					</table>
					<form:errors path="*" class="errors"/>
	            </form:form>
			</div>
            <!------------------------------ //개발자 소스 입력 END ------------------------------->
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