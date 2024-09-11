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

const pwdRegExp =
  /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,15}$/;
const emailRegExp =
  /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

let pwdSubmit = false;
let pwdcSubmit = false;
let nicknameSubmit = true;
let emailSubmit = true;
let ageSubmit = true;
let nameSubmit = true;
let beforePwd = false;

pwd.addEventListener("input", function () {
  // 비밀번호 체크
  const pwdValue = $(this).val().trim();

  if (pwdValue === "") {
    $("#pwdResult").text(" 필수 입력사항입니다").css("color", "red");
    pwdSubmit = false;
  } else if (!pwdRegExp.test(pwdValue)) {
    $("#pwdResult")
      .text(
        " 특수문자,대문자,소문자,숫자가 한개이상 무조건 포함되어야 합니다.(8~15자)"
      )
      .css("color", "red");
    pwdSubmit = false;
  } else {
    pwdSubmit = true;
    $("#pwdResult").text(" 사용 가능한 비밀번호입니다.").css("color", "green");
  }
});
pwdc.addEventListener("input", function () {
  // 비밀번호확인 체크
  const pwdcValue = $(this).val().trim();

  if (pwdcValue === $("#pwd").val()) {
    $("#pwdcResult").text(" 비밀번호가 일치합니다").css("color", "green");
    pwdcSubmit = true;
  } else {
    $("#pwdcResult").text(" 비밀번호가 일치하지 않습니다.").css("color", "red");
    pwdcSubmit = false;
  }
});

nickname.addEventListener("input", function () {
  // 닉네임 체크
  const nicknameValue = $(this).val().trim();

  if (nicknameValue === "") {
    $("#nicknameResult").text(" 필수 입력사항입니다").css("color", "red");
    nicknameSubmit = false;
  } else {
    $.ajax({
      type: "POST",
      url: "/nicknameCheck", // 컨트롤러 URL
      data: { nickname: nicknameValue },
      success: function (result) {
        if (result) {
          // 서버 응답을 'true' 또는 'false'로 가정
          console.log(result);
          $("#nicknameResult")
            .text(" 사용 가능한 닉네임 입니다")
            .css("color", "green");
          nicknameSubmit = true;
        } else {
          console.log(result);
          $("#nicknameResult").text(" 중복 닉네임 입니다").css("color", "red");
          nicknameSubmit = false;
        }
      },
    });
  }
});
email.addEventListener("input", function () {
  // 이메일
  const emailValue = $(this).val().trim();

  if (emailValue === "") {
    $("#emailResult").text(" 필수 입력사항입니다").css("color", "red");
    emailSubmit = false;
  } else if (!emailRegExp.test(emailValue)) {
    $("#emailResult").text("이메일 형식에 맞춰야 합니다.").css("color", "red");
    emailSubmit = false;
  } else {
    $.ajax({
      type: "POST",
      url: "/emailCheck", // 컨트롤러 URL
      data: { email: emailValue },
      success: function (result) {
        if (result) {
          console.log(result);
          $("#emailResult")
            .text(" 사용 가능한 이메일 입니다")
            .css("color", "green");
          emailSubmit = true;
        } else {
          console.log(result);
          $("#emailResult").text(" 중복 이메일 입니다").css("color", "red");
          emailSubmit = false;
        }
      },
    });
  }
});

age.addEventListener("input", function () {
  // 나이체크
  const ageValue = $(this).val().trim();

  if (ageValue === "" || isNaN(Number(ageValue)) || Number(ageValue) <= 0) {
    ageSubmit = false;
    $("#ageResult").text(" 올바른 나이를 입력해주십시오.").css("color", "red");
  } else {
    $("#ageResult").text("").css("color", "green");
    ageSubmit = true;
  }
});
// 이름
const nameInput = document.getElementById("name");
nameInput.addEventListener("input", function () {
  // 이름 확인
  const nameValue = $(this).val().trim();

  if (nameValue === "") {
    nameSubmit = false;
    $("#nameResult").text("이름을 입력해 주십시오.").css("color", "red");
  } else {
    $("#nameResult").text("");
    nameSubmit = true;
  }
});

function validate() {
  if (!pwdSubmit) pwd.focus();
  else if (!pwdcSubmit) pwdc.focus();
  else if (!ageSubmit) age.focus();
  else if (!nameSubmit) nameInput.focus();
  else if (!nicknameSubmit) nickname.focus();
  else if (!emailSubmit) email.focus();

  if (
    !(
      pwdSubmit &&
      pwdcSubmit &&
      ageSubmit &&
      nicknameSubmit &&
      emailSubmit &&
      nameSubmit
    )
  ) {
    console.log("비밀번호 : " + pwdSubmit);
    console.log("비밀번호확인 : " + pwdcSubmit);
    console.log("나이 : " + ageSubmit);
    console.log("닉네임 : " + nicknameSubmit);
    console.log("이메일 : " + emailSubmit);
    console.log("이름 : " + nameSubmit);
    alert("!! 다시 확인해주십시오!!");
  }
  if (
    pwdSubmit &&
    pwdcSubmit &&
    ageSubmit &&
    nicknameSubmit &&
    emailSubmit &&
    nameSubmit
  ) {
    alert("변경완료");
  }

  return (
    pwdSubmit &&
    pwdcSubmit &&
    ageSubmit &&
    nicknameSubmit &&
    emailSubmit &&
    nameSubmit
  );
}

// 주소 검색
function sample5_execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      var addr = data.address; // 최종 주소 변수
      // 주소 정보를 해당 필드에 넣는다.
      document.getElementById("sample5_address").value = addr;
    },
  }).open();
}
