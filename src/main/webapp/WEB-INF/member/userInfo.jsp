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
					<div class="text">
						<div>
							<h1>
								<i class="fa-solid fa-user"></i> ${mem.member.nickname}
							</h1>
						</div>
						<div id="group">
							<span>모임 참여 횟수</span>
							<p> ${mem.member.memberMeetCount}회</p>
							<span>온도 ${mem.member.memberManner}℃</span>
						</div>
						<div>
							<h1>자기소개</h1>
						</div>
						<div>
							<h3>${mem.member.memberInfo}</h3>
						</div>
					</div>
				</div>
			</div>
			<div id="section2">
				<div>
					<h1>가입한 클럽</h1>
					<c:forEach items="${mem.membershipUserList}" var="list">
						<a href="/"><img class="club-img"
							src="http://192.168.10.51:8081/membership/${list.membership.membershipCode}/${list.membership.membershipImg}"></a>
					</c:forEach>
				</div>
			</div>
		</div>
	</main>

</body>
</html>
