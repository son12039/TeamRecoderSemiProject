

function showReplyForm(commentCode) {
        let formId = "#reply-form-" + commentCode;
        let formElement = $(formId);

        // 대댓글 숨기기
        formElement.toggle();
    }

//  댓글
 $("#submit-comment").click((e) =>{
 	$.ajax({
 		url: "/mainComment",
 		type: 'POST',
 		data: $("#comment-frm").serialize(),
 		success: function() {
 				alert("댓글 등록 완료!");
				location.reload();
 			}
 
 		}		
 		
 	);	
 	
 });
//대댓글
function recomment(e, code) {
	const inputs = $(e.target).prevAll();
	const membershipCode = inputs[0].value;
	const id = inputs[1].value;
	const text = inputs[2].value;

	$.ajax({
		url: '/mainComment', 
		type: 'POST',
		data: {
			mainParentsCommentCode: code,
			mainCommentText: text,
			id: id,  
			membershipCode: membershipCode
		},
		success: function() {
			alert("댇ㅅ댓글 등록 완료!")
			location.reload();
		}
	})
	
}
function deleteComment(e, commentCode){
	
	$.ajax({
			url: '/deleteComment', 
			type: 'POST',
			data: {
				mainCommentCode: commentCode,
			},
			success: function() {
				alert("댓글 삭제!")
				location.reload();
			}
		})
	
}
function updateForm(commentCode) {
	let formId = "#update-form-" + commentCode;
	let formElement = $(formId);
	console.log(formId);
	console.log(formElement);

	// 수정버튼 토글 잘오는데 왜안댐?
	formElement.toggle();
    }

function updateComment(e, commentCode){
	const inputs = $(e.target).prev();
	const text = inputs[0].value;
	console.log(text);
	$.ajax({
			url: '/deleteComment', 
			type: 'POST',
			data: {
				mainCommentCode: commentCode,
				mainCommentText: text
			},
			success: function() {
				alert("댓글 수정!")
				location.reload();
			}
		})
	
}
