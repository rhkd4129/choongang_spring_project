<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<!DOCTYPE html>
<html>

<head>

<script type="text/javascript">
    $(function() {

        $.ajax({
            url			: '/main_header',
            dataType 	: 'text',
            success		: function(data) {
                console.log("ddd");
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

<div id="header"></div>

<div class="container-fluid">
    <div class="row">

        <!-- 메뉴 -->
        <div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
        </div>

        <!-- 본문 -->
        <main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                ${task.task_id}
                ${task.task_content}
        </main>

    </div>
</div>


<div id="footer"></div>

</body>
</html>
