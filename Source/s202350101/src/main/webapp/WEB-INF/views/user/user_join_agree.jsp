<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
<meta charset="utf-8">
<title>PMS Login</title>

<link href="/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="/bootstrap-5.3.2-examples/css/sign-in.css" rel="stylesheet">
<link href="/bootstrap-5.3.2-dist/css/bootstrap.css" rel="stylesheet">
<style>
.form-signin {
    max-width: 600px;
    padding: 1rem;
}
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
.agree-conditions {
	overflow:scroll;
	margin-top:10px;
	width:100%;
	height:100px;
	border:1px solid #e5e5e5;
	padding:10px;
}
.btn-next {
	text-align:right;
}
</style>
<script type="text/javascript">
	// checkALL이 체크되면 check1, check2가 모두 체크되는 자바스크립트
	document.addEventListener("DOMContentLoaded", function() {
	    var checkAll = document.getElementById("checkALL");
	    var check1 = document.getElementById("check1");
	    var check2 = document.getElementById("check2");
	
	    checkAll.addEventListener("change", function() {
	        check1.checked = this.checked;
	        check2.checked = this.checked;
	    });
	
	    check1.addEventListener("change", function() {
	        if (!this.checked) {
	            checkAll.checked = false;
	            
	        }
	    });
	
	    check2.addEventListener("change", function() {
	        if (!this.checked) {
	            checkAll.checked = false;
	            
	        }
	    });
	    
	 	// 모두 체크되지 않으면 메세지
	    var form = document.getElementsByName("frm")[0];

    	form.addEventListener("submit", function(event) {
	        if (!check1.checked || !check2.checked) {
	            event.preventDefault(); // 폼 제출 방지
	            alert("이용약관을 모두 동의해 주세요."); // 오류 메시지 표시
	        }
    	});
	    
	    
	    // check1, check2가 체크되면 checkALL이 체크되는 로직
	    check1.addEventListener("change", updateCheckAll);
	    check2.addEventListener("change", updateCheckAll);

	    function updateCheckAll() {
	        if (check1.checked && check2.checked) {
	            checkAll.checked = true;
	        } else {
	            checkAll.checked = false;
	        }
	    }
	    
	});
</script>

</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">

	<main class="form-signin w-100 m-auto">
		<form action="user_join_write" name="frm">
			<div class="login-wrapper">
				<h2>PMS <span class="text-secondary">약관 동의</span></h2>
				<h1 class="h6 mb-2 fw-normal">Project Management System</h1>
				<p class="text-primary">프로젝트 관리 시스템</p>
				
        		<h5>중앙정보처리 학원 계정 서비스<p> 약관에 동의해 주세요.</h5>
		        <div>
			        <h6><input type="checkbox" id="checkALL"> 전체 동의하기</h6>
			        
			        <h6>
			        	<input type="checkbox" id="check1"> <label for="check1"> [필수] 중앙 정보처리 이용약관</label>
			        	<div class="agree-conditions">이용약관 넣을예정</div>
			        </h6>
			        
			        <h6>
			        	<input type="checkbox" id="check2"> <label for="check2"> [필수] 개인정보 수집 및 이용</label>
			           	<div class="agree-conditions">이용약관 넣을예정</div>			        
			        </h6>
			        
			       <!--<h4><input type="checkbox"> [선택] 이벤트・혜택 정보 수신 수집
			        	<a href="member_agree_option.html"><small style="color: red;">[전체보기]</small></a>
			        </h4> -->
		        </div>
				<div class="btn-next"><input type="submit" class="btn btn-primary" value="다음"></div>
				
				<p class="mt-5 mb-3 text-body-secondary">&copy; 2023. 중앙정보기술인재개발원 프로젝트</p>
			</div>
		</form>
		
	</main>
</body>
</html>