/**
 * 
 */
 

function callAction(action, mapping_name) {
	//작성, 조회, 답변작성 : 새창 or 프레임
	//수정 : 현재창 or 프레임
	//추천 : ajax현재창 or 프레임
	//삭제 : ajax창닫기 or 프레임
	
	if(action == "delete") {
		if(!window.confirm("삭제하시겠습니까?")) {return false;}
	}
	var checked = false;
	if($('#idNewWinFlag')) { //목록창에만 새 창 열기 체크박스 존재
		checked = $("#idNewWinFlag").is(':checked');
	}
	if(checked || opener) { //목록에 새 창 열기 체크되어 있거나, 이미 문서가 열린 경우 opener가 있는 경우(새 창 열기로 연 경우)
		
		switch(action) {
		case "write": case "read": //작성, 조회(새창), 답변작성
			popup(mapping_name);
			break;
		case "edit": case "reload": //수정(편집모드), 조회(reload)
			location.href=mapping_name;
			break;
		case "good": 
			$.ajax({
				url			: mapping_name,
				dataType 	: 'html',
				success		: function(data) {
					if(data == "success") {
						alert("추천처리 완료하였습니다.");
						callAction('reload', mapping_name.replace("_good","_read")); //추천 증가한 정보로 새로고침 열기
					}else if(data == "duplicated"){
						alert("이미 추천하였습니다.");
					}else{
						alert("추천 실패하였습니다. 관리자에 문의하세요.");
					}
				}
			});
			break;
		case "delete": //삭제
			$.ajax({
				url			: mapping_name,
				dataType 	: 'html',
				success		: function(data) {
					if(data == "success") {
						alert("삭제처리 완료하였습니다.");
						if(opener) {
							opener.document.location.reload();
							window.close();
						}
					}else{
						alert("삭제처리 실패하였습니다. 관리자에 문의하세요.");
					}
				}
			});
			break;
		default:
			popup(mapping_name);
			break;
		}
	}else{	//프레임에서 열기 이거나, opener가 없는 경우(=프레임에서 문서가 열린 경우)
		$('#idFrameSet').addClass("frame_set");
		$('#idFrameList').addClass("frame_left");
		$('#idFrameDoc').addClass("frame_right");
		$.ajax({
			url			: mapping_name,
			dataType 	: 'html',
			success		: function(data) {
				if(action == "delete" && data == "success") {
					//삭제 성공인 경우
					alert("삭제처리 완료하였습니다.");
					location.href="prj_board_data_list";
				}else if(action == "good"){ //추천
					if(data == "success"){
						alert("추천완료하였습니다.");
						callAction('read', mapping_name.replace("_good","_read")); //추천 증가한 정보로 조회창 열기						
					}else if(data == "duplicated"){
						alert("이미 추천하였습니다.");
					}else{
						alert("추천처리 실패하였습니다. 관리자에 문의하세요.");
					}
				}else{ //작성, 조회, 수정, 답변작성
					$('#idFrameDoc').html(data);
				}
			}
		});
	}
}

//문서 버튼 >> 닫기
function closeDoc() {
	if(opener) {
		if(opener.location.href.indexOf("_list") > 0) { //목록에서 뜬 경우만 새로고침
			opener.location.reload();
		}
		window.close();
	}else{
		location.reload();
	}
}

function deleteFlagAttach() {
	$('#idAttachDeleteFlag').val("D");
	$('#idAttachFile').hide();
	$('#idAttachInput').show();
}

	