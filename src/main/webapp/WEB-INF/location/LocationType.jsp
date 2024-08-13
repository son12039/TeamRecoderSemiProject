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
<!-- 대분류 위치 -->
<select id="locationLaNameSelect">
	<option value="">----------------</option>
	<c:forEach items="${allMemberLaList}" var="Location">
		<option name="${Location.locLaName}">${Location.locLaName}</option>
	</c:forEach>
</select>

<!-- 소분류 위치 (aJax로 만들어짐) -->
<select id="locationSNameSelect">
	<option value="">----------------</option>
	<option name=""></option>
	</div>
</select>


<!-- 대분류 타입 -->
<select id="typeLanameSelect">
	<option value="">----------------</option>
	<c:forEach items="${allMemberLaType}" var="Type">
		<option name="${Type.typeLaName}">${Type.typeLaName}</option>
	</c:forEach>
</select>

<!-- 소분류 타입 (aJax로 만들어짐) -->
<select id="typeSNameSelect">
	<option value="">----------------</option>
	<option name=""></option>
	</div>
</select>



<!-- 더미데이터 -->
<!-- 조건 가쟈와서 하나씩 쪼개기? -->


<div class="allMemberBoxhead">
	<div class="allMemberBoxBody">
		<c:forEach items="${allMember}" var="allMember">
			<div class="allMemberBox">
				<div>큰 지역 :
					${allMember.getMembershipLocation().getLocationCategory().getLocLaName()}</div>
				<div>작은 지역 :
					${allMember.getMembershipLocation().getLocationCategory().getLocSName()}</div>
				<div>큰 타입 :
					${allMember.getMembershipType().getTypeCategory().getTypeLaName()}</div>
				<div>작은 타입 :
					${allMember.getMembershipType().getTypeCategory().getTypeSName()}</div>
			</div>
		</c:forEach>
	</div>
</div>





<script src="${pageContext.request.contextPath}/js/locationType.js"></script>
</body>
</html>