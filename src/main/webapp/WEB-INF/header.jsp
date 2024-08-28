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
        <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/header.css"
        />
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
      </head>
      
<body>
 <header>
	<div class="header">
		<div class="header_left">
			<a href="/">
				<div class="LOGO">DAMOIM</div>
			</a>
			</div>
			<div class="header_right">
			
				<sec:authorize access="!isAuthenticated()">
              <div class="header_right_menu">
                <a href="/signUp">회원가입</a>
                <a href="/loginPage">로그인</a>
              </div>
              </sec:authorize>
              
				<sec:authorize access="isAuthenticated()" var="principal">
				<sec:authentication property="principal" var="member" />
			
				<div class="index-member-area"> 
				<div class="index-member-img">
				<c:choose>
				  
						<c:when test="${member.memberImg != null}">
								<img class="index-profile-img" src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}">
						</c:when>
						<c:otherwise>
							<img class="index-profile-img"
								src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
						</c:otherwise>
						
					</c:choose>
						</div>
						<div class="index-member-memberinfo">
				         <p class="index-member-nickname">${member.nickname}님</p>
				         <p class="index-member-info">${member.memberInfo}</p>
					      </div>
		
				</div>
					<div class="index-member-menu">
						<div class="index-member-link"></a><a href="/update">마이페이지</a> </div>
						<div class="index-member-link"><a href="/myMembership?id=${member.id}">나의모임</a> </div>
						<div class="index-member-link"><a href="/logout">로그아웃</a></div>
							
							</div>

				</sec:authorize>
			</div>
		
	</div>
	
	 </header>
	</body>
</html>