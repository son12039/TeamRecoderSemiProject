<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>마이페이지 테스트</h1>
	<a href="">내정보 수정</a>
	<h2>회원 정보</h2>
	이름 : ${sessionScope.member.name}
	아이디 : ${sessionScope.member.id}
</body>
</html>