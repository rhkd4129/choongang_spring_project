<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 통합검색 -->
<script type="text/javascript" src="/searchall/js/searchall.js"></script>

<!-- 알림 -->
<link rel="stylesheet" type="text/css" href="/alarm/css/alarm.css">
<!-- <script type="text/javascript" src="/alarm/js/alarm.js"></script> 진희것만 JS파일이 적용안됨-->

<!-- 채팅 -->
<link rel="stylesheet" type="text/css" href="/pmschat/css/chat.css">
<script type="text/javascript" src="/pmschat/js/chat.js"></script>

<script type="text/javascript">
//진희
// stomp 사용   
let stompClient;

// 소켓 연결   
function onSocket() {
    let stompClient;

    function disconnectWebSocket() {
        if (stompClient && stompClient.connected) {
            stompClient.disconnect(function () {
                console.log("Disconnected WebSocket.");
                setTimeout(connectWebSocket, 5000); // 5초 후 다시 연결 시도
            });
        }
    }

    function connectWebSocket() {
        let socket = new SockJS('/websocket');
        let loginUser = '${userInfo.user_id}';
        let notCon = $('#cntNotify');
        // console.log("1");
        stompClient = Stomp.over(socket);
        // console.log("2");

        const obj = {
            project_id: '${userInfo.project_id}',
            user_id: '${userInfo.user_id}',
            user_auth: '${userInfo.user_auth}'
        };

        stompClient.connect({}, function (frame) {

          var cntAlarm = 0;
           
           
            console.log('Connected: ' + frame);

            // console.log("3");
            console.log(obj);

          console.log("cntAlarmSTART");
         
            // 프로젝트 생성 승인 알림 (팀장)
            stompClient.subscribe("/noti/prjapprove", function(data) {
               console.log("프로젝트 생성 승인 알림");
               
               var approvedata = JSON.parse(data.body);
               console.log(approvedata);
               
               let getUI = approvedata.obj;
               let user_id = getUI.user_id;
               
               approvedata = approvedata.firList;
               
               let prjApproveNotify = $('#prjApproveNotify');
               
                
                let str = '';
               
                if (user_id == loginUser) {
                   prjApproveNotify.empty();
                   
                   for (var i = 0; i < approvedata.length; i++) {
                     const prjName = approvedata[i].project_name;                  // 프로젝트명
                     
                     str += '<p onclick="location.href=' + "'/prj_mgr_step_list'" + '">' + prjName + ' 프로젝트 생성이 승인되었습니다.</p>';
                     cntAlarm += 1;
                     console.log("cntAlarm");
                     console.log(cntAlarm);
                  }
                }
                
               prjApproveNotify.append(str);
              //
                notCon.empty();
                notCon.append(cntAlarm);
                //
            });
            
            // 회의일정 알림
            stompClient.subscribe("/noti/meeting", function (data) {
            var rtndata = JSON.parse(data.body);
            console.log(rtndata);
            
            let getUI = rtndata.obj;
               let user_id = getUI.user_id;
               
               rtndata = rtndata.firList;
            
                const date = new Date();
                const year = date.getFullYear();
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + (date.getDate())).slice(-2);
                let now = year + "-" + month + "-" + day;

                console.log("now date: " + now);
                
                let meetingNotify = $('#meetingNotify');
                
                let meetStr = '';
                
                if (user_id == loginUser) {
                   meetingNotify.empty();
                   
                   for (var i = 0; i < rtndata.length; i++) {
                       const meetingDate = rtndata[i].meeting_date;
                       // console.log(meetingDate);
   
                       if (meetingDate == now) {
                          meetStr += '<p onclick="location.href=' + "'/prj_meeting_calendar?project_id=" + rtndata[i].project_id + "'" + '"' + '>오늘(' + rtndata[i].meeting_date + ') 예정된 ' + rtndata[i].meeting_title + ' 회의가 있습니다.</p>';
                        cntAlarm += 1;
                        console.log("cntAlarm");
                        console.log(cntAlarm);
                       }
                   }
                }
                
                meetingNotify.append(meetStr);
              //
                notCon.empty();
                notCon.append(cntAlarm);
                //
            });
            
            // 게시판 답글
            stompClient.subscribe("/noti/boardRep", function(data) {
               var repdata = JSON.parse(data.body);
               console.log(repdata);
               
               let getUI = repdata.obj;
               let user_id = getUI.user_id;
               
               repdata = repdata.firList;
               
               let repNotify = $('#repNotify');
               
               let repStr = '';
               
               if (user_id == loginUser) {
                  repNotify.empty();
                  
                  for (var i = 0; i < repdata.length; i++) {
                       const doc_no = repdata[i].doc_no;            // 글 번호
                       const subject = repdata[i].subject;            // 글 제목
                       const board_name = repdata[i].app_name;         // 게시판 이름
                       const app_id = repdata[i].app_id;
                       
                       for (var j = 0; j < repdata.length; j++) {
                          const parent_doc_no = repdata[j].parent_doc_no;
                          const parent_app_id = repdata[j].app_id;
                          
                          const rep_subject = repdata[j].subject;      // 답글 제목
                          const rep_doc_no = repdata[j].doc_no;      // 답글 번호
                          const doc_group = repdata[j].doc_group;      // doc_group
                          
                          // 질문 게시판
                          if (app_id == 2) {
//                                글번호      답글 부모
                             if (doc_no == parent_doc_no && parent_app_id == app_id) {
                                repStr += '<p onclick="location.href=' + "'/board_qna?doc_group=" + doc_group + "&doc_group_list=y'" + '"' + '>[' + board_name + ' 게시판] ' + subject + '에 [답글] ' + rep_subject + '이 등록되었습니다.</p>';
                              cntAlarm += 1;
                              console.log("cntAlarm");
                              console.log(cntAlarm);
                             }
                          }
                          
                          // 프로젝트 공지/자료 게시판
                          if (app_id == 3) {
//                                글번호      답글 부모
                             if (doc_no == parent_doc_no && parent_app_id == app_id) {
                                repStr += '<p onclick="location.href=' + "'/prj_board_data_list?doc_group=" + doc_group + "&doc_group_list=y'" + '"' + '>[' + board_name + ' 게시판] ' + subject + '에 [답글] ' + rep_subject + '이 등록되었습니다.</p>';
                              cntAlarm += 1;
                              console.log("cntAlarm");
                              console.log(cntAlarm);   
                             }
                          }
                       }
                   }
               }
               
               repNotify.append(repStr);
              //
                notCon.empty();
                notCon.append(cntAlarm);
                //
            });
            
            // 게시판 댓글
            stompClient.subscribe("/noti/boardComt", function(data) {
               var comtdata = JSON.parse(data.body);
               console.log(comtdata);
               
               let getUI = comtdata.obj;
               let user_id = getUI.user_id;
               
               comtdata = comtdata.firList;
               
               let comtNotify = $('#comtNotify');
               
               
               let comtStr = '';

            let doc = 0;
            let app = 0;
            
            if (user_id == loginUser) {
               comtNotify.empty();
               
               for (var i = 0; i < comtdata.length; i++) {
                     const app_id          = comtdata[i].app_id;            //   테이블 분류
                     const board_name       = comtdata[i].app_name;            //    게시판 이름
                     const bd_category       = comtdata[i].bd_category;         //    카테고리
                     const doc_no          = comtdata[i].doc_no;            //    글 번호
                     const subject          = comtdata[i].subject;            //    글 제목
                     const comment_context    = comtdata[i].comment_context;      //    댓글 내용
                     const comment_count      = comtdata[i].comment_count;      //    댓글 갯수
                     const project_id       = comtdata[i].project_id;         //   프로젝트 ID
                     
                     // 공용게시판
                     if (app_id == 1) {
                        if (bd_category == '자유') {      // 자유 게시판
                        let loc ='free_content';
                           if (doc != doc_no || app != app_id){
                              comtStr += '<p onclick="locatFree('+ doc_no + ", 'board_free_read'" + ')">[' + bd_category + ' 게시판] ' + subject + '에 새로운 댓글이 ' + comment_count + '건 등록되었습니다.</p>';
                              console.log('comment_count');
                              console.log(comment_count);
                              doc = doc_no;
                              app = app_id;
                                cntAlarm += 1;
                              console.log("cntAlarm");
                                console.log(cntAlarm);
                           }
                        }
                        if (bd_category == '이벤트') {   // 이벤트 게시판
                           let loc = 'event_content';
                           if (doc != doc_no || app != app_id){
                              comtStr += '<p onclick="locatFree('+ doc_no + ", 'board_event_read'" + ')">[' + bd_category + ' 게시판] ' + subject + '에 새로운 댓글이 ' + comment_count + '건 등록되었습니다.</p>';
                              console.log('comment_count');
                              console.log(comment_count);
                              doc = doc_no;
                              app = app_id;
                                cntAlarm += 1;
                              console.log("cntAlarm");
                                console.log(cntAlarm);
                           }
                        }
                     }
                     
                     // 프로젝트 공지/자료
                     if (app_id == 3) {
                        if (doc != doc_no || app != app_id){
                           comtStr += '<p onclick="locatPrj('+ doc_no + ',' + project_id + ", 'prj_board_data_read'" + ')">[' + board_name + ' 게시판] ' + subject + "에 새로운 댓글이 " + comment_count + "건 등록되었습니다.</p>";
                           console.log('comment_count');
                           console.log(comment_count);
                           doc = doc_no;
                           app = app_id;
                             cntAlarm += 1;
                           console.log("cntAlarm");
                             console.log(cntAlarm);
                        }
                     }
                     
                     // 프로젝트 업무보고
                     if (app_id == 4) {
                        if (doc != doc_no || app != app_id){
                           comtStr += '<p onclick="locatPrj('+ doc_no + ',' + project_id + ", 'prj_board_report_read'" + ')">[' + board_name + ' 게시판] ' + subject + "에 새로운 댓글이 " + comment_count + "건 등록되었습니다.</p>";
                           console.log('comment_count');
                           console.log(comment_count);
                           doc = doc_no;
                           app = app_id;
                             cntAlarm += 1;
                           console.log("cntAlarm");
                             console.log(cntAlarm);
                        }
                     }
                  };
            }
            
               comtNotify.append(comtStr);
              //
                notCon.empty();
                notCon.append(cntAlarm);
                //
            });
            
            // 프로젝트 생성 신청 - admin 계정만 해당
           stompClient.subscribe("/noti/newprj", function(data) {
              var newprjdata = JSON.parse(data.body);
              console.log(newprjdata);
              
              let getUI = newprjdata.obj;
               let user_id = getUI.user_id;
               
               newprjdata = newprjdata.firList;
              
              let adminNotify = $('#adminNotify');
              
              let newprjStr = '';
              const newprj = newprjdata.length;
            
              if (user_id == loginUser) {
                 adminNotify.empty();
                 
                 if (newprj != 0) {
                     newprjStr = '<p onclick="location.href=' + "'/admin_approval'" + '">신규 프로젝트 생성 신청이 ' + newprj + '건 있습니다.</p>';
                     cntAlarm += 1;
                     console.log("cntAlarm");
                     console.log(cntAlarm);
                  }
              }
              
              adminNotify.append(newprjStr);
              
              //
                notCon.empty();
                notCon.append(cntAlarm);
                //
           });

            stompClient.send('/queue/approve', {}, JSON.stringify(obj));   // 프로젝트 생성 승인
            stompClient.send('/queue/meet', {}, JSON.stringify(obj));      // 회의일정
            stompClient.send('/queue/rep', {}, JSON.stringify(obj));      // 답글
            stompClient.send('/queue/comt', {}, JSON.stringify(obj));      // 댓글
            stompClient.send('/queue/prj', {}, JSON.stringify(obj));      // 프로젝트 생성 신청 (admin)
        });
    }

    // 초기 연결 수행
    connectWebSocket();

    // 5초마다 웹 소켓 연결을 끊고 다시 연결
    setInterval(function () {
        disconnectWebSocket();
    }, 5000);
}

