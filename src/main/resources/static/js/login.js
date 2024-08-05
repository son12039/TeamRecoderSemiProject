// 아이디, 비밀번호, 비밀번호 확인 태그 받기
let id = document.querySelector("#memberId");
let pwd = document.querySelector("#memberPwd");
let ckPwd = document.querySelector("#PwdCheck");

// 각 태그에 변화 시 체크 조건 실행
id.onchange = checkId;
pwd.onchange = checkPwd;
ckPwd.onchange = comparePwd;

// 아이디 체크조건
function checkId() {
  if (id.value.length > 15 || id.value.length < 4) {
    alert("4~15 자리의 아이디를 입력하세요 :)");
    id.select();
  }
}

// 비밀번호 체크조건
function checkPwd() {
  if (pwd.value.length < 8) {
    alert("8 자리 이상의 비밀번호를 입력하세요 :)");
    document.querySelector("#memberPwd").value = "";
    pwd.focus();
  }
}

// 비밀번호 확인 체크조건
function comparePwd() {
  if (pwd.value != ckPwd.value) {
    alert("비밀번호가 일치하지 않습니다 :)");
    document.querySelector("#PwdCheck").value = "";
    ckPwd.focus();
  }
}
