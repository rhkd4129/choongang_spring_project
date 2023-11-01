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
        #chat_bottom{
            display:flex;

        }
        #chat_content{
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
    </script>
</head>


<!-- HEADER -->


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

</html>
