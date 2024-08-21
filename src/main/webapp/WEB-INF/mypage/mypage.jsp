<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mypage.css" />
<title>Document</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

</head>
<body>
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		<nav>
			<a href="/">HOME</a> <a href="/logout">로그아웃</a> <a
				href="/updateCheck">회원정보 수정</a>
		</nav>
		<form action="/updateMember" method="post" id="form"
			enctype="multipart/form-data">
			<div class="info">
				<h1>${member.nickname}님의정보</h1>
			</div>
			<div class="info">
				<div class="text"></div>
				<h1>프로필 수정</h1>
				<input class="form-control" name="file" type="file" accept="image/*" id="file">
				<img
					src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}">
				<h3>나의 온도 : ${member.memberManner}°C</h3>
				<h3>
					memberInfo 수정 : <input type="text" id="memberInfo"
						name="memberInfo" value="${member.memberInfo}">
				</h3>
				<h3>
					memberHobby 수정 : <input type="text" id="memberHobby"
						name="memberHobby" value="${member.memberHobby}">
				</h3>
				<h3>
					memberType 수정 : <input type="text" id="memberType"
						name="memberType" value="${member.memberType}">
				</h3>
				<h3>
					memberLocation 수정 : <input type="text" id="memberType"
						name="memberLocation" value="${member.memberLocation}">
				</h3>
				<h3>성별</h3>
				<c:choose>
					<c:when test="${member.gender} === 'M'">
						<h1>남자</h1>
					</c:when>
					<c:otherwise>
						<h1>여자</h1>
					</c:otherwise>
				</c:choose>


				<div class="info">
					내가 가입한 클럽
					<c:forEach items="${info}" var="info">
						<h1>${info.membership.membershipName}</h1>
						<c:choose>
							<c:when test="${info.listGrade == 'guest'}">
								<h1>가입 대기중입니다</h1>
							</c:when>
							<c:when test="${info.listGrade == 'host'}">
								<h1>${info.listGrade}</h1>
							</c:when>
						</c:choose>
					</c:forEach>
				</div>
				<button type="button" id="updateSubmit">수정</button>
		</form>
		<script
			src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script>
	// file은 기본이 list라서 배열로 보내줘야한다
		$("#updateSubmit").click(()=>{
			const formData = new FormData();
			formData.append("memberInfo", $("#memberInfo").val());
			formData.append("file", $("#file")[0].files[0]);
			$.ajax({
				type: "post",
				url: "/updateMember",
				data: formData,
				contentType: false,
				processData: false,
				success: function (result) {
					if (result){
						window.location.href = "/";
						alert("정보가 수정되었습니다");
					}
				},
			});
		});
	</script>
	</sec:authorize>
</body>

</html>
