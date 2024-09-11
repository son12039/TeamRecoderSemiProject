// ******************************************************
// 읽기전 변수명에 Friend 붙은 애들은 다 로케이션,타입 눌렀을때 옆에 뜨는 애들임 (변수명 생각이 안나서 이렇게 지음)
// ******************************************************

// 아이콘 처리 하는 부분
const link = document.createElement("link");
link.rel = "stylesheet";
link.href =
	"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css";
document.head.appendChild(link);

// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
const urlParams = url.searchParams;

// 상위 지역 : locationLaName, 하위 지역들 : locationSName,
// 상위 유형 : typeLaName, 하위 유형들 : typeSName
// 검색 : keyword
const searchDto = {
	page: 1,
	locationLaName: urlParams.get("locationLaName"), // 없으면 null
	locationSName: urlParams.getAll("locationSName"), // 없으면 빈배열
	typeLaName: urlParams.get("typeLaName"),
	typeSName: urlParams.getAll("typeSName"),
	keyword: urlParams.get("keyword"), // 없으면 null
};

// 화면단 -> 네비게이션 바 스타일링만 (새로고침했을때 남아있게)
if (searchDto.locationLaName !== null) {
	const check = $(
		`#locationLaNameForm input[type=checkbox][value="${searchDto.locationLaName}"]`
	)[0];
	$(check).next("label").css({
		background: "#dbdbdb",
	});
	$("#locationSNameForm").css({
		backgroundColor: "#FCA35B",
		color: "white",
		cursor: "pointer",
	});
	// 로케이션 눌렀을떄 사용자가 선택한거 옆에 보여줄 친구
	let locationLaFriend =
		"<div class='locationLaFriend'>" +
		urlParams.get("locationLaName") +
		"</div>";
	$(".locationLaStar").append(locationLaFriend);
}

if (searchDto.locationSName.length > 0) {
	for (let name of searchDto.locationSName) {
		const check = $(
			`#locationSNameForm input[type=checkbox][value="${name}"]`
		)[0];
		$(check).prop("checked", true).next("label").css({
			background: "#dbdbdb",
		});
	}
	// 로케이션 눌렀을땐 사용자가 선택한거 옆에 보여줄 친구
	let locationSLength = urlParams.getAll("locationSName").length - 1;
	let locationSFriend =
		urlParams.getAll("locationSName").length <= 1
			? "<div class='locationSFriend'>" +
			urlParams.getAll("locationSName")[0] +
			"</div>"
			: "<div class='locationSFriend'>" +
			urlParams.getAll("locationSName")[0] +
			" 외 " +
			locationSLength +
			"</div>";
	$(".locationSStar").append(locationSFriend);
}

if (searchDto.typeLaName !== null) {
	const check = $(
		`#typeLaNameSelect input[type=checkbox][value="${searchDto.typeLaName}"]`
	)[0];
	$(check).next("label").css({
		background: "#dbdbdb",
	});
	$("#typeSNameForm").css({
		backgroundColor: "#FCA35B",
		color: "white",
		cursor: "pointer",
	});
	// 로케이션 눌렀을떄 사용자가 선택한거 옆에 보여줄 친구
	let typeLaFriend =
		"<div class='typeLaFriend'>" + urlParams.get("typeLaName") + "</div>";
	$(".typeLaStar").append(typeLaFriend);
}

if (searchDto.typeSName.length > 0) {
	for (let name of searchDto.typeSName) {
		const check = $(`#typeSNameForm input[type=checkbox][value="${name}"]`)[0];
		$(check).prop("checked", true).next("label").css({
			background: "#dbdbdb",
		});
	}
	// 로케이션 눌렀을땐 사용자가 선택한거 옆에 보여줄 친구
	let typeSLength = urlParams.getAll("typeSName").length - 1;
	let typeSFriend =
		urlParams.getAll("typeSName").length <= 1
			? "<div class='typeSFriend'>" +
			urlParams.getAll("typeSName")[0] +
			"</div>"
			: "<div class='typeSFriend'>" +
			urlParams.getAll("typeSName")[0] +
			" 외 " +
			typeSLength +
			"</div>";
	$(".typeSStar").append(typeSFriend);
}
// 새로고침했을때 정보 남아있게끔
if(searchDto.keyword !== null){
	$("#keyword").val(searchDto.keyword)
}

