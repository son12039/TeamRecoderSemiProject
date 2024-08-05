<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>로그인 페이지</title>
      
        <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
          <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login.css"
        />
       
      </head>
      <body>
     
        <div class="header">
          <div class="header_left">
            <div class="LOGO">DAMOIM</div>
            <div class="menu">menu1</div>
            <div class="menu">menu2</div>
            <div class="menu">menu3</div>
            <div class="menu">menu4</div>
            <div class="header_right">
          <c:if test="${empty mem}">
              <div class="header_right_menu">
                <a href="/">회원가입</a>
              </div>
            
              </c:if>
              <c:if test="${not empty mem}">
              <div class="header_right_menu">
                <a href="/mypage">마이페이지</a> 
                <a href="/logout">로그아웃</a>
              </div>
              </c:if>
            </div>
          </div>
        </div>
        <c:if test="${empty mem}">
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
             <a href="/register">아직 회원이 아니라면?</a> <input type="submit" class="btn" value="로그인" />
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
          </c:if>
          <c:if test="${not empty mem }">
          <div class="container">
          <div class="login-box">
            <h1>${mem.name}님 환영합니다~</h1>
            </div>
          </div>      
          </c:if>
        </div>
        
        
        <div class="membership-list">
        
       <c:forEach  items="${list}" var="id">
       <div class="membership-card">
       <div class="membership-img">
       <img  src="${id.membership.membershipImg}">    
       </div>  
       <div class="membership-info">
       <h1 class="membership-name"> ${id.membership.membershipName}</h1>
       <h2>${id.membership.membershipInfo} </h2>   
       <h2>호스트 : ${id.member.nickname}</h2>
      
    
        <c:choose>
        <c:when test="${id.member.memberImg.equals('')}">
        <img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">
        </c:when>
        <c:otherwise>
       <img class="user-img" src="${id.member.memberImg}"> 
       </c:otherwise>
       </c:choose>
    
     
       </div>
       </div>
       </c:forEach>
        </div>
      
        
         
        <script src="https://kit.fontawesome.com/a076d05399.js"></script>
        <script src="login.js"></script>
      </body>
    </html>
  </body>
</html>