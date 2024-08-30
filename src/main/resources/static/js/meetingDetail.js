
$('.btn').click(function() {
		      
		      $(this).toggleClass('active');
		})

function showReplyForm(commentCode) {
        let formId = "#recomment-box-" + commentCode;
        let formElement = $(formId);
		let button = $(this);
		console.log(formId);
		console.log(formElement);
		console.log("버튼" +button);
		
       
		// 대댓글 숨기기
        formElement.toggle();

		
    }
	


//  댓글
 $("#submit-comment").click((e) =>{
	if($('#textbox').val() != ""){
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
 $("#textbox").keydown(function(key) {  
	                          
 	 if (key.keyCode == 13) {   
		if($('#textbox').val() != ""){         
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
	const inputs = $(e.target).prevAll();
	const meetCode = inputs[0].value;
	const id = inputs[1].value;
	const text = inputs[2].value;

	  if(text != ""){
	$.ajax({
		url: '/meetingsComment', 
		type: 'POST',
		data: {
			meetParentsCommentCode: code,
			meetCommentText: text,
			id: id,  
			meetCode: meetCode
		},
		success: function() {
			alert("대댓글 등록 완료!")
			location.reload();
		}
	})
	}
	}
	
// 대댓글 엔터처리
function reCommentKey(code){
	const id = "#textbox" + code;
  	
	$(id).keydown(function(key) { 
		if (key.keyCode == 13) { 
			if($(id).val() != ""){
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
)}


// 삭제 클릭
function deleteComment(e, commentCode){
		
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
function updateComment(e, commentCode){
	const inputs = $(e.target).prev();
	const text = inputs[0].value;
	if(text != ''){
	
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
function updateKey(commentCode){
	const id = "#textbox-update-" + commentCode
	$(id).keydown(function(key) {   
		
		 if (key.keyCode == 13) {   
			if($(id).val() != ''){   
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

)}