<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#menubar {
	max-width: 280px;
}
.project-title {
	padding-left: 30px;
	color: #fdb933;
	font-weight: bold;
}
.menu-title{
	padding-left: 30px;
	color: #000000;
	font-weight: bold;
	
	border:0px;
	/* background-color: #e2e3e5; */
	border-radius: 5px;
	padding: 5px 5px 5px 10px;
	margin: 5px 5px 5px 20px;
	width: 222px;
	list-style-type: none; /* 불릿 제거 */
	list-style-position: inside;  /* 들여쓰기 */
}
	.menu-title:hover {
		background-color: #fff1eb;
	}
	.menu-title a:link {
		text-decoration: none;
		color: #212529;
	}
	.menu-title a:hover {
		color: #212529;
	}
	.menu-title a:active {
		color: #212529;
	}
	.menu-title a:visited {
		color: #212529;
	} 

.menu-box {
	border:0px;
	background-color: #ffffff;
	border-radius: 5px;
	padding: 5px 5px 5px 10px;
	margin: 5px;
	width: 200px;
	list-style-type: none; /* 불릿 제거 */
	/* list-style-type: square  네모모양 */
	list-style-position: inside;  /* 들여쓰기 */
}
	.menu-box:hover {
		background-color: #fff1eb;
	}
	.menu-box a:link {
		text-decoration: none;
		color: #212529;
	}
	.menu-box a:hover {
		color: #212529;
	}
	.menu-box a:active {
		color: #212529;
	}
	.menu-box a:visited {
		color: #212529;
	}
</style>
			<div class="offcanvas-md offcanvas-end bg-body-tertiary" style="max-width:300px;" tabindex="-1" id="sidebarMenu" aria-labelledby="sidebarMenuLabel">
 				<!-- <div id="menu-my-part">
 					<div class="photo"></div>
 					<p class="name">홍길동</p>
 					<div class="count-info">
 						<ul>
 							<li>전체글: 45</li>
 							<li>쪽지: 5</li>
 							<li>작업진행률 : 50% (5/10)</li>
 						</ul>	
 						</ul>
 					</div> 								
 				</div> -->
 				
				<p style="height:10px"></p>
 				<p class="menu-title">내 글 모음</p>
				<ul>
					<li class="menu-box"><a href="/project/manager/p1.html">내가 쓴 게시글</a></li>
					<li class="menu-box"><a href="/project/manager/p1.html">내가 쓴 댓글</a></li>
					<li class="menu-box"><a href="/project/manager/p1.html">내가 추천한 게시글</a></li>
				</ul>
 				<p class="menu-title">To-Do List</p>
  				<!-- <p class="project-title">
					<select class="form-select" aria-label=".form-select-lg example" onChange="goto('notice_list.html')">
					  <option selected>프로젝트 이동</option>
					  <option value="1">PMS</option>
					</select>
 				</p> -->
  				<p class="menu-title">프로젝트</p>
 				<ul>
 					<li class="menu-box"><a href="/project/manager/p1.html">프로젝트 생성</a></li>
 					<li class="menu-box"><a href="/project/manager/p2.html">프로젝트 단계 프로파일</a></li>
 					<li class="menu-box"><a href="dashboard">프로젝트 홈</a></li>
 					<li class="menu-box">프로젝트 캘린더</li>
 					<li class="menu-box"><a href="/project/board/project_board_data_list.html">공지/자료</a></li>
 					<li class="menu-box">회의록</li>
 					<li class="menu-box"><a href="/project/board/project_board_report_list.html">업무보고</a></li>
 				</ul>
 				
 				<p class="menu-title">완료 프로젝트 목록</p>

 				<p class="menu-title">Board</p>
 				<ul>
 					<li class="menu-box"><a href="">전체 공지사항</a></li>
 					<li class="menu-box"><a href="">이벤트</a></li>
 					<li class="menu-box"><a href="">Q&A 게시판</a></li>
 					<li class="menu-box"><a href="">자유 게시판</a></li>
 				</ul>
 				
 				<p class="menu-title">관리자 페이지</p>
 				
 			</div>