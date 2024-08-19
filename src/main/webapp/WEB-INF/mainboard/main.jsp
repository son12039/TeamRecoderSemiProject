<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클럽 홍보게시판</title>
  <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
          <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css"
        />
</head>
<body>

<div id="container">
    <h1>${main.membership.membershipName }</h1>
    <img id="mainImg" src="http://192.168.10.51:8081/membership/${main.membership.membershipCode}/${main.membership.membershipImg}" >
    <h2>${main.membership.membershipInfo }</h2>
    <p>인원 현황 :  ${membershipUserCount}/${main.membership.membershipMax}</p>
    <h2>호스트 : ${main.member.nickname}</h2>
    <p>가입조건 : 사지멀쩡한 남녀노소 누구나!!</p>
    	
			
    	
    	<sec:authorize access="isAuthenticated()" var="principal">
<sec:authentication property="principal" var="member" />
    <c:choose>

    <c:when test="${checkMember.listGrade == 'guest'}">
				<p>가입 대기중인 클럽입니다</p>
			</c:when>
			<c:when
				test="${(checkMember != null) && (checkMember.listGrade != 'guest')}">
				<a href="/club/${main.membership.membershipCode}">☞ 내 클럽 페이지로 이동하기</a>
			</c:when>
    <c:when test="${membershipUserCount >= main.membership.membershipMax}">
    <h2>최대 인원에 도달한 클럽입니다 신청할수 없습니다.</h2>
    </c:when>
 
	
       <c:when test="${checkMember == null && member != null}"> 
     
    <form action="/membershipApply" method="post">
    <input type="submit" value="가입 신청하기">
    <input type="hidden" name="membershipCode" value="${main.membership.membershipCode}">
    <input type="hidden" name="id" value="${member.id}">
    <input type="hidden" name="listGrade" value="guest">
    </form>
    </c:when>
    </c:choose>
   </sec:authorize>
</div>

</body>
</html>