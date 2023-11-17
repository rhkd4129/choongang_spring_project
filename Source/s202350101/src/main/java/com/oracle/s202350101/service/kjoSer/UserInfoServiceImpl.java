package com.oracle.s202350101.service.kjoSer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.KjoResponse;
import com.oracle.s202350101.model.Paging;
import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.kjoDao.UserInfoDao;
import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoDao UIdao;

    @Override
    public UserInfo findbyuserId(UserInfo userInfo) {
        log.info("findbyuserId start ID : " + userInfo.getUser_id());
        userInfo = UIdao.findbyuserId(userInfo);
        return userInfo;
    }

    @Override
    public List<UserInfo> findbyclassuser(UserInfo ui) {
        log.info("findbyclassuser start");
        List<UserInfo> UIList = UIdao.findbyclassuser(ui);
        return UIList;
    }

    //<!--어드민 제외 사용자 정보, 사용자 참여 프로젝트 조회  ++  페이징 추가-->
    public KjoResponse pageUserInfov2(UserInfo userInfo, String currentPage ) {
        KjoResponse kjo = new KjoResponse();
        //	페이징을 하기 위한 START, END,	TOTAL 지정.
        Paging page = new Paging(userInfo.getTotal(), currentPage);
        userInfo.setStart(page.getStart());
        userInfo.setEnd(page.getEnd());
        kjo.setFirList(UIdao.pageUserInfo(userInfo));
        kjo.setObj(page);

        return kjo;
    }

    //<!--특정 강의실 내 어드민 제외 사용자 조회 & 채팅 사용-->
    @Override
    public List<UserInfo> findbyClassUserAndChatEnv(UserInfo userInfo) {
        log.info("findbyclassuser start");
        List<UserInfo> UIList = UIdao.findbyClassUserAndChatEnv(userInfo);
        return UIList;
    }


    @Override
    public List<UserInfo> findbyClassUserProject(int cl_Id) {
        log.info("findbyClassUserProject start");
        List<UserInfo> UIList = UIdao.findbyClassUserProject(cl_Id);
        return UIList;

    }

    //  팀장 권한 수정
    @Override
    public int auth_modify(KjoRequestDto kjorequest) {
        List<String> user_ids = kjorequest.getUser_id();    //  userid 리스트 저장
        
        List<String> user_auths = kjorequest.getUser_auth();    //  user_auth 리스트 저장

        //  팀장, 학생으로    나눠서 저장.
        List<String> user_manager = new ArrayList<>();
        List<String> user_student = new ArrayList<>();

        for (int i=0; i<user_ids.size(); i++) {
            String user_auth = user_auths.get(i);       //  권한
            String user_id = user_ids.get(i);           //  user PK: id
            if (user_auth.equals("manager")) {
                user_manager.add(user_id);
            } else user_student.add(user_id);
        }
        //  쿼리문에서 foreach와 case를 통해 하기보다
        //  java에서 하기로 선택.
        int manager_cnt = UIdao.auth_modify_manager(user_manager);
        int student_cnt = UIdao.auth_modify_student(user_student);
        log.info("manager_cnt: {} student_cnt: {}",manager_cnt,student_cnt);

        return manager_cnt+student_cnt;
    }

    @Override
    public List<UserInfo> pageUserInfo( UserInfo userInfo) {

        log.info("pageUserInfo start");
        List<UserInfo> UI1 = UIdao.pageUserInfo(userInfo);
        log.info("TOT cnt : => " + UI1.size());
        return UI1;
    }

}
