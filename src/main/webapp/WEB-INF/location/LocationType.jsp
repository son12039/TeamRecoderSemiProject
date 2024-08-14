<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/locationType.css" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<select id="locationLaNameSelect">
		<option>전체보기</option>
		<c:forEach items="${locLaNameList}" var="locLaName">
			<option>${locLaName}</option>
		</c:forEach>
	</select>
	
	<c:forEach items="${list}" var="info">
		${info.membershipCode}<br>
		${info.membershipName}<br>
		${info.membershipImg}<br>
		${info.membershipInfo}<br>
		${info.membershipDate}<br>
		${info.membershipGrade}<br>
		${info.membershipMax}<br>
		<c:forEach items="${info.locations}" var="location">
			# ${location.locLaName} ${location.locSName} 
		</c:forEach>
	<br>
		<c:forEach items="${info.types}" var="type">
		 	# ${type.typeLaName} ${type.typeSName}
		</c:forEach>
		------------------<br>
	</c:forEach>
	
	<script src="${pageContext.request.contextPath}/js/locationType.js"></script>
</body>
</html>