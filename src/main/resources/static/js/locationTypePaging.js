let page = 1;
let isLoading = false; // 로딩 상태를 추적
window.addEventListener("scroll", () => {

	// 953 + 582 =    1535
	if (1200 <= window.scrollY + window.innerHeight){
		console.log("들어왔어!");
		$(".locationTypeBody").css({position : "fixed",
									 width : "100%",
									 top : "70px",
									 display : "flex"
								 });
		$(".locationTypeBodyBox").css({margin : "auto"})

	}else{
		console.log("빠져나왔어!");
		$(".locationTypeBody").removeAttr("style")
		$(".locationTypeBodyBox").removeAttr("style")
	}
	
	if (isLoading) return; // 로딩 중이면 AJAX 요청을 방지
	
	if (document.body.offsetHeight <= window.scrollY + window.innerHeight) {
		isLoading = true; // 로딩 상태 설정
		
		page++;
		$.ajax({
			url: 'list',
			type: 'get',
			data: { page: page },
			success: function(clubList) {
				let div = $(".membership-list");
				console.log(clubList);
				$.each(clubList, function(index, club) {
					let ajaxLocationType =
						'<div class="membership-card">' +
						'<div class="membership-img">' +
						'<a href="/' + club.membershipCode + '">';
					if (club.membershipImg != null) {
						ajaxLocationType += '<img src="http://192.168.10.51:8081/membership/' + club.membershipCode +'/'+ club.membershipImg + '">';
					} else {
						ajaxLocationType += '<img src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">';
					}
					ajaxLocationType += '</a>' +
						'</div>' +
						'<div class="membership-info">' +
						'<h1 class="membership-name">' + club.membershipName + '</h1>' +
						'<h2>' + club.membershipInfo + '</h2>' +
						'<h3>멤버수 :' + club.count+ '/' + club.membershipMax + '</h3>' +
						'<div id="host">';
					if (club.memberImg != null) {
						ajaxLocationType += '<img class="user-img" src="http://192.168.10.51:8081/member/' + club.id +'/'+ club.memberImg + '">';
					} else {
						ajaxLocationType += '<img class="user-img"src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">';
					}
					ajaxLocationType += '<h2>호스트 : ' + club.nickname + '</h2>' +
						'<input type="hidden" name="code" value="' + club.membershipCode + '">' +
						'<br>' +
						'</div>'+
							'<div class="locationTypeBox">' +
							'<div class="location">';
								for (let location of club.locations) {
									ajaxLocationType += '<div class="locationList">'+ '#' + location.locLaName+'#' +  location.locSName+'</div>';
								}
								ajaxLocationType +='</div>'+'<br>'+
								'<div class="type">';
								for (let type of club.types) {
									ajaxLocationType += '<div class="typeList">'+ type.typeSName+'</div>';
								}
								ajaxLocationType += '</div>'+
							'</div>'+
					'</div>'+
					'</div>';

					div.append(ajaxLocationType);
					
				});
				isLoading = false; // 로딩 상태 해제
			}
		});
	}
});

/*

*/


