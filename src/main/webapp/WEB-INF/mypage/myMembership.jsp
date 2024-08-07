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
        
        <style>
        .membership-card { display: none; }
        .active { display: block; }
    </style>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
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
   
<div class="membership-type">
        <button class="all" data-target="allmem">전체 목록</button>
        <button class="mine" data-target="minemem">내가 만든 모임</button>
        <button class="ing" data-target="ingmem">가입 중인 모임</button>
        <button class="wait" data-target="waitmem">가입 대기중인 모임</button>
    </div>
  <div class="membership-card" id="allmem" >
  
   <c:forEach items="${membership}" var="mem">
    
    <div class="membership-each" >
     <div><img  class="membership-img" src="${mem.membership.membershipImg}"></div>
     <div class="membership-String">
     <div><p>${mem.membership.membershipName} </p></div>
     <div><p>${mem.membership.membershipInfo}</p></div>
     </div>
    </div>
  
   </c:forEach>
    </div>
  <div class="membership-card"  id="waitmem">
  
   <c:forEach items="${membership}" var="mem">
    
    <c:if test="${mem.listGrade == 'guest'}">
  <div class="membership-each">
     <div><img  class="membership-img" src="${mem.membership.membershipImg}"></div>
     <div class="membership-String">
     <div><p>${mem.membership.membershipName} </p></div>
     <div><p>${mem.membership.membershipInfo}</p></div>
     </div>
    </div>
    </c:if>
  
   </c:forEach>
  </div>
  
  <div class="membership-card" id="minemem">
  
   <c:forEach items="${membership}" var="mem">
    
    <c:if test="${mem.listGrade == 'host'}">
   <div class="membership-each">
     <div><img  class="membership-img" src="${mem.membership.membershipImg}"></div>
     <div class="membership-String">
     <div><p>${mem.membership.membershipName} </p></div>
     <div><p>${mem.membership.membershipInfo}</p></div>
     </div>
    </div>
    </c:if>
  
   </c:forEach>
  </div>
  <div class="membership-card"  id="ingmem">
  
   <c:forEach items="${membership}" var="mem">
    
    <c:if test="${mem.listGrade != 'guest'}">
   <div class="membership-each">
     <div><img  class="membership-img" src="${mem.membership.membershipImg}"></div>
     <div class="membership-String">
     <div><p>${mem.membership.membershipName} </p></div>
     <div><p>${mem.membership.membershipInfo}</p></div>
     </div>
    </div>
    </c:if>
  
   </c:forEach>
  </div>
  <script src="${pageContext.request.contextPath}/js/myMembership.js">
     
    </script>
</body>
</html>