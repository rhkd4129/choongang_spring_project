<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task List</title>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script type="text/javascript">
        // 테이블을 업데이트하는 함수 정렬함수
        function updateTable(table, data) {
            var tbody = $(table).find('tbody');

            tbody.empty();
            for (var i = 0; i < data.length; i++) {
                var statusText = "";
                var task_priority = "";

                switch (data[i].task_status) {
                    case '0':
                        statusText = '낮음';
                        break;
                    case '1':
                        statusText = '중간';
                        break;
                    case '2':
                        statusText = '높음';
                        break;
                }

                switch (data[i].task_priority) {
                    case '0':
                        task_priority = '예정';
                        break;
                    case '1':
                        task_priority = '진행중';
                        break;
                    case '2':
                        task_priority = '완료된 작업';
                        break;
                }

                var newRow = "<tr>" +
                    "<td>" + data[i].rn + "</td>" +
                    "<td>" + data[i].user_name + "</td>" +
                    "<td>" + data[i].project_s_name + "</td>" +
                    "<td><a href='task_detail?task_id=" + data[i].task_id + "&project_id=" + data[i].project_id + "'>" + data[i].task_subject + "</a></td>" +
                    "<td>" + data[i].task_start_time + "</td>" +
                    "<td>" + data[i].task_end_time + "</td>" +
                    "<td>" + statusText + "</td>" +
                    "<td>" + task_priority + "</td>" +
                    "</tr>";
                tbody.append(newRow);
            }
        }

        $(document).ready(function () {
            $.ajax({
                url: '/main_header',
                dataType: 'html',
                success: function (data) {
                    console.log("ddd");
                    $('#header').html(data);
                }
            });
            $.ajax({
                url: '/main_menu',
                dataType: 'html',
                success: function (data) {
                    $('#menubar').html(data);
                }
            });
            $.ajax({
                url: '/main_footer',
                dataType: 'html',
                success: function (data) {
                    $('#footer').html(data);
                }
            });
        });

        function toggleButtonText(keyword_division , keyword) {
            var table = $("#table1"); // 기본 테이블
            var button = $("#sort");
            console.log(keyword_division,keyword);
            if (button.val()=== "내") {
                button.val("오");
                $.ajax({
                    url: '/task_time_desc',
                    data:{"keyword":keyword, "keyword_division":keyword_division},
                    dataType: 'json',
                    success: function (data) {

                        console.log("내림차순으로 데이터 ");
                        updateTable(table, data.onelist);
                    }
                });
            } else {
                button.val("내");
                console.log(keyword_division,keyword);
                $.ajax({
                    url: '/task_time_acsc',
                    data:{"keyword":keyword, "keyword_division":keyword_division},
                    dataType: 'json',
                    success: function (data) {
                        console.log("오름차순으로 데이터 ");

                        updateTable(table, data.onelist);
                    }
                });
            }
        }

    </script>
</head>
<body>


