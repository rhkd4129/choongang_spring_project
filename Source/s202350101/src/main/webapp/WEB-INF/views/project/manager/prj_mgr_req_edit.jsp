<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<!-- CSS END -->

<!-- JS START -->
<!-- JS END -->

<script type="text/javascript">

</script>
</head>
<body>


			<!------------------------------ //개발자 소스 입력 START ------------------------------->

			<form action="req_edit" method="post">
			
			<h2>프로젝트 정보</h2>
			<hr>
			<input type="hidden" name="project_id" value="${prjInfo.project_id }">
			<input type="hidden" name="project_approve" value="${prjInfo.project_approve}" >
			<input type="hidden" name="project_status" value="${prjInfo.project_status}">
			<table class="table">
			<colgroup>
			<col width="150">
			<col width="*">
			</colgroup> 
				<tr>
					<th>프로젝트명</th>
					<td> <input type="text" name="project_name" value="${prjInfo.project_name}" class="form-control" aria-label="Username" aria-describedby="basic-addon1"></td>
				</tr>
				<tr>
					<th>프로젝트 기간</th>
					<td>
						<table>
						<tr>
						<td><input type="date" name="project_startdate" class="form-control" value="${prjInfo.project_startdate}"></td> 
						<td> ~ </td>
						<td><input type="date" name="project_enddate" class="form-control" value="${prjInfo.project_enddate}"></td>
						</tr>
						</table>
					</td>
				</tr>
				<tr>
					<th>프로젝트 팀장</th>
					<td><input type="hidden" name="project_manager_id" value="${userInfo.user_id }">
						<input type="text" name="project_manager_name" value="${userInfo.user_name }" class="form-control" aria-label="Username" aria-describedby="basic-addon1"></td>
				</tr>
				<tr>
					<th>프로젝트 팀원</th>
					<td><c:set var="num" value="0"></c:set>
						<c:forEach var="User" items="${listName}">
						 	<c:if test="${userInfoDTO.user_id != User.user_id}">
						 	 	<input type="checkbox" name="member_user_id" value="${User.user_id}"
						 	 		<c:forEach var="member" items="${listMember}">
						 	 			<c:if test="${member.user_id == User.user_id}"> checked </c:if>
						 	 		</c:forEach>
						 	 	><label >  ${User.user_name }  </label>	
						 	</c:if>
						 	<c:if test="${num % 8 == 0 }"><p></c:if>
						 	<c:set var="num" value="${num+1 }"></c:set>			  			 	
			  			 </c:forEach></td>
				</tr>					
				<tr>
					<th>프로젝트 소개</th>
					<td><textarea class="form-control" name="project_intro" aria-label="With textarea" rows="5">${prjInfo.project_intro}</textarea></td>
				</tr>
	 				
			</table>
			
				<div align="right">
					 <button type="submit" class="btn btn-secondary" >저장</button>
				</div>

</form>
	  		<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>