// 알림버튼 클릭 시 작동
function notifyClick() {
   
   var con = document.getElementById("notify");
/*       let prjApproveNotify = $('#prjApproveNotify');
   let meetingNotify = $('#meetingNotify');
   let repNotify = $('#repNotify');
   let comtNotify = $('#comtNotify');
   let adminNotify = $('#adminNotify'); */
   
   if (con.style.display == 'none') {
      con.style.display = 'block';
   } else {
      con.style.display = 'none';
   }
   
/*       if (prjApproveNotify.text() == '') { 
      prjApproveNotify.css('display', 'none');
   }
   
   if (meetingNotify.text() == '') { 
       meetingNotify.css('display', 'none');
   }
   
   if (repNotify.text() == '') { 
      repNotify.css('display', 'none');
   }
   
   if (comtNotify.text() == '') { 
      comtNotify.css('display', 'none');
   }
   if (adminNotify.text() == '') { 
      adminNotify.css('display', 'none');
   } */
};

// 페이지 이동
function locatFree(cdoc_no, lloc){
   console.log("locat!");
   console.log(cdoc_no);
   
   var _width = '800';
   var _height = '700';
   var _left = Math.ceil(( window.screen.width - _width )/2);
   var _top = Math.ceil(( window.screen.height - _height )/2);
   
   window.open(
      "/" + lloc + "?doc_no=" + cdoc_no,
      "Child",
      "width=" + _width + ", height=" + _height + ", top=" + _top + ", left=" + _left
   );
}

