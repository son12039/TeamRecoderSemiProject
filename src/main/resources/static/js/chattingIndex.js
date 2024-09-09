$(document).ready(function() {
	// 채팅방 목록 불러오기
	$("#chatMain").hide();
	$("#reduce").click(function() {
		$("#reduce").hide();
		main();
	});
	$("#can").click(function() {
		$("#chatMain").hide();
		$("#reduce").show();
	});
	const link = document.createElement('link');
	link.rel = 'stylesheet';
	link.href = 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css';
	document.head.appendChild(link);
	const chattingRoomList = function() {
		enterChattingroomCode().then(function(codeList) {
			$.ajax({
				url: "/chattingRoomList", type: "GET"
			})
				.then(function(roomList) {
					const codeSet = new Set(codeList.map(String));
					let listHtml = "";
					for (let i = 0; i < roomList.length; i++) {
						if (codeSet.has(roomList[i].roomNumber)) {
							listHtml += `
	   			<li data-room_number=${roomList[i].roomNumber}>
					<span class="chat_title">${roomList[i].roomName}</span>
					<span class="chat_count">${roomList[i].users.length} <i class="fa-solid fa-user"></i></span>
				</li>`;
						}
					}
					$("main ul").html(listHtml);
				});
		});
	};

	const enterChattingroomCode = function() {
		return $.ajax({
			url: "/enterChattingroomCode", type: "GET"
		})
			.then(function(response) {
				return response;
			});
	};


	const socket = new SockJS('http://localhost:8080/websocket');
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
		$(".chat").hide();
		$("main").show();

		subscribeCancle();
		const subscribeId = stomp.subscribe("/topic/roomList", function() {
			chattingRoomList();
		});

		subscribe.push(subscribeId);
		chattingRoomList();
	};

	stomp.connect({}, function() {
	});
	// 채팅방
	const info = (function() {
		let nickname = "";
		let roomNumber = "";
		let image = "";
		return {
			getNickname: () => nickname,
			setNickname: (set) => { nickname = set; },
			getRoomNumber: () => roomNumber,
			setRoomNumber: (set) => { roomNumber = set; },
			getImage: () => image,
			setImage: (set) => { image = set; },
		};
	})();
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
		let a = `<img src="${messageInfo.memimg}" width="100px"/>`;

		message = message.replaceAll("\n", "<br>").replaceAll(" ", "&nbsp")
			.replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;");
		const date = messageInfo.date;
		const d = new Date(date);

		const time = String(d.getHours()).padStart(2, "0")
			+ ":"
			+ String(d.getMinutes()).padStart(2, "0");
		let check = info.getNickname() === nickname;
		let sender = check ? "chat_me" : "chat_other";
		nickname = check ? "" : nickname;
		let img = check ? "" : messageInfo.img;
		const chatHtml = `
        <li>
            <div class=${sender}>
                <div class="all">                    
					${check ? "" : a}
					<div class="nm">
					<div class="nickname">${nickname}</div>
					<div class="message">
                        <span class=chat_in_time>${time}</span>
                        <span class="chat_content">
						${messageInfo.img != null ? `<img src='${messageInfo.img}'/>` : ""}
						${message}</span>
						</div>
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
	const initRoom = function(room, nickname, img) {
		stomp.send("/socket/roomList");
		$("main").hide();
		$(".chat").show();
		info.setNickname(nickname);
		info.setRoomNumber(room.roomNumber);
		info.setImage(img);
		$(".chat .chat_title").text(room.roomName);
		userList(room.users);
		chattingConnect(room.roomNumber);

		$(".chat_input_area textarea").focus();
	};

	// 메세지 보내기
	const sendMessage = function() {
		const img = $(".chat_body > img").first();
		const isImage = img.length > 0;
		const message = $(".chat_input_area textarea");
		if (message.val() === "" && img.length == 0) {
			return;
		}

		const roomNumber = info.getRoomNumber();
		const nickname = info.getNickname();
		const memimg = $("#imginput").val();
		const data = {
			img: img.attr("src"),
			message: message.val(),
			nickname: nickname,
			memimg: memimg,
		};
		stomp.send("/socket/sendMessage/" + roomNumber, {}, JSON.stringify(data));
		message.val("");
		if (isImage) {
			img.remove();
		}
	};

	$("#text").click(function() {
		sendMessage();
		$(".chat_input_area textarea").focus();
	});
	$(".chat_body").on("click", "img", function() {
		$(".chat_body > img").remove();
	});

	// 채팅방 안에서 채팅 발송 로직
	$(".chat_input_area textarea").keydown(function(event) {
		if (event.keyCode === 13) { // Enter 키
			if (event.shiftKey) {
				event.preventDefault(); // 기본 줄바꿈 동작 방지
				return;
			}
			event.preventDefault(); // Enter 단독으로 기본 동작 방지
			sendMessage(); // 메시지 전송 함수 호출
		}
	});

	const enterChattingRoom = function(roomNumber) {
		$.ajax({
			url: "/nick1", type: "GET"
		})
			.then(function(mem) {
				const data = {
					roomNumber: roomNumber,
					nickname: mem.nickname,
				};
				$.ajax({
					url: "/chattingRoom-enter",
					type: "GET",
					data: data,
					success: function(room) { // 성공 시 호출됨
						initRoom(room, mem.nickname, mem.memberImg);

						// 채팅방 참가 메세지
						room.message = mem.nickname + "님이 참가하셨습니다";
						stomp.send(
							"/socket/notification/" + roomNumber, {},
							JSON.stringify(room));
					}
				});
			});
	};

	$(document).on("click", "main li", function() {
		const roomNumber = $(this).data("room_number");
		enterChattingRoom(roomNumber);
	});

	// 채팅방 나가기
	$(".chat_back").click(function() {
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
				alert("방이 터진 것 같아요");
			});

	});
	window.addEventListener('beforeunload', () => {
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
				$(".chat ul.chat_list").html("");

				info.setRoomNumber("");
				info.setNickname("");
			})

	});

	$(".chat_input_area textarea").on('drop', (e) => {
		e.preventDefault();
		$(this).attr('placeholder', '');
		const files = e.originalEvent.dataTransfer.files;

		const reader = new FileReader();

		reader.onload = function(event) {
			$(".chat_body > img").remove();
			const img = document.createElement("img");
			img.setAttribute("src", event.target.result);
			$(".chat_body").children().first().before(img);
		}
		reader.readAsDataURL(files[0]);
		$(".chat_input_area textarea").focus();
	});

	$(".chat_input_area textarea").on('click', (e) => {
		e.preventDefault();
		$(".chat_input_area textarea").focus();
	});
	//@@@@@@@@@@@@@@@@@@@@@@@@@@
	const makeDraggable = function(element) {
		$(element).on('mousedown', function(event) {
			event.preventDefault();

			const $element = $(this);
			const elementRect = $element[0].getBoundingClientRect();
			const startX = event.clientX - elementRect.left;
			const startY = event.clientY - elementRect.top;

			const onMouseMove = function(event) {
				const scrollLeft = $(window).scrollLeft();
				const scrollTop = $(window).scrollTop();

				// 드래그할 위치 계산
				const newX = event.clientX - startX + scrollLeft;
				const newY = event.clientY - startY + scrollTop;

				// 요소의 크기와 뷰포트 경계를 고려하여 위치 조정
				const elementWidth = $element.outerWidth();
				const elementHeight = $element.outerHeight();
				const viewportWidth = $(window).width();
				const viewportHeight = $(window).height();

				let finalX = newX;
				let finalY = newY;

				// 경계 검사 및 조정
				if (finalX < 0) finalX = 0;
				if (finalY < 0) finalY = 0;

				// 요소가 뷰포트의 오른쪽 또는 아래쪽으로 벗어나지 않도록 조정
				if (finalX + elementWidth > viewportWidth + scrollLeft) {
					finalX = viewportWidth + scrollLeft - elementWidth;
				}
				if (finalY + elementHeight > viewportHeight + scrollTop) {
					finalY = viewportHeight + scrollTop - elementHeight;
				}

				$("main").css({
					left: finalX - scrollLeft + 'px',
					top: finalY - scrollTop + 'px'
				});
				$(".chat").css({
					left: finalX - scrollLeft + 'px',
					top: finalY - scrollTop + 'px'
				});
			};

			const onMouseUp = function() {
				$(document).off('mousemove', onMouseMove);
				$(document).off('mouseup', onMouseUp);
			};

			$(document).on('mousemove', onMouseMove);
			$(document).on('mouseup', onMouseUp);
		});

		// 드래그 시작 시 기본 동작 방지
		$(element).on('dragstart', function() {
			return false;
		});
	};

	// 드래그 가능한 요소에 함수 적용
	makeDraggable('#chatMain nav');
	makeDraggable('#chat');

});
