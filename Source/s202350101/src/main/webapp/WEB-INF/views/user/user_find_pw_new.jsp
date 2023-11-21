<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %> 
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

<script type="text/javascript">

	/* 비밀번호 확인 */
	$(document).ready(function(){
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
	
	function updatePw() {
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
		
	}
</script>

</head>
<body>
	<div class="login-wrapper">
        <h2>비밀번호 찾기</h2>
        <form method="post" id="login-form" name="frm">
        <input type="hidden" name="user_id" id="user_id" value="${user_id }">
           새 비밀번호 : 	 <input type="password" name="user_pw1" id="user_pw1" required="required" placeholder="PW"><p>
           새 비밀번호 확인 : <input type="password" name="user_pw2" id="user_pw2" required="required" placeholder="PW2"><p>
        <span class="point successPwChk"></span>
        <input type="hidden" id="pwDoubleChk"/>
        <button type="button" onclick="updatePw()">확인</button>
  		</form>
        
    </div>
</body>
</html>