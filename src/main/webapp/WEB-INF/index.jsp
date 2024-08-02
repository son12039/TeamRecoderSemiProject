<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
</head>
<body>
    <div class="container">
        <section class="one">
        <a href="/mypage">마이페이지</a>
        <a href="/login">로그인</a>
        <a href="/regiter">회원가입</a>
		<c:forEach items="${Profile}" var = "image">
		<div>
			<img src="${image.imgUrl}"/>
		</div>
		</c:forEach>
            <h1>First Page</h1>
        </section>
        <section class="two">
            <h1>First Page</h1>
        </section>
        <section class="three">
            <h1>First Page</h1>
        </section>
        <section class="four">
            <h1>First Page</h1>
        </section>
        <section class="five">
            <h1>First Page</h1>
        </section>
    </div>
    <script src="mainpage.js"></script>
</body>
</html>