<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/reset.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypage.css"
    />
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
            <div class="text">비밀번호 변경</div>
            <form action="/update" method="post">
              <input type="text" name="pwd" value="${mem.pwd}" />
              <input type="submit" value="수정" />
            </form>
          </div>
          <div class="info">
            <div class="text">이름</div>
            <h1>${mem.name}</h1>
          </div>
          <div class="info">
            <div class="text">연락처</div>
            <h1>${mem.phone}</h1>
          </div>
          <div class="info">
            <div class="text">주소</div>
            <h1>${mem.addr}</h1>
          </div>
          <div class="info">
            <div class="text">Email</div>
            <h1>${mem.email}</h1>
          </div>
          <div class="info">
            <div class="text">성별</div>
            <c:choose>
              <c:when test="${mem.gender} == M">
                <h1>남자</h1>
              </c:when>
              <c:otherwise>
                <h1>여자</h1>
              </c:otherwise>
            </c:choose>
          </div>
          <div class="info">
            <div class="text">회원온도</div>
            <h1>${mem.memberManner}</h1>
            <h1></h1>
          </div>
          <c:forEach items="${info}" var="info">
            <div class="info">
              <div class="text">모임명</div>
              <h1>${info.membership.membershipName}</h1>

              <c:choose>
                <c:when test="${info.listGrade == 'guest'}">
                  <h1>가입 대기중입니다</h1>
                </c:when>
                <c:when test="${info.listGrade == 'host'}">
                  <h1>${info.listGrade}</h1>
                </c:when>
              </c:choose>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
  </body>
</html>