<div id="header"></div>
<div class="container-fluid">
    <div class="row">
        <!-- 메뉴 -->
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
       		<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
			  <symbol id="house-door-fill" viewBox="0 0 16 16">
			    <path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5z"></path>
			  </symbol>
			</svg>		
			<nav aria-label="breadcrumb" style="padding-top:5px;padding-left: calc(var(--bs-gutter-x) * 0.5);">
			    <ol class="breadcrumb breadcrumb-chevron p-1">
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis" href="/main">
			          <svg class="bi" width="16" height="16"><use xlink:href="#house-door-fill"></use></svg>
			          <span class="visually-hidden">Home</span>
			        </a>
			      </li>
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="">프로젝트</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">작업 목록</li>
			    </ol>
			</nav>
			<!-- <div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">작업 목록</span>
				</div>
			</div> -->
        
			<div class="container-fluid">
				<table width="100%" style="height:45px">
					<tr>
						<td style="vertical-align:top"><span class="apptitle">작업 목록</span></td>
						<td align="right">
							<form action="task_list" method="GET">
								<table>
									<tr>
										<td>
											<select class="form-select" name="keyword_division" style="font-size:0.8rem">
					                            <option value="task_subject">작업명</option>
					                            <option value="project_s_name">단계별</option>
					                            <option value="user_name">작업자별</option>
					                        </select>
										</td>
										<td><input type="text" class="form-control me-2" style="font-size:0.8rem" name="keyword" placeholder="검색어를 입력하세요" required="required"></td>
										<td>
											<button type="submit" class="btn btn-dark btn-sm">검색</button>
											<button type="button" class="btn btn-outline-secondary btn-sm" onclick="goto('task_list')" style="cursor:pointer">
							         			<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-arrow-clockwise" viewBox="0 0 16 16">
													<path fill-rule="evenodd" d="M8 3a5 5 0 1 0 4.546 2.914.5.5 0 0 1 .908-.417A6 6 0 1 1 8 2v1z"></path>
													<path d="M8 4.466V.534a.25.25 0 0 1 .41-.192l2.36 1.966c.12.1.12.284 0 .384L8.41 4.658A.25.25 0 0 1 8 4.466z"></path>
												</svg>
											</button>
										</td>
									</tr>
								</table>
							</form>	
						</td>
					</tr>
				</table>						
				<table width="100%" style="margin-bottom:5px">
					<tr>
						<td width="100">
							<!-- <a class="btn btn-primary" href="task_create_form">새 작업</a> -->
							<button type="button" class="btn btn-dark btn-sm" onclick="goto('task_create_form')">작성</button>
						</td>
						<td width="*" style="text-align:right">
							<c:if test="${not empty keyword}">								
								<a href="prj_board_data_list"><img src="/common/images/btn_icon_delete2.png" width="18" height="19" style="vertical-align:bottom"></a> 
								검색어( <c:forEach var="code" items="${search_codelist}"><c:if test="${code.cate_code == search}">${code.cate_name}</c:if></c:forEach> = ${keyword} ) 
								<img src="/common/images/icon_search.png" width="14" height="14" style="vertical-align:bottom"> 검색 건수
							</c:if>
							<c:if test="${keyword eq null}">총 건수</c:if>
							 : ${taskCount}
						</td>
					</tr>
				</table>
        
				<c:choose>
	                <c:when test="${true== param.status}">
	                    <div class="alert alert-success" role="alert">저장 완료!</div>
	                </c:when>
	
	                <c:when test="${true== update_param.status}">
	                    <div class="alert alert-warning" role="alert">수정완료! ...</div>
	                </c:when>
	
	                <c:when test="${false== param.status}">
	                    <div class="alert alert-warning" role="alert">서버에러 ...</div>
	                </c:when>
	            </c:choose>


				<c:set var="num" value="${page.total - page.start + 1 }"></c:set>

	            <div class="table-responsive">
	                <table class="table table-hover" id="table1">
						<colgroup>
							<col width="5%"></col>
							<col width="12%"></col>
							<col width="36%"></col>
							<col width="10%"></col>
							<col width="11%"></col>
							<col width="12%"></col>
							<col width="7%"></col>
							<col width="7%"></col>
						</colgroup>
						<thead class="table-light">
	                    <tr>
	                        <th>작업번호</th>
	                        <th>프로젝트 단계</th>
	                        <th>작업명</th>
	                        <th>작업 담당자</th>
	                        <th>작업시작일</th>
	                        <th>마감일 
	                        <span id="sort" onclick="toggleButtonText('${keyword_division}','${keyword}')" style="cursor:pointer;vertical-align: -0.1em;"> 
		                        <svg xmlns="http://www.w3.org/2000/svg" width="13" height="13" fill="currentColor" class="bi bi-chevron-expand" viewBox="0 0 16 16">
									<path fill-rule="evenodd" d="M3.646 9.146a.5.5 0 0 1 .708 0L8 12.793l3.646-3.647a.5.5 0 0 1 .708.708l-4 4a.5.5 0 0 1-.708 0l-4-4a.5.5 0 0 1 0-.708zm0-2.292a.5.5 0 0 0 .708 0L8 3.207l3.646 3.647a.5.5 0 0 0 .708-.708l-4-4a.5.5 0 0 0-.708 0l-4 4a.5.5 0 0 0 0 .708z"/>
								</svg>
							</span>
	                        </th>	
	                        <th>우선순위</th>
	                        <th>작업상태</th>
	                    </tr>
	                    </thead>
	                    <tbody id="tbodys">
	                    <c:forEach var="task" items="${taskList}">
	                        <tr>
	                            <td>${task.rn}</td>
	                            <td>${task.project_s_name}</td>
	                            <td><a href='task_detail?task_id=${task.task_id}&project_id=${task.project_id}'>${task.task_subject}</a></td>
	                            <td>
	                            	${task.user_name}
									<span class="iconChat">
										<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
											<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
											<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
										</svg>
										<input type="hidden" name="chat_id" value="${task.user_id}">
									</span>
	                            </td>
	                            <td>${task.task_start_time}</td>
	                            <td>${task.task_end_time}</td>
	                            <td>
	                                <c:choose>
	                                    <c:when test="${task.task_priority == '0'}">낮음</c:when>
	                                    <c:when test="${task.task_priority == '1'}">보통</c:when>
	                                    <c:when test="${task.task_priority == '2'}">높음</c:when>
	                                </c:choose>
	                            </td>
	                            <td>
	                                <c:choose>
	                                    <c:when test="${task.task_status == '0'}">예정</c:when>
	                                    <c:when test="${task.task_status == '1'}">진행중</c:when>
	                                    <c:when test="${task.task_status == '2'}">완료됨</c:when>
	                                </c:choose>
	                            </td>
	                        </tr>
	                        <c:set var="num" value="${num - 1 }"></c:set>
	                    </c:forEach>
	                    </tbody>
	                </table>
	            </div>
				<div class="pagination justify-content-center">
		            <c:if test="${page.startPage > page.pageBlock}">
		                <a href="task_list?currentPage=${page.startPage - page.pageBlock}" class="btn btn-primary">이전</a>
		            </c:if>
		            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
		                <a href="task_list?currentPage=${i}" class="btn btn-primary">${i}</a>
		            </c:forEach>
		            <c:if test="${page.endPage < page.totalPage}">
		                <a href="task_list?currentPage=${page.startPage + page.pageBlock}" class="btn btn-primary">다음</a>
		            </c:if>
				</div>
			</div>
			<p><p>
		</main>
	</div>
</div>

<div id="footer"></div>

</body>
</html>
