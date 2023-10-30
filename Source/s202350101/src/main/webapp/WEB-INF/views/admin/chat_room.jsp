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
        #chat_bottom{
            display:flex;

        }
        #chat_content{
            height: 400px;
            width: 90%;
            background-color: yellow;
            overflow: scroll;
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
                    <p>상대방 이름</p>
                </div>
                <div id="chat_content" class="bg-body-tertiary p-3 rounded-2">

                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>
                    <p>d</p>


                </div>
                <div id="chat_bottom">
                    <textarea cols="40" rows="3">
                    </textarea>
                    <input type="submit" class="btn btn-primary" value="작성완료">
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
