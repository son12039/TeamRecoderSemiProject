$(document).ready(function() {
	console.log("입장1");
	const urlParams = new URL(location.href).searchParams;
	const membershipCode = urlParams.get('membershipCode') * 1;
	console.log(membershipCode);
	// 채팅방 목록 불러오기
	const chattingRoomList = function() {
		console.log("방검색시작");

		$.ajax({ url: "/chattingRoomList", type: "GET", })
			.then(function(roomList) {
				console.log(roomList);
				listHtml(roomList);
			})
			.fail(function() {
				alert("방목록 불러오기 오류");
			});
	};

	// 방 목록 그리기
	const listHtml = function(roomList) {
		let listHtml = "";
		// 회원 정보를 가져오는 AJAX 호출
		$.ajax({
			url: "/getMemberInfo",
			type: "GET",
			success: function(data) {
				console.log("성공");
				for (let i = 0; i < roomList.length; i++) {
					if (`${roomList[i].roomNumber}` == membershipCode) {
						listHtml += `
			   <li data-room_number=${roomList[i].roomNumber}>
					<span class="chat_title">${roomList[i].roomName}</span>
					<span class="chat_count">${roomList[i].users.length}명</span>
			  </li>`;  
				break;	}
				}
				let i = membershipCode

				$("main ul").html(listHtml);
			}
		});
	};

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
	};

	// 메인 화면
	const main = function() {
		$("main").show();
		subscribeCancle();

		const room = chattingRoom();
		if (room) {
			return;
		}

		const subscribeId = stomp.subscribe("/topic/roomList", function() {
			chattingRoomList();
		});

		subscribe.push(subscribeId);
		chattingRoomList();
	};

	stomp.connect({}, function() {
		main();
	});

	// 채팅방
	const info = (function() {
		let nickname = "";
		let roomNumber = "";

		return {
			getNickname: () => nickname,
			setNickname: (set) => { nickname = set; },
			getRoomNumber: () => roomNumber,
			setRoomNumber: (set) => { roomNumber = set; },
		};
	})();

	const errorMSG = function(result) {
		if (result.status == 404) {
			alert("없는 방이유");
		} else {
			alert("방이 터진 것 같아유");
		}
		location.href = "/";
	};

	// 참가자 그리기
	const userList = function(users) {
		$(".chat .chat_users .user").text(users.length + "명");

		let userHtml = "";
		for (let i = 0; i < users.length; i++) {
			userHtml += `<li>${users[i]}</li>`;
		}

		$(".chat .chat_nickname ul").html(userHtml);
	};

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

		let sender = info.getNickname() === nickname ? "chat_me" : "chat_other";
		nickname = info.getNickname() === nickname ? "" : nickname;

		const chatHtml = `
        <li>
            <div class=${sender}>
                <div>
                    <div class="nickname">${nickname}</div>
                    <div class="message">
                        <span class=chat_in_time>${time}</span>
                        <span class="chat_content">${message}</span>
                    </div>
                </div>
            </div>
        </li>`;

		$(".chat ul.chat_list").append(chatHtml);
		$(".chat ul").scrollTop($(".chat ul")[0].scrollHeight);
	};

	// 채팅방 구독
	const chattingConnect = function(roomNumber) {
		subscribeCancle();

		const id1 = stomp.subscribe("/topic/message/" + roomNumber, function(result) {
			const message = JSON.parse(result.body);
			chatting(message);
		});

		const id2 = stomp.subscribe("/topic/notification/" + roomNumber, function(result) {
			const room = JSON.parse(result.body);
			const message = room.message;
			userList(room.users);

			const chatHtml = `
            <li>
                <div class="notification">
                    <span>${message}</span>
                </div>
            </li>`;

			$(".chat ul.chat_list").append(chatHtml);
			$(".chat ul").scrollTop($(".chat ul")[0].scrollHeight);
		});

		subscribe.push(id1);
		subscribe.push(id2);
	};

	// 채팅방 세팅
	const initRoom = function(room, nickname) {
		stomp.send("/socket/roomList");

		$("main").hide();

		info.setNickname(nickname);
		info.setRoomNumber(room.roomNumber);

		$(".chat").show();
		$(".chat .chat_title").text(room.roomName);

		userList(room.users);
		chattingConnect(room.roomNumber);

		$(".chat_input_area textarea").focus();
	};

	// 메세지 보내기
	const sendMessage = function() {
		const message = $(".chat_input_area textarea");

		if (message.val() === "") {
			return;
		}

		const roomNumber = info.getRoomNumber();
		const nickname = info.getNickname();

		const data = {
			message: message.val(),
			nickname: nickname,
		};

		stomp.send("/socket/sendMessage/" + roomNumber, {}, JSON.stringify(data));
		message.val("");
	};

	$(".chat_button_area button").click(function() {
		sendMessage();
		$(".chat_input_area textarea").focus();
	});

	// 채팅방 안에서 채팅 발송 로직
	$(".chat_input_area textarea").keypress(function(event) {
		if (event.keyCode === 13) { // Enter 눌렀을 때
			if (!event.shiftKey) { // Shift 안 누르고 있을 때
				event.preventDefault();
				sendMessage();
			}
		}
	});

	const enterChattingRoom = function(roomNumber) {
		let id = "";
		$.ajax({ url: "/nick1", type: "GET", })
			.then(function(nickname) {

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
					}
				});
			});
	};

	// 새 채팅방 만들기
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
					};

					$.ajax({
						url: "/chattingRoom",
						type: "POST",
						data: data,
					})
						.then(function(room) {
							initRoom(room, nickname);
						})
						.fail(function() {
							alert("에러가 발생했습니다");
						});
				}
			});
	};

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
			});
	});

	$(document).on("dblclick", "main li", function() {
		const roomNumber = $(this).data("room_number");
		console.log("클릭!");
		enterChattingRoom(roomNumber);
	});

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

							if (room.users.length !== 0) {
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
						});
				}
			});
	});

	// 대화 중이던 방
	const chattingRoom = function() {
		let returnRoom = null;

		$.ajax({
			url: "/chattingRoom",
			type: "GET",
			async: false,
		})
			.then(function(result) {
				if (result !== "") {
					const room = result.chattingRoom;
					const nickname = result.myNickname;
					initRoom(room, nickname);
					returnRoom = result;
				}
			})
			.fail(function(result) {
				errorMSG(result);
			});

		return returnRoom;
	};

	// css용 오류나면 제일 먼저 치워버리기@@@@@@@@@@@@@@@@@@@@@@@@@@

	const characters = document.querySelectorAll('.character');

	const speed = 2; 
	const boundaryPadding = 50; 

	function getRandomDirection() {
		const angle = Math.random() * 2 * Math.PI; 
		return {
			x: Math.cos(angle) * speed,
			y: Math.sin(angle) * speed
		};
	}

	function getRandomColor() {
		const r = Math.floor(Math.random() * 256); 
		const g = Math.floor(Math.random() * 256);
		const b = Math.floor(Math.random() * 256);
		return `rgb(${r},${g},${b})`;
	}

	function updatePosition(character, direction) {
		const rect = character.getBoundingClientRect();
		let newX = rect.left + direction.x;
		let newY = rect.top + direction.y;

		// 화면 경계를 고려하여 방향 변경
		if (newX < 0 || newX + rect.width > window.innerWidth) {
			direction.x *= -1;
			character.style.backgroundColor = getRandomColor(); // 방향 변경 시 색상 랜덤 변경
		}
		if (newY < 0 || newY + rect.height > window.innerHeight) {
			direction.y *= -1;
			character.style.backgroundColor = getRandomColor(); // 방향 변경 시 색상 랜덤 변경
		}

		character.style.left = `${rect.left + direction.x}px`;
		character.style.top = `${rect.top + direction.y}px`;

		// Recursive update
		requestAnimationFrame(() => updatePosition(character, direction));
	}

	characters.forEach(character => {
		const direction = getRandomDirection();
		character.style.left = `${Math.random() * (window.innerWidth - 50)}px`;
		character.style.top = `${Math.random() * (window.innerHeight - 50)}px`;
		character.style.backgroundColor = getRandomColor(); // 초기 색상 랜덤 설정
		updatePosition(character, direction);
	});
	// css용 오류나면 제일 먼저 치워버리기@@@@@@@@@@@@@@@@@@@@@@@@@@
});
