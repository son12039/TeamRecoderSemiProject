<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:forEach items="${allType}" var="type">
		 <form action="/location/typeSmall" method="get">
			<input type="hidden" name="type" value="${type.locLargeCode}" />
			<input type="submit" value="${type.locLargeName}"/>
		</form>
	</c:forEach>
</body>
</html>