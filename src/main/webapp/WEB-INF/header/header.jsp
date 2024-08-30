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
		<div class="main_header_body">
			<div class="main_header_left">
				<a  href="/">공지사항</a>
				<a  href="/">이벤트</a>
			</div>
			<div class="main_header_center">
				<div class="main_header_center_text">DAMOIM</div>
			</div>
			<div class="main_header_right">
					<sec:authorize access="!isAuthenticated()">
		             <div class="main_header_right_menu">
			               <a href="/signUp">회원가입</a>
			               <a href="/loginPage">로그인</a>
		             </div>
	              </sec:authorize>
					<sec:authorize access="isAuthenticated()" var="principal">
						<sec:authentication property="principal" var="member" />
							<div class="main_header_right_user"> 
								<div class="main_header_right_nickname">${member.nickname}</div>
								<c:choose>
									<c:when test="${member.memberImg != null}">
											<img class="main_header_right_user_img" src="http://192.168.10.51:8081/member/${member.id}/${member.memberImg}">
									</c:when>
								
									<c:otherwise>
										<img class="main_header_right_user_img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
									</c:otherwise>
								</c:choose>	
							</div>
							<div class="main_header_right_menu">
								<a href="/mypage">마이페이지</a> 
								<a href="/logout">로그아웃</a>
							</div>
					</sec:authorize>
			</div>		
		</div>
	</div>
</body>
</html>