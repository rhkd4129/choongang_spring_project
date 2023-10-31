<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/header.jsp"%>


<form action="insert" method="post" class="border border-dark p-4">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="project_step_seq">프로젝트 단계 순서:</label>
                    <input type="text" class="form-control" name="project_step_seq" id="project_step_seq">
                </div>
                <div class="form-group">
                    <label for="task_subject">작업명:</label>
                    <input type="text" class="form-control" name="task_subject" id="task_subject">
                </div>
                <div class="form-group">
                    <label for="task_content">업무 내용:</label>
                    <input type="text" class="form-control" name="task_content" id="task_content">
                </div>
                <div class="form-group">
                    <label for="task_stat_time">Start date:</label>
                    <input type="date" class="form-control" id="task_stat_time" name="task_stat_time" value="2018-07-22" min="2018-01-01" max="2018-12-31" />
                </div>
                <div class="form-group">
                    <label for="task_end_time">task_end_time</label>
                    <input type="date" class="form-control" id="task_end_time" name="task_end_time" value="2018-07-22" min="2018-01-01" max="2018-12-31" />
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Status:</label>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="status" value="0" id="status0">
                        <label class="form-check-label" for="status0">예정된 작업</label>
                    </div>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="status" value="1" id="status1">
                        <label class="form-check-label" for="status1">진행중인 작업</label>
                    </div>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="status" value="2" id="status2">
                        <label class="form-check-label" for="status2">완료된 작업</label>
                    </div>
                </div>
                <div class="form-group">
                    <label>Priority:</label>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="priority" value="0" id="priority0">
                        <label class="form-check-label" for="priority0">낮음</label>
                    </div>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="priority" value="1" id="priority1">
                        <label class="form-check-label" for="priority1">보통</label>
                    </div>
                    <div class="form-check">
                        <input type="radio" class="form-check-input" name="priority" value="2" id="priority2">
                        <label class="form-check-label" for="priority2">높음</label>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">새 작업</button>

</form>
