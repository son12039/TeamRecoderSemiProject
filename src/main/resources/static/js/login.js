// id가 id인 태그에 input 값이 입력될때마다 실행되는 함수

let check = false;

function validate() {
  if ($("#id").val() == "") {
    alert("아이디를 입력해주세요!");
    $("#id").focus();
   return false;
  } else if ($("#pwd").val() == "") {
    $("#pwd").focus();
    alert("비밀번호를 입력해주세요!");
	return false;
  } 

  
  return true;
  
}