function locatPrj(cdoc_no, cproject_id, lloc){
   console.log("locat!");
   console.log(cdoc_no);
   
   var _width = '800';
   var _height = '700';
   var _left = Math.ceil(( window.screen.width - _width )/2);
   var _top = Math.ceil(( window.screen.height - _height )/2);
   
   window.open(
      "/" + lloc + "?doc_no=" + cdoc_no + "&project_id=" + cproject_id,
      "Child",
      "width=" + _width + ", height=" + _height + ", top=" + _top + ", left=" + _left
   );
}

function notify_close(){
    let con = document.getElementById("notify");
    con.style.display = 'none';
}


$(function(){
    onSocket();
});
</script>

<nav class="navbar navbar-expand-md navbar-dark fixed-top pms-bg-dark">
	<div class="container-fluid">		
		<button class="btn btn-secondary" onclick="goto('/main')">
			<!-- <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-house-door-fill" viewBox="0 0 16 16">
				<path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5Z"/>
			</svg> -->
			<svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-rocket-takeoff" viewBox="0 0 16 16">
			<path d="M9.752 6.193c.599.6 1.73.437 2.528-.362.798-.799.96-1.932.362-2.531-.599-.6-1.73-.438-2.528.361-.798.8-.96 1.933-.362 2.532"/>
			<path d="M15.811 3.312c-.363 1.534-1.334 3.626-3.64 6.218l-.24 2.408a2.56 2.56 0 0 1-.732 1.526L8.817 15.85a.51.51 0 0 1-.867-.434l.27-1.899c.04-.28-.013-.593-.131-.956a9.42 9.42 0 0 0-.249-.657l-.082-.202c-.815-.197-1.578-.662-2.191-1.277-.614-.615-1.079-1.379-1.275-2.195l-.203-.083a9.556 9.556 0 0 0-.655-.248c-.363-.119-.675-.172-.955-.132l-1.896.27A.51.51 0 0 1 .15 7.17l2.382-2.386c.41-.41.947-.67 1.524-.734h.006l2.4-.238C9.005 1.55 11.087.582 12.623.208c.89-.217 1.59-.232 2.08-.188.244.023.435.06.57.093.067.017.12.033.16.045.184.06.279.13.351.295l.029.073a3.475 3.475 0 0 1 .157.721c.055.485.051 1.178-.159 2.065Zm-4.828 7.475.04-.04-.107 1.081a1.536 1.536 0 0 1-.44.913l-1.298 1.3.054-.38c.072-.506-.034-.993-.172-1.418a8.548 8.548 0 0 0-.164-.45c.738-.065 1.462-.38 2.087-1.006ZM5.205 5c-.625.626-.94 1.351-1.004 2.09a8.497 8.497 0 0 0-.45-.164c-.424-.138-.91-.244-1.416-.172l-.38.054 1.3-1.3c.245-.246.566-.401.91-.44l1.08-.107-.04.039Zm9.406-3.961c-.38-.034-.967-.027-1.746.163-1.558.38-3.917 1.496-6.937 4.521-.62.62-.799 1.34-.687 2.051.107.676.483 1.362 1.048 1.928.564.565 1.25.941 1.924 1.049.71.112 1.429-.067 2.048-.688 3.079-3.083 4.192-5.444 4.556-6.987.183-.771.18-1.345.138-1.713a2.835 2.835 0 0 0-.045-.283 3.078 3.078 0 0 0-.3-.041Z"/>
			<path d="M7.009 12.139a7.632 7.632 0 0 1-1.804-1.352A7.568 7.568 0 0 1 3.794 8.86c-1.102.992-1.965 5.054-1.839 5.18.125.126 3.936-.896 5.054-1.902Z"/>
			</svg>
			<a class="navbar-brand" style="margin-left:10px" href="/main"><b>PMS</b></a>
		</button>		
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="navbar-nav me-auto mb-2 mb-md-0">
	        </ul>
			<img class="uploadFile" style=" width: 32px; height: 32px; border-radius: 50%;" alt="UpLoad File" src="${pageContext.request.contextPath}/${userInfo.attach_path }/${userInfo.attach_name}">
			<ul class="nav nav-pills">
				<li class="nav-item">
					<a class="nav-link px-2 link-light" aria-current="page" href="#">${userInfo.user_name }</a>
				</li>
			</ul>
			<!-- <div class="dropdown text-end" style="margin-right:20px">
				<a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
					<img class="uploadFile" style=" width: 32px; height: 32px; border-radius: 50%;" alt="UpLoad File" src="${pageContext.request.contextPath}/${userInfo.attach_path }/${userInfo.attach_name}">
				</a>
				<ul class="dropdown-menu text-small" style="">
					<li><a class="dropdown-item" href="mypage_main">내 정보 설정</a></li>
					<li><hr class="dropdown-divider"></li>
					<li><a class="dropdown-item" href="user_logout">로그아웃</a></li>
				</ul>
			</div> -->
			<!-- 알림 -->
			<button class="btn btn-secondary" onclick="notifyClick()">
				<svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">
					<path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z"></path>
				</svg>
			</button>
			<span id="cntNotify" class="pms-circle-header" onclick="notifyClick()">0</span>
			
			<!-- 채팅 -->
			<button class="btn btn-secondary" onclick="chat_button()">
				<svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
					<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"/>
					<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"/>
				</svg>
			</button>
			<span id="cntMsg" class="pms-circle-header" onclick="chat_button()">0</span>
			
			<!-- 내 정보 설정 -->
			<button class="btn btn-secondary" onclick="goto('mypage_main')">
				<svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-person-gear" viewBox="0 0 16 16">
					<path d="M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0ZM8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4Zm.256 7a4.474 4.474 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10c.26 0 .507.009.74.025.226-.341.496-.65.804-.918C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4s1 1 1 1h5.256Zm3.63-4.54c.18-.613 1.048-.613 1.229 0l.043.148a.64.64 0 0 0 .921.382l.136-.074c.561-.306 1.175.308.87.869l-.075.136a.64.64 0 0 0 .382.92l.149.045c.612.18.612 1.048 0 1.229l-.15.043a.64.64 0 0 0-.38.921l.074.136c.305.561-.309 1.175-.87.87l-.136-.075a.64.64 0 0 0-.92.382l-.045.149c-.18.612-1.048.612-1.229 0l-.043-.15a.64.64 0 0 0-.921-.38l-.136.074c-.561.305-1.175-.309-.87-.87l.075-.136a.64.64 0 0 0-.382-.92l-.148-.045c-.613-.18-.613-1.048 0-1.229l.148-.043a.64.64 0 0 0 .382-.921l-.074-.136c-.306-.561.308-1.175.869-.87l.136.075a.64.64 0 0 0 .92-.382l.045-.148ZM14 12.5a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0Z"/>
				</svg>
			</button>
			<!-- 로그아웃 -->
			<button class="btn btn-secondary" onclick="goto('user_logout')">
				<svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-power" viewBox="0 0 16 16">
					<path d="M7.5 1v7h1V1h-1z"/>
					<path d="M3 8.812a4.999 4.999 0 0 1 2.578-4.375l-.485-.874A6 6 0 1 0 11 3.616l-.501.865A5 5 0 1 1 3 8.812z"/>
				</svg>
			</button>	

			<div class="d-flex" role="search" style="margin-left:10px">        
				<input id="search" class="form-control me-2" type="search" placeholder="통합검색" aria-label="Search" name="keyword">
				<button class="btn btn-dark" type="submit" onclick="searchAll()">Search</button>
				<input type="hidden" name="searchall_project_id" value="${userInfo.project_id}"> 
			</div>
		</div>
	</div>
