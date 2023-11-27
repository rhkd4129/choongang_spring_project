package com.oracle.s202350101.dao.kjoDao;

import java.util.List;

import com.oracle.s202350101.model.ClassRoom;

public interface ClassRoomDao {
    //	ClassRoom	생성
    int                     saveClassRoom(ClassRoom cr);
    //	모든 ClassRoom 조회
    List<ClassRoom>         findAllClassRoom();
    //	강의실 id를 기준으로 변경_USINFO
    int                     updateUsInfobyUsClassId(ClassRoom cr);


    //	강의실 id를 기준으로 삭제_Class
    int                     deletebyId(ClassRoom cr);

    //	강의실 id를 기준으로 삭제_UserInfo
    int                     deleteUsInfobyUsClassId(ClassRoom cr);

    //	강의실 id를 기준으로 삭제_TODO
    int                     deleteTodobyClassId(ClassRoom cr);

    //	강의실 id를 기준으로 삭제_USENV
    int                     deleteUsEnvbyClassId(ClassRoom cr);
}
