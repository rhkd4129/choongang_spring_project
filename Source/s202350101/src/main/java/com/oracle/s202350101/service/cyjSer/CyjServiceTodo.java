package com.oracle.s202350101.service.cyjSer;

import java.util.List;

import com.oracle.s202350101.model.Todo;

public interface CyjServiceTodo {

	int 			todoInsert(Todo todo);
	List<Todo> 		todoTotalSelect(Todo todo);
	int             todoListDelete(Todo todo);
	
//	List<Todo> 		listTodoShow(String loginId);
	

}
