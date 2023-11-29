/**
 * 
 */


//준우
var ws;
let chat_chats = document.getElementById("chat_chats");
let chat_users = document.getElementById("chat_users");

function chat_button() {
    var con = document.getElementById("chatbox");

    if (con.style.display == 'none') {
        con.style.display = 'flex';
        chat_users.style.display = 'block';
    } else {
        con.style.display = 'none';
        chat_users.style.display = 'none';
    }
}
function chat_close() {
    var con = document.getElementById("chatbox");

    con.style.display = 'none';
    chat_users.style.display = 'none';
}

function chat_user_bt() {
    chat_chats.style.display = 'none';
    chat_users.style.display = 'block';
}

function chat_chats_bt() {
    chat_users.style.display = 'none';
    chat_chats.style.display = 'block';
}

function chat_room(user_id) {
    console.log(user_id);
    
    var width = "500";
	var height = Math.ceil((window.screen.height)*0.7);
    
    window.open(
        "/chat_room?user_id=" + user_id,
        "" + user_id + "",      //  다중 채팅을 위해서 선언
        "width="+width+", height="+height+", top=109, left=243, location=no, directories=no, resizable=yes, status=no, toolbar=no, menubar=no, scrollbars=auto"
    );
}

$(
    function wsOpen() {
        let ws;             //  웹소캣
        let chatstompClient;    //  stomp

        let user = $("#user_id").val();
        let usName = $("#user_name").val();
        console.log("user");
        console.log(user);
        console.log("usName");
        console.log(usName);
        let wsUri = "/chat";
        ws = new SockJS(wsUri);                    //   websocket 연결
        chatstompClient = Stomp.over(ws);
        chatstompClient.connect({}, function (frame) {
            // console.log("chatstompClient");
            // console.log(chatstompClient);
            let option = {
                sender_id: user,
                user_name: usName
            };
            chatstompClient.send("/queue/chat/cnt", {}, JSON.stringify(option));     //  여기도 중괄호 왜?

            chatstompClient.subscribe("/app/cnttotmsg", function (message) {
                console.log("getMessage");

                let msg = JSON.parse(message.body);
                let cntmsg      = $('#cntMsg');     //  읽지 않은 메시지 수입력할 공간
                let getUserId   = msg.secobj;       //  요청의 주체
                let noReadChat  = msg.obj;          //  내가 읽지 않은 메시지 수
                let chatList    = msg.secList;      //  채팅방 목록
                // let recUserID = msg.trdobj;

                console.log(msg);
                console.log("chatList");
                //let con = '읽지 않은 메시지: ' + noReadChat;
                let con = noReadChat;
                console.log("getUserId");
                console.log(getUserId);
                console.log("user");
                console.log(user);

                if (getUserId == user) {
                    cntmsg.empty();
                    cntmsg.append(con);
                }
                if (getUserId == user) {

                    let chat_chats = $('#chat_chats');   //  채팅방 공간
                    let chatroom_con = '';

                    $.each(chatList , function (index, ChatRoom) {
                        // chatroom_con += '<div id="chat_chats" style="display:none">'
                        let show_time = ChatRoom.show_time == null ? '최근 메시지 없음' :  ChatRoom.show_time;
                        let msg_con = ChatRoom.msg_con == null ? '최근 메시지 없음' : ChatRoom.msg_con;
                        let read_cnt = ChatRoom.read_cnt == 0 ? 0 : ChatRoom.read_cnt;

                        console.log("ChatRoom");
                        console.log(ChatRoom);
                        if (ChatRoom.sender_id == user) {
                            chatroom_con += '<div id="chat_chat_list" onclick="chat_room('+"'" + ChatRoom.receiver_id + "'"+ ')">';
                        } else {
                            chatroom_con += '<div id="chat_chat_list" onclick="chat_room('+"'" + ChatRoom.sender_id + "'" + ')">';
                        }
                        chatroom_con += '<div id="chat_ch_left">';
                        console.log("hihi");
                        console.log(ChatRoom.attach_path);
                        console.log(ChatRoom.attach_name);
                        chatroom_con += '<img className='+'"uploadFile"'+'style='+'"width:30px; height: 30px; border-radius: 70%;"'+' src='+'"'+ChatRoom.attach_path+'/'+ChatRoom.attach_name+'"></div>';
                        chatroom_con += '<div id="chat_ch_center">';
                        console.log("ChatRoom.user_name");
                        console.log(ChatRoom.user_name);
                        console.log("getUserId");
                        console.log(getUserId);


                        chatroom_con += '<p>' +  ChatRoom.user_name + '</p>';
                        // chatroom_con += '<p>' + ChatRoom.user_name + '</p>';
                        chatroom_con += '<p>'+"'"+ msg_con+"'"+'</p></div>';
                        chatroom_con += '<div id="chat_ch_right"><p>' + show_time + '</p></div>';
                        chatroom_con += '<div id="readCnt"> <p>'+"'"+read_cnt + "'" +'</p></div></div>';
                    });
                    chat_chats.empty();
                    // console.log("chatroom_con");
                    // alert("hi");
                    // console.log(chatroom_con);
                    chat_chats.append(chatroom_con);
                }
            })
        })
    }
)

$(function(){
    $(".iconChat").click(function(){
		var user_id = $(this).find("input").val();
        chat_room(user_id);
	});
});
