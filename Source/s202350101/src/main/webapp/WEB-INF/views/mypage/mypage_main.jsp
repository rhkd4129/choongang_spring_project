<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="/bootstrap-5.3.2-examples/css/modals.css"><!-- 모달창 -->		
<!--CSS START -->
<style>
	/* 좌측 프로필 컨텐츠 스타일 */
    #contents .mypage, .myenv  {
        background-color: rgba(var(--bs-tertiary-bg-rgb)) !important;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        margin: 15px;
    }
    
    #contents .mytitle {
        font-size: 20px;
    	font-weight: bold;
    }

    #contents .mypage img {
        border-radius: 50%;
        margin-bottom: 15px;
        width: 200px;
        height:200px;
    }

    #contents .mypage h2 {
        color: #007bff;
    }

    #contents .mypage h3 {
        color: #6c757d;
    }
    
	.userEnvTable th, td {
		padding: 9px;
	}
	
	#mypage_check_pw_msg {
		padding-top: 10px;
		padding-bottom: 10px;
		color: var(--bs-form-invalid-color);
	}

</style>

<!-- CSS END -->

<!-- JS START -->
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	
	function user_env_click() {
		var userEnv = $('#userEnv').serialize();
//		alert("클릭->"+userEnv);
		
		$.ajax({
			url		  : 'user_env',
			dataType  : 'text',
			data	  : userEnv,
			success : function(data) {
				if(data == 1) {
					alert("수정 되었습니다.");
					location.href='mypage_main';
				} else {
					alert("수정실패");
				}
			}
		});

	}

</script>

<!-- JS END -->

