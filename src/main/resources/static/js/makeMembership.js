$(document).ready(function() {
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
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	// 이 밑에만 슥 가져가시면 돼요!
	let beforeSelLoc = ""

	function locLaChoose() {
		if (beforeSelLoc == $(this).val()) {
			$(".locSbox").html("");
			beforeSelLoc = "";
			return;
		}
		beforeSelLoc = $(this).val();
		const locL = $(this);
		$('input[name="locationLaName"]').not(locL).prop('checked', false);
		let locS = "";
		$.ajax({
			url: '/locationSList',
			method: 'GET',
			data: { laName: locL.val() },
			success: function(re) {
				$(".locSbox").html();
				for (const i of re)
					locS += `
				        <input type="checkbox" value="${i}" id="${i}" name="locLN">
				        <label for="${i}" class="locLNCss">${i}</label>
				    `;
				$(".locSbox").html(locS);
			},
		})
	}
	$('input[name="locationLaName"]').on('click', locLaChoose);

	let beforeSelType = "";

	function typeLaChoose() {
		if (beforeSelType == $(this).val()) {
			$(".typeSbox").html("");
			beforeSelType = "";
			return;
		}
		beforeSelType = $(this).val();
		const typeL = $(this);
		$('input[name="typeLaName"]').not(typeL).prop('checked', false);
		let typeS = "";
		$.ajax({
			url: '/typeSName',
			method: 'GET',
			data: { typeLaName: typeL.val() },
			success: function(re) {
				$(".locSbox").html();
				for (const i of re)
					typeS += `
					        <input type="checkbox" value="${i}" id="${i}" name="typeLN">
					        <label for="${i}" class="locLNCss">${i}</label>
					    `;
				$(".typeSbox").html(typeS);
			},
		})
	}
	$('input[name="typeLaName"]').on('click', typeLaChoose);


	$("#createClub").on('click', function() {
		const selLoc = [];
		selLoc.push($('input[name="locationLaName"]:checked').val());
		$('input[name="locLN"]:checked').each(function() {
			selLoc.push($(this).val());
		});
		const selType = [];
		selType.push($('input[name="typeLaName"]:checked').val());
		$('input[name="typeLN"]:checked').each(function() {
			selType.push($(this).val());
		});

		if (!membershipNameCheck) {
			alert("클럽명 확인해주세요")
			return;
		}
		if (!membershipMaxSubmit) {
			alert("최대인원을 확인해주세요")
			return;
		}
		if (selType.length === 1) {
			alert("타입을 확인해주세요")
			return;
		}
		if (selLoc.length === 1) {
			alert("지역을 확인해주세요")
			return;
		}
		let membershipName = $("#membershipName").val();
		let f = document.getElementById('file');
		let file = f.files[0];
		let membershipAccessionText = $("#membershipAccessionText").val();
		let membershipSimpleText = $("#membershipSimpleText").val();
		let membershipMax = $("#membershipMax").val();
		let formData = new FormData();
		formData.append('membershipName', membershipName)
		formData.append('membershipAccessionText', membershipAccessionText)
		formData.append('membershipSimpleText', membershipSimpleText)
		formData.append('membershipMax', membershipMax)
		if (file !== undefined) {
			formData.append('file', file);
		}
		formData.append('LB', JSON.stringify(selLoc));
		formData.append('TB', JSON.stringify(selType));
		if (selType.length != 1 && selLoc.length != 1 && membershipNameCheck && membershipMaxSubmit) {
			$.ajax({
				type: 'post',
				url: '/makeMembership',
				data: formData,
				processData: false,
				contentType: false,
				success: function(code) {
					window.location.replace(`/club/${code}`);
				}
			});
		}
	});
});