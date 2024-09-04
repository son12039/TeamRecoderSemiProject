<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/updateCheck.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="container">
		<h1>비밀번호를 입력하세요</h1>
		<form onsubmit="return false">
			<input type="password" name="pwdCheck" id="pwdCheck"> 
			<button type="button" id="button">확인</button>
		</form>
		
		<!-- 
		텍스트 인풋 영역에서 커서가 있는 상태에서 
		키보드 Enter를 치게 되면 submit이 일어남
		그럴 경우 onsubmit="return false" 로 폼 전송을 막는다
		그런다음 엔터 이벤트 (keyCode = 13)와 버튼의 클릭 이벤트를 걸어서
		updateMember() 함수 작동
		 -->
		<script>
			$("#pwdCheck").keyup((e) => {
				if(e.keyCode === 13) {
					e.preventDefault();
					resignMember();
				}
			})
			$("#button").click(() => {
				const pwdCheck = $("#pwdCheck").val();
				resignMember();
			});
			
			function resignMember() {
				$.ajax({
					type: "post",
					url: "/resignCheck",
					data: "pwdCheck=" + $("#pwdCheck").val(),
					success: function (result){
						console.log(result);
						if(result){
							window.location.href = "/memberDelete";
						} else {
							alert("비밀번호가 다릅니다");
						}
					},
				});
			}
		</script>
	</div>
</body>
</html>