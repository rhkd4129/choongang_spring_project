package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.kjoDao.ClassRoomDaoImpl;
import com.oracle.s202350101.model.ClassRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassRoomServiceImpl implements ClassRoomService{

	private final ClassRoomDaoImpl CRdao;
	
	@Override
	public List<ClassRoom> findAllClassRoom() {
		List<ClassRoom> CRList = CRdao.findAllClassRoom();
		return CRList;
	}
	

}
