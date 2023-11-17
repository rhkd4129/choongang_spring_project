package com.oracle.s202350101.dao.cyjDao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.Todo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CyjDaolmplTodo implements CyjDaoTodo {
	
	private final SqlSession session;

	
	// insert
	@Override
	public int todoInsert(Todo todo) {
		System.out.println("CyjDaolmplTodo todoInsert Start..");
		
		int todoInsert = 0;
		try {
			todoInsert = session.insert("cyTodoListInsert", todo);
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo todoInsert Exception-> " + e.getMessage());
		}
		return todoInsert;
	}
	
	
	
	
	
}
