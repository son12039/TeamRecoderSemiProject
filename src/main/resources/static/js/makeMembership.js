// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
const urlParams = url.searchParams;

// 상위 지역 : locationLaName, 하위 지역들 : locationSName, 
// 상위 유형 : typeLaName, 하위 유형들 : typeSName

// 파라미터값 존재여부 체크 & 파라미터 값 가져오기
// 상위 지역 필터




$("#typeLaNameForm input[type=checkbox]").change(function() {

	//url 지우기
	urlParams.delete("typeLaName");
	urlParams.delete("typeSName");
	// ajax 체인걸어놔서 두번째까지는 안와서 변수선언
	const type = $(this);
	//체인지하면서 css 변경
	// 체크 이벤트 
	const typeName = $(this).val();
	if ($(this).prop('checked')) {
		// 체크 걸면서 css도 같이 주기
		$('#typeLaNameForm input[type=checkbox]')
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
		if (typeName !== '초기화') {
			urlParams.append('typeLaName', typeName);
		}
		if($(this).val() === '초기화'){
			$(this).prop('checked', true)
					.next("label")
					.css({backgroundColor : "",
						  color : ""
					});	
		}
	}

	// 새로고침안하고 url로 보내기
	history.pushState({}, null, url);
	let list = "";
	$.ajax({
		url: 'list',
		type: 'get',
		data: {
			typeName: urlParams.get("typeLaName"),
			typeSName: urlParams.getAll("typeSName"),
			typeTLaName: urlParams.get("typeTLaName"),
			typeTSName: urlParams.getAll("typeTSName")
		},
		success: function(clubList1) {
			renderClubList(clubList1);
			$.ajax({
				url: 'typeSList',
				type: 'get',
				data: "typeName=" + typeName,
				success: function(typeS) {
					$(".typeSDiv").empty();
					if (type.prop('checked')) {
						$.each(typeS, function(index, item) {
							list += `
							<input type="checkbox" value="${item}" id="${item}"
										name="typeSName">
									<label for="${item}" class="typeTCss">${item}</label>`
						})
						$(".typeSDiv").html(list);
					}
					if(type.val() === '초기화'){
						list+=`<div class="typeClassiFication">취미를 선택해주세요</div>`
						$(".typeSDiv").html(list)
					}
					$("#typeSNameForm input[type=checkbox]").change(function() {
						const locationSName = $(this).val();
						if ($(this).is(':checked')) {
							$(this).prop("checked",true)
							.next("label")
							.css({backgroundColor : "rgb(255, 235, 187)",
								  color : "#fca35b"
							});
							urlParams.append('typeSName', typeSName);
						} else {
							$(this).prop("checked",false)
							.next("label")
							.css({backgroundColor : "",
								  color : ""
							});							
							urlParams.delete('typeSName');
							const inputAll = $(this).parent().find("input[type=checkbox]");
							for (let input of inputAll) {
								if (input.checked) {
									urlParams.append("typeSName", input.value)
									
								}
							}
						}
						history.pushState({}, null, url);
						// 지역 작은놈들 페이징
						$.ajax({
							url: 'list',
							type: 'get',
							data: {
								typeLaName: urlParams.get("typeLaName"),
								typeSName: urlParams.getAll("typeSName"),
								typeTLaName: urlParams.get("typeTLaName"),
								typeTSName: urlParams.getAll("typeTSName")
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






/*타입 클릭시*/
$("#typeLaNameSelect input[type=checkbox]").change(function() {
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
	$.ajax({
		url: "list",
		type: "get",
		data: {
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
			'<h3><i class="fa-solid fa-users"></i> : ' + club.count + '/' + club.membershipMax + '</h3>' +
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










/* 이 위부터 유형별 체크박스 =========================================================*/

/* ============================================================================= */









// 현재 페이지의 URL에서 쿼리 문자열과 해시(#)를 제거할때 사용 
					//(현재 페이지,제목 문자열, 현재 페이지 경로를 포함된 URL )
history.replaceState({}, null, location.pathname); // {}: 현재 페이지 상태 저장을 위한 빈칸 사용, 저장 필요 없으면 null 사용가능  

//@@@@@@@@@@@@@@@@@@@@@@@@
// 클럽명 중복 체크
let membershipNameCheck = false;
membershipName.addEventListener('input', function() {
	const membershipNameValue = $(this).val().trim();
	$.ajax({
		type: 'POST',
		url: '/membershipNameCheck', // 컨트롤러 URL
		data: { membershipName: membershipNameValue },
		success: function(result) {
			if (result) {
				if (membershipNameValue === "") {
					$('#name').text(" 필수 입력사항입니다").css('color', 'red');
					membershipNameCheck = false;
				} else if (membershipNameValue.length >= 50) {
					$('#name').text("50자 이하까지 쓸 수 있습니다.").css('color', 'red');
					membershipNameCheck = false;
				} else {
					$('#name').text(" 사용 가능한 클럽명 입니다").css('color', 'green');
					membershipNameCheck = true;
				}

			} else {
				$('#name').text(" 중복된 클럽명입니다 다시 작성해 주세요").css('color', 'red');
				membershipNameCheck = false;
			}
		}
	});
});

// 클럽 인원수 설정
const membershipMaxRegExp = /^[0-9]{2,100}$/;

let membershipMaxSubmit = false; // 최대인원 설정 여부를 나타내는 변수 초기화

// membershipMax 요소에 입력 이벤트 리스너 추가
membershipMax.addEventListener('input', function() {
	const membershipMaxValue = $(this).val().trim(); // jQuery를 사용하여 현재 값 가져오기

	if (membershipMaxValue === '') {
		$('#max').text(" 필수 입력사항입니다").css('color', 'red');
		membershipMaxSubmit = false; // 제출 불가 상태로 
	} else if (membershipMaxValue <= 1 || membershipMaxValue >= 101) {
		$('#max').text("설정 가능한 최소 인원은 2명 최대 인원수는 100명입니다").css('color', 'red');
		membershipMaxSubmit = false;
	} else {
		$('#max').text("설정 가능한 인원수 입니다").css('color', 'green');
		membershipMaxSubmit = true;
	}
});









// 유형 선택 시 변경 이벤트
/*
$('#typeLaNameMem').change(function() {
	let location = $(this).val()// 선택된 지역 값을 가져옴
	let list = "";// 지역 하위 목록을 저장할 변수
	let allList = `<option>전체보기</option>`;
	$.ajax({
		type: 'post',
		url: '/memLocation',
		data: 'locationLaName=' + location,
		success: function(locationS) {
			$.each(locationS, function(index, item) {
				list += `<checkbox>${item}</checkbox>`;
			});
			if (list !== "") {
				$("#locationSNameMem").html(list);	// 하위 지역 목록을 업데이트
			} else {
				$("#locationSNameMem").html(allList); // 하위 지역이 없으면 전체보기로 설정
			}

		}

	})
});
*/

// 유형 선택 시 변경 이벤트
/*
$('#typeLaNameMem').change(function() {
	let type = $(this).val()
	let list = "";
	let allList = `<checkbox>전체보기</checkbox>`;

	$.ajax({
		type: 'post',
		url: '/memType',
		data: $.param({ typeLaName: type }), // & 문자가 URL 쿼리 문자열에서 파라미터 구분자로 사용됨 그래서 URL인코딩 처리를 해줘야 함
		success: function(typeS) {
			$.each(typeS, function(index, item) {
				list += `<checkbox>${item}</checkbox>`;
			
			});
			if (list !== "") {
				$("#typeSNameMem").html(list);
				console.log("if", type);
			} else {
				$("#typeSNameMem").html(allList);
				console.log("else", type);
			}

		}

	});
});

*/

// 클릭 이벤트 사용해서 클릭하면 선택한 정보가 따로 쌓이게
let locationBtnCheck = false; // 클릭 상태를 나타내는 변수 (초기값은 false)
let LB = ""; // 선택된 지역 정보를 저장할 변수 
let list = "";
let allList = `<option>전체보기</option>`; // 전체보기 옵션을 포함한 HTML 문자열
let selectedAreas = []; // 선택된 지역 정보를 저장할 배열
locationBtn.addEventListener("click", function(e) {
	e.preventDefault();
	let locationbig = $("#locationLaNameMem option:selected").val();
	let locationsmall = $("#locationSNameMem option:selected").val();
	let string = "";

	if ($("#test1").html().substr(0, 2) !== locationbig && $("#test1").html() !== "") {
		alert("어허");
		return;
	}
	if ($("#test1").html().includes(locationsmall) && $("#test1").html() !== "") {
		alert("어허2");
		return;
	}
	if ($("#test1").html() == "") {
		string += $("#test1").html() + locationbig + " = " + locationsmall;
	} else {
		string += $("#test1").html() + ", " + locationsmall;
	}
	if ($("#locationLaNameMem option:selected").val() === "전체보기") {
		locationBtnCheck = false;
		alert("지역을 선택해 주세요")
		return; // 클릭 이벤트를 막고 함수 종료
	}
	$("#test1").html(string);
});

locationBtncancel.addEventListener("click", function() {
	$("#test1").empty();
	LB = "";// LB 변수도 초기화
	selectedAreas = [];

});



// 클릭 이벤트 사용해서 클릭하면 선택한 정보가 따로 쌓이게
let typeBtnCheck = false; // 클릭 상태를 나타내는 변수 (초기값은 false)
let TB = ""; // 선택된 유형 정보를 저장할 변수
let allList2 = `<option>전체보기</option>`; // 전체보기 옵션을 포함한 HTML 문자열
let selectedtype = []; // 선택된 유형 정보를 저장할 배열(중복체크용)
let typetext = "";
// typeBtn 버튼에 클릭 이벤트 리스너 추가
typeBtn.addEventListener("click", function(e) {
	e.preventDefault();
	let typebig = $("#typeLaNameMem option:selected").val();
	let typesmall = $("#typeSNameMem option:selected").val();
	let string = "";

	if ($("#test2").html().substr(0, 2) !== typebig.substr(0, 2) && $("#test2").html() !== "") {
		alert("어허");
		return;
	}
	if ($("#test2").html().includes(typesmall) && $("#test2").html() !== "") {
		alert("어허2");
		return;
	}
	if ($("#test2").html() == "") {
		string += $("#test2").html() + typebig + " = " + typesmall;
	} else {
		string += $("#test2").html() + ", " + typesmall;
	}
	if ($("#typeLaNameMem option:selected").val() === "전체보기") {
		typeBtnCheck = false;
		alert("유형을 선택해 주세요")
		return; // 클릭 이벤트를 막고 함수 종료
	}
	$("#test2").html(string);

});







// 유형 취소
typeBtncancel.addEventListener("click", function() {
	$("#test2").empty();
	TB = "";// TB 변수도 초기화
	selectedAreas = [];


});

function validate() { // 생성버튼 눌렀을때 작동
	let membershipName = $("#membershipName").val();
	let f = document.getElementById('file');
	let file = f.files[0];
	let membershipAccessionText = $("#membershipAccessionText").val();
	let membershipSimpleText = $("#membershipSimpleText").val();
	let membershipMax = $("#membershipMax").val();
	let loc = $("#test1").text();
	let tp = $("#test2").text();

	
	

	let formData = new FormData(); // FormData 객체를 사용하여 파일과 데이터를 함께 전송합니다.
	//formData.append('dto', JSON.stringify(data));
	formData.append('membershipName',membershipName)
	formData.append('membershipAccessionText',membershipAccessionText)
	formData.append('membershipSimpleText',membershipSimpleText)
	formData.append('membershipMax',membershipMax)
	formData.append('file', file);
	formData.append('LB', loc);
	formData.append('TB', tp);
	$.ajax({
		type: 'post',
		url: '/makeMembership',
		data: formData,
		processData: false,
		contentType: false,
		success: function() {

			
			//  mypage 페이지로 이동
			window.location.href = '/mypage';
			
		}
		
	});
	
	

}