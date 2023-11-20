<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
	body{
	 -ms-overflow-style: none;
	 }
	#notify {
	    position: absolute;
	    width: 350px;
	    height: 450px;
	    right: 400px;
	    z-index: 999;
	    padding: 0px 10px 10px 10px;
	    background-color: rgba(13, 110, 253, 0.25);;
	    overflow-y: scroll;
		box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
	}
	#notify::-webkit-scrollbar {
	  display: none;
	}
	#prjApproveNotify {
		background-color: white;
	    padding: 15px 5px 5px 5px;
	    margin-top: 10px;
	    border-radius: 10px;
	}
	#meetingNotify {
		background-color: white;
	    padding: 15px 5px 5px 5px;
	    margin-top: 10px;
	    border-radius: 10px;
	}
	#repNotify {
		background-color: white;
	    padding: 15px 5px 5px 5px;
	    margin-top: 10px;
	    border-radius: 10px;
	}
	#comtNotify {
		background-color: white;
	    padding: 15px 5px 5px 5px;
	    margin-top: 10px;
	    border-radius: 10px;
	}
	#adminNotify {
		background-color: white;
	    padding: 15px 5px 5px 5px;
	    margin-top: 10px;
	    border-radius: 10px;
	}
    #notify_close{
        margin: 8px 0px 8px 0px;
    }
    #notify_close_btn{
        margin: 0 0 0 83%;
    }
</style>

