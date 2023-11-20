<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

                    $('#header').html(data); // 응답 데이터를 #header 요소에 추가
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
	// 승인
        function app_ok() {
        	
        	// hid_us_id 값을 모두 가져오기
        	            var hidPrjIdArray = [];

        	// project_approve 값을 모두 가져오기
        	            var prjAuthArray = [];
        	            document.querySelectorAll("input[name=project_approve]").forEach(function (element) {
        	            	if(element.checked) {
        	            		hidPrjIdArray.push(element.value);
        	            		prjAuthArray.push('2');		//	권한설정이 1인것에 체크하면 2로 바뀜 (승인완료)
        	            	}        	            	
        	            });
        	            console.log("prjAuthArray: " + prjAuthArray);
        	            if(hidPrjIdArray.length == 0){
        	            	alert("승인 대상 프로젝트를 선택 하세요");
        	            	return false;
        	            }
        	            
//        		컨트롤러에 전송할 주소
        	            var sendurl = '/app_ok';
        	            console.log("sendURL: " + sendurl);
        	            $.ajax({
        	                url: sendurl,
        	                type: "POST",
        	                data: JSON.stringify({project_id: hidPrjIdArray, project_approve: prjAuthArray}),
        	                contentType: "application/json",		//서버로 JSON 데이터를 전송
        	                dataType: "json",						//	서버로부터	json 데이터 받음
        	                success: function (response) {
        	                    // 서버에서 받은 JSON 응답을 처리
        	                    alert("권한 수정 완료" + response);
        	                    location.href="/admin_approval";
        	                }
        	            });
        	        }

        // 삭제
      function app_del() {
        	
        	// hid_us_id 값을 모두 가져오기
        	            var hidPrjIdArray = [];

        	//del_status 값을 모두 가져오기
        	            var delStatusArray = [];
        	            document.querySelectorAll("input[name=project_del]").forEach(function (element) {
        	            	if(element.checked) {
        	            		hidPrjIdArray.push(element.value);
        	            		delStatusArray.push('1');		//	권한설정이 1인것에 체크하면 2로 바뀜 (승인완료)
        	            	}        	            	
        	            });
        	            console.log("delStatusArray: " + delStatusArray);
        	            if(hidPrjIdArray.length == 0){
        	            	alert("삭제 할 프로젝트를 선택 하세요");
        	            	return false;
        	            }
        	            
//        		컨트롤러에 전송할 주소
        	            var sendurl = '/app_del';
        	            console.log("sendURL: " + sendurl);
        	            $.ajax({
        	                url: sendurl,
        	                type: "POST",
        	                data: JSON.stringify({project_id: hidPrjIdArray, del_status: delStatusArray}),
        	                contentType: "application/json",		//서버로 JSON 데이터를 전송
        	                dataType: "json",						//	서버로부터	json 데이터 받음
        	                success: function (response) {
        	                    // 서버에서 받은 JSON 응답을 처리
        	                    alert("권한 수정 완료" + response);
        	                    location.href="/admin_approval";
        	                }
        	            });
        	        }

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
			      <li class="breadcrumb-item active" aria-current="page">프로젝트 관리</li>
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
							<button class="nav-link" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-1" type="button" role="tab" aria-controls="nav-1" aria-selected="false" tabindex="-1" onclick="location.href='/admin_projectmanager'">팀장 권한 설정</button>
							<button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-2" type="button" role="tab" aria-controls="nav-2" aria-selected="false" onclick="location.href='/admin_board'">게시판 관리</button>
							<button class="nav-link active" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-3" type="button" role="tab" aria-controls="nav-3" aria-selected="true" tabindex="-1" onclick="location.href='/admin_approval'">프로젝트 관리</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-4" type="button" role="tab" aria-controls="nav-4" aria-selected="false" tabindex="-1" onclick="location.href='/admin_add_class'">반 생성</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-5" type="button" role="tab" aria-controls="nav-5" aria-selected="false" tabindex="-1" onclick="location.href='/admin_class_list'">반 목록</button>
						</div>
               		</nav>
				</div>

				<form>
					<table style="margin-bottom:5px">
                    	<tr>
                    		<td>
			                    <button class="btn btn-primary" onclick="app_ok();"  type="button">생성 승인</button>
			                    <button class="btn btn-primary" onclick="app_del();"  type="button">프로젝트 삭제</button>
			                </td>
	                	</tr>
                    </table>
                    
                    <table class="table">
                        <thead>
                        <tr>
                        	<th>프로젝트 번호</th>
                            <th>프로젝트 이름</th>
                            <th>반 번호</th>
                            <th>신청일</th>
                            <th>신청자(ID)</th>
                            <th>진행상태</th>
                            <th>수정</th>
                            <th>승인여부</th>
                            <th>삭제</th>
                        </tr>
                        </thead>
                        <tbody id="project_list">
                        
                        <c:forEach items="${approveList }" var="project">
							
		                             <tr>
		                            	<input type="hidden" name="hid_project_id" value="${project.project_id}">
		                                <td>${project.project_id}</td>
		                                <td>${project.project_name}</td>
		                                <td>${project.project_manager_class}</td>
		                                <td>${project.project_create_date}</td>	                                
		                                <td>${project.project_manager_name}(${project.project_manager_id})</td>
		                                <td>${project.project_status_name }</td>
		                                <td><a href="prj_mgr_req_edit?project_id=${project.project_id}">수정</a></td>
		                                
		                                <td>
		                               	<c:choose>
                            				<c:when test="${project.project_approve eq '1'}">
                            					<input type="checkbox" name="project_approve" value="${project.project_id }">
                            				</c:when>
                            				<c:when test="${project.project_approve eq '2'}">
                            					${project.project_approve_name }
                            				</c:when>
                            			</c:choose>
                            			</td>
                            			
                            			<td>
		             					<c:choose>
		             						<c:when test="${project.del_status  eq '0'}">
		                                        <input type="checkbox" name="project_del" value="${project.project_id }">
		                                    </c:when>
		                                    <c:when test="${project.del_status eq '1'}">
		                                        ${project.del_status_name }
		                                    </c:when>
		                                </c:choose>
		                                </td>
		                            </tr>
                          
                        </c:forEach>

                        </tbody>

                    </table>

                    <nav aria-label="Page navigation example">
						<ul class="pagination justify-content-center">
                   
						<c:if test="${page.startPage > page.pageBlock}">
						    <li class="page-item disabled"><a class="page-link" href="admin_approval?currentPage=${page.startPage-page.pageBlock}" tabindex="-1" aria-disabled="true">Previous</a></li>
						</c:if>
						<c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
							<li class="page-item"><a class="page-link" href="admin_approval?currentPage=${i}">[${i}]</a></li>
						</c:forEach>                  
						<c:if test="${page.endPage > page.totalPage}">
							<li class="page-item"><a class="page-link" href="admin_approval?currentPage=${page.startPage+page.pageBlock}">Next</a></li>
						</c:if>
                   
						</ul>
               		</nav>
                </form>
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