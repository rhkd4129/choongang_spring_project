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
<script type="text/javascript" src="/project/board/js/prj_board_report.js"></script>
<!-- JS END -->

<script type="text/javascript">
</script>
</head>
<body>
<!-- CONTENT -->
			<!------------------------------ //개발자 소스 입력 START ------------------------------->
			<div class="container-fluid">
					<form name="formPrjBdData" action="prj_board_report_update" method="post" enctype="multipart/form-data">
						<input type="hidden" name="doc_no" value="${board.doc_no}">
						<input type="hidden" name="project_id" value="${board.project_id}">
						<input type="hidden" name="user_id" value="${board.user_id}">
						<div style="margin-top:20px;height:34px">
							<h3>문서 작성</h3>
						</div>
						<table width="100%" style="margin-top:7px">
							<tr>
								<td style="text-align:right">
									<button type="submit" class="btn btn-secondary btn-sm">저장</button>
									<button type="button" class="btn btn-secondary btn-sm" onclick="closeDoc()">닫기</button>
								</td>
							</tr>
						</table>
						<table class="table" width="100%">
							<colgroup>
								<col width="20%"></col>
								<col width="80%"></col>
							</colgroup>
							<tr>
								<td>작성자</td>
								<td><input type="text" class="form-control" name="user_name" value="${board.user_name}" readonly></td>
							</tr>
							<tr>
								<td>작성일</td>
								<td><input type="text" class="form-control" name="create_date_str" value="${board.create_date}" readonly></td>
							</tr>
							<tr>
								<td>제목</td>
								<td><input type="text" class="form-control" name="subject" required="required" value="${board.subject}"></td>
							</tr>
							<tr>
								<td>분류</td>
								<td>
									<input type="hidden" name="bd_category_name" value="">
									<select class="form-select" name="bd_category" id="bd_category">
									<c:forEach var="code" items="${bd_category_codelist}">
										<option  
										<c:if test="${board.bd_category == code.cate_code}">selected</c:if> 
										value="${code.cate_code}">${code.cate_name}</option>								
									</c:forEach>
									</select>
								</td>
							</tr>							
							<tr>
								<td>파일첨부</td>
								<td>
									<table width="100%">
										<tr>
											<td>
												<input type="hidden" name="attach_name" value="${board.attach_name}">
												<input type="hidden" name="attach_path" value="${board.attach_path}">
												<input type="hidden" name="attach_delete_flag" id="idAttachDeleteFlag" value="">
												<div id="idAttachFile">
													<c:if test="${board.attach_path != null}">
														<a href="/upload/${board.attach_path}" target="_blank">${board.attach_name}</a>
														&nbsp;&nbsp;<img src="/common/images/btn_icon_delete2.png" onclick="deleteFlagAttach()" style="cursor:pointer">
														<%-- <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${board.attach_path}" width="100"> --%>
													</c:if>													
												</div>																						
												<div id="idAttachInput" <c:if test="${board.attach_path != null}">style="display:none;"</c:if> >
													<input type="file" class="form-control form-control-sm" name="file1">
												</div>
											</td>
										</tr>
									</table>								
								</td>
							</tr>
							<tr>
								<td>본문</td>
								<td>							
									<div class="input-group">
										<textarea class="form-control" aria-label="With textarea" name="doc_body" rows="15">${board.doc_body}</textarea>
									</div>
								</td>
							</tr>
						</table>
					</form>
			</div>
	  		<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>