<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
<meta charset="utf-8">
<title>PMS Login</title>

<link href="/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="/bootstrap-5.3.2-examples/css/sign-in.css" rel="stylesheet">
<link href="/bootstrap-5.3.2-dist/css/bootstrap.css" rel="stylesheet">
<style type="text/css">
.form-signin {
    max-width: 800px;
    padding: 1rem;
}
.wrapper > h2{
	font-weight: bold;
    font-size: 30px;
    color: #2C3E50;
    margin-bottom: 10px;
}
.wrapper{
    width: 800px;
    box-sizing: border-box;
    border: 2px solid lightgrey;
    border-radius: 10px;
    background-color: white;
    /* 가로 중앙 정렬을 위한 추가된 스타일 */
    margin: 0 auto;
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.info-table > th, td {
	padding:5px;
}
select {
    width: 230px; /* 원하는 가로 크기로 조정 (예: 200px) */
    padding: 5px; /* 내용과 경계 사이의 간격을 조절 (옵션) */
}
.form-signin input[type="password"] {
    margin-bottom: 0px;
    border-top-right-radius: var(--bs-border-radius);
    border-top-left-radius: var(--bs-border-radius);
}
.form-signin input[type="email"] {
    margin-bottom: 0px;
    border-bottom-right-radius: var(--bs-border-radius);
    border-bottom-left-radius: var(--bs-border-radius);
}
</style>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

	/* 주소 API */
	function sample6_execDaumPostcode() {
	    new daum.Postcode({
	        oncomplete: function(data) {
	            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
	            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	            var addr = ''; // 주소 변수
	            var extraAddr = ''; // 참고항목 변수
	
	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                addr = data.roadAddress;
	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                addr = data.jibunAddress;
	            }
	
	            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	            if(data.userSelectedType === 'R'){
	                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                    extraAddr += data.bname;
	                }
	                // 건물명이 있고, 공동주택일 경우 추가한다.
	                if(data.buildingName !== '' && data.apartment === 'Y'){
	                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                }
	                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                if(extraAddr !== ''){
	                    extraAddr = ' (' + extraAddr + ')';
	                }
	                // 조합된 참고항목을 해당 필드에 넣는다.
	                document.getElementById("sample6_extraAddress").value = extraAddr;
	            
	            } else {
	                document.getElementById("sample6_extraAddress").value = '';
	            }
	
	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
	            document.getElementById('sample6_postcode').value = data.zonecode;
	            document.getElementById("sample6_address").value = addr;
	            // 커서를 상세주소 필드로 이동한다.
	            document.getElementById("sample6_detailAddress").focus();
	        }
	    }).open();
	}

	/* 중복확인 */
	let id_ver = 0;		// ID 중복확인
	let mail_ver = 0;	// 메일인증
	let pw_ver = 0; 	// PW 확인
	function id_confirm() {
		$.ajax({
			url : 'id_confirm',
			dataType : 'text',
			data : { user_id : $('#user_id').val() },
			success : function(data) {
				if($('#user_id').val() == '') {
					$('#msg').text("ID를 입력해주세요");
					return false;
				} else if(data == 1) {
					$('#user_id').val('');		// 필드값 비움
					$('#msg').text("중복된 ID 입니다!");
					return false;
				} else {
					$('#user_id').val();	// 입력한 id 유지
					$('#msg').text("사용 가능한 ID 입니다!");
					id_ver = 1;		// 사용 가능한 ID이면 중복확인 0 -> 1
					return true;
				}
			}
			
		});
	}
	
	/* 이메일 전송 */
	function send_save_mail() {
		
		$('#msg2').text("");
		// 인증 확인버튼 초기화
		$('#confirmBtn').attr("disabled", false);

		if($('#user_email').val() == "") {
			$('#msg2').text("이메일을 입력해 주세요!");
		} else {
			$("#mail_number").css("display", "");
			$.ajax({
				url : 'send_save_mail',
				type : 'POST',
				dataType : 'text',
				data : { auth_email : $('#user_email').val() },
				success : function(data) {
					alert("인증번호가 전송 되었습니다!");
					$("#Confirm").attr("value", data);
				}
			});
			
			/* 메일 타이머 */
			var timer = null;
			var isRunning = false;
			
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
		}
	}

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
    	     $('#confirmBtn').attr("disabled","disabled");
    	     isRunning = false;
            }
        }, 1000);
	    isRunning = true;
	}

	/* 이메일 인증번호 검증 */
	function confirm_authNumber() {
		
		var number1 = $("#number").val();
		var number2 = $("#Confirm").val();
		
		if(number1 == "") {
			$('#msg2').text("인증 번호를 입력해주세요.");
			return false;
		} else if(number1 != number2) {
			$('#msg2').text("인증 번호가 다릅니다.");
			return false;
		} else {
			$('#msg2').text("인증 되었습니다.");
			clearInterval(timer);
			$('#time').html("");
			mail_ver = 1;	// 이메일 인증되면 0 -> 1
			return true;
		}
	}

	/* 중복확인 + 이메일전송 검증 + 비밀번호 확인*/
	function write_user_info() {
		let total_ver = id_ver + mail_ver + pw_ver;
		
		if(total_ver == 3) {
			//alert("축하합니다 가입완료되었습니다!")
			return true;
		} else if (id_ver != 1) {
			$('#msg').text("ID 중복확인을 해주세요.");
			return false;
		} else if (pw_ver != 1) {
			$(".successPwChk").text("비밀번호를 확인해주세요.");
			return false;
		} else if (mail_ver != 1) {
			$('#msg2').text("이메일 인증이 되지 않았습니다.");
			return false;
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
					pw_ver = 1; // 비번 0 -> 1
					return true;
				}else{
					$(".successPwChk").text("비밀번호가 일치하지 않습니다.");
					$(".successPwChk").css("color", "red");
					$("#pwDoubleChk").val("false");
					return false;
				}
			}
		});
	});
	
	
