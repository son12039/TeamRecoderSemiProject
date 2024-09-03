<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>


	    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous"
    />
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/reset.css" />
   <link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/login.css" />

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<script src="https://kit.fontawesome.com/ef885bd654.js"
	crossorigin="anonymous"></script>
	<style>
		#box{
			padding-top: 100px;
		}
	</style>
</head>
<body>
	<jsp:include page="../header/header.jsp"></jsp:include>
<div id="box">

	    <div class="container">
      <h1>비밀번호 찾기</h1>
    
      <form action="/sendEmail" method="post">
        <div class="mb-3">
          <label for="id" class="form-label">아이디</label>
          <input type="text" class="form-control" id="id" name="id" />
        </div>
        <div class="mb-3">
           <label for="eamil" class="form-label">이메일</label>
   		 <input type="text" class="form-control" id="email" name="email">
        </div>
        <c:if test="${message == '임시 비밀번호가 이메일로 전송되었습니다.'}">
        <div id=green>${message}</div>
        </c:if>
        <c:if test="${message != '임시 비밀번호가 이메일로 전송되었습니다.'}">
        <div id="red">${message}</div>
        </c:if>
        
  	<button type="submit" class="btn">비밀번호 찾기</button>
      </form>
     	<div id="a-link">
        <a class="link" class="link" href="/loginPage">로그인 페이지</a>
        <span class="link">|</span>
        <a class="link"  href="/findId">아이디 찾기</a>
        <span class="link">|</span>
        <a class="link"  href="/findPassword">비밀번호 찾기</a>
	</div>
</div>
</div>
<jsp:include page="../footer/footer.jsp" />
</body>
</html>