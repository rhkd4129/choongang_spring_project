<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header_main.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!--CSS START -->
<!-- CSS END -->

<!-- JS START -->


<!-- JS END -->

<script type="text/javascript">
	$(function() {
		
		$.ajax({
			url			: '/main_header',
			dataType 	: 'html',
			success		: function(data) {
				$('#header').html(data);
			}
		});
		
		$.ajax({
			url			: '/main_menu',
			dataType 	: 'html',
			success		: function(data) {
				$('#menubar').html(data);
			}
		});
	
		$.ajax({
			url			: '/main_footer',
			dataType 	: 'html',
			success		: function(data) {
				$('#footer').html(data);
			}
		});
		
		status_check();
	});
	
	// 프로젝트 진행상태 버튼(시작, 종료)
	function status_check(){
		if(${prjInfo.project_status} == '0'){
			$("#startBtn").show();
		}else if(${prjInfo.project_status} == '1'){
			$("#startBtn").hide();
			$("#endBtn").show();	
		}else{
		}
	}
	
	// 프로젝트 정보 수정
	function list_up(){
		$.ajax({
			url			: '/prj_mgr_req_edit?project_id=${prjInfo.project_id}',
			dataType 	: 'html',
			success		: function(data) {
				$('#divPrjInfo').html(data);
			}
		});
	}

	
	// 프로젝트 시작 버튼
	function prj_start(){
		
		if (${prjInfo.project_approve} === 2){
			var params = {};
			params.project_id = ${prjInfo.project_id};
			params.project_status = 1;
			$.ajax({
				url			: "/prj_status_bnt",
				type      	: "POST",
			    contentType : "application/json; charset=utf-8",
			    data       	: JSON.stringify(params),
			    dataType    : "text",
			    success		: function(data) {
					if(data == "success") {
						$('#idPrjStauts').html("진행중");
						$("#startBtn").hide();
						$("#endBtn").show();
					} else if(data == "fail"){
						alert("프로젝트 진행상태가 변경되지 않았습니다. 관리자에 문의하세요");
					}
				},
			    error: function(xhr, status, error){
			    	alert("에러");
			    	console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText); 
			    }
			});	
		}else if(${prjInfo.project_approve} === 1){
			alert("프로젝트 승인 전 입니다. 관리자에게 문의하세요");
			location.href="/prj_mgr_step_list";
		}
	}
		
	
	// 프로젝트 종료 버튼
	function prj_end(){
		var params = {};
		params.project_id = ${prjInfo.project_id};
		params.project_status = 2;
		$.ajax({
			url			: "/prj_status_bnt",
			type      	: "POST",
		    contentType : "application/json; charset=utf-8",  
		    data       	: JSON.stringify(params),
		    dataType    : "text",
			success		: function(data) {
				if(data == "success") {
					$('#idPrjStauts').html("완료");
					$("#startBtn").hide();
					$("#endBtn").hide();
				}else if(data == "fail"){
					alert("프로젝트 진행상태가 변경되지 않았습니다. 관리자에 문의하세요");
				}
			},
		    error: function(xhr, status, error){
		    	alert("에러");
		    	console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText); 
		    }
		});			
	}
	// 프로젝트 단계 설정
    function prj_order() {
    	
    	if(checkDuplication("id","project_order")){
    		alert("단계가 중복됩니다. 다시 설정하십시오.");
    		return false;
    	}
		
        var prjStepArray = [];
		var stepLenth = "${titleListCount}";

		
		for(var i=1; i<=parseInt(stepLenth); i++){
			var prjStep = {};
			prjStep.project_id 			= "${prjInfo.project_id}";
			prjStep.project_step_seq 	= $("#Step_"+i.toString()).text();
			prjStep.project_order		= $("select[name=project_order_"+i.toString()+"] option:selected").val();
			prjStepArray.push(prjStep);
		}
				
		// 컨트롤러에 전송할 주소
 
         $.ajax({
             url		: "/prj_order_update",
             type		: "POST",
             data		: JSON.stringify(prjStepArray),
             contentType: "application/json",		//서버로 JSON 데이터를 전송
          	 dataType	: "json",					//	서버로부터	json 데이터 받음
             success	: function (response) {
		                 // 서버에서 받은 JSON 응답을 처리
		                 alert("단계설정 완료" + response);
		                 location.href="/prj_mgr_step_list";
             }
         });
		
     }

	// 단계선택 비교
	 function checkDuplication(type, objName){
		var temp = [];
		var obj = $('select['+type+'='+objName+']');
		var result = false;
		
		$(obj).each(function(i){
			temp[i]=$(this).val();
		});
		
		$(temp).each(function(i){
			var x=0;
			$(obj).each(function(){
				if(temp[i] == $(this).val()){
					x++;
				}
			});
			if(x>1){
				result=true;
			}
		});
		return result;		
	}
	
