package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import com.oracle.s202350101.model.ClassRoom;

public interface ClassRoomService {

//	모든 강의실 조회
	List<ClassRoom> findAllClassRoom();


//	ClassRoom	생성
	int saveClassRoom(ClassRoom cr);
}
