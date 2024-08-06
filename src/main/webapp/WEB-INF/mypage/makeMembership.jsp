<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/makeMembership" method="post">
 클럽명 : <input type="text" name="membershipName">
  사진첨부: <input type="file" name="membershipImg">
   클럽소개 :<input type="text" name="membershipInfo">
    최대 인원 : <input type="text" name="membershipMax">
    <h2> ${mem.id}</h2>
     <input type="hidden" name="id" value="${mem.id}">
     <input type="hidden" name="listGrade" value="host">
    <button type="submit">클럽생성</button>
    </form>

</body>
</html>