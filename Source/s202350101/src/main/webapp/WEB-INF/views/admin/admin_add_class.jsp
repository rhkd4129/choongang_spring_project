<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <style text="text/css">
        #test {
            margin-top: 5%;
            margin-bottom: 15%;
        }

        #admin_page_list {
            margin-bottom: 5%;
        }
    </style>

    <script type="text/javascript">
        $(function () {

            $.ajax({
                url: '/main_header',
                dataType: 'text',
                success: function (data) {
                    $('#header').html(data);
                }
            });

            $.ajax({
                url: '/main_menu',
                dataType: 'text',
                success: function (data) {
                    $('#menubar').html(data);
                }
            });

            $.ajax({
                url: '/main_footer',
                dataType: 'text',
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
        <div id="menubar"
             class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <!------------------------------ //개발자 소스 입력 START ------------------------------->

            <div id="test">
                <div id="admin_page_list">
                    <div class="btn btn-secondary" onclick="location.href='/admin_projectmanager'">팀장 권한 설정</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_board'">게시판 관리</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_project'">프로젝트 생성 승인</div>
                    <div class="btn btn-primary" onclick="location.href='/admin_add_class'">반 생성</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_class_list'">반 목록</div>
                </div>

                <form action="/admin_add_class" method="post">
                    <h2>반생성</h2>
                    반 번호 : <input class="form-control" type="text" name="CLASS_ROOM_NUM"><br>
                    담당 강사 : <input class="form-control" type="text" name="CLASS_MASTER"><br>
                    강의 이름 : <input class="form-control" type="text" name="CLASS_NAME"><br>
                    학원 위치 :
                    <select class="form-select" name="CLASS_AREA">
                        <option value="이대">이대</option>
                        <option value="강남">강남</option>
                    </select> <br>
                    시작 날짜 : <input type="date" name="CLASS_START_DATE"><br>
                    종료 날짜 : <input type="date" name="CLASS_END_DATE"><br>

                    <button type="submit">반 생성하기</button>
                    <button type="reset">
                        초기화하기
                    </button>
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