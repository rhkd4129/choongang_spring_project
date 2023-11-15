<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- BOOTSTRAP START -->
<link rel="stylesheet"
	href="/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/bootstrap-5.3.2-examples/css/bootstrap.css">
<!-- 화면색모드_버튼색상 -->
<script type="text/javascript"
	src="/bootstrap-5.3.2-examples/assets/js/color-modes.js"></script>
<!-- 화면색모드 -->
<link rel="stylesheet"
	href="/bootstrap-5.3.2-examples/css/offcanvas-navbar.css">
<!-- 타이틀 -->
<link rel="stylesheet"
	href="/bootstrap-5.3.2-examples/css/dropdowns.css">
<!-- 달력 -->
<script type="text/javascript"
	src="/bootstrap-5.3.2-examples/assets/dist/js/bootstrap.bundle.min.js"></script>
<!-- BOOTSTRAP END -->

<!-- COMMON START -->
<link rel="stylesheet" type="text/css" href="/common/css/common.css">
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- COMMON END -->
<script type="text/javascript">
	$(function() {

		$.ajax({
			url : '/header.html',
			dataType : 'html',
			success : function(data) {
				$('#header').html(data);
			}
		});

		$.ajax({
			url : '/menubar.html',
			dataType : 'html',
			success : function(data) {
				$('#menubar').html(data);
			}
		});

		$.ajax({
			url : '/center.html',
			dataType : 'html',
			success : function(data) {
				$('#center').html(data);
			}
		});

		$.ajax({
			url : '/footer.html',
			dataType : 'text',
			success : function(data) {
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
			<div id="menubar"
				class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
			</div>

			<!-- 본문 -->
			<main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<!------------------------------ //개발자 소스 입력 START ------------------------------->
				<!------------------------------ //개발자 소스 입력 END ------------------------------->
			</main>

		</div>
	</div>

	<!-- FOOTER -->
	<footer class="footer py-2">
		<div id="footer" class="container"></div>
	</footer>
</body>
</html>