<script type="text/javascript">
//진희
	// stomp 사용
	let stompClient;


	$(document).ready(function () {
	    onSocket();
	});

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
	        // console.log("1");
	        stompClient = Stomp.over(socket);
	        // console.log("2");

	        const obj = {
	            project_id: '${userInfo.project_id}',
	            user_id: '${userInfo.user_id}',
	            user_auth: '${userInfo.user_auth}'
	        };

	        stompClient.connect({}, function (frame) {
	            console.log('Connected: ' + frame);

	            // console.log("3");
	            console.log(obj);

	            // 프로젝트 생성 승인 알림 (팀장)
	            stompClient.subscribe("/noti/prjapprove", function(data) {
	            	console.log("프로젝트 생성 승인 알림");

	            	var approvedata = JSON.parse(data.body);
	            	console.log(approvedata);

	            	let prjApproveNotify = $('#prjApproveNotify');
	            	prjApproveNotify.empty();

	                let str = '';

	            	for (var i = 0; i < approvedata.length; i++) {
	            		const prjName = approvedata[i].project_name;						// 프로젝트명

	            		str += '<p onclick="location.href=' + "'/prj_mgr_step_list'" + '">' + prjName + ' 프로젝트 생성이 승인되었습니다.</p>';
	            	}

	            	prjApproveNotify.append(str);
	            });

	            // 회의일정 알림
	            stompClient.subscribe("/noti/meeting", function (data) {
					var rtndata = JSON.parse(data.body);
					console.log(rtndata);

	                const date = new Date();
	                const year = date.getFullYear();
	                const month = ('0' + (date.getMonth() + 1)).slice(-2);
	                const day = ('0' + (date.getDate())).slice(-2);
	                let now = year + "-" + month + "-" + day;

	                console.log("now date: " + now);

	                let meetingNotify = $('#meetingNotify');
	                meetingNotify.empty();

	                let meetStr = '';

	                for (var i = 0; i < rtndata.length; i++) {
	                    const meetingDate = rtndata[i].meeting_date;
	                    // console.log(meetingDate);

	                    if (meetingDate == now) {
	                    	meetStr += '<p onclick="location.href=' + "'/prj_meeting_calendar?project_id=" + rtndata[i].project_id + "'" + '"' + '>오늘(' + rtndata[i].meeting_date + ') 예정된 ' + rtndata[i].meeting_title + ' 회의가 있습니다.</p>';
	                    }
	                }

	                meetingNotify.append(meetStr);
	            });

	            // 게시판 답글
	            stompClient.subscribe("/noti/boardRep", function(data) {
	            	var repdata = JSON.parse(data.body);
	            	console.log(repdata);

	            	let repNotify = $('#repNotify');
	            	repNotify.empty();

	            	let repStr = '';

 	            	for (var i = 0; i < repdata.length; i++) {
	                    const doc_no = repdata[i].doc_no;				// 글 번호
	                    const subject = repdata[i].subject;				// 글 제목
	                    const board_name = repdata[i].app_name;			// 게시판 이름
	                    const app_id = repdata[i].app_id;

	                    for (var j = 0; j < repdata.length; j++) {
	                    	const parent_doc_no = repdata[j].parent_doc_no;
	                    	const parent_app_id = repdata[j].app_id;

	                    	const rep_subject = repdata[j].subject;		// 답글 제목
	                    	const rep_doc_no = repdata[j].doc_no;		// 답글 번호
	                    	const doc_group = repdata[j].doc_group;		// doc_group

	                    	// 질문 게시판
	                    	if (app_id == 2) {
//	                    			글번호		답글 부모
		                    	if (doc_no == parent_doc_no && parent_app_id == app_id) {
			                    	repStr += '<p>[' + board_name + ' 게시판] ' + subject + '에 [답글] ' + rep_subject + '이 등록되었습니다.</p>';
			                    }
	                    	}

	                    	// 프로젝트 공지/자료 게시판
	                    	if (app_id == 3) {
//	                    			글번호		답글 부모
		                    	if (doc_no == parent_doc_no && parent_app_id == app_id) {
			                    	repStr += '<p onclick="location.href=' + "'/prj_board_data_list?doc_group=" + doc_group + "&doc_group_list=y'" + '"' + '>[' + board_name + ' 게시판] ' + subject + '에 [답글] ' + rep_subject + '이 등록되었습니다.</p>';
			                    }
	                    	}
	                    }
	                }

	            	repNotify.append(repStr);
	            });

	            // 게시판 댓글
	            stompClient.subscribe("/noti/boardComt", function(data) {
	            	var comtdata = JSON.parse(data.body);
	            	console.log(comtdata);

	            	let comtNotify = $('#comtNotify');
	            	comtNotify.empty();

	            	let comtStr = '';

    				let doc = 0;
    				let app = 0;

	            	for (var i = 0; i < comtdata.length; i++) {
	            		const app_id 			= comtdata[i].app_id;				//	테이블 분류
            			const board_name 		= comtdata[i].app_name;				// 	게시판 이름
	            		const bd_category 		= comtdata[i].bd_category;			// 	카테고리
	            		const doc_no 			= comtdata[i].doc_no;				// 	글 번호
	            		const subject 			= comtdata[i].subject;				// 	글 제목
	            		const comment_context 	= comtdata[i].comment_context;		// 	댓글 내용
	            		const comment_count		= comtdata[i].comment_count;		// 	댓글 갯수
	            		const project_id 		= comtdata[i].project_id;			//	프로젝트 ID

	            		// 공용게시판
	            		if (app_id == 1) {
	            			if (bd_category == '자유') {		// 자유 게시판
								let loc ='free_content';
	            				if (doc != doc_no || app != app_id){
	            					comtStr += '<p onclick="locatFree('+ doc_no + ", 'free_content'" + ')">[' + bd_category + ' 게시판] ' + subject + '에 새로운 댓글이 ' + comment_count + '건 등록되었습니다.</p>';
	            					console.log('comment_count');
	            					console.log(comment_count);
	            					doc = doc_no;
	            					app = app_id;
	            				}
	            			}
	            			if (bd_category == '이벤트') {	// 이벤트 게시판
	            				let loc = 'event_content';
	            				if (doc != doc_no || app != app_id){
	            					comtStr += '<p onclick="locatFree('+ doc_no + ", 'event_content'" + ')">[' + bd_category + ' 게시판] ' + subject + '에 새로운 댓글이 ' + comment_count + '건 등록되었습니다.</p>';
	            					console.log('comment_count');
	            					console.log(comment_count);
	            					doc = doc_no;
	            					app = app_id;
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
            				}
	            		}
	            	};

	            	comtNotify.append(comtStr);
	            });

	         	// 프로젝트 생성 신청 - admin 계정만 해당
            	stompClient.subscribe("/noti/newprj", function(data) {
            		var newprjdata = JSON.parse(data.body);
            		console.log(newprjdata);

            		let adminNotify = $('#adminNotify');
            		adminNotify.empty();

            		let newprjStr = '';
            		const newprj = newprjdata.length;

            		if (newprj != 0) {
            			newprjStr = '<p onclick="location.href=' + "'/admin_approval'" + '">신규 프로젝트 생성 신청이 ' + newprj + '건 있습니다.</p>';
            		}


            		adminNotify.append(newprjStr);
            	});

	            stompClient.send('/queue/approve', {}, JSON.stringify(obj));	// 프로젝트 생성 승인
	            stompClient.send('/queue/meet', {}, JSON.stringify(obj));		// 회의일정
	            stompClient.send('/queue/rep', {}, JSON.stringify(obj));		// 답글
	            stompClient.send('/queue/comt', {}, JSON.stringify(obj));		// 댓글
	            stompClient.send('/queue/prj', {}, JSON.stringify(obj));		// 프로젝트 생성 신청 (admin)
	        });
	    }

	    // 초기 연결 수행
	    connectWebSocket();

	    // 5초마다 웹 소켓 연결을 끊고 다시 연결
	    setInterval(function () {
	        disconnectWebSocket();
	    }, 5000);
	};

    function notify_close(){
        let con = document.getElementById("notify");
        con.style.display = 'none';
    }
	// 알림버튼 클릭 시 작동
	function notifyClick() {

		var con = document.getElementById("notify");
		let prjApproveNotify = $('#prjApproveNotify');
		let meetingNotify = $('#meetingNotify');
		let repNotify = $('#repNotify');
    	let comtNotify = $('#comtNotify');
    	let adminNotify = $('#adminNotify');

		if (con.style.display == 'none') {
			con.style.display = 'block';
		} else {
			con.style.display = 'none';
		}

		if (prjApproveNotify.text() == '') {
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
		}
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

</script>


<style text="text/css">

    #chatbox {
        right: 0;
        position: absolute;
        z-index: 999;
        float: right;
        width: 20%;
        height: auto; /* 변경된 높이 값 */
        background-color: rgba(13, 110, 253, 0.25);
        /*display: none;*/
        flex-direction: column;
        align-items: center;
        box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
    }

    #chat_bottom {
        display: flex;

    }

    #chat_top {
        margin: 8px 0px 8px 0px;
    }

    #chat_content {
        height: 400px;
        width: 90%;
        background-color: yellow;
        overflow-x: hidden;
    }

    #chat_student_list {

        display: flex;
        justify-content: space-evenly;
        overflow-x: hidden;
    }

    #chat_chat_list {

        display: flex;
        justify-content: space-evenly;
        overflow-x: hidden;
    }

    #center {
        position: relative;
    }

    #chat_ch_center {
        font-size: 0.7em;
    }

    #cntMsg {
        color: white;
    }

