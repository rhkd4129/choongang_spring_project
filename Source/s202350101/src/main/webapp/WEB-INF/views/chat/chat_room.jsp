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
            display: flex;

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

        }
        #right_chat_msg{
            display: flex;
            flex-direction: row-reverse;
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

        var ws;             //  웹소캣
        var stompClient;    //  stomp
        $(
            function wsOpen() {
                myid = $('#userInfo.user_id');

                console.log("wsOPEN location.href: " + location.host);
                var wsUri = "/chat";

                console.log("wsURI: " + wsUri);
                ws = new SockJS(wsUri);                    //   websocket 연결
                console.log("WS: "+ws);
                //          Stomp : WebSocket over SockJS
                //                  WebSocket을 기반으로 하는 메시징 프로토콜
                stompClient = Stomp.over(ws);               //  찾아봐야함. 설명하기 능력부족 위에 설명.
                console.log("stompClient: "+stompClient);


                //  websocket에 대한 세션의 세부 설정을 하고 싶다면 핸들러 클래스를 만들자.

                // var sockJsOptions = {
                //     debug: true,          // 디버그 모드 활성화
                //     rtt: 10000,           // 연결 시도 간격 (밀리초)
                //     server: 'https://example.com',  // SockJS 서버 주소
                //     transport: 'websocket',        // 웹소켓을 사용할 것임
                //     timeout: 30000,                // 연결 타임아웃 (밀리초)
                // };

                //  웹 소캣 연결을 설정하는 부분.
                //  {} : 연결 옵션을 설정할 수 있는 곳 (
        //              headers: 웹 소캣 연결 시 전송할 헤더 정보 지정 가능. 서버로 전달되고,
                //          사용자 정의 헤더 추가 가능.
                //      host:   연결할 웹 소캣 서버의 호스트 주소 지정 가능.
                //      login:  사용자 인증 정보 설정 가능. 사용자 이름 또는 토큰 ( JWT 사용 가능?)
                //      passcode:   사용자 비밀번호 또는 암호 설정에 사용.
                //  function (frame) : 연결 성공 시 이뤄진 후 호출될 콜백 함수 정의
                stompClient.connect({}, function (frame) {  //  중괄호 왜? 사용해? 왜?
                    // 연결이 설정되면 구독할 주제 등록
                    stompClient.subscribe("/queue/greetings", function (message) {
                        // 서버에서 메시지를 받았을 때 실행할 코드
                        console.log("Received message: " + message.body);
                        //  메시지 수신 성공 시 main_header에 읽지 않은 채팅 수 업데이트.
                        //  -> send(/app/chat/notify)
                        //  컨트롤러에서 SendTo /queue/
                        //  main_header에서

                        // 메시지 body를 JSON 형식으로 파싱
                        var rtnmsg = JSON.parse(message.body);

                        // 필요한 데이터 추출
                        var chatRoomId = rtnmsg.chat_room_id;
                        var msgContent = rtnmsg.msg_con;
                        var senderId = rtnmsg.sender_id;
                        var send_time = rtnmsg.send_time;
                        //  로그인 사용자 id
                        var myID = '${userInfo.user_id}';
                        // 여기에서 메시지를 화면에 표시하거나 처리할 수 있음
                        var chat_con = $('#chat_content');
                        let con='';
                        console.log("sender: " + senderId + " myID: " + myID);
                        if (senderId == myID) { //  내가 보낸 메세지
                            con += '<div id="right_chat_msg" >';
                            con += '<p>' + msgContent + '</p>';
                            con += '<p>' + send_time + '</p>';
                            con += '</div>';
                        } else {                //  상대방이 보낸 메세지
                            con += '<div id="left_chat_msg" >';
                            con += '<p>' + msgContent + '</p>';
                            con += '<p>' + send_time + '</p>';
                            con += '</div>';
                        }
                        chat_con.append(con);
                    });
                });
            });

        // 전체 Message 전송
        function send() {
            //	사용자id 값을 받아야함.
            var option = {
                type: "message",
                chat_room_id: ${ChatRoom.chat_room_id},
                sender_id: '${userInfo.user_id}',
                youID: $("#youID").val(),
                msg_con: $("#send_message").val()
            }
            console.log(option);
            //  {} : 헤더 정보 설정 시 사용.
            //  웹 소켓 메시지에 대한 추가 정보 담음
            //  EX_ 인증 토큰, 메시지 유형, 목적지 주소, 메시지ID(식별자), 시간 정보, 사용자 정보
            stompClient.send("/app/chat/send", {}, JSON.stringify(option));     //  여기도 중괄호 왜?
            $('#send_message').val('');
        };
    </script>
</head>


<!-- HEADER -->


<div id="chatbox">
    <div id="chat_top">
        <p>${your.user_name}</p>
    </div>
    <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">
        <input id="chat_room_id" type="hidden" value="${ChatRoom.chat_room_id}">
        <c:forEach items="${CMList}" var="msg">             <%--채팅방 내 메세지--%>
            <c:choose>
                <c:when test="${userInfo.user_id eq msg.sender_id}">        <%--내가 보낸 메세지--%>
                    <input id="myID" type="hidden" value="${msg.sender_id}">
                    <div id="right_chat_msg" >
                        <p>${msg.msg_con}</p>
                        <p>${msg.send_time}</p>
                        <c:choose>
                            <c:when test="${msg.read_chk eq 'N'}">
                                <p>안 읽음</p>
                            </c:when>
                            <c:otherwise>
                                <p>읽음</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:when>
                <c:otherwise>
                    <input id="youID" type="hidden" value="${msg.sender_id}">   <%--상대가 보낸 메세지--%>
                    <div id="left_chat_msg">
                        <p>${msg.msg_con}</p>
                        <p>${msg.send_time}</p>
                        <c:choose>
                            <c:when test="${msg.read_chk eq 'N'}">
                                <p>안 읽음</p>
                            </c:when>
                            <c:otherwise>
                                <p>읽음</p>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
    <div id="chat_bottom">
        <textarea cols="40" rows="3" id="send_message">
        </textarea>
        <input type="button" class="btn btn-primary" value="작성완료" onclick="send()">
    </div>
</div>
<!------------------------------ //개발자 소스 입력 END ------------------------------->

</html>
