const url = new URL(location.href);
const urlList = url.pathname.split("/");
const code = urlList[urlList.length - 1];

$(document).ready(function() {
	// 채팅방 세팅
	const initRoom = function(room, nickname, img) {
		stomp.send("/socket/roomList");
		info.setNickname(nickname);
		info.setRoomNumber(room.roomNumber);
		info.setImage(img);
		$(".chat .chat_title").text(room.roomName);
		chattingConnect(room.roomNumber);
		$(".chat_input_area textarea").focus();
	};


	const socket = new SockJS("http://localhost:8080/websocket");
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
	stomp.connect({}, function() {
		enterChattingRoom(code);
	});
	// 채팅방
	const info = (function() {
		let nickname = "";
		let roomNumber = "";
		let image = "";
		return {
			getNickname: () => nickname,
			setNickname: (set) => {
				nickname = set;
			},
			getRoomNumber: () => roomNumber,
			setRoomNumber: (set) => {
				roomNumber = set;
			},
			getImage: () => image,
			setImage: (set) => {
				image = set;
			},
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
		message = message
			.replaceAll("\n", "<br>")
			.replaceAll(" ", "&nbsp")
			.replaceAll("<", "&lt;")
			.replaceAll(">", "&gt;");
		const date = messageInfo.date;
		const d = new Date(date);
		const time =
			String(d.getHours()).padStart(2, "0") +
			":" +
			String(d.getMinutes()).padStart(2, "0");
		let check = info.getNickname() === nickname;
		let sender = check ? "chat_me" : "chat_other";
		nickname = check ? "" : nickname;
		let img = check ? "" : a;
		const chatHtml = `
        <li>
            <div class=${sender}>
                <div class="all">
					${img}
					<div class="nm">
					<div class="nickname">${nickname}</div>
					<div class="message">
                        <span class=chat_in_time>${time}</span>
                        <span class="chat_content">
						${messageInfo.img != null ? a : ""}
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
		const id1 = stomp.subscribe(
			"/topic/message/" + roomNumber,
			function(result) {
				const message = JSON.parse(result.body);
				chatting(message);
			}
		);
		const id2 = stomp.subscribe(
			"/topic/notification/" + roomNumber,
			function(result) {
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
			}
		);
		subscribe.push(id1);
		subscribe.push(id2);
	};
	// 메세지 보내기
	const sendMessage = function() {
		const img = $(".chat_input img");
		const message = $(".chat_input_area textarea");
		if (message.val() === "" && $(".chat_input img").length == 0) {
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
		img.remove();
	};
	$("#text").click(function() {
		sendMessage();
		$(".chat_input_area textarea").focus();
	});
	$("#cancle").click(function() {
		$(".chat_input img").remove();
		$(".chat_input_area textarea").focus();
	});
	// 채팅방 안에서 채팅 발송 로직

	$(".chat_input_area textarea").keydown(function(event) {
		if (event.keyCode === 13) {
			// Enter 키
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
			url: "/nick1",
			type: "GET",
		}).then(function(mem) {
			const data = {
				roomNumber: roomNumber,
				nickname: mem.nickname,
			};
			$.ajax({
				url: "/chattingRoom-enter",
				type: "GET",
				data: data,
				success: function(room) {
					// 성공 시 호출됨
					initRoom(room, mem.nickname, mem.memberImg);

					// 채팅방 참가 메세지
					room.message = mem.nickname + "님이 참가하셨습니다";
					stomp.send(
						"/socket/notification/" + roomNumber,
						{},
						JSON.stringify(room)
					);
				},
			});
		});
	};



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
						"/socket/notification/" + roomNumber,
						{},
						JSON.stringify(room)
					);
				}
				// 채팅방 목록 업데이트
				stomp.send("/socket/roomList");
			})
	});
	window.addEventListener("beforeunload", () => {
		$(".chat_back").trigger("click");
	});

	$(".chat_input_area textarea").on("drop", (e) => {
		e.preventDefault();
		$(".chat_input img").remove();
		const files = e.originalEvent.dataTransfer.files;

		const reader = new FileReader();

		reader.onload = function(event) {
			const img = document.createElement("img");
			img.setAttribute("src", event.target.result);
			$(".chat_input_area").before(img);
		};
		reader.readAsDataURL(files[0]);
		$(".chat_input_area textarea").focus();
	});

	//@@@@css용

	let deg = 0;
	setInterval(() => {
		$("body").css({
			background: `linear-gradient(${deg--}deg,#ff9a9e, #fecfef, #fad0c4,#fbc2eb, #a1c4fd, #e6dee9)`
		})
	}, 60);
	
	
	$(function() {
		const $canvas = $('#can');
		const canvas = $canvas.get(0);
		const ctx = canvas.getContext('2d');

		let isDrawing = false;
		let lastX = 0;
		let lastY = 0;


		let trails = [];


		function resizeCanvas() {
			canvas.width = $(window).width();
			canvas.height = $(window).height();
		}
		$(window).on('resize', resizeCanvas);
		resizeCanvas();


		function startDrawing(e) {
			isDrawing = true;
			[lastX, lastY] = [e.clientX, e.clientY];
		}


		function stopDrawing() {
			isDrawing = false;
		}
		let col = 0;
		let bol = true;
		let r = 0; // 빨강색
		let g = 0; // 초록색
		let b = 0; // 파랑색
		function draw(e) {
			if (bol) {
				col += 3;
			} else {
				col -= 3;
			}
			if (col >= 255) bol = false;
			if (col <= 1) bol = true;
			r = 255; // 빨강색
			g = col; // 초록색
			b = 255 - col; // 파랑색
			if (!isDrawing) return;

			const now = Date.now();


			trails.push({
				x1: lastX,
				y1: lastY,
				x2: e.clientX,
				y2: e.clientY,
				time: now,
				alpha: 1
			});


			ctx.beginPath();
			ctx.moveTo(lastX, lastY);
			ctx.lineTo(e.clientX, e.clientY);
			ctx.strokeStyle = `rgba(0, 0, 0, 1)`;
			ctx.lineWidth = 2;
			ctx.stroke();

			[lastX, lastY] = [e.clientX, e.clientY];
		}


		function clearTrails() {
			const now = Date.now();


			trails.forEach(trail => {
				const age = now - trail.time;

				trail.alpha = Math.max(1 - age / 500, 0);
			});


			ctx.clearRect(0, 0, canvas.width, canvas.height);


			trails.forEach(trail => {
				ctx.beginPath();
				ctx.moveTo(trail.x1, trail.y1);
				ctx.lineTo(trail.x2, trail.y2);
				ctx.strokeStyle = `rgba(${r}, ${g}, ${b}, ${trail.alpha})`;
				ctx.lineWidth = 2;
				ctx.stroke();
			});


			trails = trails.filter(trail => trail.alpha > 0);
		}

		function animate() {
			clearTrails();
			requestAnimationFrame(animate);
		}

		$canvas.on('mousemove', draw);
		$canvas.on('mouseleave', stopDrawing);
		$canvas.on('mouseenter', startDrawing);

		animate();
	});


	//@@@@

});