</style>
<script type="text/javascript">
    //준우
    var ws;
    let chat_chats = document.getElementById("chat_chats");
    let chat_users = document.getElementById("chat_users");

    function chat_button() {
        var con = document.getElementById("chatbox");

        if (con.style.display == 'none') {
            con.style.display = 'flex';
            chat_users.style.display = 'block';
        } else {
            con.style.display = 'none';
            chat_users.style.display = 'none';
        }
    }
    function chat_close() {
        var con = document.getElementById("chatbox");

        con.style.display = 'none';
        chat_users.style.display = 'none';
    }

    function chat_user_bt() {
        chat_chats.style.display = 'none';
        chat_users.style.display = 'block';
    }

    function chat_chats_bt() {
        chat_users.style.display = 'none';
        chat_chats.style.display = 'block';
    }

    function chat_room(user_id) {
        console.log(user_id);
        window.open(
            "/chat_room?user_id=" + user_id,
            "Child",
            "width=600, height=570, top=50, left=50, resizable=no"
        );
    }

    $(
        function wsOpen() {
            let ws;             //  웹소캣
            let chatstompClient;    //  stomp
            let user = '${userInfo.user_id}';
            let wsUri = "/chat";
            ws = new SockJS(wsUri);                    //   websocket 연결
            chatstompClient = Stomp.over(ws);
            chatstompClient.connect({}, function (frame) {
                // console.log("chatstompClient");
                // console.log(chatstompClient);
                let option = {
                    sender_id: user
                };
                chatstompClient.send("/queue/chat/cnt", {}, JSON.stringify(option));     //  여기도 중괄호 왜?

                chatstompClient.subscribe("/app/cnttotmsg", function (message) {
                    console.log("getMessage");

                    let msg = JSON.parse(message.body);
                    let cntmsg      = $('#cntMsg');     //  읽지 않은 메시지 수입력할 공간
                    let getUserId   = msg.secobj;       //  요청의 주체
                    let noReadChat  = msg.obj;          //  내가 읽지 않은 메시지 수
                    let chatList    = msg.secList;      //  채팅방 목록
                    // let recUserID = msg.trdobj;

                    console.log(msg);
                    console.log("chatList");
                    let con = '읽지 않은 메시지: ' + noReadChat;


                    if (getUserId == user) {
                        cntmsg.empty();
                        cntmsg.append(con);
                    }
                    if (getUserId == user) {

                        let chat_chats = $('#chat_chats');   //  채팅방 공간
                        let chatroom_con = '';

                        $.each(chatList , function (index, ChatRoom) {
                            // chatroom_con += '<div id="chat_chats" style="display:none">'
                            let show_time = ChatRoom.show_time == null ? '최근 메시지 없음' :  ChatRoom.show_time;
                            let msg_con = ChatRoom.msg_con == null ? '최근 메시지 없음' : ChatRoom.msg_con;
                            let read_cnt = ChatRoom.read_cnt == 0 ? 0 : ChatRoom.read_cnt;

                            console.log("ChatRoom");
                            console.log(ChatRoom);
                            if (ChatRoom.sender_id == user) {
                                chatroom_con += '<div id="chat_chat_list" onclick="chat_room('+"'" + ChatRoom.receiver_id + "'"+ ')">';
                            } else {
                                chatroom_con += '<div id="chat_chat_list" onclick="chat_room('+"'" + ChatRoom.sender_id + "'" + ')">';
                            }
                            chatroom_con += '<div id="chat_ch_left">';
                            console.log("hihi");
                            console.log(ChatRoom.attach_path);
                            console.log(ChatRoom.attach_name);
                            chatroom_con += '<img className='+'"uploadFile"'+'style='+'"width:30px; height: 30px; border-radius: 70%;"'+' src='+'"'+'${pageContext.request.contextPath}'+ChatRoom.attach_path+'/'+ChatRoom.attach_name+'"></div>';
                            chatroom_con += '<div id="chat_ch_center">';
                            chatroom_con += '<p>' + ChatRoom.user_name + '</p>';
                            chatroom_con += '<p>'+"'"+ msg_con+"'"+'</p></div>';
                            chatroom_con += '<div id="chat_ch_right"><p>' + show_time + '</p></div>';
                            chatroom_con += '<div id="readCnt"> <p>'+"'"+read_cnt + "'" +'</p></div></div>';
                        });
                        chat_chats.empty();
                        // console.log("chatroom_con");
                        // alert("hi");
                        // console.log(chatroom_con);
                        chat_chats.append(chatroom_con);
                    }
                })
            })
        }
    )

