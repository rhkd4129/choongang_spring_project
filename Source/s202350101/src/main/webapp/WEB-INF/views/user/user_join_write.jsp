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
</head>
<body>
	<div class="login-wrapper">
        <h2>ChoongAng</h2>
        
        <form action="writeUserInfo" method="post" name="frm" id="login-form">
        	<table>
        	<!-- <tr><td>
        		<input type="hidden" name="attach_name" value="">
        		<input type="hidden" name="attach_path">
        		<input type="hidden" name="project_id">
        		<input type="hidden" name="del_status">
        		<input type="hidden" name="chat_room_ses">
        	</td></tr>
			 -->
			<tr><th>아이디 : </th><td><input type="text" name="user_id" 
				required="required" >
				<input type="button" value="중복확인:미구현" onclick="chk()"> </td></tr>
			<tr><th>비밀번호 : </th><td><input type="password" name="user_pw" 
				required="required"> </td></tr>
			<!-- <tr><th>비밀번호 확인 : </th><td><input type="password" name="user_pw2" 
				required="required"> </td></tr>	 -->
			<tr><th>소속</th>
			<td>
				<select name="class_area">
					<c:forEach var="classList" items="${classList}">
						<option value="${classList.class_area }">${classList.class_area }</option>
					</c:forEach>
				</select><p>
				<select name="class_id">
					<c:forEach var="classList" items="${classList}">
						<option value="${classList.class_id }">${classList.class_room_num }</option>
					</c:forEach>
				</select><p>
			</td></tr>
			<tr><th>이름 : </th><td><input type="text" name="user_name"></td></tr>
           	<tr><th>성별 : </th><td><input type="radio" name="user_gender" value="M">남  <input type="radio" name="user_gender" value="F">여</td></tr>
          	<tr><th>전화번호 : </th><td><input type="tel" name="user_number" placeholder="010-xxxx-xxxx"></td></tr>
            <tr><th>이메일 : </th><td><input type="email" name="user_email" placeholder="ID@Email.com"></td></tr>
          	<tr><th>주소 : </th><td><input type="text" name="user_address"><p> </td></tr>
          	<tr><th>생년월일test : </th><td><input type="date" name="user_birth"><p> </td></tr>
          	<tr><th>생년월일 : </th>
          	<td>
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
				</div><p> </td></tr>
          	      
        	<td><input type="submit" value="가입하기2" style="float: center"></td>
  			<!-- 가입을 축하합니다 페이지 -->
  			</table>
  		</form>
        
    </div>
</body>
</html>