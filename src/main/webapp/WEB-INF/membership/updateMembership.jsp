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
<input id="hiddenImg" type="hidden" value="http://192.168.10.51:8081/membership/${membership.membershipCode}/${membership.membershipImg}">
	<sec:authorize access="isAuthenticated()" var="principal">
		<sec:authentication property="principal" var="member" />
      ${membership}
		<form enctype="multipart/form-data">
             <input type="hidden" id="membershipCode" name="membershipCode" value="${membership.membershipCode}">
			<label for="membershipName"> 클럽이름 : <span class="name" id="name"></span></label> 
				<input type="text" id="membershipName"
				name="membershipName" maxlength="50" value="${membership.membershipName}"><br> 
				
				사진첨부: <input type="file" name="file" id="file" accept="image/*" onchange="imgShow(event)"><br>
				
				클럽 가입조건 : <input type="text" id="membershipAccessionText" name="membershipAccessionText" value="${membership.membershipAccessionText}"><br>
				클럽 간단한 설명:<input type="text" id="membershipSimpleText" name="membershipSimpleText" value="${membership.membershipSimpleText}"><br>
				최대 인원 : <span class="max" id="max"></span> <input type="number"
				for="membershipMax" id="membershipMax" name="membershipMax" value="${membership.membershipMax}"><br>


								<div id="image_container">
									<img
										src="http://192.168.10.51:8081/membership/${membership.membershipCode}/${membership.membershipImg}"
										alt="Profile Image">
								</div>
						




			<!-- 08-20 채승훈 -->
			<p>지역</p>
			<select id="locationLaNameMem">

				<option for="allviwe" class="all" id="all" name="all">전체보기</option>
				<c:forEach items="${locLaNameList}" var="locationLaName">
					<option id="addlocation" >${locationLaName}</option>
				</c:forEach>
			</select> <select id="locationSNameMem">
				<option value="???">전체보기</option>
			</select>
			<div id="test1" class="select">${locList}</div>
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
			<div id="test2" class="select">${type}</div>
			<input type="button" value="추가" id="typeBtn"> 
			<input type="button" value="취소" id="typeBtncancel"><br><br>
			<button type="button" onclick="validate()">클럽생성</button>
			<div>
				<h2>${mem.id}</h2>
				<input type="hidden" name="id" value="${mem.id}"> <input
					type="hidden" name="listGrade" value="host"><br> <a href="/"
					id="toIndex">생성 취소</a>
			</div>
		</form>
	</sec:authorize>

	<script src="${pageContext.request.contextPath}/js/updateMembership.js"></script>


	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="${pageContext.request.contextPath}/js/locationType.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/locationTypePaging.js"></script>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>

function imgShow(event) {
	 var reader = new FileReader();
	   
	    reader.onload = function(event) {
	        var container = document.getElementById('image_container');
	        container.innerHTML = '';
	        var img = document.createElement('img');
	        img.className = 'image'
	        
	        img.setAttribute('src', event.target.result);
	        container.appendChild(img);
	      
	        
	    };
	   
	   
	    
	    if (event.target.files.length > 0) {
	    
	    	
	        reader.readAsDataURL(event.target.files[0]);
	        
	    } else {
	    	
	    	$(".image").remove();
	    	
	    }
}













</script>
</body>
</html>
