<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
  </head>
  <body>
    
     
   
   <c:if test="${mem.name == null }">
    <a href="/register"> 회원가입</a>
    <form action="/login" method="post"> 
    아이디 : <input type="text" name="id"> <br>
    비밀번호 : <input type="text" name="pwd"> <br>
    <input type="submit" value="확인"> <br>
 
    </form>
       </c:if>
       
       <c:if test="${mem.name != null}">
       <h1>${mem.name} 님 환영합니다</h1>
       </c:if>
  </body>
</html>