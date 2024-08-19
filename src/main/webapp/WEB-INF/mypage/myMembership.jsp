<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    
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
        
      <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
        <style>
        .membership-card{
        display: none;
        }
        
        </style>
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
   
   <sec:authorize access="isAuthenticated()" var="principal">
				<sec:authentication property="principal" var="member" />
     <c:set var="hasHost" value="${false}" />

    <!-- Iterate through memberships to check for 'host' -->
    <c:forEach items="${membership}" var="mem">
        <c:if test="${mem.listGrade == 'host'}">
            <c:set var="hasHost" value="${true}" />
        </c:if>
    </c:forEach>

    <!-- Display the form to create a club only if no 'host' is present -->


 
	    <c:choose>
        <c:when test="${!hasHost}">
            <form action="/makeMembership">            
                <input type="hidden" name="id" value="${mem.id}">
                <button type="submit" value="클럽생성">클럽 만들기</button>
            </form>
        </c:when>
        <c:otherwise>

            <p>클럽 생성 기능이 활성화되지 않았습니다. 이미 보유중인 크럽이 있습니다.</p>
        </c:otherwise>
        </c:choose>
   
   <div class="membership-type">
   <button id="all-club-button">가입 중인 모든 클럽</button> 
  <button id="manage-club-button">내가 관리중인 클럽</button>  
  <button id="wait-club-button">가입 대기중인 클럽</button>  
  </div>
  
  <div class="membership-card" id = "wait-club">
  <h1>가입 대기중인 클럽 보기</h1>
   <c:forEach items="${membership}" var="mem">
    
    <c:if test="${mem.listGrade == 'guest'}">
   
  <div class="membership-each">
     <div><img  class="membership-img" src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"></div>
     <div class="membership-String">
     <div><p>${mem.membership.membershipName} </p></div>
     <div><p>${mem.membership.membershipInfo}</p></div>
     </div>
    </div>

    </c:if>
  
   </c:forEach>
  </div>
  
  <div class="membership-card" id = "manage-club">
  <h1>관리중인 클럽 보기</h1>
   <c:forEach items="${membership}" var="mem">
    
    <c:if test="${mem.listGrade == 'host' || mem.listGrade == 'admin'}">
    <a href="/club/${mem.membership.membershipCode}">
   <div class="membership-each">
     <div><img  class="membership-img" src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"></div>
     <div class="membership-String">
     <div><p>${mem.membership.membershipName} </p></div>
     <div><p>${mem.membership.membershipInfo}</p></div>
     </div>
    </div>
    </a>
    </c:if>
  
   </c:forEach>
  </div>
  <div class="membership-card" id = "all-club" style="display: block;">
  <h1>가입 된 클럽 보기</h1>
   <c:forEach items="${membership}" var="mem">
    
    <c:if test="${mem.listGrade == 'regular'|| mem.listGrade == 'host' || mem.listGrade == 'admin'}">
   <a href="/club/${mem.membership.membershipCode}">
   <div class="membership-each">
     <div><img  class="membership-img" src="http://192.168.10.51:8081/membership/${mem.membership.membershipCode}/${mem.membership.membershipImg}"></div>
     <div class="membership-String">
     <div><p>${mem.membership.membershipName} </p></div>
     <div><p>${mem.membership.membershipInfo}</p></div>
     </div>
    </div>
    </a>
    </c:if>
  
   </c:forEach>
  </div>
  
  </sec:authorize>
  
  <script src="${pageContext.request.contextPath}/js/myMembership.js">  
    </script>
</body>
</html>