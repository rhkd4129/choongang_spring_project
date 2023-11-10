<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style type="text/css">
	#notify {
	    position: absolute;
	    width: 300px;
	    height: 400px;
	    background-color: skyblue;
	    right: 300px;
	    z-index: 999;
	}
</style>

<script type="text/javascript">
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
        console.log("1");
        stompClient = Stomp.over(socket);
        console.log("2");

        const obj = {
            project_id: '${userInfo.project_id}',
            user_id: '${userInfo.user_id}'
        };

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            console.log("3");
            console.log(obj);

            stompClient.subscribe("/noti/test", function (data) {
                console.log("hi");
                console.log(data);

                var rtndata = JSON.parse(data.body);

                const date = new Date();
                const year = date.getFullYear();
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + (date.getDate())).slice(-2);
                let now = year + "-" + month + "-" + day;

                console.log("now date: " + now);
                
                let notify = $('#notify');
                notify.empty();
                
                let str = '';
                
                for (var i = 0; i < rtndata.length; i++) {
                    const meetingDate = rtndata[i].meeting_date;
                    console.log(meetingDate);

                    if (meetingDate == now) {
						str += '<p onclick="location.href=' + "'/prj_meeting_calendar?project_id=" + rtndata[i].project_id + "'" + '"' + '>오늘(' + rtndata[i].meeting_date + ') 예정된 ' + rtndata[i].meeting_title + ' 회의가 있습니다.</p>';
                    }
                }
                
                notify.append(str);
            });

            stompClient.send('/queue/post', {}, JSON.stringify(obj));
            console.log("4");
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
		
		if (con.style.display == 'none') {
			con.style.display = 'block';
		} else {
			con.style.display = 'none';
		}
		
	};
</script>
   

<style text="text/css">

    #chatbox {
        right: 0;
        position: absolute;
        z-index: 999;
        float: right;
        width: 30%;
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


</style>
<script type="text/javascript">
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
            "width=600, height=570, top=50, left=50"
        );
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
            <button id="chat_button" type="button" onclick="chat_button()">채팅</button>
            <%-- 채팅--%>
            <%-- 채팅--%>
			<div class="dropdown text-end">
				<a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
					<img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle">
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
				<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-secondary" type="submit">Search</button>
			</div>
		</div>
	</div>
</nav>

<div id="notify" style="display: none; overflow-y: scroll; height:400px;">
	<div id="notifyBox">
	
	</div>
</div>

<div id="chatbox" style="display: none">
    <div id="chat_top">
        <input onclick="chat_user_bt()" id="chat_user_bt" class="btn btn-warning" type="button" value="학생 목록">
        <input onclick="chat_chats_bt()" id="chat_chat_bt" class="btn btn-warning" type="button" value="채팅 목록">
    </div>
    <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">
        <div id="chat_users" style="display: none">
            <c:forEach items="${chatUIList}" var="chat_user">
                <div id="chat_student_list">
                    <div id="chat_st_left">
                        <p>이미지</p>
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
                        <p>이미지</p>
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
                </div>
            </c:forEach>

            </div>

        </div>

    </div>
</div>