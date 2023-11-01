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
/* 	function chk() {
		if (frm.checkbox.value = null){
			alert("약관에 동의해 주세요.");
		} else
			location.href="user_join_write.html";
	}
 */
	/* if(document.getElementById("input_check").checked) {
	    document.getElementById("input_check_hidden").disabled = true;
	} */
 
 
</script>

</head>
<body>
	<div class="login-wrapper">
        <h2>ChoongAng</h2>
        <h3>중앙정보처리 학원 계정 서비스<p> 약관에 동의해 주세요.</h3>
	<!--<form action="user_join_write" name="frm" onsubmit="return chk()"> -->
        <form action="user_join_write" name="frm">
	        <div>
		        <h4><input type="checkbox"> 전체 동의하기</h4>
		        <h4><input type="checkbox"> [필수] 중앙 정보처리 이용약관 
		        	<a href="member_agree_neces1.html"><small style="color: red;">[전체보기]</small></a>
		        </h4>
		        <h4><input type="checkbox"> [필수] 개인정보 수집 및 이용 
		        	<a href="member_agree_neces2.html"><small style="color: red;">[전체보기]</small></a>
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