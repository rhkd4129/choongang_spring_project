<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>

	<script type="text/javascript">

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
					console.log(data);
					$('#footer').html(data);
				}
			});
			showCommentList(); //댓글 조회
		});

		function showCommentList() {
			$.ajax({
				url			: 'todo_list_select',
				contentType : 'application/json; charset:utf-8',
				/* data 		: JSON.stringify(params), */
				dataType 	: 'json',
				success		: function(data) {
					var keys = Object.keys(data);
					for (const key of keys) {
						showTodoBox(data[key]);
					}
				},
				error: function(xhr, status, error){
					console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
				}
			});
		}

		function showTodoBox(day_todo) {
			var mainContainer = $('<div>').addClass('d-flex flex-column flex-md-row gap-4 py-md-4 align-items-center justify-content-center').attr('id', 'todoContainer');
			var dropdownMenu = $('<div>').addClass('dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per').attr({
				'data-bs-theme': 'light',
				'style': 'height:354px'
			});
			var form = $('<form>').addClass('p-2 mb-2 bg-body-tertiary border-bottom');
			var todoInput = $('<input>').attr({
				'type': 'text',
				'id': 'todo_list',
				'name': 'todo_list',
				'class': 'form-control',
				'autocomplete': 'false',
				'placeholder': '오늘 할 일을 메모합니다...'
			});
			form.append(todoInput);
			var listGroup = $('<div>').addClass('list-group').attr('id', 'idToDoList');
			dropdownMenu.append(form, listGroup);
			mainContainer.append(dropdownMenu);

			$(day_todo).each(function(index, todoItem){
				var todoDiv = $('<div>').text(todoItem.todo_list);
				listGroup.append(todoDiv);
			});


			$('#box').append(mainContainer);
		}


		// 리스트 입력
		$("#todo_list").keydown(function(e) {   // keydown : 키 입력시 발생되는 이벤트
			if(e.keyCode == 13){
				alert("insert");
				var params = {};
				params.todo_priority = $("input[name='todo_priority']:checked").val();  // radio박스에 checked된 애의 value
				params.todo_list = $('#todo_list').val(); 								// value 갖고 옴
				alert(params.todo_list);

				$.ajax({
					url			 : 'todo_list_insert',
					type 		 : 'POST',
					data		 : JSON.stringify(params), 		 		// 보낼 데이터
					contentType  : 'application/json; charset=utf-8',	// json 형식울 인코딩해서 데이터 전송
					dataType 	 : 'text',  				 	 		// 받을 데이터
					success 	 : function(loginId) {
						alert("로그인ID : " + loginId);
					}
				});
			}
		});






		////////////////////////////////////////////////////////////////////////////
		// 삭제 버튼 클릭하면 리스트 삭제
		$('#idToDoList').on('click', '.deleteButtonId', function (e) {
			var valueString = $(this).val();
			var values = JSON.parse(valueString);
			var todo_no = values.todo_no;
			var user_id = values.user_id;

			alert("todo_no 삭제: " + todo_no + ", user_id: " + user_id);
			alert("todo_no: " + todo_no);

			$.ajax({
				url			: 'todo_list_delete',
				type		: 'POST',
				dataType	: 'text',
				data		: {'todo_no': todo_no, 'user_id': user_id},
				success		: function(data) {
					alert("data 확인: " + data);
					if (data == 1) {
						alert("삭제되었습니다");
						window.location.href = "todo_list";
					}
				}
			});
		});
		/////////////////////////////////////////////////////////////////////////////////////////////////
		// 체크박스 클릭
		$('#idToDoList').on('click', 'input:checkbox', function(){
			var valueString = $(this).val();
			var values = JSON.parse(valueString);
			var todo_no = values.todo_no;
			var user_id = values.user_id;
			console.log("todo_no-> " + todo_no);
			console.log("user_id-> " + user_id);
			if($(this).prop('checked')){
				$(this).parent().addClass("selected")//.attr("id", "checkTodo");  // this = 'input:checkbox'
				/* $(this).parent().addId */
				console.log("체크박스 클릭");
				// 할 일 완료 -> Y로 변경
				$.ajax({
					url 		 : 'todo_list_todoCheck_y',
					type 		 : 'POST',
					dataType 	 : 'text',
					data 		 : {'todo_no': todo_no, 'user_id': user_id},
					success		 : function(data){
						if (data == 1){
							alert("리스트 완료해서 Y로 변경");
						}
					}
				});
			} else {
				$(this).parent().removeClass("selected")//.attr("id", "checkTodo");
				console.log("체크안했다.");
				// Y에서 클릭하면 N으로 변경
				$.ajax({
					url 		 : 'todo_list_todoCheck_y',
					type 		 : 'POST',
					dataType 	 : 'text',
					data 		 : {'todo_no': todo_no, 'user_id': user_id},
					success		 : function(data){
						if (data == 1){alert("Y에서 N으로 변경");}
					}
				});
			}

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
						<a class="link-body-emphasis fw-semibold text-decoration-none" href="">오늘 할 일</a>
					</li>
				</ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">오늘 할 일</span>
				</div>
			</div>


			<div class="album py-5">
				<div class="container">
					<div id="box" class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">


						<!-- TO-DO LIST -->









					</div>
				</div>
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


<!-- <label class="list-group-item d-flex gap-3 bg-body-tertiary">
                      <input class="form-check-input form-check-input-placeholder bg-body-tertiary flex-shrink-0 pe-none" disabled="" type="checkbox" value="" style="font-size: 1.375em;">
                      <span class="pt-1 form-checked-content">
                        <span contenteditable="true" class="w-100">Boot Sprint Project 생성하기</span>
                        <small class="d-block text-body-secondary">
                          <svg class="bi me-1" width="1em" height="1em"><use xlink:href="#list-check"></use></svg>
                          Choose list...
                        </small>
                      </span>
                </label> -->
<%--

        <div class="d-flex flex-column flex-md-row gap-4 py-md-4 align-items-center justify-content-center"> <!-- p-4 -->
            <div class="dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per" data-bs-theme="light" style="height:354px">
                <form class="p-2 mb-2 bg-body-tertiary border-bottom">
                    <c:forEach var="code" items="${codeList}">
                        <input type="radio" id="todo_priority" name="todo_priority" value="${code.cate_code}"
                        <c:if test="${code.cate_code eq '1'}">checked="checked"</c:if>
                         >${code.cate_name}
                    </c:forEach>
                    <input type="text" id="todo_list" name="todo_list" class="form-control" autocomplete="false" placeholder="오늘 할 일을 메모합니다...">
                <input type="date" id="dt" onchange="cal_click(event)"/>
                </form>


                <div class="list-group">
                    <div id="idToDoList" style="overflow:scroll; height:500px;"></div>
                </div>
            </div>
    </div>	 --%>

</body>
</html>