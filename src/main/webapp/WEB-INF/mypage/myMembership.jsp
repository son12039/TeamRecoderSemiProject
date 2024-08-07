<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

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

  <div class="membership-card">
  	<c:choose>
  		<c:when test="${not empty membership}">

		<c:forEach items="${membership}" var="mem">
			
				<a href="/${mem.membership.membershipCode}club"><div class="membership-each">
				<img class="membership-img" src="${mem.membership.membershipImg}">
				<div class="membership-text">
					<div class="membership-name">
						<p>${mem.membership.membershipName}</p>
					</div>
					<div class="membership-info">
						<p>${mem.membership.membershipInfo}</p>
					</div>
					</a>
					<!--  이렇게가 아니라 가입대기중 클럽 , 호스트, 관리자, 회원 인클럽 분리 -->
					<div class="myGrade">
						<c:choose>
							<c:when test="${mem.listGrade == 'host'}">
								<p>호스트 입니다.</p>
							</c:when>
							<c:when test="${mem.listGrade == 'admin'}">
								<p>관리자 입니다.</p>
							</c:when>
							<c:when test="${mem.listGrade == 'regular'}">
								<p>회원 입니다.</p>
							</c:when>
							<c:when test="${mem.listGrade == 'guest'}">
								<p>가입 대기중입니다!</p>
							</c:when>
						</c:choose>
					</div>
				</div>
			</div>
		</c:forEach>
		</c:when>
		<c:otherwise>
			<h1>현재 가입된 클럽이 없습니다.</h1>
		</c:otherwise>
  	</c:choose>
	</div>
   
   <div class="membership-type">
  <button class="all">전체 목록 </button>  
  <button class="mine">내가 만든 모임</button>  
  <button class="ing">가입 중인 모임</button>  
  <button class="wait">가입 대기중인 모임</button>  
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
  <div class="membership-card" style="display: none">
  
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
  
  <div class="membership-card" style="display: none">
  
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
  <div class="membership-card" style="display: none">
  
   <c:forEach items="${membership}" var="mem">
    
    <c:if test="${mem.listGrade == 'admin'}">
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
  <script src="${pageContext.request.contextPath}/js/myMembership.js"></script>
</body>
</html>