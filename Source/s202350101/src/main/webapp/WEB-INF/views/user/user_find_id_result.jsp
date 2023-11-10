<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

</head>
<body>
	<div class="login-wrapper">
        <h2>아이디 찾기</h2>
        <form method="post" action="" id="login-form">
            ${userInfoDto.user_name }님의 아이디는 ${userInfoDto.user_id } 입니다.<p>
            <a href="user_login"><input type="button" value="로그인"></a>
            <a href="user_find_pw"><input type="button" value="비밀번호 찾기"></a>
  		</form>
        
    </div>
</body>
</html>