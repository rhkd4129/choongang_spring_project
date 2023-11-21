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

			<form action="req_edit" method="post" enctype="multipart/form-data">

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
					<td><input type="hidden" name="project_manager_id" value="${prjInfo.project_manager_id }">
						<input type="text" name="project_manager_name" value="${prjInfo.project_manager_name }" class="form-control" aria-label="Username" aria-describedby="basic-addon1"></td>
				</tr>
				<tr>
					<th>프로젝트 팀원</th>
					<td style="line-height:40px"><c:set var="num" value="1"></c:set>
						<c:forEach var="User" items="${listName}">
						 	<c:if test="${prjInfo.project_manager_id != User.user_id}">
						 	 	<input type="checkbox" name="member_user_id" value="${User.user_id}"
						 	 		<c:forEach var="member" items="${listMember}">
						 	 			<c:if test="${member.user_id == User.user_id}"> checked </c:if>
						 	 		</c:forEach>
						 	 	><label >  ${User.user_name }  </label>	
						 	 	<c:if test="${num % 10 == 0 }"><br></c:if>
						 	 	<c:set var="num" value="${num+1 }"></c:set>
						 	</c:if>			  			 	
			  			 </c:forEach></td>
				</tr>					
				<tr>
					<th>프로젝트 소개</th>
					<td><textarea class="form-control" name="project_intro" aria-label="With textarea" rows="5">${prjInfo.project_intro}</textarea></td>
				</tr>
				
				<tr>
					<td>파일첨부</td>
					<td>
						<table width="100%">
							<tr>
								<td>
									<input type="hidden" name="attach_name" value="${prjInfo.attach_name}">
									<input type="hidden" name="attach_path" value="${prjInfo.attach_path}">
									<input type="hidden" name="attach_delete_flag" id="idAttachDeleteFlag" value="">
									<div id="idAttachFile">
										<c:if test="${prjInfo.attach_path ne null}">
											<a href="/upload/${prjInfo.attach_path}" target="_blank">${prjInfo.attach_name}</a>
											&nbsp;&nbsp;<img src="/common/images/btn_icon_delete2.png" onclick="deleteFlagAttach()" style="cursor:pointer">
											<%-- <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${board.attach_path}" width="100"> --%>
										</c:if>													
									</div>																						
									<div id="idAttachInput">
										<input type="file" class="form-control form-control-sm" name="file1">
									</div>
								</td>
							</tr>
						</table>								
					</td>
				</tr>
	 				
			</table>
			
				<div align="right">
					 <button type="submit" class="btn btn-secondary" style="width:130px;height:35px" >저장</button>
				</div>

</form>
	  		<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>