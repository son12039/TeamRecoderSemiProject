<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css" />
  </head>
  <body>
    <div class="container">
      <h1>회원 정보 수정</h1>
      <form action="/update" method="post">
        <div class="form-group">
          <label>아이디 </label>
          <p>${mem.id}</p>
          <input type="text" id="username" name="id" placeholder="id"  />
        </div>
        <div class="form-group">
          <label>비밀번호 변경</label>
          <p>${mem.pwd}</p>
          <input type="password" id="password" name="pwd" placeholder="pwd" />
        </div>
        <div class="form-group">
          <label >이름</label>
          <p>${mem.name}</p>
          <input type="text" id="name" name="name" placeholder="name"  />
        </div>
        <div class="form-group">
          <label >연락처</label>
          <p>${mem.phone}</p>
          <input type="tel" id="contact" name="phone" placeholder="phone" />
        </div>
        <div class="form-group">
          <label >주소</label>
          <p>${mem.addr}</p>
          <input type="text" id="address" name="addr" placeholder="addr" />
        </div>
        <div class="form-group">
          <label>Email</label>
          <p>${mem.email}</p>
          <input type="email" id="email" name="email" placeholder="email" />
        </div>
        <div class="form-group">
          <label >나이</label>
          <p>${mem.age}</p>
          <input type="number" id="age" name="age" placeholder="age" />
        </div>
        <div class="form-group">
          <label >성별</label>
          <p>${mem.gender}</p>
          <select id="gender" name="gender" >
            <option value="" disabled selected>선택하세요</option>
            <option value="male">남성</option>
            <option value="female">여성</option>
            <option value="other">기타</option>
          </select>
        </div>
        <div class="form-group">
          <label >회원등급</label>
          <input type="text" id="membership" name="membership" placeholder="listGrade" />
        </div>
        <button type="submit">제출</button>
      </form>
    </div>
  </body>
</html>
