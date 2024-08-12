<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>로그인 페이지</title>
        <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login.css"
        />
        <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
      </head>
      <body>
        <div class="header">
          <div class="header_left">
            <div class="header_right">
              <div class="header_right_menu"><a href="/register">회원가입</a>
              </div>
              <div class="header_right_menu"><a href="/login">로그인</a></div>
              <div class="header_right_menu"><a href="/mypage">마이페이지</a>
              </div>
            </div>
          </div>
        </div>
        <div class="container">
          <div class="login-box">
            <h1>로그인</h1>
            <form action="/login" method="post">
              <div class="textbox">
                <i class="fas fa-user"></i>
                <input type="text" placeholder="아이디" name="id" required />
              </div>
              <div class="textbox">
                <i class="fas fa-lock"></i>
                <input type="password" placeholder="비밀번호" name="pwd" />
              </div>
              <input type="submit" class="btn" value="로그인" />
            </form>
            <div class="login-boxs">
              <div class="google_box">
                <a href="https://www.google.com/">GOOGLE</a>
              </div>
              <div class="naver_box">
                <a href="https://www.naver.com/">NAVER</a>
              </div>
              <div class="facebook_box">
                <a href="https://www.facebook.com/">FACEBOOK</a>
              </div>
            </div>
          </div>
        </div>
        <div></div>
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <script src="login.js"></script>
      </body>
    </html>
  </body>
</html>
