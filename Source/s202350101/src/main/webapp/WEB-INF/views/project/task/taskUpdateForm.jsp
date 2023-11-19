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


        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4" >
            <form></form>
            <h3>작업 수정 폼</h3>
            <!------------------------------ //개발자 소스 입력 START ----- "-------------------------->
            <form:form action="task_update" method="post" modelAttribute='task' class="border border-dark p-4" enctype="multipart/form-data" >
                <input type="hidden" name="task_id" value="${task.task_id}">
                <input type="hidden" name="project_id" value="${task.project_id }">

                <div class="container">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <select id="order_by" name="project_step_seq">
                                    <c:forEach var="step" items="${prjStepList}">

                                        <option value="${step.project_step_seq}" <c:if test="${  step.project_s_name eq task.project_s_name}">selected</c:if>>
                                                ${step.project_order}: ${step.project_s_name}
                                        </option>
                                    </c:forEach>
                                </select>

                            </div>

                            <div class="form-group">
                                <label for="task_subject">작업명:</label>
                                <input type="text" class="form-control" name="task_subject" id="task_subject" value="${task.task_subject}">
                                <form:errors path="task_subject" class="errors"/>
                            </div>
                            <div class="form-group">
                                <label for="task_content">업무 내용:</label>
                                <textarea  rows="5" class="form-control" name="task_content" id="task_content" value="${task.task_content}"></textarea>

                                <form:errors path="task_content" class="errors" />
                            </div>

                            <div class="form-group">
                                <label>공동작업자</label><br>

                                <c:forEach var="all_user" items="${task_create_form_worker_list}">
                                    <c:set var="isChecked" value=""/>
                                    <c:forEach var="select_user" items="${taskSubList}">
                                        <c:if test="${all_user.user_id eq select_user.worker_id}">
                                            <c:set var="isChec'ked" value="checked "/>
                                        </c:if>
                                    </c:forEach>
                                    <input type="checkbox" name="workerIdList" ${isChecked} value="${all_user.user_id}" id="user_${all_user.user_id}">
                                    <label for="user_${all_user.user_id}">${all_user.user_name}</label><br>
                                </c:forEach>

                            </div>


                            <div class="form-group">
                                <label for="task_start_time">작업 시작일</label>
                                <input type="date" class="form-control" id="task_start_time" name="task_start_time" value="${task.task_start_time}" min="2023-07-22" max="2030-12-31" />
                            </div>
                            <div class="form-group">
                                <label for="task_end_time">작업 마감일</label>
                                <input type="date" class="form-control" id="task_end_time" name="task_end_time" value="${task.task_end_time}"  min="2023-07-22" max="2030-12-31" />
                            </div>



                            <button style="margin-top: 20px;" type="submit" class="btn btn-primary">수정완료</button>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label>작업 상태</label>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" name="task_status" value="0" id="status0"
                                           <c:if test="${task.task_status == 0}">checked</c:if>>

                                    <label class="form-check-label" for="status0">예정된 작업</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" name="task_status" value="1" id="status1"
                                           <c:if test="${task.task_status == 1}">checked</c:if>>
                                    <label class="form-check-label" for="status1">진행중인 작업</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" name="task_status" value="2" id="status2"
                                    <c:if test="${task.task_status == 2}">checked</c:if>>
                                    <label class="form-check-label" for="status2">완료된 작업</label>
                                </div>
                                <form:errors path="task_status" class="errors" />
                            </div>
                            <div class="form-group">
                                <label>우선순위 :</label>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" name="task_priority" value="0" id="priority0"
                                           <c:if test="${task.task_priority == 0}">checked</c:if>>
                                    <label class="form-check-label" for="priority0">낮음</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" name="task_priority" value="1" id="priority1"
                                           <c:if test="${task.task_priority == 1}">checked</c:if>>
                                    <label class="form-check-label" for="priority1">보통</label>
                                </div>
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" name="task_priority" value="2" id="priority2"
                                           <c:if test="${task.task_priority == 2}">checked</c:if>>
                                    <label class="form-check-label" for="priority2">높음</label>
                                </div>
                                <form:errors path="task_priority" class="errors" />
                            </div>


                            <div class="form-group">
                                <label for="file1">파일첨부</label>
                                <input type="file" class="form-control" id="file1" name="file1" multiple="multiple" >
                                <label  class="fw-bold">첨부파일</label>
                                <form:errors path="*" class="errors"/>
                                <c:choose>
                                    <c:when test="${not empty taskAttachList}">
                                        <div class="img_box">
                                        <c:forEach items="${taskAttachList}" var="taskAttach">

                                            <img  class="attached_img" alt ="이미지 없음" src="${taskAttach.attach_path}/${taskAttach.attach_name}" alt="Attached Image">
                                            <a href="${taskAttach.attach_path}/${taskAttach.attach_name}" target="_blank">보기</a><br>
                                            <label for="attach_delete_no">삭제 </label>
                                            <input type="checkbox" id="attach_delete_no" name="attach_delete_no" value="${taskAttach.attach_no}">

                                        </c:forEach>
                                        </div>

                                    </c:when>
                                    <c:otherwise>
                                        <p class="fw-bold"> 첨부파일 없음 </p>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                </div>


            </form:form>
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