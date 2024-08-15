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
	<!-- 08_14 -->
	<!-- 1.화면 옵션에 도시 이름 전체 리스트 보여주기 -->
	<select id="locationLaNameSelect">
		<option>전체보기</option>
		<c:forEach items="${locLaNameList}" var="locLaName">
			<option>${locLaName}</option>
		</c:forEach>
	</select>
	
	<select>
		<option>전체보기</option>
	</select>
	
	<select id="typeLaNameSelect">
		<option>전체보기</option>
		<c:forEach items="${typeLaNameList}" var="typeName">
			<option>${typeName}</option>
		</c:forEach>
	</select>
	
	<select>
		<option>전체보기</option>
	</select>
	<br>
	<!-- 3. 로케이션 및 타입 리스트 뿌리기 -->
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