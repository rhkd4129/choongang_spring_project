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
	
	// select
//	@Override
//	public List<Todo> listTodoShow(String loginId) {
//		System.out.println("CyjServicelmplTodo listTodoShow Start..");
//		List<Todo> listTodo = cdt.listTodo(loginId);
//		System.out.println("CyjServicelmplTodo listTodo-> " + listTodo);
//		
//		return listTodo;
//	}

	
	
	
	

}
