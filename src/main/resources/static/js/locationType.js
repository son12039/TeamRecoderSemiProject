/* 소분류 Ajax로 가져오기*/
$(function() {

	let search = {
		locLaName: null,
		locSName: null,
		typeLaName: null,
		typeSName: null,
	}

	$("#locationLaNameSelect").change(function() {
		search.locLaName = $(this).val();
		let list = "";
		$.ajax({
			type: "get",
			url: "locationSList",
			data: search,

			success: function(result) {
				const select = $("#locationSNameSelect");
				select.find("option:not(:first)").remove();
				// 상단바 위치 보여주기
				$.each(result.sLocation, function(index, item) {
					select.append($('<option></option>').attr('value', item).text(item));
				});
				$(".allMemberBoxBody").empty();
				$.each(result.LaLocation, function(index, item) { 
					console.log(item)
				});
			}
		})
	});

	$("#typeLanameSelect").change(function() {
		search.typeLaName = $(this).val();
		$.ajax({
			type: "get",
			url: "typeSList",
			data: search,

			success: function(result) {
				const select = $("#typeSNameSelect");
				select.find("option:not(:first)").remove();
				$.each(result, function(index, item) {
					select.append($('<option></option>').attr('value', item).text(item));
				});
			}
		})
	});
	
	
	
	$("#typeSNameSelect").change(function() {
		search.typeSName = $(this).val();

	});
	
	
	
	
	//$("#typeLanameSelect").change(function() {
		//const typeLaName = $(this).val();
		//$.ajax({
			//type: "get",
			//url: "classificationSLocation",
			//data: "typeLaName=" + typeLaName,


//		})
		
		//$.ajax({
			//		type: "get",
			//		url: "locationSList",
			//		data: search,

			//		success: function(result) {
			//			console.log(result);
			//			const select = $("#locationSNameSelect");
			//			select.find("option:not(:first)").remove();
				//		$.each(result.sLocation, function(index, item) {
					//		select.append($('<option></option>').attr('value', item).text(item));
					//	});
					//	$.each(result.LaLocation, function(index, item) {
					//		console.log(item);
					//	})
				//	}
				//})
	//});


});









