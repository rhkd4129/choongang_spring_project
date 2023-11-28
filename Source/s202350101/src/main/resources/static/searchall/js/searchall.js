/**
 * 
 */
//$(document).ready(function(){ //--3.0이상 지원하지 않음. 아래 코드랑 같음 : 아래 형식 권장 $(function(){
$(function(){
	$("#search").keydown(function (key) {
		if(key.keyCode == 13) {
	      searchAll();
		}
	});
});

function searchAll(){
	var keyword = $("input[name=keyword]").val(); // 검색어
	var project_id = $("input[name=searchall_project_id]").val(); // 프로젝트ID
	var params = {};
	params.keyword = keyword;
	params.project_id = project_id;
	
	$.ajax({
		url			: '/search_all',
		data		: JSON.stringify(params),
		type		: 'POST',
		contentType	: 'application/json; charset:utf-8',
		dataType	: 'json',
		success		: function(data){
			//alert(data);
		    showSearchList(data, keyword);
		},
		error		: function(xhr, status, error){
			console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
		}		
	});
	
}
/* 
*/

// 검색(인정)
function showSearchList(docList, keyword){
	if(Object.keys(docList).length==0){
		alert("해당 검색 결과가 없습니다.");
	}
	else{
    	$("#center").empty();
    	var list = '';        	
    	list += '<div class="container-fluid"><div style="margin-top:20px;height:45px"><h3>통합 검색 결과</h3></div>검색결과 ' + Object.keys(docList).length + '건   검색어:' + keyword + '</div>';
    	$("#center").append(list);
    	list = '<div class="container-fluid"><div style="width:85%;" id="divSearchResult"></div></div>';
    	$("#center").append(list);
		$(docList).each(function(index, doc){
			//alert("제목 :"+doc.subject);





			
			list = '<div class="d-flex text-body-secondary pt-3">';
			//list += '<svg class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"></rect><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>';
			
			var cate_idx = "1";
			switch(doc.bd_category) {
			case "공지": 		cate_idx="1-1"; break;
			case "이벤트": 	cate_idx="1-2"; break;
			case "자유": 		cate_idx="1-3"; break;
			default :		cate_idx=doc.app_id; break;
			}

			list 	+= '<span class="pms-circle-search bg-' + cate_idx + '">';
			if(cate_idx == "1-1" || cate_idx == "1-2" || cate_idx == "1-3") {
				list	+= (doc.bd_category=='이벤트'?'Event':doc.bd_category);				
			}else if(cate_idx == "2") {
				list	+= 'Q&A';
			}else if(cate_idx == "3") {
				list 	+= '자료';
			}else if(cate_idx == "4") {
				list 	+= '업무';
			}else if(cate_idx == "5") {
				list 	+= '작업';
			}
			list	+= '</span>';

			list += '<div class="pb-3 mb-0 lh-sm border-bottom w-100">';
			list += '<div class="d-flex justify-content-between">';
			list += '<strong class="text-gray-dark"><a href="javascript:popup(\'/';
			switch(doc.bd_category) {
			case "공지": 
				list += 'board_notify_read';
				list += '?doc_no='+doc.doc_no+'\')">';
				break;
			case "이벤트":
				list += 'board_event_read'; 
				list += '?doc_no='+doc.doc_no+'\')">';
				break;
			case "자유": 
				list += 'board_free_read'; 
				list += '?doc_no='+doc.doc_no+'\')">';
				break;
			default :
				if(doc.app_path == "BD_QNA") {
					list += 'board_qna_read';
					list += '?doc_no='+doc.doc_no+'\')">';
				}else if(doc.app_path == "PRJ_BD_DATA") {
					list += 'prj_board_data_read';
					list += '?doc_no='+doc.doc_no+'&project_id='+doc.project_id+'\')">';
				}else if(doc.app_path == "PRJ_BD_REP") {
					list += 'prj_board_report_read';
					list += '?doc_no='+doc.doc_no+'&project_id='+doc.project_id+'\')">';
				}else if(doc.app_path == "TASK") {
					list += 'task_read';
					list += '?doc_no='+doc.doc_no+'&project_id='+doc.project_id+'\')">';
				}else{
					//list += '';
				}
				break;
			}
			//제목
			if(doc.app_path == 'TASK'){
				list += doc.task_subject;
			}else{
				list += doc.subject;
			}
			
			list += '</a> | ';
			if(doc.app_path == "BD_FREE"){
				list	+= doc.bd_category; // 공지, 이벤트, 자유
			}else{
				list	+= doc.app_name;	// Q&A, 작업, 공지/자료, 업무보고
			}
			list += '</strong>';
			list += '<a href="#">' + doc.user_name + ' ' + formatDateTime(doc.create_date) + '</a>';
			list += '</div>';
			list += '<span class="d-block pms-overflow-search">';
			//본문
			if(doc.app_path == 'TASK'){
				list += doc.task_content;
			}else{
				list += doc.doc_body;
			}
			
			list += '</span>';
			list += '</div>';
			list += '</div>';
			 
			$("#divSearchResult").append(list);
		});
	}
}
    
  