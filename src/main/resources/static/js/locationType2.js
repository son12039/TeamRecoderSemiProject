

// 비상용



// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
const urlParams = url.searchParams;

// 상위 지역 : locationLaName, 하위 지역들 : locationSName, 
// 상위 유형 : typeLaName, 하위 유형들 : typeSName

// 파라미터값 존재여부 체크 & 파라미터 값 가져오기
// 상위 지역 필터 
$("#locationLaNameForm").click(function() {
	$('.locationSDiv').hide();
	$('.typeLaDiv').hide();
	$('.typeSDiv').hide();
	$('.locationLaDiv').toggle();
})
$("#locationSNameForm").click(function() {
	if ($(".locationSDiv").css("display") == "none" && laName !== "" && laName !== null) {
		$('.locationLaDiv').hide();
		$('.typeLaDiv').hide();
		$('.typeSDiv').hide();
		$('.locationSDiv').toggle();
	} else {
		$('.locationSDiv').hide();
	}
})

$("#typeLaNameSelect").click(function() {
	$('.locationSDiv').hide();
	$('.locationLaDiv').hide();
	$('.typeSDiv').hide();
	$('.typeLaDiv').toggle();
})
$("#typeSNameForm").click(function() {
	if ($(".typeSDiv").css("display") == "none" && typeLLa !== "" && typeLLa !== null) {
		$('.locationSDiv').hide();
		$('.locationLaDiv').hide();
		$('.typeLaDiv').hide();
		$('.typeSDiv').toggle();
	} else {
		$('.typeSDiv').hide();
	}
})
let laName = ""
let locationLaFriend = ""
$("#locationLaNameForm input[type=checkbox]").change(function() {
	// 아이콘 처리 하는 부분
	const link = document.createElement('link');
	link.rel = 'stylesheet';
	link.href = 'https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css';
	document.head.appendChild(link);

	urlParams.delete("locationLaName");
	urlParams.delete("locationSName");
	// ajax 체인걸어놔서 두번째까지는 안와서 변수선언
	const location = $(this);
	//체인지하면서 css 변경
	// 체크 이벤트 
	laName = $(this).val() == laName ? "" : $(this).val();

	// 타입 눌렀을떄 사용자가 선택한거 옆에 보여줄 친구
	locationLaFriend = "<div class='locationLaFriend'>" + $(this).val() + "</div>";

	if ($(this).prop('checked')) {
		//console.log($(this).val()) // 서울 경기 부산 ...
		// 체크 걸면서 css도 같이 주기
		// 친구 지우기
		$(".locationLaFriend").remove()
		$('#locationLaNameForm input[type=checkbox]')
			.prop('checked', false)
			.next("label")
			.css({
				background: ""
			});
		$(this).prop('checked', true)
		if (laName !== '') {
			urlParams.append('locationLaName', laName);
			$(this).prop('checked', true)
				.next("label")
				.css({
					background: "#dbdbdb"
				})
			$("#locationSNameForm").css({
				backgroundColor: "#FCA35B",
				color: "white",
				cursor: "pointer"
			})
			// 친구 만들기
			$(".locationLaStar").append(locationLaFriend)
			//전체보기 눌렀을때
		} else if (laName === '') {
			$("#locationSNameForm").css({
				backgroundColor: "",
				color: "",
				cursor: ""
			})
		}
	} else {
		// 버튼 자기 자신 선택했을때 지우기
		$(this).prop('checked', false)
			.next("label")
			.css({
				background: ""
			})
		$("#locationSNameForm").css({
			backgroundColor: "",
			color: "",
			cursor: ""
		})
		// 친구 지우기
		$(".locationLaFriend").remove()
	}


	// 체크 눌렀을때 그쪽 방향으로 이동
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
	// 새로고침안하고 url로 보내기
	history.pushState({}, null, url);
	let list = "";
	page = 1;
	$.ajax({
		url: 'list',
		type: 'get',
		data: {
			page: page,
			locationLaName: urlParams.get("locationLaName"),
			locationSName: urlParams.getAll("locationSName"),
			typeLaName: urlParams.get("typeLaName"),
			typeSName: urlParams.getAll("typeSName")
		},
		success: function(clubList1) {
			renderClubList(clubList1);
			$.ajax({
				url: 'locationSList',
				type: 'get',
				data: "laName=" + laName,
				success: function(locationS) {
					$(".locationSDiv").empty();
					if (location.prop('checked')) {
						$.each(locationS, function(index, item) {
							list += `
								<input type="checkbox" value="${item}" id="${item}"
									name="locationSName">
								<label for="${item}" class="locationSCss">${item}</label>`
						})
						$(".locationSDiv").html(list);
					}
					// 소분류 친구 만들기
					let locationSList = [];
					let locationSFriend = "";
					$("#locationSNameForm input[type=checkbox]").change(function() {
						$(".locationSFriend").remove()
						window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
						const locationSName = $(this).val();
						if ($(this).is(':checked')) {
							$(".locationSFriend").remove()
							// 친구들 배열에 넣어주기
							locationSList.push($(this).val());
							$(this).prop("checked", true)
								.next("label")
								.css({
									background: "#dbdbdb"
								})

							urlParams.append('locationSName', locationSName);
						} else {
							$(".locationSFriend").remove()
							$(this).prop("checked", false)
								.next("label")
								.css({
									background: "#f3f3f3"
								})
							// 여기서부터 집에서 시작하기 (친구들 만들기)
							for (let i = 0; i < locationSList.length; i++) {
								if (locationSList[i] == ($(this).val())) {
									locationSList.splice(i, 1);
									urlParams.delete("locationSName");
								}
							}
							urlParams.delete('locationSName');
							const inputAll = $(this).parent().find("input[type=checkbox]");
							for (let input of inputAll) {
								if (input.checked) {
									urlParams.append("locationSName", input.value)

								}
							}
						}
						let locationSVal = locationSList.length - 1
						locationSFriend = locationSList.length <= 1 ? "<div class='locationSFriend'>" + locationSList[0] + "</div>" : "<div class='locationSFriend'>" + locationSList[0] + " 외 " + locationSVal + "</div>";
						$(".locationSBox").append(locationSFriend);

						if (locationSVal < 0) {
							$(".locationSFriend").remove()
						}

						history.pushState({}, null, url);
						// 지역 작은놈들 페이징
						page = 1;
						$.ajax({
							url: 'list',
							type: 'get',
							data: {
								page: page,
								locationLaName: urlParams.get("locationLaName"),
								locationSName: urlParams.getAll("locationSName"),
								typeLaName: urlParams.get("typeLaName"),
								typeSName: urlParams.getAll("typeSName")
							},
							success: function(clubList2) {
								renderClubList(clubList2);
							}
						})
					});
				}
			})
		}
	});
});



