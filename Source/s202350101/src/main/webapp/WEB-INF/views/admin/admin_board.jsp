<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">

    <style text="text/css">
    </style>

    <script type="text/javascript">
        $(function () {
            $.ajax({
                url: '/main_header',
                dataType: 'html',
                success: function (data) {
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

//  강의실 변경 시
        function cl_room(currentpage) {
            var cl_room_val = $('#cl_room_List').val();
            console.log(cl_room_val);
            // var curpage = 1;
            var sendurl = '/admin_board_ajax/?class_id=' + cl_room_val;			// + currentpage;
            console.log(sendurl);
            $.ajax({
                url: sendurl,
                dataType: 'json',
                success: function (jsonData) {
                    console.log(jsonData);
                    var projectList = $('#pr_List');
                    projectList.empty();

                    $.each(jsonData.secList, function (index, prList) {
                        console.log("PRLIST: " + prList);
                        var authOptionBox = $('<option name="pr_num" value="' + prList.project_id + '"> ' + prList.project_name + '</option>');
                        projectList.append(authOptionBox);
                    });
                }
            })
        }

        function pr_info(currentpage) {
            var cl_room_val = $('#cl_room_List').val();
            var project_id = $('#pr_List').val();
            var option = {
                class_id: cl_room_val, //  현재 채팅방
                project_id: project_id,       //  본인 id
                currentpage: currentpage,         //  상대 id
            }
            console.log(cl_room_val);
            // var curpage = 1;
            var sendurl = '/admin_board_pbd_ajax';			// + currentpage;
            console.log(sendurl);
            $.ajax({
                url: sendurl,
                data: option,
                success: function (jsonData) {
                    console.log(jsonData);
                    var PBDList_body = $('#PBDList_body');
                    PBDList_body.empty();
                    // 게시글 목록 동적 생성
                    $.each(jsonData.firList, function (index, board) {
                        var tr = $('<tr>');
                        tr.append('<td>' + board.rn + '</td>');

                        tr.append('<input type="hidden" id="pbd_doc_no" value="' +board.doc_no +'"/>');
                        tr.append('<input type="hidden" id="pbd_prj_no" value="' +board.project_id +'"/>');

                        tr.append('<td style="cursor: pointer; color: blue;" onClick="locatPrj(' + board.doc_no + ',' + board.project_id + ',' + "'prj_board_data_read'" + ')">'+ board.subject+'</td>');
                        tr.append('<td>' + board.user_name + '</td>');
                        tr.append('<td>' + formatDate(new Date(board.create_date)) + '</td>'); // 날짜 포맷 변경
                        tr.append('<td>' + board.bd_category_name + '</td>');
                        // 첨부 파일 관련
                        var attach_name = board.attach_name;
                        var attach_length = attach_name ? attach_name.length : 0;
                        var extension_name = attach_length > 3 ? attach_name.substring(attach_length - 3, attach_length) : '';
                        // if (extension_name !== '') {
                        //     var img = $('<img src="/common/images/attach/icon_' + extension_name + '.png" alt="' + board.attach_name + '" style="cursor:pointer" onclick="popup(\'/upload/' + board.attach_path + '\',800,600)">');
                        //     tr.append($('<td>').append(img));
                        // } else {
                        //     tr.append('<td></td>'); // 빈 칸 처리
                        // }
                        tr.append('<td>' + board.bd_count + '</td>');
                        tr.append('<td>' + board.good_count + '</td>');

                        tr.append('<td style="cursor: pointer; color: blue;" onClick="locatPrj(' + board.doc_no + ',' + board.project_id + ',' + "'prj_board_data_edit'" + ')">수정</td>');
                        tr.append('<td><input type="checkbox" name="pbd_del_chkbox" />');
                        PBDList_body.append(tr);
                    });
                    // 페이징 동적 생성
                    var paginationDiv = $('#d_p');
                    paginationDiv.empty();
                    var jspPagination = '<div id="d_p" class="pagination justify-content-center">';
                    if (jsonData.obj.startPage > jsonData.obj.pageBlock) {
                        jspPagination += '<div onclick="pr_info(' + (jsonData.obj.startPage - jsonData.obj.pageBlock) + ')"><p>[이전]</p></div>';
                    }
                    for (var i = jsonData.obj.startPage; i <= jsonData.obj.endPage; i++) {
                        var currentPageStyle = i === jsonData.obj.currentPage ? '-webkit-text-stroke: thick;' : '';
                        jspPagination += '<div class="page-item" style="' + currentPageStyle + '" onClick="pr_info(' + i + ')"><div class="page-link">' + i + '</div></div>';
                    }
                    if (jsonData.obj.endPage < jsonData.obj.totalPage) {
                        jspPagination += '<div onclick="pr_info(' + (jsonData.obj.startPage + jsonData.obj.pageBlock) + ')"><p>[다음]</p></div>';
                    }
                    jspPagination += '</div>';
                    paginationDiv.html(jspPagination);
                    // 날짜 포맷 함수
                    function formatDate(date) {
                        var year = date.getFullYear();
                        var month = ("0" + (date.getMonth() + 1)).slice(-2);
                        var day = ("0" + date.getDate()).slice(-2);
                        return year + "-" + month + "-" + day;
                    }
                }
            });
        }

        function event_search(currentPage) {
            var keyword = $('#search_text').val();      //  검색 키워드
            var category = $('#bd_CTG').val();          //  선택한 카테고리
            if (category === '전체') {                   //  전체 카테고리 선택 시
                category = '%%';                        //      모든 카테고리 처리
            }
            console.log(keyword);
            console.log(currentPage);
            console.log(category);

            var sendData = {
                keyword: keyword,
                bd_category: category,
                currentPage: currentPage
            };

// 데이터를 URL에 직접 추가
// var sendurl = "/admin_board_ajax_paging_search/?keyword=" + encodeURIComponent(keyword) + "&currentPage=" + currentPage;
            var sendurl = "/admin_board_ajax_paging_search";
            console.log(sendurl);
            $.ajax({
                url: "/admin_board_ajax_paging_search",
                contentType: "application/json; charset=UTF-8",
                dataType: 'json',
                data: sendData,
                success: function (jsonData) {
                    console.log(jsonData);  //  반환되는 데이터

                    var BFList_body = $('#BFList_body');        //  게시글 목록 지정
                    BFList_body.empty();                        //  게시글 목록 중복을 막기 위한 내용 삭제

                    $.each(jsonData.firList, function (index, BFL) {        //  반환된 데이터 입력
                        let board_category = BFL.bd_category;
						let board_update = BFL.bd_category;
                        // category = (category == '공지')? board_content:
                        switch (board_category) {
                            case '공지':
								board_category = 'board_content';
								board_category = 'board_update';
                                break;
                            case "이벤트":
								board_category = 'event_content';
								board_category = 'event_update';
                                break;
                            case "자유":
								board_category = 'free_content';
								board_category = 'free_update';
                                break;
                        }
                        var tr = $('<tr>');
                        tr.append('<input type="hidden" id="bf_doc_no" value="'+ BFL.doc_no + '"/>');
                        tr.append('<td>' + BFL.rn + '</td>');
                        tr.append('<td>' + BFL.bd_category + '</td>');

                        tr.append('<td style="cursor: pointer; color: blue;" onclick="location.href=\'/' +board_category +'?doc_no=' + BFL.doc_no + '\'">' + BFL.subject + '</td>');

                        // tr.append('<td onclick="location.href=' + 'board_category' + '">' + BFL.subject + '</td>');
                        tr.append('<td>' + BFL.user_name + '</td>');
                        tr.append('<td>' + BFL.create_date + '</td>');
                        tr.append('<td>' + BFL.good_count + '</td>');
						<%--<td><a href="board_update?doc_no=${BF.doc_no}">수정</a></td>--%>
						tr.append('<td style="cursor: pointer; color: blue;" onclick="location.href=\'/' +board_category +'?doc_no=' + BFL.doc_no + '\'">' +"수정"+ '</td>');
                        tr.append('<td><input type="checkbox" name="del_chkbox" />');
                        BFList_body.append(tr);
                    });
                    var page = jsonData.obj;            //  페이징 객체

                    var paginationDiv = $('#e_p');      //  페이징 번호 위치
                    paginationDiv.empty();              //  페이징 번호 중복을 막기 위한 내용 삭제
                    var jspPagination = '<div id="e_p" class="pagination">';
                    if (page.startPage > page.pageBlock) {
                        jpsPagination += '<div onclick="event_search(' + (page.startPage - page.pageBlock) + ')"><p>이전</p></div>';
                    }
                    for (var i = page.startPage; i <= page.endPage; i++) {
                        var currentPageStyle = i === page.currentPage ? '-webkit-text-stroke: thick;' : '';     // 현재 페이지와 i가 일치할 때 스타일을 적용

                        jspPagination += '<div class="page-item" style="' + currentPageStyle + '" onClick="event_search(' + i + ')"><div class="page-link">' + i + '</div></div>';
                    }
                    if (page.endPage < page.totalPage) {
                        jpsPagination += '<div onclick="event_search(' + (page.startPage + page.pageBlock) + ')"><p>이전</p></div>';
                    }
                    jspPagination += '</div>';
                    //  위 html코드들을 입력
                    paginationDiv.html(jspPagination);

                }
            })
        }
        //  게시글 삭제
        function bdfree_del(){
            var delbox = [];        //  삭제 버튼에 체크된 게시글
            document.querySelectorAll("input[name=del_chkbox]:checked").forEach(function (checkbox) {
                //  체크된 게시글 id값들 리스트에 저장.
                var bf_doc_noInput = checkbox.closest('tr').querySelector("#bf_doc_no");
                if (bf_doc_noInput) {
                    var bf_doc_no = bf_doc_noInput.value;
                    delbox.push(bf_doc_no);
                }
            });
            console.log("Selected bf_doc_no values: " + delbox);
            console.log(delbox);
            $.ajax({
                url: "/admin_board_del",
                type: "POST",
                data: JSON.stringify({user_id: delbox}),
                contentType: "application/json",
                dataType: "json",
                success: function (response) {
                    alert("삭제가 완료되었습니다.");
                    event_search(1);
                },
            })
        }
        //  공지 / 자료 삭제
        function pbdBoard_del(){
            var prj_delbox = [];        //  삭제 버튼에 체크된 게시글
            var doc_delbox = [];        //  삭제 버튼에 체크된 게시글
            document.querySelectorAll("input[name=pbd_del_chkbox]:checked").forEach(function (checkbox) {
                //  체크된 게시글 id값들 리스트에 저장.
                var pbd_prj_noInput = checkbox.closest('tr').querySelector("#pbd_prj_no");
                var pbd_doc_noInput = checkbox.closest('tr').querySelector("#pbd_doc_no");
                if (pbd_prj_noInput && pbd_doc_noInput) {
                    var pbd_prj_no = pbd_prj_noInput.value;
                    var pbd_doc_no = pbd_doc_noInput.value;
                    prj_delbox.push(pbd_prj_no);
                    doc_delbox.push(pbd_doc_no);
                }
            });

            console.log("Selected pbd_prj_no values: " + prj_delbox);
            console.log(prj_delbox);
            console.log("Selected pbd_doc_no values: " + doc_delbox);
            console.log(doc_delbox);
            $.ajax({
                url: "/admin_pbd_del",
                type: "POST",
                data: JSON.stringify({firList: prj_delbox , secList:doc_delbox}),
                contentType: "application/json",
                dataType: "json",
                success: function (response) {
                    alert("삭제가 완료되었습니다.");
                    pr_info(1);
                },
            })
        }

        function callAction(action, mapping_name) {
            console.log("callAction");
            console.log(action, mapping_name);
            var checked = true;
            if (document.getElementById("idNewWinFlag") != null) {
                console.log("not null");
                checked = $("#idNewWinFlag").is(':checked');
            }
        }

        function locatPrj(cdoc_no, cproject_id, lloc){
            console.log("locat!");
            console.log(cdoc_no);
            window.open(
                "/" + lloc + "?doc_no=" + cdoc_no + "&project_id=" + cproject_id,
                "Child",
                "width=600, height=570, top=50, left=50"
            );
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
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <!------------------------------ //개발자 소스 입력 START ------------------------------->
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
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="/admin_projectmanager">관리자 설정</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">게시판 관리</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">관리자 설정</span>
				</div>
			</div>
			
            <div class="container-fluid">
                
				<div id="admin_page_list" class="bd-example m-0 border-0">
					<nav>
						<div class="nav nav-tabs mb-3" id="nav-tab" role="tablist">
							<button class="nav-link" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-1" type="button" role="tab" aria-controls="nav-1" aria-selected="false" tabindex="-1" onclick="location.href='/admin_projectmanager'">팀장 권한 설정</button>
							<button class="nav-link active" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-2" type="button" role="tab" aria-controls="nav-2" aria-selected="true" onclick="location.href='/admin_board'">게시판 관리</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-3" type="button" role="tab" aria-controls="nav-3" aria-selected="false" tabindex="-1" onclick="location.href='/admin_approval'">프로젝트 관리</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-4" type="button" role="tab" aria-controls="nav-4" aria-selected="false" tabindex="-1" onclick="location.href='/admin_add_class'">반 생성</button>
							<button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-5" type="button" role="tab" aria-controls="nav-5" aria-selected="false" tabindex="-1" onclick="location.href='/admin_class_list'">반 목록</button>
						</div>
					</nav>
				</div>
				
                <p>
				<div id="selbox" style="margin-top:5px;margin-bottom:5px">
					<table style="margin-bottom:5px">
                    	<tr>
                    		<td>
			                   <select id="cl_room_List" class="form-select" onchange="cl_room(1)">
		                            <c:forEach items="${CRList}" var="list">
		                                <option name="class_room_num" value="${list.class_id}">${list.class_area} ${list.class_room_num}</option>
		                            </c:forEach>
		                        </select>
							</td>
							<td>
								<%--onchange -> onclick 변경--%>
		                        <select id="pr_List" class="form-select" onclick="pr_info(1)">
		                            <c:forEach items="${PIList}" var="list">
		                                <option name="pr_num"
		                                        value="${list.project_id}">${list.project_name} <%--${list.class_room_num}--%>
		                                </option>
		                            </c:forEach>
		                        </select>
	                		</td>
	                		<td style="padding-left:10px">
		                		<input type="button" id="pbdBoard_del" class="btn btn-primary" value="삭제" onclick="pbdBoard_del()"/>
	                		</td>
	                	</tr>
                    </table>                    
				</div>
				
                <table class="table">
                    <thead class="table-light">
                    <tr>
                        <th>글 번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>분류</th>
                        <th>조회수</th>
                        <th>추천수</th>
                        <th>수정</th>
                        <th>삭제</th>
                    </tr>
                    </thead>
                    <tbody id="PBDList_body">
	                    <c:forEach items="${PBDList}" var="board">
	                        <tr>
	                            <td>${board.rn}</td>
	                            <input type="hidden" id="pbd_doc_no" value="${board.doc_no}"/>
	                            <input type="hidden" id="pbd_prj_no" value="${board.project_id}"/>
	
	                            <td><a href="javascript:locatPrj(${board.doc_no},${board.project_id},'prj_board_data_read')"> ${board.subject}</a></td>
	                            <td>${board.user_name}</td>
	                            <td><fmt:formatDate value="${board.create_date}" type="date" pattern="yyyy-MM-dd"/></td>
	                            <td>${board.bd_category_name}</td>
	                             <td>${board.bd_count}</td>
	                            <td>${board.good_count}</td>
	                            <td onclick="locatPrj(${board.doc_no},${board.project_id},'prj_board_data_edit')">수정</td>
	                            <td><input type="checkbox" name="pbd_del_chkbox" />
	                        </tr>
	                        <c:set var="num" value="${num-1}"></c:set>
	                    </c:forEach>
	                    <div id="d_p" class="pagination justify-content-center">
	                        <c:if test="${page2.startPage > page2.pageBlock}">
	                            <div onclick="pr_info(${page2.startPage-page2.pageBlock})">
	                                <p>[이전]</p>
	                            </div>
	                        </c:if>
	                        <c:forEach var="i" begin="${page2.startPage}" end="${page2.endPage}">
	                            <div class="page-item" onclick="pr_info(${i})">
	                                <div class="page-link">${i}</div>
	                            </div>
	
	                        </c:forEach>
	
	                        <c:if test="${page2.endPage > page2.pageBlock}">
	                            <div onclick="pr_info(${page2.startPage+page2.pageBlock})">
	                                <p>[다음]</p>
	                            </div>
	                        </c:if>
	                    </div>
                    </tbody>
                </table>
            
	            <div id="ev">
					<table style="margin-bottom:5px">
                    	<tr>
                    		<td><div class="btn btn-success">작성자</div></td>
                    		<td>
			                    <select id="bd_CTG" class="form-select">
			                        <option name="bd_ctg_li" value="공지">공지</option>
			                        <option name="bd_ctg_li" value="자유">자유</option>
			                        <option name="bd_ctg_li" value="이벤트">이벤트</option>
			                        <option name="bd_ctg_li" value="전체">전체</option>
			                    </select>
	                		</td>
	                		<td style="padding-left:10px">
				                <input type="text" id="search_text" class="form-control"/>
							</td>
							<td>
				                <input type="button" id="search_button" class="btn btn-info" value="검색" onclick="event_search(1)"/>
							</td>
							<td>
				                <input type="button" id="del_button" class="btn btn-primary" value="삭제" onclick="bdfree_del()"/>
	                		</td>
	                	</tr>
                    </table>

	                <table class="table">
	                    <thead class="table-light">
		                    <tr>
		                        <th>번호</th>
		                        <th>게시종류</th>
		                        <th>제목</th>
		                        <th>작성자</th>
		                        <th>작성일</th>
		                        <th>조회수</th>
		                        <th>수정</th>
		                        <th>삭제</th>
							</tr>
						</thead>
						<tbody id="BFList_body">
	                        <c:forEach items="${BFList}" var="BF">
	                            <tr>
	
	                                <input type="hidden" id="bf_doc_no" value="${BF.doc_no}"/>
	                                <td>${BF.rn}</td>
	                                <td>${BF.bd_category}</td>
	                                <td><a href="board_content?doc_no=${BF.doc_no}">${BF.subject}</a></td>
	                                <td>${BF.user_name}</td>
	                                <td>${BF.create_date}</td>
	                                <td>${BF.good_count}</td>
									<td><a href="board_update?doc_no=${BF.doc_no}">수정</a></td>
	                                <td><input type="checkbox" name="del_chkbox" />
	                            </tr>
	                        </c:forEach>
		                    <div id="e_p" class="pagination justify-content-center">
		                        <c:if test="${page.startPage > page.pageBlock}">
		                            <div onclick="event_search(${page.startPage-page.pageBlock})">
		                                <p>[이전]</p>
		                            </div>
		                        </c:if>
		                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
		                            <div class="page-item" onclick="event_search(${i})">
		                                <div class="page-link" style="cursor:pointer">${i}</div>
		                            </div>
		
		                        </c:forEach>
		
		                        <c:if test="${page.endPage > page.pageBlock}">
		                            <div onclick="event_search(${page.startPage+page.pageBlock})">
		                                <p>[다음]</p>
		                            </div>
		                        </c:if>
		                    </div>
	                    </tbody>
 					</table>
 					
				</div>
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

