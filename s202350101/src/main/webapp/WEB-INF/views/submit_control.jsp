<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script language="javascript">
if("${status}" == "success") {
	switch("${action}") {
	case "insert":
		if(opener) { //작성->저장->조회
			opener.document.location.reload();
			window.close();
		}else{
			location.href = "${redirect}";
		}
		break;
	case "update":
		if(opener) { //수정->저장->조회
			opener.document.location.reload();
			window.close();
		}else{
			location.href = "${redirect}";
		}
		break;
	default:
		location.href = "${redirect}";
		break;
	}
}
</script>
</head>
<body>
</body>
</html>