<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style text="text/css">
#test {
	margin-top: 5%;
	margin-bottom: 15%;
}
</style>

<script type="text/javascript">
	$(function () {
		$.ajax({
			url: '/main_header',
			dataType: 'text',
			success: function (data) {
				$('#header').html(data);
			}
		});

		$.ajax({
			url: '/main_menu',
			dataType: 'text',
			success: function (data) {
				$('#menubar').html(data);
			}
		});

		$.ajax({
			url: '/main_footer',
			dataType: 'text',
			success: function (data) {
				$('#footer').html(data);
			}
		});
	});

	function cl_room(currentpage) {
		var cl_room_val = $('#cl_room_List').val();
		console.log(cl_room_val);
		// var curpage = 1;
		var sendurl = '/admin_board/?class_id='+cl_room_val;			// + currentpage;
		console.log(sendurl);

	}
	function pr_info(currentpage) {
		var cl_room_val = $('#cl_room_List').val();
		var project_id = $('#pr_List').val();

		console.log(cl_room_val);
		// var curpage = 1;
		var sendurl = '/admin_board/?class_id='+cl_room_val+"&project_id="+project_id;			// + currentpage;
		console.log(sendurl);

	}


</script>
</head>

<body>

	<!-- HEADER -->
	<header id="header"></header>


	<!-- CONTENT -->
	<div class="container-fluid">
		<div class="row">

			<!-- 메뉴 -->
			<div id="menubar"
				class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
			</div>

			<!-- 본문 -->
			<main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<!------------------------------ //개발자 소스 입력 START ------------------------------->

				<div id="test">
					<div id="admin_page_list">
						<div class="btn btn-secondary" onclick="location.href='/admin_projectmanager'">팀장 권한 설정</div>
						<div class="btn btn-primary" onclick="location.href='/admin_board'">게시판 관리</div>
						<div class="btn btn-secondary" onclick="location.href='/admin_approval'">프로젝트 생성 승인</div>
						<div class="btn btn-secondary" onclick="location.href='/admin_add_class'">반 생성</div>
						<div class="btn btn-secondary" onclick="location.href='/admin_class_list'">반 목록</div>
					</div>
					<p></p>
					<table class="table">
						<select id="cl_room_List" onchange="cl_room(1)">
							<c:forEach items="${CRList}" var="list">
								<option name="class_room_num" value="${list.class_id}">${list.class_area}
										${list.class_room_num}</option>
							</c:forEach>
						</select>
						<select id="pr_List" onchange="pr_info()">
							<c:forEach items="${PIList}" var="list">
								<option name="pr_num" value="${list.project_id}">${list.project_name}	<%--${list.class_room_num}--%>
										</option>
							</c:forEach>
						</select>

						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>내용</th>
								<th>날짜</th>
								<th>수정</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td><a href="#">수정</td>
								<td><input type="checkbox" name="xxx" value="yyy" checked>
								</td>
							</tr>
							<tr>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td><a href="#">수정</td>
								<td><input type="checkbox" name="xxx" value="yyy" checked>
								</td>
							</tr>
							<tr>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td><a href="#">수정</td>
								<td><input type="checkbox" name="xxx" value="yyy" checked>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div id="ev">
					<div class="btn btn-secondary">이벤트</div>
					<p></p>
					<table class="table">
						<thead>

							<button class="btn btn-primary"
								onclick="location.href='addpost.html'" type="button">학원전체</button>

							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>내용</th>
								<th>날짜</th>
								<th>수정</th>
								<th>삭제</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td><a href="#">수정</td>
								<td><input type="checkbox" name="xxx" value="yyy" checked>
								</td>
							</tr>
							<tr>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td><a href="#">수정</td>
								<td><input type="checkbox" name="xxx" value="yyy" checked>
								</td>
							</tr>
							<tr>
								<td>1</td>
								<td>2</td>
								<td>3</td>
								<td>4</td>
								<td><a href="#">수정</td>
								<td><input type="checkbox" name="xxx" value="yyy" checked>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!------------------------------ //개발자 소스 입력 END ------------------------------->
			</main>

		</div>
	</div>

	<!-- FOOTER -->
	<footer class="footer py-2">
		<div id="footer" class="container"></div>
	</footer>


</body>

</html>

