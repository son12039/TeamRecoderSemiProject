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
			<div id="header">${mem.member.nickname} 님의 프로필</div>
			<div id="section">
				<img src="http://192.168.10.51:8081/member/${mem.member.id}/${mem.member.memberImg}">
				<div class="info">
					<div class="text">
						<div>
							<h1>
								<i class="fa-solid fa-user"></i>${mem.member.nickname}
							</h1>
							<button>팔로우?</button>
							<button>메시지 보내기</button>
						</div>
						

						<div id="group">
							<span>모임 참여 횟수 </span>
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
				<c:forEach items="${dto}" var="list">
					<h1>가입한 클럽들</h1>
					<img src="img/cake.jpg" /> 
					<img src="img/cat.jpg" /> 
					<img src="img/family1.jpg" />
					</c:forEach>
				</div>
			</div>
		</div>
	</main>

</body>
</html>
