// 비밀번호
const pwd = document.querySelector("#pwd");
const pwdCheck = document.querySelector("#pwdCheck");
// 이름
const name = document.querySelector("#name");
const nameCheck = document.querySelector("#nameCheck");
// 연락처
const phone = document.querySelector("#phone");
const phoneCheck = document.querySelector("#phoneCheck");
// 주소
const addr = document.querySelector("#addr");
const addrCheck = document.querySelector("#addrCheck");
// 이메일
const email = document.querySelector("#email");
const emailCheck = document.querySelector("#emailCheck");
// 나이
const age = document.querySelector("#age");
const ageCheck = document.querySelector("#ageCheck");
// 정규표현식 체크
let pwdReg = false;
let nameReg = false;
let phoneReg = false;
let addrReg = false;
let emailReg = false;
let ageReg = false;



// 비밀번호 체크
pwd.addEventListener("input", function (e) {
  const regExp = /^[!-~]{7,14}$/;
  
  if (regExp.test(pwd.value)) {
    pwdCheck.style.color = "green";
    pwdCheck.innerHTML = "OK";
	pwdReg = true;
  } else {
    pwdCheck.style.color = "red";
    pwdCheck.innerHTML = "특수문자포함 7글자 이상 14글자 미만";
	pwdReg = false;
  }
});

// 이름 체크
name.addEventListener("input", function () {
  const regExp = /^[가-힣]{2,}$/;

  if (regExp.test(name.value)) {
    nameCheck.style.color = "green";
    nameCheck.innerHTML = "OK";
	nameReg = true;
  } else {
    nameCheck.style.color = "red";
    nameCheck.innerHTML = "한글 이름만 가능합니다";
	nameReg = false;
  }
});

// 연락처 체크
phone.addEventListener("input", function () {
  // 앞 세자리
  const regExp = /^(010-\d{4}-\d{4}|0[2-9]\d{1,2}-\d{3,4}-\d{4})$/;
  if (regExp.test(phone.value)) {
    phoneCheck.style.color = "green";
    phoneCheck.innerHTML = "OK";
	phoneReg = true;
  } else {
    phoneCheck.style.color = "red";
    phoneCheck.innerHTML = " - 을 포함시켜서 입력하세요";
	phoneReg = false;
  }
});

/*
010-1234-5678: 휴대폰 번호
02-123-4567: 서울 지역번호
031-123-4567: 경기도 지역번호
032-123-4567: 인천 지역번호
033-123-4567: 강원도 지역번호
041-123-4567: 충청남도 지역번호
042-123-4567: 대전 지역번호
043-123-4567: 충청북도 지역번호
044-123-4567: 세종 지역번호
051-123-4567: 부산 지역번호
052-123-4567: 울산 지역번호
053-123-4567: 대구 지역번호
054-123-4567: 경상북도 지역번호
055-123-4567: 경상남도 지역번호
061-123-4567: 전라남도 지역번호
062-123-4567: 광주 지역번호
063-123-4567: 전라북도 지역번호
064-123-4567: 제주 지역번호
*/

// 주소는 입력창 새로 만들어야함
// 주소 체크
addr.addEventListener("input", function () {
  const regExp = /^[가-힣 ]*$/;

  if (regExp.test(addr.value)) {
    addrCheck.style.color = "green";
    addrCheck.innerHTML = "OK";
	addrReg = true;
  } else {
    addrCheck.style.color = "red";
    addrCheck.innerHTML = "형식에 맞춰서 입력하세요";
	addrReg = false;
  }
});

// 이메일 체크
email.addEventListener("input", function () {
  const regExp = /^[!-~]+@[!-~]+$/;

  if (regExp.test(email.value)) {
    emailCheck.style.color = "green";
    emailCheck.innerHTML = "OK";
	emailReg = true;
  } else {
    emailCheck.style.color = "red";
    emailCheck.innerHTML = "이메일 형식으로 입력하세요";
	emailReg = false;
  }
});

// 나이 체크
age.addEventListener("input", function () {
  const regExp = /^[0-9]{1,2}$/;

  if (regExp.test(age.value)) {
    ageCheck.style.color = "green";
    ageCheck.innerHTML = "OK";
	ageReg = true;
  } else {
    ageCheck.style.color = "red";
    ageCheck.innerHTML = "숫자만 입력 가능합니다";
	ageReg = false;
  }
});

// 정규표현식 다 맞으면 form 제출
const form = document.querySelector("#form")
form.addEventListener("submit",function (e) {
	// 모든 정규표현식이 유효한지 확인
	if (pwdReg && nameReg && phoneReg && addrReg && emailReg && ageReg){
		return true;
	} else {
		e.preventDefault();
		alert("!");
	}
});




