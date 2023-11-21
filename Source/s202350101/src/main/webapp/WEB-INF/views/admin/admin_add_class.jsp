<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<%@ taglib  prefix="form"  uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <style text="text/css">
		.errors{
			color: red;
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

        function auth_mod() {
// hid_us_id 값을 모두 가져오기
            var hidUsIdArray = [];
            document.querySelectorAll("input[name=hid_us_id]").forEach(function (element) {
                hidUsIdArray.push(element.value);
                console.log(element.value);
            });
            console.log("hidUsIdArray: " + hidUsIdArray);

// user_auth 값을 모두 가져오기
            var userAuthArray = [];
            document.querySelectorAll("input[name=user_auth]").forEach(function (element) {
                userAuthArray.push(element.checked ? 'manager' : 'student');		//	권한이 true 일 때: student, false 일 때: manager
            });
            console.log("userAuthArray: " + userAuthArray);
//	현재 반 값 가져오기
            var class_room_num = $("#cl_room_List").val();
            console.log("class_room_num: " + class_room_num);
//	컨트롤러에 전송할 주소
            var sendurl = '/auth_mod';
            $.ajax({
                url: sendurl,
                type: "POST",
                data: JSON.stringify({user_id: hidUsIdArray, user_auth: userAuthArray}),
                contentType: "application/json",		//서버로 JSON 데이터를 전송
                dataType: "json",						//	서버로부터	json	데이터	받음
                success: function (response) {
                    // 서버에서 받은 JSON 응답을 처리
                    alert("권한 수정 완료" + response);
                }
            });
        }
//  강의실 변경 시 비동기로 강의실 내 학생 조회
        function cl_room() {
            var cl_room_val = $('#cl_room_List').val();
            console.log(cl_room_val);
            var sendurl = '/admin_projectmanagerRest/' + cl_room_val;
            console.log(sendurl);

            $.ajax({
                url: sendurl,
                dataType: 'json',
                success: function (jsonData) {

                    console.log(jsonData);
                    var tbody = $('#user_list'); // tbody 요소를 선택
                    // 테이블 초기화
                    tbody.empty();
                    // 데이터를 순회하면서 테이블에 추가
                    $.each(jsonData, function (index, user) {
                        var tr = $('<tr>'); // 새로운 <tr> 엘리먼트 생성

                        // <td> 엘리먼트 생성 및 데이터 추가
                        tr.append('<td>' + user.user_name + '</td>');
                        tr.append('<td>' + user.project_name + '</td>');

                        var authCheckbox = $('<input type="checkbox" name="user_auth">'); // 체크박스 생성

                        if (user.user_auth === 'manager') {
                            authCheckbox.prop('checked', true); // 'manager'인 경우 체크
                        }

                        var authTd = $('<td>').append(authCheckbox); // <td>에 체크박스 추가
                        tr.append(authTd);

                        tbody.append(tr); // <tr>을 <tbody>에 추가
                    });
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
			      <li class="breadcrumb-item active" aria-current="page">반 목록</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
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
							<button class="nav-link active" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-4" type="button" role="tab" aria-controls="nav-4" aria-selected="true" tabindex="-1" onclick="location.href='/admin_add_class'">반 생성</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-5" type="button" role="tab" aria-controls="nav-5" aria-selected="false" tabindex="-1" onclick="location.href='/admin_class_list'">반 목록</button>
						</div>
					</nav>
				</div>

                <form:form action="/admin_add_class" modelAttribute="cr" method="post" style="width:50%">
                    <span><h6><b>반생성</b></h6></span>
                    
                    <table width="600" class="table" style="margin-top:20px">
                    <colgroup>
                    	<col width="10%"></col>
                    	<col width="40%"></col>
                    	<col width="10%"></col>
                    	<col width="40%"></col>
                    </colgroup>
						<tr style="">
                  			<td>반 번호</td>
                  			<td colspan="3">
								<input class="form-control" type="text" name="class_room_num" value="${cr.class_room_num}">
								<form:errors path="class_room_num" class="errors" />
							</td>

						</tr>
						<tr>
                  			<td>담당 강사</td>
                  			<td colspan="3"><input class="form-control" type="text" name="class_master" value="${cr.class_master}">
								<form:errors path="class_master" class="errors"/>
							</td>

						</tr>
						<tr>
                  			<td>강의 이름</td>
                  			<td colspan="3"><input class="form-control" type="text" name="class_name" value="${cr.class_name}">
								<form:errors path="class_name" class="errors"/>
							</td>

						</tr>
						<tr>
                  			<td>학원 위치</td>
                  			<td colspan="3">
	                  			<select class="form-select" name="class_area">
			                        <option value="이대">이대</option>
			                        <option value="강남">강남</option>
			                    </select>
                  			</td>
						</tr>
						<tr>
                  			<td>시작 날짜</td>
                  			<td><input type="date" name="class_start_date" id="" class="form-control" value="${cr.class_start_date}">
								<form:errors path="class_start_date" class="errors"/></td>

                  			<td>종료 날짜</td>
                  			<td><input type="date" name="class_end_date" class="form-control" value="${cr.class_end_date}">
								<form:errors path="class_end_date" class="errors"/><br></td>

						</tr>
                    </table>
                    <button class="btn btn-secondary" type="submit">반 생성하기</button>
                    <button class="btn btn-secondary" type="reset">초기화하기</button>
                </form:form>
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