</nav>

<div id="notify" style="display: none;">
    <div id="notify_close" >
      <input onclick="notify_close()" id="notify_close_btn" class="btn-close" type="button">
    </div>
    <div id="notify_box">
      <c:if test="${userInfo.user_auth == 'manager'}">
         <div class="noticate">프로젝트 승인</div>
         <div id="prjApproveNotify">
         <!-- 프로젝트 생성 승인 알림 -->
         </div>
      </c:if>
      <c:if test="${userInfo.user_auth == 'manager' || userInfo.user_auth == 'student'}">
         <div class="noticate">회의</div>
         <div id="meetingNotify">
         <!-- 회의일정 알림 -->
         </div>
         <div class="noticate">답글</div>
         <div id="repNotify">
         <!-- 답글 알림 -->
         </div>
         <div class="noticate">댓글</div>
         <div id="comtNotify">
         <!-- 댓글 알림 -->
         </div>
      </c:if>
      <c:if test="${userInfo.user_auth == 'admin'}">
         <div class="noticate">프로젝트 생성 신청</div>
         <div id="adminNotify">
         <!-- admin 알림 -->
         </div>
      </c:if>
   </div>
</div>

<div id="chatbox" style="display: none">
	<span style="text-align:right"><input onclick="chat_close()" id="chat_close" class="btn-close bg-white" type="button"></span>
    <div>
    	<input type="hidden" id="user_id" value="${userInfo.user_id}">
    	<input type="hidden" id="user_name" value="${userInfo.user_name}">
    	<table width="100%" id="chat_top">
    		<tr>
    			<td align="left" width="130">
			    	<img class="uploadFile" style=" width: 32px; height: 32px; border-radius: 50%;" alt="UpLoad File" 
			    	src="${pageContext.request.contextPath}/${userInfo.attach_path }/${userInfo.attach_name}">
			    	<span style="margin-left:10px;color:#ffffff">${userInfo.user_name}</span>
    			</td>
    			<td align="left" width="*">
			        <input onclick="chat_user_bt()" id="chat_user_bt" class="btn btn-warning btn-sm" type="button" value="학생 목록">
			        <input onclick="chat_chats_bt()" id="chat_chat_bt" class="btn btn-warning btn-sm" type="button" value="채팅 목록">       
    			</td>
    			<td width="50">
    			</td>
    		</tr>
    	</table>
    </div>
    <div id="chat_content" class="p-3 rounded-2">
        <div id="chat_users" style="display: none">

            <c:forEach items="${chatUIList}" var="chat_user">
                <div id="chat_student_list">
                    <div id="chat_st_left">
                        <img class="uploadFile" style=" width: 32px; height: 32px; border-radius: 50%;" alt="UpLoad File" src="${pageContext.request.contextPath}/${chat_user.attach_path }/${chat_user.attach_name}">
                    </div>
                    <div id="chat_st_center">
                        <p>${chat_user.user_name}</p>
                    </div>
                    <div id="chat_st_right">
                        <button type="button" onclick="chat_room('${chat_user.user_id}')" type="button" class="btn btn-dark btn-sm"
                               value="채팅하기">
							<svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" fill="currentColor" class="bi bi-chat-text" viewBox="0 0 16 16">
								<path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z"></path>
								<path d="M4 5.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zM4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8zm0 2.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5z"></path>
							</svg>
						</button>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div id="chat_chats" style="display: none">
            <c:forEach items="${chatRooms}" var="chatRoom">

            <c:choose>
            <c:when test="${chatRoom.sender_id eq userInfo.user_id}">
            <div id="chat_chat_list" onclick="chat_room('${chatRoom.receiver_id}')">
                </c:when>
                <c:otherwise>
                <div id="chat_chat_list" onclick="chat_room('${chatRoom.sender_id}')">
                    </c:otherwise>
                    </c:choose>
                    <div id="chat_ch_left">
                        <img class="uploadFile" style=" width: 32px; height: 32px; border-radius: 50%;" src="${pageContext.request.contextPath}/${chatRoom.attach_path }/${chatRoom.attach_name}">
                    </div>
                    <div id="chat_ch_center">

                        <c:choose>
                            <c:when test="${chatRoom.sender_id eq userInfo.user_id}">
                                <p>${chatRoom.receiver_id}</p>
                            </c:when>
                            <c:otherwise>
                                <p>${chatRoom.sender_id}</p>
                            </c:otherwise>
                        </c:choose>
                        <p>최근 메시지</p>

                    </div>
                    <div id="chat_ch_right">
                        <p>시간</p>
                    </div>
                    <div id="readCnt">
                        <p></p>
                    </div>
                </div>
                </c:forEach>

            </div>

        </div>

    </div>
</div>