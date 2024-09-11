
$('.btn').click(function() {

	$(this).toggleClass('active');
})

function showReplyForm(commentCode) {
	let formId = "#recomment-box-" + commentCode;
	let formElement = $(formId);
	let button = $(this);
	console.log(formId);
	console.log(formElement);
	console.log("버튼" + button);


	// 대댓글 숨기기
	formElement.toggle();


}



//  댓글
$("#submit-comment").click((e) => {
	if ($('#textbox').val() != "") {
		console.log($("#comment-frm").serialize())
		$.ajax({
			url: "/meetingsComment",
			type: 'POST',
			data: $("#comment-frm").serialize(),
			success: function() {
				alert("댓글 등록 완료!");
				location.reload();
			}

		});
	}

});
// 댓글엔터처리
$("#textbox").keydown(function(key) {

	if (key.keyCode == 13) {
		if ($('#textbox').val() != "") {
			$.ajax({
				url: "/meetingsComment",
				type: 'POST',
				data: $("#comment-frm").serialize(),
				success: function() {
					alert("댓글 등록 완료!");
					location.reload();
				}

			});
		}
	}
});
//대댓글
function recomment(e, code) {
	const id = "#textbox" + code;

	if ($(id).val() != "") {
		$.ajax({
			url: '/meetingsComment',
			type: 'POST',
			data: {
				meetParentsCommentCode: code,
				meetCommentText: $(id).val(),
				id: $(id).next().val(),
				meetCode: $(id).next().next().val()
			},
			success: function() {
				alert("대댓글 등록 완료!")
				location.reload();
			}
		})
	}
}

// 대댓글 엔터처리
function reCommentKey(code) {
	const id = "#textbox" + code;

	$(id).keydown(function(key) {
		if (key.keyCode == 13) {
			if ($(id).val() != "") {
				$.ajax({
					url: '/meetingsComment',
					type: 'POST',
					data: {
						meetParentsCommentCode: code,
						meetCommentText: $(id).val(),
						id: $(id).next().val(),
						meetCode: $(id).next().next().val()
					},
					success: function() {
						alert("대댓글 등록 완료!")
						location.reload();
					}
				})
			}
		}
	}
	)
}


// 삭제 클릭
function deleteComment(e, commentCode) {

	$.ajax({
		url: '/deleteMeetingsComment',
		type: 'POST',
		data: {
			meetCommentCode: commentCode,
		},
		success: function() {
			alert("댓글 삭제!")
			location.reload();
		}
	})

}
// 수정 토글처리
function updateForm(commentCode) {
	let formId = "#update-form-" + commentCode;
	let formElement = $(formId);
	formElement.toggle();
}
// 수정 클릭처리
function updateComment(e, commentCode) {
	const inputs = $(e.target).prev();
	const text = inputs[0].value;
	if (text != '') {

		$.ajax({
			url: '/updateMeetingsComment',
			type: 'POST',
			data: {
				meetCommentCode: commentCode,
				meetCommentText: text
			},
			success: function() {
				alert("댓글 수정!")
				location.reload();
			}
		})
	}
}
// 수정 엔터처리
function updateKey(commentCode) {
	const id = "#textbox-update-" + commentCode
	$(id).keydown(function(key) {

		if (key.keyCode == 13) {
			if ($(id).val() != '') {
				$.ajax({
					url: '/updateMeetingsComment',
					type: 'POST',
					data: {
						meetCommentCode: commentCode,
						meetCommentText: $(id).val()
					},
					success: function() {
						alert("댓글 수정!")
						location.reload();
					}
				})
			}
		}
	}

	)
}

// 현재 날짜를 계산해서 
// 현재 날짜를 기준으로 참가 ,참가 취소버튼을 마감으로 바꾸고 버튼 비활성화 시키는 기능 
const today = new Date()
const now = today.toISOString().split('T')[0];
const start = $("#meetDateStart").text();
const end = $("#meetDateEnd").text();
const gap = start -end;
console.log(now)
console.log($("#meetDateStart").text())
if (start < now) {
	$("#gogo").val("마감");
	$("#gogo").attr("disabled", true);
	$("#gogo").css("background", "black");

}
const oldDate = new Date(start);
const newDate = new Date(end);
newDate.setDate(newDate.getDate()+1);
let diff = Math.abs(newDate.getTime() - oldDate.getTime());
diff = Math.ceil(diff / (1000 * 60 * 60 * 24));
$("#date").text("일정: "+start+" ~ "+end+"   ("+diff+"일)");

