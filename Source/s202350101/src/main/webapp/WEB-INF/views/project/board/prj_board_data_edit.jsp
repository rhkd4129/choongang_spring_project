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
<script type="text/javascript" src="/project/board/js/prj_board_data.js"></script>
<!-- JS END -->

<script type="text/javascript">
$(function() {
	$('#notify_flag_chkbox').click(function(){
		var checked = $(this).is(':checked');
		if(checked) {
			$(this).val("Y");
			$('input[name=notify_flag]').val("Y");
		}
		else {
			$(this).val("N");
			$('input[name=notify_flag]').val("N");
		}
	});
});

</script>
</head>
<body>
<!-- CONTENT -->
			<!------------------------------ //개발자 소스 입력 START ------------------------------->
			<div class="container-fluid">
					<form name="formPrjBdData" action="prj_board_data_update" method="post" enctype="multipart/form-data">
						<input type="hidden" name="doc_no" value="${board.doc_no}">
						<input type="hidden" name="project_id" value="${board.project_id}">
						<input type="hidden" name="user_id" value="${board.user_id}">
						<input type="hidden" name="notify_flag" value="${board.notify_flag}">
						<div style="height:34px">
							<span class="apptitle">문서 수정</span>
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
							<c:if test="${board.parent_doc_no ne ''}">
							<tr>
								<td>원글제목</td>											 
								<td><a href="javascript:callDocAjax('prj_board_data_read?doc_no=${board.parent_doc_no}&project_id=${board.project_id}')">${board.parent_doc_subject}</a></td>
							</tr>
							</c:if>
							<tr>
								<td>제목</td>
								<td><input type="text" class="form-control" name="subject" required="required" value="${board.subject}"></td>
							</tr>
							<c:if test="${board.parent_doc_no eq ''}">
							<tr>
								<td>공지여부</td>
								<td>
									<input type="checkbox" class="form-check-label" name="notify_flag_chkbox" id="notify_flag_chkbox" 
									<c:if test="${board.notify_flag eq 'Y'}">checked</c:if> 
									 value="${board.notify_flag}">
									<label class="form-check-label" for="notify_flag_chkbox">공지여부</label>
								</td>
							</tr>
							</c:if>
							<tr>
								<td>분류</td>
								<td>
									<input type="hidden" name="bd_category_name" value="">
									<select class="form-select" name="bd_category" id="bd_category">
									<c:forEach var="code" items="${bd_category_codelist}">
										<option  
										<c:if test="${board.bd_category eq code.cate_code}">selected</c:if> 
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
													<c:if test="${board.attach_path ne null}">
														<a href="/upload/${board.attach_path}" target="_blank">${board.attach_name}</a>
														&nbsp;&nbsp;<img src="/common/images/btn_icon_delete2.png" onclick="deleteFlagAttach()" style="cursor:pointer">
														<%-- <img alt="UpLoad Image" src="${pageContext.request.contextPath}/upload/${board.attach_path}" width="100"> --%>
													</c:if>													
												</div>																						
												<div id="idAttachInput" <c:if test="${board.attach_path ne null}">style="display:none;"</c:if> >
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