// 결과값에 대한 걸 먼저 생각!
// locationSNameList, typeSNameList, list
let locationSNameListResult = "";
const locationSNameList = async (callback) => {
	$.ajax({
		url: "locationSList",
		type: "get",
		data: "laName=" + searchDto.locationLaName,
		success: function(result) {
			locationSNameListResult = "";
			$.each(result, function(index, item) {
				locationSNameListResult += `<input type="checkbox" value="${item}" id="${item}" name="locationSName" onchange="locationSelect(event)">
				<label for="${item}" class="locationSCss">${item}</label>`;
			});
			if (callback) callback(result);
		},
	});
};

let typeSNameListResult = "";
const typeSNameList = (callback) => {
	$.ajax({
		url: "typeSName",
		type: "get",
		data: $.param({ typeLaName: searchDto.typeLaName }),
		success: function(result) {
			typeSNameListResult = "";
			$.each(result, function(index, item) {
				typeSNameListResult += `<input type="checkbox" value="${item}" id="${item}" name="locationSName" onchange="typeSelect(event)">
					<label for="${item}" class="locationSCss">${item}</label>`;
			});
			if (callback) callback(result);
		},
	});
};

const list = (page) => {
	searchDto.page = page; // 페이징 처리까지 생각해서!
	$.ajax({
		url: "list",
		type: "get",
		data: searchDto,
		success: function(result) {
			if(result.length != 0){
				renderClubList(result);	
			}else{
				renderClubList2();	
			}			
			
		},
	});
};

// 상단 도시별 버튼 눌렀을 때
$("#locationLaNameForm").click(function() {
	$(".locationSDiv").hide();
	$(".typeLaDiv").hide();
	$(".typeSDiv").hide();
	$(".locationLaDiv").toggle();
});

// 도시별 해당 지역 눌렀을 때
// -> 하단 지역별 리스트 가져와야 하고, 리스트도 새로 가져와야 하고
$("#locationLaNameForm input[type=checkbox]").change(function() {
	// 체크 눌렀을때 그쪽 방향으로 이동
	window.scrollTo({ top: 900, left: 0, behavior: "smooth" });
	searchDto.locationSName = [];
	const laName = $(this).val();
	$(".locationSDiv").empty();
	// 새로 눌러졌을때 둘다 삭제하기
	$(".locationLaFriend").remove();
	//삭제하면서 다시 초기화
	$(".locationSFriend").remove();

	// 로케이션 눌렀을떄 사용자가 선택한거 옆에 보여줄 친구
	let locationLaFriend = "<div class='locationLaFriend'>" + laName + "</div>";

	if ($(this).prop("checked") && laName !== "") {
		$("#locationLaNameForm input[type=checkbox]").prop("checked", false)
				.next("label").css({background: "",});
		$(this).prop("checked", true)
				.next("label").css({background: "#dbdbdb",});

		// 전체보기 제외 클릭이 이루어질때
		searchDto.locationLaName = laName;

		if (urlParams.has("locationLaName")) {
			// 친구 만들기
			$(".locationLaStar").append(locationLaFriend);
			urlParams.set("locationLaName", searchDto.locationLaName);
		} else {
			// 친구 만들기
			$(".locationLaStar").append(locationLaFriend);
			urlParams.append("locationLaName", searchDto.locationLaName);
		}
		$("#locationSNameForm").css({backgroundColor: "#FCA35B",color: "white",cursor: "pointer",});
		locationSNameList(() => {
			$(".locationSDiv").html(locationSNameListResult);
		});
	} else {
		searchDto.locationLaName = null;
		urlParams.delete("locationLaName");
		$("#locationLaNameForm input[type=checkbox]").prop("checked", false)
													.next("label").css({background: "",});
		$("#locationSNameForm").css({backgroundColor: "",color: "",cursor: "",});
	}
	history.pushState({}, null, url);
	list(1);
});

// 상단 지역별 버튼 눌렀을 때
$("#locationSNameForm").click(function() {
	if (searchDto.locationLaName !== null) {
		$(".locationLaDiv").hide();
		$(".typeLaDiv").hide();
		$(".typeSDiv").hide();
		$(".locationSDiv").toggle();
	} else {
		$(".locationSDiv").hide();
	}
});

