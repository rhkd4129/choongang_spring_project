<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <style text="text/css">
        #test {
            margin-top: 5%;
            margin-bottom: 5%;
        }
        #event_bar{
            display: flex;
        }
        #search_bar{
            display: flex;
            width: 54%;
        }


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

            console.log(cl_room_val);
            // var curpage = 1;
            var sendurl = '/admin_board/?class_id=' + cl_room_val + "&project_id=" + project_id;			// + currentpage;
            console.log(sendurl);
        }

        // function eventList(currentPage) {
        //     console.log(currentPage);
        //     var sendurl = "/admin_board_ajax_paging/?currentPage=" + currentPage;
        //     console.log("sendURL: " + sendurl);
        //     $.ajax({
        //         url: sendurl,
        //         dataType: 'json',
        //         success: function (jsonData) {
        //             console.log(jsonData);
        //             var BFList_body = $('#BFList_body');
        //             BFList_body.empty();
        //             $.each(jsonData.firList, function (index, BFL) {
        //                 var tr = $('<tr>');
        //
        //                 tr.append('<td>' + BFL.doc_no + '</td>');
        //                 tr.append('<td>' + BFL.subject + '</td>');
        //                 tr.append('<td>' + BFL.user_name + '</td>');
        //                 tr.append('<td>' + BFL.create_date + '</td>');
        //                 tr.append('<td>' + BFL.good_count + '</td>');
        //                 tr.append('<td><a href="#">수정</a></td>');
        //                 tr.append('<td><input type="checkbox" name="xxx" value="yyy" checked>');
        //                 BFList_body.append(tr);
        //             });
        //
        //             var page = jsonData.obj;
        //
        //             var paginationDiv = $('#e_p');
        //             paginationDiv.empty();
        //             var jspPagination = '<div id="e_p" class="pagination">';
        //             if (page.startPage > page.pageBlock) {
        //                 jpsPagination += '<div onclick="eventList(' + (page.startPage - page.pageBlock) + ')"><p>이전</p></div>';
        //             }
        //             for (var i = page.startPage; i <= page.endPage; i++) {
        //                 var currentPageStyle = i === page.currentPage ? '-webkit-text-stroke: thick;' : ''; // 현재 페이지와 i가 일치할 때 스타일을 적용
        //
        //                 jspPagination += '<div class="page-item" style="' + currentPageStyle + '" onClick="eventList(' + i + ')"><div class="page-link">' + i + '</div></div>';
        //             }
        //             if (page.endPage < page.totalPage) {
        //                 jpsPagination += '<div onclick="eventList(' + (page.startPage + page.pageBlock) + ')"><p>이전</p></div>';
        //             }
        //             jspPagination += '</div>';
        //             paginationDiv.html(jspPagination);
        //         }
        //     })
        //     // var BFList = $('#BFList_body');
        //     // BFList.empty();
        // }

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
                        var tr = $('<tr>');

                        tr.append('<td>' + BFL.rn + '</td>');
                        tr.append('<td>' + BFL.subject + '</td>');
                        tr.append('<td>' + BFL.user_name + '</td>');
                        tr.append('<td>' + BFL.create_date + '</td>');
                        tr.append('<td>' + BFL.good_count + '</td>');
                        tr.append('<td><a href="#">수정</a></td>');
                        tr.append('<td><input type="checkbox" name="xxx" value="yyy" checked>');
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
                    <div class="btn btn-secondary" onclick="location.href='/admin_approval'">프로젝트 관리</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_add_class'">반 생성</div>
                    <div class="btn btn-secondary" onclick="location.href='/admin_class_list'">반 목록</div>
                </div>
                <p></p>
                <table class="table">
                    <div id="selbox" style="display: flex">

                        <select id="cl_room_List" class="form-select" style="width: 30%" onchange="cl_room(1)">
                            <c:forEach items="${CRList}" var="list">
                                <option name="class_room_num" value="${list.class_id}">${list.class_area}
                                        ${list.class_room_num}</option>
                            </c:forEach>
                        </select>
                        <select id="pr_List" class="form-select" style="width: 30% " onchange="pr_info()">
                            <c:forEach items="${PIList}" var="list">
                                <option name="pr_num"
                                        value="${list.project_id}">${list.project_name} <%--${list.class_room_num}--%>
                                </option>
                            </c:forEach>
                        </select>


                    </div>
                    <thead>
                    <tr>
                        <th>글 번호</th>
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
                        <td><a href="#">수정</a></td>
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
                        <td><a href="#">수정</a></td>
                        <td><input type="checkbox" name="xxx" value="yyy" checked>
                        </td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>2</td>
                        <td>3</td>
                        <td>4</td>
                        <td><a href="#">수정</a></td>
                        <td><input type="checkbox" name="xxx" value="yyy" checked>
                        </td>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>2</td>
                        <td>3</td>
                        <td>4</td>
                        <td><a href="#">수정</a></td>
                        <td><input type="checkbox" name="xxx" value="yyy" checked>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div id="ev">
                <div id="event_bar">
                    <div class="btn btn-success">이벤트</div>

                    <select id="bd_CTG" class="form-select" style="width: 15%">
                        <option name="bd_ctg_li" value="공지">공지</option>
                        <option name="bd_ctg_li" value="자유">자유</option>
                        <option name="bd_ctg_li" value="이벤트">이벤트</option>
                        <option name="bd_ctg_li" value="전체">전체</option>
                    </select>

                    <div id="search_bar">
                    <input type="text" id="search_text" class="form-control"/>
                    <input type="button" id="search_button" class="btn btn-info" value="검색" onclick="event_search(1)"/>
                    </div>
                </div>

                <table class="table">
                    <thead>


                    <tr>
                        <th>번호</th>
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
                                <td>${BF.doc_no}</td>
                                <td>${BF.subject}</td>
                                <td>${BF.user_name}</td>
                                <td>${BF.create_date}</td>
                                <td>${BF.good_count}</td>
                                <td><a href="#">수정</a></td>
                                <td><input type="checkbox" name="xxx" value="yyy" checked>
                            </tr>
                        </c:forEach>
                    <div id="e_p" class="pagination">
                        <c:if test="${page.startPage > page.pageBlock}">
                            <%--                            <p onclick="page('/admin_projectmanagerRest/${page.startPage-page.pageBlock}/')">이전</p>--%>
                            <div onclick="event_search(${page.startPage-page.pageBlock})">
                                <p>[이전]</p>
                            </div>
                        </c:if>
                        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
                            <%--                            <a href="/admin_projectmanager/?cl_id=${cl_id}&currentPage=${i}">[${i}]</a>--%>
                            <div class="page-item" onclick="event_search(${i})">
                                <div class="page-link">${i}</div>
                            </div>

                        </c:forEach>

                        <c:if test="${page.endPage > page.pageBlock}">
                            <div onclick="event_search(${page.startPage+page.pageBlock})">
                                <p>[다음]</p>
                            </div>
                            <%--                            <a href="/admin_projectmanager/${cl_id}currentPage=${page.startPage+page.pageBlock}">[다음]</a>--%>
                        </c:if>
                    </div>
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

