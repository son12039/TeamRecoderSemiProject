

// 도시별 해당 지역 눌렀을 때 
// -> 하단 지역별 리스트 가져와야 하고, 리스트도 새로 가져와야 하고
$("#locationLaNameForm input[type=checkbox]").change(function() {
	
	urlParams.delete("locationSName");
	searchDto.locationSName = [];
	const laName = $(this).val();
	$(".locationSDiv").empty();

	// 타입 눌렀을떄 사용자가 선택한거 옆에 보여줄 친구
	locationLaFriend = "<div class='locationLaFriend'>" + laName + "</div>";


	if ($(this).prop('checked') && laName !== '') {

		// 새로 눌러졌을때 둘다 삭제하기
		$(".locationLaFriend").remove()
		//삭제하면서 다시 초기화
		locationSList = [];
		$(".locationSFriend").remove()

		$("#locationLaNameForm input[type=checkbox]").prop('checked', false).next('label').css({
			background: ""
		})
		$(this).prop('checked', true).next('label').css({
			background: "#dbdbdb"
		})

		// 전체보기 제외 클릭이 이루어질때 
		searchDto.locationLaName = laName;

		if (urlParams.has("locationLaName")) {
			// 친구 만들기
			$(".locationLaStar").append(locationLaFriend)
			urlParams.set("locationLaName", searchDto.locationLaName);
		} else {
			// 친구 만들기
			$(".locationLaStar").append(locationLaFriend)
			urlParams.append("locationLaName", searchDto.locationLaName);
		}
		$("#locationSNameForm").css({
			backgroundColor: "#FCA35B",
			color: "white",
			cursor: "pointer"
		})

		locationSNameList(() => {
			$(".locationSDiv").html(locationSNameListResult);
		});

	} else {
		searchDto.locationLaName = null;
		urlParams.delete("locationLaName");
		$("#locationLaNameForm input[type=checkbox]").prop('checked', false).next('label').css({
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

	div.empty();
	list(1);
});
