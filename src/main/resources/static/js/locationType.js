// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
const urlParams = url.searchParams;

// 상위 지역 : locationLaName, 하위 지역들 : locationSName, 
// 상위 유형 : typeLaName, 하위 유형들 : typeSName

// 파라미터값 존재여부 체크 & 파라미터 값 가져오기
// 상위 지역 필터



if (urlParams.has("locationLaName")) {
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });;// 클릭시 해당 위치로
	const locationLaName = urlParams.get('locationLaName');
	const list = $('#locationLaNameForm input');
	for (let item of list) {
		if (locationLaName === item.value) {
			item.setAttribute('checked', true);
		}
	}
}
if (urlParams.has("locationSName")) {
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
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
	// ajax 체인걸어놔서 두번째까지는 안와서 변수선언
	const location = $(this);
	//체인지하면서 css 변경
	// 체크 이벤트 
	const laName = $(this).val();
	if ($(this).prop('checked')) {
		// 체크 걸면서 css도 같이 주기
		$('#locationLaNameForm input[type=checkbox]')
							.prop('checked', false)
							.next("label")
							.css({backgroundColor : "",
								  color : ""
							});

		$(this).prop('checked', true)
				.next("label")
				.css({backgroundColor : "rgb(255, 235, 187)",
					  color : "#fca35b"
				});
		if (laName !== '초기화') {
			urlParams.append('locationLaName', laName);
		}
	}
	// 체크 해제했을때 스타일 삭제


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
									<label for="${item}" class="locationTypeCss">${item}</label>`
						})
						$(".locationSDiv").html(list);
					}
					if(location.val() === '초기화'){
						list+=`<div class="classiFication">지역을 선택해주세요</div>`
						$(".locationSDiv").html(list)
					}
					$("#locationSNameForm input[type=checkbox]").change(function() {
						window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
						const locationSName = $(this).val();
						if ($(this).is(':checked')) {
							$(this).prop("checked",true)
							.next("label")
							.css({backgroundColor : "rgb(255, 235, 187)",
								  color : "#fca35b"
							});
							urlParams.append('locationSName', locationSName);
						} else {
							$(this).prop("checked",false)
							.next("label")
							.css({backgroundColor : "",
								  color : ""
							});							
							urlParams.delete('locationSName');
							const inputAll = $(this).parent().find("input[type=checkbox]");
							for (let input of inputAll) {
								if (input.checked) {
									urlParams.append("locationSName", input.value)
									
								}
							}
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
$("#typeLaNameSelect input[type=checkbox]").change(function() {
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
	urlParams.delete('typeLaName')
	urlParams.delete('typeSName')
	const typeLaName = $(this).val();
	const typeLa = $(this);
	if ($(this).prop('checked')) {
		$('#typeLaNameSelect input[type=checkbox]')
							.prop('checked', false)
							.next('label')
							.css({backgroundColor : "",
								  borderRadius : ""
							});
		$(this).prop('checked', true)
					.next('label')
					.css({backgroundColor : "#fca35b",
						  borderRadius : "20px"
					});
		if (typeLaName !== '전체보기') {
			urlParams.append('typeLaName', typeLaName)
		}else{
			$(this).prop('checked', true)
					.next('label')
					.css({backgroundColor : "",
						  borderRadius : ""
					});	
		}
	}
	if(!$(this).prop('checked')){
		$(this).prop('checked',false)
						.next('label')
						.css({backgroundColor : "",
							  borderRadius : ""
						});
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
					$("#typeSNameForm").empty();
					
					if (typeLa.prop('checked')) {
						$("#typeSNameForm").css({height : "40px"})
						$.each(result, function(index, item) {
							list += `<input type="checkbox" value="${item}" id="${item}"
							name="typeSName">
							<label for="${item}" class="typeSCss">${item}</label>`
						})
						$("#typeSNameForm").html(list)
						if(typeLa.val() === '전체보기'){
							$("#typeSNameForm").css({height : ""})	
						}
					}else{
						$("#typeSNameForm").css({height : ""})
					}
					$("#typeSNameForm input[type=checkbox]").change(function() {
						window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
						const typeSName = $(this).val();
						if ($(this).is(':checked')) {
							urlParams.append('typeSName', typeSName);
							$(this).prop("checked",true)
								   .next("label")
								   .css({
										backgroundColor : "#fca35b",
										borderRadius : "20px"
								   });
						} else {
							$(this).prop("checked",false)
								   .next("label")
								   .css({
										backgroundColor : "",
										borderRadius : ""
								   });
							urlParams.delete('typeSName');
							const inputAll = $(this).parent().find("input[type=checkbox]");
							for (let input of inputAll) {
								if (input.checked) {
									urlParams.append('typeSName', input.value);
								}
							}
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


if (urlParams.has("typeLaName")) {
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
	let typeLaName = urlParams.get("typeLaName")
	let list = $('#typeLaNameSelect input')
	for (let item of list) {
		if (typeLaName === item.value) {
			item.setAttribute("checked", true)
		}
	}
}
if (urlParams.has("typeSName")) {
	window.scrollTo({ top: 900, left: 0, behavior: 'smooth' });
	const typeSName = urlParams.getAll('typeSName');
	const list = $('#typeSNameForm input');
	for (let item of list) {
		if (typeSName.includes(item.value)) {
			item.setAttribute("checked", true);
		}
	}
}





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
			'<a href="/userInfo/'+club.nickname+'">'+
			'<div class="host">';
		if (club.memberImg != null) {
			ajaxLocationType += '<img class="user-img" src="http://192.168.10.51:8081/member/' + club.id + '/' + club.memberImg + '">';
		} else {
			ajaxLocationType += '<img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">';
		}
		ajaxLocationType += '<h2>호스트 : ' + club.nickname + '</h2>' +
			'<input type="hidden" name="code" value="' + club.membershipCode + '">' +
			'<br>' +
			'</div>' +
			'</a>'+
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

// 새로고침시 모든 url 정보 사라지게 하기
/*
window.addEventListener('load', (e) => {
	window.scrollTo({ top: 0, left: 0, behavior: 'smooth' });
	const baseUrl = window.location.origin + '/';
	if (window.location.search) {
		window.location.href = baseUrl;
	}
});
*/

