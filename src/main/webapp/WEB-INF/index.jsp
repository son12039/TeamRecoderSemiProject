<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Damoim 페이지!!</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/index.css" />
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>


	<jsp:include page="header/header.jsp" />
	<div class="body_img_box_body">
		<div class="body_img_box">
			<img  class="body_img" src="http://192.168.10.51:8081/mainImg.jpg"/>
		</div>
	</div>


	<!-- 08-20 채승훈 -->
<div class="locationTypeBody">
	<div class="locationTypeBodyBox">
		<form id="locationLaNameForm">
			<input type="checkbox" value="전체보기" id="locLaNameAll" /> <label class="locLaNameLabel" for="locLaNameAll">전체보기</label>
			<c:forEach items="${locLaNameList}" var="locLaName">
				<input type="checkbox" value="${locLaName}" id="${locLaName}" name="locationLaName">
				<label for="${locLaName}" class="locationLaCss">${locLaName}</label>
			</c:forEach>
		</form>
		
		
		<form id="locationSNameForm">
			<c:forEach items="${locSNameList}" var="locSName">
				<input type="checkbox" value="${locSName}" id="${locSName}"
					name="locationSName">
				<label for="${locSName}" class="locationTypeCss">${locSName}</label>
			</c:forEach>
		</form>
		

		<form id="typeLaNameSelect">
			<input type="checkbox" value="전체보기" id="typeLaNameAll" /> <label class="typeLaNameLabel" for="typeLaNameAll">전체보기</label>
			<c:forEach items="${typeLaNameList}" var="typeLaName">
				<input type="checkbox" value="${typeLaName}" id="${typeLaName}" name="typeLaName">
				<label for="${typeLaName}" class="typeLaCss">${typeLaName}</label>
			</c:forEach>
		</form>
		
		
		
		<form id="typeSNameForm">
			<c:forEach items="${typeSNameList}" var="typeSName">
				<input type="checkbox" value="${typeSName}" id="${typeSName}"
					name="typeSName">
				<label for="${typeSName}">${typeSName}</label>
			</c:forEach>
		</form>
		
	</div>
</div>



	<br>
	<div class="membership-list">
		<c:forEach items="${list}" var="info" varStatus="status">
			<div class="membership-card">
				<div class="membership-img">
					<a href="/${info.membershipCode}">
					 <c:choose>
							<c:when test="${info.membershipImg != null}">
								<img src="http://192.168.10.51:8081/membership/${info.membershipCode}/${info.membershipImg}">
							</c:when>
							<c:otherwise>
								<img src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">
							</c:otherwise>
						</c:choose>
					</a>
				</div>
				<div class="membership-info">
					<h1 class="membership-name">${info.membershipName}</h1>
					<h2>${info.membershipSimpleText}</h2>
					<h3>멤버수 :
						${info.count}/${info.membershipMax}</h3>
					<a href="/userInfo/${info.nickname}">
					<div class="host">
						<c:choose>
						
							<c:when test="${info.memberImg != null}">
								<img class="user-img" src="http://192.168.10.51:8081/member/${info.id}/${info.memberImg}">
							</c:when>
							<c:otherwise>
								<img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
							</c:otherwise>
						</c:choose>
						<h2>호스트 : ${info.nickname}</h2>
						<input type="hidden" name="code" value="${info.membershipCode}">
					
					</div>
					</a>
						<div class="locationTypeBox">
							<div class="location">
								<c:forEach items="${info.locations}" var="location">
										<div class="locationList"># ${location.locLaName} ${location.locSName}</div>
								</c:forEach>
							</div>
							<br>
							<div class="type">
								<c:forEach items="${info.types}" var="type">
									 	 <div class="typeList">${type.typeSName}</div>
								</c:forEach>
							</div>
						</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<jsp:include page="footer/footer.jsp" />
	
	<!-- <a href="/dummyUpdate">!!!!!!!!!!!주의!!!!!!!!!!!! 기존 유저들 비밀번호 암호화하는거임 건드리지 말것</a> -->
	<!-- <div class="membership-list"> -->
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="${pageContext.request.contextPath}/js/locationType.js"></script>
	<script src="${pageContext.request.contextPath}/js/locationTypePaging.js"></script>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	
	
	
</body>
</html>