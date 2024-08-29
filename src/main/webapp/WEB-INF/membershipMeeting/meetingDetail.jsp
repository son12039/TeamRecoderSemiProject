<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<h1>${meet.meetTitle}</h1>
<h1>미트코드 : ${meet.meetCode}</h1>
<h1>작성자 : ${meet.id}</h1>

 <div id="meetInfo" >${meet.meetInfo}</div>


<c:forEach items="${list}" var="list" >
<c:if test="${list.meetAgreeYn == true }">
<p>참석여부 현황 : ${list.member.nickname} ---- 참여</p> 
</c:if>
<c:if test="${list.meetAgreeYn == false }">
<p>참석여부 현황 : ${list.member.nickname} ---- 불참</p> 
</c:if>
	<sec:authorize access="isAuthenticated()" var="principal">
<sec:authentication property="principal" var="member" />
<c:if test="${member.nickname == list.member.nickname}">
<form id="go">
<input type="hidden" name="meetCode" value="${meet.meetCode}">
<input type="hidden" name="id" value="${member.id}">
<input type="hidden" name="meetAgreeYn" value="${list.meetAgreeYn}"> 

<c:choose>
    <c:when test="${list.meetAgreeYn == false}">
        <input class="agree" type="submit" value="참가">
    </c:when>
    <c:otherwise>
        <input class="agree" type="submit" value="참가취소">
    </c:otherwise>
</c:choose>
</form>
</c:if>
</sec:authorize>


</c:forEach>

<form action="/meetingDelete">
<input type="hidden" name="meetCode" value="${meet.meetCode}">
<input type="submit" value="삭제">

</form>

  <script>
  
  
  
        $(document).ready(function() {
            $('#go').on('submit', function(event) {
                event.preventDefault(); // 폼의 기본 제출을 막습니다.
                
                $.ajax({
                    url: '/go', // 요청을 보낼 URL
                    type: 'POST',
                    data: $(this).serialize(), // 폼의 데이터를 직렬화합니다.
                    success: function(response) {
                        alert($(".agree").val()+" 완료되었습니다 !!");
                         location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert('오류가 발생했습니다.');
                        console.error(xhr.responseText);
                    }
                });
            });
        });
    </script>
</body>
</html>