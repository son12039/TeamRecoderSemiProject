<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>정보 수정</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/updateMembership.css">
</head>
<body>
	<jsp:include page="/WEB-INF/header/header.jsp" />
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		
		<div class="container">
			<h1>클럽 정보 수정</h1>
			<form action="/updateMembership" method="post"
				enctype="multipart/form-data">
				<div class="form-group">
					<label for="membershipName">클럽 이름 수정 : </label> <input type="text"
						id="membershipName" name="membershipName">
				</div>
				
				<button type="submit">수정하기</button>
			</form>
		</div>
	</sec:authorize>



</body>
</html>