// 지역별 해당 지역들 눌렀을 때 -> 리스트만 가져오기??? 위에 url 리스트로 뿌리고
function locationSelect(event) {
	// 체크 눌렀을때 그쪽 방향으로 이동
	window.scrollTo({ top: 900, left: 0, behavior: "smooth" });
	const locationSName = $(event.target).val();
	//다시 클릭했으니 초기화 하고 새로운 정보넣기
	$(".locationSFriend").remove();
	let locationSLength = "";
	let loctionSFriend = "";

	if ($(event.target).is(":checked")) {
		urlParams.append("locationSName", locationSName);
		$(event.target).prop("checked", true).next("label").css({background: "#dbdbdb",});
		searchDto.locationSName.push(locationSName);
		locationSLength = urlParams.getAll("locationSName").length - 1;
		loctionSFriend =
			urlParams.getAll("locationSName").length <= 1
				? "<div class='locationSFriend'>" +urlParams.getAll("locationSName")[0] +"</div>"
				: "<div class='locationSFriend'>" +urlParams.getAll("locationSName")[0] +" 외 " +locationSLength +"</div>";
		// 로케이션 눌렀을땐 사용자가 선택한거 옆에 보여줄 친구
		$(".locationSStar").append(loctionSFriend);
	} else {
		urlParams.delete("locationSName");
		searchDto.locationSName = [];
		const inputAll = $(event.target).parent().find("input[type=checkbox]");
		for (let input of inputAll) {
			if (input.checked) {
				urlParams.append("locationSName", input.value);
				searchDto.locationSName.push(input.value);
			}
		}
		$(event.target).prop("checked", false).next("label").css({background: "#f3f3f3",});
		locationSLength = urlParams.getAll("locationSName").length - 1;
		loctionSFriend =
			urlParams.getAll("locationSName").length <= 1  
			? "<div class='locationSFriend'>" +urlParams.getAll("locationSName")[0] +"</div>" 
			: "<div class='locationSFriend'>" +urlParams.getAll("locationSName")[0] +" 외 " +locationSLength +"</div>";
		// 로케이션 눌렀을땐 사용자가 선택한거 옆에 보여줄 친구 (눌린 버튼 눌럿을때도 변화게끔)
		$(".locationSStar").append(loctionSFriend);
		if (locationSLength === -1) {
			$(".locationSFriend").remove();
		}
	}

	history.pushState({}, null, url);
	list(1);
}

// 상단 타입별 버튼 눌렀을 때
$("#typeLaNameSelect").click(function() {
	$(".locationSDiv").hide();
	$(".locationLaDiv").hide();
	$(".typeSDiv").hide();
	$(".typeLaDiv").toggle();
});
// 타입별 해당 분류 눌렀을 때
$("#typeLaNameSelect input[type=checkbox]").change(function() {
	// 체크 눌렀을때 그쪽 방향으로 이동
	window.scrollTo({ top: 900, left: 0, behavior: "smooth" });
	searchDto.typeSName = [];
	const typeLa = $(this).val();
	$(".typeSDiv").empty();
	// 클릭할때 친구들 지우기
	$(".typeLaFriend").remove();
	$(".typeSFriend").remove();
	// 로케이션 눌렀을떄 사용자가 선택한거 옆에 보여줄 친구
	let typeLaFriend = "<div class='typeLaFriend'>" + typeLa + "</div>";

	if ($(this).prop("checked") && typeLa !== "") {
		$("#typeLaNameSelect input[type=checkbox]").prop("checked", false).next("label").css({background: "",});
		$(this).prop("checked", true).next("label").css({background: "#dbdbdb",});

		searchDto.typeLaName = typeLa;

		if (urlParams.has("typeLaName")) {
			// 친구 만들기
			$(".typeLaStar").append(typeLaFriend);
			urlParams.set("typeLaName", searchDto.typeLaName);
		} else {
			// 친구 만들기
			$(".typeLaStar").append(typeLaFriend);
			urlParams.append("typeLaName", searchDto.typeLaName);
		}
		$("#typeSNameForm").css({backgroundColor: "#FCA35B",color: "white",cursor: "pointer",});
		typeSNameList(() => {
			$(".typeSDiv").html(typeSNameListResult);
		});
	} else {
		searchDto.typeLaName = null;
		urlParams.delete("typeLaName");
		$("#typeLaNameSelect input[type=checkbox]").prop("checked", false).next("label").css({background: "",});
		$("#typeSNameForm").css({backgroundColor: "",color: "",	cursor: "",});
	}
	history.pushState({}, null, url);
	list(1);
});

