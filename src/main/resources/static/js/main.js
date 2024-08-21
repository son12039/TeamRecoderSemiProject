

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
	console.log(text);
	console.log(id);
	console.log(membershipCode);
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
	//$(`#comment-box-${}`)
}
//let hiddenCode = $("#hiddenCode").val;
//let hiddenSubmit = "#submit-comment-comment-" + hiddenCode;
//$("#recommment").click((e) => {
	//alert("#####");
	//alert(e.target.value);
  /*  let commentCommentFrom = "#comment-frm-" + hiddenCode;
	e.preventDefault();
     // AJAX 요청 보내기
     $.ajax({
         url: '/mainComment', // 대댓글을 처리할 서버 URL
         type: 'POST',
         data: $(commentCommentFrom).serialize,
         success: function() {  
             alert('대댓글이 등록완료!.');
			 location.reload();

         }
     });*/
// });