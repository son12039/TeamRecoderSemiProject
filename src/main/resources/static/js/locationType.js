// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
const urlParams = url.searchParams;

// 상위 지역 : locationLaName, 하위 지역들 : locationSName, 
// 상위 유형 : typeLaName, 하위 유형들 : typeSName

// 파라미터값 존재여부 체크 & 파라미터 값 가져오기
// 상위 지역 필터

let searchAjax = {
	locLaName: null,
	locSName: [],
	typeLaName: null,
	typeSName: [],
}


if (urlParams.has("locationLaName")) {
	window.scrollTo({ top: 630, left: 0, behavior: 'smooth' });// 클릭시 해당 위치로
	const locationLaName = urlParams.get('locationLaName');
	const list = $('#locationLaNameForm input');
	for (let item of list) {
		if (locationLaName === item.value) {
			item.setAttribute('checked', true);
		}
	}
}
if (urlParams.has("locationSName")) {
	window.scrollTo({ top: 630, left: 0, behavior: 'smooth' });
	const locationSName = urlParams.getAll('locationSName');
	const list = $('#locationSNameForm input');
	for (let item of list) {
		if (locationSName.includes(item.value)) {
			item.setAttribute("checked", true);
		}
	}
}

$("#locationLaNameForm input[type=checkbox]").change(function() {
	urlParams.delete("locationLaName");
	urlParams.delete("locationSName");
	const laName = $(this).val();
	if ($(this).prop('checked')) {
		$('#locationLaNameForm input[type=checkbox]').prop('checked', false);
		$(this).prop('checked', true);
		if (laName !== '전체보기') {
			urlParams.append('locationLaName', laName);
		} else {
			urlParams.delete('locationLaName');
		}
	} else {
		urlParams.delete('locationLaName');
	}
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
	history.pushState({}, null, url);
	searchAjax.locLaName = $(this).val();
	console.log(searchAjax.locLaName)
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
		success: function(clubList) {
			renderClubList(clubList);
			console.log(searchAjax);
			$.ajax({
				url: 'locationSList',
				type: 'get',
				data: searchAjax,
				success: function(location) {
					console.log(location);
					let locationS = $(".locationSNameForm");
				}
			})
		}
	});
});



$("#locationSNameForm input[type=checkbox]").change(function() {
	const locationSName = $(this).val();

	if (locationSName === '전체보기') {
		urlParams.delete('locationSName');
	} else if ($(this).is(':checked')) {
		urlParams.append('locationSName', locationSName);
	} else {
		urlParams.delete('locationSName');
		const inputAll = $(this).parent().find("input[type=checkbox]");
		for (let input of inputAll) {
			if (input.checked) {
				urlParams.append("locationSName", input.value);
			}
		}

	}
	location.href = url;

});



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



/*타입 클릭시*/
$("#typeLaNameSelect input[type=checkbox]").change(function() {
	urlParams.delete('typeLaName')
	urlParams.delete('typeSName')
	const typeLaName = $(this).val();

	if (typeLaName != '전체보기') {
		urlParams.append('typeLaName', typeLaName)
	}
	location.href = url;
})


$("#typeSNameForm input[type=checkbox]").change(function() {
	const typeSName = $(this).val();
	console.log(typeSName);
	if (typeSName === '전체보기') {
		urlParams.delete('typeSName');
	} else if ($(this).is(':checked')) {
		urlParams.append('typeSName', typeSName);
	} else {
		urlParams.delete('typeSName');
		const inputAll = $(this).parent().find("input[type=checkbox]");
		for (let input of inputAll) {
			if (input.checked) {
				urlParams.append('typeSName', input.value);
			}
		}
	}
	location.href = url;
})



if (urlParams.has("typeLaName")) {
	//window.scrollTo({top:630, left:0, behavior:'smooth'});
	let typeLaName = urlParams.get("typeLaName")
	let list = $('#typeLaNameSelect input')
	for (let item of list) {
		if (typeLaName === item.value) {
			item.setAttribute("checked", true)
		}
	}
}
if (urlParams.has("typeSName")) {
	//window.scrollTo({top:630, left:0, behavior:'smooth'});
	const typeSName = urlParams.getAll('typeSName');
	const list = $('#typeSNameForm input');
	for (let item of list) {
		if (typeSName.includes(item.value)) {
			item.setAttribute("checked", true);
		}
	}
}

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



function renderClubList(clubList) {
	let div = $(".membership-list");
	div.empty();
	$.each(clubList, function(index, club) {
		let ajaxLocationType =
			'<div class="membership-card">' +
			'<div class="membership-img">' +
			'<a href="/' + club.membershipCode + '">';
		if (club.membershipImg != null) {
			ajaxLocationType += '<img src="http://192.168.10.51:8081/membership/' + club.membershipCode + '/' + club.membershipImg + '">';
		} else {
			ajaxLocationType += '<img src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">';
		}
		ajaxLocationType += '</a>' +
			'</div>' +
			'<div class="membership-info">' +
			'<h1 class="membership-name">' + club.membershipName + '</h1>' +
			'<h2>' + club.membershipSimpleText + '</h2>' +
			'<h3>멤버수 : ' + club.count + '/' + club.membershipMax + '</h3>' +
			'<div id="host">';
		if (club.memberImg != null) {
			ajaxLocationType += '<img class="user-img" src="http://192.168.10.51:8081/member/' + club.id + '/' + club.memberImg + '">';
		} else {
			ajaxLocationType += '<img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">';
		}
		ajaxLocationType += '<h2>호스트 : ' + club.nickname + '</h2>' +
			'<input type="hidden" name="code" value="' + club.membershipCode + '">' +
			'<br>' +
			'</div>' +
			'<div class="locationTypeBox">' +
			'<div class="location">';
		for (let location of club.locations) {
			ajaxLocationType += '<div class="locationList">' + '# ' + location.locLaName + location.locSName + '</div>';
		}
		ajaxLocationType += '</div>' + '<br>' +
			'<div class="type">';
		for (let type of club.types) {
			ajaxLocationType += '<div class="typeList">' + type.typeSName + '</div>';
		}
		ajaxLocationType += '</div>' +
			'</div>' +
			'</div>' +
			'</div>';

		div.append(ajaxLocationType);
	});
}








