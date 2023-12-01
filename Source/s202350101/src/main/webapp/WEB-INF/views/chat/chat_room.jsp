<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style text="text/css">
.bg-body-tertiary {
    --bs-bg-opacity: 1;
    background-color: #002c41 !important;
}
/* 002c41 2C3E50
383838 383838
*/
#chatbox {
    width: 100%;
    height: 100%; /* 변경된 높이 값 */
    /* background-color: rgba(13, 110, 253, 0.25); */
    background-color: #002c41;;
    color: #fff;
/*     display: flex;
    flex-direction: column; */
    align-items: center;
}

#chat_top {
	width: 100%;
	background-color: #002c41;
	padding:10px;
}
#chat_top > table, th, td {
	color: #fff;
}
#chat_content {
    height: calc(100vh - 128px);;
    width: 100%;
    background-color: #002c41;
    color:#fff;
    overflow-x: hidden;
}
#chat_bottom {
	position: fixed;
	left: 0px;
	bottom: 0px;
    width: 100%;
    height: 75px;
    display: flex;
    background-color: #383838;
    justify-content: center;
}

.msg-table {
	width: 100%;
	margin: 10px 0px;
}

#left_chat_msg{
    /* display: flex;
    flex-direction: row; 
    align-items: baseline;
    */
    display: block;
    width: fit-content;
    max-width:300px;
    background-color: #6c757d ;
    color:#fff;
    border-radius:10px;
    padding:10px;
    margin:5px 0px;
    text-align: left;
}
#right_chat_msg{
    /* display: flex;
    flex-direction: row-reverse; 
    align-items: baseline; */
    display: block;
    width: fit-content;
    max-width:300px;   
    background-color: #ffc107;
    color:#000;
    border-radius:10px;
    padding:10px;
    margin:5px 0px;
    text-align: left;
}

#chat_msg_con {
    font-size: 13px;
}
#chat_msg_time {
    font-size: 12px;
    margin: 0 8px 0 8px;
    color: #8299A6;
}
#chat_msg_read{
    font-size: 12px;
}

#send_message{
    width: 100%;
    background-color: #383838;
    border: 0px solid;
    border-radius: 0px;
	color:#fff;
	font-size:15px;
}
#send_button{
	border-radius: 0px;
}

