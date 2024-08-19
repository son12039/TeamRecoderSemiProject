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
		<a href="/">HOME</a> 
		<a href="/logout">로그아웃</a> 
		<a href="/updateCheck">중요정보 수정</a>
	</nav>

	<form action="updateMember" id ="updateMember">
		<div class="info">
		<img src=""/>
			<div class="text">성별</div>
			<c:choose>
				<c:when test="${mem.gender} === 'M'">
					<h1>남자</h1>
				</c:when>
				<c:otherwise>
					<h1>여자</h1>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="info">
			<div class="text"></div>
			<h1>나의 정보</h1>
			<h3>${mem.memberManner}°C</h3>
			
			<h3>${mem.memberInfo} </h3>
			<input type="text" id="memberInfo" name="memberInfo" value="Info수정" >
			<h3>취미 : ${mem.memberHobby}</h3>
			<!-- 취미는 분류 받아서 업데이트 -->
			<h3>타입 : ${mem.memberType}</h3>
			<!-- 타입은 분류 받아서 업데이트 -->
			<h3>지역 : ${mem.memberLocation}</h3>
			<!-- 장소도 분류 받아서 업데이트 -->
		</div>
		
		<div class="info">가입한 클럽
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

		<button type ="submit">변경</button>
	</form>
</body>

</html>
