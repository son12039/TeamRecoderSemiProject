<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:forEach items="${LocationCategoryLarge}" var="Location">
	 <form action="/locationsSmall" method="get">
		<input type="submit" value="${Location.locLargeName}"/>
	</form>
</c:forEach>
</body>
</html>