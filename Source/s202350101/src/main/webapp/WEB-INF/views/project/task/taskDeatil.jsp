<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Task List</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- 채팅 -->
<link rel="stylesheet" type="text/css" href="/pmschat/css/chat.css">
<script type="text/javascript" src="/pmschat/js/chat.js"></script>

<style>
    .attached_img{
        width: 150px;
        height: 150px;
        padding: 1%;
        border: 1px solid black;}

</style>
<script type="text/javascript">
    function delete_task(user_id,task_id,project_id){
        //var userInput =  prompt("삭제라혀면 ID 를입력하세요 ");
        //if(userInput === user_id){
        if(confirm("삭제하시겠습니까?")){
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
        }
        /*} else {
            alert("작성자만 삭제 할 수 있습니다. 아이디를 확인하세요");
            return;
        }*/
    }
    function update_task(user_id,task_id,project_id){
        //var userInput =  prompt("수정하려면 ID 를 입력하세요 ");
        //if(userInput === user_id) {
            var newUrl = "/task_update_form?projectId=" + project_id + "&taskId=" + task_id;
            window.location.href = newUrl;
        //} else {
        //    alert("작성자만 수정 할 수 있습니다. 아이디를 확인하세요 ");
        //    return;
        // }
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
            <!-- 여기에 메뉴 내용 추가 -->
        </div>


        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
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
					<span class="apptitle">작업 상세 내역</span>
				</div>
			</div>

			<div class="container-fluid">
				<table width="100%" style="margin-top:10px;">
					<tr>
						<td style="text-align:right">
							<button type="button" class="btn btn-dark btn-sm" onclick="update_task('${task.user_id}',${task.task_id},${task.project_id})">수정</button>
							<button type="button" class="btn btn-dark btn-sm" onclick="delete_task('${task.user_id}',${task.task_id},${task.project_id})">삭제</button>
							<button type="button" class="btn btn-dark btn-sm" onclick="goto('task_list')">닫기</button>
						</td>
					</tr>
				</table>						
                <table class="table">
                	<colgroup>
                		<col width="15%"></td>
                		<col width="85%"></td>
                	</colgroup>
                    <tbody>
                        <tr>
                            <th>프로젝트 단계</th>
                            <td><span class="font-cate">${task.project_step_seq}. ${task.project_s_name}</span></td>
                        </tr>
                        <tr>
                            <th>작업 제목</th>
                            <td><span class="font-subject">${task.task_subject}</span></td>
                        </tr>
                        <tr>
                            <th>시작일 ~ 마감일</th>
                            <td>${task.task_start_time} ~ ${task.task_end_time}</td>
                        </tr>
                        <tr>
                            <th>작업 우선순위</th>
                            <td>
                                <c:choose>
                                    <c:when test="${task.task_priority eq 0}">낮음</c:when>
                                    <c:when test="${task.task_priority eq 1}">보통</c:when>
                                    <c:when test="${task.task_priority eq 2}">높음</c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>작업 진행 상태</th>
                            <td>
                                <c:choose>
                                    <c:when test="${task.task_status eq 0}">예정된 작업</c:when>
                                    <c:when test="${task.task_status eq 1}">진행중인 작업</c:when>
                                    <c:when test="${task.task_status eq 2}">완료된 작업</c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>작업 담당자</th>
                            <td>
                            	${task.user_name}
                            	<span class="iconChat">
									<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
										<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
										<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
									</svg>
									<input type="hidden" name="chat_id" value="${task.user_id}">
								</span>
                            </td>
                        </tr>
                        <tr>
                            <th>공동 작업자</th>
                            <td style="line-height:25px">
                                <c:choose>
                                    <c:when test="${not empty taskSubList}">
                                        <c:forEach items="${taskSubList}" var="taskSub" varStatus="seq">
                                            ${taskSub.user_name}
						                    <span class="iconChat">
												<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
													<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
													<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
												</svg>
												<input type="hidden" name="chat_id" value="${taskSub.worker_id}">
											</span>                                            
                                            <br>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
										공동 작업자 없음
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>첨부파일</th>
                            <td>
                                <!---- 변경사항 --->
                                <c:choose>
                                    <c:when test="${not empty taskAttachList}">
                                        <div>
                                            <c:forEach items="${taskAttachList}" var="taskAttach">
                                                <c:set var="fileExtension" value="${fn:substringAfter(taskAttach.attach_path, '.')}" />

                                                <c:choose>
                                                    <c:when test="${fileExtension eq 'jpg' or fileExtension eq 'jpeg' or fileExtension eq 'png' or fileExtension eq 'gif'}">
                                                        <a href="javascript:popup('/upload/${taskAttach.attach_path}',800,600)">${taskAttach.attach_name}</a><br>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="/upload/${taskAttach.attach_path}" download="${taskAttach.attach_name}">${taskAttach.attach_name}</a><br>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </c:when>
                                </c:choose>
                                <!---- 변경사항 --->
                            </td>
                        </tr>
                        <tr style="height:200px">
                            <th>작업 내용</th>
                            <td><pre>${task.task_content}</pre></td>
                        </tr>
                    </tbody>
                </table>

            </div>
        </main>
    </div>
</div>

<div id="footer"></div>

</body>
</html>
