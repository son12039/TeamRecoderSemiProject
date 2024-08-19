<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
          href="${pageContext.request.contextPath}/css/index.css"
        />
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
      </head>

      <form action="/LocationType" method="get">통합버전<input type="submit"></form>

        <div class="membership-list">
<body>
	<div class="header">
		<div class="header_left">
			<a href="/">
				<div class="LOGO">DAMOIM</div>
			</a>
			<div class="header_right">
			
				<sec:authorize access="!isAuthenticated()">
              <div class="header_right_menu">
                <a href="/signUp">회원가입</a>
                <a href="/loginPage">로그인</a>
              </div>
            
              </sec:authorize>
              
				<sec:authorize access="isAuthenticated()" var="principal">
				<sec:authentication property="principal" var="member" />
				<p>${member}</p>
				<div> ${member.nickname}
				<c:choose>
						<c:when test="${member.memberImg != null}">
								<img class="user-img" src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}">
						</c:when>
					
						<c:otherwise>
							<img class="user-img"
								src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
						</c:otherwise>
					</c:choose>
					
				
				</div>
					<div class="header_right_menu">
						<a href="/update">마이페이지</a> <a href="/myMembership?id=${member.id}">나의
							모임</a> <a href="/logout">로그아웃</a>
					</div>
				</sec:authorize>
			</div>
		</div>
	</div>
	<div class="membership-list">
		<c:forEach items="${list}" var="id" varStatus="status">
			<div class="membership-card">
				<div class="membership-img">
					<a href="/${id.membership.membershipCode}"> 
					<c:choose>
						<c:when test="${id.membership.membershipImg != null}">
								<img src="http://192.168.10.51:8081/membership/${id.membership.membershipCode}/${id.membership.membershipImg}">
						</c:when>
					
						<c:otherwise>
							<img 
								src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">
						</c:otherwise>
					</c:choose>
					</a>
				</div>
				<div class="membership-info">
					<h1 class="membership-name">${id.membership.membershipName}</h1>
					<h2>${id.membership.membershipInfo}</h2>
					<h3>멤버수 :
						${countList.get(status.index)}/${id.membership.membershipMax}</h3>
						<div id="host">
						<c:choose>
						<c:when test="${id.member.memberImg != null}">
								<img class="user-img" src="http://192.168.10.51:8081/member/${id.member.id}/${id.member.memberImg}">
						</c:when>
						<c:otherwise>
							<img class="user-img"
								src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
						</c:otherwise>
					</c:choose>
					<h2>호스트 : ${id.member.nickname}</h2>
					<input type="hidden" name="code"
						value="${id.membership.membershipCode}">
					
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	
	
	
	<a href="/dummyUpdate">!!!!!!!!!!!주의!!!!!!!!!!!! 기존 유저들 비밀번호 암호화하는거임 건드리지 말것</a>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="login.js"></script>
	
	
	
</body>
</html>