</script>

<script type="text/javascript">
//인정
//  $(function(){ 아래 코드랑 같음
	$(document).ready(function(){
		$("#search").keydown(function (key) {
			if(key.keyCode == 13) {
		      searchAll();
			}
		});
	});

    function searchAll(){
    	var keyword = $("input[name=keyword]").val(); // 검색어
    	var params = {};
    	params.keyword = keyword;

    	$.ajax({
    		url			: '/search_all',
    		data		: JSON.stringify(params),
    		type		: 'POST',
    		contentType	: 'application/json; charset:utf-8',
    		dataType	: 'json',
    		success		: function(data){
    			//alert(data);
			    showSearchList(data, keyword);
			},
			error		: function(xhr, status, error){
				console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
			}
    	});

    }
/*
   */

    // 검색(인정)
    function showSearchList(docList, keyword){
    	if(Object.keys(docList).length==0){
    		alert("해당 검색 결과가 없습니다.");
    	}
    	else{
        	$("#center").empty();
        	var list = '';
        	list += '<div class="container-fluid"><div style="margin-top:20px;height:45px"><h3>통합 검색 결과</h3></div>검색결과 ' + Object.keys(docList).length + '건   검색어:' + keyword + '</div>';
        	$("#center").append(list);
        	list = '<div class="container-fluid"><div style="width:85%;" id="divSearchResult"></div></div>';
        	$("#center").append(list);
    		$(docList).each(function(index, doc){
    			//alert("제목 :"+doc.subject);

				list = '<div class="d-flex text-body-secondary pt-3">';
				list += '<svg class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"></rect><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>';
				list += '<div class="pb-3 mb-0 small lh-sm border-bottom w-100">';
				list += '<div class="d-flex justify-content-between">';
				list += '<strong class="text-gray-dark"><a href="javascript:popup(\'bd_free?doc_no=\')">'+doc.subject+'</a> | ';
				if(doc.app_id == "1"){
    				list	+= doc.bd_category;
    			}else{
    				list	+= doc.app_name;
    			}
				list += '</strong>';
				list += '<a href="#">' + doc.user_name + ' ' + formatDateTime(doc.create_date) + '</a>';
				list += '</div>';
				list += '<span class="d-block">' + doc.doc_body + '</span>';
				list += '</div>';
				list += '</div>';

    			$("#divSearchResult").append(list);
    		});
    	}
    }


