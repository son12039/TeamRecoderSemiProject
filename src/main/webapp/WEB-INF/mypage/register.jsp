<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
    
  </head>
  <body>
  
    <form action="/register" method="post">
      아이디 : <input type="text" id="memberId" name="id" /><br />
      비밀번호 : <input type="password" id="memberPwd" name="pwd" /><br />
      비밀번호 확인 : <input type="password" id="PwdCheck" name="pwdCheck">
      주소 : <input type="text" name="addr" /><br />
      전화번호 : <input type="text" name="phone" /><br />
      이메일 : <input type="text" name="email" /><br />
      이름 : <input type="text" name="name" /><br />
      나이 : <input type="text" name="age" /><br />
      <label>
        성별 : 남<input type="checkbox" name="gender"  value="M"/> 여<input
          type="checkbox"
          name="gender" value="F" /></label
      ><br />
      <input type="submit" value="회원가입" />
    </form>
     <script  src="${pageContext.request.contextPath}/js/login.js">
   </script>
   
  </body>
</html>