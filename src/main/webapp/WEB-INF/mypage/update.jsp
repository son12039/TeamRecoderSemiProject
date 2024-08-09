<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/update.css" />
  </head>
  <body>
    <div class="container">
      <h1>회원 정보 수정</h1>
      <form action="/update" method="post" id="form">
        <div class="form-group">
          <label>비밀번호</label>
          <p>현재 비밀번호 / ${mem.pwd}</p>
          <label>변경할 비밀번호</label>
          <span id="pwdCheck"></span>
          <input type="password" id="pwd" name="pwd" placeholder="pwd" />
        </div>
        <div class="form-group">
          <label >이름</label>
          <p>현재 이름 / ${mem.name}</p>
          <label>변경할 이름</label>
          <span id="nameCheck"></span>
          <input type="text" id="name" name="name" placeholder="name"  />
        </div>
        <div class="form-group">
          <label >연락처</label>
          <p>현재 연락처 / ${mem.phone}</p>
          <label>변경할 연락처</label>
          <span id="phoneCheck"></span>
          <input type="tel" id="phone" name="phone" placeholder="phone" />
        </div>
        <div class="form-group">
          <label >주소</label>
          <p>현재 주소 / ${mem.addr}</p>
          <label>변경할 주소</label>
          <span id="addrCheck"></span>
          <input type="text" id="addr" name="addr" placeholder="addr"  />
        </div>
        <div class="form-group">
          <label>Email</label>
          <p>현재 이메일 / ${mem.email}</p>
          <label>변경할 이메일</label>
          <span id="emailCheck"></span>
          <input type="email" id="email" name="email" placeholder="email"  />
        </div>
        <div class="form-group">
          <label >나이</label>
          <p>현재 나이 / ${mem.age}</p>
          <label>변경할 나이</label>
          <span id="ageCheck"></span>
          <input type="text" id="age" name="age" placeholder="age"  />
        </div>
        <button type="submit">제출</button>
      </form>
    </div>
    <script src="${pageContext.request.contextPath}/js/update.js"></script>
  </body>
</html>
