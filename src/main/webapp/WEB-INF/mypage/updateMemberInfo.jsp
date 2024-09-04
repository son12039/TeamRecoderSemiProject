<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>회원 정보 수정</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/updateMemberInfo.css" />

</head>
<body>
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		<jsp:include page="/WEB-INF/header/header.jsp" />

		<!-- (동문) -->
		<div class="top-container">
			<div class="container">
				<h1>회원 정보 수정</h1>
				<div class="form-group">
					<form action="/updateMemberInfo" method="post" id="form">
						<div class="form-group">
							<h2>닉네임</h2>
							<input type="text" id="nickname" name="nickname"
								placeholder="nickname" value="${member.nickname}" /> <span
								id="nicknameCheck"></span>
						</div>
						<div class="form-group">
							<h2>비밀번호</h2>
							<input type="password" id="pwd" name="pwd" placeholder="pwd"
								required /> <span id="pwdCheck"></span>
						</div>
						<div class="form-group">
							<h2>비밀번호 확인</h2>
							<input type="password" id="pwdConfirmCheck" placeholder="pwd"
								required /> <span id="pwdConfirm"></span>
						</div>
						<div class="form-group">
							<h2>이름</h2>
							<input type="text" id="name" name="name" placeholder="name"
								value="${member.name}" required /> <span id="nameCheck"></span>
						</div>
						<div class="form-group">
							<h2>연락처</h2>
							<input type="tel" id="phone" name="phone" placeholder="phone"
								value="${member.phone}" required /> <span id="phoneCheck"></span>
						</div>
						<div class="form-group">
							<h2>주소</h2>
							<div id="addrDetail-box">
								<c:set var="addressParts" value="${fn:split(member.addr, '#')}" />
								<c:choose>
									<c:when test="${fn:length(addressParts) == 2}">
										<div class="form-group">
											<input type="text" id="sample5_address" name="addr"
												value="${addressParts[0]}" placeholder="주소" required /> <input
												type="button" id="addr-btn"
												onclick="sample5_execDaumPostcode()" value="주소 검색" /> <input
												type="text" id="addrDetail" name="addrDetail"
												value="${addressParts[1]}" placeholder="상세주소를 입력해주세요" />
										</div>
									</c:when>
								</c:choose>
							</div>
						</div>
						<div class="form-group">
							<h2>이메일</h2>
							<input type="email" id="email" name="email" placeholder="email"
								value="${member.email}" required /> <span id="emailCheck"></span>
						</div>
						<div class="form-group">
							<h2>나이</h2>
							<input type="text" id="age" name="age" placeholder="age"
								value="${member.age}" required /> <span id="ageCheck"></span>
						</div>
						<div class="button_box">
							<button type="submit" id="updateSubmit">변경</button>
							<i class="fa-solid fa-xmark"></i><a href="/resignPage"
								class="resign">회원탈퇴</a>
						</div>

					</form>
				</div>
			</div>
		</div>
		<jsp:include page="../footer/footer.jsp" />
		<script src="https://kit.fontawesome.com/a076d05399.js"></script>

		<script
			src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/updateMemberInfo.js"></script>
		<script>
		
		$("#updateSubmit").click(()=>{
			const updateSubmit = $("#nickname").val();
			$.ajax({
				type: "post",
				url: "/updateMemberInfo",
				data: $("#form").serialize(),
				success: function (result) {
					if (result){
						window.location.href = "/";
					} else {
						nicknameCheck.innerHTML= "이미 있는 닉네임 입니다"
						nicknameCheck.style.color= "red"						
					}
				},
			});
		});
		
		
	</script>
	</sec:authorize>
</body>
</html>













