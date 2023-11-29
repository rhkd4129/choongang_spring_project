package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import com.oracle.s202350101.model.ClassRoom;

public interface ClassRoomService {

//	모든 강의실 조회
	List<ClassRoom> findAllClassRoom();


//	ClassRoom	생성
	int saveClassRoom(ClassRoom cr);

//	강의실 id를 기준으로 삭제
	int deletebyId(ClassRoom cr);
}
