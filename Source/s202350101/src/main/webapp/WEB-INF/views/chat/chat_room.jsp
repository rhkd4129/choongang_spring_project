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



        // 전체 Message 전송
        function send() {
            //	사용자id 값을 받아야함.
            var option = {
                type : "message",
                sessionId : $("#sessionId").val(),
                userName : $("#userName").val(),
                yourName : $("#member_sub").val(),
                msg : $("#sendMsg").val()
            }
            // 자바스크립트의 값을 JSON 문자열로 변환
            ws.send(JSON.stringify(option));
            $('#sendMsg').val("");
        }
    </script>
</head>


<!-- HEADER -->


<div id="chatbox">
    <div id="chat_top">
        <p>상대방 이름</p>
    </div>
    <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">

        <c:forEach items="${CMList}" var="msg">
            <c:choose>
                <c:when test="${userInfo.user_id eq msg.sender_id}">
                    <p style="text-align: left">${msg.msg_con}</p>
                    <p style="text-align: left">${msg.send_time}</p>
                </c:when>
                <c:otherwise>
                    <p style="text-align: right">${msg.msg_con}</p>
                    <p style="text-align: right">${msg.send_time}</p>
                </c:otherwise>
            </c:choose>
        </c:forEach>



    </div>
    <div id="chat_bottom">
        <textarea cols="40" rows="3">
        </textarea>
        <input type="button" class="btn btn-primary" value="작성완료" onclick="send()">
    </div>
</div>
<!------------------------------ //개발자 소스 입력 END ------------------------------->

</html>
