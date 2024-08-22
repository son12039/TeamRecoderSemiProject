<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
</head>
<body>
	<div class="main_header">
		<div class="main_header_left">
			<div class="main_header_left_text">문토</div>
			<div class="main_header_left_text">문토</div>
			<div class="main_header_left_text">문토</div>
			<div class="main_header_left_text">문토</div>
		</div>
		<div class="main_header_center">
			<div class="main_header_center_text">DAMOIM</div>
		</div>
		<div class="main_header_rigth">
				<sec:authorize access="!isAuthenticated()">
	             <div class="header_right_menu">
	               <a href="/signUp">회원가입</a>
	               <a href="/loginPage">로그인</a>
	             </div>
              </sec:authorize>
				<sec:authorize access="isAuthenticated()" var="principal">
					<sec:authentication property="principal" var="member" />
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
							<a href="/update">마이페이지</a> <a href="/myMembership">나의
								모임</a> <a href="/logout">로그아웃</a>
						</div>
				</sec:authorize>
		</div>
	</div>
</body>
</html>