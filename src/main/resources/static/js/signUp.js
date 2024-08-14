function showPage(pageId) { // 회원가입 페이지 이동
  document.querySelectorAll(".page").forEach((page) => {
    if (page.id === pageId) {
      page.classList.remove("hidden");
      page.classList.add("visible");
    } else {
      page.classList.remove("visible");
      page.classList.add("hidden");
    }
  });
}
const idRegExp = /^[a-zA-z][a-zA-Z0-9]{7,13}$/;
const pwdRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,15}$/;

let idSubmit = false;
let pwdSubmit = false;
let pwdcSubmit = false;
let nicknameSubmit = false;
let ageSubmit = false;

id.addEventListener('input', function() { // 아이디 체크
  const idValue = $(this).val().trim();
  
  if (idValue === '') {
    $('#idResult').text(" 필수 입력사항입니다").css('color', 'red');
    idSubmit = false;
  } else if (!idRegExp.test(idValue)) {
    $('#idResult').text(" 첫글자는 영어로, 영어 또는 숫자로만 8~14글자로 구성할수 있습니다.").css('color', 'red');
    idSubmit = false;
  } else {
    $.ajax({
      type: 'POST',
      url: '/idCheck', // 컨트롤러 URL
      data: { id: idValue },
      success: function(result) {
        if (result) { 
          $('#idResult').text(" 사용 가능한 ID 입니다").css('color', 'green');
          idSubmit = true;
        } else {
          $('#idResult').text(" 중복 ID 입니다").css('color', 'red');
          idSubmit = false;
        }
      }
    });
  }
});

pwd.addEventListener('input', function() { // 아이디 체크
  const pwdValue = $(this).val().trim();
  
  if (pwdValue === '') {
    $('#pwdResult').text(" 필수 입력사항입니다").css('color', 'red');
    pwdSubmit = false;
  } else if (!pwdRegExp.test(pwdValue)) {
    $('#pwdResult').text(" 특수문자,대문자,소문자,숫자가 한개이상 무조건 포함되어야 합니다.(8~15자)").css('color', 'red');
    pwdSubmit = false;
  } else{
	pwdSubmit = true;
	$('#pwdResult').text(" 사용 가능한 비밀번호입니다.").css('color', 'green');
  }
 });
pwdc.addEventListener('input', function() { // 아이디 체크
    const pwdcValue = $(this).val().trim();
    
    if (pwdcValue === $("#pwd").val()) {
		$('#pwdcResult').text(" 비밀번호가 일치합니다").css('color', 'green');
		pwdcSubmit = true;
    } else {
	  
	  $('#pwdcResult').text(" 비밀번호가 일치하지 않습니다.").css('color', 'red');
	  pwdcSubmit = false;
      
    } 
 });
nickname.addEventListener('input', function() { // 닉네임 체크
  const nicknameValue = $(this).val().trim();
  
  if (nicknameValue === '') {
    $('#nicknameResult').text(" 필수 입력사항입니다").css('color', 'red');
    nicknameSubmit = false;
  } else {
    $.ajax({
      type: 'POST',
      url: '/nicknameCheck', // 컨트롤러 URL
      data: { nickname: nicknameValue },
      success: function(result) {
        if (result) { // 서버 응답을 'true' 또는 'false'로 가정
			console.log(result);
          $('#nicknameResult').text(" 사용 가능한 닉네임 입니다").css('color', 'green');
          nicknameSubmit = true;
        } else {
			console.log(result);
          $('#nicknameResult').text(" 중복 닉네임 입니다").css('color', 'red');
          nicknameSubmit = false;
        }
      }
    });
  }
});

age.addEventListener('input', function() { // 나이체크
  const ageValue = $(this).val().trim();
  
  if (ageValue === '' || isNaN(Number(ageValue)) || Number(ageValue) <= 0) {
	ageSubmit = false;
	$('#ageResult').text(" 올바른 나이를 입력해주십시오.").css('color', 'green');
  } else{
	$('#ageResult').text("");
	ageSubmit = true;
  }
});
// 버튼색 변경
function updateSubmitButtonState() {
    const submitButton = document.getElementById('singUp');
    if (idSubmit && pwdSubmit && pwdcSubmit && ageSubmit && nicknameSubmit) {
        submitButton.classList.add('button-enabled');
    } else {
        submitButton.classList.remove('button-enabled');
    }
}

function validate() {
	if(!idSubmit)id.focus();
	
	else if(!pwdSubmit)pwd.focus();
	
	else if(!pwdcSubmit)pwdc.focus();
	
	else if(!ageSubmit)age.focus();
	
	else if(!nicknameSubmit)nickname.focus();
	
	if(!(idSubmit && pwdSubmit && pwdcSubmit && ageSubmit && nicknameSubmit)){
		alert("!! 다시 확인해주십시오!!");
	}
	
	return idSubmit && pwdSubmit && pwdcSubmit && ageSubmit && nicknameSubmit;
}
