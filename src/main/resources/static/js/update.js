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

pwd.addEventListener("input", function () {
  const regExp = /^[!-~]{7,14}$/;
  const check = regExp.test(pwd.value);
  if (check) {
    pwdCheck.style.color = "green";
    pwdCheck.innerHTML = "OK";
  } else {
    pwdCheck.style.color = "red";
    pwdCheck.innerHTML = "형식에 맞춰서 입력하세요";
  }
});

name.addEventListener("input", function () {
  const regExp = /^[가-힣]{2,}$/;
  const check = regExp.test(name.value);
  if (check) {
    nameCheck.style.color = "green";
    nameCheck.innerHTML = "OK";
  } else {
    nameCheck.style.color = "red";
    nameCheck.innerHTML = "형식에 맞춰서 입력하세요";
  }
});

phone.addEventListener("input", function () {
  // 앞 세자리
  const regExp = /^(010-\d{4}-\d{4}|0[2-9]\d{1,2}-\d{3,4}-\d{4})$/;
  const check = regExp.test(phone.value);
  if (check) {
    phoneCheck.style.color = "green";
    phoneCheck.innerHTML = "OK";
  } else {
    phoneCheck.style.color = "red";
    phoneCheck.innerHTML = "형식에 맞춰서 입력하세요";
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
addr.addEventListener("input", function () {
  const regExp = /^[가-힣]$/;
  const check = regExp.test(addr.value);
  if (check) {
    addrCheck.style.color = "green";
    addrCheck.innerHTML = "OK";
  } else {
    addrCheck.style.color = "red";
    addrCheck.innerHTML = "형식에 맞춰서 입력하세요";
  }
});

email.addEventListener("input", function () {
  const regExp = /^[!-~]+@[!-~]+$/;
  const check = regExp.test(email.value);
  if (check) {
    emailCheck.style.color = "green";
    emailCheck.innerHTML = "OK";
  } else {
    emailCheck.style.color = "red";
    emailCheck.innerHTML = "형식에 맞춰서 입력하세요";
  }
});

age.addEventListener("input", function () {
  const regExp = /^[0-9]{1,2}$/;
  const check = regExp.test(age.value);
  if (check) {
    ageCheck.style.color = "green";
    ageCheck.innerHTML = "OK";
  } else {
    ageCheck.style.color = "red";
    ageCheck.innerHTML = "형식에 맞춰서 입력하세요";
  }
});

