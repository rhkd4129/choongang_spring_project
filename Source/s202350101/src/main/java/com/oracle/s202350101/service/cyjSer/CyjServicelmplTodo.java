package com.oracle.s202350101.service.cyjSer;

import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.cyjDao.CyjDao;
import com.oracle.s202350101.dao.cyjDao.CyjDaoTodo;
import com.oracle.s202350101.model.Todo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CyjServicelmplTodo implements CyjServiceTodo {
	
	private final CyjDaoTodo cdt;

	
	// insert
	@Override
	public int todoInsert(Todo todo) {
		System.out.println("CyjServicelmplTodo todoInsert Start..");
		int todoInsert = cdt.todoInsert(todo);
		System.out.println("CyjServicelmplTodo todoInsert-> " + todoInsert);
		
		return todoInsert;
	}
	
	
	
	

}
