
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
 	$.ajax({
 		url: "/mainComment",
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
 				url: "/mainComment",
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
	const membershipCode = inputs[0].value;
	const id = inputs[1].value;
	const text = inputs[2].value;
	  if(text != ""){
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
		url: '/mainComment', 
		type: 'POST',
		data: {
			mainParentsCommentCode: code,
			mainCommentText: $(id).val(),
			id: $(id).next().val(),  
			membershipCode: $(id).next().next().val()
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
			url: '/updateComment', 
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
}
// 수정 엔터처리
function updateKey(commentCode){
	const id = "#textbox-update-" + commentCode
	$(id).keydown(function(key) {   
		
		 if (key.keyCode == 13) {   
			if($(id).val() != ''){   
			$.ajax({
						url: '/updateComment', 
						type: 'POST',
						data: {
							mainCommentCode: commentCode,
							mainCommentText: $(id).val()
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


// 채승훈 댓글 클릭시 밑에 보더 이벤트 주기
$("#textbox").click(function(){
	console.log($("#textbox"))
	if($("#textbox")){
		$("#textbox").css({borderBottom : "1px solid red"})
	}else{
		$("#textbox").css({borderBottom : ""})
	}
})
