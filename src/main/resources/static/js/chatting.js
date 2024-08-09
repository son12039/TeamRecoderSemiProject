$(document).ready(function() {

	
	$("#member123").click(()=> {
		console.log("membershipList");
	});

	// 채팅방 목록 불러오기 채팅서버 입장하자마자 실행
	const chattingRoomList = function() {

		$.ajax({
			url: "/chattingRoomList",
			type: "GET",
		})
			.then(function(result) {
				// result에 방이름이랑 방 코드가 리스트로 담겨있음, 현재 로그인된 회원의 가입된 클럽코드와 방코드가 일치 하는 리스트만 
				// 담아 listHtml한테 보내야함
				// 로그인정보와 리스트를 함께 보내기, 어떤 방법으로 회원정보는 여기서 가져오기
				console.log("chattingRoomList도착");
				listHtml(result)
			})
			.fail(function() {
				alert("방목록 불러오기 오류");
			})
	}

	// 방 목록 그리기
	const listHtml = function(roomList) {
		let listHtml = "";
		for (let i = roomList.length - 1; i >= 0; i--) {
			listHtml += `
				<li data-room_number=${roomList[i].roomNumber}>
                    <span class="chat_title">${roomList[i].roomName}</span>
                    <span class="chat_count">${roomList[i].users.length}명</span>
	            </li>`;
		}
		$("main ul").html(listHtml);
	}



	const socket = new SockJS('/websocket');
	const stomp = Stomp.over(socket);
	stomp.debug = null; // stomp 콘솔출력 X


	// 구독을 취소하기위해 구독 시 아이디 저장
	const subscribe = [];

	// 모든 구독 취소하기
	const subscribeCancle = function() {
		const length = subscribe.length;
		for (let i = 0; i < length; i++) {
			const sid = subscribe.pop();
			stomp.unsubscribe(sid.id);
		}
	}


	// 메인 화면
	const main = function() {
		$("main").show();

		// 기존 구독 취소
		subscribeCancle();

		// 채팅 중이었던 방이 있을때
		const room = chattingRoom();

		if (room) {
			return;
		}

		const subscribeId = stomp.subscribe("/topic/roomList", function() {
			// "/topic/roomList"에서 메세지가 왔을때 실행할 함수
			chattingRoomList();
		});

		subscribe.push(subscribeId);
		chattingRoomList();
	};



	stomp.connect({}, function() {
		main();
	});


	// ----------------- 메인화면 ---------------------------



	// ----------------- 채팅방 ---------------------------


	const info = (function() {
		let nickname = "";
		let roomNumber = "";

		const getNickname = function() {
			return nickname;
		}

		const setNickname = function(set) {
			nickname = set;
		}

		const getRoomNumber = function() {
			return roomNumber;
		}

		const setRoomNumber = function(set) {
			roomNumber = set;
		}
		return {
			getNickname: getNickname,
			setNickname: setNickname,
			getRoomNumber: getRoomNumber,
			setRoomNumber: setRoomNumber,
		}
	})();


	const errorMSG = function(result) {
		if (result.status == 404) {
			alert("없는 방이유");
		} else {
			alert("방이 터진 것 같아유");
		}
		location.href = "/";
	}


	// 참가자 그리기
	const userList = function(users) {
		$(".chat .chat_users .user").text(users.length + "명");

		let userHtml = "";
		for (let i = 0; i < users.length; i++) {
			userHtml += `
			<li>${users[i]}</li>`;
		}

		$(".chat .chat_nickname ul").html(userHtml);
	}


	// 메세지 그리기
	const chatting = function(messageInfo) {
		let nickname = messageInfo.nickname;
		let message = messageInfo.message;

		message = message.replaceAll("\n", "<br>").replaceAll(" ", "&nbsp");

		const date = messageInfo.date;
		const d = new Date(date);

		const time = String(d.getHours()).padStart(2, "0")
			+ ":"
			+ String(d.getMinutes()).padStart(2, "0");

		let sender = "";

		if (info.getNickname() == nickname) {
			sender = "chat_me";
			nickname = "";
		} else {
			sender = "chat_other";
		}


		const chatHtml = `
        <li>
            <div class=${sender}>
            	<div>
	            	<div class="nickname">${nickname}</div>
	            	<div class="message">
		                <span class=chat_in_time>${time}</span>
		                <span class="chat_content">${message}</span>
	                <span>
                </div>
            </div>
        </li>`;

		$(".chat ul.chat_list").append(chatHtml);

		$(".chat ul").scrollTop($(".chat ul")[0].scrollHeight);
	}


	// 채팅방 구독
	const chattingConnect = function(roomNumber) {
		// 기존 구독 취소
		subscribeCancle();

		// 메세지를 받을 경로
		const id1 = stomp.subscribe("/topic/message/" + roomNumber, function(result) {
			const message = JSON.parse(result.body);

			// 메세지가 왔을때 실행할 함수
			chatting(message);
		})

		// 입장,퇴장 알림을 받을 경로
		const id2 = stomp.subscribe("/topic/notification/" + roomNumber, function(result) {
			const room = JSON.parse(result.body);
			const message = room.message;

			// 메세지가 왔을때 실행할 함수
			userList(room.users);

			const chatHtml = `
	        <li>
	        	<div class="notification">
            		<span>${message}</span>
            	</div>
	        </li>`;

			$(".chat ul.chat_list").append(chatHtml);

			$(".chat ul").scrollTop($(".chat ul")[0].scrollHeight);

		})

		subscribe.push(id1);
		subscribe.push(id2);
	}



	// 채팅방 세팅
	const initRoom = function(room, nickname) {
		// 방 목록 업데이트
		stomp.send("/socket/roomList");

		$("main").hide();

		info.setNickname(nickname);
		info.setRoomNumber(room.roomNumber);

		$(".chat").show();
		$(".chat .chat_title").text(room.roomName);

		userList(room.users);
		chattingConnect(room.roomNumber);

		$(".chat_input_area textarea").focus();
	}


	// 메세지 보내기
	const sendMessage = function() {
		const message = $(".chat_input_area textarea");

		if (message.val() == "") {
			return;
		}

		const roomNumber = info.getRoomNumber();
		const nickname = info.getNickname();

		const data = {
			message: message.val(),
			nickname: nickname,
		}

		stomp.send("/socket/sendMessage/" + roomNumber, {}, JSON.stringify(data));
		message.val("");
	}




	$(".chat_button_area button").click(function() {
		sendMessage();
		$(".chat_input_area textarea").focus();
	})

	// 채팅방 안에서 채팅발송 로직
	$(".chat_input_area textarea").keypress(function(event) {
		if (event.keyCode == 13) { // enter 눌렀을 때 채팅방에서 줄바꿈대신 메세지발송
			if (!event.shiftKey) { //shift안누르고있을때
				event.preventDefault();

				sendMessage();
			}
		}
	})


	// 채팅방 입장 시 닉네임 입력받음 만약 채팅방에 있는 사용자 중 동일한 닉네임이 있으면 재입력하라고 뜸 
	const enterChattingRoom = function(roomNumber) {
		swal({
			text: "사용하실 닉네임을 입력해주세요",
			content: "input",
			buttons: ["취소", "확인"],
			closeOnClickOutside: false
		})
			.then(function(nickname) {
				if (nickname) {
					const data = {
						roomNumber: roomNumber,
						nickname: nickname
					};

					$.ajax({
						url: "/chattingRoom-enter",
						type: "GET",
						data: data,
						success: function(room) { // 성공 시 호출됨
							initRoom(room, nickname);

							// 채팅방 참가 메세지
							room.message = nickname + "님이 참가하셨습니다";
							stomp.send(
								"/socket/notification/" + roomNumber, {},
								JSON.stringify(room));
						},
						error: function(xhr) {
							if (xhr.status === 409) {
								swal("닉네임 중복", "이미 사용 중인 닉네임입니다. 다른 닉네임을 입력해주세요.", "error")
									.then(() => enterChattingRoom(roomNumber)); // 재시도
							} else {
								errorMSG(xhr);
							}
						}
					});
				}
			});
	};



	// 새 채팅방 만들기 -----------------------------------------------------------
	const createRoom = function(roomName) {
		swal({
			text: "사용하실 닉네임을 입력해주세요",
			content: "input",
			buttons: ["취소", "확인"],
			closeOnClickOutside: false
		})
			.then(function(nickname) {
				if (nickname) {

					const data = {
						roomName: roomName,
						nickname: nickname
					}

					$.ajax({
						url: "/chattingRoom",
						type: "POST",
						data: data,
					})
						.then(function(room) {
							initRoom(room, nickname)
						})
						.fail(function() {
							alert("에러가 발생했습니다");
						})
				}
			})
	}


	// 새 채팅방 만들기 -----------------------------------------------------------

	$(".new_chat").click(function() {
		swal({
			text: "방 이름을 입력해주세요",
			content: "input",
			buttons: ["취소", "확인"],
			closeOnClickOutside: false
		})
			.then(function(roomName) {
				if (roomName) {
					createRoom(roomName);
				}
			})
	})






	$(document).on("dblclick", "main li", function() {
		const roomNumber = $(this).data("room_number");
		enterChattingRoom(roomNumber);
	})




	// 채팅방 나가기
	$(".chat_back").click(function() {
		swal({
			text: "대화방에서 나갈까요?",
			buttons: ["취소", "확인"]
		})
			.then(function(result) {
				if (result) {
					$.ajax({
						url: "/chattingRoom-exit",
						type: "PATCH",
					})
						.then(function(room) {
							const roomNumber = info.getRoomNumber();

							if (room.users.length != 0) {
								// 채팅방 나가기 메세지
								room.message = info.getNickname() + "님이 퇴장하셨습니다";
								stomp.send(
									"/socket/notification/" + roomNumber, {},
									JSON.stringify(room));
							}

							// 채팅방 목록 업데이트
							stomp.send("/socket/roomList");

							main();
							$(".chat").hide();
							$(".chat ul.chat_list").html("");

							info.setRoomNumber("");
							info.setNickname("");
						})
						.fail(function() {
							errorMSG();
						})
				}
			})
	})



	// 대화 중이던 방
	const chattingRoom = function() {
		let returnRoom = null;

		$.ajax({
			url: "/chattingRoom",
			type: "GET",
			async: false,
		})
			.then(function(result) {
				if (result != "") {
					const room = result.chattingRoom;
					const nickname = result.myNickname;
					initRoom(room, nickname);
					returnRoom = result;
				}
			})
			.fail(function(result) {
				errorMSG(result);
			})

		return returnRoom;
	};


})