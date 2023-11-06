<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta  charset="UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        var rows=[];
        $(function() {
            var container = document.getElementById("timeline");
            $.ajax({
                url			: '/main_header',
                dataType 	: 'text',
                success		: function(data) {
                    $('#header').html(data);
                }
            });

            $.ajax({
                url			: '/main_menu',
                dataType 	: 'text',
                success		: function(data) {
                    $('#menubar').html(data);
                }
            });


            $.ajax({
                url			: '/main_footer',
                dataType 	: 'text',
                success		: function(data) {
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
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">






            <!------------------------------ //개발자 소스 입력 END ------------------------------->
        </main>

    </div>
</div>

<!-- FOOTER -->
<footer class="footer py-2">
    <div id="footer" class="container">
    </div>
</footer>

</body>
</html>
