<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
</style>

<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
  <symbol id="bootstrap" viewBox="0 0 118 94">
    <title>Bootstrap</title>
    <path fill-rule="evenodd" clip-rule="evenodd" d="M24.509 0c-6.733 0-11.715 5.893-11.492 12.284.214 6.14-.064 14.092-2.066 20.577C8.943 39.365 5.547 43.485 0 44.014v5.972c5.547.529 8.943 4.649 10.951 11.153 2.002 6.485 2.28 14.437 2.066 20.577C12.794 88.106 17.776 94 24.51 94H93.5c6.733 0 11.714-5.893 11.491-12.284-.214-6.14.064-14.092 2.066-20.577 2.009-6.504 5.396-10.624 10.943-11.153v-5.972c-5.547-.529-8.934-4.649-10.943-11.153-2.002-6.484-2.28-14.437-2.066-20.577C105.214 5.894 100.233 0 93.5 0H24.508zM80 57.863C80 66.663 73.436 72 62.543 72H44a2 2 0 01-2-2V24a2 2 0 012-2h18.437c9.083 0 15.044 4.92 15.044 12.474 0 5.302-4.01 10.049-9.119 10.88v.277C75.317 46.394 80 51.21 80 57.863zM60.521 28.34H49.948v14.934h8.905c6.884 0 10.68-2.772 10.68-7.727 0-4.643-3.264-7.207-9.012-7.207zM49.948 49.2v16.458H60.91c7.167 0 10.964-2.876 10.964-8.281 0-5.406-3.903-8.178-11.425-8.178H49.948z"></path>
  </symbol>
  <symbol id="home" viewBox="0 0 16 16">
    <path d="M8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4.5a.5.5 0 0 0 .5-.5v-4h2v4a.5.5 0 0 0 .5.5H14a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146zM2.5 14V7.707l5.5-5.5 5.5 5.5V14H10v-4a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5v4H2.5z"></path>
  </symbol>
  <symbol id="speedometer2" viewBox="0 0 16 16">
    <path d="M8 4a.5.5 0 0 1 .5.5V6a.5.5 0 0 1-1 0V4.5A.5.5 0 0 1 8 4zM3.732 5.732a.5.5 0 0 1 .707 0l.915.914a.5.5 0 1 1-.708.708l-.914-.915a.5.5 0 0 1 0-.707zM2 10a.5.5 0 0 1 .5-.5h1.586a.5.5 0 0 1 0 1H2.5A.5.5 0 0 1 2 10zm9.5 0a.5.5 0 0 1 .5-.5h1.5a.5.5 0 0 1 0 1H12a.5.5 0 0 1-.5-.5zm.754-4.246a.389.389 0 0 0-.527-.02L7.547 9.31a.91.91 0 1 0 1.302 1.258l3.434-4.297a.389.389 0 0 0-.029-.518z"></path>
    <path fill-rule="evenodd" d="M0 10a8 8 0 1 1 15.547 2.661c-.442 1.253-1.845 1.602-2.932 1.25C11.309 13.488 9.475 13 8 13c-1.474 0-3.31.488-4.615.911-1.087.352-2.49.003-2.932-1.25A7.988 7.988 0 0 1 0 10zm8-7a7 7 0 0 0-6.603 9.329c.203.575.923.876 1.68.63C4.397 12.533 6.358 12 8 12s3.604.532 4.923.96c.757.245 1.477-.056 1.68-.631A7 7 0 0 0 8 3z"></path>
  </symbol>
  <symbol id="table" viewBox="0 0 16 16">
    <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2zm15 2h-4v3h4V4zm0 4h-4v3h4V8zm0 4h-4v3h3a1 1 0 0 0 1-1v-2zm-5 3v-3H6v3h4zm-5 0v-3H1v2a1 1 0 0 0 1 1h3zm-4-4h4V8H1v3zm0-4h4V4H1v3zm5-3v3h4V4H6zm4 4H6v3h4V8z"></path>
  </symbol>
  <symbol id="people-circle" viewBox="0 0 16 16">
    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
  </symbol>
  <symbol id="grid" viewBox="0 0 16 16">
    <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zM2.5 2a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zm6.5.5A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zM1 10.5A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3zm6.5.5A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3zm1.5-.5a.5.5 0 0 0-.5.5v3a.5.5 0 0 0 .5.5h3a.5.5 0 0 0 .5-.5v-3a.5.5 0 0 0-.5-.5h-3z"></path>
  </symbol>