</style>

    <script type="text/javascript">
        $(function () {
            $('#send_message').val('');
        });
        let ws;             //  웹소캣
        let chatstompClient;    //  stomp
        let nowchatroomid = ${ChatRoom.chat_room_id};   //  접속중인  ID
        //  로그인 사용자
        let myID = '${ChatRoom.sender_id}';
        //  상대방 사용자
        let yourID = '${ChatRoom.receiver_id}';
        $(
            function wsOpen() {
                //  채팅 웹 소캣 주소
                let wsUri = "/chat";

                console.log("wsURI: " + wsUri);
                ws = new SockJS(wsUri);                    //   websocket 연결
                console.log("WS: ");
                console.log(ws);
                //          Stomp : WebSocket over SockJS
                //                  WebSocket을 기반으로 하는 메시징 프로토콜
                chatstompClient = Stomp.over(ws);
                console.log("stompClient: " + chatstompClient);
                //  websocket에 대한 세션의 세부 설정을 하고 싶다면 핸들러 클래스를 만들자.

                //  웹 소캣 연결을 설정하는 부분.
                //  {} : 연결 옵션을 설정할 수 있는 곳 (
                //      headers: 웹 소캣 연결 시 전송할 헤더 정보 지정 가능. 서버로 전달되고,
                //          사용자 정의 헤더 추가 가능.
                //      host:   연결할 웹 소캣 서버의 호스트 주소 지정 가능.
                //      login:  사용자 인증 정보 설정 가능. 사용자 이름 또는 토큰 ( JWT 사용 가능?)
                //      passcode:   사용자 비밀번호 또는 암호 설정에 사용.
                //  function (frame) : 연결 성공 시 이뤄진 후 호출될 콜백 함수 정의
                chatstompClient.connect({}, function (frame) {  //  중괄호 왜? 사용해? 왜?
                    // 연결이 설정되면 구독할 주제 등록
                    console.log(chatstompClient);

                    //  연결되면 메시지 조회
                    receive();
                    chatstompClient.subscribe("/app/great", function (messages) {
                        var res = JSON.parse(messages.body);
                        var firlist = res.firList;
                        console.log("app/great");
                        console.log("firlist");
                        console.log(firlist);
                        console.log("firlist.body");

                        // 필요한 데이터 추출
                        let chatRoomId = res.obj.chat_room_id;  //  서버에 전송된 요청 ID

                        console.log(chatRoomId);
                        console.log(nowchatroomid);
                        if (chatRoomId == nowchatroomid) {
                            $('#send_message').val('');
                            var chat_con = $('#chat_content');
                            chat_con.empty();

                            $.each(firlist, function (index, chatmsg) {
                                console.log(chatmsg);
                                var con = '';
                                
                                let read_chker = chatmsg.read_chk;
                                if (read_chker == 'N') {
                                    read_chker = '안 읽음';
                                } else {
                                    read_chker = '';
                                }
                                console.log("chatmsg.sender_id"+chatmsg.sender_id);
                                console.log("myID"+myID);
                                if (chatmsg.sender_id == myID) { //  내가 보낸 메세지
                                	con += '<table class="msg-table"><tr><td align="right">';
                                    con += '<span id="right_chat_msg" >';
                                    con += '<span id="chat_msg_con">  ' + chatmsg.msg_con + '  </span>';
                                    con += '</span>';
                                    con += '<span id="chat_msg_read"> ' + read_chker + ' </span>';
                                    con += '<span id="chat_msg_time">  ' + chatmsg.show_time + '  </span>';
                                    con += '</td></tr></table>';
                                } else {                //  상대방이 보낸 메세지
                                	con += '<table class="msg-table"><tr><td align="left">';
                                    con += '<span id="left_chat_msg" >';
                                    con += '<span id="chat_msg_con">  ' + chatmsg.msg_con + '  </span>';
                                    con += '</span>';
                                    con += '<span id="chat_msg_time">  ' + chatmsg.show_time + '  </span>';
                                    con += '<span id="chat_msg_read"> ' + read_chker + ' </span>';
                                    con += '</td></tr></table>';
                                }
                                chat_con.append(con);
                            });

                        }
                    });

                    chatstompClient.subscribe("/app/chatreceive", function (message) {
                        console.log(chatstompClient);
                        // 서버에서 메시지를 받았을 때 실행할 코드
                        console.log("Received message: " + message.body);
                        //  메시지 수신 성공 시 main_header에 읽지 않은 채팅 수 업데이트.
                        //  -> send(/app/chat/notify)
                        //  컨트롤러에서 SendTo /queue/
                        //  main_header에서

                        // 메시지 body를 JSON 형식으로 파싱
                        let rtnmsg = JSON.parse(message.body);

                        // 필요한 데이터 추출
                        let chatRoomId = rtnmsg.chat_room_id;
                        //  컨트롤러에서 받은 데이터의 채팅방 id 검증
                        if (chatRoomId == nowchatroomid) {
                            receive();
                        }
                    });
                });
            }
        );

        function receive(){
            //	사용자id 값을 받아야함.
            var option = {
                chat_room_id: nowchatroomid, //  현재 채팅방
                sender_id: myID,       //  본인 id
                receiver_id: yourID,         //  상대 id
            }
            var mainoption={
                chat_room_id: nowchatroomid, //  현재 채팅방
                receiver_id: myID,       //  본인 id
                sender_id: yourID,         //  상대 id
            }
            console.log("receive: ");
            console.log(option);
            //  {} : 헤더 정보 설정 시 사용.
            //  웹 소켓 메시지에 대한 추가 정보 담음
            //  EX_ 인증 토큰, 메시지 유형, 목적지 주소, 메시지ID(식별자), 시간 정보, 사용자 정보
            chatstompClient.send("/queue/chat/cnt", {}, JSON.stringify(mainoption));
            chatstompClient.send("/queue/chat/cnt", {}, JSON.stringify(option));
            chatstompClient.send("/queue/chat/receive", {}, JSON.stringify(option));
            $('#send_message').val('');
        }

        // 전체 Message 전송
        function send() {
            //	사용자id 값을 받아야함.
            var option = {
                chat_room_id: nowchatroomid, //  현재 채팅방
                sender_id: myID,       //  본인 id
                receiver_id: yourID,         //  상대 id
                msg_con: $("#send_message").val()
            }

            let value = option.msg_con;
            if( value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length ) ){
                alert("메시지를 입력하세요");
                return false;
            }

            console.log("send: ");
            console.log(option);
            //  {} : 헤더 정보 설정 시 사용.
            //  웹 소켓 메시지에 대한 추가 정보 담음
            //  EX_ 인증 토큰, 메시지 유형, 목적지 주소, 메시지ID(식별자), 시간 정보, 사용자 정보
            option.msg_con != null
            chatstompClient.send("/queue/chat/send", {}, JSON.stringify(option));     //  여기도 중괄호 왜?

        }

        function enterKey(event) {
            if (event.key === 'Enter') {
                send();
            }
        }

    </script>
</head>


<!-- HEADER -->


<div id="chatbox">
    <div id="chat_top">
    	<table width="100%">
    		<tr>
	    		<td align="left">
			        <img class="uploadFile" style=" width: 32px; height: 32px; border-radius: 50%;" alt="UpLoad File" 
			        src="${pageContext.request.contextPath}/${your.attach_path }/${your.attach_name}">
			        <span style="margin-left:10px">${your.user_name}</span>
	    		</td>
	    		<td align="right">
			        <span style="margin-right:10px">${userInfo.user_name}</span>
			        <img class="uploadFile" style=" width: 32px; height: 32px; border-radius: 50%;" alt="UpLoad File" 
			        src="${pageContext.request.contextPath}/${userInfo.attach_path }/${userInfo.attach_name}">
	    		</td>
    		</tr>
    	</table>
    </div>
    <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">
        <input id="chat_room_id" type="hidden" value="${ChatRoom.chat_room_id}">
    </div>
    <div id="chat_bottom">
        <input type="text" class="form-control" id="send_message" onkeyup="enterKey(event);">
        <button type="button" id="send_button" class="btn btn-secondary" onclick="send()">
        	<svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-arrow-up-square" viewBox="0 0 16 16">
			<path fill-rule="evenodd" d="M15 2a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1zM0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm8.5 9.5a.5.5 0 0 1-1 0V5.707L5.354 7.854a.5.5 0 1 1-.708-.708l3-3a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 5.707z"/>
			</svg><br>보내기
		</button>
    </div>
</div>
<!------------------------------ //개발자 소스 입력 END ------------------------------->

</html>
