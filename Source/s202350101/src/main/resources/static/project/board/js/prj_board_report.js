/**
 * 
 */
 
function callAction(action, mapping_name) {
	//작성, 조회 : 새창 or 프레임
	//수정 : 현재창 or 프레임
	//삭제 : 창닫기 or 프레임
	
	if(action == "delete") {
		if(!window.confirm("삭제하시겠습니까?")) {return false;}
	}
	var checked = false;
	if($('#idNewWinFlag')) { //목록창에만 새 창 열기 체크박스 존재
		checked = $("#idNewWinFlag").is(':checked');
	}
	if(checked || opener) { //목록에 새 창 열기 체크되어 있거나, 이미 문서가 열린 경우 opener가 있는 경우(새 창 열기로 연 경우)
		switch(action) {
		case "write": case "read": //작성, 조회
			popup(mapping_name);
			break;
		case "edit": //수정(편집모드)
			location.href=mapping_name;
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
					location.href="prj_board_report_list";
				}else{ //작성, 조회, 수정
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
		/*
		$('#idFrameSet').removeClass("frame_set");
		$('#idFrameList').removeClass("frame_left");
		$('#idFrameDoc').removeClass("frame_right");
		$('#idFrameDoc').html("");
		*/
	}
}

function deleteFlagAttach() {
	$('#idAttachDeleteFlag').val("D");
	$('#idAttachFile').hide();
	$('#idAttachInput').show();
}
