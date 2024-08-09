<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/location.css"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<button class="button">지역</button>
<div class="type-box">
	<div class="type1">
	<c:forEach items="${allLocation}" var="location">
		<div class="tpye-box-child">
			<div class="types">${location.locLaName}</div>
		</div>
	</c:forEach>
	</div>
</div>



<script>
$(document).ready(function() {
    $(".button").click(function() {
        $(".tpye-box-child").toggle(); // 클릭 시 박스의 표시 상태를 토글
    });
    
});
</script>
</body>
</html>