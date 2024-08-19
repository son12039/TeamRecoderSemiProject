<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 부트 스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
  <script type="text/javascript">
     
    </script>
</head>
<body>
<div class="container">
  <h1>로그인</h1>    <!--  제출전 한번 더 검증하기 위함  -->
  <form action="/login" method="post" onsubmit="return validate()" >
  
  <div class="mb-3">
    <label for="id" class="form-label">아이디</label>
    <input type="text" class="form-control" id="id" name="username" >
    <div id="idCheck" class="form-text"></div>
  </div>
  <div class="mb-3">
    <label for="password" class="form-label">비밀번호</label>
    <input type="password" class="form-control" id="pwd" name="password">
    <div id="pwdCheck" class="form-text"></div>
  </div>
	<div id="result"></div>
  <button type="submit" class="btn btn-dark">로그인</button>
               
               
</form>
<a href="/findMember">아이디/비밀번호 찾기</a>
</div>




  <script src="${pageContext.request.contextPath}/js/login.js"></script> 
</body>
</html>