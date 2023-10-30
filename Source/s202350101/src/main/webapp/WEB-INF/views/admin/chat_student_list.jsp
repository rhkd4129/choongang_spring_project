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
            width: 30%;
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

        #chat_student_list {

            display: flex;
            justify-content: space-evenly;
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

        function chat_button() {
            var con = document.getElementById("chatbox");
            if (con.style.display == 'none') {
                con.style.display = 'flex';
            } else {
                con.style.display = 'none';
            }
        }
    </script>
</head>

<body>

<!-- HEADER -->
<header id="header"></header>


<!-- CONTENT -->
<div class="container-fluid">
    <div class="row">

        <!-- 메뉴 -->
        <div id="menubar"
             class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <!------------------------------ //개발자 소스 입력 START ------------------------------->

            <div id="chatbox">
                <div id="chat_top">
                    <p>학생 목록</p>
                </div>
                <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">
                    <c:forEach begin="0" end="11">
                        <div id="chat_student_list">
                            <div id="chat_st_left">
                                <p>이미지</p>
                            </div>
                            <div id="chat_st_center">
                                <p>사용자명</p>
                            </div>
                            <div id="chat_st_right">
                                <input type="button" class="btn btn-primary" value="채팅하기">
                            </div>
                        </div>
                    </c:forEach>
                </div>

            </div>
            <!------------------------------ //개발자 소스 입력 END ------------------------------->
        </main>

    </div>
</div>

<!-- FOOTER -->
<footer class="footer py-2">
    <div id="footer" class="container"></div>
</footer>


</body>

</html>
