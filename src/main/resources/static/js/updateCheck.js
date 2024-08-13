const updateCheck = document.querySelector("#updateCheck");
const checkSpan = document.querySelector("#checkSpan");
const pwdCheck = document.querySelector("#pwdCheck");

updateCheck.addEventListener("submit" , function() {
	
	if (!pwdCheck.value){
		checkSpan.style.color = "red"
		checkSpan.innerHTML = "비밀번호가 틀렸습니다 다시 입력해주세요";
	}
	
})