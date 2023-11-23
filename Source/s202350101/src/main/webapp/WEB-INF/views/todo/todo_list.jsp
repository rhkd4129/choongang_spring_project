<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<!-- CSS END -->
<style>
	.deleteButtonId{
	/* margin-left:90px;/*  */ */
	}
</style>
<!-- JS START -->
<!-- JS END -->

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
				$('#footer').html(data);
			}
		});
		
		
		// 리스트 입력
		$("#todo_list").keydown(function(e) {   // keydown : 키 입력시 발생되는 이벤트
			if(e.keyCode == 13){
				alert("insert");
				// 보내는 data : json, 받는 data는 text
				// data = { todo_priority : 1, todo_list : 내용 }
				// var params = {todo_priority:2, todo_list: "밥먹기"};
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
		
		
		// 리스트 보여주기
		$.ajax({
			url 		: 'todo_list_select',
			dataType 	: 'json',
			success 	: function(todoList){
				const todoListDiv = $('#idToDoList');
				
				$.each(todoList, function(index, todo){				
					var newLabel = $("<label></label>");
					newLabel.addClass('list-group-item d-flex gap-3');
					
					var input = $('<input>');
					input.prop("type", "checkbox");
					input.prop("value",JSON.stringify({ 'todo_no': todo.todo_no, 'user_id': todo.user_id}));
				
					if(todo.todo_check === 'Y'){
						input.prop('checked',true);	
					}else{
						input.prop('checked',false); 
					}
					
					input.addClass("form-check-input flex-shrink-0");
					input.id = index;  				// 반복하려고 index ? , <input>에 id는 "list_check"로 되어 있는데,,
					
					var list = $('<label></label>');
					list.htmlfor = index;   		// .htmlFor : input의 id나 name을 적어 input과 연결
				
					var strong = $("<strong></strong>");
				
					var deleteButton = $('<button></button>');
					deleteButton.prop("type", "button");
					deleteButton.text("삭제");
					deleteButton.val(JSON.stringify({ 'todo_no': todo.todo_no, 'user_id': todo.user_id}));
					deleteButton.addClass('deleteButtonId');
					strong.html(todo.todo_list);
					
					/**/
					newLabel.append(input);
					newLabel.append(strong);
					newLabel.append(deleteButton);
					todoListDiv.append(newLabel);	
				})
			}
		});
		
		
		// 삭제 버튼 클릭하면 리스트 삭제
		$('#idToDoList').on('click', '.deleteButtonId', function(e) {
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
				
					<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
						<div class="d-flex flex-column flex-md-row gap-4 py-md-4 align-items-center justify-content-center"> <!-- p-4 -->
						
							<!-- TO-DO LIST -->
							<div class="dropdown-menu d-block position-static pt-0 mx-0 rounded-3 shadow overflow-hidden w-100per" data-bs-theme="light" style="height:354px">
								<form class="p-2 mb-2 bg-body-tertiary border-bottom">
									<c:forEach var="code" items="${codeList}">
								<input type="radio" id="todo_priority" name="todo_priority" value="${code.cate_code}" 
								<c:if test="${code.cate_code eq '1'}">checked="checked"</c:if>
								>${code.cate_name}
								</c:forEach>
									<input type="text" id="todo_list" name="todo_list" class="form-control" autocomplete="false" placeholder="오늘 할 일을 메모합니다...">
								</form>
							    
							    
								<div class="list-group">
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
							
									<div id="idToDoListCount"></div>
									
									<div id="idToDoList" style="overflow:scroll; height:500px;"></div>
												
								</div>
							</div>
						</div>
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

<!-- color-modes -->
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
      <symbol id="check2" viewBox="0 0 16 16">
        <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
      </symbol>
      <symbol id="circle-half" viewBox="0 0 16 16">
        <path d="M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z"/>
      </symbol>
      <symbol id="moon-stars-fill" viewBox="0 0 16 16">
        <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z"/>
        <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>
      </symbol>
      <symbol id="sun-fill" viewBox="0 0 16 16">
        <path d="M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
      </symbol>
    </svg>

    <div class="dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle">
      <button class="btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center"
              id="bd-theme"
              type="button"
              aria-expanded="false"
              data-bs-toggle="dropdown"
              aria-label="Toggle theme (auto)">
        <svg class="bi my-1 theme-icon-active" width="1em" height="1em"><use href="#circle-half"></use></svg>
        <span class="visually-hidden" id="bd-theme-text">Toggle theme</span>
      </button>
      <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="bd-theme-text">
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="light" aria-pressed="false">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#sun-fill"></use></svg>
            Light
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="dark" aria-pressed="false">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#moon-stars-fill"></use></svg>
            Dark
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center active" data-bs-theme-value="auto" aria-pressed="true">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#circle-half"></use></svg>
            Auto
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
      </ul>
    </div>

	
    
</body>
</html>