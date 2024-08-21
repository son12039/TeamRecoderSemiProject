function showReplyForm(commentCode) {
        var formId = "#reply-form-" + commentCode;
        var formElement = $(formId);

        // 대댓글 숨기기
        formElement.toggle();
    }

//  댓글
 $("#submit-comment").click((e) =>{
 	e.preventDefault(); // 기존 이벤트 제거(폼태그 보내는 submit)
 	$.ajax({
 		url: "/mainComment",
 		type: 'POST',
 		data: $("#comment-frm").serialize(),
 		success: function(re) {
 				alert("댓글 등록 완료!")
				location.reload();
 			}
 
 		}		
 		
 	);	
 	
 });

$("#submit-comment-comment-" + commentCode).click((e) => {
	let commentCode = e.date('commentcode')
    let formId = "#comment-frm-" + commentCode;
	e.preventDefault();
     // AJAX 요청 보내기
     $.ajax({
         url: '/mainComment', // 대댓글을 처리할 서버 URL
         type: 'POST',
         data: $(formId).serialize,
         success: function(re) {  
             alert('대댓글이 등록되었습니다.');
			 location.reload();

         }
     });
 });