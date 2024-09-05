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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/locationType.css" />
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
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />

		<form enctype="multipart/form-data">

			<label for="membershipName"> 클럽이름 : <span class="name" id="name"></span></label> 
				<input type="text" id="membershipName"
				name="membershipName" maxlength="50"><br> 
				사진첨부: <input type="file" name="file" id="file" accept="image/*"><br>
				클럽 가입조건 : <input type="text" id="membershipAccessionText" name="membershipAccessionText"><br>
				클럽 간단한 설명:<input type="text" id="membershipSimpleText" name="membershipSimpleText"><br>
				최대 인원 : <span class="max" id="max"></span> <input type="number"
				for="membershipMax" id="membershipMax" name="membershipMax"><br>


			<!-- 08-20 채승훈 -->
			<p>지역</p>
			<select id="locationLaNameMem">

				<option for="allviwe" class="all" id="all" name="all">전체보기</option>
				<c:forEach items="${locLaNameList}" var="locationLaName">
					<option id="addlocation">${locationLaName}</option>
				</c:forEach>
			</select> <select id="locationSNameMem">
				<option>전체보기</option>
			</select>
			<div id="test1" class="select"></div>
			<input type="button" value="추가" id="locationBtn">
			<input type="button" value="취소" id="locationBtncancel"><br><br>
			<p>유형</p>
			<select id="typeLaNameMem">
				<option>전체보기</option>
				<c:forEach items="${typeLaNameList}" var="typeLaName">
					<option>${typeLaName}</option>
				</c:forEach>
			</select> <select id="typeSNameMem">
				<option>전체보기</option>
			</select>
			<div id="test2" class="select"></div>
			<input type="button" value="추가" id="typeBtn"> 
			<input type="button" value="취소" id="typeBtncancel"><br><br>
			<button type="button" onclick="validate(event)">클럽생성</button>
			<div>
				<h2>${mem.id}</h2>
				<input type="hidden" name="id" value="${mem.id}"> <input
					type="hidden" name="listGrade" value="host"><br> <a href="/"
					id="toIndex">생성 취소</a>
			</div>
		</form>
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
