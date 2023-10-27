<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- BOOTSTRAP START -->
<link rel="stylesheet" href="/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="/bootstrap-5.3.2-examples/css/bootstrap.css"><!-- 화면색모드_버튼색상 -->
<script type="text/javascript" src="/bootstrap-5.3.2-examples/assets/js/color-modes.js"></script><!-- 화면색모드 -->
<link rel="stylesheet" href="/bootstrap-5.3.2-examples/css/offcanvas-navbar.css"><!-- 타이틀 -->
<link rel="stylesheet" href="/bootstrap-5.3.2-examples/css/dropdowns.css"><!-- 달력 -->
<script type="text/javascript" src="/bootstrap-5.3.2-examples/assets/dist/js/bootstrap.bundle.min.js"></script>
<!-- BOOTSTRAP END -->

<!-- COMMON START -->
<link rel="stylesheet" type="text/css" href="/common/css/common.css">
<script type="text/javascript" src="/common/js/common.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- COMMON END -->
<script type="text/javascript">
	$(function() {
		
		$.ajax({
			url			: '/header.html',
			dataType 	: 'text',
			success		: function(data) {
				$('#header').html(data);
			}
		});
		
		$.ajax({
			url			: '/menubar.html',
			dataType 	: 'text',
			success		: function(data) {
				$('#menubar').html(data);
			}
		});
		
		$.ajax({
			url			: '/center.html',
			dataType 	: 'text',
			success		: function(data) {
				$('#center').html(data);
			}
		});
	
		$.ajax({
			url			: '/footer.html',
			dataType 	: 'text',
			success		: function(data) {
				$('#footer').html(data);
			}
		});
	});
</script>
</head>
<body>

</body>
</html>