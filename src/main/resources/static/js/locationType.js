// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
const urlParams = url.searchParams;

//window.scrollTo({top: localStorage.getItem("scroll"), behavior: 'instant'});

// 상위 지역 : locationLaName, 하위 지역들 : locationSName, 
// 상위 유형 : typeLaName, 하위 유형들 : typeSName

// 파라미터값 존재여부 체크 & 파라미터 값 가져오기
// 상위 지역 필터


if(urlParams.has("locationLaName")) {
	window.scrollTo({top:630, left:0, behavior:'smooth'});// 클릭시 해당 위치로
	const locationLaName = urlParams.get('locationLaName');
	const list = $('#locationLaNameSelect option');
	for(let item of list) {
		if(locationLaName === item.value) {
			item.setAttribute('selected', true);
		}
	}
}
if(urlParams.has("locationSName")) {
	window.scrollTo({top:630, left:0, behavior:'smooth'});
	const locationSName = urlParams.getAll('locationSName');
	const list = $('#locationSNameForm input');
	for(let item of list) {
		if(locationSName.includes(item.value)) {
			item.setAttribute("checked", true);
		}
	}
}

$("#locationLaNameSelect").change(function() {
	urlParams.delete("locationLaName");
	urlParams.delete("locationSName");
	const laName = $(this).val();
	if(laName !== '전체보기') {
		urlParams.append('locationLaName', laName);
	}
	location.href = url;

});


	
$("#locationSNameForm input[type=checkbox]").change(function() {
	const locationSName = $(this).val();

	if(locationSName === '전체보기') {
		urlParams.delete('locationSName');
	} else if($(this).is(':checked')) {
		urlParams.append('locationSName', locationSName);
	} else {
		urlParams.delete('locationSName');
		const inputAll = $(this).parent().find("input[type=checkbox]");
		for(let input of inputAll) {
			if(input.checked) {
				urlParams.append("locationSName", input.value);
			}
		}

	}
	location.href = url;

});



$("#locSNameAll").change(function(){
	if($("#locSNameAll").is(":checked")){
		for(let item of $('#locationSNameForm input[name="locationSName"]')) {
			item.checked = false;
		}
    }
});
$('#locationSNameForm input[name="locationSName"]').change(function() {
	if($("#locSNameAll").is(":checked")){
		$("#locSNameAll")[0].checked = false;
	} 
})



/*타입 클릭시*/
$("#typeLaNameSelect").change(function(){
	let typeLaName = $(this).val();
	urlParams.delete('typeLaName')
	urlParams.delete('typeSName')
	if(typeLaName!='전체보기'){
		urlParams.append('typeLaName',typeLaName)	
	}
	location.href = url;
})


$("#typeSNameForm input[type=checkbox]").change(function(){
	const typeSName = $(this).val();
	console.log(typeSName);
	if(typeSName === '전체보기'){
		urlParams.delete('typeSName');	
	}else if($(this).is(':checked')){
		urlParams.append('typeSName', typeSName);
	}else {
		urlParams.delete('typeSName');
		const inputAll = $(this).parent().find("input[type=checkbox]");
		for(let input of inputAll){
			if(input.checked){
				urlParams.append('typeSName', input.value);
			}
		}
	}
	location.href = url;
})



if(urlParams.has("typeLaName")){
	window.scrollTo({top:630, left:0, behavior:'smooth'});
	let typeLaName = urlParams.get("typeLaName")
	let list = $('#typeLaNameSelect option')
	for(let item of list){
		if(typeLaName === item.value){
			item.setAttribute("selected",true)
		}
	}
}
if(urlParams.has("typeSName")){
	window.scrollTo({top:630, left:0, behavior:'smooth'});
	const typeSName = urlParams.getAll('typeSName');	
	const list = $('#typeSNameForm input');
	for(let item of list){
		if(typeSName.includes(item.value)){
			item.setAttribute("checked",true);
		}
	}
}

$("#typeSNameAll").change(function(){
	if($("#typeSNameAll").is(":checked")){
		for(let item of $('#typeSNameForm input[name="typeSName"]')) {
			item.checked = false;
		}
    }
});

$('#typeSNameForm input[name="typeSName"]').change(function() {
	if($("#typeSNameAll").is(":checked")){
		$("#typeSNameAll")[0].checked = false;
	} 
})


