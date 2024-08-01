<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypage.css" />
    <title>Document</title>
  </head>
  <body>
    <div class="img">
      <div class="header">
        <div class="LOGO">LOGO</div>
        <div class="menu">menu1</div>
        <div class="menu">menu2</div>
        <div class="menu">menu3</div>
        <div class="menu">menu4</div>
        <div class="header_right">
        <a href="/">HOME</a>
          <a href="/logout">로그아웃</a>
        </div>
      </div>
      <div class="section">
        <div class="mypage"><h1>My Page</h1></div>
        <div>
          <label for="id">
            <div class="info">
              <div class="text">아이디</div>
              <h1>${mem.id}</h1>
            </div></label
          >
          <div class="info">
          <div class="text"> 비밀번호 변경 </div>
          <form action="/update" method="post">
   <input type="text" name="pwd" value="${mem.pwd}">
   <input type="submit" value="수정">
 </form>
          
          </div>
          <div class="info">
            <div class="text">이름</div>
            <h1>${mem.name}</h1>
          </div>
          <div class="info">
            <div class="text">연락처</div>
            <h1>01026110875</h1>
          </div>
          <div class="info">
            <div class="text">주소</div>
            <h1>부천</h1>
          </div>
          <div class="info">
            <div class="text">Email</div>
            <h1></h1>
          </div>
          <div class="info">
            <div class="text">나이</div>
            <h1>${mem.age}</h1>
          </div>
          <div class="info">
            <div class="text">성별</div>
            <h1></h1>
          </div>
          <div class="info">
            <div class="text">회원등급</div>
            <h1></h1>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