</script>
</head>
<body>

<!-- HEADER -->
<header id="header"></header>

<!-- CONTENT -->
<div class="container-fluid">
	<div class="row">
 		
 		<!-- 메뉴 -->
		<div id="menubar" class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
		</div>
		
		<!-- 본문 -->
		<main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
			<!------------------------------ //개발자 소스 입력 START ------------------------------->
			<svg xmlns="http://www.w3.org/2000/svg" class="d-none">
			  <symbol id="house-door-fill" viewBox="0 0 16 16">
			    <path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.5a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5v-7a.5.5 0 0 0-.146-.354L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.354 1.146a.5.5 0 0 0-.708 0l-6 6A.5.5 0 0 0 1.5 7.5v7a.5.5 0 0 0 .5.5h4a.5.5 0 0 0 .5-.5z"></path>
			  </symbol>
			</svg>		
			<nav aria-label="breadcrumb" style="padding-top:5px;padding-left: calc(var(--bs-gutter-x) * 0.5);">
			    <ol class="breadcrumb breadcrumb-chevron p-1">
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis" href="/main">
			          <svg class="bi" width="16" height="16"><use xlink:href="#house-door-fill"></use></svg>
			          <span class="visually-hidden">Home</span>
			        </a>
			      </li>
			      <li class="breadcrumb-item">
			        <a class="link-body-emphasis fw-semibold text-decoration-none" href="">프로젝트</a>
			      </li>
			      <li class="breadcrumb-item active" aria-current="page">프로젝트 정보</li>
			    </ol>
			</nav>
			<div class="container-fluid">
				<div style="margin-top:15px;height:45px">
					<span class="apptitle">프로젝트 정보</span>
				</div>
			</div>
			
		   	<c:if test="${userInfoDTO.user_auth != 'manager' }">
					<script type="text/javascript">
						alert("팀장 권한이 없습니다. 관리자에게 문의하세요");
						location.href = "/main";
					</script>
			</c:if>
		
			<c:if test="${ProjectNotFound == 'TRUE'}">
					<script type="text/javascript">
						alert("프로젝트가 존재하지 않습니다");
						location.href = "/main";
					</script>
			</c:if>
	
			<div class="container" style="margin-left:0px">
				<div id="divPrjInfo">
					<hr>
					<input type="hidden" name="project_id" value="${prjInfo.project_id }">
					<input type="hidden" name="project_approve" value="${prjInfo.project_approve}" >
					<table class="table">
						<colgroup>
						<col width="150">
						<col width="*">
						</colgroup> 
						<tr>
							<th>프로젝트명</th>
							<td>${prjInfo.project_name}</td>
						</tr>
						<tr>
							<th>프로젝트 기간</th>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${prjInfo.project_startdate}"/> ~ 
								<fmt:formatDate pattern="yyyy-MM-dd" value="${prjInfo.project_enddate}"/></td>
						</tr>
						<tr>
							<th>프로젝트 팀장</th>
							<td>${prjInfo.project_manager_name}</td>
						</tr>
						<tr>
							<th>프로젝트 팀원</th>
							<td><c:forEach var = "member" items="${listMember}" >
									<c:if test="${userInfoDTO.user_id != member.user_id}">
									<input type="hidden" value="${member.user_id}"> 
									<label>${member.user_name }</label>
									</c:if>
					      		</c:forEach></td>
						</tr>					
						<tr>
							<th>프로젝트 소개</th>
							<td>${prjInfo.project_intro}</td>
						</tr>
						<tr>
							<th>승인상태</th>
							<td>${prjInfo.project_approve_name}</td>
						</tr>		
						<tr>
							<th>진행상태</th>
							<td>
								<span id="idPrjStauts">
									<c:if test="${prjInfo.project_status == 0}">예정</c:if>
									<c:if test="${prjInfo.project_status == 1}">진행중</c:if>
									<c:if test="${prjInfo.project_status == 2}">완료</c:if>
								</span>
							</td>
						</tr>
						<tr>	
							<th>첨부파일</th>
							<td><a href="javascript:popup('/upload/${prjInfo.attach_path}',800,600)">${prjInfo.attach_name}</a></td>
						</tr>					
					</table>
					
		
					<div align="right">
					 	<button type="button" id="startBtn" style="display:none;" class="btn btn-dark btn-sm" onclick="prj_start()">프로젝트 시작</button>
					 	<button type="button" id="endBtn" style="display:none;" class="btn btn-dark btn-sm" onclick="prj_end()">프로젝트 종료</button>
						<button type="button" class="btn btn-dark btn-sm" onclick="list_up()">수정</button>
					</div>
				</div>
	
				<hr>
				
				<c:if test="${prjInfo.project_approve eq 2}">
					<h2>프로젝트 단계 추가</h2>
					<hr>
		
					<div class="form-group row">
						<div class="col-lg-12">
							<button class="btn btn-dark btn-sm float-left "  style="width:130px;height:35px" onclick="prj_order()">단계설정</button>
		
							<button class="btn btn-dark btn-sm float-lg-end"  style="width:130px;height:35px" onclick="location.href='prj_mgr_step_insert?project_id=${prjInfo.project_id}'">추가</button><P>
						</div>
					</div>
		
		<%--				<div align="right">--%>
		<%--				<button type="button"  class="btn btn-secondary" onclick="prj_order()">단계설정</button>--%>
		<%--			 	<button type="button"  class="btn btn-secondary" onclick="location.href='prj_mgr_step_insert?project_id=${prjInfo.project_id}'">추가</button><p>--%>
		<%--			</div>--%>
		<%--			--%>
					<c:forEach var="Step" items="${titleList }" varStatus="status">
						<span style="display: none;" id="Step_${status.count}">${Step.project_step_seq}</span>
						<div class="input-group mb-3" id="PrjStep">
				 			 <span class="input-group-text">
				 			 	<select id="project_order" name="project_order_${status.count}" class="form-select">
				 			 		<c:forEach var="order" begin="1" end="${titleListCount}">
				 			 			<option  value="${order}" 
				 			 				<c:if test="${order == Step.project_order}">selected</c:if>
				 			 			>단계${order}</option>
				 			 		</c:forEach>	
				 			 	</select>
				 			 </span>
							 <input type="text" class="form-control" aria-label="Username" aria-describedby="basic-addon1" id="prjStep" value="${Step.project_s_name }">
							 	<div class="d-grid gap-2 d-md-block" id="buttons">
			  						<button class="btn btn-outline-secondary" style="width:54px;height:54px" type="button" onclick="location.href='prj_mgr_step_edit?project_id=${Step.project_id}&project_step_seq=${Step.project_step_seq}'">수정</button>
			  						<button class="btn btn-outline-secondary" style="width:54px;height:54px" type="button" onclick="location.href='deleteStep?project_id=${Step.project_id}&project_step_seq=${Step.project_step_seq}'">삭제</button>
								</div>
						 </div>	
					</c:forEach>
		
					<div align="right">
						 <button type="button" class="btn btn-dark btn-sm" style="width:130px;height:35px" onclick="location.href='prj_mgr_step_read?project_id=${prjInfo.project_id}'">포트폴리오 생성</button><p>
					</div>
				</c:if>		
			</div>
	  		<!------------------------------ //개발자 소스 입력 END ------------------------------->
		</main>		
		
	</div>
