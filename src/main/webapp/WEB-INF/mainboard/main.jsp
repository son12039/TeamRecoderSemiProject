<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</div>
</body>
</html>