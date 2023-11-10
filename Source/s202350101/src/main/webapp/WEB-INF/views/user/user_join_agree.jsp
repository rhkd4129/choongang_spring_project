<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">

.login-wrapper{
    width: 450px;
    height: 500px;
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
    padding-bottom: 20px;
    color: #A6A6A6;
    margin-bottom: 20px;
    margin-top: -10px;
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
<body>
	<div class="login-wrapper">
        <h2>ChoongAng</h2>
        <h3>중앙정보처리 학원 계정 서비스<p> 약관에 동의해 주세요.</h3>
	<!--<form action="user_join_write" name="frm" onsubmit="return chk()"> -->
        <form action="user_join_write" name="frm">
	        <div>
		        <h4><input type="checkbox" id="checkALL"> 전체 동의하기</h4>
		        <h4><input type="checkbox" id="check1"> [필수] 중앙 정보처리 이용약관 
		        	<a href="user/user_agree_neces1.html"><small style="color: red;">[전체보기]</small></a>
		        </h4>
		        <h4><input type="checkbox" id="check2"> [필수] 개인정보 수집 및 이용
		        	<a href="user/user_agree_neces2.html"><small style="color: red;">[전체보기]</small></a>
		        </h4>
		       <!--  <h4><input type="checkbox"> [선택] 이벤트・혜택 정보 수신 수집
		        	<a href="member_agree_option.html"><small style="color: red;">[전체보기]</small></a>
		        </h4> -->
	
	        </div>
	        <input type="submit" value="다음">
        </form>
        
        <!-- <a href="user_join_write.html"><input type="button" value="다음"></a> -->
        
    </div>

</body>
</html>