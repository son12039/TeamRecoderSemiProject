function submit(num){
	let frm = "#agreefrm-" + num;
	console.log(frm);
	let btn = "#agreeMember-" + num;
	console.log(btn);
	$(btn).click((e) =>{
			$.ajax({
				url: "/agreeMember",
				type: 'post',
				data: $(frm).serialize(),
				success: function() {
					location.reload();
					}
					
						
				
			});	
			
		});
}

