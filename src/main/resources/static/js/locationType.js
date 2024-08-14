const url = new URL(location.href);
const urlParams = url.searchParams;
const laName = urlParams.get('laName');

const laNameList = $("#locationLaNameSelect").children();
for(let i = 0; i < laNameList.length; i++) {
	if(laNameList[i].innerHTML === laName) {
		laNameList[i].setAttribute("selected", true);
	}
}

console.log(laName);

$("#locationLaNameSelect").change(function() {
	const laName = $(this).val();
	
	if(laName!=='전체보기') {
		//console.log(location);
		location.href = "/LocationType?laName=" + laName;
	} else {
		location.href = "/LocationType";
	}
	//const location = $(this);
	//$.ajax({
		//type: "get",
	///	url: "LocationType",
	//	data: {
	//		location : location.val()
	//	},
	//	success: function(data) {
			//for(let i = 0; i < location.children().length; i++) {
			//	if(location.children()[i].innerHTML === location.val()) {
			//		console.log(location.children()[i]);
			//		location.children()[i].setAttribute("selected", true);
			//	}
			//}
	//		$('body').html(data);
			
		//}
//})
});