</script>

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
	<div class="container-fluid">
		<a class="navbar-brand" href="/main">PMS</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarCollapse">
			<ul class="navbar-nav me-auto mb-2 mb-md-0">
	        </ul>
			<ul class="nav nav-pills">
				<li class="nav-item">
					<a class="nav-link px-2 link-light" aria-current="page" href="#">${userInfo.user_name }</a>
				</li>
			</ul>
            <%--<input style="background-image: url('admin/images/chat.png');  " type="button" class="img-button" onclick="alert('클릭!')">--%>
            <div id="cntMsg"> </div>
            <button id="chat_button" type="button" onclick="chat_button()">채팅</button>
            <%-- 채팅--%>
            <%-- 채팅--%>
			<div class="dropdown text-end">
                <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                    <img class="uploadFile" style=" width: 30px; height: 30px; border-radius: 70%;" alt="UpLoad File" src="${pageContext.request.contextPath}/${userInfo.attach_path }/${userInfo.attach_name}"></td>
                </a>
				<ul class="dropdown-menu text-small" style="">
					<li><a class="dropdown-item" href="mypage_main">내 정보 설정</a></li>
					<li><hr class="dropdown-divider"></li>
					<li><a class="dropdown-item" href="user_logout">로그아웃</a></li>
				</ul>
			</div>
			<div>
				<button type="button" onclick="notifyClick()">알림</button>
			</div>
			<div class="d-flex" role="search" style="margin-left:10px">
				<input id="search" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="keyword">
				<button class="btn btn-outline-secondary" type="submit" onclick="searchAll()">Search</button>
			</div>
		</div>
	</div>
</nav>

<div id="notify" style="display: none;">
    <div id="notify_close" >

        <input onclick="notify_close()" id="notify_close_btn" class="btn btn-warning" type="button" value="닫기">
    </div>

	<div id="prjApproveNotify">
	<!-- 프로젝트 생성 승인 알림 -->
	</div>
	<div id="meetingNotify">
	<!-- 회의일정 알림 -->
	</div>
	<div id="repNotify">
	<!-- 답글 알림 -->
	</div>
	<div id="comtNotify">
	<!-- 댓글 알림 -->
	</div>
	<div id="adminNotify">
	<!-- 댓글 알림 -->
	</div>
</div>

<div id="chatbox" style="display: none">
    <div id="chat_top">
        <input onclick="chat_user_bt()" id="chat_user_bt" class="btn btn-warning" type="button" value="학생 목록">
        <input onclick="chat_chats_bt()" id="chat_chat_bt" class="btn btn-warning" type="button" value="채팅 목록">
        <input onclick="chat_close()" id="chat_close" class="btn-close" type="button">
    </div>
    <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">
        <div id="chat_users" style="display: none">

            <c:forEach items="${chatUIList}" var="chat_user">
                <div id="chat_student_list">
                    <div id="chat_st_left">
                        <img class="uploadFile" style=" width: 30px; height: 30px; border-radius: 70%;" alt="UpLoad File" src="${pageContext.request.contextPath}/${chat_user.attach_path }/${chat_user.attach_name}"></td>
                    </div>
                    <div id="chat_st_center">
                        <p>${chat_user.user_name}</p>
                    </div>
                    <div id="chat_st_right">
                        <input onclick="chat_room('${chat_user.user_id}')" type="button" class="btn btn-primary"
                               value="채팅하기">
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
                        <img class="uploadFile" style=" width: 30px; height: 30px; border-radius: 70%;" src="${pageContext.request.contextPath}/${chatRoom.attach_path }/${chatRoom.attach_name}">
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