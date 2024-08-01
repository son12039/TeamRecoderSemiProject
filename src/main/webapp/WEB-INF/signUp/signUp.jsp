<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
            <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signUp.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css"/>
    
  </head>
  <body>
    <form action="/signUp" method="post">
      *필수 아이디 : <input type="text" name="id" /><br />
      *필수 비밀번호 : <input type="password" name="pwd" /><br />
      비밀번호 확인 : <input type="password"  /><br /> <!--  프론트에서 처리 -->
      주소 : <input type="text" name="addr" /><br />
      전화번호 : <input type="text" name="phone" /><br />
      이메일 : <input type="text" name="email" /><br />
      이름 : <input type="text" name="name" /><br />
      나이 : <input type="text" name="age" /><br /> <!--  프론트에서 비어있는거 null오류처리 -->
      <label for="gender">
        성별 : 남<input type="checkbox" name="gender" value="M"/> 
        여<input type="checkbox" name="gender"  value="F"  /></label
      ><br />
      *필수 닉네임 : <input type="text" name="userName" /><br />
      이미지주소 입력 : <input type="text" name="userImg" /><br />
      관심사를 적어주세요 : <input type="text" name="userHobby" /><br />
      자기소개를 해주세요 : <input type="text" name="userInfo" /><br />
      자주 활동하는 지역을 적어주세요 : <input type="text" name="userLocation" /><br />
      취미를 적어주세요  : <input type="text" name="userType" /><br />

      <input type="submit" value="회원가입" />
    </form>
  </body>
</html>
