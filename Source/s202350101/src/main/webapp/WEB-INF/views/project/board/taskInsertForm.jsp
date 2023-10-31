<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form action="insert" method="post">

    <label for="project_step_seq">프로젝트 단계 순서:</label>
    <input type="text" name="project_step_seq" id="project_step_seq">

    <br>

    <label for="task_subject">작업명:</label>
    <input type="text" name="task_subject" id="task_subject">

    <br>

    <label for="task_content">업무 내용:</label>
    <input type="text" name="task_content" id="task_content">
    <br>

    <label for="task_stat_time">Start date:</label>
    <input type="date" id="task_stat_time" name="task_stat_time" value="2018-07-22" min="2018-01-01" max="2018-12-31" />
    <br>
    <label for="task_end_time">task_end_time </label>
    <input type="date" id="task_end_time" name="task_end_time" value="2018-07-22" min="2018-01-01" max="2018-12-31" />

    <br><br>


    <input type="radio" name="status" value="0">
    <label> 예정된 작업</label>
    <input type="radio" name="status" value="1">
    <label> 진행중인 작업</label>
    <input type="radio" name="status" value="2">
    <label> 완료된 작업</label>

    <br>
    <br>

    <input type="radio" name="priority" value="0">
    <label>낮음</label>
    <input type="radio" name="priority" value="1">
    <label>보통</label>
    <input type="radio" name="priority" value="2">
    <label>높음</label><br>

    <button type="submit">새작업</button>
</form>
