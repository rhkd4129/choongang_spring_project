<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>

	<style>
		/* 초기 스타일: 가운데 정렬, 밑줄 없음, 진하게 표시 */
		.todo-label {
			text-decoration: none;
			font-weight: bold; /* 텍스트를 진하게 표시합니다. */
		}

		/* 체크박스가 선택되었을 때 인접한 텍스트에 밑줄이 그어지고, 연하게 표시됩니다. */
		input[type="checkbox"]:checked + .todo-label {
			text-decoration: underline;
			font-weight: normal; /* 텍스트를 연하게 표시합니다. */
		}

		.dan{
			color:red;

		}
		.nor{
			 color: black;
		}

		.easy{
			color: #264260;
		}

	</style>


	<meta charset="UTF-8">
	<title>To do List</title>
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
			showCommentList(); //todo 조회
		});

	// 오늘 todo생성하기 버튼이 보여지는 지 안보여지는 체크 하는 거 
		function todayTodoCreateCheck(){
			var text = $('.fw-bold.too:first').text();
			curDate = getCurrentDate().replace(/\//g, '-');
			if(text != curDate) {
				$('#btnBox .btn.btn-bd-primary').show();
			}
			else{
				$('#btnBox .btn.btn-bd-primary').hide();
			}
		}
		
	
		function showCommentList() {
			$.ajax({
				url			: 'todo_list_select',
				contentType : 'application/json; charset:utf-8',
				dataType 	: 'json',
				success		: function(data) {
					globalOnelist = data.onelist;
					
					var mapData = data.mapData;
					var keys = Object.keys(mapData);
					for (const key of keys) {
						//key하나의 날짜가 들어있고 mapData[key]이건 해당 날짜에 잇는 todo들
						// 결국 하나의 날짜에 해당하는 todoBox그려줌
						showTodoBox(mapData[key],globalOnelist);
					}

					todayTodoCreateCheck();
				},
				error: function(xhr, status, error){
					console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
				}
			});
		}

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//newTodoToday= false이면 새로운 생성이 아니라 그냥 원래잇던거 보여주는 개념
		// 만약 true면 함수에 중간 로직이 달라짐
		function showTodoBox(day_todo='',codeList=globalOnelist,newTodoToday=false) {
			var mainContainer = $('<div>').addClass('d-flex flex-column flex-md-row gap-4 py-md-4 align-items-center justify-content-center')
					.attr('id', 'todoContainer');
			var dropdownMenu = $('<div>').addClass('dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per')
					.attr({
						'data-bs-theme': 'light',
						'style': 'height:354px'
					});

			var aysnForm = $('<div>').addClass('p-2 mb-2 bg-body-tertiary border-bottom');
			if (newTodoToday === false) {
				// 이미 오늘날짜에 해당하는 todo가 있따면 원랭 잇는 날짜를 보여주고
				var a = $('<span>').addClass('fw-bold too').text(day_todo[0].todo_date);
			} else {
				//  아니라면 새로운 생성이므로 생성시점 날짜보여주기
				var a = $('<span>').addClass('fw-bold too').text(getCurrentDate());
			}
			aysnForm.append(a);


			////////////////////  radio버튼 생성 //////////////////////////////
			var radio = $('<div>');
			$(codeList).each(function (index, code) {
				var uniqueId = newTodoToday === false ? 'todo_priority_' + day_todo[0].todo_no : 'new_todo_priority';

				var todo_priority_check = $('<input>').attr({
					'type': 'radio',
					'id': uniqueId,
					'name': 'todo_priority',
					'value': code.cate_code
				});
				var label = $('<label>').attr({'for': uniqueId}).text(code.cate_name);
				radio.append(todo_priority_check, label);
			});

			aysnForm.append(radio);
			///////////////////////////////////////////////////////////
			var todoInput = $('<input>').attr({
				'type': 'text',
				'id': 'todo_list',
				'name': 'todo_list',
				'class': 'form-control',
				'autocomplete': 'false',
				'placeholder': '오늘 할 일을 메모합니다...'
			});
			
			// 엔터키 누르면 
			todoInput.keydown(function (e) {
				if (e.keyCode == 13) {
					var context = $(this).val();
					var todo_priority = $("input[name='todo_priority']:checked").val();
					var create_date = newTodoToday === false ? day_todo[0].todo_date.replace(/-/g, '/') : getCurrentDate();

					if (todo_priority == undefined) {
						alert("우선순위를 체크해 주세요! ");
					} else {
						insert_todoList(context, todo_priority, create_date);
					}
				}
			});
			aysnForm.append(todoInput);
			///////////////////////////////////////////////////////////
			var listGroup = $('<div>').addClass('list-group').attr({
				'id': 'idToDoList',
				'style': 'max-height: 250px; overflow-y: auto;'
			});
			//최종적으로 aysnForm안에 (라디오박스와 input이 있다)
			dropdownMenu.append(aysnForm, listGroup);
			mainContainer.append(dropdownMenu);

			///////////////////////////////////////////////////////////
			if (newTodoToday === false) {
				// 하나의 날짜에 todo 항목 순서대로 체크박스, 작업 내용, 삭제 버튼 생성해주기 하나의 row
				$(day_todo).each(function (index, todoItem) { 
					var newDiv = $('<div>').addClass('list-group-item d-flex justify-content-between align-items-center').attr({
						id: 'todo'
					});
						
					// 완료된거 체크하는 체크박스 생성 
					var checkboxElement = $('<input>').attr({
						type: 'checkbox',
						id: 'todo_check'+todoItem.todo_no,
						name: 'todo_check',
						value: todoItem.todo_priority
					}).addClass('me-2');
					
					
					if(todoItem.todo_check =='Y'){
						checkboxElement.attr({
							 checked: true
						})
					}
					
					// 체크박스에 체크될시에 따라 update가 됨	
					checkboxElement.change(function (e) {
						if ($(this).prop('checked')) {
							checkYN(todoItem.todo_no, todoItem.user_id, true);
						} else {
							checkYN(todoItem.todo_no, todoItem.user_id, false);
						}
					});

					// todoList내용 Label생성
					var labelElement = $('<label>').attr({
						for: 'todo_check', 
						id: 'todo_check' + todoItem.todo_no
					})
					if (todoItem.todo_priority == 2) {
						labelElement.text(todoItem.todo_list).addClass('flex-grow-1 todo-label text-danger');
					} else if (todoItem.todo_priority == 1) {
						labelElement.text(todoItem.todo_list).addClass('flex-grow-1 todo-label text-secondary');
					} else {
						labelElement.text(todoItem.todo_list).addClass('flex-grow-1 todo-label text-primary ');
					}
					
					//삭제 버튼 생성 
					var deleteButton = $('<button>').addClass('btn btn-secondary').attr({
						id: 'deleteButton',
						'data-todo-no': todoItem.todo_no,
						'data-user-id': todoItem.user_id
					}).text('삭제').click(function () {
						var todoNo = $(this).data('todo-no');
						var userId = $(this).data('user-id');
						deleteBtnClick(todoNo, userId);
					});
					//하나의 todo에 순서대로 체크박스 , 내용, 삭제 버튼이 추가된다.
					newDiv.append(checkboxElement, labelElement, deleteButton);
					//최종적으로 todoList box에 추가됨
					listGroup.append(newDiv);
				});
			}
			//첫번째생성이 아닐경우 fasle 인 경우는 그냥 날짜 순서대로 추가해주고
			if (newTodoToday === false) {///생성시에만
				$('#box').append(mainContainer);
			} else {
				// 해당 날에 첫번재 생성이면 맨앞에 요소를 추가해준다
				$('#box').prepend(mainContainer);
			}
		}

		//////////////////////////현재 시간 가져오기  ////////////////////////////////////////////////
		function getCurrentDate() {
			var currentDate = new Date();
			var year = currentDate.getFullYear().toString();
			var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
			var day = currentDate.getDate().toString().padStart(2, '0');
			return year + "/" + month + "/" + day;
		}
		/////////////////////////////////////////////////////////////////////////


		///////////////////////// ///////////////////////////////////////////////////////
		function insert_todoList(todo_list,todo_priority,create_date){
			// alert("할 일 목록: " + todo_list + "\n우선순위: " + todo_priority + "\n생성 날짜: " + create_date);
			$.ajax({
				url			 : 'todo_list_insert',
				type 		 : 'POST',
				data: {
					'todo_list': todo_list,
					'todo_priority': todo_priority,
					'create_date':create_date
				},	 		// 보낼 데이터
				dataType 	 : 'text',  				 	 		// 받을 데이터
				success 	 : function(data) {

					$('#box').empty();
					showCommentList();
				}
			});
		}
		///////////////////////// ///////////////////////////////////////////////////////
		function deleteBtnClick(todoNo,userId){
			$.ajax({
				url			: 'todo_list_delete',
				type		: 'POST',
				dataType	: 'text',
				data		: {'todo_no': todoNo, 'user_id': userId},
				success		: function(data) {
					$('#box').empty();
					showCommentList();
				}
			});
		}
		///////////////////////// ///////////////////////////////////////////////////////
		function checkYN(todo_no, user_id ,check ) {
			$.ajax({
				url 		 : 'todo_list_todoCheck_y',
				type 		 : 'POST',
				dataType 	 : 'text',
				data 		 : {'todo_no': todo_no,
					'user_id': user_id,
					'check':check},
				success		 : function(data){
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
						<a class="link-body-emphasis fw-semibold text-decoration-none" href="">오늘 할 일</a>
					</li>
				</ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">ToDoList</span>
				</div>
				<div>
					<div id="btnBox" class="createBtn">
						<!-- 변수를 문자열로 감싸주고 JSON.stringify를 사용하여 객체를 문자열로 변환 -->
						<button class="btn btn-bd-primary" onclick="showTodoBox('', globalOnelist, true)">오늘 할 일 생성하기</button>
					</div>
				</div>
			</div>

			<div class="album py-5">
				<div class="container">
					<div id="box" class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
						<!-- TO-DO LIST -->
					</div>
				</div>
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