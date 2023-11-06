package com.oracle.s202350101.controller;


import com.oracle.s202350101.model.AjaxResponse;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.Paging;
import com.oracle.s202350101.service.lkhSer.LkhService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AsynController {
    private final LkhService lkhService;


    @GetMapping("bar")
    public PrjInfo bar(HttpServletRequest request , Model model){
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        int projectId = userInfo.getProject_id();
        return lkhService.project_day(projectId);
    }


    @GetMapping("dashboard_doughnut")
    public List<Integer> dashboard(HttpServletRequest request) {
        UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");
        int id = userInfo.getProject_id();
        log.info("dashboard Controller init");
        List<Integer> taskStatusList = new ArrayList<>();
        taskStatusList =  lkhService.doughnut_chart(id);
        return taskStatusList;
    }


    @GetMapping("dashboard_bar")
    public List<Task> dashboard_bar(HttpServletRequest request) {
        UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");
        int id = userInfo.getProject_id();
        log.info("dashboard_bar Controller init");
        List<Task> taskUserWorkStatusList = new ArrayList<>();
        taskUserWorkStatusList =  lkhService.Workload_chart(id);
        return taskUserWorkStatusList;
    }

    @GetMapping("task_timeline")
    public List<Task> task_timeline(){
        return  lkhService.task_timeline();
    }
    ///// 대시 보드 홈 /////

    // 내림차순 올림차순 으로 보기
    @GetMapping("task_time_desc")
    public AjaxResponse task_time_desc(HttpServletRequest request, String currentPage){
        UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
        log.info("내림");
        int projectId = userInfo.getProject_id();
        Task task =  new Task();
        task.setProject_id(projectId);
        int taskCount = lkhService.task_count(projectId);
        Paging page = new Paging(taskCount, currentPage);
        task.setStart(page.getStart());
        task.setEnd(page.getEnd());
        List<Task>  taskList =  lkhService.task_time_decs(task);
        AjaxResponse data = new AjaxResponse();
        data.setOnelist(taskList);
        data.setOneObject(page);

        return data;
    }

    @GetMapping("task_time_acsc")
    public  AjaxResponse task_time_aces(HttpServletRequest request,String currentPage){
        log.info("올림");
        UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
        int projectId = userInfo.getProject_id();
        Task task =  new Task();
        task.setProject_id(projectId);
        int taskCount = lkhService.task_count(projectId);
        Paging   page = new Paging(taskCount, currentPage);
        task.setStart(page.getStart());
        task.setEnd(page.getEnd());

        List<Task>  taskList =  lkhService.task_time_aces(task);

        AjaxResponse data = new AjaxResponse();
        data.setOnelist(taskList);
        data.setOneObject(page);
        return data;
    }

    //휴지통 삭제 버튼
    @PostMapping("task_garbage")
    public int task_update(int task_id){
        log.info("휴지통 이동하기 비동기 함수 진입점 ");
        return lkhService.task_garbage(task_id);

    }





}
