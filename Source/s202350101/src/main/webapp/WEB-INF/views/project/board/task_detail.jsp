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

            <div style="border: 1px black solid;">
                <p>${task.task_id}  </p>
                <p>${task.task_subject}</p>
                <p>${task.task_subject}</p>
                <p>${task.task_stat_time}</p>
                <p>${task.task_stat_time}</p>
                <p>${task.task_stat_time}</p>
                <p>${task.task_end_itme}</p>
                <p>${task.task_priority}</p>
                <p>${task.task_status}</p>
            </div>
                    <a type="button"   class="btn btn-primary" href="task_garbage_view">task_garbage</a>
                    <a type="button"   class="btn btn-primary" href="task_update_view">task_update</a>

        </main>

    </div>
</div>


<div id="footer"></div>

</body>
</html>
