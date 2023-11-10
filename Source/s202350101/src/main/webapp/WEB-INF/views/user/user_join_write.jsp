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
    height: 700px;
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
    margin-bottom: 20px;
    margin-top: 0px;
}
select {
    width: 230px; /* 원하는 가로 크기로 조정 (예: 200px) */
    padding: 5px; /* 내용과 경계 사이의 간격을 조절 (옵션) */
}
.info#info__birth {
	width: 10px;
    padding: 5px;
    display: flex;
}

</style>

<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script> -->
<script type="text/javascript">
	function id_confirm() {
		location.href="id_confirm?user_id="+frm.user_id.value;
	}
 	
	function send_save_mail() {
		location.href="send_save_mail?user_email="+frm.user_email.value;
		alert("인증번호가 전송 되었습니다.")
	}
	
	/* function confirm_authNumber() {
		location.href="confirm_auth_number?auth_number="+frm.auth_number.value;
		alert("인증번호가 전송 되었습니다.")
	} */

	

</script>
</head>
<body>
	<div class="login-wrapper">
        <h2>ChoongAng</h2>
        
        <form action="writeUserInfo" method="post" name="frm" id="login-form">
        	<table>
        	
			<tr><th>아이디 : </th><td><input type="text" name="user_id" 
				required="required" value="${user_id }">
				<input type="button" value="중복확인(ajax)" onclick="id_confirm()">
				<c:if test="${msg != null }"> ${msg }</c:if>
			</td></tr>
			<tr><th>비밀번호 : </th><td><input type="password" name="user_pw" 
				required="required"> </td></tr>
			<tr><th>비밀번호 확인 : </th><td><input type="password" name="user_pw2" 
				required="required"> </td></tr>
			<tr><th>소속</th>
			<td>
				<select name="class_id">
					<c:forEach var="classList" items="${classList}">
						<option value="${classList.class_id }">${classList.class_area }점 ${classList.class_room_num }반   ${classList.class_start_date } ~ ${classList.class_end_date }</option>
					</c:forEach>
				</select><p>
			</td></tr>
			<tr><th>이름 : </th><td><input type="text" name="user_name"></td></tr>
           	<tr><th>성별 : </th><td><input type="radio" name="user_gender" value="M">남  <input type="radio" name="user_gender" value="F">여</td></tr>
          	<tr><th>전화번호 : </th><td><input type="tel" name="user_number" placeholder="010-xxxx-xxxx" pattern="\d{2,3}-\d{3,4}-\d{4}" title="2,3자리-3,4자리-4자리"><!-- <input type="button" value="인증하기"> --></td></tr>
            <tr><th>이메일 : </th><td><input type="email" name="user_email" placeholder="ID@Email.com">
            			  <input type="button" value="이메일 인증(구현중 )" onclick="send_save_mail()">
            			  <input type="text" name="auth_number" placeholder="인증번호 6자리를 입력해주세요!" maxlength="6">
            			  <input type="button" value="인증번호 확인" onclick="confirm_authNumber">
            			  </td></tr>
            <tr><th>주소 : </th><td><input type="text" name="user_address"><p> </td></tr>
          	<tr><th>생년월일test : </th><td><input type="date" name="user_birth"><p> </td></tr>
          	<tr><th>생년월일 : </th>
          	<!-- <td>
          		<div class="info" id="info__birth">
				    <select class="box" id="birth-year">
					    <option disabled selected>출생 연도</option>
					</select>
					    <select class="box" id="birth-month">
					    <option disabled selected>월</option>
				    </select>
					    <select class="box" id="birth-day">
					    <option disabled selected>일</option>
				    </select>
				</div><p> 
			</td> -->
			</tr>
        	<td><input type="submit" value="가입하기(ajax)" style="float: center"></td>
  			</table>
  		</form>
        
    </div>
</body>
</html>