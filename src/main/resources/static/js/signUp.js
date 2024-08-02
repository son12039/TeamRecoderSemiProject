


function showPage(pageId) {
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
function idPwdCheck() {
    const id = document.getElementById("id").value;
    const pwd = document.getElementById("pwd").value;
    const pwdc = document.getElementById("pwdc").value;
    const idRegExp = /^[a-zA-Z0-9]{10,20}$/;
    const pwdRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{10,20}$/;

    if (!idRegExp.test(id)) {
        alert("아이디는 10~20자의 영문자와 숫자로만 구성되어야 합니다!!!");
        return false;
    }

    if (!pwdRegExp.test(pwd)) {
        alert("비밀번호는 10~20자의 영문자, 숫자, 특수문자가 포함되어야 합니다!!!");
        return false;
    }

    if (pwd !== pwdc) {
        alert("비밀번호가 일치하지 않습니다!!!");
        return false;
    }

    return true;
}
