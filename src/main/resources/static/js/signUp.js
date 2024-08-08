const id = document.querySelector("#id");
const pwd = document.querySelector("#pwd");
const pwdc = document.querySelector("#pwdc");
const idRegExp = /^[a-zA-Z0-9]{10,20}$/;
const pwdRegExp =
  /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{10,20}$/;

function showPage(pageId) {
  // 회원가입 페이지 이동
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

id.addEventListener("submit", function idCheck() {
  // id 유효성 채크
  if (!idRegExp.test(id)) {
    alert("아이디는 10~20자의 영문자와 숫자로만 구성되어야 합니다!!!");
    return false;
  } // 따로 분리해서 id 검사 이후 중복체크 기능 추가
  return true;
});
pwd.addEventListener("submit", function pwdCheck() {
  if (!pwdRegExp.test(pwd)) {
    alert("비밀번호는 10~20자의 영문자, 숫자, 특수문자가 포함되어야 합니다!!!");
    return false;
  }

  return true;
});

pwdc.addEventListener("submit", function pwdcCheck() {
  if (pwd !== pwdc) {
    return false;
  }
  return true;
});

// 닉네임 중복 체크 기능

// 위사항들 해당시 데이터 안보내게
