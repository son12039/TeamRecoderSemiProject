<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

    <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
          <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/myMembership.css"
        />
</head>
<body>

 <%-- 
  <c:forEach items="${membership}" var="mem">
   <h1>모임명 :${mem.membership.membershipName}</h1>
   <img src="${mem.membership.membershipImg }">
   <h2>모임 소개 :${mem.membership.membershipInfo}</h2>
   <h3>나의 등급 : ${mem.listGrade}</h3>
   
   
  
  </c:forEach>
   --%>
  <div class="membership-card">
   <c:forEach items="${membership}" var="mem">
   <div class="membership-each">
   <img class="membership-img" src="${mem.membership.membershipImg}">
   <div class="membership-text">
   <div class="membership-name"><p>${mem.membership.membershipName}</p> </div>
   <div class="membership-info"><p>${mem.membership.membershipInfo}</p></div>
   <div class="myGrade">
   <c:choose>
   <c:when test="${mem.listGrade == 'host'}">
   <p>내가 만든 클럽!</p>
   </c:when>
   <c:when test="${mem.listGrade == 'guest'}">
   <p>가입 대기중입니다!</p>
   </c:when>
   </c:choose>
   </div>
   </div>
   </div>
   </c:forEach>
  </div>
</body>
</html>