/**
 * 
 */
	//회원조회
	/*function showSawonList(arg) {
		var v_pageNum = "1";
		if(arg != null) {
			v_pageNum = arg;
		}
		$.ajax({
			url : "sawonList.do",
			data : {pageNum : v_pageNum},
			async : true,
			dataType : 'text',
			success : function(data) {
				$('#content').attr('class','content-none');
				$('#content').html(data);
			}
		});
	}*/
	
	//회원등록
	function openSawonWrite() {
		var url = "sawonWriteForm.do?openMode=popup";
		popup(url, 800, 400);
	}

	//선택 회원조회 (이름 선택시 팝업 읽기모드 창 열기)
 	function openSawonContent(sabun, pageNum) {
		var url =  "sawonContent.do?sabun="+sabun+"&pageNum="+pageNum;
			url += "&openMode=popup";
		popup(url, 800, 400);
	}
	
	//------------------------------------------------------------------
	//제품조회
	/*function showItemList(arg) {
		var v_pageNum = "1";
		if(arg != null) {
			v_pageNum = arg;
		}
		$.ajax({
			url : "itemList.do",
			data : {pageNum : v_pageNum},
			async : true,
			dataType : 'text',
			success : function(data) {
				$('#content').attr('class','content-none');
				$('#content').html(data);
			}
		});
	}*/
	
	//제품등록
	function openItemWrite() {
		var url = "itemWriteForm.do?openMode=popup";
		popup(url, 800, 600);
	}

	//선택 제품조회 (선택시 팝업 읽기모드 창 열기)
 	function openItemContent(item_code, pageNum) {
		var url =  "itemContent.do?item_code="+item_code+"&pageNum="+pageNum;
			url += "&openMode=popup";
		popup(url, 800, 600);
	}

	
	//------------------------------------------------------------------
	//거래처조회
	/*function showCustomList(arg) {
		var v_pageNum = "1";
		if(arg != null) {
			v_pageNum = arg;
		}
		$.ajax({
			url : "customList.do",
			data : {pageNum : v_pageNum},
			async : true,
			dataType : 'text',
			success : function(data) {
				$('#content').attr('class','content-none');
				$('#content').html(data);
			}
		});
	}*/
	
	//거래처등록
	function openCustomWrite() {
		var url = "customWriteForm.do?openMode=popup";
		popup(url, 800, 600);
	}

	//선택 거래처조회 (선택시 팝업 읽기모드 창 열기)
 	function openCustomContent(custcode, pageNum) {
		var url =  "customContent.do?custcode="+custcode+"&pageNum="+pageNum;
			url += "&openMode=popup";
		popup(url, 800, 600);
	}
	
	//------------------------------------------------------------------
	//주문조회 목록(사용안함)
	/*function showOrderList(arg) {
		var v_pageNum = "1";
		if(arg != null) {
			v_pageNum = arg;
		}
		$.ajax({
			url : "orderList.do",
			data : {pageNum : v_pageNum},
			async : true,
			dataType : 'text',
			success : function(data) {
				$('#content').attr('class','content-none');
				$('#content').html(data);
			}
		});
	}*/
	
	//주문등록(현페이지)
	function showOrderWrite(arg) {
		var v_pageNum = "1";
		if(arg != null) {
			v_pageNum = arg;
		}
		$.ajax({
			url : "orderWriteForm.do",
			type : 'post',
			data : {pageNum : v_pageNum, openMode : 'main'},
			async : true,
			dataType : 'text',
			success : function(data) {
				$('#content').attr('class','content-none');
				$('#content').html(data);
			}
		});
	}

	//주문등록(팝업)
	function openOrderWrite() {
		var url = "orderWriteForm.do?openMode=popup";
		popup(url, 800, 600);
	}
	
	//선택 주문조회(현페이지)
	function showOrderContent(v_order_date, v_custcode, arg) {
		var v_pageNum = "1";
		if(arg != null) {
			v_pageNum = arg;
		}
		$.ajax({
			url : "orderContent.do",
			type : 'post',
			data : {order_date : v_order_date, custcode : v_custcode, pageNum : v_pageNum, openMode : 'main'},
			async : true,
			dataType : 'text',
			success : function(data) {
				$('#content').attr('class','content-none');
				$('#content').html(data);
			}
		});
	}	

	//선택 주문조회 (선택시 팝업 읽기모드 창 열기)
 	function openOrderContent(order_date, custcode, pageNum) {
		var url =  "orderContent.do?order_date="+order_date+"&custcode="+custcode+"&pageNum="+pageNum;
			url += "&openMode=popup";
		popup(url, 800, 600);
	}
