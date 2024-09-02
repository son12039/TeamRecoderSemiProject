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
<!-- Do+Hyeon 폰트 -->
<!-- font-family: "Do Hyeon", sans-serif; -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/headerIndex.css" />
	<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Nanum+Pen+Script&family=New+Amsterdam&display=swap" rel="stylesheet">
<!-- 로고 폰트 -->
<!-- font-family: "Chewy", system-ui; -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Chewy&family=Do+Hyeon&family=Gothic+A1:wght@100;200;300;400;500;600;700;800;900&family=Jua&display=swap" rel="stylesheet">
</head>
<body>
	<div class="main_header">
		<div class="main_header_body">
		<!-- 
			<div class="main_header_left">
				<a  href="/">공지사항</a>
				<a  href="/">이벤트</a>
			</div>		
		 -->
			<div class="main_header_center">
				<a href="/" class="main_header_center_text">DAMOIM</a>
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
								<a href="/update">마이페이지</a> 
								<a href="/myMembership">나의모임</a> 
								<a href="/logout">로그아웃</a>
							</div>
					</sec:authorize>
			</div>		
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>