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
<title>Document</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/userInfo.css" />
<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<main>

		<div id="container">
			<div id="section">
				<div>
					<c:choose>
						<c:when test="${mem.member.memberImg != null}">
						<img
							src="http://192.168.10.51:8081/member/${mem.member.id}/${mem.member.memberImg}">
						</c:when>
						<c:otherwise>
						<img
							src="http://192.168.10.51:8081/기본프사.jpg">
						</c:otherwise>
					</c:choose>
				</div>

				<div class="info">

						<div class="group">
							<span>모임 참여 횟수 ${mem.member.memberMeetCount}회</span>
						</div>
						<div class="group">
							<span>온도 ${mem.member.memberManner}℃</span>
						</div>

				</div>
			</div>
			<div class="memberInfo_div">
				<h1>
					<i class="fa-solid fa-user"></i> ${mem.member.nickname}
				</h1>
			</div>
			<div class="memberInfo_div">
				<h2>${mem.member.memberInfo}</h2>
			</div>

			<div id="section2">
				<div class="section2_memberInfo">
					<h1>가입한 클럽</h1>
				</div>
				<div class="club_info">
					<c:forEach items="${mem.membershipUserList}" var="list">
						<c:if test="${list.listGrade != 'guest'}">
							<div class="club_box">
							<a href="/${list.membership.membershipCode}"><img class="club_img" src="http://192.168.10.51:8081/membership/${list.membership.membershipCode}/${list.membership.membershipImg}"></a>
							<p>${list.membership.membershipName}</p>
							</div>
						</c:if>
							<!-- <c:if test="${list.listGrade == 'guest'}">
							<img class="club-img" src="http://192.168.10.51:8081/membership/${list.membership.membershipCode}/${list.membership.membershipImg}">
						</c:if> -->
					</c:forEach>
				</div>
				<div class="section2_memberInfo">
					<h1>가입신청한 클럽</h1>
					<div class="club_info">
					<c:forEach items="${mem.membershipUserList}" var="list">
						 <c:if test="${list.listGrade == 'guest'}">
						 	<div class="club_box">
							<a href="/${list.membership.membershipCode}""><img class="club_img" src="http://192.168.10.51:8081/membership/${list.membership.membershipCode}/${list.membership.membershipImg}"></a>
							<p>${list.membership.membershipName}</p>
							</div>
						</c:if>
					</c:forEach>
					</div>
				</div>
				
			</div>
		</div>
	</main>

</body>
</html>
