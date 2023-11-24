
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

