let LTList = {
	locationLaName : null,
	locationSName : null,
	typeLaName : null,
	typeSName : null
}
function changeList(){
	let urlList = "/LocationType?"
	if(LTList.locationLaName){
		urlList += "laName=" + LTList.locationLaName + "&";
	}else if(LTList.locationSName){
		urlList += "sName=" + LTList.locationSName + "&";
	}else if(LTList.typeLaName){
		urlList += "typeLaName=" + LTList.typeLaName + "&";
	}else if(LTList.typeSName){
		urlList += "typeSName=" + LTList.typeSName + "&";
	}
	return urlList;
}

// <!-- 2.2 체인시 그 값을 다시 옵션에 넣기 -->
// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
// 콘솔 찍을시 나오는 size:1 은 현재 파라미터가 1개 있다는거
const urlParams = url.searchParams;
// 파라미터의 값 가져오기
const laName = urlParams.get('laName');
const typeName = urlParams.get('typeName');

// 옵션들 싹다 가져오고
const laNameList = $("#locationLaNameSelect").children();
for(let i = 0; i < laNameList.length; i++) {
	//innerHTML이 태그에 넣는데만 사용하는게 아니라 비교할때도 사용할수있음 저렇게하면 그 태그의 값을 가져옴
	if(laNameList[i].innerHTML === laName) {
		//맞으면 추가
		laNameList[i].setAttribute("selected", true);
	}
}
const typeNameList = $("#typeLaNameSelect").children();
for(let i = 0; i < typeNameList.length; i++) {
	if(typeNameList[i].innerHTML === typeName) {
		typeNameList[i].setAttribute("selected", true);
	}
}


// <!-- 2.1 체인시 그 값을 다시 옵션에 넣기 -->
//	1번의 로케이션 클릭했을때 이벤트 발생
$("#locationLaNameSelect").change(function() {
	// 클릭했을때 값을 laName에 저장
	/*
	const laName = $(this).val();
	if(laName!=='전체보기') {
		// laName에 값이 전체보기가 아니라면 href에 보내기 << 여기가 getMapping 되는곳
		location.href = "/LocationType?laName=" + laName;
	} else {
		// laName에 값이 널이면 아무것도 안보내기
		location.href = "/LocationType";
	}	
	*/
	const laName = $(this).val();
	if(laName!=='전체보기') {
		// laName에 값이 전체보기가 아니라면 href에 보내기 << 여기가 getMapping 되는곳
		location.href = "/LocationType?laName=" + laName;
	} else {
		// laName에 값이 널이면 아무것도 안보내기
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

$("#typeLaNameSelect").change(function(){
	let typeName = $(this).val();
	console.log(typeName)
	if(typeName != '전체보기'){
		location.href = "/LocationType?TypeName=" + typeName;
	}else{
		location.href = "/LocationType?"
	}
});


















