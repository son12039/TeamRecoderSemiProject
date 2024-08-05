<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h1> 상세 페이지 </h1>
 <h1>${main.membership.membershipName }</h1>
 <h3>${main.membership.membershipInfo }</h3>
 <img src="${main.membership.membershipImg }">
<h1>호스트 : ${main.member.nickname}</h1>
</body>
</html>