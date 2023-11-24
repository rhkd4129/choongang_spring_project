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
	var params = {};
	params.keyword = keyword;

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
			list += '<svg class="bd-placeholder-img flex-shrink-0 me-2 rounded" width="32" height="32" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 32x32" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#007bff"></rect><text x="50%" y="50%" fill="#007bff" dy=".3em">32x32</text></svg>';
			list += '<div class="pb-3 mb-0 small lh-sm border-bottom w-100">';
			list += '<div class="d-flex justify-content-between">';
			list += '<strong class="text-gray-dark"><a href="javascript:popup(\'bd_free?doc_no=\')">'+doc.subject+'</a> | ';
			if(doc.app_id == "1"){
				list	+= doc.bd_category;
			}else{
				list	+= doc.app_name;	
			}
			list += '</strong>';
			list += '<a href="#">' + doc.user_name + ' ' + formatDateTime(doc.create_date) + '</a>';
			list += '</div>';
			list += '<span class="d-block">' + doc.doc_body + '</span>';
			list += '</div>';
			list += '</div>';
			 
			$("#divSearchResult").append(list);
		});
	}
}
    
  