// 상단 분류별 버튼 눌렀을 때
$("#typeSNameForm").click(function() {
	if (searchDto.typeLaName !== null) {
		$(".locationSDiv").hide();
		$(".locationLaDiv").hide();
		$(".typeLaDiv").hide();
		$(".typeSDiv").toggle();
	} else {
		$(".typeSDiv").hide();
	}
});
// 분류별 해당 분류별들 눌렀을 때
function typeSelect(event) {
	// 체크 눌렀을때 그쪽 방향으로 이동
	window.scrollTo({ top: 900, left: 0, behavior: "smooth" });
	let typeSName = $(event.target).val();
	//다시 클릭했으니 초기화 하고 새로운 정보넣기
	$(".typeSFriend").remove();
	let typeSLength = "";
	let typeSFriend = "";

	if ($(event.target).is(":checked")) {
		urlParams.append("typeSName", typeSName);
		$(event.target).prop("checked", true).next("label").css({background: "#dbdbdb",});
		searchDto.typeSName.push(typeSName);
		typeSLength = urlParams.getAll("typeSName").length - 1;
		typeSFriend =
			urlParams.getAll("typeSName").length <= 1
				? "<div class='typeSFriend'>" +urlParams.getAll("typeSName")[0] +"</div>"
				: "<div class='typeSFriend'>" +urlParams.getAll("typeSName")[0] +" 외 " +typeSLength +"</div>";
		// 로케이션 눌렀을땐 사용자가 선택한거 옆에 보여줄 친구
		$(".typeSStar").append(typeSFriend);
	} else {
		urlParams.delete("typeSName");
		searchDto.typeSName = [];
		const inputAll = $(event.target).parent().find("input[type=checkbox]");
		for (let input of inputAll) {
			if (input.checked) {
				urlParams.append("typeSName", input.value);
				searchDto.typeSName.push(input.value);
			}
		}
		$(event.target).prop("checked", false).next("label").css({background: "#f3f3f3",});
		typeSLength = urlParams.getAll("typeSName").length - 1;
		typeSFriend =
			urlParams.getAll("typeSName").length <= 1
				? "<div class='typeSFriend'>" +urlParams.getAll("typeSName")[0] +"</div>"
				: "<div class='typeSFriend'>" +urlParams.getAll("typeSName")[0] +" 외 " +	typeSLength +"</div>";
		// 로케이션 눌렀을땐 사용자가 선택한거 옆에 보여줄 친구
		$(".typeSStar").append(typeSFriend);
		if (typeSLength === -1) {
			$(".typeSFriend").remove();
		}
	}
	history.pushState({}, null, url);
	list(1);
}

// 엔터 쳐도 검색 되게끔
$(".locationTypeSearch input").keyup((e) => {
	if (e.key === "Enter") {
		search();
	}
});

// 검색 버튼 눌렀을 때
function search() {
	urlParams.delete("keyword");
	const keyword = $("#keyword").val();
	urlParams.append("keyword", keyword);
	searchDto.keyword = keyword;
	history.pushState({}, null, url);
	list(1);
}
function renderClubList2(){
	let div = $(".membership-list");
	div.empty();
	div.append(`<div class="noMember">해당 정보가 없습니다.. 😥</div>`);
}

