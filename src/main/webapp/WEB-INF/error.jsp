<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>

    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/reset.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/headerIndex.css"
    />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Nanum+Pen+Script&family=New+Amsterdam&display=swap"
      rel="stylesheet"
    />

    <title>Document</title>
    <script
      src="https://kit.fontawesome.com/ef885bd654.js"
      crossorigin="anonymous"
    ></script>
    <style>
      body {
        background-color: #f9f7f3;
        width: 100%;
      }
      #box {
        height: 100vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }

      #box-top {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }
      #box-top > i {
        color: red;
        position: fixed;
      }
      #logo {
        position: fixed;
        font-size: 5rem;
        font-weight: bold;
        color: #fca35b;
        font-family: "Do Hyeon", sans-serif;
      }
      #box-medium {
        padding-top: 10px;
        padding-bottom: 80px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }
      #box-bottom {
        padding-top: 80px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
      }
      h1 {
        margin-top: 150px;
        font-family: "Do Hyeon", sans-serif;
        font-size: 2rem;
        margin-bottom: 10px;
      }
      h2 {
        font-family: "Do Hyeon", sans-serif;
        font-size: 1.5rem;
        margin-bottom: 10px;
      }
      h3 {
        font-family: "Do Hyeon", sans-serif;
        font-size: 1.2rem;
        margin-bottom: 5px;
      }
      a {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
      }
      a > i {
        color: #fca35b;
      }
      a > i:hover {
        color: #14213d;
      }
    </style>
  </head>

  <style></style>
  <body>
    <div id="box">
      <div id="box-top">
        <div id="logo">DAMOIM</div>
        <i class="fa-solid fa-ban fa-10x"></i>
      </div>
      <div id="box-medium"><h1>정상적인 페이지 접근이 아닙니다.</h1></div>
      <div id="box-bottom">
        <h2>비 정상적인 이용이 아닐 경우 관리자에게 문의 바랍니다.</h2>
        <a href="/">
          <h3>시작 페이지로 이동하기</h3>
          <i class="fa-solid fa-house-chimney fa-5x"></i
        ></a>
      </div>
    </div>
  </body>
</html>
