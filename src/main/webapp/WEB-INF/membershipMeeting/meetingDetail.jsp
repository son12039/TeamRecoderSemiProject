<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>${meet.meetTitle}</h1>
<h1>미트코드 : ${meet.meetCode}</h1>
<h1>작성자 : ${meet.id}</h1>
<h2>${meet.meetInfo}</h2>
<c:forEach items="${list}" var="list" >

<p>참석여부 현황 : ${list.member.nickname} ---- ${list.meetAgreeYn}</p> 
	<sec:authorize access="isAuthenticated()" var="principal">
<sec:authentication property="principal" var="member" />
<c:if test="${member.nickname == list.member.nickname}">
<form action="/go">
<input type="hidden" name="meetCode" value="${meet.meetCode}">
<input type="hidden" name="id" value="${member.id}">
<input type="submit" value="참가">
</form>
</c:if>
</sec:authorize>


</c:forEach>

</body>
</html>