<script type="text/javascript">
	$(function() {
		
		$.ajax({
			url			: '/main_header',
			async		: false,
			dataType 	: 'html',
			success		: function(data) {
				$('#header').html(data);
			}
		});
		
		$.ajax({
			url			: '/main_menu',
			async		: false,
			dataType 	: 'html',
			success		: function(data) {
				$('#menubar').html(data);
			}
		});
	
		$.ajax({
			url			: '/main_footer',
			async		: false,
			dataType 	: 'html',
			success		: function(data) {
				$('#footer').html(data);
			}
		});
		
		
		var mypage_msg = "${msg}";
		
		//개인정보 수정 성공시
		if(mypage_msg != "" && mypage_msg == "success") {
			if(location.href.indexOf("mypage_update_result") > 0) {
				alert("수정 완료하였습니다.");
				location.href = "/mypage_main";
			}
		}

		//개인정보 수정 : 비번재확인 실패시
		if(mypage_msg != "" && mypage_msg != "success") {
			$("#btnMypageUpdate").click(); //모달창 다시띄움([개인정보 수정] 버튼 클릭)
		}
		
		//비번재확인 모달창 닫을때 메시지 지우기 닫기
		$("#closeModal").click(function(){
			$("#mypage_check_pw_msg").html("");
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
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="">내 정보 설정</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">개인정보 및 환경설정</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">개인정보 및 환경설정</span>
				</div>
			</div>

                        <%-- <h2 class="fw-normal">${userInfoDto.user_name }</h2>
                        <h5 class="fw-normal">ID : ${userInfoDto.user_id }</h5>
                        <p>핸드폰 번호 : ${userInfoDto.user_number }
				        <p>생년월일 : ${user_birth }
			        	<p>소속 : 중앙  ${classRoom.class_area }반 ${classRoom.class_room_num }호
			        	<p>기간 : ${classRoom.class_start_date } ~ ${classRoom.class_end_date }
			        	<p>담당 강사 : ${classRoom.class_master } 
                        <p><a class="btn btn-secondary" href="/mypage_check_pw">개인정보수정 »</a></p> --%>

			
			<div id="contents" class="container-fluid">
				<div class="row">
               
					<div class="mypage col-lg-5">
						<table width="100%" height="100%">
                        	<tr height="25"><td align="center"><span class="mytitle">개인정보</span></td></tr>
	                        <tr height="*">
	                        	<td>
						
									<div style="text-align:center">
										<img class="uploadFile" alt="UpLoad File" src="${pageContext.request.contextPath}/${userInfoDto.attach_path }/${userInfoDto.attach_name}">
									</div>
											
			
									<div class="row g-3">
									
										<div class="col-12">
											<label for="username" class="form-label">아이디</label>
											<div class="input-group has-validation">
												<span class="input-group-text">ID</span>
												<input type="text" class="form-control" id="username" value="${userInfoDto.user_id }" readonly>
												<div class="invalid-feedback"></div>
											</div>
										</div>
										
										<div class="col-12">
											<label for="address" class="form-label">이름</label>
											<input type="text" class="form-control" id="address" value="${userInfoDto.user_name }" readonly>
											<div class="invalid-feedback"></div>
										</div>
										
										<div class="col-md-6">
											<label for="address" class="form-label">핸드폰 번호</label>
											<input type="text" class="form-control" id="address" value="${userInfoDto.user_number }" readonly>
											<div class="invalid-feedback"></div>
										</div>
										
										<div class="col-md-6">
											<label for="firstName" class="form-label">생년월일</label>
											<input type="text" class="form-control" id="firstName" value="${user_birth }" readonly>
											<div class="invalid-feedback"></div>
										</div>
										
										<div class="col-md-8">
											<label for="address" class="form-label">소속</label>
											<input type="text" class="form-control" id="" value="중앙  ${classRoom.class_area }반 ${classRoom.class_room_num }호" readonly>
											<div class="invalid-feedback"></div>
										</div>
										
										<div class="col-md-4">
											<label for="zip" class="form-label">담당강사</label>
											<input type="text" class="form-control" id="zip" value="${classRoom.class_master }" readonly>
											<div class="invalid-feedback"></div>
										</div>
										
										<div class="col-12">
											<label for="lastName" class="form-label">기간</label>
											<input type="text" class="form-control" id="lastName" 
											value="<fmt:formatDate value="${classRoom.class_start_date}" type="date" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${classRoom.class_end_date}" type="date" pattern="yyyy-MM-dd"/>" readonly>
											<div class="invalid-feedback"></div>
										</div>
									
									</div>
									<p class="my-4"></p>
									
									<!-- <a href="/mypage_check_pw"> -->
									<button type="button" id="btnMypageUpdate" class="w-100 btn btn-success btn-lg" data-bs-toggle="modal" data-bs-target="#mypage_check_pw">개인정보 수정</button>
									<!-- </a> -->
								</td>
							</tr>
						</table>
					</div>
 
                    <div class="myenv col-lg-5">
						<table width="100%" height="100%">
                        	<tr height="25"><td align="center"><span class="mytitle">환경설정</span></td></tr>
	                        <tr height="*">
	                        	<td align="center">
                        
									<form id="userEnv">												
										<table class="userEnvTable">
											<tr>
												<td>댓글 알림</td>
												<td>
													<input type="radio" name="env_alarm_comm" value="Y"  ${userEnv.env_alarm_comm == 'Y' ? 'checked' : ''}> <label class="form-check-label">YES</label>
													<input type="radio" name="env_alarm_comm" value="N"  ${userEnv.env_alarm_comm == 'N' ? 'checked' : ''} > <label class="form-check-label">NO</label>
												</td>
											</tr>
											<tr>
												<td>답글 알림</td>
												<td>
													<input type="radio" name="env_alarm_reply" value="Y" ${userEnv.env_alarm_reply == 'Y' ? 'checked' : ''}> <label class="form-check-label">YES</label>
													<input type="radio" name="env_alarm_reply" value="N" ${userEnv.env_alarm_reply == 'N' ? 'checked' : ''}> <label class="form-check-label">NO</label>
												</td>
											</tr>
											<c:if test="${userInfoDto.user_auth == 'manager'}">
											<tr>
												<td>프로젝트 생성 승인 알림</td>
												<td>
													<input type="radio" name="env_alarm_mine" value="Y" ${userEnv.env_alarm_mine == 'Y' ? 'checked' : ''}> <label class="form-check-label">YES</label>
													<input type="radio" name="env_alarm_mine" value="N" ${userEnv.env_alarm_mine == 'N' ? 'checked' : ''}> <label class="form-check-label">NO</label>
												</td>
											</tr>
											</c:if>
											<tr>
												<td>회의일정 알림</td>
												<td>
													<input type="radio" name="env_alarm_meeting" value="Y" ${userEnv.env_alarm_meeting == 'Y' ? 'checked' : ''}> <label class="form-check-label">YES</label>
													<input type="radio" name="env_alarm_meeting" value="N" ${userEnv.env_alarm_meeting == 'N' ? 'checked' : ''}> <label class="form-check-label">NO</label>
												</td>
											</tr>
											<tr>
												<td>채팅 이용</td>
												<td>
													<input type="radio" name="env_chat" value="Y" ${userEnv.env_chat == 'Y' ? 'checked' : ''}> <label class="form-check-label">YES</label>
													<input type="radio" name="env_chat" value="N" ${userEnv.env_chat == 'N' ? 'checked' : ''}> <label class="form-check-label">NO</label>
												</td>
											</tr>
											<tr>
												<td align="center" colspan="2">
													<p></p>
													<button type="button" class="w-100 btn btn-dark btn-lg" onclick="user_env_click()">저장</button>
												</td>
											</tr>
										</table>   
									</form>

								</td>
							</tr>
						</table>

					</div>

				</div>
			</div>
													<!--게시글에 댓글이 달릴 경우 알림을 받을 수 있습니다.
														게시글에 답글이 달릴 경우 알림을 받을 수 있습니다.
														프로젝트 생성 승인이 된 경우 경우 알림을 받을 수 있습니다.
														회의 일정이 잡힌 경우 알림을 받을 수 있습니다.
														채팅 기능을 이용 할 수 있습니다.
													 -->
													 
									 
<div class="modal fade" id="mypage_check_pw" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="staticBackdropLabel">비밀번호 재확인</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<form action="/mypage_update" method="post">
				<div class="modal-body">
					<h6>회원님의 정보를 안전하게 보호하기 위해 비밀번호를 다시 확인해주세요</h6>
					<p></p>
					<label>아이디</label>
					<div style="margin-bottom:10px"><input type="text" class="form-control" name="user_id" id="user_id" placeholder="Id" value="${user_id}"></div>
					<label>비밀번호</label>
					<div style="margin-bottom:10px"><input type="password" class="form-control" name="user_pw" id="user_pw" placeholder="Password"></div>
					<div id="mypage_check_pw_msg">${msg}</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-danger">확인</button>
					<button type="button" id="closeModal" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				</div>
			</form>
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