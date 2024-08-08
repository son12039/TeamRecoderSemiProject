<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h1>우리 클럽을 소개합니다!!!</h1>
    <h2>${main.membership.membershipName }</h2>
    <img id="mainImg" src="${main.membership.membershipImg }" >
    <h3>${main.membership.membershipInfo }</h3>
    <p>인원 현황 :  ${membershipUserCount}/${main.membership.membershipMax}</p>
    <h4>호스트 : ${main.member.nickname}</h4>
    <p>가입조건 : 사지멀쩡한 남녀노소 누구나!!</p>
    <c:choose>
   <c:when test="${checkMember == null && mem != null}"> 
    <form action="/membershipApply" method="post">
    <input type="submit" value="가입 신청하기">
    <input type="hidden" name="membershipCode" value="${main.membership.membershipCode}">
    <input type="hidden" name="id" value="${mem.id}">
    <input type="hidden" name="listGrade" value="guest">
    </form>
    </c:when>
    <c:when test="${checkMember != null}">
    <h2>이미 가입 신청한 클럽이거나 가입한 클럽입니다.</h2>
    </c:when>
    </c:choose>
</div>
</body>
</html>