// 현재 페이지에 대한 url 저장하기
const url = new URL(location.href);
// 현재 페이지에 대한 url 파라미터 가져오기기
const urlParams = url.searchParams;

// 상위 지역 : locationLaName, 하위 지역들 : locationSName, 
// 상위 유형 : typeLaName, 하위 유형들 : typeSName

// 파라미터값 존재여부 체크 & 파라미터 값 가져오기
// 상위 지역 필터

if(urlParams.has("locationLaName")) {
	
	const locationLaName = urlParams.get('locationLaName');
	const list = $('#locationLaNameSelect option');
	for(let item of list) {
		if(locationLaName === item.value) {
			item.setAttribute('selected', true);
		}
	}
}

if(urlParams.has("locationSName")) {
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

$("#locationSNameForm").submit((e) => {
	e.preventDefault();
	urlParams.delete("locationSName");
	const locationSName = e.target.locationSName;
	if(locationSName.length === undefined) {
		// 세종시 -> 즉 1개일 때
		if(locationSName.checked) {
			urlParams.append('locationSName', locationSName.value);
		}
	} else {
		// 그게 아니라면 여러값 리스트!
		for(let item of locationSName) {
			if(item.checked) {
				urlParams.append('locationSName', item.value);
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




console.log(urlParams.has("typeLaName")); // false or true
console.log(urlParams.has("typeSName")); // false or true

//


const typeLaName = urlParams.get('typeLaName');
const typeSName = urlParams.getAll('typeSName');


console.log(typeLaName); // null or 디저트
console.log(typeSName); // [] or [커피, 아이스티]





