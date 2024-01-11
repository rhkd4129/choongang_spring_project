## 📣 중앙인재개발원 1차 프로젝트 - PMS(Project Manager System)

<img width="1397" alt="프로젝트 메인" src="https://github.com/rhkd4129/sundo_project_23501a/assets/77871676/4f6bf9a3-cb4b-411f-9d44-e1f282d13994">

# 📖 프로젝트 개요
##  프로젝트 소개 

![시스템 프로세스](readme_image/project_summary_1.PNG)

### 시스템 프로세스
![시스템 프로세스](readme_image/SystemProcess.PNG)

###  프로젝트 기간
 🕛2023.10.10 - 2023.11.30

### 📚 개발환경

- <img src="https://img.shields.io/badge/Framework-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"><img src="https://img.shields.io/badge/3.2.0-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Build-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"><img src="https://img.shields.io/badge/8.5-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/Language-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"><img src="https://img.shields.io/badge/11-515151?style=for-the-badge">
- <img src="https://img.shields.io/badge/DATABASE-%23121011?style=for-the-badge">  <img src="https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white">
- <img src="https://img.shields.io/badge/front-%23121011?style=for-the-badge"><img src="https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white"><img src="https://img.shields.io/badge/css-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white"><img src="https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E"><img src="https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white"><img src="https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white">





## 프로젝트 팀원 및 역할
👑👩 팀장 황인정 (HIJ) : 프로젝트 생성, 프로젝트 관리, 관리자 페이지, 통합검색

🧑 팀원 이광현 (LKH) : 프로젝트 작업 문서 관리(생성/조회/수정/삭제) 및 다양한 시각화, 휴지통

👩 팀원 이진희 (LJH) : 프로젝트용 캘린더, 알림, 프로젝트 게시판

🧑 팀원 강준우 (KJO) : 채팅, 관리자 페이지

🧑 팀원 문경훈 (MKH) : 회원가입 및 권한 설정

👩 팀원 차예지 (CYJ) : 전체 공지사항/이벤트/Q&A 게시판, 검색기능, To Do List 등록/삭제

👩 팀원 조미혜 (JMH) : 프로젝트 메인, 프로젝트 Home, 프로젝트 게시판, 검색기능

## ERD 
![시스템 프로세스](readme_image/ERD.PNG)

## 🔭 나의 구현 기능

###  ❗ 나의 기능 간단 정리 
<img src="readme_image/my_erd.PNG" alt="myErd" width="800" height="400">

<ul>
  <li>팀장이 생성신청시 관리자가 승인하면 프로젝트가 만들어진다.</li>
  <li>그 안에서 프로젝트 단계별[ex) 분석/설계, 기능구현,테스트, 배포]등 작업 생성해 관리(수정,삭제,조회)</li>
  <li>하나의 작업에는 여러 공동작업자가 았을 수 있고 여러개의 파일을 첨부 할 수 있다.</li>
  <li> 이러한 작업들을 바탕으로 여러 그래프로 시각화로 한눈에 보여준다.
</ul>


### 🔥 작업목록 🔥  
![taskList](readme_image/taskList.PNG)

### 🔥 작업조회 🔥  
![taskDetail](readme_image/taskDetail.PNG)

### 🔥 작업생성 🔥  
 팀프로젝트 팀원들의 작업 생성시 요구 조건  
 ❗ 하나의 작업(Task Table)생성 시 여려 공동작업자(TaskSub Table)와 다수 첨부파일(Task Attach)의 생성이 있을 수 있다.  

 어러 테이블에 다중 Row를 Insert하는 상황 발생    ➡ 여러개의 DAO를 묶어서 한개의 서비스에 트랜잭션 처리
![taskCreate](readme_image/taskCreate.PNG)

 ### 작업 생성 프로세스

작업 생성시 3개의 테이블 공통적으로 project_id와 task_id가 필요(PK)   
  ➡ 그 중 project_id는 원래 있던 값을 가져와서 사용하면 되므로 문제  x

task_id 는 Task Table 에서 생성 시  MAX+1형식으로 생성 됨➡  이 값을 가져다 나머지 2개의 TABLE에 insert.

Task(Table)에 Insert할떄 max+1을 하고 그 이후 두개의 테이블은 max+1이 커밋 된 후 그 시점에  max값을 가져오면 같은 값을 가져오게 된다.

Task_sub(Table)는 체크박스 형식으로 user_id를 리스트로 입력받아  List<taskSub> 형식으로 받아서 순서대로 insert 

task_attach(Table)인 첨부파일 같은 경우는 pk가 project_id, task_id, attach_no  3개   파일이 리스트 형식으로 넘어온다.  
그냥 insert문을 3번 처리하여 할 수도 있지만,  다른 방법이 없을까 생각 하던 중  예를 들어 파일이 3개가 들어온다고 가정 시 현재 max 에 값 순서대로 max+1, max+2, max+3 처럼 1씩 커지게 java에서  for 문으로 
Pk 설정후 다중 insert 처리 
![taskCreate2](readme_image/taskCreate2.PNG)

### 🔥 작업수정 🔥  
![taskUpdate2](readme_image/taskUpdate2.PNG)
![taskUpdate](readme_image/taskUpdate.PNG)

### 🔥 작업삭제 및 휴지통  🔥  
![taskDelete](readme_image/taskDelete.PNG)


### 🔥 작업보드(대시보드) 🔥  


### 🔥 타임라인 🔥  
![taskDelete](readme_image/taskTimeLine.PNG)







## 📌 프로젝트 회고 


  
  








