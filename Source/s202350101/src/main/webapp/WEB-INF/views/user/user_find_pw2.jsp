<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
<meta charset="utf-8">
<title>PMS Login</title>

<!-- <link rel="canonical" href="https://getbootstrap.com/docs/5.3/examples/sign-in/">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@docsearch/css@3"> -->
<link href="/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="/bootstrap-5.3.2-examples/css/sign-in.css" rel="stylesheet">

<script type="text/javascript">
</script>
<style>
.login-wrapper > h2{
	font-weight: bold;
    font-size: 30px;
    color: #2C3E50;
    margin-bottom: 10px;
}
.form-signin input[type="text"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
}
</style>
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

    
<main class="form-signin w-100 m-auto">
	<form action="user_login_check" id="login-form" method="post">
		<div class="login-wrapper">
			<h2>PMS</h2>
			<h1 class="h6 mb-2 fw-normal">Project Management System</h1>
			<p class="text-primary">프로젝트 관리 시스템</p>
			
			<!-- 5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다. -->
			<div class="form-floating">
				<input type="text" class="form-control" name="user_id" id="user_id" placeholder="ID" value="${user_id}">
				<label for="user_id">ID</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" name="user_pw" id="user_pw" placeholder="Password">
				<label for="user_pw">Password</label>
			</div>
			
			<c:if test="${idMsgBox != null}">
				<div class="invalid-feedback" style="display:block">${idMsgBox}</div>
			</c:if>

			<c:if test="${pwMsgBox != null}">
				<div class="invalid-feedback" style="display:block">${pwMsgBox}</div>
			</c:if>
			
			<div class="form-check text-start my-3">
				<input class="form-check-input" type="checkbox" value="remember-me" id="remember-check">
				<label class="form-check-label" for="remember-check">
					아이디 저장하기
				</label>
			</div>
			
			<button class="btn btn-primary w-100 py-2" type="submit">Login</button>
			
			<div style="margin-top:20px">
				<a href="user_find_pw"><input type="button" class="btn btn-secondary btn-sm" style="font-size:0.75rem" value="비밀번호 찾기"></a>
				<a href="user_find_id"><input type="button" class="btn btn-secondary btn-sm" style="font-size:0.75rem" value="아이디 찾기"></a>
				<a href="user_join_agree"><input type="button" class="btn btn-outline-secondary btn-sm" style="font-size:0.75rem" value="회원가입"></a>
			</div>
			<p class="mt-5 mb-3 text-body-secondary">&copy; 2023. 중앙정보기술인재개발원 프로젝트</p>
		</div>
	</form>
	
</main>

</body>
</html>