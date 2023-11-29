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
			      <li class="breadcrumb-item active" aria-current="page">작업 목록</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">휴지통</span>
				</div>
			</div>
			
			<div class="container-fluid">
				<table width="100%" style="margin-bottom:5px">
					<tr>
						<td width="100">
							<!-- <button type="button" class="btn btn-secondary btn-sm" onclick="goto('')">작성</button> -->
						</td>
						<td width="*" style="text-align:right">
							삭제 건수 : ${taskCount}
						</td>
					</tr>
				</table>
			
				<c:choose>
	            <c:when test="${not empty param.status}">
	            <div class="alert alert-success" role="alert">삭제 완료</div>
	            </c:when>
	            </c:choose>
	            <c:set var="num" value="${page.total - page.start + 1 }"></c:set>

	            <div class="table-responsive">
	                <table class="table table-hover" id="table1">
	                	<colgroup>
							<col width="5%"></col>
							<col width="12%"></col>
							<col width="36%"></col>
							<col width="10%"></col>
							<col width="11%"></col>
							<col width="12%"></col>
							<col width="14%"></col>
						</colgroup>
	                    <thead class="table-light">
	                    <tr>
	                        <th>작업번호</th>
	                        <th>프로젝트 단계</th>
	                        <th>작업명</th>
	                        <th>작업 담당자</th>
	                        <th>작업시작일</th>
	                        <th>마감일</th>
	                        <th>복구</th>
	                    </tr>
	                    </thead>
	                    <tbody id="tbodys">
	                    <c:forEach var="task" items="${garbageList}">
	                        <tr>
	                            <td>${task.rn}</td>
	                            <td>${task.project_s_name}</td>
	                            <td>${task.task_subject}</td>
	                            <td>${task.user_name}</td>
	                            <td>${task.task_start_time}</td>
	                            <td>${task.task_end_time}</td>
	                            <td>
	                                <button class="btn btn-danger btn-sm" onclick="taskDelete('${currentUserId}','${task.user_id}',${task.task_id},${task.project_id})">영구 삭제</button>
	                                <button class="btn btn-dark btn-sm" onclick="taskRestore('${currentUserId}','${task.user_id}',${task.task_id},${task.project_id})">복구</button>
	                            </td>
	                        </tr>
	                        <c:set var="num" value="${num - 1 }"></c:set>
	
	                    </c:forEach>
	                    </tbody>
	                </table>
	            </div>
	            <div class="pagination justify-content-center">
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
			</div>
			<p><p>
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
