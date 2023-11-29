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

//	강의실 id를 기준으로 삭제_Class
    @Override
    public int deletebyId(ClassRoom cr) {
		log.info("deletebyId START");
		int result = 0;
		try {
			result = session.delete("deletebyId", cr);
			log.info("deletebyId {}",result);
		} catch (Exception e) {
            log.info("deletebyId ERROR : {}",e.getMessage());
        }
		return result;

    }

	//	강의실 id를 기준으로 삭제_UserInfo
	@Override
	public int deleteUsInfobyUsClassId(ClassRoom cr) {
		log.info("deleteUsInfobyUsClassId START");
		int result = 0;
		try {
			result = session.delete("deleteUsInfobyUsClassId", cr);
			log.info("deleteUsInfobyUsClassId {}",result);
		} catch (Exception e) {
			log.info("deleteUsInfobyUsClassId ERROR : {}",e.getMessage());
		}
		return result;
	}

	//	강의실 id를 기준으로 삭제_TODO
	@Override
	public int deleteTodobyClassId(ClassRoom cr) {
		log.info("deleteTodobyClassId START");
		int result = 0;
		try {
			result = session.delete("deleteTodobyClassId", cr);
			log.info("deleteTodobyClassId {}",result);
		} catch (Exception e) {
			log.info("deleteTodobyClassId ERROR : {}",e.getMessage());
		}
		return result;
	}

	//	강의실 id를 기준으로 삭제_USENV
	@Override
	public int deleteUsEnvbyClassId(ClassRoom cr) {
		log.info("deleteUsEnvbyClassId START");
		int result = 0;
		try {
			result = session.delete("deleteUsEnvbyClassId", cr);
			log.info("deleteUsEnvbyClassId {}",result);
		} catch (Exception e) {
			log.info("deleteUsEnvbyClassId ERROR : {}",e.getMessage());
		}
		return result;
	}

	@Override
	public int updateUsInfobyUsClassId(ClassRoom cr) {
		log.info("updateUsInfobyUsClassId START");
		int result = 0;
		try {
			result = session.update("updateUsInfobyUsClassId", cr);
			log.info("updateUsInfobyUsClassId {}",result);
		} catch (Exception e) {
			log.info("updateUsInfobyUsClassId ERROR : {}",e.getMessage());
		}
		return result;
	}


}
