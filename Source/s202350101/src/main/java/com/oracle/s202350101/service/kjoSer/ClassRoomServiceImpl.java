package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.kjoDao.ClassRoomDaoImpl;
import com.oracle.s202350101.model.ClassRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ClassRoomServiceImpl implements ClassRoomService{

	private final ClassRoomDaoImpl CRdao;
	
//	모든 ClassRoom 조회
	@Override
	public List<ClassRoom> findAllClassRoom() {
		List<ClassRoom> CRList = CRdao.findAllClassRoom();
		return CRList;
	}

//	ClassRoom	생성
	@Override
	public int saveClassRoom(ClassRoom cr) {
		return CRdao.saveClassRoom(cr);
	}

//	강의실 id를 기준으로 삭제
    @Override
    public int deletebyId(ClassRoom cr) {
		int result = 0;

		result += CRdao.deleteUsInfobyClassId(cr);
		result += CRdao.deletebyUsClassId(cr);
		result += CRdao.deletebyId(cr);

		return result;
    }


}
