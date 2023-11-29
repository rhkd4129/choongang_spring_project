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
    <style>

        .img_box{
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            padding: 2%;
        }
        .attached_img{

            width: 150px;
            height: 150px;
            padding: 1%;
            margin: 2%;
            border: 1px solid black;

        }
        body{overflow: auto; /* 넘치는 부분은 스크롤로 처리 */}
    </style>
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
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="prj_home">프로젝트</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">작업</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">작업 수정</span>
				</div>
			</div>
			
			<div class="container-fluid">
            	<form:form action="task_update" method="post" modelAttribute='task' enctype="multipart/form-data" >
	                <input type="hidden" name="task_id" value="${task.task_id}">
	                <input type="hidden" name="project_id" value="${task.project_id }">

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
                                        <option value="${step.project_step_seq}" <c:if test="${  step.project_s_name eq task.project_s_name}">selected</c:if>>
                                                ${step.project_order}: ${step.project_s_name}
                                        </option>
                                    </c:forEach>
                                </select>
							</td>
							<th>작성일</th>
							<td>${task.create_date}</td>
						</tr>
						<tr>
							<th>작업명</th>
							<td>
								<input type="text" class="form-control" name="task_subject" id="task_subject" value="${task.task_subject}">
                                <form:errors path="task_subject" class="errors"/>
	                        </td>
							<th rowspan="7">작업 내용</th>
							<td rowspan="7">
								<textarea  rows="20" class="form-control" name="task_content" id="task_content">${task.task_content}</textarea>
                                <form:errors path="task_content" class="errors" />
							</td>
						</tr>
						<tr>
							<th>시작일 ~ 마감일</th>
							<td>
								<table>
									<tr>
										<td><input type="date" class="form-control" id="task_start_time" name="task_start_time" value="${task.task_start_time}" min="2023-07-22" max="2030-12-31" /></td>
										<td>~</td>
										<td><input type="date" class="form-control" id="task_end_time" name="task_end_time" value="${task.task_end_time}"  min="2023-07-22" max="2030-12-31" /></td>
									</tr>
								</table>							
							</td>
						</tr>
						<tr>
							<th>우선 순위</th>
							<td>
								<input type="radio" class="form-check-input" name="task_priority" value="0" id="priority0"
                                <c:if test="${task.task_priority == 0}">checked</c:if>>
                                <label class="form-check-label" for="priority0">낮음</label>
                                
	                            <input type="radio" class="form-check-input" name="task_priority" value="1" id="priority1"
                                <c:if test="${task.task_priority == 1}">checked</c:if>>
                                <label class="form-check-label" for="priority1">보통</label>
                                
	                            <input type="radio" class="form-check-input" name="task_priority" value="2" id="priority2"
                                <c:if test="${task.task_priority == 2}">checked</c:if>>
                                <label class="form-check-label" for="priority2">높음</label>
                                <form:errors path="task_priority" class="errors" />
							</td>
						</tr>
						<tr>
							<th>작업 상태</th>
							<td>
								<input type="radio" class="form-check-input" name="task_status" value="0" id="status0"
                                <c:if test="${task.task_status == 0}">checked</c:if>>
                                <label class="form-check-label" for="status0">예정된 작업</label>
								
								<input type="radio" class="form-check-input" name="task_status" value="1" id="status1"
                                <c:if test="${task.task_status == 1}">checked</c:if>>
                                <label class="form-check-label" for="status1">진행중인 작업</label>
								
	                            <input type="radio" class="form-check-input" name="task_status" value="2" id="status2"
                                <c:if test="${task.task_status == 2}">checked</c:if>>
                                <label class="form-check-label" for="status2">완료된 작업</label>
                                <form:errors path="task_status" class="errors" />
							</td>
						</tr>
						<tr>
							<th>작성자</th>
							<td>${task.user_name}</td>
						</tr>
						<tr>
							<th>공동 작업자</th>
							<td style="line-height:25px">
								<c:forEach var="all_user" items="${task_create_form_worker_list}">
                                    <c:set var="isChecked" value=""/>
                                    <c:forEach var="select_user" items="${taskSubList}">
                                        <c:if test="${all_user.user_id eq select_user.worker_id}">
                                            <c:set var="isChecked" value="checked"/>
                                        </c:if>
                                    </c:forEach>
                                    <input type="checkbox" name="workerIdList" ${isChecked} value="${all_user.user_id}" id="user_${all_user.user_id}">
                                    <label for="user_${all_user.user_id}">${all_user.user_name}</label><br>
                                </c:forEach>
							</td>
						</tr>
						<tr>
							<th>파일 첨부<small>(최대:6개까지)</small></th>
							<td>                                
                                <c:choose>
                                    <c:when test="${not empty taskAttachList}">
                                        <div>
                                            <c:forEach items="${taskAttachList}" var="taskAttach">
                                            	<div style="line-height:35px">
	                                                <a href="javascript:popup('/upload/${taskAttach.attach_path}',800,600)">${taskAttach.attach_name}</a>
	                                                <label for="attach_delete_no">삭제 </label>
	                                         		<input type="checkbox" id="attach_delete_no" name="attach_delete_no" value="${taskAttach.attach_no}">
                                         		</div>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                </c:choose>
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