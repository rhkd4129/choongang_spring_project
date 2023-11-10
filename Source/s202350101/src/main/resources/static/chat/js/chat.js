/**
 * 
 */
 
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
        window.open(
            "/chat_room?user_id=" + user_id,
            "Child",
            "width=600, height=570, top=50, left=50"
        );
    }