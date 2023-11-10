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

//전: 2023-11-09T01:44:25.000+00:00
//후: 2023-11-09 01:44:25
function formatDateTime(d) {
	d = d.substring(0, d.indexOf('.'));
	d = d.replace('T', ' ');
	return d; 
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
