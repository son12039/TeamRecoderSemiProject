<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
      
     
        
        <form action="/pwdCheck" method="post">
        비밀번호 입력 :  <input type="password" name="pwd">
        <input type="hidden" name="id" value="${mem.id}">
        <input type="submit" value="확인">
        </form>
       

</body>
</html>