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
					<form name="formPrjBdData" action="prj_board_data_insert" method="post" enctype="multipart/form-data">
						<input type="hidden" name="project_id" value="${userInfoDTO.project_id}">
						<input type="hidden" name="app_id" value="3">
						<input type="hidden" name="user_id" value="${userInfoDTO.user_id}">
						<input type="hidden" name="notify_flag" value="N">
						<input type="hidden" name="bd_count" value="0">
						<input type="hidden" name="good_count" value="0">
 						<input type="hidden" name="doc_group" value="${doc_group}">
						<input type="hidden" name="doc_step" value="${doc_step}">
						<input type="hidden" name="doc_indent" value="${doc_indent}">
						<input type="hidden" name="parent_doc_user_id" value="${parent_doc_user_id}">
						<input type="hidden" name="parent_doc_no" value="${parent_doc_no}">
						<input type="hidden" name="attach_name" value="">
						<input type="hidden" name="attach_path" value="">
						<input type="hidden" name="alarm_flag" value="${alarm_flag}">
						
						<div style="height:34px">
							<span class="apptitle">문서 작성</span>
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
								<td><input type="text" class="form-control" name="user_name" value="${userInfoDTO.user_name}" readonly></td>
							</tr>
							<tr>
								<td>작성일</td>
								<td><input type="text" class="form-control" name="create_date_str" value="${todayDate}" readonly></td>
							</tr>
							<c:if test="${parent_doc_user_id ne ''}">
							<tr>
								<td>원글제목</td>											 
								<td><a href="javascript:callAction('read','prj_board_data_read?doc_no=${parent_doc_no}&project_id=${project_id}')">${parent_doc_subject}</a></td>
							</tr>
							</c:if>
							<tr>
								<td>제목</td>
								<td><input type="text" class="form-control" name="subject" value="${subject}" required="required"></td>
							</tr>
							<c:if test="${parent_doc_no eq '0'}">
							<tr>
								<td>공지여부</td>
								<td>
									<input type="checkbox" class="form-check-label" name="notify_flag_chkbox" id="notify_flag_chkbox" value="N">
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
										<option value="${code.cate_code}">${code.cate_name}</option>
									</c:forEach>
									</select>
								</td>
							</tr>							
							<tr>
								<td>파일첨부</td>
								<td><input type="file" class="form-control form-control-sm" name="file1"></td>
							</tr>
							<tr>
								<td>본문</td>
								<td>							
									<div class="input-group">
										<textarea class="form-control" aria-label="With textarea" name="doc_body" rows="15"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
			</div>
			</div>
	  		<!------------------------------ //개발자 소스 입력 END ------------------------------->
</body>
</html>