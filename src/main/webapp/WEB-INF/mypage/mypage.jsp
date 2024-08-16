<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</head>
<body>
	<nav>
		<a href="/">HOME</a> <a href="/logout">로그아웃</a> <a href="/updateCheck">중요정보
			수정</a>

	</nav>

	<form action="updateMember" id ="updateMember">
		<div class="info">
			<div class="text">성별</div>
			<c:choose>
				<c:when test="${mem.gender} == 'M'">
					<h1>남자</h1>
				</c:when>
				<c:otherwise>
					<h1>여자</h1>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="info">
			<div class="text">회원온도</div>
			<h1>${mem.memberManner}</h1>
		</div>

		<c:forEach items="${info}" var="info">
			<div class="info">
				<div class="text">모임명</div>

				<h1>${info.membership.membershipName}</h1>

				<c:choose>
					<c:when test="${info.listGrade == 'guest'}">
						<h1>가입 대기중입니다</h1>
					</c:when>
					<c:when test="${info.listGrade == 'host'}">
						<h1>${info.listGrade}</h1>
					</c:when>
				</c:choose>
			</div>
		</c:forEach>
		<button type ="submit">변경</button>
	</form>
</body>

</html>
