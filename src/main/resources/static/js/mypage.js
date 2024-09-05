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
	})
});

// 토글 버튼
const toggleIcon = document.querySelector(".toggle__icon");
const membershipCreate = document.querySelector(".membership-create");

if (toggleIcon !== null) {
	toggleIcon.addEventListener("click", () => {
		membershipCreate.classList.toggle("active");
		toggleIcon.classList.toggle("active");
	});
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

