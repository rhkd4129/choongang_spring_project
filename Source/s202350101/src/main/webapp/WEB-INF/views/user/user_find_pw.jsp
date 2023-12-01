<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
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
.form-signin input[name="user_id"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}
.form-signin input[type="email"] {
    margin-top: -1px;
    border-top-right-radius: 0;
    border-top-left-radius: 0;
    border-bottom-right-radius: var(--bs-border-radius);
    border-bottom-left-radius: var(--bs-border-radius);
}

</style>

<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> -->
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	
	function sendNumber() {
		$('#msg2').text("");
	    $.ajax({
	        url: 'user_find_pw_auth',
	        dataType: 'text',
	        data: {
	            'user_id': $('#user_id').val(),
	            'auth_email': $('#auth_email').val()
	        },
	        success: function (data) {
	            if (data == 1) {
	            	$("#mail_number").css("display", "block");	// 입력박스
	                $.ajax({
	                    url: 'send_save_mail',
	                    dataType: 'text',
						async: false,
	                    type: 'post',
	                    data: {
	                    	'user_id': $('#user_id').val(),
	                        'auth_email': $('#auth_email').val()
	                    },
	                    success: function (data) {
	                        console.log("data : " + data);
	                        alert("인증번호 발송");
	                        $("#Confirm").attr("value", data);
	                    }
	                });
	            	
	                /* 메일 타이머 */
	            	var timer = null;
	            	var isRunning = false;
	            	
	                $(function(){
			    		    	var display = $('#time');
			    		    	var leftSec = 120;
			    		    	// 남은 시간
			    		    	// 이미 타이머가 작동중이면 중지
			    		    	if (isRunning){
			    		    		clearInterval(timer);
			    		    		display.html("");
			    		    		startTimer(leftSec, display);
			    		    	}else{
			    		    	startTimer(leftSec, display);
			    	    		
			    	    		}
			    	})
	    	    
			    	function startTimer(count, display) {
			    	            
			    	    		var minutes, seconds;
			    	            timer = setInterval(function () {
			    	            minutes = parseInt(count / 60, 10);
			    	            seconds = parseInt(count % 60, 10);
			    	     
			    	            minutes = minutes < 10 ? "0" + minutes : minutes;
			    	            seconds = seconds < 10 ? "0" + seconds : seconds;
			    	     
			    	            display.html(minutes + ":" + seconds);
			    	     
			    	            // 타이머 끝
			    	            if (--count < 0) {
			    	    	     clearInterval(timer);
			    	    	     display.html("다시 인증해 주세요");
			    	    	     $('.confirmBtn').attr("disabled","disabled");
			    	    	     isRunning = false;
			    	            }
			    	        }, 1000);
			    	             isRunning = true;
			    	}
	            	
	            } else if (data == 0) {
	                $('#msg2').text("이메일 주소가 다릅니다!");
	            } else if (data == 2) {
	                $('#msg2').text("아이디가 존재하지 않습니다!");
	            } else if (data == 3) {
	                $('#msg2').text("이메일 주소가 존재하지 않습니다!");
	            } else {
	                alert("무엇도아님");
	            }
	        }
	    });
	}
	
	function confirmNumber(){
		var number1 = $("#number").val();
		var number2 = $("#Confirm").val();
		var number3 = 0;
		
		var user_id = $('#user_id').val();
		console.log("user_id->" + user_id);
		var sendurl = "/user_find_pw_new?user_id=" + user_id;
		if(number1 == "") {
            $('#msg2').text("인증 번호를 입력해주세요.");

		} else if(number1 != number2) {
            $('#msg2').text("인증 번호가 다릅니다.");

		} else {
			alert("인증 되었습니다.")
			number3 = 1;
			location.href=sendurl + "&number3=1";
		}
		
	}
	
	/* 비밀번호 확인 */
	$(function(){
		$("#userpasschk").blur(function(){
			if($("#userpasschk").val() != "" && $("#userpass").val() != ""){
				if($("#userpasschk").val() == $("#userpass").val()){
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
	
	/* msg box 초기화 */
	/* $("#user_id, #auth_email").on('click', function() {
	    $('#msg2').text("");
	}); */

</script>

</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

	<main class="form-signin w-100 m-auto">
		<form action="" id="login-form" method="post">
			<div class="login-wrapper">
				<h2>PMS <span class="text-secondary">비밀번호 찾기</span></h2>
				<h1 class="h6 mb-2 fw-normal">Project Management System</h1>
				<p class="text-primary">프로젝트 관리 시스템</p>
				
				
				<div class="form-floating">
					<input type="text" class="form-control" name="user_id" id="user_id" placeholder="ID" value="${user_id}">
					<label for="user_id">ID</label>
				</div>
				<div class="form-floating">
					<input type="email" class="form-control" name="auth_email" id="auth_email" required="required" placeholder="ID@gmail.com">
					<label for="auth_email">E-mail</label>
				</div>
	
				<div class="invalid-feedback" style="display:block">
					<div id="msg2" style="margin-top:10px"></div>
				</div>
				
				<div style="margin-top:20px">
					<button type="button" class="btn btn-primary w-100 py-2" onclick="sendNumber()">인증번호 전송</button>
				</div>
	
				<div id="mail_number" name="mail_number" style="display:none; margin-top:20px;">
					<input type="text" class="form-control" name="number" id="number" placeholder="인증번호 입력">
					<div class="invalid-feedback" style="display:block">
						<div id="time" style="margin-top:10px;margin-bottom:10px;"></div>
					</div>
					<button type="button" class="btn btn-secondary btn-sm" onclick="confirmNumber()">확인</button>
				</div>
				
				<input type="hidden" id="Confirm" name="Confirm" value="">
				
				<p class="mt-5 mb-3 text-body-secondary">&copy; 2023. 중앙정보기술인재개발원 프로젝트</p>
			</div>
		</form>
		
	</main>
</body>
</html>