<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/sendEmail" method="post">
  <div class="mb-3">
    <label for="id" class="form-label">아이디</label>
    <input type="text" class="form-control" id="id" name="id" >
  </div>
  <div class="mb-3">
    <label for="eamil" class="form-label">이메일</label>
    <input type="text" class="form-control" id="email" name="email">
  </div>
	<div id="result"></div>
  <button type="submit" class="btn btn-dark">비밀번호 찾기</button>
</form>
</body>
</html>