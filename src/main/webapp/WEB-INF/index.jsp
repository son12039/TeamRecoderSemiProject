<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Damoim 페이지!!</title>
        <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
          <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/index.css"/>
        
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
       
      </head>
      
<body>
	<div class="header">
		<div class="header_left">
			<a href="/">
				<div class="LOGO">DAMOIM</div>
			</a>
			<div class="header_right">
				<c:if test="${empty mem}">
					<div class="header_right_menu">
						<a href="/signUp">회원가입</a> <a href="/loginPage">로그인</a>
					</div>
				</c:if>
				<c:if test="${not empty mem}">
				<div> ${mem.nickname}
				<img class="user-img" src="http://192.168.10.51:8081/member/${mem.id}/${mem.memberImg}">
				</div>
					<div class="header_right_menu">
						<a href="/mypage">내 정보</a> 
						<a href="/updateMembership">내 클럽 업데이트</a>
						<a href="/myMembership?id=${mem.id}">
						나의 모임</a> <a href="/logout">로그아웃</a>
					</div>
				</c:if>
			</div>
		</div>
	</div>


	<div class="membership-list">

		<c:forEach items="${list}" var="id" varStatus="status">

			<div class="membership-card">
				<div class="membership-img">
				<!-- 클럽 이미지 -->
					<a href="/${id.membership.membershipCode}"> <img
						src="http://192.168.10.51:8081/membership/${id.membership.membershipCode}/${id.membership.membershipImg}">
					</a>
					
					
				</div>
				<div class="membership-info">
				<!-- 농사짓기 -->
					<h1 class="membership-name">${id.membership.membershipName}</h1>
					<!-- 농사짓는 사람들의 모임입니다 -->
					<h2>${id.membership.membershipInfo}</h2>
					<h2>호스트 : ${id.member.nickname}</h2>
					<input type="hidden" name="code"
						value="${id.membership.membershipCode}">
					<h3>멤버수 : 
					
						${countList.get(status.index)}/
						${id.membership.membershipMax}
						<a href="/kakaoMap">
						<i class="fa-solid fa-location-dot">모임위치</i>
						</a>
						</h3>
						
					

					<c:choose>
						<c:when test="${id.member.memberImg == ''}">
							<img class="user-img"
								src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
						</c:when>
						<c:otherwise>
							<img class="user-img" src="http://192.168.10.51:8081/member/${id.member.id}/${id.member.memberImg}">
						</c:otherwise>
					</c:choose>
				</div>
			</div>

		</c:forEach>
	</div>
	<a href="/dummyUpdate">!!!!!!!!!!!주의!!!!!!!!!!!! 기존 유저들 비밀번호 암호화하는거임 건드리지 말것</a>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="login.js"></script>
	
</body>
</html>

