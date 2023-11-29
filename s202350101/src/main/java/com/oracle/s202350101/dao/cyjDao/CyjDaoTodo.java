package com.oracle.s202350101.dao.cyjDao;

import java.util.List;

import com.oracle.s202350101.model.Todo;

public interface CyjDaoTodo {

	int				todoInsert(Todo todo);
	List<Todo>		todoTotalSelect(Todo todo);
	int				todoListDelete(Todo todo);

	int				todoListTodoCheckY(Todo todo);   // Y로 변경
	int 			todoListTodoCheckN(Todo todo);	 // N으로 변경 
	List<Todo>				todoDate(Todo todo); 		// 날자를 가여좀
	

}
