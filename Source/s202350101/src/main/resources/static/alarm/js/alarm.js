/**
 * 
 */
 
 	// stomp 사용	
	let stompClient;
	
	
	$(document).ready(function () {
	    onSocket();
	});
	
	// 소켓 연결	
function onSocket() {
    let stompClient;

    function disconnectWebSocket() {
        if (stompClient && stompClient.connected) {
            stompClient.disconnect(function () {
                console.log("Disconnected WebSocket.");
                setTimeout(connectWebSocket, 5000); // 5초 후 다시 연결 시도
            });
        }
    }

    function connectWebSocket() {
        let socket = new SockJS('/websocket');
        console.log("1");
        stompClient = Stomp.over(socket);
        console.log("2");

        const obj = {
            project_id: '${userInfo.project_id}',
            user_id: '${userInfo.user_id}'
        };

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            console.log("3");
            console.log(obj);

            stompClient.subscribe("/noti/test", function (data) {
                console.log("hi");
                console.log(data);

                var rtndata = JSON.parse(data.body);

                const date = new Date();
                const year = date.getFullYear();
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + (date.getDate())).slice(-2);
                let now = year + "-" + month + "-" + day;

                console.log("now date: " + now);
                
                let notify = $('#notify');
                notify.empty();
                
                let str = '';
                
                for (var i = 0; i < rtndata.length; i++) {
                    const meetingDate = rtndata[i].meeting_date;
                    console.log(meetingDate);

                    if (meetingDate == now) {
						str += '<p onclick="location.href=' + "'/prj_meeting_calendar?project_id=" + rtndata[i].project_id + "'" + '"' + '>오늘(' + rtndata[i].meeting_date + ') 예정된 ' + rtndata[i].meeting_title + ' 회의가 있습니다.</p>';
                    }
                }
                
                notify.append(str);
            });

            stompClient.send('/queue/post', {}, JSON.stringify(obj));
            console.log("4");
        });
    }

    // 초기 연결 수행
    connectWebSocket();

    // 5초마다 웹 소켓 연결을 끊고 다시 연결
    setInterval(function () {
        disconnectWebSocket();
    }, 5000);
}

    
	// 알림버튼 클릭 시 작동
	function notifyClick() {
		
		var con = document.getElementById("notify");
		
		if (con.style.display == 'none') {
			con.style.display = 'block';
		} else {
			con.style.display = 'none';
		}
		
	};