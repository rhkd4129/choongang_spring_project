<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.login-wrapper{
    width: 400px;
    height: 450px;
    padding: 40px;
    box-sizing: border-box;
    border: 2px solid lightgrey;
    border-radius: 10px;
    
    /* 가로 중앙 정렬을 위한 추가된 스타일 */
    margin: 0 auto; 
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.login-wrapper > h2{
    font-size: 30px;
    padding-bottom: 40px;
    color: #A6A6A6;
    margin-bottom: 40px;
    margin-top: -30px;
}
select {
    width: 250px; /* 원하는 가로 크기로 조정 (예: 200px) */
    padding: 5px; /* 내용과 경계 사이의 간격을 조절 (옵션) */
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
			    		    	var display = $('.time');
			    		    	var leftSec = 10;
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
	$(document).ready(function(){
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
<body>
	<div class="login-wrapper">
        <h2>비밀번호 찾기</h2>
        <form method="post" action="" id="login-form">
            아이디 : <input type="text" id="user_id" name="user_id" required="required" placeholder="ID"><p>
            이메일주소 : 
            <div id="mail_input">
	            <input type="text" name="auth_email" id="auth_email" required="required" placeholder="ID@gmail.com">
	            <button class="timerButton" type="button" onclick="sendNumber()">인증번호 전송</button>
            </div>
          	<br>
            <div id="mail_number" name="mail_number" style="display: none">
	            <input type="text" name="number" id="number" placeholder="인증번호 입력">
	            <small style="color: red"><div class="time"></div></small>
	    		<button class="confirmBtn" type="button" onclick="confirmNumber()">확인</button>
    		</div>
    			<small style="color: red"><div id="msg2"></div></small>
    		
    		<br>
    		<input type="hidden" id="Confirm" name="Confirm" value="">
    		
  		</form>
        
    </div>
</body>
</html>