</div>

<!-- FOOTER -->
<footer class="footer py-2">
  <div id="footer" class="container">
  </div>
</footer>

<!-- color-modes -->
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
      <symbol id="check2" viewBox="0 0 16 16">
        <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
      </symbol>
      <symbol id="circle-half" viewBox="0 0 16 16">
        <path d="M8 15A7 7 0 1 0 8 1v14zm0 1A8 8 0 1 1 8 0a8 8 0 0 1 0 16z"/>
      </symbol>
      <symbol id="moon-stars-fill" viewBox="0 0 16 16">
        <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z"/>
        <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>
      </symbol>
      <symbol id="sun-fill" viewBox="0 0 16 16">
        <path d="M8 12a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
      </symbol>
    </svg>

    <div class="dropdown position-fixed bottom-0 end-0 mb-3 me-3 bd-mode-toggle">
      <button class="btn btn-bd-primary py-2 dropdown-toggle d-flex align-items-center"
              id="bd-theme"
              type="button"
              aria-expanded="false"
              data-bs-toggle="dropdown"
              aria-label="Toggle theme (auto)">
        <svg class="bi my-1 theme-icon-active" width="1em" height="1em"><use href="#circle-half"></use></svg>
        <span class="visually-hidden" id="bd-theme-text">Toggle theme</span>
      </button>
      <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="bd-theme-text">
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="light" aria-pressed="false">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#sun-fill"></use></svg>
            Light
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center" data-bs-theme-value="dark" aria-pressed="false">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#moon-stars-fill"></use></svg>
            Dark
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
        <li>
          <button type="button" class="dropdown-item d-flex align-items-center active" data-bs-theme-value="auto" aria-pressed="true">
            <svg class="bi me-2 opacity-50 theme-icon" width="1em" height="1em"><use href="#circle-half"></use></svg>
            Auto
            <svg class="bi ms-auto d-none" width="1em" height="1em"><use href="#check2"></use></svg>
          </button>
        </li>
      </ul>
    </div>
    
</body>
</html>