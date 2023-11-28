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
.form-signin input[name="user_name"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}
.form-signin input[name="user_number"] {
    margin-top: -1px;
    border-top-right-radius: 0;
    border-top-left-radius: 0;
    border-bottom-right-radius: var(--bs-border-radius);
    border-bottom-left-radius: var(--bs-border-radius);
}
.msg {
	border : 1px solid #e5e5e5;
	border-radius : 6px;
	padding: 10px;
	background-color: white;
	height: 80px;
	vertical-align: middle;
}

</style>

</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

	<main class="form-signin w-100 m-auto">
		<form method="" action="">
			<div class="login-wrapper">
				<h2>PMS <span class="text-secondary">아이디 찾기</span></h2>
				<h1 class="h6 mb-2 fw-normal">Project Management System</h1>
				<p class="text-primary">프로젝트 관리 시스템</p>
				
				<div class="msg">
					${userInfoDto.user_name }님의 아이디는 ${userInfoDto.user_id } 입니다.
				</div>
				
				<div style="margin-top:20px">
					<a href="user_login"><button class="btn btn-primary py-2" type="button">로그인</button></a>
					<a href="user_find_pw"><button class="btn btn-secondary py-2" type="button">비밀번호 찾기</button></a>
				</div>
				
			</div>
		</form>					
		
	</main>
</body>
</html>