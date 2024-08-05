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
		<c:forEach items="${allLocation}" var="Location">
			 <form action="/location/Locations" method="get">
				<input type="hidden" name="location" value="${Location.locLargeCode}" />
				<input type="submit" value="${Location.locLargeName}"/>
			</form>
		</c:forEach>
      </div>
      <div class="locationTableChild">
        <p>1</p>
        <p>2</p>
        <p>3</p>
        <p>4</p>
        <p>5</p>
      </div>
    </div>

</body>
</html>