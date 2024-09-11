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
	var newPage = document.getElementById(pageId);
	newPage.classList.remove('hidden');
	newPage.classList.add('visible');

	window.scrollTo({
		top: 0,
		behavior: 'smooth'
	});
}
const idRegExp = /^[a-zA-z][a-zA-Z0-9]{7,13}$/;
const pwdRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,15}$/;
const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

let idSubmit = false;
let pwdSubmit = false;
let pwdcSubmit = false;
let nicknameSubmit = false;
let emailSubmit = false;
let ageSubmit = false;
let nameSubmit = false;

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
					$('#idResult').html('<img src="https://cdn-icons-png.flaticon.com/512/11433/11433360.png" width="13px">');
					idSubmit = true;
				} else {
					$('#idResult').text(" 중복 ID 입니다").css('color', 'red');
					idSubmit = false;
				}
			}
		});
	}
});

pwd.addEventListener('input', function() { // 비밀번호 체크
	const pwdValue = $(this).val().trim();

	if (pwdValue === '') {
		$('#pwdResult').text(" 필수 입력사항입니다").css('color', 'red');
		pwdSubmit = false;
	} else if (!pwdRegExp.test(pwdValue)) {
		$('#pwdResult').text(" 특수문자,영문자,숫자가 한개이상 무조건 포함되어야 합니다.(8~15자)").css('color', 'red');
		pwdSubmit = false;
	} else {
		pwdSubmit = true;
		$('#pwdResult').html('<img src="https://cdn-icons-png.flaticon.com/512/11433/11433360.png" width="13px">');
	}
});
pwdc.addEventListener('input', function() { // 비밀번호확인 체크
	const pwdcValue = $(this).val().trim();

	if (pwdcValue === $("#pwd").val()) {
		$('#pwdcResult').html('<img src="https://cdn-icons-png.flaticon.com/512/11433/11433360.png" width="13px">');
		pwdcSubmit = true;
	} else {

		$('#pwdcResult').text(" 비밀번호가 일치하지 않습니다.").css('color', 'red');
		pwdcSubmit = false;

	}
});
let nick = "";
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
				if (result) { // 리턴은 boolean
					console.log(result);
					$('#nicknameResult').html('<img src="https://cdn-icons-png.flaticon.com/512/11433/11433360.png" width="13px">');
					nick = nicknameValue;
					nicknameSubmit = true;
				} else {
					console.log(result);
					$('#nicknameResult').text("중복 닉네임 입니다").css('color', 'red');
					nicknameSubmit = false;
				}
			}
		});
	}
});
email.addEventListener('input', function() { // 닉네임 체크
	const emailValue = $(this).val().trim();

	if (emailValue === '') {
		$('#emailResult').text(" 필수 입력사항입니다").css('color', 'red');
		emailSubmit = false;
	} else if (!emailRegExp.test(emailValue)) {
		$('#emailResult').text("이메일 형식에 맞춰야 합니다.").css('color', 'red');
		emailSubmit = false;
	} else {
		$.ajax({
			type: 'POST',
			url: '/emailCheck', // 컨트롤러 URL
			data: { email: emailValue },
			success: function(result) {
				if (result) { // 서버 응답을 'true' 또는 'false'로 가정
					console.log(result);
					$('#emailResult').html('<img src="https://cdn-icons-png.flaticon.com/512/11433/11433360.png" width="13px">');
					emailSubmit = true;
				} else {
					console.log(result);
					$('#emailResult').text(" 중복 이메일 입니다").css('color', 'red');
					emailSubmit = false;
				}
			}
		});
	}
});

age.addEventListener('input', function() { // 나이체크
	const ageValue = $(this).val().trim();

	if (ageValue === '' || isNaN(Number(ageValue)) || Number(ageValue) <= 0) {
		ageSubmit = false;
		$('#ageResult').text(" 올바른 나이를 입력해주십시오.").css('color', 'red');
	} else {
		$('#ageResult').text("").css('color', 'green');
		ageSubmit = true;
	}
});
// 이름
const nameInput = document.getElementById('name');
nameInput.addEventListener('input', function() { // 이름 확인
	const nameValue = $(this).val().trim();

	if (nameValue === '') {
		nameSubmit = false;
		$('#nameResult').text("이름을 입력해 주십시오.").css('color', 'red');
	} else {
		$('#nameResult').html('<img src="https://cdn-icons-png.flaticon.com/512/11433/11433360.png" width="13px">');
		nameSubmit = true;
	}
});
const ageInput = document.getElementById('age');
ageInput.addEventListener('input', function() { // 이름 확인
	const ageValue = $(this).val().trim();

	if (ageValue === '') {
		ageSubmit = false;
		$('#ageResult').text("나이를 입력해 주십시오.").css('color', 'red');
	} else {
		$('#ageResult').html('<img src="https://cdn-icons-png.flaticon.com/512/11433/11433360.png" width="13px">');
		ageSubmit = true;
	}
});
function validate() {
	if (!idSubmit) id.focus();

	else if (!pwdSubmit) pwd.focus();

	else if (!pwdcSubmit) pwdc.focus();

	else if (!ageSubmit) age.focus();

	else if (!nameSubmit) nameInput.focus();

	else if (!nicknameSubmit) nickname.focus();

	else if (!emailSubmit) email.focus();

	if (!(idSubmit && pwdSubmit && pwdcSubmit && ageSubmit && nicknameSubmit && emailSubmit && nameSubmit)) {
		alert("!! 다시 확인해주십시오!!");
	}
	if ((idSubmit && pwdSubmit && pwdcSubmit && ageSubmit && nicknameSubmit && emailSubmit && nameSubmit)) {

		alert(nick + "님 환영합니다!");
	}

	return idSubmit && pwdSubmit && pwdcSubmit && ageSubmit && nicknameSubmit && emailSubmit && nameSubmit;
}