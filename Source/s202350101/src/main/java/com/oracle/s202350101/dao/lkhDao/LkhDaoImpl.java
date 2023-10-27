package com.oracle.s202350101.dao.lkhDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.Task;

import lombok.RequiredArgsConstructor;


@Repository
@RequiredArgsConstructor
public class LkhDaoImpl implements LkhDao {
	private final SqlSession sqlSession;
	
	@Override
	public List<Task> work_status() {
		List<Task> teamWorkStats= null;
		Map<String, String> teamwork = null;
		
		System.out.println("Aa");
		try {
			teamWorkStats = sqlSession.selectList("teamWorkStats");
		
			for(Task  task:teamWorkStats) {
//					if(task =='1') {
//						
//					}
				}
			
			
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return teamWorkStats;
		}
	}
