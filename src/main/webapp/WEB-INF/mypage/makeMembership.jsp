<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>클럽 생성</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/makeMembership.css" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<style>
select {
	display: block;
	width: 200px;
	padding: 10px;
	margin: 10px 0;
	border: 2px solid #333;
	border-radius: 5px;
	background-color: #f0f0f0;
}
</style>
</head>
<body>
	<jsp:include page="../header/header.jsp" />
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
		<div class="box">
			<form enctype="multipart/form-data">
				<label for="membershipName"> 클럽이름 : <span class="name"
					id="name"></span></label> <input type="text" id="membershipName"
					name="membershipName" maxlength="50"><br> 사진첨부: <input
					type="file" name="file" id="file" accept="image/*"><br>
				클럽 가입조건 : <input type="text" id="membershipAccessionText"
					name="membershipAccessionText"><br> 클럽 간단한 설명:<input
					type="text" id="membershipSimpleText" name="membershipSimpleText"><br>
				최대 인원 : <span class="max" id="max"></span> <input type="number"
					for="membershipMax" id="membershipMax" name="membershipMax"><br>

				<!-- ======================================= -->
				<!-- 08-20 채승훈 -->
				<!-- 09/06 서동문 CSS 작업 -->
				<div class="container">
					<div class="select-group">
						<div class="select-title">
							<p>지역 선택</p>
						</div>
						<div class="select-menu">
							<div class="select">
								<div class="select-option"></div>

							</div>
						</div>
						<div id="test1">
							<h1>선택한</h1>
						</div>
					</div>

					<!-- ======================================= -->

					<div class="select-group">
						<div class="select-title">
							<p>유형</p>
						</div>
						<div class="select-menu">
							<div class="select">
								<div class="select-option">
									<select id="typeLaNameMem">
										<option>전체보기</option>
										<c:forEach items="${typeLaNameList}" var="typeLaName">
											<option>${typeLaName}</option>
										</c:forEach>
									</select> <select id="typeSNameMem">
										<option>전체보기</option>
									</select>
								</div>
								<div class="select-input">
									<input type="button" value="추가" id="typeBtn" class="select-btn">
									<input type="button" value="취소" id="typeBtncancel"
										class="select-btn">
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<!-- 도시별 지역별 태그 선택 ============================================================== -->
		<div class="locationTypeBody">
			<div class="locationTypeBodyBox">
				<form id="locationLaNameForm">
					<div class="locationBoxHead">
						<div class="locationLaStar">도시별</div>
						<input type="checkbox" value="초기화" id="locLaNameAll" /> <label
							class="locLaNameLabel" for="locLaNameAll">초기화</label>
					</div>
					<div class="locationLaDiv">
						<c:forEach items="${locLaNameList}" var="locLaName">
							<input type="checkbox" value="${locLaName}" id="${locLaName}"
								name="locationLaName">
							<label for="${locLaName}" class="locationLaCss">${locLaName}</label>
						</c:forEach>
					</div>
				</form>
				<div class="line"></div>
				
				<form id="locationSNameForm">
					<div class="locationSStar">지역별</div>
					<div class="locationSDiv">
						<div class="classiFication">지역을 선택해주세요</div>
						<c:forEach items="${locSNameList}" var="locSName">
							<input type="checkbox" value="${locSName}" id="${locSName}"
								name="locationSName">
							<label for="${locSName}" class="locationTypeCss">${locSName}</label>
						</c:forEach>
					</div>
				</form>
			</div>
		</div>
		<!-- 도시별 지역별 태그 선택 ============================================================== -->
		
		<!-- 유형별 취미 태그 선택 =============================================================== -->
	 	<div class="typeBody">
			<div class="typeBodyBox">
				<form id="typeLaNameForm">
					<div class="typeBoxHead">
						<div class="typeLaStar">유형</div>
						<input type="checkbox" value="초기화" id="typeNameAll" /> <label
							class="typeLaNameLabel" for="typeNameAll">초기화</label>
					</div>
					<div class="typeLaDiv">
						<c:forEach items="${typeLaNameList}" var="typeLaName">
							<input type="checkbox" value="${typeLaName}" id="${typeLaName}"
								name="locationLaName">
							<label for="${typeLaName}" class="typeLaCss">${typeLaName}</label>
						</c:forEach>
					</div>
				</form>
				<div class="line"></div>
				<form id="typeSNameForm">
					<div class="typeSStar">취미별</div>
					<div class="typeSDiv">
						<div class="typeClassiFication">취미를 선택해주세요</div>
						<c:forEach items="${typeSNameList}" var="typeSName">
							<input type="checkbox" value="${typeSName}" id="${typeSName}"
								name="typeSName">
							<label for="${typeSName}" class="typeCss">${typeSName}</label>
						</c:forEach>
					</div>
				</form>
			</div>
		</div>
	
		<div class="box">
			<div class="container">

				<div class="form-group">
					<div id="test2" class="button-group">
						<button type="button" onclick="validate(event)" class="btn">클럽생성</button>
						<h2>${mem.id}</h2>
						<input type="hidden" name="id" value="${mem.id}"> <input
							type="hidden" name="listGrade" value="host">
					</div>
				</div>

			</div>
		</div>











	</sec:authorize>

	<script src="${pageContext.request.contextPath}/js/makeMembership.js"></script>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="${pageContext.request.contextPath}/js/locationType.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/locationTypePaging.js"></script>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</body>
</html>
