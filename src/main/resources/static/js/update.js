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
// 상세주소
const addrDetail = document.querySelector("#addrDetail");
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
let addrDetailReg = false;
let emailReg = false;
let ageReg = false;
let allCheck = false;


// 비밀번호 체크
pwd.addEventListener("input", function () {
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

// 주소 체크
addr.addEventListener("input", function () {
  const regExp = /^[가-힣 ]*$/;

  if (regExp.test(addr.value)) {
	addrReg = true;
  } else {
	addrReg = false;
  }
});

// 상세 주소 체크
addrDetail.addEventListener("input", function () {
  const regExp = /^[가-힣 ]*$/;

  if (regExp.test(addrDetail.value)) {
	addrDetailReg = true;
  } else {
	addrDetailReg = false;
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
form.addEventListener("submit", function(e) {
	// 필드가 비어 있는지 확인
	if (!pwd.value || !name.value || !phone.value ||
		!addr.value || !addrDetail.value ||
		!email.value || !age.value) {
		alert("모든 필드를 입력해주세요.");
		e.preventDefault();
		return;
	}
	// 모든 정규표현식이 유효한지 확인
	if (pwdReg && nameReg && phoneReg &&
		addrReg && addrDetailReg &&
		emailReg && ageReg) {
		return true;
	} else {
		e.preventDefault();
		return;
	}
});

// 주소 검색
function sample5_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address; // 최종 주소 변수
            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("sample5_address").value = addr;
        }
    }).open();
}



