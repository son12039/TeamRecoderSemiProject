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
</head>
<body>
	
	<div class="locationTable">
      <div class="location">
		<c:forEach items="${allType}" var="type">
			 <form action="/locationtype" method="get">
				<input type="hidden" name="type" value="${type.typeLaName}" />
				<input type="submit" value="${type.typeLaName}"/>
			</form>
		</c:forEach>
      </div>
    </div>
</body>
</html>