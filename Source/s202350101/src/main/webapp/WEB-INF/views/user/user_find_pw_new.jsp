<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
<meta charset="utf-8">
<title>PMS Login</title>

<link href="/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="/bootstrap-5.3.2-examples/css/sign-in.css" rel="stylesheet">

<style>
.login-wrapper > h2{
	font-weight: bold;
    font-size: 30px;
    color: #2C3E50;
    margin-bottom: 10px;
}

.form-signin input[name="user_pw1"] {
    margin-bottom: -1px;
    border-top-right-radius: var(--bs-border-radius);
    border-top-left-radius: var(--bs-border-radius);
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}
.form-signin input[name="user_pw2"] {
    margin-top: -1px;
    border-top-right-radius: 0;
    border-top-left-radius: 0;
}
</style>

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	/* 비밀번호 확인 */
	//$(document).ready(function(){
	$(function(){
		$("#user_pw2").blur(function(){
			if($("#user_pw2").val() != "" && $("#user_pw1").val() != ""){
				if($("#user_pw2").val() == $("#user_pw1").val()){
					$(".successPwChk").text("비밀번호가 일치합니다.");
					$(".successPwChk").css("color", "green");
					$("#pwDoubleChk").val("true");
				}else{
					$(".successPwChk").text("비밀번호가 일치하지 않습니다.");
					$(".successPwChk").css("color", "red");
					$("#pwDoubleChk").val("false");
				}
			}
		});
	});
	
/* 	function updatePw() {
	//	alert("클릭했음");
		var user_pw = $('#user_pw1').val();
		var user_pw2 = $('#user_pw2').val();
		var user_id = $('#user_id').val();
		var sendurl = "/user_find_pw_update?user_pw=" + user_pw + "&user_id=" + user_id; 
		
		console.log(user_id + " / " + user_pw);
		
		if (user_pw != user_pw2) {
			$(".successPwChk").text("비밀번호를 확인해 주세요.");
		} else {
			alert("수정되었습니다");
			location.href = sendurl;
		}		
	} */
	
	function updatePw() {
		var user_pw = $('#user_pw1').val();
		var user_pw2 = $('#user_pw2').val();
		
		if($.trim(user_pw) == '' || $.trim(user_pw2) == '') {
			$('#user_pw1').val("");
			$('#user_pw2').val("");
			$(".successPwChk").text("새 비밀번호를 입력해 주세요.");
			return false;
		}
		if(user_pw != user_pw2) {
			$(".successPwChk").text("비밀번호를 확인해 주세요.");
			return false;
		}
		
		var user_id = $('#user_id').val();
		var sendurl = "/user_find_pw_update"; 
		
		console.log(user_id + " / " + user_pw);
		
		 $.ajax({
             url: sendurl,
             type: 'post',
             data: {
             	'user_pw': user_pw,
                'user_id': user_id
             },
             dataType: 'text',
             success: function (data) {
                 console.log("data : " + data);
                 if(data == "success") {
                	 alert("새 비밀번호로 변경되었습니다");
                	 location.href = "/user_login";
                 }else{
                	 alert("새 비밀번호로 변경에 실패했습니다. 관리자에 문의하세요.");
                 }                 
             }
         });
	}
</script>

</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

<main class="form-signin w-100 m-auto">
	<div class="login-wrapper">
		<h2>PMS <span class="text-secondary">비밀번호 찾기</span></h2>
		<h1 class="h6 mb-2 fw-normal">Project Management System</h1>
		<p class="text-primary">프로젝트 관리 시스템</p>
				
		<form method="post" id="login-form" name="frm">
			<input type="hidden" name="user_id" id="user_id" value="${user_id }">
			
			<div class="form-floating">
				<input type="password" class="form-control" name="user_pw1" id="user_pw1" required="required" placeholder="PW">
				<label for="user_pw1">새 비밀번호</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" name="user_pw2" id="user_pw2" required="required" placeholder="PW2">
				<label for="user_pw1">새 비밀번호 확인</label>
			</div>

			<div class="invalid-feedback" style="display:block">
				<span class="point successPwChk"></span>
			</div>
			
			<input type="hidden" id="pwDoubleChk"/>
			<div style="margin-top:10px">
				<button type="button" class="btn btn-primary w-100 py-2" onclick="updatePw()">확인</button>
			</div>
			<p class="mt-5 mb-3 text-body-secondary">&copy; 2023. 중앙정보기술인재개발원 프로젝트</p>
  		</form>
    </div>
</main>
</body>
</html>