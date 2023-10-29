package com.oracle.s202350101.dao.kjoDao;

import java.util.List;

import com.oracle.s202350101.model.ClassRoom;

public interface ClassRoomDao {
	
	List<ClassRoom> findAllClassRoom();

    int saveClassRoom(ClassRoom cr);
}
