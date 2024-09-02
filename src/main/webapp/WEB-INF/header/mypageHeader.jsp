<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mypageHeader.css" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

</head>
<body>
	<!-- 로그인 정보에 따라 헤더와 메뉴 표시 -->
	<nav class="header_nav">
		<div class="header_left">
			<a id="LOGO" href="/">다모임</a>
		</div>
		<div class="header_right">
			<a href="/mypage">마이페이지</a>
			<a href="/logout">로그아웃</a>
		</div>
	</nav>
</body>
</html>