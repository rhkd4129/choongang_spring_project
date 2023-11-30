package com.oracle.s202350101.controller;


import com.oracle.s202350101.model.AjaxResponse;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.service.lkhSer.LkhService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/*******************************************
 **   비동기 만 모아 놓은 컨트롤러             **
 *******************************************/
@RestController
@RequiredArgsConstructor
@Slf4j
public class LkhAsynController {
    private final LkhService lkhService;


    /*****************************************************************
     **    대시보드 홈  여기는 그래프 그려주는 메서드 /dashboard/         ****
     ****************************************************************/
    // 총 프로젝트 기간 그래프
    @GetMapping("project_day")
    public PrjInfo project_day(HttpServletRequest request , Model model){
        log.info("project_day AsynController init");

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

        /*****************************************************/
        // 현재 유저의 프로젝트 기간 정보를 리턴해줌
        return lkhService.project_day(userInfo.getProject_id());
        /*****************************************************/
    }

    // 도넛 그래프
    @GetMapping("doughnut_chart")
    public List<Integer> doughnut_chart(HttpServletRequest request) {
        log.info("doughnut_chart AsynController init");

        UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");

        /*****************************************************/
        return lkhService.doughnut_chart(userInfo.getProject_id());
        //현재 프로젝트의 작업에 작업상태(예정, 짆랭중, 완료됨)별로 합계를 구하여 넘겨줌
        /*****************************************************/
    }

    // 인원별  진척률 그래프
    @GetMapping("workload_chart")
    public List<Task> workload_chart(HttpServletRequest request) {
        log.info("workload_chart AsynController init");

        UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");

        /*****************************************************/
        //현재 프로젝트의 인원별 각 작업의 상태를 구하여 넘겨줌
        return lkhService.workload_chart(userInfo.getProject_id());
        /*****************************************************/
    }

    //프로젝트 단계별 그래프
    @GetMapping("project_step_chart")
    public AjaxResponse project_step_chart(HttpServletRequest request){
        log.info("project_step_chart AsynController init");

        UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");


        //serivce에서 다 필터링된 데이터만 받아서 리턴  projectStepData map형태이고 각 프로젝트 단계별 작업들의 정보가 존재
        // serivce에 로직이 구현되어 잇음
        /**********************************************************************************/
        AjaxResponse projectStepData  =lkhService.project_step_chart(userInfo.getProject_id());
        /**********************************************************************************/
        return projectStepData;



    }
    /********************************************
     **********      대시보드 페이지 끝       ******
     *******************************************/




    /*****************************************************************************
     ****   작업 타임라인 페이지 들어가자마자 ajax로  뿌리기 /task_timeline/       ******
     ****************************************************************************/
    @GetMapping("task_timeline_asyn")
    public List<Task> task_timeline(String timeline_type, HttpServletRequest request){
        log.info("task_timeline AsynController init");

        UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
        /********************************************************************************/
        //2023-11-29 수정함 by jmh
        if(timeline_type == null) {
        	timeline_type = "by_step"; //기본-단계별
        }else {
        	if(timeline_type.equals("")) {
        		timeline_type = "by_step"; //기본-단계별
        	}
        }        
        return  lkhService.task_timeline(userInfo.getProject_id(), timeline_type);
        // serivce에 로직이 구현되어 잇음
        /********************************************************************************/
    }


    /*****************************************************************************
     **** 작업 목록에서 마감일 기준으로 누르면 내림차순 올림차순 으로 보기 /task_list/ ******
     ****************************************************************************/
    //
    @GetMapping("task_time_desc")
    public AjaxResponse task_time_desc(HttpServletRequest request, String currentPage,String keyword_division , String keyword){
        log.info("task_time_desc AsynController init");


        UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");

        int projectId = userInfo.getProject_id();
        Task task =  new Task();
        task.setProject_id(projectId);
        task.setKeyword_division(keyword_division);
        task.setKeyword(keyword);
        /********************************************************/
        int taskCount = lkhService.task_count(task);
        /********************************************************/
        Paging page = new Paging(taskCount, currentPage);
        task.setStart(page.getStart());
        task.setEnd(page.getEnd());

        /******************************************************/
        List<Task>  taskList =  lkhService.task_time_decs(task);
        System.out.println("--=------------------------------------------");
        System.out.println(taskList);
        /****************************************************/

        AjaxResponse data = new AjaxResponse();
        data.setOnelist(taskList);
        data.setOneObject(page);

        return data;
    }

    @GetMapping("task_time_acsc")
    public  AjaxResponse task_time_aces(HttpServletRequest request,String currentPage,String keyword_division , String keyword){
        log.info("task_time_aces AsynController init");


        UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
        int projectId = userInfo.getProject_id();
        Task task =  new Task();
        task.setProject_id(projectId);
        task.setKeyword_division(keyword_division);
        task.setKeyword(keyword);

        /****************************************************/
        int taskCount = lkhService.task_count(task);
        /****************************************************/

        Paging   page = new Paging(taskCount, currentPage);
        task.setStart(page.getStart());
        task.setEnd(page.getEnd());

        /****************************************************/
        List<Task>  taskList =  lkhService.task_time_aces(task);
        /****************************************************/
        System.out.println("--=------------------------------------------");
        System.out.println(taskList);

        AjaxResponse data = new AjaxResponse();
        data.setOnelist(taskList);
        data.setOneObject(page);
        return data;
    }

    //휴지통으로넣기
    //#######################################################################
    //############   작업 삭제시키기 -> 휴지통으로 이동 /task_garbage/      #######
    //#######################################################################
    @PostMapping("task_garbage")
    public int task_garbage(int task_id, int project_id){
        log.info("task_garbage AsynController init");

        Task task = new Task();
        task.setTask_id(task_id);
        task.setProject_id(project_id);
        /****************************************************/
        int result = lkhService.task_garbage(task);
        /****************************************************/
        log.info("result-> {}",result);
        return result;

    }

    // 휴지통에서 영구삭제시키기

    //#######################################################################
    //############   휴지통에서 영구 삭제시키기 /task_delete/          #######
    //#######################################################################
    @PostMapping("task_delete")
    public int task_delete(int task_id,  int project_id){
        log.info("task_delete AsynController init");
        Task task = new Task();
        task.setProject_id(project_id);
        task.setTask_id(task_id);
        /****************************************************/
        int result = lkhService.task_delete(task);
        /****************************************************/
        log.info("result-> {}",result);
        return result;
    }

    //휴지통에서 복구 시키기


    //#######################################################################
    //############   휴지통에서 복구 버튼 누를 시 /task_restore/          #######
    //#######################################################################
    @PostMapping("task_restore")
    public int task_restore(int task_id, int project_id){
        log.info("task_restore  ctr: task_id :{}  proejct_id :{} ",task_id,project_id);
        Task task = new Task();
        task.setProject_id(project_id);
        task.setTask_id(task_id);
        /****************************************************/
        int result = lkhService.task_restore(task);
        /****************************************************/
        log.info("result-> {}",result);
        return  result;
    }



}