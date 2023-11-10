<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<!DOCTYPE html>
<html>

<head>

<script type="text/javascript">
    function delete_task(user_id,task_id,project_id){
        var userInput =  prompt("삭제라혀면 id입력하세요 ");
        if(userInput === user_id){
            $.ajax({
                url         :'/task_garbage',
                type        :'post',
                data        :{"task_id":task_id,
                            'project_id':project_id},
                dataType    :"text",
                success     :function (data){
                    if(data === '1'){
                        alert("삭제성공");
                        window.location.href ="/dashboard";
                    } else{// UPDATE를 수행햇지만 결과가 0이나올떄
                        alert("삭제 에러 ");
                    }
                    return;
                }
            });
        } else {
            alert("아이디를 확인하세요");
            return;
        }
    }
    $(function() {
        $.ajax({
            url			: '/main_header',
            dataType 	: 'html',
            success		: function(data) {
                console.log("ddd");
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

<div id="header"></div>

<div class="container-fluid">
    <div class="row">

        <!-- 메뉴 -->
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">

            <div class="border border-dark p-4">
                <div class="mb-3">
                    <label for="task_id" class="fw-bold">Task ID</label>
                    <p id="task_id">${task.task_id}</p>
                </div>

                <div class="mb-3">
                    <label for="task_oner" class="fw-bold">작업 담당자</label>
                    <p id="task_oner">${task.user_name}</p>
                </div>

                <div class="mb-3">
                    <label for="task_subject" class="fw-bold">Task Subject</label>
                    <p id="task_subject">${task.task_subject}</p>
                </div>

                <div class="mb-3">
                    <label for="project_s_name" class="fw-bold">Project Name</label>
                    <p id="project_s_name">${task.project_step_seq}:${task.project_s_name}</p>
                </div>

                <div class="mb-3">
                    <label for="task_stat_time" class="fw-bold">Start Time</label>
                    <p id="task_stat_time">${task.task_stat_time}</p>
                </div>

                <div class="mb-3">
                    <label for="task_end_itme" class="fw-bold">End Time</label>
                    <p id="task_end_itme">${task.task_end_itme}</p>
                </div>

                <div class="mb-3">
                    <label for="task_priority" class="fw-bold">Task Priority</label>
                    <p id="task_priority">${task.task_priority}</p>
                </div>

                <div class="mb-3">
                    <label for="task_status" class="fw-bold">Task Status</label>
                    <p id="task_status">${task.task_status}</p>
                </div>

                <c:choose>
                    <c:when test="${not empty taskSubList}">
                        <label for="worker" class="fw-bold">공동 작업자</label>
                        <c:forEach items="${taskSubList}" var="taskSub" varStatus="seq">
                            <p id="worker"> ${taskSub.user_name}</p>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p class="fw-bold">공동 작업자 없음</p>
                    </c:otherwise>
                </c:choose>


            </div>
            <a type="button" class="btn btn-primary" onclick="delete_task('${task.user_id}',${task.task_id},${task.project_id})">삭제하기</a>
            <a type="button" class="btn btn-primary" href="task_update_view">수정하기</a>
        </main>

    </div>
</div>


<div id="footer"></div>

</body>
</html>
