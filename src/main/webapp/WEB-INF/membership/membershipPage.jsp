
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
          
         <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<main>
	<form id="chatserver">
		<input type="button" value="채팅서버가기">
	 </form>
	<a href="chatserver?membershipName=${main.membership.membershipName}">채팅서버가기</a>
    <h2>${main.membership.membershipName}</h2>
    <img id="mainImg" src="${main.membership.membershipImg }" >
    <h3>${main.membership.membershipInfo }</h3>
    <p>인원 현황 :  ${membershipUserCount}/${main.membership.membershipMax}</p>
    <h4>호스트 : ${main.member.nickname} <img id="hostImg" src="${main.member.memberImg}"></h4>
    
    <c:forEach items="${allMember}" var="cMember">
    	<div class="memberTable">
    		<c:choose>
    		<c:when test="${cMember.listGrade == 'guest'}">
    			<ul> 
    		<li>${cMember.member.nickname} - 가입 대기중</li>
    		<li><img class="allmemberImg" src="${cMember.member.memberImg}"></li>
    		<c:if test="${main.member.id == mem.id}">
    		<form action="/agreeMember" method="post">
    			<input type="hidden" name="id" value="${cMember.member.id}">
    			<input type="hidden" name="listGrade" value="regular">
    			<input type="hidden" name="membershipCode" value="${main.membership.membershipCode}">
    			<input type="submit" value="가입 승인하기">
    		</form>
    		</c:if>
    		</ul>	    		</c:when>
    		<c:otherwise>
    		<ul> 
    		<li>${cMember.member.nickname}</li>
    		<li><img class="allmemberImg" src="${cMember.member.memberImg}"></li>
    		<li>${cMember.listGrade}</li>
    		</ul>
    		</c:otherwise>
    		</c:choose>
    	</div>
    </c:forEach>
    </main>
    <script src="${pageContext.request.contextPath}/js/membershipPage.js"></script>
</body>
</html>
