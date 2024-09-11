

$(document).ready(function() {
    function recommendation(targetMember, loginMember, plusMinus) {
		$.ajax({
			url: '/recommendation', 
					type: 'POST',
					data: {
						targetMember : targetMember, 
						loginMember : loginMember, 
						plusMinus : plusMinus
					},
					success: function(date) {
						if(date){
							alert("추천&비추천이 완료되었습니다")
						}else {
							alert("회원 추천기능은 24시간마다 가능합니다.")
						}
						location.reload();
					}
		})
    }

    $('#plus-btn').on('click', function() {
        // 버튼에서 데이터 속성 가져오기
        const targetMemberId = $(this).data('target-member-id');
        const loginMemberId = $(this).data('login-member-id');
		if (confirm("추천&비추천은 24시간마다 가능합니다 정말로 해당 회원을 추천 하시겠습니까?")) {
		    recommendation(targetMemberId, loginMemberId, false);
		} else {
		    
		}
    });
	$('#minus-btn').on('click', function() {
	        // 버튼에서 데이터 속성 가져오기
	     const targetMemberId = $(this).data('target-member-id');
	     const loginMemberId = $(this).data('login-member-id');
		 if (confirm("추천&비추천은 24시간마다 가능합니다 정말로 해당 회원을 비추천 하시겠습니까?")) {
		     recommendation(targetMemberId, loginMemberId, true);
		 } else {
		     
		 }
	  });
});