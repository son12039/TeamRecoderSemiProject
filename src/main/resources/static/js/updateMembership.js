// 현재 페이지의 URL에서 쿼리 문자열과 해시(#)를 제거할때 사용 
					//(현재 페이지,제목 문자열, 현재 페이지 경로를 포함된 URL )
history.replaceState({}, null, location.pathname); // {}: 현재 페이지 상태 저장을 위한 빈칸 사용, 저장 필요 없으면 null 사용가능  








let zIndex = -1;

function imgShow(event) {
	 var reader = new FileReader();
	   
	    reader.onload = function(event) {
	    	console.log(container)
	        var container = document.getElementById('image_container');
	     
	        var img = document.createElement('img');
	        img.className = 'image'
	        
	        img.setAttribute('src', event.target.result);
	        container.appendChild(img);
	      
	        
	    };
	   
	   
	    
	    if (event.target.files.length > 0) {
	    
	    	
	        reader.readAsDataURL(event.target.files[0]);
	     	zIndex = -1 ;
	        $("#default").css('z-index', zIndex)
	        
	    } else {
	    	
	    	zIndex = -1 ;
	    	 $("#default").css('z-index', zIndex)
	    	$(".image").remove();
	    	
	    }
}







	   function asd() {
	    
	     document.getElementById('file').value = "";
	    	 zIndex = 10 ;
	        $('#default').css('z-index', zIndex); 
	    };
	



























//@@@@@@@@@@@@@@@@@@@@@@@@
// 클럽명 중복 체크
let membershipNameCheck = true;
membershipName.addEventListener('input', function() {
	const membershipNameValue = $(this).val().trim();
	const membershipCodeValue = $("#membershipCode").val()

	$.ajax({
		type: 'POST',
		url: '/membershipNameCheck', // 컨트롤러 URL
		data: { membershipName: membershipNameValue,
			membershipCode : membershipCodeValue
		 },
		success: function(result) {
			if (result) {
				if (membershipNameValue === "") {
					$('#name').text(" 필수 입력사항입니다").css('color', 'red');
					membershipNameCheck = false;
				} else if (membershipNameValue.length >= 50) {
					$('#name').text("50자 이하까지 쓸 수 있습니다.").css('color', 'red');
					membershipNameCheck = false;
				} else {
					$('#name').text(" 사용 가능한 클럽명 입니다").css('color', 'green');
					membershipNameCheck = true;
				}

			} else {
				$('#name').text(" 중복된 클럽명입니다 다시 작성해 주세요").css('color', 'red');
				membershipNameCheck = false;
			}
		}
	});
});

// 클럽 인원수 설정
/*const membershipMaxRegExp = /^[0-9]{2,100}$/;*/

let membershipMaxSubmit = true; // 최대인원 설정 여부를 나타내는 변수 초기화

// membershipMax 요소에 입력 이벤트 리스너 추가
membershipMax.addEventListener('input', function() {
	const membershipMaxValue = $(this).val().trim(); // jQuery를 사용하여 현재 값 가져오기
	const inputNumber = parseFloat(membershipMaxValue);
	
	if (membershipMaxValue === '') {
		$('#max').text(" 필수 입력사항입니다").css('color', 'red');
		membershipMaxSubmit = false; // 제출 불가 상태로
		// 최소지정 2 < 지금 4  
		// 최대값을 지정  최소인원 현재원  ~ 최대원 
		// 최대 인원 < 100 
		// 정한 최대인원이 지금 현재인원보다 작아서는 안된다 
		// 최대인원이 현재 인원보다 크거나 같으면서 101보다는 작다 
		// 현재인원 <= 최대인원 < 101 
	} else if (inputNumber >= $("#count").text() && inputNumber < 101) {
		$('#max').text("설정 가능한 인원수 입니다").css('color', 'green');
				membershipMaxSubmit = true;	
	} else {
			$('#max').text("설정 가능한 최소 인원은 "+$("#count").text()+"명 최대 인원수는 100명입니다").css('color', 'red');
			membershipMaxSubmit = false;
	}
});


// 지역 선택 시 변경 이벤트
$('#locationLaNameMem').change(function() {
	let location = $(this).val()// 선택된 지역 값을 가져옴
	let list = "";// 지역 하위 목록을 저장할 변수
	let allList = `<option>전체보기</option>`;
	$.ajax({
		type: 'post',
		url: '/memLocation',
		data: 'locationLaName=' + location,
		success: function(locationS) {
			$.each(locationS, function(index, item) {
				list += `<option>${item}</option>`;
			});
			if (list !== "") {
				$("#locationSNameMem").html(list);	// 하위 지역 목록을 업데이트
			} else {
				$("#locationSNameMem").html(allList); // 하위 지역이 없으면 전체보기로 설정
			}

		}

	})
});

// 유형 선택 시 변경 이벤트
$('#typeLaNameMem').change(function() {
	let type = $(this).val()
	let list = "";
	let allList = `<option>전체보기</option>`;

	$.ajax({
		type: 'post',
		url: '/memType',
		data: $.param({ typeLaName: type }), // & 문자가 URL 쿼리 문자열에서 파라미터 구분자로 사용됨 그래서 URL인코딩 처리를 해줘야 함
		success: function(typeS) {
			$.each(typeS, function(index, item) {
				list += `<option>${item}</option>`;


			});
			if (list !== "") {
				$("#typeSNameMem").html(list);
				console.log("if", type);
			} else {
				$("#typeSNameMem").html(allList);
				console.log("else", type);
			}

		}

	});
});



