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
    </style>

    <script type="text/javascript">
        $(function () {
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

        var ws;
        var stompClient;
        $(
            function wsOpen() {
                console.log("wsOPEN location.href: " + location.host);
                <%--var wsUri = "ws://" + location.host + "${pageContext.request.contextPath}/chating";--%>
                <%--var wsUri = "ws://" + location.host + "${pageContext.request.contextPath}/chat";--%>
                var wsUri = "/chat";

                console.log("wsURI: " + wsUri);
                ws = new SockJS(wsUri);                    //websocket 연결
                console.log("WS: "+ws);

                stompClient = Stomp.over(ws);               //  찾아봐야함. 설명하기 능력부족
                console.log("stompClient: "+stompClient);

                stompClient.connect({}, function (frame) {  //  중괄호 왜? 사용해? 왜?
                    console.log('Connected: ' + frame);

                    // 연결이 설정되면 구독할 주제 등록
                    stompClient.subscribe("/topic/greetings", function (message) {
                        // 서버에서 메시지를 받았을 때 실행할 코드
                        console.log("message :"+message);
                        var messageBody = JSON.parse(message.body);
                        console.log("Received message: " + messageBody.msg);
                        // 여기에서 메시지를 화면에 표시하거나 처리할 수 있음
                    });

                        // // 연결이 설정되면 서버에 메시지를 보낼 수 있음
                        // stompClient.send("/app/hello", {}, JSON.stringify({ content: "Hello, server!" }));
                });

            });

        // 전체 Message 전송
        function send() {
            //	사용자id 값을 받아야함.
            var option = {
                type: "message",
                chat_room_id: $("#chat_room_id").val(),
                sender_id: $("#myID").val(),
                youID: $("#youID").val(),
                msg_con: $("#send_message").val()
            }
            console.log(option);
            //
            stompClient.send("/app/chat/send", {}, JSON.stringify(option));     //  여기도 중괄호 왜?
        };
    </script>
</head>


<!-- HEADER -->


<div id="chatbox">
    <div id="chat_top">
        <p>상대방 이름</p>
    </div>
    <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">
        <input id="chat_room_id" type="hidden" value="${ChatRoom.chat_room_id}">
        <c:forEach items="${CMList}" var="msg">
            <c:choose>
                <c:when test="${userInfo.user_id eq msg.sender_id}">
                    <input id="myID" type="hidden" value="${msg.sender_id}">
                    <p style="text-align: right">${msg.msg_con}</p>
                    <p style="text-align: right">${msg.send_time}</p>
                </c:when>
                <c:otherwise>
                    <input id="youID" type="hidden" value="${msg.sender_id}">
                    <p style="text-align: left">${msg.msg_con}</p>
                    <p style="text-align: left">${msg.send_time}</p>
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
