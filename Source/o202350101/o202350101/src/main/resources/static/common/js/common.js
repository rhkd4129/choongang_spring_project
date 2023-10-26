/**
 * 
 */

function popup(url, w, h) {
	var pwidth = 800;
	var pheight = 600;
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