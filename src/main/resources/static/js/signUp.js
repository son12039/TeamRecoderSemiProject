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
const idRegExp = /^[a-zA-Z0-9]{10,20}$/;
const pwdRegExp = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{10,20}$/;

let chk1 = false;
let chk2 = false;

$('#id').on('keyup', function() { // 아이디 체크
  const idValue = $(this).val().trim();
  
  if (idValue === '') {
    $('#idResult').text("필수 입력사항입니다").css('color', 'red');
    chk1 = false;
  } else if (!idRegExp.test(idValue)) {
    $('#idResult').text("영어,숫자로만 10~20자를 입력해주십시오").css('color', 'red');
    chk1 = false;
  } else {
    $.ajax({
      type: 'POST',
      url: '/idCheck', // 컨트롤러 URL
      data: { id: idValue },
      success: function(result) {
        if (result) { 
			console.log(result);
          $('#idResult').text("사용 가능한 ID 입니다").css('color', 'green');
          chk1 = true;
        } else {
			console.log(result);
          $('#idResult').text("중복 ID 입니다").css('color', 'red');
          chk1 = false;
        }
      }
    });
  }
});

$('#nickname').on('keyup', function() { // 닉네임 체크
  const nicknameValue = $(this).val().trim();
  
  if (nicknameValue === '') {
    $('#nicknameResult').text("필수 입력사항입니다").css('color', 'red');
    chk2 = false;
  } else {
    $.ajax({
      type: 'POST',
      url: '/nicknameCheck', // 컨트롤러 URL
      data: { nickname: nicknameValue },
      success: function(result) {
        if (result) { // 서버 응답을 'true' 또는 'false'로 가정
			console.log(result);
          $('#nicknameResult').text("사용 가능한 닉네임 입니다").css('color', 'green');
          chk2 = true;
        } else {
			console.log(result);
          $('#nicknameResult').text("중복 닉네임 입니다").css('color', 'red');
          chk2 = false;
        }
      }
    });
  }
});

$('#age').on('keyup', function() {
  const ageValue = $(this).val().trim();
  
  if (ageValue === '' || isNaN(Number(ageValue)) || Number(ageValue) <= 0) {
    $(this).val('0');
    
  } 
});

$('#signUpForm').on('submit', function(e) {
  let isValid = true;

  if (!chk1) {
    alert("아이디를 확인해 주세요.");
    isValid = false;
  }

  if (!chk2) {
    alert("닉네임을 확인해 주세요.");
    isValid = false;
  }

  if (!pwdRegExp.test($('#pwd').val())) {
    alert("비밀번호는 10~20자, 영어, 숫자, 특수문자를 포함해야 합니다.");
    isValid = false;
  }

  if ($('#pwd').val() !== $('#pwdc').val()) {
    alert("비밀번호가 일치하지 않습니다.");
    isValid = false;
  }

  const ageValue = $('#age').val().trim();
  if (ageValue === '' || isNaN(Number(ageValue)) || Number(ageValue) <= 0) {
    alert("나이는 숫자만 입력할 수 있습니다.");
    isValid = false;
  }

  if (!isValid) {
    e.preventDefault(); // 폼 제출 방지
  }
});
