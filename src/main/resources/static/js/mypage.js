$(document).ready(function() {
	// 버튼 클릭 시 해당 섹션의 표시/숨김 처리
	$("#all-club-button").click(function() {
		$(".membership-card").hide(); // 모든 카드 숨기기
		$("#all-club").show(); // 전체 목록 카드만 보이기
	});
	$("#manage-club-button").click(function() {
		$(".membership-card").hide(); // 모든 카드 숨기기
		$("#manage-club").show(); // 내가 관리중인 클럽 카드만 보이기
	});
	$("#wait-club-button").click(function() {
		$(".membership-card").hide(); // 모든 카드 숨기기
		$("#wait-club").show(); // 가입 대기중인 클럽 카드만 보이기
	});
	$("#all-meet-button").click(function() {
		$(".membership-card").hide(); // 모든 카드 숨기기
		$("#all-meet").show(); // 가입 대기중인 클럽 카드만 보이기
		calendar.render();
	});


});

function show() {

	if ($("#menu").css("display") === "none") {
		$("#menu").css("display", "block");
		$("#arrow").css("transform", "rotate(180deg)")

	} else {
		$("#menu").css("display", "none");
		$("#arrow").css("transform", "rotate(0deg)")
	}
}
function deleteList(grade, membershipCode) {
	let text = "";
	if (grade === 'admin') {
		text = "당신의 등급은 관리자입니다 정말 탈퇴하시겠습니까'?"
	} else if (grade === 'admin') {
		text = "정말 탈퇴하시겠습니까?"
	} else {
		text = "정말로 신청 취소하시겠습니까?"
	}

	if (confirm(text)) {
		$.ajax({
			url: '/deleteList',
			type: 'POST',
			data: {
				membershipCode: membershipCode
			},
			success: function() {
				alert("변경 완료")
				location.reload();
			}
		})
	}
}

function allDeleteMembership() {
	let pwdCheck = $("#pwdCheck").val();
	if (confirm("클럽원이 본인만 남아있는 클럽만 삭제할 수 있으며 해당 클럽에 대한 모든 데이터는 삭제 처리 됩니다 그래도 삭제하시겠습니까?")) {
		$.ajax({
			url: '/allDeleteMembership',
			type: 'POST',
			data: {
				pwdCheck: pwdCheck
			},
			success: function(bo) {
				if (bo) {
					alert("클럽 삭제 완료");
				} else {
					alert("클럽 삭제 실패");
				}
				location.reload();
			},
			error: function() {
				alert("클럽 삭제 실패")
				location.reload();
			}
		}
		)
	}
};