</svg>
<div class="flex-shrink-0 p-3">
    <ul class="list-unstyled ps-0">
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#mypost-collapse" aria-expanded="true">
		내 글 모음
        </button>
        <div class="collapse show" id="mypost-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
            <li><a href="mypost_board_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded">내가 쓴 게시글</a></li>
            <li><a href="mypost_comment_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded">내가 쓴 댓글</a></li>
            <li><a href="mypost_good_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded">내가 추천한 게시글</a></li>
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#todolist-collapse" aria-expanded="false">
		<span onclick="goto('todo_list')" style="cursor:pointer">오늘 할 일</span>
        </button>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#project-collapse" aria-expanded="true">
		프로젝트
        </button>
        <div class="collapse show" id="project-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
            <li><a href="prj_mgr_req_create" class="link-body-emphasis d-inline-flex text-decoration-none rounded">프로젝트 생성</a></li>
            <li><a href="prj_mgr_step_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded">프로젝트 단계 프로파일</a></li>
            <li><a href="javascript:gotoCheckProjectID('prj_home')" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><svg class="bi pe-none me-2" width="16" height="16"><use xlink:href="#home"></use></svg>프로젝트 Home</a></li>
            <li><a href="dashboard" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><svg class="bi pe-none me-2" width="16" height="16"><use xlink:href="#grid"></use></svg>작업 보드</a></li>
            <li>
            	<a href="task_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><img src="/common/images/icon_board.png" width="16" height="14" style="margin-top:2px;margin-right:8px">작업 목록</a>
            	<span style="margin-left:10px;cursor:pointer" onclick="goto('garbage_list')">
            		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
					  <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"></path>
					  <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"></path>
					</svg>
            	</span>
            </li>
            <li><a href="task_timeline" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><svg class="bi pe-none me-2" width="16" height="16"><use xlink:href="#speedometer2"></use></svg>작업 타임 라인</a></li>
            <li><a href="prj_calendar" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><svg class="bi pe-none me-2" width="16" height="16"><use xlink:href="#table"></use></svg>프로젝트 캘린더</a></li>
            <li><a href="prj_board_data_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><svg class="bi pe-none me-2" width="16" height="16"><use xlink:href="#grid"></use></svg>공지/자료</a></li>
            <li><a href="prj_meeting_calendar" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><svg class="bi pe-none me-2" width="16" height="16"><use xlink:href="#people-circle"></use></svg>회의록</a></li>
            <li><a href="prj_board_report_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded"><svg class="bi pe-none me-2" width="16" height="16"><use xlink:href="#grid"></use></svg>업무보고</a></li>
          </ul>
        </div>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#complete-collapse" aria-expanded="false">
		<span onclick="goto('prj_complete_list')" style="cursor:pointer">완료 프로젝트 목록</span>
        </button>
      </li>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#board-collapse" aria-expanded="true">
		전체 게시판
        </button>
        <div class="collapse show" id="board-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
            <li><a href="board_notify" class="link-body-emphasis d-inline-flex text-decoration-none rounded">전체 공지사항</a></li>
            <li><a href="board_event" class="link-body-emphasis d-inline-flex text-decoration-none rounded">이벤트</a></li>
            <li><a href="board_qna" class="link-body-emphasis d-inline-flex text-decoration-none rounded">Q&A 게시판</a></li>
            <li><a href="board_free" class="link-body-emphasis d-inline-flex text-decoration-none rounded">자유 게시판</a></li>
          </ul>
        </div>
      </li>
      <hr>
      <li class="mb-1">
        <button class="btn btn-toggle d-inline-flex align-items-center rounded border-0 collapsed" data-bs-toggle="collapse" data-bs-target="#admin-collapse" aria-expanded="true">
		관리자 설정
        </button>
        <div class="collapse show" id="admin-collapse">
          <ul class="btn-toggle-nav list-unstyled fw-normal pb-1">
            <li><a href="admin_projectmanager" class="link-body-emphasis d-inline-flex text-decoration-none rounded">팀장 권한 설정</a></li>
            <li><a href="admin_board" class="link-body-emphasis d-inline-flex text-decoration-none rounded">게시판 관리</a></li>
            <li><a href="admin_approval" class="link-body-emphasis d-inline-flex text-decoration-none rounded">프로젝트 관리</a></li>
            <li><a href="admin_add_class" class="link-body-emphasis d-inline-flex text-decoration-none rounded">반 생성</a></li>
            <li><a href="admin_class_list" class="link-body-emphasis d-inline-flex text-decoration-none rounded">반 목록</a></li>
          </ul>
        </div>
      </li>
    </ul>
  </div>