</script>
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

	<main class="form-signin w-100 m-auto">
		<div class="wrapper">
			<h2>PMS <span class="text-secondary">회원 가입</span></h2>
			<h1 class="h6 mb-2 fw-normal">Project Management System</h1><span class="text-primary">프로젝트 관리 시스템</span>
        
	        <form:form action="write_user_info" method="post" 
	                   name="frm" 
	                   id="login-form" 
	                   modelAttribute="userInfo"
	                   onsubmit="return write_user_info()" width="100%">
	        	<table width="700" class="info-table">
		        	<tr>
		        		<td>
		        			<input type="hidden" name="env_alarm_comm">
		        			<input type="hidden" name="env_alarm_reply">
		        			<input type="hidden" name="env_alarm_mine">
		        			<input type="hidden" name="env_alarm_meeting">
		        			<input type="hidden" name="env_chat">
		        		</td>
		        	</tr>
		        	
					<tr>
						<th>아이디*</th>
						<td>
							<input type="text" class="form-control" id="user_id" name="user_id" value="${userInfo.user_id }">
							<small style="color: red"><form:errors path="user_id"/></small>
							<small style="color: red"><div id="msg"></div></small>
						</td>
						<td><input type="button" class="btn btn-dark btn-sm" value="중복확인" required="required" onclick="return id_confirm()"></td>
					</tr>
					<tr>
						<th>
							<label for="userpass">비밀번호*</label>
						</th>
						<td colspan="2">
							<input id="userpass" class="form-control" type="password" name="user_pw" value="${userInfo.user_pw }" placeholder="Password" autocomplete="off">
							<small style="color: red"><form:errors path="user_pw"/></small>
						</td>
					</tr>
					<tr>
						<th>
							<label for="userpasschk">비밀번호 확인*</label>
						</th>
						<td colspan="2">
							<input id="userpasschk" class="form-control" type="password" name="user_pw2" value="${userInfo.user_pw }" placeholder="동일하게 입력해주세요." autocomplete="off"/>
							
							<small style="color: red"><span class="point successPwChk"></span></small>
							<input type="hidden" id="pwDoubleChk"/>
						</td>
					</tr>
						
					<tr>
						<th>소속*</th>
						<td colspan="2">
							<select name="class_id" class="form-select" >
								<c:forEach var="classList" items="${classList}">
									<option value="${classList.class_id }">${classList.class_area }점 ${classList.class_room_num }반   
									<fmt:formatDate value="${classList.class_start_date}" type="date" pattern="yyyy-MM-dd"/>
									 ~ 
									<fmt:formatDate value="${classList.class_end_date}" type="date" pattern="yyyy-MM-dd"/>
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>이름*</th>
						<td colspan="2">
							<input type="text" class="form-control" name="user_name" value="${userInfo.user_name}">
							<small style="color: red"><form:errors path="user_name"/></small>
						</td>
					</tr>
		          	<tr>
		          		<th>생년월일</th>
		          		<td>
		          			<input type="date" class="form-control" name="user_birth" value="${userInfo.user_birth}">
		          		</td>
		          		<td></td>
		          	</tr>
		           	<tr>
		           		<th>성별</th>
		           		<td colspan="2">
		           			남 <input type="radio" name="user_gender" value="M" ${userInfo.user_gender == 'M' ? 'checked' : ''}>
		           			여 <input type="radio" name="user_gender" value="F" ${userInfo.user_gender == 'F' ? 'checked' : ''}>
		           		</td>
		           	</tr>
		          	<tr>
		          		<th>전화번호*</th>
		          		<td colspan="2">
		          			<input type="tel" class="form-control" name="user_number" placeholder="010-xxxx-xxxx" value="${userInfo.user_number}">
		          			<small style="color: red"><form:errors path="user_number"/></small> 
		          		</td>
		          	</tr>
		          	
		            <tr>
		            	<th>이메일*</th>
		            	<td>
		            		<div id="mail_input">
			            		<input type="email" class="form-control" name="user_email" id="user_email" placeholder="ID@Email.com" value="${userInfo.user_email}">
			            		<small style="color: red"><div id="msg2"></div></small>
		            		</div>
		            	</td>
		            	<td>
		            		<button type="button" id="confirmBtn" class="btn btn-danger btn-sm" onclick="send_save_mail()">이메일 인증</button>
		            	</td>
		            </tr>
		            <tr id="mail_number" name="mail_number" style="display: none">
		            	<th>인증번호 입력</th>
		            	<td>
		            		<input type="text" class="form-control" name="number" id="number" placeholder="인증번호 입력">
						    <small style="color: red"><div id="time"></div></small>
						    <button class="btn btn-dark btn-sm" type="button" onclick="confirm_authNumber()">확인</button>
				    		<small style="color: red"><form:errors path="user_email"/></small>
				    		<input type="hidden" id="Confirm" value="">				    		
		            	</td>
		            	<td></td>
		            </tr>
		            <tr>
		            	<th>우편번호</th>
		            	<td>
		            		<input type="text" class="form-control" name="sample6_postcode" id="sample6_postcode" placeholder="우편번호">
		            	</td>
		            	<td>
							<input type="button" class="btn btn-dark btn-sm" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
						</td>
					</tr>
					<tr>
						<th>주소</th>
						<td colspan="2">
							<input type="text" class="form-control mb-2" name="sample6_address" id="sample6_address" placeholder="주소">
							<input type="text" class="form-control mb-2" name="sample6_detailAddress" id="sample6_detailAddress" placeholder="상세주소">
							<input type="text" class="form-control" name="sample6_extraAddress" id="sample6_extraAddress" placeholder="참고항목">
		            		<input type="hidden" name="user_address">
		            	</td>
		            </tr>
					
		        	<tr>
		        		<td></td>
		        		<td>
		        			<!-- <input type="submit" class="w-100 btn btn-success btn-lg" value="가입하기"> -->
		        			<button class="btn btn-success w-100 py-2" type="submit">가입하기</button>
		        		</td>
		        		<td></td>
		        	</tr>		
		  		</table>
	  		</form:form>
        	<p class="mt-3 mb-3 text-body-secondary">&copy; 2023. 중앙정보기술인재개발원 프로젝트</p>
    	</div>
    </main>
</body>
</html>