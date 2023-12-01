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

</style>

</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

	<main class="form-signin w-100 m-auto">
		<form method="post" action="user_find_id_result" id="login-form">
			<div class="login-wrapper">
				<h2>PMS <span class="text-secondary">아이디 찾기</span></h2>
				<h1 class="h6 mb-2 fw-normal">Project Management System</h1>
				<p class="text-primary">프로젝트 관리 시스템</p>
				
				
				<div class="form-floating">
					<input type="text" class="form-control" name="user_name" placeholder="Name" value="${user_id}">
					<label for="user_id">이름</label>
				</div>
				<div class="form-floating">
					<input type="text" class="form-control" name="user_number" placeholder="010-xxxx-xxxx">
					<label for="auth_email">핸드폰 번호</label>
				</div>
	
				<div class="invalid-feedback" style="display:block">
					<small style="color: red;"><c:if test="${msg != null}">${msg }</c:if>
				</div>
				
				<div style="margin-top:20px">
					<button class="btn btn-primary w-100 py-2" type="submit">아이디 찾기</button>
				</div>
				
				<p class="mt-5 mb-3 text-body-secondary">&copy; 2023. 중앙정보기술인재개발원 프로젝트</p>
			</div>
		</form>
		
	</main>
</body>
</ht2ml>