package com.oracle.s202350101.dao.kjoDao;

import java.util.List;

import com.oracle.s202350101.model.ClassRoom;

public interface ClassRoomDao {

    //	모든 ClassRoom 조회
	List<ClassRoom> findAllClassRoom();

    //	ClassRoom	생성
    int saveClassRoom(ClassRoom cr);

    int deletebyId(ClassRoom cr);

    int deletebyUsClassId(ClassRoom cr);

    int deleteUsInfobyClassId(ClassRoom cr);
}
