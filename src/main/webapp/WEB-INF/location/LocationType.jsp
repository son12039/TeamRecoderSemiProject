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

	<c:if test="${locSNameList.size()!=0}">
	<form id="locationSNameForm">
		<input type="checkbox" value="전체보기" id="locSNameAll"/> <label for="locSNameAll">전체보기</label>
		<c:forEach items="${locSNameList}" var="locSName">
			<input type="checkbox" value="${locSName}" id="${locSName}" name="locationSName"> 
			<label for="${locSName}">${locSName}</label>
		</c:forEach>
		<input type="submit" value="지역 검색" id="locSNameBtn">
	</form>
	</c:if>
	<br>
	
	
	<select id="typeLaNameSelect">
		<option>전체보기</option>
		<c:forEach items="${typeLaNameList}" var="typeLaName">
			<option>${typeLaName}</option>
		</c:forEach>
	</select>
	
	<c:if test="${typeSNameList.size()!=0}">
	<form id="typeSNameForm">
		<input type="checkbox" value="지역 보기" id="typeSNameAll"><label for="typeSNameAll">전체보기</label>
		<c:forEach items="${typeSNameList}" var="typeSName">
			<input type="checkbox" value="${typeSName}" id="${typeSName}" name="typeSName">
			<label for="${typeSName}">${typeSName}</label>
		</c:forEach>	
		<input type="submit" value="타입 검색" id="typeSNameBtn">
	</form>
	</c:if>
	
	
	<br>
	<!-- 3. 로케이션 및 타입 리스트 뿌리기 -->
	<c:if test="${not empty list}">
	<c:forEach items="${list}" var="info">
		${info.membershipCode}<br>
		${info.membershipName}<br>
		${info.membershipImg}<br>
		${info.membershipInfo}<br>
		${info.membershipDate}<br>
		${info.membershipGrade}<br>
		${info.membershipMax}<br>
		${info.nickname}<br>
		${info.memberImg}<br>
	<c:forEach items="${info.locations}" var="location">
		# ${location.locLaName} ${location.locSName} 
	</c:forEach>
	<br>
	<c:forEach items="${info.types}" var="type">
	 	# ${type.typeLaName} ${type.typeSName}
	</c:forEach>
	------------------<br>
	</c:forEach>
	</c:if>
	<c:if test="${empty list}">
		<h1>없어용</h1>
	</c:if>
	
	<script src="${pageContext.request.contextPath}/js/locationType.js"></script>
</body>
</html>