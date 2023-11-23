package com.oracle.s202350101.service.cyjSer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.cyjDao.CyjDao;
import com.oracle.s202350101.dao.cyjDao.CyjDaoTodo;
import com.oracle.s202350101.model.Todo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CyjServicelmplTodo implements CyjServiceTodo {

	private final CyjDaoTodo cdt;


	// 리스트 입력
	@Override
	public int todoInsert(Todo todo) {
		System.out.println("CyjServicelmplTodo todoInsert Start..");
		int todoInsert = cdt.todoInsert(todo);
		System.out.println("CyjServicelmplTodo todoInsert-> " + todoInsert);

		return todoInsert;
	}

	// 리스트 보여주기
	@Override
	public List<Todo> todoTotalSelect(Todo todo) {
		System.out.println("CyjServicelmplTodo todoTotalSelect Start..");
		List<Todo> todoTotalSelect = cdt.todoTotalSelect(todo);
		System.out.println("CyjServicelmplTodo todoTotalSelect-> " + todoTotalSelect);

		return todoTotalSelect;
	}

	// 삭제 버튼 클릭하면 리스트 삭제
	@Override
	public int todoListDelete(Todo todo) {
		System.out.println("CyjServicelmplTodo todoListDelete Start..");
		int todoListDelete = cdt.todoListDelete(todo);
		System.out.println("CyjServicelmplTodo todoListDelete-> " + todoListDelete);

		return todoListDelete;
	}

	// 각각의 todoList 갖고 옴
	@Override
	public Todo oneRowTodoList(Todo todo) {
		System.out.println("CyjServicelmplTodo oneRowTodoList Start..");
		Todo oneRowTodoList = cdt.oneRowTodoList(todo);
		System.out.println("CyjServicelmplTodo oneRowTodoList-> " + oneRowTodoList);

		return oneRowTodoList;
	}

	// 달력에서 날짜 클릭하면 해당 날짜 리스트 나옴
//	@Override
//	public List<Todo> clickList(Todo todo) {
//		System.out.println("CyjServicelmplTodo clickList Start..");
//		List<Todo> clickList = cdt.clickList(todo);
//		System.out.println("CyjServicelmplTodo clickList-> " + clickList);
//
//		return clickList;
//	}

	// Y로 변경
	@Override
	public int todoListTodoCheckY(Todo todo) {
		System.out.println("CyjServicelmplTodo todoListTodoCheckY Start..");
		int todoListTodoCheckY = cdt.todoListTodoCheckY(todo);
		System.out.println("CyjServicelmplTodo todoListTodoCheckY-> " + todoListTodoCheckY);

		return todoListTodoCheckY;
	}

	// N으로 변경
	@Override
	public int todoListTodoCheckN(Todo todo) {
		System.out.println("CyjServicelmplTodo todoListTodoCheckN Start..");
		int todoListTodoCheckN = cdt.todoListTodoCheckN(todo);
		System.out.println("CyjServicelmplTodo todoListTodoCheckN-> " + todoListTodoCheckN);

		return todoListTodoCheckN;
	}


	@Override
	public List<Todo> todoDate(Todo todo) {
		List<Todo> todoDateList = null;
		try {
			todoDateList = cdt.todoDate(todo);
			System.out.println("CyjDaolmplTodo todoDate-> " + todo);
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo todoDate Exception-> " + e.getMessage());
		}
		return todoDateList;
	}









}
