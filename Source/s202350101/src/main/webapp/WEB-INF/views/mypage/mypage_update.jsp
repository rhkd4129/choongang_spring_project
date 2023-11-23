<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<!-- CSS END -->

<!-- JS START -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
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
	                    extraAddr = '(' + extraAddr + ')';
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
	            document.getElementById("sample6_detailAddress").value = '';
	            document.getElementById("sample6_detailAddress").focus();
	        }
	    }).open();
	}
	
	/* 비밀번호 확인 */
	let pw_ver = 0;
	
	$(document).ready(function(){
		$("#userpasschk").blur(function(){
			if($("#userpasschk").val() != "" && $("#userpass").val() != ""){
				if($("#userpasschk").val() == $("#userpass").val()){
					$(".successPwChk").text("비밀번호가 일치합니다.");
					$(".successPwChk").css("color", "green");
					$("#pwDoubleChk").val("true");
					pw_ver = 1;
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
	
	/* 비밀번호 확인 2 */
	function update_user_info() {
		let total_ver = pw_ver;
		
		if(total_ver == 1) {
			return true;
		} else if (pw_ver != 1) {
			$(".successPwChk").text("비밀번호를 확인해주세요.");
			return false;
		}
	}
	
	function send_save_mail() {
//		alert("클릭!");
		
		if($('#user_email').val() === "") {
			alert("이메일을 입력해 주세요!");
		} else {
			$("#mail_number").css("display", "block");
//			alert("이메일을 입력했음");
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
			$(function(){
				    $(".timerButton").click(function(e){
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
			    	});
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
		}
	}
	
	function confirm_authNumber() {
		
//		alert("클릭!");
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
			mail_ver = 1;	// 이메일 인증되면 0 -> 1
			return true;
		}
	}
	


</script>

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
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="mypage_main">내 정보 설정</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">개인 정보 수정</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">개인 정보 수정</span>
				</div>
			</div>
		    
			<form:form action="mypage_update_result" 
					   id="userInfo" 
					   method="post" 
					   enctype="multipart/form-data" 
					   modelAttribute="userInfo"
					   onsubmit="return update_user_info()">
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
					<tr><th>새 비밀번호(*)</th>
						<td>
							<input type="password" id="userpass" name="user_pw" placeholder="Password">
							<small style="color: red"><form:errors path="user_pw"/></small><p>
						</td>
					</tr>
						
					<tr><th>새 비밀번호 확인(*)</th><td>
						<input type="password" id="userpasschk" placeholder="Password">
							<span class="point successPwChk"></span>
							<input type="hidden" id="pwDoubleChk"/>
						</td></tr>
					<tr><th>이름(*)</th>
						<td>
							<input type="text" name="user_name" value="${userInfoDTO.user_name}">
							<small style="color: red"><form:errors path="user_name"/></small><p>
						</td>
					</tr>
				
					<tr><th>소속</th><td>
						<select name="class_id">
							<c:forEach var="cList" items="${classList}">
								<option value="${cList.class_id }" 
									<c:if test="${cList.class_id eq userInfoDTO.class_id}">selected</c:if>>${cList.class_area }점 ${cList.class_room_num }반   ${cList.class_start_date } ~ ${cList.class_end_date }</option>
							</c:forEach>
						</select><p>
						</td></tr>
					<tr><th>핸드폰번호(*)</th>
						<td>
							<input type="text" name="user_number" required="required" value="${userInfoDTO.user_number }">
							<small style="color: red"><form:errors path="user_number"/></small> 
						</td>
					</tr>
					<tr><th>생년월일</th><td>
						<input type="date" name="user_birth" value="${user_birth}"></td></tr>
					<tr><th>성별 : </th><td>
						남 <input type="radio" name="user_gender" value="M" ${userInfoDTO.user_gender == 'M' ? 'checked' : ''}>
						여 <input type="radio" name="user_gender" value="F" ${userInfoDTO.user_gender == 'F' ? 'checked' : ''}>
					</td></tr>			    
				    <tr>
				    	<th>이메일(*) : </th>
					    <td>
					    	<input type="email" name="user_email" id="user_email" value="${userInfoDTO.user_email }" placeholder="ID@Email.com">
	           			  	<input class="timerButton" type="button" value="수정" onclick="return send_save_mail()">
	           			  	<div id="mail_number" name="mail_number" style="display: none">
							    <input type="text" name="number" id="number" placeholder="인증번호 입력">
							    <button class="confirmBtn" name="confirmBtn" id="confirmBtn" onclick="return confirm_authNumber()">확인</button>
							    <small style="color: red"><div class="time"></div></small>
					    		<small style="color: red"><div id="msg2"></div></small>
					    		<small style="color: red"><form:errors path="user_email"/></small>
					    		<input type="hidden" id="Confirm" value="">
					    	</div>
	           			</td>
           			</tr>
					<%-- <tr>
						<th>주소</th>
						<td>
							<c:if test="${postcode != null}">
								<input type="text" name="sample6_postcode" id="sample6_postcode" value="${postcode }" placeholder="우편번호">
						    </c:if>
						    <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
						    <c:if test="${address != null}">
								<input type="text" name="sample6_address" id="sample6_address" value="${address }" placeholder="주소"><br>
						    </c:if>
						    <c:if test="${detailAddress != null}">
						    	<input type="text" name="sample6_detailAddress" id="sample6_detailAddress" value="${detailAddress }" placeholder="상세주소">
						    </c:if>
						    <c:if test="${extraAddress != null}">
								<input type="text" name="sample6_extraAddress" id="sample6_extraAddress" value="${extraAddress }" placeholder="참고항목">
						    </c:if>
						    <c:if test="${userInfo.user_address != null}">
								<input type="hidden" name="user_address" value="${userInfo.user_address}"><p>
						    </c:if>
						    
						</td>
					</tr> --%>
					<tr>
					    <th>주소</th>
					    <td>
					       	<c:set var="sample6_postcode_value" value="${empty postcode ? '' : postcode}" />
							<input type="text" name="sample6_postcode" id="sample6_postcode" value="${sample6_postcode_value}" placeholder="우편번호">
					       	
					        <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
					        
					        <c:set var="sample6_address_value" value="${empty address ? '' : address}" />
							<input type="text" name="sample6_address" id="sample6_address" value="${sample6_address_value}" placeholder="주소">
					        
							<c:set var="sample6_detailAddress_value" value="${empty detailAddress ? '' : detailAddress}" />
							<input type="text" name="sample6_detailAddress" id="sample6_detailAddress" value="${sample6_detailAddress_value}" placeholder="상세주소">
					        
					        <c:set var="sample6_extraAddress_value" value="${empty extraAddress ? '' : extraAddress}" />
							<input type="text" name="sample6_extraAddress" id="sample6_extraAddress" value="${sample6_extraAddress_value}" placeholder="참고항목">
					        
					        <%-- <c:if test="${address != null}">
					            <input type="text" name="sample6_address" id="sample6_address" value="${address }" placeholder="주소"><br>
					        </c:if>
								<input type="text" name="sample6_address" id="sample6_address" value="" placeholder="주소"><br>
								
					        <c:if test="${detailAddress != null}">
					            <input type="text" name="sample6_detailAddress" id="sample6_detailAddress" value="${detailAddress }" placeholder="상세주소">
					        </c:if>
								<input type="text" name="sample6_detailAddress" id="sample6_detailAddress" value="" placeholder="상세주소">
					
					        <c:if test="${extraAddress != null}">
					            <input type="text" name="sample6_extraAddress" id="sample6_extraAddress" value="${extraAddress }" placeholder="참고항목">
					        </c:if>
								<input type="text" name="sample6_extraAddress" id="sample6_extraAddress" value="" placeholder="참고항목"> --%>
					
					        <c:if test="${userInfo.user_address != null}">
					            <input type="hidden" name="user_address" value="${userInfo.user_address}">
					        </c:if>
					    </td>
					</tr>
					<tr><td colspan="2">
						<input type="submit" value="수정">
						<!-- <button type="button" onclick="editAction()">수정</button> -->
						</td>
					</tr>
				</table>
			
			</form:form>
			
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