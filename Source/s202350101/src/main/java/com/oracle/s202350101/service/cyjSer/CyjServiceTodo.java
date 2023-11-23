package com.oracle.s202350101.service.cyjSer;

import java.util.List;

import com.oracle.s202350101.model.Todo;

public interface CyjServiceTodo {

	int 			todoInsert(Todo todo);
	List<Todo>		todoTotalSelect(Todo todo);
	int				todoListDelete(Todo todo);
	Todo 			oneRowTodoList(Todo todo);		// 각각의 todoList 갖고 옴

	int				todoListTodoCheckY(Todo todo);	// Y로 변경
	int				todoListTodoCheckN(Todo todo);	// N으로 변경




	List<Todo>				todoDate(Todo todo); 		// 날자를 가여좀
}
