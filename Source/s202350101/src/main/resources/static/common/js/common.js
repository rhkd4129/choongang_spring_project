/**
 * 
 */

function popup(url, w, h) {
	var pwidth = 800;
	var pheight = 800;
	if(w != null) pwidth = w;
	if(h != null) pheight = h;

	var left = Math.ceil((window.screen.width - pwidth)/2);
	var top  = Math.ceil((window.screen.height - pheight)/2);

	var options = "location=no, directories=no, resizable=yes, status=no, toolbar=no, menubar=no, scrollbars=auto"
	            + ", height="+pheight+", width="+pwidth+", left="+left+", top="+top;

	window.open(url, "_blank", options);
}

function goto(url) {
	location.href = url;
}

function gotoCheckProjectID(url, s_project_id) {
	if(typeof s_project_id == "undefined") {
		location.href = url;
	}else{
		location.href = url+"?s_project_id="+s_project_id;
	}
}

function loadCalendar() {
	var calendarEl = document.getElementById('calendar');
	 	
    var calendar = new FullCalendar.Calendar(calendarEl, {
       initialView : 'dayGridMonth'
    });
    
    calendar.render();

	$(".fc-scrollgrid-sync-table").css("width", $(".fc-col-header").css("width"));
    $(".fc-scrollgrid-sync-table").css("height","290px");
}


//문서 버튼 >> 닫기
function closeDoc() {
	if(opener) {
		if(opener.location.href.indexOf("_list") != -1) {
			opener.location.reload();
		}
		window.close();
	}else{
		history.go(-1);
	}
}

//게시판 페이지 이동
function gotoPage(currentPage) {
	//currentPage 경우의 수
	//1.list_mapping_name
	//2.list_mapping_name?currentPage=1
	//3.list_mapping_name?search=s_doc_body&keyword=test
	//4.list_mapping_name?search=s_doc_body&keyword=test&currentPage=1
	//5.list_mapping_name?doc_group=27&doc_group_lsit=y
	//6.list_mapping_name?doc_group=27&doc_group_lsit=y&currentPage=1
	
	var loc = location.href;
	
	if(loc.indexOf("?currentPage=") != -1) { //2
		loc = loc.substring(0, loc.indexOf("?currentPage=")+13);
		loc = loc + currentPage;
	}else if(loc.indexOf("&currentPage=") != -1) { //4, 6
		loc = loc.substring(0, loc.indexOf("&currentPage=")+13);
		loc = loc + currentPage;
	}else{
		if(loc.indexOf("?") != -1){ //3 5  맨뒤에 추가 
			loc = loc + "&currentPage=" + currentPage;
		}else{ //1 파라미터 없으면 첫번째 추가
			loc = loc + "?currentPage=" + currentPage;
		}
	}
	location.href = loc;
}


//d값
//변경전: 2023-11-09T01:44:25.000+00:00
//변경후: 2023-11-09 01:44:25
function formatDateTime(d) {
	if(d.indexOf('.') != -1) {
		d = d.substring(0, d.indexOf('.'));
		d = d.replace('T', ' ');
	}
	return d; 
}

//변경전: 2023-11-09T01:44:25.000+00:00
//변경후: 2023-11-09
function formatDate(d) {
	if(d.indexOf('T') != -1) {
		d = d.substring(0, d.indexOf('T'));
	}
	return d;
}

/*
// getUrlParams() 함수를 사용하여 쿼리 매개변수 추출
// var params = getUrlParams();
// console.log(params);
// 출력: { name: 'John', age: '25', city: 'seoul' }

// 예제 : 특정 쿼리 매개변수 값 가져오기
// var name = params.name;
// var age  = params.age;
// var city = params.city;
*/
function getUrlParams() {
  var params = {};
  window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { params[key] = value; });
  return params;
}

function setCookie(cookieName, cookieValue, days, cookiePath, cookieDomain, cookieSecure){
	var cookieExpire = new Date();
	cookieExpire.setDate(cookieExpire.getDate() + days); // 설정 일수만큼 현재시간에 만료값으로 지정
	var cookieText=escape(cookieName)+'='+escape(cookieValue);
	cookieText+=(cookieExpire ? '; EXPIRES='+cookieExpire.toUTCString() : '');
	cookieText+=(cookiePath ? '; PATH='+cookiePath : '');
	cookieText+=(cookieDomain ? '; DOMAIN='+cookieDomain : '');
	cookieText+=(cookieSecure ? '; SECURE' : '');
	document.cookie=cookieText;
}
 
function getCookie(cookieName){
	var cookieValue=null;
	if(document.cookie){
		var array=document.cookie.split((escape(cookieName)+'='));
		if(array.length >= 2){
			var arraySub=array[1].split(';');
			cookieValue=unescape(arraySub[0]);
 		}
	}
	return cookieValue;
}
 
function deleteCookie(cookieName){
	var temp=getCookie(cookieName);
	if(temp){
		setCookie(cookieName,temp,(new Date(1)));
	}
}

function deleteFlagAttach() {
	$('#idAttachDeleteFlag').val("D");
	$('#idAttachFile').hide();
	$('#idAttachInput').show();
}