// 로케이션
$("#locSNameAll").change(function() {
	if ($("#locSNameAll").is(":checked")) {
		for (let item of $('#locationSNameForm input[name="locationSName"]')) {
			item.checked = false;
		}
	}
});
$('#locationSNameForm input[name="locationSName"]').change(function() {
	if ($("#locSNameAll").is(":checked")) {
		$("#locSNameAll")[0].checked = false;
	}
})
// 타입
$("#typeSNameAll").change(function() {
	if ($("#typeSNameAll").is(":checked")) {
		for (let item of $('#typeSNameForm input[name="typeSName"]')) {
			item.checked = false;
		}
	}
});

$('#typeSNameForm input[name="typeSName"]').change(function() {
	if ($("#typeSNameAll").is(":checked")) {
		$("#typeSNameAll")[0].checked = false;
	}
})

/*타입 클릭시*/
let typeLLa = ""
let typeSNameFreind = ""
$("#typeLaNameSelect input[type=checkbox]").change(function() {
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
	urlParams.delete('typeLaName')
	urlParams.delete('typeSName')

	const typeLaName = $(this).val();
	const typeLa = $(this);
	typeLLa = typeLaName == typeLLa ? "" : typeLaName;
	// 타입 눌렀을떄 사용자가 선택한거 옆에 보여줄 친구
	let typeLaFriend = "<div class='typeLaFriend'>" + $(this).val() + "</div>";
	if ($(this).prop('checked')) {
		// 친구 지우기
		$(".typeLaFriend").remove()
		$('#typeLaNameSelect input[type=checkbox]')
			.prop('checked', false)
			.next("label")
			.css({
				background: ""
			});
		$(this).prop('checked', true)
		$("#typeSNameForm").css({
			backgroundColor: "#FCA35B",
			color: "white",
			cursor: "pointer"
		})

		if (typeLaName !== '') {
			urlParams.append('typeLaName', typeLaName)
			// 친구 만들기
			$(".typeLaStar").append(typeLaFriend)
			$(this).prop('checked', true)
				.next("label")
				.css({
					background: "#dbdbdb"
				})
			//전체보기 눌렀을때
		} else if (typeLaName === '') {
			$("#typeSNameForm").css({
				backgroundColor: "",
				color: "",
				cursor: ""
			})
		}
	} else {
		$(this).prop('checked', false)
			.next("label")
			.css({
				background: ""
			})
		$("#typeSNameForm").css({
			backgroundColor: "",
			color: "",
			cursor: ""
		})
		// 친구 지우기
		$(".typeLaFriend").remove()
	}


	history.pushState({}, null, url);
	let list = "";
	page = 1;
	$.ajax({
		url: "list",
		type: "get",
		data: {
			page: page,
			locationLaName: urlParams.get("locationLaName"),
			locationSName: urlParams.getAll("locationSName"),
			typeLaName: urlParams.get("typeLaName"),
			typeSName: urlParams.getAll("typeSName")
		},
		success: function(clubList1) {
			renderClubList(clubList1);
			$.ajax({
				url: 'typeSName',
				type: 'get',
				data: $.param({ typeLaName: typeLaName }),
				success: function(result) {
					$(".typeSDiv").empty();
					if (typeLa.prop('checked')) {
						$.each(result, function(index, item) {
							list += `<input type="checkbox" value="${item}" id="${item}"
							name="typeSName">
							<label for="${item}" id="typeSCss">${item}</label>`
						})
						$(".typeSDiv").html(list)

					}
					// 소분류 친구 만들기
					let typeSList = [];
					let tpyeSFreind = "";
					$("#typeSNameForm input[type=checkbox]").change(function() {
						$(".typeSFriend").remove()
						window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
						const typeSName = $(this).val();
						if ($(this).is(':checked')) {
							$(".typeSFriend").remove()
							// 클릭한 애들넣기
							typeSList.push($(this).val())
							urlParams.append('typeSName', typeSName);
							$(this).prop("checked", true)
								.next("label")
								.css({
									backgroundColor: "#dbdbdb"
								});
						} else {
							$(".typeSFriend").remove()
							urlParams.delete('typeSName')
							//배열로 언체크드 되면 삭제하기
							for (let i = 0; i < typeSList.length; i++) {
								if (typeSList[i] == ($(this).val())) {
									typeSList.splice(i, 1);
									urlParams.delete('typeSName')
								}
							}
							$(this).prop("checked", false)
								.next("label")
								.css({
									backgroundColor: ""
								});
							const inputAll = $(this).parent().find("input[type=checkbox]");
							for (let input of inputAll) {
								if (input.checked) {
									urlParams.append('typeSName', input.value);
								}
							}
						}
						let typeSVal = typeSList.length - 1
						tpyeSFreind = typeSList.length <= 1 ? "<div class='typeSFriend'>" + typeSList[0] + "</div>" : "<div class='typeSFriend'>" + typeSList[0] + " 외 " + typeSVal + "</div>";
						// 친구 넣기
						$(".typeSBox").append(tpyeSFreind);
						// 0일때 친구 지우기
						if (typeSList.length === 0) {
							$(".typeSFriend").remove()
						}
						history.pushState({}, null, url);
						// 타입 작은놈들
						page = 1;
						$.ajax({
							url: 'list',
							type: 'get',
							data: {
								page: page,
								locationLaName: urlParams.get("locationLaName"),
								locationSName: urlParams.getAll("locationSName"),
								typeLaName: urlParams.get("typeLaName"),
								typeSName: urlParams.getAll("typeSName")
							},
							success: function(clubList2) {
								renderClubList(clubList2);
							}
						})
					})
				}
			})
		}
	})
})



