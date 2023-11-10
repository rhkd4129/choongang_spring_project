package com.oracle.s202350101.dao.kjoDao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.ClassRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ClassRoomDaoImpl implements ClassRoomDao{
	
	private final SqlSession session;

	//	모든 ClassRoom 조회
	@Override
	public List<ClassRoom> findAllClassRoom() {
		List<ClassRoom> CRList = null;
		log.info("findAllClassRoom start");
		try {
			CRList = session.selectList("findAllClassRoom");
			System.out.println(CRList.stream().collect(Collectors.toList()));
			log.info("findAllClassRoom finish");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return CRList;
		
	}

	//	ClassRoom	생성
	@Override
	public int saveClassRoom(ClassRoom cr) {
		log.info("saveClassRoom start");
		int result = 0;
		try {
			result = session.insert("saveClassRoom",cr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
