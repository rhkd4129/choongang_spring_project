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
	    $("#mail_number").css("display", "block");
	    $.ajax({
	        url: 'user_find_pw_auth',
	        dataType: 'text',
	        data: {
	            'user_id': $('#user_id').val(),
	            'auth_email': $('#auth_email').val()
	        },
	        success: function (data) {
	            if (data == 1) {
	                $.ajax({
	                    url: 'send_save_mail',
	                    dataType: 'json',
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
	            } else if (data == 0) {
	                alert("이메일 주소가 다릅니다!");
	            } else if (data == 2) {
	                alert("아이디가 존재하지 않습니다!");
	            } else {
	                alert("무엇도아님");
	            }
	        }
	    });
	}
	
	function confirmNumber(){
		var number1 = $("#number").val();
		var number2 = $("#Confirm").val();
		
		var user_id = $('#user_id').val();
		console.log("user_id->" + user_id);
		var sendurl = "/user_find_pw_new?user_id=" + user_id;
		
		// 문제 1. authNumber가 넘어 오기전에 곧바로 확인 누르면 number1과 number2가 빈칸이라서 같음 처리됨
		// 문제 2. 인증후에 user_id를 바꿔서 확인 누르면 바뀐 아이디의 비밀번호가 바뀜
		if(number1 == number2){
			alert("인증 되었습니다.");
			location.href=sendurl;
		} else if(number1 == "") {
			alert("인증 번호를 입력해주세요.");
		} else {
			alert("인증 번호가 다릅니다.")
		}
		
	}

</script>

</head>
<body>
	<div class="login-wrapper">
        <h2>비밀번호 찾기</h2>
        <form method="post" action="" id="login-form">
            아이디 : <input type="text" id="user_id" name="user_id" required="required" placeholder="ID"><p>
            이메일주소 : 
            <div id="mail_input" name="mail_input">
	            <input type="text" name="auth_email" id="auth_email" required="required" placeholder="ID@gmail.com">
	            <button type="button" id="sendBtn" name="sendBtn" onclick="sendNumber()">인증번호 전송</button>
            </div>
            	<br>
            <div id="mail_number" name="mail_number" style="display: none">
	            <input type="text" name="number" id="number" placeholder="인증번호 입력">
	    		<button type="button" name="confirmBtn" id="confirmBtn" onclick="confirmNumber()">확인</button>
    		</div>
    		<br>
    		<input type="hidden" id="Confirm" name="Confirm" value="">
    		
  		</form>
        
    </div>
</body>
</html>