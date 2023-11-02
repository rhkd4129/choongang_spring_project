<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


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
        var chat_chats = document.getElementById("chat_chats");
        var chat_users = document.getElementById("chat_users");
        chat_chats.style.display = 'none';
        chat_users.style.display = 'block';
    }

    function chat_chats_bt() {
        var chat_chats = document.getElementById("chat_chats");
        var chat_users = document.getElementById("chat_users");
        chat_users.style.display = 'none';
        chat_chats.style.display = 'block';
    }

    function chat_room(){
        window.open(
            "chat_room",
            "Child",
            "width=400, height=300, top=50, left=50"
        );

    }

</script>
<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/main">PMS</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse"
                aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav me-auto mb-2 mb-md-0">
                <!-- <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link disabled" aria-disabled="true">Disabled</a>
                  </li> -->
            </ul>
            <ul class="nav nav-pills">
                <li class="nav-item">
                    <a class="nav-link px-2 link-light" aria-current="page" href="#">${userInfo.user_name}</a>
                </li>
            </ul>
            <%--<input style="background-image: url('admin/images/chat.png');  " type="button" class="img-button" onclick="alert('클릭!')">--%>
            <button id="chat_button" type="button" onclick="chat_button()">채팅</button>
            <%-- 채팅--%>
            <%-- 채팅--%>
            <div class="dropdown text-end">
                <a href="#" class="d-block link-body-emphasis text-decoration-none dropdown-toggle"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="https://github.com/mdo.png" alt="mdo" width="32" height="32" class="rounded-circle">
                </a>
                <ul class="dropdown-menu text-small" style="">
                    <li><a class="dropdown-item" href="mypage_main">내 정보 설정</a></li>
                    <li><hr class="dropdown-divider"></li>
                    <li><a class="dropdown-item" href="user_logout">로그아웃</a></li>
                </ul>
            </div>
            <div class="d-flex" role="search" style="margin-left:10px">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-secondary" type="submit">Search</button>
            </div>
        </div>
    </div>
</nav>

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
                                    <input onclick="chat_room()" type="button" class="btn btn-primary" value="채팅하기${chat_user.user_id}">
                                </div>
                            </div>
                        </c:forEach>
<%--            <c:forEach begin="0" end="11">--%>
<%--                <div id="chat_student_list">--%>
<%--                    <div id="chat_st_left">--%>
<%--                        <p>이미지</p>--%>
<%--                    </div>--%>
<%--                    <div id="chat_st_center">--%>
<%--                        <p>사용자명</p>--%>
<%--                    </div>--%>
<%--                    <div id="chat_st_right">--%>
<%--                        <input type="button" class="btn btn-primary" value="채팅하기">--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
        </div>
        <div id="chat_chats" style="display: none">
            <c:forEach begin="0" end="11">
                <div id="chat_chat_list">
                    <div id="chat_ch_left">
                        <p>이미지</p>
                    </div>
                    <div id="chat_ch_center">
                        <p>사용자명</p>
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

