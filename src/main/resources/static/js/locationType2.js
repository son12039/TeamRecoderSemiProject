/* 소분류 Ajax로 가져오기*/
$(function() {
	
	console.log()

	let search = {
		locLaName: null,
		locSName: null,
		typeLaName: null,
		typeSName: null,
	}	
	
	/* 나중에 배열로 만들어서 값을 2개 이상 받았을때 만들기
	let search = {
	    locLaName: [], 
	    locSName: [],
	    typeLaName: [],
	    typeSName: [],
	};
	그러면 각 제이쿼리에 한번 싹 비어주면서 받을값을 저장
	*/
	$("#locationLaNameSelect").change(function() {
		search.locLaName = $(this).val()!='' ?  $(this).val() : null;
		search.locSName = null;
		console.log(search) // 확인용
		let list = "";
		$.ajax({
			type: "get",
			url: "locationLaList",
			data: search,
			
			success: function(result) {
				
				const select = $("#locationSNameSelect");
				select.find("option:not(:first)").remove();
				// 상단바 위치 보여주기
				let filter = [...new Set(result.sLocation)] //[...new Set(result.sLocation)] 종복된값 필터링 개꿀
				$.each(filter, function(index, item) {
					select.append($('<option></option>').attr('value', item).text(item));
				});
					
				// 테이블 분류
				$(".allMemberBoxBody").empty();
				console.log(result.LaLocation.length);
				$.each(result.LaLocation, function(index, item) { 
					list += `<div class="allMemberBox">
									<div>큰 지역 :
										${item.locLaName}</div>
									<div>작은 지역 :
										${item.locSName}</div>
									<div>큰 타입 :
										${item.typeLaName}</div>
									<div>작은 타입 :
										${item.typeSName}</div>
								</div>`;
				});
				$(".allMemberBoxBody").html(list)
			}
		})
	});
	$("#locationSNameSelect").change(function() {
		search.locSName = $(this).val();
		console.log(search) // 확인용
		let list = "";
		$.ajax({
			type:"get",
			url:"locationSList",
			data:search,
			
			success:function(result){
				$(".allMemberBoxBody").empty();
				$.each(result, function(index, item) { 
					list += `<div class="allMemberBox">
									<div>큰 지역 :
										${item.locLaName}</div>
									<div>작은 지역 :
										${item.locSName}</div>
									<div>큰 타입 :
										${item.typeLaName}</div>
									<div>작은 타입 :
										${item.typeSName}</div>
								</div>`;
				});
				$(".allMemberBoxBody").html(list)
			}
		})
	});
	
	
	
	$("#typeLanameSelect").change(function() {
		search.typeLaName = $(this).val()!='' ?  $(this).val() : null;
		search.typeSName = null;
		
		console.log(search) // 확인용
		let list = "";
		$.ajax({
			type: "get",
			url: "typeLaList",
			data: search,

			success: function(result) {
				const select = $("#typeSNameSelect");
				select.find("option:not(:first)").remove();
				$.each(result.sType, function(index, item) {
					select.append($('<option></option>').attr('value', item).text(item));
				});
				
				//테이블 분류
				$(".allMemberBoxBody").empty();
				$.each(result.LaType, function(index, item) { 
					list += `<div class="allMemberBox">
									<div>큰 지역 :
										${item.locLaName}</div>
									<div>작은 지역 :
										${item.locSName}</div>
									<div>큰 타입 :
										${item.typeLaName}</div>
									<div>작은 타입 :
										${item.typeSName}</div>
								</div>`;
				});			
				$(".allMemberBoxBody").html(list)
			}
		})
	});
	
	$("#typeSNameSelect").change(function() {
		search.typeSName = $(this).val();
		console.log(search) // 확인용
		let list ="";
		$.ajax({
			type:"get",
			url:"typeSList",
			data:search,
			
			success : function(result){
				$(".allMemberBoxBody").empty();
				$.each(result, function(index, item){
					list += `<div class="allMemberBox">
									<div>큰 지역 :
										${item.locLaName}</div>
									<div>작은 지역 :
										${item.locSName}</div>
									<div>큰 타입 :
										${item.typeLaName}</div>
									<div>작은 타입 :
										${item.typeSName}</div>
								</div>`;
				});			
				$(".allMemberBoxBody").html(list)
			}
		})

	});

});
