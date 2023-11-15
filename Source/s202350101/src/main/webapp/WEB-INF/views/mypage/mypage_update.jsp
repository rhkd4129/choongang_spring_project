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

<!-- JS START -->
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	
	function editAction() {
		alert("클릭했음");
	   	var formData = new FormData();

	    var file = $('#file1')[0].files[0]; // 파일을 가져옵니다.
	    formData.append('file1', file); // FormData에 파일을 추가합니다.
	
	    var userInfo = $('#userInfo').serialize(); // 다른 사용자 정보도 FormData에 추가할 수 있습니다.
	    formData.append('userInfo', userInfo);
		
		$.ajax({
			 url:'mypage_update_result',
			 method: 'GET',
			 
		     dataType : 'text',
		     data : formData, userInfo
			 data : userInfo,
			 contentType : false,
			 processData : false,
			 success : function(data) {
				if(data == 1){
					alert("수정완료");
					location.href='mypage_main';
				} else {
					alert("수정실패");
				}
			}
	
		});
	}

</script> -->

<!-- JS END -->
<script type="text/javascript">

	$(function() {
		
		$.ajax({
			url			: '/main_header',
			dataType 	: 'text',
			success		: function(data) {
				$('#header').html(data);
			}
		});
		
		$.ajax({
			url			: '/main_menu',
			dataType 	: 'text',
			success		: function(data) {
				$('#menubar').html(data);
			}
		});
	
		$.ajax({
			url			: '/main_footer',
			dataType 	: 'text',
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
			<!------------------------------ //개발자 소스 입력 START ------------------------------->
		    
		    <h2>개인 정보 수정</h2>
			<form action="mypage_update_result" id="userInfo" method="post" enctype="multipart/form-data">
				<input type="hidden" name="user_id" value="${userInfoDTO.user_id }">
				<input type="hidden" name="project_id" value="${userInfoDTO.project_id }">
				<table>
					<tr>
						<th>프로필 사진</th>
						<td><img class="uploadFile" style="size: width: 250px; height: 250px;" alt="UpLoad File" src="${pageContext.request.contextPath}/${userInfoDTO.attach_path }/${userInfoDTO.attach_name}"><br>
						<input type="file" class="form-control" name="file1" id="file1">
						</td>
						
					</tr>
					<tr><th>아이디</th><td>${userInfoDTO.user_id}</td></tr>
					<tr><th>새 비밀번호</th><td>
						<input type="password" id="user_pw1" name="user_pw" placeholder="Password"></td></tr>
					<tr><th>새 비밀번호 확인</th><td>
						<input type="password" id="user_pw2" placeholder="Password"></td></tr>
					<tr><th>이름</th><td>
						<input type="text" name="user_name" value="${userInfoDTO.user_name}"></td></tr>
					<tr><th>소속</th><td>
						<select name="class_id">
							<c:forEach var="classList" items="${classList}">
								<option value="${classList.class_id }">${classList.class_area }점 ${classList.class_room_num }반   ${classList.class_start_date } ~ ${classList.class_end_date }</option>
							</c:forEach>
						</select><p>
						</td></tr>
					<tr><th>핸드폰번호</th><td>
						<input type="text" name="user_number" required="required" value="${userInfoDTO.user_number }"></td></tr>
					<tr><th>생년월일</th><td>
						<input type="date" name="user_birth" required="required" value="${userInfoDTO.user_birth }"></td></tr>
					<tr><th>성별 : </th><td>
						남 <input type="radio" name="user_gender" value="M" ${userInfoDTO.user_gender == 'M' ? 'checked' : ''}>
						여 <input type="radio" name="user_gender" value="F" ${userInfoDTO.user_gender == 'F' ? 'checked' : ''}>
					</td></tr>			    
				    <tr><th>이메일 : </th><td>
				    	<input type="email" name="user_email" value="${userInfoDTO.user_email }" placeholder="ID@Email.com">
           			  	<input type="button" value="이메일 인증(이메일 저장  + 메일전송)" onclick="send_save_mail()"></td></tr>
					<tr><th>주소</th>
						<td>
						<input type="text" name="user_address" value="${userInfoDTO.user_address }">
						</td>
					</tr>
					<tr><td colspan="2">
						<input type="submit" value="수정">
						<!-- <button type="button" onclick="editAction()">수정</button> -->
						</td>
					</tr>
				</table>
			
			</form>
			
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