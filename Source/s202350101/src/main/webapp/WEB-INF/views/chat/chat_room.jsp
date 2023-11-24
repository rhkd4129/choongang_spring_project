<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <style text="text/css">

        #chatbox {
            float: right;
            width: 100%;
            height: auto; /* 변경된 높이 값 */
            background-color: rgba(13, 110, 253, 0.25);
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        #chat_bottom {
            width: 100%;
            display: flex;
            justify-content: center;

            height: 124px;

        }

        #chat_content {
            height: 400px;
            width: 90%;
            background-color: yellow;
            overflow-x: hidden;
        }
        #left_chat_msg{
            display: flex;
            flex-direction: row;
            align-items: baseline;

        }
        #right_chat_msg{
            display: flex;
            flex-direction: row-reverse;
            align-items: baseline;
        }

        #chat_msg_con {
            font-size: 16px;
        }
        #chat_msg_time {
            font-size: 12px;
            margin: 0 8px 0 8px;
        }
        #chat_msg_read{
            font-size: 12px;
        }
        #send_message{
            width: 77%;
        }

    </style>

    <script type="text/javascript">
        $(function () {
            $('#send_message').val('');
            $.ajax({
                url: '../main_header',
                dataType: 'text',
                success: function (data) {
                    $('#header').html(data);
                }
            });

            $.ajax({
                url: '../main_menu',
                dataType: 'text',
                success: function (data) {
                    $('#menubar').html(data);
                }
            });

            $.ajax({
                url: '../main_footer',
                dataType: 'text',
                success: function (data) {
                    $('#footer').html(data);
                }
            });
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
                                    con += '<div id="right_chat_msg" >';
                                    con += '<p id="chat_msg_con">  ' + chatmsg.msg_con + '  </p>';
                                    con += '<p id="chat_msg_time">  ' + chatmsg.show_time + '  </p>';
                                    con += '<p id="chat_msg_read"> ' + read_chker + ' </p>';
                                    con += '</div>';
                                } else {                //  상대방이 보낸 메세지
                                    con += '<div id="left_chat_msg" >';
                                    con += '<p id="chat_msg_con">  ' + chatmsg.msg_con + '  </p>';
                                    con += '<p id="chat_msg_time">  ' + chatmsg.show_time + '  </p>';
                                    con += '<p id="chat_msg_read"> ' + read_chker + ' </p>';
                                    con += '</div>';
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
        <p>${your.user_name}</p>
    </div>
    <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">
        <input id="chat_room_id" type="hidden" value="${ChatRoom.chat_room_id}">
    </div>
    <div id="chat_bottom">
        <input type="text" id="send_message" onkeyup="enterKey(event);">
        <input type="button" class="btn btn-primary" value="작성완료" onclick="send()">
    </div>
</div>
<!------------------------------ //개발자 소스 입력 END ------------------------------->

</html>
