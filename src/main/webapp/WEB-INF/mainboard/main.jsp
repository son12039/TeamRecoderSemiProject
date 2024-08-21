<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클럽 홍보게시판</title>
  <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/reset.css"
        />
          <link
          rel="stylesheet"
          href="${pageContext.request.contextPath}/css/main.css"
        />
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <style>
        .reply-form{
       	 display: none;
        }
        </style>
</head>
<body>

<div id="container">
    <h1>${main.membership.membershipName }</h1>
    <img id="mainImg" src="http://192.168.10.51:8081/membership/${main.membership.membershipCode}/${main.membership.membershipImg}" >
    <h2>${main.membership.membershipInfo }</h2>
    <p>인원 현황 :  ${main.count}/${main.membership.membershipMax}</p>
    <h2>호스트 : ${main.member.nickname}</h2>
    <p>가입조건 : 사지멀쩡한 남녀노소 누구나!!</p>
    	
			
    	
    	<sec:authorize access="isAuthenticated()" var="principal">
<sec:authentication property="principal" var="member" />

	<c:set var="memberGrade" value="none"/>
	<c:forEach items="${member.memberListDTO}" var="list" >
		<c:if  test="${list.membershipCode == main.membership.membershipCode}">
			<c:set var="memberGrade" value="${list.listGrade}"/>
	</c:if>
    </c:forEach>
    
    <c:choose>
    

    		<c:when test="${memberGrade == 'guest'}">
				<p>가입 대기중인 클럽입니다</p>
			</c:when>
			<c:when
				test="${memberGrade == 'regular' || memberGrade == 'host' || memberGrade == 'admin'}">
				<a href="/club/${main.membership.membershipCode}">☞ 내 클럽 페이지로 이동하기</a>
			</c:when>
    <c:when test="${memberGrade == 'none' && main.count >= main.membership.membershipMax}">
    <h2>최대 인원에 도달한 클럽입니다 신청할수 없습니다.</h2>
    </c:when>
 
	
       <c:when test="${memberGrade == 'none' && main.count < main.membership.membershipMax }"> 
     
    <form action="/membershipApply" method="post">
    <input type="submit" value="가입 신청하기">
    <input type="hidden" name="membershipCode" value="${main.membership.membershipCode}">
    <input type="hidden" name="id" value="${member.id}">
    <input type="hidden" name="listGrade" value="guest">
    </form>
    </c:when>
    </c:choose>
</div>
	<div id="comment-container">
    <form id="comment-frm">
    <div id="comment-box">
    <input type="text" name="mainCommentText" placeholder="댓글을 입력하세요">
    <input type="hidden" name="id" value="${member.id}">
    <input type="hidden" name="membershipCode" value="${main.membership.membershipCode}">
    <button id="submit-comment" type="submit">댓글 등록</button>
    </div>
    </form>
    <c:choose>
    <c:when test="${fn:length(comment) == 0}">
    
    <p>현재 등록된 댓글이 없습니다.</p>
	</c:when>

<c:otherwise>

<c:forEach items="${comment}" var="com">
    <div id="comm-${com.mainCommentCode}" class="comment">
        댓글 내용 ${com.mainCommentText} <br/>
        댓글 작성시간 ${com.mainCommentDate} <br/>
        댓글 작성자 닉네임 ${com.member.nickname} <br/>
        <button type="button" onclick="showReplyForm(${com.mainCommentCode})">대댓글</button>
        
        <div id="reply-form-${com.mainCommentCode}" class="reply-form" >
            <form id="comment-frm-${com.mainCommentCode}">
                <div id="comment-box">
                    <input type="text" name="mainCommentText" placeholder="대댓글을 입력하세요" >
                    <input type="hidden" name="mainParentsCommentCode" value="${com.mainCommentCode}">
                    <input type="hidden" name="id" value="${com.member.id}">
                    <input type="hidden" name="membershipCode" value="${com.membershipCode}">
                    <button id="submit-comment-comment-${com.mainCommentCode}" data-comment-code="${com.mainCommentCode}" type="submit">댓글 등록</button>
                </div>
            </form>
        </div>
    </div>
</c:forEach>

</c:otherwise>
  </c:choose>

  
 </sec:authorize>
 </div>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>