// 클릭 이벤트 사용해서 클릭하면 선택한 정보가 따로 쌓이게
let locationBtnCheck = false; // 클릭 상태를 나타내는 변수 (초기값은 false)
let LB = ""; // 선택된 지역 정보를 저장할 변수 
let list = "";
let allList = `<option>전체보기</option>`; // 전체보기 옵션을 포함한 HTML 문자열
let selectedAreas = []; // 선택된 지역 정보를 저장할 배열
locationBtn.addEventListener("click", function(e) {
	e.preventDefault();
	let locationbig = $("#locationLaNameMem option:selected").val();
	let locationsmall = $("#locationSNameMem option:selected").val();
	let string = "";
// 앞에 두글자가 선택된 이름의 값과 다르면서 공백이 아닐때 

	if ($("#test1").html().substr(0, 2) != locationbig && $("#test1").html() != "") {
		alert("하나의 카테고리에서만 선택할 수 있습니다!");
		return;
	}
	if ($("#test1").html().includes(locationsmall) && $("#test1").html() !== "") {
		alert("중복선택은 불가능합니다");
		return;
	}
	if ($("#test1").html() == "") {
		string += $("#test1").html() + locationbig + " = " + locationsmall;
	} else {
		string += $("#test1").html() + ", " + locationsmall;
	}
	if ($("#locationLaNameMem option:selected").val() === "전체보기") {
		locationBtnCheck = false;
		alert("지역을 선택해 주세요")
		return; // 클릭 이벤트를 막고 함수 종료
	}
	$("#test1").html(string);
});

locationBtncancel.addEventListener("click", function() {
	$("#test1").empty();
	LB = "";// LB 변수도 초기화
	selectedAreas = [];

});



// 클릭 이벤트 사용해서 클릭하면 선택한 정보가 따로 쌓이게
let typeBtnCheck = false; // 클릭 상태를 나타내는 변수 (초기값은 false)
let TB = ""; // 선택된 유형 정보를 저장할 변수
let allList2 = `<option>전체보기</option>`; // 전체보기 옵션을 포함한 HTML 문자열
let selectedtype = []; // 선택된 유형 정보를 저장할 배열(중복체크용)
let typetext = "";
// typeBtn 버튼에 클릭 이벤트 리스너 추가
typeBtn.addEventListener("click", function(e) {
	e.preventDefault();
	let typebig = $("#typeLaNameMem option:selected").val();
	let typesmall = $("#typeSNameMem option:selected").val();
	let string = "";

	if ($("#test2").html().substr(0, 2) !== typebig.substr(0, 2) && $("#test2").html() !== "") {
		alert("하나의 카테고리에서만 선택할 수 있습니다!");
		return;
	}
	if ($("#test2").html().includes(typesmall) && $("#test2").html() !== "") {
		alert("중복선택은 불가능합니다!");
		return;
	}
	if ($("#test2").html() == "") {
		string += $("#test2").html() + typebig + " = " + typesmall;
	} else {
		string += $("#test2").html() + ", " + typesmall;
	}
	if ($("#typeLaNameMem option:selected").val() === "전체보기") {
		typeBtnCheck = false;
		alert("유형을 선택해 주세요")
		return; // 클릭 이벤트를 막고 함수 종료
	}
	$("#test2").html(string);

});

// 유형 취소
typeBtncancel.addEventListener("click", function() {
	$("#test2").empty();
	TB = "";// TB 변수도 초기화
	selectedAreas = [];


});

function validate() { // 생성버튼 눌렀을때 작동
	
	alert("??")
	
	let membershipName = $("#membershipName").val();
	let f = document.getElementById('file');
	

	let file = f.files[0];
	
	let membershipCode =$("#membershipCode").val();
	let membershipAccessionText = $("#membershipAccessionText").val();
	let membershipSimpleText = $("#membershipSimpleText").val();
	let membershipMax = $("#membershipMax").val();
	let loc = $("#test1").text();
	let tp = $("#test2").text();

	
if(!membershipNameCheck){
	
	alert("클럽명을 다시 확인해주세요!")
	
	return;
}	
	
if(!membershipMaxSubmit){
	
	alert("인원 수를 다시 확인해주세요!")
	
	return;
}	
		
if(loc === "" || tp === ""){
	alert("지역 혹은 타입을 확인해주세요! ")
	return;
	
}
	
	

	let formData = new FormData(); // FormData 객체를 사용하여 파일과 데이터를 함께 전송합니다.
	//formData.append('dto', JSON.stringify(data));
	formData.append('membershipName',membershipName)
	formData.append('membershipCode',membershipCode)
	formData.append('membershipAccessionText',membershipAccessionText)
	formData.append('membershipSimpleText',membershipSimpleText)
	formData.append('membershipMax',membershipMax)
	formData.append('zIndex', zIndex)
	if(file !== undefined){
	formData.append('file', file);
	}
	formData.append('LB', loc);
	formData.append('TB', tp);
		console.log(membershipName);
		console.log(membershipAccessionText);
		console.log(membershipSimpleText);
		console.log(membershipMax);
		console.log(loc);
		console.log(tp);
		console.log(file);
		console.log(zIndex);
		console.log([...formData.entries()]);
	$.ajax({
		type: 'post',
		url: '/updateMembership',
		data: formData,
		processData: false,
		contentType: false,
		success: function() {

			
			//  mypage 페이지로 이동
			window.location.href = '/mypage';
			
		}
		
	});
	
	

}