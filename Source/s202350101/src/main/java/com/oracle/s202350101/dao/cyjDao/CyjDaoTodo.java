package com.oracle.s202350101.dao.cyjDao;

import java.util.List;

import com.oracle.s202350101.model.Todo;

public interface CyjDaoTodo {

	int 			todoInsert(Todo todo);
	List<Todo> 		todoTotalSelect(Todo todo);
	int             todoListDelete(Todo todo);
	
	
//	List<Todo> 		listTodo(String loginId);

}
