package com.oracle.s202350101.service.kjoSer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.oracle.s202350101.model.KjoRequestDto;
import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.kjoDao.UserInfoDao;
import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoDao UIdao;

    @Override
    public List<UserInfo> findbyclassuser(int cl_id) {
        log.info("findbyclassuser start");
        List<UserInfo> UIList = UIdao.findbyclassuser(cl_id);
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

}