function renderClubList(clubList) {
	let div = $(".membership-list");
	div.empty();

	$.each(clubList, function(index, club) {
		let ajaxLocationType = '<div class="membership-card">';
		if (club.membershipDate > today30 && today > club.membershipDate) {
			ajaxLocationType +=
				'<img alt="" src="http://192.168.10.51:8081/sungil/2%ED%8A%B8.png" class="new">';
		}
		ajaxLocationType += '<div class="membership-img">' +
			'<a href="/' +
			club.membershipCode +
			'">';
		if (club.membershipImg) {
			ajaxLocationType +=
				'<img src="http://192.168.10.51:8081/membership/' +
				club.membershipCode +
				"/" +
				club.membershipImg +
				'">';
		} else {
			ajaxLocationType +=
				'<img src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">';
		}

		ajaxLocationType +=
			"</a>" +
			"</div>" +
			'<div class="membership-info">' +
			'<h1 class="membership-name">' +
			club.membershipName +
			"</h1>" +
			"<h2>" +
			club.membershipSimpleText +
			"</h2>" +
			'<a href="/userInfo/' +
			club.nickname +
			'">' +
			'<div class="host">';

		if (club.memberImg) {
			ajaxLocationType +=
				'<div class="hostImg">' +
				'<span style="color:rgb(252, 177, 3)"><i class="fa-solid fa-crown"></i></span>' +
				'<img class="user-img" src="http://192.168.10.51:8081/member/' +
				club.id +
				"/" +
				club.memberImg +
				'">' +
				"</div>";
		} else {
			ajaxLocationType +=
				'<div class="hostImg">' +
				'<span style="color:rgb(252, 177, 3)"><i class="fa-solid fa-crown"></i></span>' +
				'<img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">' +
				"</div>";
		}

		ajaxLocationType +=
			"<h2>호스트 : " +
			club.nickname +
			"</h2>" +
			'<input type="hidden" name="code" value="' +
			club.membershipCode +
			'">' +
			'<div class="memberMannerIndex">';

		if (club.memberManner < 30) {
			ajaxLocationType +=
				'<span style="color: red"><i class="fa-solid fa-face-angry fa-2x"></i></span>' +
				"<p>" +
				club.memberManner +
				"℃</p>";
		} else if (club.memberManner >= 30 && club.memberManner <= 40) {
			ajaxLocationType +=
				'<span style="color: rgb(252, 177, 3)"><i class="fa-solid fa-face-smile fa-2x"></i></span>' +
				"<p>" +
				club.memberManner +
				"℃</p>";
		} else {
			ajaxLocationType +=
				'<span style="color: green"><i class="fa-solid fa-face-grin fa-2x"></i></span>' +
				"<p>" +
				club.memberManner +
				"℃</p>";
		}

		ajaxLocationType +=
			"</div>" +
			"</div>" +
			"</a>" +
			'<h3><i class="fa-solid fa-users"></i> : ' +
			club.count +
			"/" +
			club.membershipMax +
			"</h3>" +
			'<div class="locationTypeBox">' +
			'<div class="location">';

		$.each(club.locations, function(i, location) {
			ajaxLocationType +=
				'<div class="locationList"># ' +
				location.locLaName +
				" " +
				location.locSName +
				"</div>";
		});

		ajaxLocationType += "</div>" + "<br>" + '<div class="type">';

		$.each(club.types, function(i, type) {
			ajaxLocationType += '<div class="typeList">' + type.typeSName + "</div>";
		});

		ajaxLocationType += "</div>" + "</div>" + "</div>";

		div.append(ajaxLocationType);
	});
}

// 초기화 (태초마을로 가자)
function reset() {
	window.scrollTo({ top: 900, left: 0, behavior: "smooth" });
	$(".locationSDiv").hide();
	$(".typeLaDiv").hide();
	$(".typeSDiv").hide();
	$(".locationLaDiv").hide();
	urlParams.delete("locationLaName");
	urlParams.delete("locationSName");
	urlParams.delete("typeLaName");
	urlParams.delete("typeSName");
	urlParams.delete("keyword");
	searchDto.locationLaName = null;
	searchDto.locationSName = [];
	searchDto.typeLaName = null;
	searchDto.typeSName = [];
	searchDto.keyword = null;
	$("#keyword").val("");
	if (searchDto.locationLaName === null) {
		const check = $(`#locationLaNameForm input[type=checkbox]`);
		$(check).next("label").css({background: "",});
		$("#locationSNameForm").css({backgroundColor: "",color: "",cursor: "",});
		$(".locationLaFriend").remove();
	}
	if (searchDto.locationSName.length == 0) {
		for (let name of searchDto.locationSName) {
			const check = $(`#locationSNameForm input[type=checkbox]`);
			$(check).prop("checked", true).next("label").css({background: "",});
		}
		$(".locationSFriend").remove();
	}
	if (searchDto.typeLaName === null) {
		const check = $(`#typeLaNameSelect input[type=checkbox]`);
		$(check).next("label").css({background: "",});
		$("#typeSNameForm").css({backgroundColor: "",color: "",cursor: "",});
		$(".typeLaFriend").remove();
	}
	if (searchDto.typeSName.length == 0) {
		for (let name of searchDto.typeSName) {
			const check = $(`#typeSNameForm input[type=checkbox]`);
			$(check).prop("checked", true).next("label").css({background: "",});
		}
		$(".typeSFriend").remove();
	}
	
	history.pushState({}, null, url);
	list(1);
}



