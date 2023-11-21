package com.oracle.s202350101.dao.cyjDao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.Todo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CyjDaolmplTodo implements CyjDaoTodo {
	
	private final SqlSession session;

	
	// 리스트 입력
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

	// 리스트 보여주기
	@Override
	public List<Todo> todoTotalSelect(Todo todo) {
		System.out.println("CyjDaolmplTodo todoTotalSelect Start..");
		
		List<Todo> totalSelect = new ArrayList<Todo>();
		try {
			totalSelect = session.selectList("cyTodoTotalSelect", todo);
			System.out.println("CyjDaolmplTodo totalSelect.size()-> " + totalSelect.size());
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo totalSelect Exception-> " + e.getMessage());
		}
		return totalSelect;
	}

	// 삭제 버튼 클릭하면 리스트 삭제
	@Override
	public int todoListDelete(Todo todo) {
		System.out.println("CyjDaolmplTodo todoListDelete Start..");
		
		int todoListDelete = 0;
		try {
			todoListDelete = session.delete("cyTodoListDelete", todo);
			System.out.println("CyjDaolmplTodo todoListDelete-> " + todoListDelete);
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo todoListDelete Exception-> " + e.getMessage());
		}
		return todoListDelete;
	}

	
	
	
	// select
//	@Override
//	public List<Todo> listTodo(String loginId) {
//		System.out.println("CyjDaolmplTodo listTodo Start..");
//		
//		List<Todo> listTodo = null;
//		try {
//			listTodo = session.selectList("cyTodoListSelect", loginId);
//			System.out.println("CyjDaolmplTodo listTodo.size()-> " + listTodo.size());
//		} catch (Exception e) {
//			System.out.println("CyjDaolmplTodo listTodo Exception-> " + e.getMessage());
//		}
//		return listTodo;
//	}
	
	
	
	
	
}
