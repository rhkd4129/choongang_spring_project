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

	// 각각의 todoList 갖고 옴
	@Override
	public Todo oneRowTodoList(Todo todo) {
		System.out.println("CyjDaolmplTodo oneRowTodoList Start..");

		Todo oneRowTodoList = null;
		try {
			oneRowTodoList = session.selectOne("cyRowTodoList", todo);
			System.out.println("CyjDaolmplTodo oneRowTodoList-> " + oneRowTodoList);
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo oneRowTodoList Exception-> " + e.getMessage());
		}
		return oneRowTodoList;
	}



	// 할 일 완료 -> Y로 변경
	@Override
	public int todoListTodoCheckY(Todo todo) {
		System.out.println("CyjDaolmplTodo todoListTodoCheckY Start..");

		int todoListTodoCheckY = 0;
		try {
			todoListTodoCheckY = session.update("cyTodoListTodoCheckY", todo);
			System.out.println("CyjDaolmplTodo todoListTodoCheckY-> " + todoListTodoCheckY);
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo todoListTodoCheckY Exception-> " + e.getMessage());
		}
		return todoListTodoCheckY;
	}

	// N으로 변경
	@Override
	public int todoListTodoCheckN(Todo todo) {
		System.out.println("CyjDaolmplTodo todoListTodoCheckN Start..");

		int todoListTodoCheckN = 0;
		try {
			todoListTodoCheckN = session.update("cyTodoListTodoCheckN", todo);
			System.out.println("CyjDaolmplTodo todoListTodoCheckN-> " + todoListTodoCheckN);
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo todoListTodoCheckN Exception-> " + e.getMessage());
		}
		return todoListTodoCheckN;
	}





	@Override
	public List<Todo> todoDate(Todo todo) {

		List<Todo> todoDateList = null;
		try {
			todoDateList = session.selectList("todoDatecount", todo);
			System.out.println("CyjDaolmplTodo todoDate-> " + todo);
		} catch (Exception e) {
			System.out.println("CyjDaolmplTodo todoDate Exception-> " + e.getMessage());
		}
		return todoDateList;
	}









}