function renderClubList(clubList) {
	let div = $(".membership-list");
	div.empty();
	$.each(clubList, function(index, club) {
		let ajaxLocationType =
			'<div class="membership-card">' +
			'<div class="membership-img">' +
			'<a href="/' + club.membershipCode + '">';
		if (club.membershipImg) {
			ajaxLocationType += '<img src="http://192.168.10.51:8081/membership/' + club.membershipCode + '/' + club.membershipImg + '">';
		} else {
			ajaxLocationType += '<img src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">';
		}
		ajaxLocationType += '</a>' +
			'</div>' +
			'<div class="membership-info">' +
			'<h1 class="membership-name">' + club.membershipName + '</h1>' +
			'<h2>' + club.membershipSimpleText + '</h2>' +

			'<a href="/userInfo/' + club.nickname + '">' +
			'<div class="host">';
		if (club.memberImg) {
			ajaxLocationType += '<img class="user-img" src="http://192.168.10.51:8081/member/' + club.id + '/' + club.memberImg + '">';
		} else {
			ajaxLocationType += '<img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">';
		}
		ajaxLocationType += '<h2>호스트 : ' + club.nickname + '</h2>' +
			'<input type="hidden" name="code" value="' + club.membershipCode + '">' +
			'<div class="memberMannerIndex">';

		if (club.memberManner < 30) {
			ajaxLocationType += '<span style="color: red"><i class="fa-solid fa-face-angry fa-2x"></i></span>' +
				'<p>' + club.memberManner + '℃</p>';
		} else if (club.memberManner >= 30 && club.memberManner <= 40) {
			ajaxLocationType += '<span style="color: rgb(252, 177, 3)"><i class="fa-solid fa-face-smile fa-2x"></i></span>' +
				'<p>' + club.memberManner + '℃</p>';
		} else {
			ajaxLocationType += '<span style="color: green"><i class="fa-solid fa-face-grin fa-2x"></i></span>' +
				'<p>' + club.memberManner + '℃</p>';
		}
		ajaxLocationType += '</div>' +
			'</div>' +
			'</a>' +
			'<h3><i class="fa-solid fa-users"></i> : ' + club.count + '/' + club.membershipMax + '</h3>' +
			'<div class="locationTypeBox">' +
			'<div class="location">';
		$.each(club.locations, function(i, location) {
			ajaxLocationType += '<div class="locationList"># ' + location.locLaName + ' ' + location.locSName + '</div>';
		});
		ajaxLocationType += '</div>' + '<br>' +
			'<div class="type">';
		$.each(club.types, function(i, type) {
			ajaxLocationType += '<div class="typeList">' + type.typeSName + '</div>';
		});
		ajaxLocationType += '</div>' +
			'</div>' +
			'</div>';
		div.append(ajaxLocationType);
	});
}


window.addEventListener('load', () => {
	const baseUrl = window.location.origin + '/';
	if (window.location.search) {
		window.location.href = baseUrl;
	}
});




