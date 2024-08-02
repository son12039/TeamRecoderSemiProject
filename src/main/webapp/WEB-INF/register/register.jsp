<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	  <form action="/register" method="post">
      아이디 : <input type="text" name="id" /><br />
      비밀번호 : <input type="password" name="pwd" /><br />
      주소 : <input type="text" name="addr" /><br />
      전화번호 : <input type="text" name="phone" /><br />
      이메일 : <input type="text" name="email" /><br />
      이름 : <input type="text" name="name" /><br />
      나이 : <input type="text" name="age" /><br />
      <label>
        성별 : 남<input type="checkbox" name="gender" /> 여<input
          type="checkbox"
          name="gender" /></label
      ><br />
      <input type="submit" value="회원가입" />
    </form>
</body>
</html>