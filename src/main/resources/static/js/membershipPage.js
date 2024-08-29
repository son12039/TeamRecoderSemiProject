$(".agreeMember").click((e) =>{
		e.preventDefault(); 
		// 기존 이벤트 제거(폼태그 보내는 submit)
		const asd = $(e.target).val();
		console.log(asd)
		$.ajax({
			url: "/agreeMember",
			type: 'post',
			data: membershipcode = code ,
			success: function() {
				location.reload()
				
				}
							
			
		});	
		
	});

