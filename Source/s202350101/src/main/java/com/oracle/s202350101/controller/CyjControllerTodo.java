package com.oracle.s202350101.controller;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import com.oracle.s202350101.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.oracle.s202350101.service.cyjSer.CyjService;
import com.oracle.s202350101.service.cyjSer.CyjServiceTodo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// @Slf4j
@Controller
@RequiredArgsConstructor
@Slf4j
public class CyjControllerTodo {

	private final CyjService cs;
	private final CyjServiceTodo cst;

	@RequestMapping(value = "todo_list")
	public String todoList(Model model,HttpServletRequest request) {

		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		// loginId : tood 리스트 작성에서 접속자이자 작성자
		String loginId = userInfoDTO.getUser_id();
		System.out.println(request.getSession().getAttribute("loginId"));  // null로 나옴
		System.out.println("CyjControllerTodo todo_list Start----------------------");

		// 우선순위 분류 코드 가져오기
		Code code = new Code();
		code.setTable_name("TODO");
		code.setField_name("TODO_PRIORITY");

		// todo_우선순위 분류 코드 가져오기
		//############################################################
		List<Code> codeListView = cs.codeList(code);
		//############################################################
		System.out.println("CyjControllerTodo codeListView-> " + codeListView.size());  // 나옴
		model.addAttribute("codeListView",codeListView);
		return "todo/todo_list";
	}


	//	 리스트 보여주기 : 원본
	@ResponseBody
	@RequestMapping(value = "todo_list_select")
	public TodoModel todoListSelect(HttpServletRequest request) {
		System.out.println("CyjControllerTodo todo_list_select Start----------------------");
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

		String loginId = userInfoDTO.getUser_id();
		System.out.println("loginIdssss"+loginId);
		Todo todo = new Todo();
		todo.setUser_id(loginId);

		// 우선순위 분류 코드 가져오기
		Code code = new Code();
		code.setTable_name("TODO");
		code.setField_name("TODO_PRIORITY");
		// todo_우선순위 분류 코드 가져오기
		//######################################################################
		List<Code> codeList = cs.codeList(code);
		//######################################################################
		System.out.println("CyjControllerTodo codeList-> " + codeList.size());  // 나옴

		//############################################################
		List<Todo> todoDataList = cst.todoDate(todo);
		List<Todo> todoTotalSelect = cst.todoTotalSelect(todo);
		//############################################################
		for (Todo to : todoTotalSelect) {
			System.out.println(to.getTodo_list());
		}
		for (Todo to : todoDataList) {
			System.out.println(to.getOne_Day());
		}


		Map<Date, List<Todo>> mapData = new TreeMap<>(Comparator.reverseOrder());

		for (Todo to : todoDataList) {
			mapData.put(to.getOne_Day(), new ArrayList<>());
		}
		for (Date key : mapData.keySet()) {
			for (Todo to : todoTotalSelect) {
				// 시간을 무시하고 날짜만 비교
				if (to.getTodo_date().toLocalDate().equals(key.toLocalDate())) {
					List<Todo> values = mapData.get(key);
					if (values != null) {
						values.add(to);
					}
				}
			}
		}
		System.out.println("--------------------------------------------------------------");
		// Enhanced for loop를 사용하여 key와 value 출력
		for (Map.Entry<Date, List<Todo>> entry : mapData.entrySet()) {
			log.info("Key: {}, Value: {}", entry.getKey(), entry.getValue());
		}
		System.out.println("----------------------------------------------------------------");
		System.out.println("CyjControllerTodo todoTotalSelect-> " + todoTotalSelect);
		AjaxResponse ajaxResponse = new AjaxResponse();
		TodoModel todoModel = new TodoModel();
		todoModel.setMapData(mapData);
		todoModel.setOnelist(codeList);

		return todoModel;
	}



	// 리스트 입력
	@ResponseBody
	@PostMapping(value = "todo_list_insert")
	public int todoListInsert( @RequestParam("todo_list") String todoList,
							   @RequestParam("todo_priority") String todoPriority ,
							   @RequestParam("create_date" ) String create_date ,
							   HttpServletRequest request) {

		System.out.println("CyjControllerTodo todo_list_insert Start----------------------");
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		String loginId = userInfoDTO.getUser_id();

		Todo todo  =new Todo();
		java.sql.Date sqlDate = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
			java.util.Date utilDate = sdf.parse(create_date);
			sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		todo.setTodo_priority(Integer.parseInt(todoPriority));
		todo.setTodo_list(todoList);
		todo.setUser_id(loginId);
		todo.setTodo_date(sqlDate);

		//#################################################
		int todoInsert = cst.todoInsert(todo);
		//#################################################
		System.out.println("CyjControllerTodo todoInsert-> " + todoInsert);

		return todoInsert;
	}

	// 삭제 버튼 클릭하면 리스트 삭제
	@ResponseBody
	@PostMapping(value = "todo_list_delete")
	public int todoListDelete(int todo_no, String user_id) {
		System.out.println("CyjControllerTodo todo_list_delete Start---------------------");
		Todo todo = new Todo();
		todo.setUser_id(user_id);
		todo.setTodo_no(todo_no);
		//############################################
		int todoListDelete = cst.todoListDelete(todo);
		//############################################
		System.out.println("CyjControllerTodo todoListDelete-> " + todoListDelete);

		return todoListDelete;
	}

	// 할 일 완료
	@ResponseBody
	@PostMapping(value = "todo_list_todoCheck_y")
	public int todoListTodoCheckY(int todo_no, String user_id,boolean check){
		System.out.println("CyjControllerTodo todo_list_todoCheck_y Start---------------");

		Todo todo = new Todo();
		todo.setUser_id(user_id);
		todo.setTodo_no(todo_no);
		//############################################################
		// 각각의 todoList 갖고 옴
//		Todo oneRowTodo = cst.oneRowTodoList(todo);
//		//############################################################
//		System.out.println("CyjControllerTodo oneRowTodo-> " + oneRowTodo);
//		System.out.println(oneRowTodo.getTodo_check());
		int todoListTodoChange = 0;
		if (check == true) {
			// Y로 변경
			//############################################################
			todoListTodoChange = cst.todoListTodoCheckY(todo);
			//############################################################
			System.out.println("CyjControllerTodo todoListTodoCheckY-> " + todoListTodoChange);
		} else {
			// N으로 변경
			//############################################################
			todoListTodoChange = cst.todoListTodoCheckN(todo);
			//############################################################
			System.out.println("CyjControllerTodo todoListTodoCheckN-> " + todoListTodoChange);
		}
		return todoListTodoChange;
	}








}