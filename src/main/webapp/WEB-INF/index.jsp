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
			<img  class="body_img" src="http://192.168.10.51:8081/member/asd12300/bf611066-4b30-43a3-b6ef-538811e5dcb8_pizza.jpg"/>
		</div>
	</div>
	<!--
	다시 꺼내써야함 
	
	
	
	잠깐 거두기
	<div class="header">
		<div class="header_left">
			<a href="/">
				<div class="LOGO">DAMOIM</div>
			</a>
			<div class="header_right">
				
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
	</div>
	 -->
	
	

	<!-- 08-20 채승훈 -->
<div class="locationTypeBody">
	<div class="locationTypeBodyBox">
	
		<form id="locationLaNameForm">
			<input type="checkbox" value="전체보기" id="locLaNameAll" /> <label for="locLaNameAll">전체보기</label>
			<c:forEach items="${locLaNameList}" var="locLaName">
				<input type="checkbox" value="${locLaName}" id="${locLaName}" name="locationLaName">
				<label for="${locLaName}">${locLaName}</label>
			</c:forEach>
		</form>
		
		
		<form id="locationSNameForm">
			<c:forEach items="${locSNameList}" var="locSName">
				<input type="checkbox" value="${locSName}" id="${locSName}"
					name="locationSName">
				<label for="${locSName}">${locSName}</label>
			</c:forEach>
		</form>
		
		<br>
		
		<form id="typeLaNameSelect">
			<input type="checkbox" value="전체보기" id="typeLaNameAll" /> <label for="typeLaNameAll">전체보기</label>
			<c:forEach items="${typeLaNameList}" var="typeLaName">
				<input type="checkbox" value="${typeLaName}" id="${typeLaName}" name="typeLaName">
				<label for="${typeLaName}">${typeLaName}</label>
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
					<a href="/${info.membershipCode}"> <c:choose>
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
					<div id="host">
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
						<br>
					</div>
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