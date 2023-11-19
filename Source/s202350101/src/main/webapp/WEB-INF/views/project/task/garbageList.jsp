<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="/lkh/css/lkh.css">

    <script type="text/javascript">
        function taskDelete(currentId, taskOwnerId,task_id,project_id){

            alert("삭제 하시겠습니까?");
            if(currentId === taskOwnerId){
                $.ajax({
                    url:'/task_delete',
                    type:'post',
                    dataType:'text',
                    data: {'task_id':task_id,
                        'project_id':project_id},
                    success:function (result){
                        console.log('result:',result);
                        if(result === '1'){
                            alert("삭제 완료");
                            window.location.href ="/dashboard";
                        }
                    }
                })
            }
            else{
                alert("작성자만 삭제할 수 있습니다.");
            }
        }
        function taskRestore(currentId, taskOwnerId,task_id,project_id){
            alert("복구 하시겠습니까?");
            if(currentId === taskOwnerId){
                $.ajax({
                    url:'/task_restore',
                    type:'post',
                    dataType:'text',
                    data: {'task_id':task_id,
                        'project_id':project_id},
                    success:function (result){
                        console.log('result:',result);
                        if(result === '1'){
                            alert("복구 완료");
                            window.location.href ="/dashboard";
                        }
                    }
                })
            }
            else{
                alert("작성자만 복구할 수 있습니다.");
            }
        }

        $(function() {

            $.ajax({
                url			: '/main_header',
                dataType 	: 'html',
                success		: function(data) {
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


            <h3 style="margin: 3%">휴지통 : ${taskCount} 개</h3>
            <c:choose>
            <c:when test="${not empty param.status}">
            <div class="alert alert-success" role="alert">삭제 완료</div>
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
                    </tr>
                    </thead>
                    <tbody id="tbodys">
                    <c:forEach var="task" items="${garbageList}">
                        <tr>
                            <td>
                                ${task.rn}

                            </td>
                            <td>${task.user_name}</td>
                            <td>${task.project_s_name}</td>
                            <td>${task.task_subject}</td>
                            <td>
                                <button  onclick="taskDelete('${currentUserId}','${task.user_id}',${task.task_id},${task.project_id})">영구 삭제</button>
                                <button  onclick="taskRestore('${currentUserId}','${task.user_id}',${task.task_id},${task.project_id})">복구</button>
                            </td>
                        </tr>
                        <c:set var="num" value="${num - 1 }"></c:set>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div>
                <c:if test="${page.startPage > page.pageBlock}">
                <a href="garbage_list?currentPage=${page.startPage - page.pageBlock}" class="btn btn-primary">이전</a>
                </c:if>
                <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                <a href="garbage_list?currentPage=${i}" class="btn btn-primary">${i}</a>
                </c:forEach>
                <c:if test="${page.endPage < page.totalPage}">
                <a href="garbage_list?currentPage=${page.startPage + page.pageBlock}" class="btn btn-primary">다음</a>
                </c:if>
            </div>
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
